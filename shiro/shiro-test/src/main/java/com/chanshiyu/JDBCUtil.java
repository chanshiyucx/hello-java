package com.chanshiyu;

import com.alibaba.druid.pool.DruidDataSource;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * JDBC工具类
 * 1) 获取 Connnection
 * 2) 释放资源
 */
public class JDBCUtil {

    private static DruidDataSource druidDataSource = null;

    static {
        try {
            InputStream inputStream =JDBCUtil.class.getClassLoader().getResourceAsStream("db.properties");
            Properties properties = new Properties();
            properties.load(inputStream);

            String url = properties.getProperty("jdbc.url");
            String user = properties.getProperty("jdbc.user");
            String password = properties.getProperty("jdbc.password");
            String dirverClass = properties.getProperty("jdbc.dirverClass");

            druidDataSource = new DruidDataSource();
            druidDataSource.setDriverClassName(dirverClass);
            druidDataSource.setUrl(url);
            druidDataSource.setUsername(user);
            druidDataSource.setPassword(password);
            druidDataSource.setInitialSize(5);
            druidDataSource.setMinIdle(1);
            druidDataSource.setMaxActive(10);
            druidDataSource.setPoolPreparedStatements(false);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取 Connection
     */
    public static synchronized Connection getConnection(){
        Connection conn = null;
        try {
            conn = druidDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 释放 DB 相关的资源
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
