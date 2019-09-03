package com.chanshiyu.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * JDBC工具类
 * 1) 获取Connnection
 * 2) 释放资源
 */
public class JDBCUtil {

    /**
     * 获取Connection
     * @return 所获得的JDBC的Connection
     */
    public static Connection getConnection() throws Exception {

        InputStream inputStream =JDBCUtil.class.getClassLoader().getResourceAsStream("db.properties");
        Properties properties = new Properties();
        properties.load(inputStream);

        String url = properties.getProperty("jdbc.url");
        String user = properties.getProperty("jdbc.user");
        String password = properties.getProperty("jdbc.password");
        String dirverClass = properties.getProperty("jdbc.dirverClass");

        Class.forName(dirverClass);
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

    /**
     * 释放DB相关的资源
     * @param resultSet
     * @param statement
     * @param connection
     */
    public static void release(ResultSet resultSet, Statement statement, Connection connection) {

        if (resultSet != null) {
            try {
                resultSet.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (statement != null) {
            try {
                statement.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (connection != null) {
            try {
                connection.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
