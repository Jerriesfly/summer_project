package dao;

import Utils.JdbcUtil;
import servlets.RegisterServlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TravelImageFavorDao {
    public boolean isFavored(int imageId, String userName) throws SQLException {
        TravelUserDao travelUserDao = new TravelUserDao();
        int userId = travelUserDao.getUID(userName);

        Connection connection = JdbcUtil.getConnection();
        String sql = "SELECT ImageID, UID FROM travelimagefavor WHERE UID = ? AND ImageID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, imageId);
        ResultSet resultSet = preparedStatement.executeQuery();
        boolean isFavored = resultSet.next();
        JdbcUtil.close(resultSet, preparedStatement, connection);

        return isFavored;
    }

    public void addFavor(int imageId, String userName) throws SQLException {
        TravelUserDao travelUserDao = new TravelUserDao();
        int userId = travelUserDao.getUID(userName);

        Connection connection = JdbcUtil.getConnection();
        String sql = "INSERT INTO travelimagefavor (UID, ImageID) VALUES(?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, imageId);
        preparedStatement.execute();
    }

    public void removeFavor(int imageId, String userName) throws SQLException {
        TravelUserDao travelUserDao = new TravelUserDao();
        int userId = travelUserDao.getUID(userName);

        Connection connection = JdbcUtil.getConnection();
        String sql = "DELETE FROM travelimagefavor WHERE UID = ? AND ImageID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, imageId);
        preparedStatement.execute();
    }
}
