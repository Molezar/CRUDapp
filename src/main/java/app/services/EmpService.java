package app.services;

import app.dao.EmployeeDao;
import app.entities.Employee;

import java.util.Date;
import java.util.List;

public class EmpService {

    public List<Employee> list(int depid) {
        EmployeeDao employeeDao = new EmployeeDao();
        return employeeDao.list(depid);
    }

    public boolean add(String name, String familyname, String email, Date date, int zp, int depid) {
        EmployeeDao employeeDao = new EmployeeDao();
        Employee emp = new Employee(name, familyname, email, date, zp, depid);
        return employeeDao.add(emp);
    }

    public void remove(int id)  {
        EmployeeDao employeeDao = new EmployeeDao();
        employeeDao.remove(id);
    }

    public boolean edit(int id, String name, String familyname, String email, Date date, int zp, int depid) {
        EmployeeDao employeeDao = new EmployeeDao();
        Employee emp = new Employee(id, name, familyname, email, date, zp, depid);
        return  employeeDao.edit(id, emp);
    }


    public Employee findById(int id) {
        EmployeeDao employeeDao = new EmployeeDao();
        return employeeDao.findById(id);
    }
}
