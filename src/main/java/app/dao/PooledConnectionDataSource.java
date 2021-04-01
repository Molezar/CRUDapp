package app.dao;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

class PooledConnectionDataSource extends AbstractConnectionDataSource{

    private static PooledConnectionDataSource pooledConnectionDataSource;
    private final BasicDataSource ds;

    private PooledConnectionDataSource() {
        try {
            ds = new BasicDataSource();
            ds.setDriverClassName(DRIVER_CLASS_NAME);
            ds.setUrl(URL);
            ds.setUsername(USERNAME);
            ds.setPassword(PASSWORD);
            ds.setMinIdle(5);
            ds.setMaxIdle(10);
            ds.setMaxOpenPreparedStatements(100);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    static PooledConnectionDataSource getInstance() {
        if (pooledConnectionDataSource == null) {
            pooledConnectionDataSource = new PooledConnectionDataSource();
        }
        return pooledConnectionDataSource;
    }
}