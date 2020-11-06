package app.model;

import app.entities.Department;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Model {
    private static Model instance = new Model();

    private List<Department> model;

    public static Model getInstance() {
        return instance;
    }

    private Model() {
        model = new ArrayList<>();
    }

    public void add(Department dep) {
        model.add(dep);
    }

    public List<String> list() {
        return model.stream()
                .map(Department::getDepName)
                .collect(Collectors.toList());
    }
}