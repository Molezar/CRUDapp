package app.dao;

import app.entities.Department;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class DepartmentDao extends AbstractDao {

    static PreparedStatement preparedStatement;

    private static final String INSERT_NEW = "INSERT INTO departments (name)  VALUES(?)";
    private static final String GETALLDEPS = "SELECT * FROM departments";
    private static final String DELETE = "DELETE FROM departments WHERE id = ?";
    private static final String UPDATE = "UPDATE departments SET name = ? WHERE id = ?";
    private static final String GETBYID = "SELECT * FROM departments WHERE id = ?";


    public DepartmentDao() {

    }

    public Department findById(int id) {
        try {
            preparedStatement = getConnection().prepareStatement(GETBYID);
            preparedStatement.setInt(1, id);
            ResultSet res = preparedStatement.executeQuery();
            res.next();
            String name = res.getString("name");
            return new Department(id, name);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public static List<Department> list() {
        ArrayList<Department> department = new ArrayList<>();
        try {
            preparedStatement = getConnection().prepareStatement(GETALLDEPS);
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()) {
                int id = res.getInt("id");
                String name = res.getString("name");
                department.add(new Department(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return department;
    }


    public boolean add(Department dep) {
        try {
            preparedStatement = getConnection().prepareStatement(INSERT_NEW);
            preparedStatement.setString(1, dep.getDepName());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void remove(int id) {
        try {
            preparedStatement = getConnection().prepareStatement(DELETE);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean edit(int id, String newName) {
        try {
            preparedStatement = getConnection().prepareStatement(UPDATE);
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
