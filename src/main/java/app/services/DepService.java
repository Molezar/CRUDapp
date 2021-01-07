package app.services;

import app.entities.Department;
import app.extensions.StringExtension;
import app.dao.DepartmentDao;

public class DepService {
    private DepartmentDao departmentDao = DepartmentDao.getInstance();


    public boolean add(String name) {
        if (StringExtension.isNullOrEmpty(name)) {
            throw new IllegalArgumentException("empty or null name");
        } else {
            int lastid = departmentDao.getLastID();
            int id = lastid + 1;
            app.entities.Department dep = new app.entities.Department(id, name);
            return departmentDao.add(dep);
        }
    }

    public void remove(int id) {
        DepartmentDao departmentDao = DepartmentDao.getInstance();
        departmentDao.remove(id);
    }

    public boolean edit(int id, String newName) {
        if (StringExtension.isNullOrEmpty(newName)) {
            throw new IllegalArgumentException("empty or null name");
        } else {
            DepartmentDao departmentDao = DepartmentDao.getInstance();
            boolean test;
            test = departmentDao.edit(id, newName);
            return test;
        }
    }


    public Department findById(int id) {
        return departmentDao.findById(id);
    }
}
