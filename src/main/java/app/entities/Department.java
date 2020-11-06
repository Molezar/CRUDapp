package app.entities;

import java.util.Objects;

public class Department {

    private String DepName;

    public Department(){

    }

    public Department(String DepName) {
        this.DepName = DepName;
    }

    public String getDepName() {
        return DepName;
    }

    public void setDepName(String DepName) {
        this.DepName = DepName;
    }

    @Override
    public String toString() {
        return "Department{name='" + DepName + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Department dep = (Department) o;

        if (!Objects.equals(DepName, dep.DepName)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = DepName != null ? DepName.hashCode() : 0;
        result = 31 * result;
        return result;
    }
}
