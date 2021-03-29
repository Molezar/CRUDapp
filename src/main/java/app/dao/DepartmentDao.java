package app.dao;

import app.entities.Department;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class DepartmentDao extends AbstractDao {

    private static final String INSERT_NEW = "INSERT INTO departments (name)  VALUES(?)";
    private static final String GETALLDEPS = "SELECT * FROM departments";
    private static final String DELETE = "DELETE FROM departments WHERE id = ?";
    private static final String UPDATE = "UPDATE departments SET name = ? WHERE id = ?";
    private static final String GETBYID = "SELECT * FROM departments WHERE id = ?";


    public DepartmentDao() {}

    public Department findById(int id) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(GETBYID);
            preparedStatement.setInt(1, id);
            ResultSet res = preparedStatement.executeQuery();
            res.next();
            String name = res.getString("name");
            return new Department(id, name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void namechecker(Department dep) {
            DepartmentDao departmentDao = new DepartmentDao();
            List<Department> department = departmentDao.list();
            int k = 0;
            while (k < department.size()) {
                Department kdep = department.get(k);
                if (dep.equals(kdep)) {
                    throw new IllegalArgumentException("this name already exists");
                }
                k++;
            }
    }


    public List<Department> list() {
        try {
            ArrayList<Department> department = new ArrayList<>();
            PreparedStatement preparedStatement = getConnection().prepareStatement(GETALLDEPS);
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()) {
                int id = res.getInt("id");
                String name = res.getString("name");
                department.add(new Department(id, name));
            }
            return department;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean add(Department dep) {
        try {
            namechecker(dep);
            PreparedStatement preparedStatement = getConnection().prepareStatement(INSERT_NEW);
            preparedStatement.setString(1, dep.getDepName());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void remove(int id) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(DELETE);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean edit(int id, Department dep) {
        try {
            DepartmentDao departmentDao = new DepartmentDao();
            Department olddep = departmentDao.findById(id);
            if (dep.getDepName().equals(olddep.getDepName())) {
                throw new IllegalArgumentException("this is the same name");
            }
            namechecker(dep);

            PreparedStatement preparedStatement = getConnection().prepareStatement(UPDATE);
            preparedStatement.setString(1, dep.getDepName());
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
