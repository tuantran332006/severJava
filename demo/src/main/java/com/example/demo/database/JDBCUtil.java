package com.example.demo.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class JDBCUtil {

    @Autowired
    private DataSource dataSource;

    public Connection getConnection() throws Exception {
        return dataSource.getConnection();
    }
}