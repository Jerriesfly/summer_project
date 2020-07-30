package dao;

import Utils.JdbcUtil;
import beans.TravelUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TravelUserDao {
    public boolean checkUserName(String userName) throws SQLException {
        Connection connection = JdbcUtil.getConnection();

        String sql = "select count(*) from travels.traveluser where UserName = ?";
        return executeQuery(userName, connection, sql);
    }

    public boolean checkEmail(String Email) throws SQLException {
        Connection connection = JdbcUtil.getConnection();

        String sql = "select count(*) from travels.traveluser where Email = ?";
        return executeQuery(Email, connection, sql);
    }

    private boolean executeQuery(String item, Connection connection, String sql) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, item);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        boolean isAvailable = (resultSet.getInt(1) == 0);
        JdbcUtil.close(resultSet, preparedStatement, connection);
        return isAvailable;
    }

    public void createTravelUser(TravelUser travelUser) throws SQLException {
        Connection connection = JdbcUtil.getConnection();

        String sql = "insert into travels.traveluser (Email, UserName, Pass, State," +
                " DateJoined, DateLastModified) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        String Email = travelUser.getEmail();
        String userName = travelUser.getUserName();
        String pass = travelUser.getPass();
        String dateJoined = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        int state = 1;
        preparedStatement.setString(1, Email);
        preparedStatement.setString(2, userName);
        preparedStatement.setString(3, pass);
        preparedStatement.setInt(4, state);
        preparedStatement.setString(5, dateJoined);
        preparedStatement.setString(6, dateJoined);
        preparedStatement.execute();

        JdbcUtil.close(null, preparedStatement, connection);
    }

    public boolean checkUser(String userName, String password) throws SQLException {
        Connection connection = JdbcUtil.getConnection();

        String sql = "SELECT UserName FROM traveluser WHERE UserName = ? AND PASS = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, userName);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();

        boolean valid = resultSet.next();
        JdbcUtil.close(resultSet, preparedStatement, connection);
        return valid;
    }

    public int getUID(String userName) throws SQLException {
        Connection connection = JdbcUtil.getConnection();
        String sql = "select UID from travels.traveluser where UserName = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, userName);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int uid = resultSet.getInt(1);
        JdbcUtil.close(resultSet, preparedStatement, connection);
        return uid;
    }

    public String getUserName(int UID) throws SQLException {
        Connection connection = JdbcUtil.getConnection();
        String sql = "select UserName from travels.traveluser where UID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, UID);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        String userName = resultSet.getString(1);
        JdbcUtil.close(resultSet, preparedStatement, connection);
        return userName;
    }

    public boolean isFriend(String userName, int friendID) throws SQLException {
        int userID = getUID(userName);
        Connection connection = JdbcUtil.getConnection();
        String sql = "select Status from travelFriend where UID = ? AND FriendID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userID);
        preparedStatement.setInt(2, friendID);
        ResultSet resultSet = preparedStatement.executeQuery();

        boolean isFriend = resultSet.next() && resultSet.getInt(1) == 1;
        JdbcUtil.close(resultSet, preparedStatement, connection);
        return isFriend;
    }

    public boolean isPublic(int friendID) throws SQLException {
        Connection connection = JdbcUtil.getConnection();
        String sql = "select isPublic from travels.traveluser where UID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, friendID);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        boolean isPublic = resultSet.getInt(1) == 1;
        JdbcUtil.close(resultSet, preparedStatement, connection);
        return isPublic;
    }

    public ArrayList<Integer> getFriends(int UID) throws SQLException {
        ArrayList<Integer> myFriends = new ArrayList<>();

        Connection connection = JdbcUtil.getConnection();
        String sql = "select FriendID from travelFriend where UID = ? AND status = 1";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, UID);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            myFriends.add(resultSet.getInt(1));
        }

        JdbcUtil.close(resultSet, preparedStatement, connection);
        return myFriends;
    }

    public ArrayList<Integer> getFootprints(String userName) throws SQLException {
        ArrayList<Integer> myFootprints = new ArrayList<>();

        int UID = getUID(userName);
        Connection connection = JdbcUtil.getConnection();
        String sql = "select ImageID from travelFootprints where UID = ? order by dateTime desc";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, UID);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            myFootprints.add(resultSet.getInt(1));
        }

        JdbcUtil.close(resultSet, preparedStatement, connection);
        return myFootprints;
    }

    public void setFootprints(String userName, int imageID) throws SQLException {
        int UID = getUID(userName);
        Connection connection = JdbcUtil.getConnection();

        String sql = "DELETE FROM travelfootprints WHERE UID = ? AND ImageID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, UID);
        preparedStatement.setInt(2, imageID);
        preparedStatement.execute();
        preparedStatement.close();

        String sql2 = "SELECT FootprintID FROM travelfootprints WHERE UID = ?";
        PreparedStatement preparedStatement2 = connection.prepareStatement(sql2, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        preparedStatement2.setInt(1, UID);
        ResultSet resultSet = preparedStatement2.executeQuery();
        resultSet.last();
        if (resultSet.getRow() == 10) {
            resultSet.beforeFirst();
            resultSet.next();
            int footprintID = resultSet.getInt(1);
            String sql3 = "DELETE FROM travelfootprints WHERE FootprintID = ?";
            PreparedStatement preparedStatement3 = connection.prepareStatement(sql3);
            preparedStatement3.setInt(1, footprintID);
            preparedStatement3.execute();
            preparedStatement3.close();
        }

        String sql4 = "insert into TravelFootprints (UID, ImageID, dateTime) VALUES (?, ? ,NOW())";
        PreparedStatement preparedStatement4 = connection.prepareStatement(sql4);
        preparedStatement4.setInt(1, UID);
        preparedStatement4.setInt(2, imageID);
        preparedStatement4.execute();
        JdbcUtil.close(resultSet, preparedStatement4, connection);
    }

    public void setStatus(String userName) throws SQLException {
        Connection connection = JdbcUtil.getConnection();
        String sql = "SELECT IsPublic FROM traveluser WHERE UserName = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, userName);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int beforeStatus = resultSet.getInt(1);
        preparedStatement.close();

        int afterStatus = 1 - beforeStatus;
        String sql2 = "UPDATE traveluser SET IsPublic = ? WHERE UserName = ?";
        PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
        preparedStatement2.setInt(1, afterStatus);
        preparedStatement2.setString(2, userName);
        preparedStatement2.execute();

        JdbcUtil.close(resultSet, preparedStatement2, connection);
    }
}
