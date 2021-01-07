package app.dao;


import java.sql.Connection;
import java.sql.SQLException;

abstract class AbstractConnectionDataSource {
    final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    final String URL = "jdbc:mysql://localhost:3306/depbd";
    final String USERNAME = "root";
    final String PASSWORD = "root";

    abstract Connection getConnection() throws SQLException;
}