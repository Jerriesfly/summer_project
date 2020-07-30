package dao;

import Utils.JdbcUtil;
import beans.TravelImage;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TravelImageDao {
    public ArrayList<Integer> getHotImagesID(int number, String condition) throws SQLException {
        Connection connection = JdbcUtil.getConnection();
        String sql;
        if (condition == null) {
            sql = "SELECT travelimagefavor.ImageID, Count(travelimagefavor.UID) AS favorCount FROM travelimagefavor " +
                    "INNER JOIN travelimage ON travelimage.ImageID = travelimagefavor.ImageID " +
                    " GROUP BY ImageID ORDER by favorCount DESC";
        } else {
            sql = "SELECT travelimagefavor.ImageID, Count(travelimagefavor.UID) AS favorCount FROM travelimagefavor " +
                    "INNER JOIN travelimage ON travelimage.ImageID = travelimagefavor.ImageID " +
                    condition + " GROUP BY ImageID ORDER by favorCount DESC";
        }
        return getImageID(number, connection, sql);
    }

    public ArrayList<Integer> getRecentImagesID(int number, String condition) throws SQLException {
        Connection connection = JdbcUtil.getConnection();
        String sql;
        if (condition == null) {
            sql = "SELECT ImageID FROM travelimage ORDER by UploadedTime DESC";
        } else {
            sql = "SELECT ImageID FROM travelimage " +
                    condition + "ORDER by UploadedTime DESC";
        }
        return getImageID(number, connection, sql);
    }

    private ArrayList<Integer> getImageID(int number, Connection connection, String sql) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<Integer> ids = new ArrayList<Integer>();

        if (number != 0) {
            int time = 0;
            while (resultSet.next() && time < number) {
                ids.add(resultSet.getInt(1));
                time++;
            }
        } else {
            while (resultSet.next()) {
                ids.add(resultSet.getInt(1));
            }
        }

        JdbcUtil.close(resultSet, preparedStatement, connection);
        return ids;
    }

    public TravelImage getImageDetail(int imageID) throws SQLException {
        TravelImage travelImage = new TravelImage();

        String sql = "select Title, Description, Path, Content, traveluser.UserName, geocountries_regions.Country_RegionName, " +
                "geocities.AsciiName, Count(travelimagefavor.favorID) AS numFavored, UploadedTime FROM travelimage " +
                "INNER JOIN traveluser ON traveluser.UID = travelimage.UID " +
                "INNER JOIN geocountries_regions ON travelimage.Country_RegionCodeISO = geocountries_regions.ISO " +
                "INNER JOIN geocities ON travelimage.CityCode = geocities.GeoNameID " +
                "LEFT JOIN travelimagefavor ON travelimagefavor.ImageID = travelimage.ImageID " +
                "WHERE travelimage.ImageID = ?";
        Connection connection = JdbcUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, imageID);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        Timestamp timestamp = resultSet.getTimestamp(9);
        if(timestamp != null) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = df.format(timestamp);

            travelImage.setTitle(resultSet.getString(1));
            travelImage.setDescription(resultSet.getString(2));
            travelImage.setPath(resultSet.getString(3));
            travelImage.setContent(resultSet.getString(4));
            travelImage.setUploader(resultSet.getString(5));
            travelImage.setCountryName(resultSet.getString(6));
            travelImage.setCityName(resultSet.getString(7));
            travelImage.setFavored(resultSet.getInt(8));
            travelImage.setUploadedTime(time);
            travelImage.setImageID(imageID);
        }

        JdbcUtil.close(resultSet, preparedStatement, connection);
        return travelImage;
    }

    public ArrayList<Integer> getUserImages(String userName) throws SQLException {
        Connection connection = JdbcUtil.getConnection();
        String sql = "SELECT ImageID from travelimage INNER JOIN traveluser ON travelimage.UID = traveluser.UID WHERE traveluser.userName = ?";
        return getID(userName, connection, sql);
    }

    public ArrayList<Integer> getUserFavors(String userName) throws SQLException {
        Connection connection = JdbcUtil.getConnection();
        String sql = "SELECT ImageID from travelimagefavor INNER JOIN traveluser ON travelimagefavor.UID = traveluser.UID WHERE traveluser.userName = ?";
        return getID(userName, connection, sql);
    }

    private ArrayList<Integer> getID(String userName, Connection connection, String sql) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, userName);
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<Integer> ids = new ArrayList<Integer>();

        while (resultSet.next()) {
            ids.add(resultSet.getInt(1));
        }
        JdbcUtil.close(resultSet, preparedStatement, connection);
        return ids;
    }
}
