package app.services;

import app.entities.Department;
import app.extensions.StringExtension;
import app.dao.DepartmentDao;

import java.sql.SQLException;
import java.util.List;

public class DepService {


    public List<Department> list() {
        DepartmentDao departmentDao = new DepartmentDao();
        return  departmentDao.list();
    }

    public boolean add(String name) throws SQLException {
            DepartmentDao departmentDao = new DepartmentDao();
            Department dep = new Department(name);
            return departmentDao.add(dep);
    }

    public void remove(int id) {
        DepartmentDao departmentDao = new DepartmentDao();
        departmentDao.remove(id);
    }

    public boolean edit(int id, String newName) throws SQLException {
        if (StringExtension.isNullOrEmpty(newName)) {
            throw new IllegalArgumentException("empty or null name");
        } else {
            DepartmentDao departmentDao = new DepartmentDao();
            Department dep = new Department(newName);
            return departmentDao.edit(id, dep);
        }
    }


    public Department findById(int id) {
        DepartmentDao departmentDao = new DepartmentDao();
        return departmentDao.findById(id);
    }
}
