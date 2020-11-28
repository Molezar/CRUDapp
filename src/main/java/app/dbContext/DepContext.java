package app.dbContext;

import app.entities.Department;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DepContext {
    private static DepContext instance = new DepContext();

    static PreparedStatement preparedStatement = null;
    Connection connection = null;

    private static final String INSERT_NEW = "INSERT INTO departments (name)  VALUES(?)";
    private static final String GETALLDEPS = "SELECT * FROM departments";
    private static final String DELETE = "DELETE FROM departments WHERE id=?";


    private static List<Department> department;

    public static DepContext getInstance() {
        return instance;
    }

    private DepContext() {
        department = new ArrayList<>();
        DBWorker worker = new DBWorker();

        try {
            preparedStatement = worker.getConnection().prepareStatement(GETALLDEPS);
            ResultSet res = preparedStatement.executeQuery();
            while(res.next()) {
                int id = res.getInt("id");
                String name = res.getString("name");
                department.add(new Department(id, name));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String getNameById(int id) {
        Department dep = department.get(id);
        return dep.getDepName();
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


//    public List<String> list() {
//        return department.stream()
//                .map(app.entities.Department::getDepName)
//                .collect(Collectors.toList());
//    }

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
       }

        public void remove(int id) {

            DBWorker worker = new DBWorker();

            try {
            preparedStatement = worker.getConnection().prepareStatement(DELETE);
            preparedStatement.setInt(1,id);
            preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

}