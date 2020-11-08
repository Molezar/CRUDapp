package app.services;

import app.extensions.StringExtension;
import app.dbContext.DepContext;

public class DepService {
    public void add(String name) {
        if (StringExtension.isNullOrEmpty(name)) {
            throw new IllegalArgumentException("empty or null name");
        } else {
            app.entities.Department dep = new app.entities.Department(name);
            DepContext depContext = DepContext.getInstance();
            depContext.add(dep);
        }
    }
}
