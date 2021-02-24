package app.dao;

import app.entities.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeeDao extends AbstractDao {

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
            PreparedStatement preparedStatement = getConnection().prepareStatement(GETBYID);
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
            throw new RuntimeException(e);
        }
    }

    private void empchecker(Employee emp) throws SQLException {
        EmployeeDao employeeDao = new EmployeeDao();
        List<Employee> employee = employeeDao.list();
        int k = 0;
        while (k < employee.size()) {
            Employee kemp = employee.get(k);
            if (emp.equals(kemp)) {
                throw new IllegalArgumentException("email");
            }
            k++;
        }
    }

    public List<Employee> list() throws SQLException {
        ArrayList<Employee> employee = new ArrayList<>();
        PreparedStatement preparedStatement = getConnection().prepareStatement(GETALLEMPS);
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
        return employee;
    }

    public List<Employee> list(int depid) throws SQLException {
        ArrayList<Employee> employee = new ArrayList<>();
        PreparedStatement preparedStatement = getConnection().prepareStatement(GETEMPS);
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
        return employee;
    }


    public void add(Employee emp) {
        try {
            empchecker(emp);
            PreparedStatement preparedStatement = getConnection().prepareStatement(INSERT_NEW);
            preparedStatement.setString(1, emp.getName());
            preparedStatement.setString(2, emp.getFamilyName());
            preparedStatement.setString(3, emp.getEmail());
            java.sql.Date sqlDate = new java.sql.Date(emp.getDate().getTime());
            preparedStatement.setDate(4, sqlDate);
            preparedStatement.setInt(5, emp.getZP());
            preparedStatement.setInt(6, emp.getDepID());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void remove(int id) throws SQLException {
        PreparedStatement preparedStatement = getConnection().prepareStatement(DELETE);
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    public void edit(int id, Employee emp) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(UPDATE);
            preparedStatement.setString(1, emp.getName());
            preparedStatement.setString(2, emp.getFamilyName());
            preparedStatement.setString(3, emp.getEmail());
            java.sql.Date sqlDate = new java.sql.Date(emp.getDate().getTime());
            preparedStatement.setDate(4, sqlDate);
            preparedStatement.setInt(5, emp.getZP());
            preparedStatement.setInt(6, emp.getDepID());
            preparedStatement.setInt(7, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
