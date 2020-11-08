package app.dbContext;

import app.entities.Department;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DepContext {
    private static DepContext instance = new DepContext();

    PreparedStatement preparedStatement = null;
    Connection connection = null;

    private static final String INSERT_NEW = "INSERT INTO departments (name)  VALUES(?)";


    private List<Department> department;

    public static DepContext getInstance() {
        return instance;
    }

    private DepContext() {
        department = new ArrayList<>();
    }

    public void add(Department dep) {
        department.add(dep);

        DBWorker worker = new DBWorker();

        try {
            preparedStatement = worker.getConnection().prepareStatement(INSERT_NEW);

//            preparedStatement.setInt(1,1);
            preparedStatement.setString(1,dep.getDepName());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        finally {
//            try {
//                worker.getConnection().close();
//            } catch (SQLException e) {
//                System.out.println("unnable to close");
//            }
//        }

    }

    public List<String> list() {
        return department.stream()
                .map(app.entities.Department::getDepName)
                .collect(Collectors.toList());
    }
}