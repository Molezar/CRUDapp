package app.services;

import app.entities.Department;
import app.extensions.StringExtension;
import app.dao.DepartmentDao;

import java.util.List;

public class DepService {
    private DepartmentDao departmentDao = new DepartmentDao();


    public List<Department> list() {
        return departmentDao.list();
    }

    public boolean add(String name) {
        if (StringExtension.isNullOrEmpty(name)) {
            throw new IllegalArgumentException("empty or null name");
        } else {
            app.entities.Department dep = new app.entities.Department(name);
            return departmentDao.add(dep);
        }
    }

    public void remove(int id) {
        departmentDao.remove(id);
    }

    public boolean edit(int id, String newName) {
        if (StringExtension.isNullOrEmpty(newName)) {
            throw new IllegalArgumentException("empty or null name");
        } else {
            departmentDao.list();
            boolean test;
            test = departmentDao.edit(id, newName);
            return test;
        }
    }


    public Department findById(int id) {
        return departmentDao.findById(id);
    }
}
