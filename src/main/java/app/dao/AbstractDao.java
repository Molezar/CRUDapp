package app.dao;

import java.sql.Connection;
import java.sql.SQLException;

abstract class AbstractDao {
    final Connection getConnection() throws SQLException {
        if (true) {
            return PooledConnectionDataSource.getInstance().getConnection();
        } else {
            return SingleConnectionDataSource.getInstance().getConnection();
        }
    }
}