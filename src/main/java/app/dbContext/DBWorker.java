package app.dbContext;

//import com.mysql.fabric.jdbc.FabricMySQLDriver;
import java.sql.*;

public class DBWorker {
    private static final String URL = "jdbc:mysql://localhost:3306/depbd";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";


    private Connection connection;

    public DBWorker() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (SQLException e) {
            System.err.println("error");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

}
