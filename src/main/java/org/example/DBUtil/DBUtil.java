package org.example.DBUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DBUtil {
    private static Connection connection = null;

    @Autowired
    public DBUtil(Connection connection) {
        this.connection = connection;
    }

    public static Connection getConnection() {
        return connection;
    }
}

