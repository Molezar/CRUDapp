package app.services;

import app.extensions.StringExtension;
import app.dbContext.DepContext;

public class DepService {
    public void add(String name) {
        if (StringExtension.isNullOrEmpty(name)) {
            throw new IllegalArgumentException("empty or null name");
        } else {
            DepContext depContext = DepContext.getInstance();
            int lastid = depContext.getLastID();
            int id = lastid + 1;
            app.entities.Department dep = new app.entities.Department(id, name);
            depContext.add(dep);
        }
    }

    public void remove(int id) {
        DepContext depContext = DepContext.getInstance();
        depContext.remove(id);
    }

    public void edit(int id, String newName) {
        DepContext depContext = DepContext.getInstance();
        depContext.edit(id, newName);
    }



        public String getNameById(int id) {
            DepContext depContext = DepContext.getInstance();
            String depName = depContext.getNameById(id);
            return depName;
        }
}
