package app.dao;

import app.entities.Employee;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;


public class EmployeeDao extends AbstractDao {

    static PreparedStatement preparedStatement;

    private static final String INSERT_NEW = "INSERT INTO employees (name)  VALUES(?)";
    private static final String GETEMPS = "SELECT * FROM employees INNER JOIN departments ON employees.depid=departments.id WHERE id = ?";
//    private static final String GETALLDEMPS = "SELECT * FROM employees";
    private static final String DELETE = "DELETE FROM employees WHERE id = ?";
    private static final String UPDATE = "UPDATE employees SET name = ? WHERE id = ?";
    private static final String GETBYID = "SELECT * FROM employees WHERE id = ?";


    public EmployeeDao() {

    }

    public Employee findById(int id) {
        try {
            preparedStatement = getConnection().prepareStatement(GETBYID);
            preparedStatement.setInt(1, id);
            ResultSet res = preparedStatement.executeQuery();
            res.next();
            String name = res.getString("name");
            String familyName = res.getString("familyname");
            String email = res.getString("email");
            Date date = res.getDate("date");
            int zp = res.getInt("zp");
            int depId = res.getInt("depid");

            return new Employee(name, familyName, email, date, zp, depId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public static List<Employee> list(int depid) {
        ArrayList<Employee> employee = new ArrayList<>();
        try {
            preparedStatement = getConnection().prepareStatement(GETEMPS);
            preparedStatement.setInt(1, depid);
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()) {
                int id = res.getInt("id");
                String name = res.getString("name");
                String familyName = res.getString("familyname");
                String email = res.getString("email");
                Date date = res.getDate("date");
                int zp = res.getInt("zp");
                int depId = res.getInt("depid");

                employee.add(new Employee(id, name, familyName, email, date, zp, depId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }


    public boolean add(Employee emp) {
        try {
            preparedStatement = getConnection().prepareStatement(INSERT_NEW);
            preparedStatement.setString(1, emp.getName());
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
