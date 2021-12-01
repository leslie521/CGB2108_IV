package com.jt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
public class DataSourceTests {

    /**
     * Java中所有的连接池都会基于这个规范进行实现，
     * 假如你想获取一个与数据库的连接，一般要先获取
     * 一个数据源对象(DataSource),拿到数据源对象后
     * ，可以基于此对象从连接池(享元模式)获取一个连接。
     * 我们当前项目默认使用的连接池为HikariCP
     */
    @Autowired
    private DataSource dataSource;//HikariDataSource

    @Test
    public void testGetConnection() throws SQLException {
        Connection conn = dataSource.getConnection();
        System.out.println(conn);
    }
}
