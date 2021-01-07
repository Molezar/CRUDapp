package app.dao;

import app.entities.Department;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class DepartmentDao extends AbstractDao {
    private static DepartmentDao instance = new DepartmentDao();

    static PreparedStatement preparedStatement = null;

    private static final String INSERT_NEW = "INSERT INTO departments (name)  VALUES(?)";
    private static final String GETALLDEPS = "SELECT * FROM departments";
    private static final String DELETE = "DELETE FROM departments WHERE id = ?";
    private static final String UPDATE = "UPDATE departments SET name = ? WHERE id = ?";


    private static List<Department> department;

    public static DepartmentDao getInstance() {
        return instance;
    }

    private DepartmentDao() {
        department = new ArrayList<>();
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

    }

    public Department findById(int id) {
        for (Department department1 : department) {

            if (department1.getDepID() == id) {
                return department1;
            }
        }
        return null;
    }


    public int getLastID() {
        if (!department.isEmpty()) {
            Department lastdep = department.get(department.size() - 1);
            int id = lastdep.getDepID();
            return id;
        }
        int id = 0;
        return id;
    }

    public List<Department> list() {
        return department;
    }


    public boolean add(Department dep) {

        int k = department.size();
        int i = 0;
        while (i != k) {
            Department sdep = department.get(i);
            String listName = sdep.getDepName();
            if (listName.equals(dep.getDepName())) {
                return false;
            }
            i++;
        }
        department.add(dep);


        try {
            preparedStatement = getConnection().prepareStatement(INSERT_NEW);
            preparedStatement.setString(1, dep.getDepName());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void remove(int id) {
        int k = department.size();
        int i = 0;
        while (i != k) {
            Department dep = department.get(i);
            int listId = dep.getDepID();
            if (listId == id) {
                department.remove(i);
                break;
            }
            i++;
        }

        try {
            preparedStatement = getConnection().prepareStatement(DELETE);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean edit(int id, String newName) {
        int k = department.size();
        int i = 0;
        while (i != k) {
            Department dep = department.get(i);
            int listId = dep.getDepID();
            if (listId == id) {
                String testname = dep.getDepName();
                if ((testname.equals(newName)) != false) {
                    return false;
                }
                department.get(i).setDepName(newName);
                break;
            }
            i++;
        }

        try {
            preparedStatement = getConnection().prepareStatement(UPDATE);
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}