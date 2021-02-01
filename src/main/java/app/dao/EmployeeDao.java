package app.dao;

import app.entities.Department;
import app.entities.Employee;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class EmployeeDao extends AbstractDao {

    static PreparedStatement preparedStatement;

    private static final String INSERT_NEW = "INSERT INTO employees (name, familyname, email, dateofbirth, zp, depid)  VALUES(?, ?, ?, ?, ?, ?)";
    private static final String GETEMPS = "SELECT * FROM employees WHERE depid = ?";
    private static final String GETALLEMPS = "SELECT * FROM employees";
    private static final String DELETE = "DELETE FROM employees WHERE id = ?";
    private static final String UPDATE = "UPDATE employees SET name =?, familyname=?, email=?, dateofbirth=?, zp=?, depid=? WHERE id = ?";
    private static final String GETBYID = "SELECT * FROM employees WHERE id = ?";


    public EmployeeDao() {

    }

    public Employee findById(int id) {
        try {
            preparedStatement = getConnection().prepareStatement(GETBYID);
            preparedStatement.setInt(1, id);
            ResultSet res = preparedStatement.executeQuery();
            res.next();
            int eid = res.getInt("id");
            String name = res.getString("name");
            String familyName = res.getString("familyname");
            String email = res.getString("email");
            Date date = res.getDate("dateofbirth");
            int zp = res.getInt("zp");
            int depId = res.getInt("depid");
            return new Employee(eid, name, familyName, email, date, zp, depId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void empchecker(Employee emp) {
        EmployeeDao employeeDao = new EmployeeDao();
        List<Employee> employee = employeeDao.list();
        int k=0;
        while (k < employee.size()) {
            Employee kemp = employee.get(k);
            if (emp.equals(kemp)) {
                throw new IllegalArgumentException("this employee already exists");
            }
            k++;
        }
    }

    public  List<Employee> list() {
        ArrayList<Employee> employee = new ArrayList<>();
        try {
            preparedStatement = getConnection().prepareStatement(GETALLEMPS);
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()) {
                int id = res.getInt("id");
                String name = res.getString("name");
                String familyName = res.getString("familyname");
                String email = res.getString("email");
                Date date = res.getDate("dateofbirth");
                int zp = res.getInt("zp");
                int depId = res.getInt("depid");

                employee.add(new Employee(id, name, familyName, email, date, zp, depId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }
    public  List<Employee> list(int depid) {
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
                Date date = res.getDate("dateofbirth");
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
        empchecker(emp);
        try {
            preparedStatement = getConnection().prepareStatement(INSERT_NEW);
            preparedStatement.setString(1, emp.getName());
            preparedStatement.setString(2, emp.getFamilyName());
            preparedStatement.setString(3, emp.getEmail());
            java.sql.Date sqlDate = new java.sql.Date(emp.getDate().getTime());
            preparedStatement.setDate(4, sqlDate);
            preparedStatement.setInt(5, emp.getZP());
            preparedStatement.setInt(6, emp.getDepID());
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

    public boolean edit(int id, Employee emp) {
        try {
            preparedStatement = getConnection().prepareStatement(UPDATE);
            preparedStatement.setString(1, emp.getName());
            preparedStatement.setString(2, emp.getFamilyName());
            preparedStatement.setString(3, emp.getEmail());
            java.sql.Date sqlDate = new java.sql.Date(emp.getDate().getTime());
            preparedStatement.setDate(4, sqlDate);
            preparedStatement.setInt(5, emp.getZP());
            preparedStatement.setInt(6, emp.getDepID());
            preparedStatement.setInt(7, id);

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
