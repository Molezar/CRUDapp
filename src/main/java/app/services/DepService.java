package app.services;

import app.entities.Department;
import app.extensions.StringExtension;
import app.dbContext.DepContext;

public class DepService {
    private DepContext depContext = DepContext.getInstance();


    public boolean add(String name) {
        if (StringExtension.isNullOrEmpty(name)) {
            throw new IllegalArgumentException("empty or null name");
        } else {
            int lastid = depContext.getLastID();
            int id = lastid + 1;
            app.entities.Department dep = new app.entities.Department(id, name);
            return depContext.add(dep);
        }
    }

    public void remove(int id) {
        DepContext depContext = DepContext.getInstance();
        depContext.remove(id);
    }

    public boolean edit(int id, String newName) {
        if (StringExtension.isNullOrEmpty(newName)) {
            throw new IllegalArgumentException("empty or null name");
        } else {
            DepContext depContext = DepContext.getInstance();
            boolean test;
            test = depContext.edit(id, newName);
            return test;
        }
    }


    public Department findById(int id) {
        return depContext.findById(id);
    }
}
