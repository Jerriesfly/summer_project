package dao;

import Utils.JdbcUtil;
import beans.Comment;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TravelCommentDao {
    public ArrayList<Integer> getImageComments(int imageID) throws SQLException {
        ArrayList<Integer> imageComments = new ArrayList<>();

        Connection connection = JdbcUtil.getConnection();
        String sql = "SELECT commentID FROM travelcomments WHERE ImageID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, imageID);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            imageComments.add(resultSet.getInt(1));
        }

        JdbcUtil.close(resultSet, preparedStatement, connection);
        return imageComments;
    }

    public Comment getComment(int commentID) throws SQLException {
        Comment comment = new Comment();

        Connection connection = JdbcUtil.getConnection();
        String sql = "Select UID, comment, dateTime FROM travelcomments WHERE commentID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, commentID);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        String userName = new TravelUserDao().getUserName(resultSet.getInt(1));
        Timestamp timestamp = resultSet.getTimestamp(3);
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(timestamp);

        comment.setUID(resultSet.getInt(1));
        comment.setComment(resultSet.getString(2));
        comment.setTime(time);
        comment.setUserName(userName);

        JdbcUtil.close(resultSet, preparedStatement, connection);
        return comment;
    }

    public void sendComment(String comment, int UID, int imageID) throws SQLException {
        Connection connection = JdbcUtil.getConnection();
        String sql = "insert into travelcomments (UID, comment, dateTime, ImageID) VALUES (?, ? , NOW(), ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, UID);
        preparedStatement.setString(2, comment);
        preparedStatement.setInt(3, imageID);
        preparedStatement.execute();
    }
}
