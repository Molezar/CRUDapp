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
    private static final String DELETE = "DELETE FROM departments WHERE id = ?";
    private static final String UPDATE = "UPDATE departments SET name = ? WHERE id = ?";


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

    public boolean add(Department dep) {

        int k = department.size();
        int i = 0;
        while (i!=k) {
            Department sdep = department.get(i);
            String listName = sdep.getDepName();
            if (listName.equals(dep.getDepName())) {
                return false;
            }
            i++;
        }
            department.add(dep);

        DBWorker worker = new DBWorker();

        try {
            preparedStatement = worker.getConnection().prepareStatement(INSERT_NEW);
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
        return true;
       }

        public void remove(int id) {
            int k = department.size();
            int i = 0;
            while (i!=k) {
                Department dep = department.get(i);
                int listId = dep.getDepID();
                if (listId == id) {
                    department.remove(i);
                    break;
                }
            i++;
            }


            DBWorker worker = new DBWorker();

            try {
            preparedStatement = worker.getConnection().prepareStatement(DELETE);
            preparedStatement.setInt(1,id);
            preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public boolean edit(int id, String newName) {
            int k = department.size();
            int i = 0;
            while (i!=k) {
                Department dep = department.get(i);
                int listId = dep.getDepID();
                if (listId == id) {
                    String testname = dep.getDepName();
                    boolean test = (testname.equals(newName));
                            if (test!=false) {
                                return false;
                            }
                    dep.setDepName(newName);
                    break;
                }
                i++;
            }

            DBWorker worker = new DBWorker();
            try {
                preparedStatement = worker.getConnection().prepareStatement(UPDATE);
                preparedStatement.setString(1,newName);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return true;
        }


    }