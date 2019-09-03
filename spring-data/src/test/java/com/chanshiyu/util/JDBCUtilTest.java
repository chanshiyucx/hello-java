package com.chanshiyu.util;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;

public class JDBCUtilTest {

    @Test
    public void getConnectionTest() throws Exception {
        Connection connection = JDBCUtil.getConnection();
        Assert.assertNotNull(connection);
    }

}