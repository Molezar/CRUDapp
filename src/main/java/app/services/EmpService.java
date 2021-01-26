package app.services;

import app.dao.DepartmentDao;
import app.entities.Department;
import app.entities.Employee;
import app.extensions.StringExtension;
import app.dao.EmployeeDao;

import java.util.List;

public class EmpService {
    private EmployeeDao employeeDao = new EmployeeDao();


    public List<Employee> list(int depid) {
        return employeeDao.list(depid);
    }

    public boolean add(String name) {
        if (StringExtension.isNullOrEmpty(name)) {
            throw new IllegalArgumentException("empty or null name");
        } else {
            app.entities.Employee emp = new app.entities.Employee(name);
            return employeeDao.add(emp);
        }
    }

    public void remove(int id) {
        employeeDao.remove(id);
    }

    public boolean edit(int id, String newName, int depid) {
        if (StringExtension.isNullOrEmpty(newName)) {
            throw new IllegalArgumentException("empty or null name");
        } else {
            employeeDao.list(depid);
            boolean test;
            test = employeeDao.edit(id, newName);
            return test;
        }
    }


    public Employee findById(int id) {
        return employeeDao.findById(id);
    }
}
