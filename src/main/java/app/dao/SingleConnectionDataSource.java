package app.dao;


import java.sql.*;

class SingleConnectionDataSource extends AbstractConnectionDataSource {

    private static SingleConnectionDataSource singleConnectionDataSource;


    private SingleConnectionDataSource() {
        try {
            Class.forName(DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    static SingleConnectionDataSource getInstance() {
        if (singleConnectionDataSource == null) {
            singleConnectionDataSource = new SingleConnectionDataSource();
        }
        return singleConnectionDataSource;
    }

}
