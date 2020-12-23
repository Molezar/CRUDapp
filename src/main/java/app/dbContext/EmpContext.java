package app.dbContext;

import app.entities.Employee;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
//import java.sql.Date;


public class EmpContext {
    private static EmpContext instance = new EmpContext();

    static PreparedStatement preparedStatement = null;
    Connection connection = null;

    private static final String INSERT_NEW = "INSERT INTO employees (name, familyname, email, dateofbirth, zp  )  VALUES(?, ?, ?, ?, ?, ?)";
    private static final String GETALLEMPS = "SELECT * FROM employees";
    private static final String DELETE = "DELETE FROM employees WHERE id = ?";
    private static final String UPDATE = "UPDATE employees SET name = ?, familyname = ?, email = ?, dateofbirth = ?, zp = ?  WHERE id = ?";


    private static List<Employee> employee;

    public static EmpContext getInstance() {
        return instance;
    }

    private EmpContext() {
        employee = new ArrayList<>();
        DBWorker worker = new DBWorker();

        try {
            preparedStatement = worker.getConnection().prepareStatement(GETALLEMPS);
            ResultSet res = preparedStatement.executeQuery();
            while(res.next()) {
                int id = res.getInt("id");
                String name = res.getString("name");
                String familyname = res.getString("familyname");
                String email = res.getString("email");
                Date dateofbirth = res.getDate("dateofbirth");
                int zp = res.getInt("zp");
                int depid = res.getInt("depid");

                employee.add(new Employee(id, name, familyname, email, dateofbirth, zp, depid ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String getNameById(int id) {
        Employee emp = employee.get(id);
        return emp.getName();
    }


    public int getLastID() {
        if (!employee.isEmpty()) {
            Employee lastemp = employee.get(employee.size() - 1);
            int id = lastemp.getEmpID();
            return id;
        }
        int id = 0;
        return id;
    }

    public List<Employee> list() {
        // todo
        return employee;
    }

    public boolean add(Employee emp) {

        int k = employee.size();
        int i = 0;
        while (i!=k) {
            Employee semp = employee.get(i);
            if (semp.equals(emp)) {
                return false;
            }
//            String listEmail = semp.getEmail();
//            if (listEmail.equals(emp.getEmail())) {
//                return false;
//            }
            i++;
        }
        employee.add(emp);

        DBWorker worker = new DBWorker();

        try {
            preparedStatement = worker.getConnection().prepareStatement(INSERT_NEW);
            preparedStatement.setString(1,emp.getName());
            preparedStatement.setString(2, emp.getFamilyName());
            preparedStatement.setString(3, emp.getEmail());
            preparedStatement.setDate(4, (java.sql.Date) emp.getDate());
            preparedStatement.setInt(5, emp.getZP());
            preparedStatement.setInt(6, emp.getDepID());

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void remove(int id) {
        int k = employee.size();
        int i = 0;
        while (i!=k) {
            Employee emp = employee.get(i);
            int listId = emp.getEmpID();
            if (listId == id) {
                employee.remove(i);
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

    public boolean edit(int id, String newName, String NewFamilyName, String newEmail, Date newDate, int newZP,  int newDepID) {
        int k = employee.size();
        int i = 0;
        while (i!=k) {
            Employee emp = employee.get(i);
            int listId = emp.getEmpID();
            if (listId == id) {
//                String testmale = emp.getEmail();
//                boolean test = (testmale.equals(newEmail));
//                if (test!=false) {
//                    return false;
//                }
                if (newEmail == emp.getEmail()) {
                    return false;
                }
                emp.setName(newName);
                emp.setFamilyName(NewFamilyName);
                emp.setEmail(newEmail);
                emp.setDate(newDate);
                emp.setZP(newZP);
                emp.setDepID(newDepID);
                break;
            }
            i++;
        }

        DBWorker worker = new DBWorker();
        try {
            preparedStatement = worker.getConnection().prepareStatement(UPDATE);
            preparedStatement.setString(1,newName);
            preparedStatement.setString(2,NewFamilyName);
            preparedStatement.setString(3,newEmail);
            preparedStatement.setDate(4, (java.sql.Date) newDate);
            preparedStatement.setInt(5, newZP);
            preparedStatement.setInt(6, newDepID);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


}