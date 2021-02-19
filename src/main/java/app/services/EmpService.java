package app.services;

import app.dao.EmployeeDao;
import app.entities.Employee;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class EmpService {

    public List<Employee> list(int depid) throws SQLException {
        EmployeeDao employeeDao = new EmployeeDao();
        return employeeDao.list(depid);
    }

    public void add(String name, String familyname, String email, Date date, int zp, int depid) {
        EmployeeDao employeeDao = new EmployeeDao();
        Employee emp = new Employee(name, familyname, email, date, zp, depid);
        employeeDao.add(emp);
    }

    public void remove(int id) throws SQLException {
        EmployeeDao employeeDao = new EmployeeDao();
        employeeDao.remove(id);
    }

    public void edit(int id, String name, String familyname, String email, Date date, int zp, int depid) {
        EmployeeDao employeeDao = new EmployeeDao();
        Employee emp = new Employee(name, familyname, email, date, zp, depid);
        employeeDao.edit(id, emp);
    }


    public Employee findById(int id) {
        EmployeeDao employeeDao = new EmployeeDao();
        return employeeDao.findById(id);
    }
}
