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


    public DepartmentDao() {
    }

    public Department findById(int id) throws SQLException {

        PreparedStatement preparedStatement = getConnection().prepareStatement(GETBYID);
        preparedStatement.setInt(1, id);
        ResultSet res = preparedStatement.executeQuery();
        res.next();
        String name = res.getString("name");
        return new Department(id, name);
    }

    private void namechecker(Department dep) throws SQLException {
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


    public List<Department> list() throws SQLException {
        ArrayList<Department> department = new ArrayList<>();
        PreparedStatement preparedStatement = getConnection().prepareStatement(GETALLDEPS);
        ResultSet res = preparedStatement.executeQuery();
        while (res.next()) {
            int id = res.getInt("id");
            String name = res.getString("name");
            department.add(new Department(id, name));
        }
        return department;
    }


    public boolean add(Department dep) throws SQLException {
        namechecker(dep);
        PreparedStatement preparedStatement = getConnection().prepareStatement(INSERT_NEW);
        preparedStatement.setString(1, dep.getDepName());
        preparedStatement.execute();
        return true;
    }

    public void remove(int id) throws SQLException {
        PreparedStatement preparedStatement = getConnection().prepareStatement(DELETE);
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    public boolean edit(int id, Department dep) throws SQLException {
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
    }
}
