package app.dao;

import java.sql.Connection;
import java.sql.SQLException;

abstract class AbstractDao {
    static final Connection getConnection() throws SQLException {
        return SingleConnectionDataSource.getInstance().getConnection();
//        return PooledConnectionDataSource.getInstance().getConnection();

//        if (true) {
//            return PooledConnectionDataSource.getInstance().getConnection();
//        } else {
//            return SingleConnectionDataSource.getInstance().getConnection();
//        }
    }
}