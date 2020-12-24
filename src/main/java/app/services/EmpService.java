//package app.services;
//
//import app.extensions.StringExtension;
//import app.dbContext.EmpContext;
//
//public class EmpService {
//    public boolean add(String name) {
//        if (StringExtension.isNullOrEmpty(name)) {
//            throw new IllegalArgumentException("empty or null name");
//        } else {
//            EmpContext empContext = EmpContext.getInstance();
//            int lastid = empContext.getLastID();
//            int id = lastid + 1;
//            app.entities.Employee emp = new app.entities.Employee(id, name);
//            return  empContext.add(emp);
//        }
//    }
//
//    public void remove(int id) {
//        EmpContext empContext = EmpContext.getInstance();
//        empContext.remove(id);
//    }
//
//    public boolean edit(int id, String newName) {
//        if (StringExtension.isNullOrEmpty(newName)) {
//            throw new IllegalArgumentException("empty or null name");
//        } else {
//            EmpContext empContext = EmpContext.getInstance();
//            boolean test;
//            test = empContext.edit(id, newName);
//            return test;
//        }
//    }
//
//
//
//    public String getNameById(int id) {
//        EmpContext empContext = EmpContext.getInstance();
//        String empName = empContext.getNameById(id);
//        return empName;
//    }
//}
//
