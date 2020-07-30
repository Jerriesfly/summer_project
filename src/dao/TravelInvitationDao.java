package dao;

import Utils.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TravelInvitationDao {
    public ArrayList<Integer> getFriendInvitations(int UID) throws SQLException {
        ArrayList<Integer> friendInvitations = new ArrayList<>();
        Connection connection = JdbcUtil.getConnection();
        String sql = "SELECT UID FROM travelFriend WHERE FriendID = ? AND Status = 0";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, UID);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            friendInvitations.add(resultSet.getInt(1));
        }

        JdbcUtil.close(resultSet, preparedStatement, connection);
        return friendInvitations;
    }

    public void deleteFriendInvitation(int UID, int friendID) throws SQLException {
        Connection connection = JdbcUtil.getConnection();
        String sql = "DELETE FROM travelFriend WHERE UID = ? AND FriendID = ? AND Status = 0";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, UID);
        preparedStatement.setInt(2, friendID);
        preparedStatement.execute();

        JdbcUtil.close(null, preparedStatement, connection);
    }

    public void addFriend(int UID, int friendID) throws SQLException {
        Connection connection = JdbcUtil.getConnection();
        String sql = "INSERT INTO travelFriend (UID, FriendID, Status) VALUES (?, ?, 1)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, UID);
        preparedStatement.setInt(2, friendID);
        preparedStatement.execute();

        JdbcUtil.close(null, preparedStatement, null);

        String sql2 = "INSERT INTO travelFriend (UID, FriendID, Status) VALUES (?, ?, 1)";
        PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
        preparedStatement2.setInt(1, friendID);
        preparedStatement2.setInt(2, UID);
        preparedStatement2.execute();

        JdbcUtil.close(null, preparedStatement2, connection);
    }

    public void makeInvitation(int UID, int friendID) throws SQLException {
        Connection connection = JdbcUtil.getConnection();
        String sql = "INSERT INTO travelFriend (UID, FriendID, Status) VALUES (?, ?, 0)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, UID);
        preparedStatement.setInt(2, friendID);
        preparedStatement.execute();

        JdbcUtil.close(null, preparedStatement, connection);
    }
}
