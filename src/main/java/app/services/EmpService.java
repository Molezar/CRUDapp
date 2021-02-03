package app.services;

import app.entities.Employee;
import app.dao.EmployeeDao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class EmpService {

    public List<Employee> list(int depid) throws SQLException {
        EmployeeDao employeeDao = new EmployeeDao();
        return employeeDao.list(depid);
    }

    public boolean add(String name, String familyname, String email, Date date, int zp, int depid) throws SQLException {
            EmployeeDao employeeDao = new EmployeeDao();
            Employee emp = new Employee(name, familyname, email, date, zp, depid);
            return employeeDao.add(emp);
    }

    public void remove(int id) throws SQLException {
        EmployeeDao employeeDao = new EmployeeDao();
        employeeDao.remove(id);
    }

    public boolean edit(int id, String name, String familyname, String email, Date date, int zp, int depid) throws SQLException {
            EmployeeDao employeeDao = new EmployeeDao();
            Employee emp = new Employee(name, familyname, email, date, zp, depid);
            return employeeDao.edit(id, emp);
    }


    public Employee findById(int id) throws SQLException {
        EmployeeDao employeeDao = new EmployeeDao();
        return employeeDao.findById(id);
    }
}
