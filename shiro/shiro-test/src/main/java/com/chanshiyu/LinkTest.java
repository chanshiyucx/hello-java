package com.chanshiyu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LinkTest {

    public static void testLink() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql ="select id, username, password from users where username = ?";

        try {
            connection = JDBCUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "shiyu");
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int id = resultSet.getInt("id");
            String username = resultSet.getString("username");
            System.out.println("id: " + id + " username:" + username);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
