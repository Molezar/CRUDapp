package app.servlets;

import app.dao.DepartmentDao;
import app.dao.EmployeeDao;
import app.entities.Department;
import app.entities.Employee;
import app.services.DepService;
import app.services.EmpService;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

public class EmployeesServlet extends HttpServlet {
    private EmployeeDao employeeDao = new EmployeeDao();
    private EmpService empService = new EmpService();
    private DepartmentDao departmentDao = new DepartmentDao();
    private DepService depService = new DepService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        switch (uri) {
            case "/employees/list":
                renderEmployeesList(req, resp);
                break;
            case "/employees/employee":
                renderEmployee(req, resp);
                break;
            case "/employees/delete" :
                deleteEmployee(req, resp);
                break;
            default:
                throw new IllegalArgumentException(uri + " is not supported here");
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String uri = req.getRequestURI();
        switch (uri) {
            case "/employees/list":
                renderEmployeesList(req, resp);
                break;
            case "/employees/update":
                try {
                    createOrUpdateEmployee(req, resp);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            default:
                throw new IllegalArgumentException(uri + " is not supported here");
        }
    }


    private void renderEmployeesList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String depid = req.getParameter("id");
        int id = Integer.parseInt(depid);
        Department dep = depService.findById(id);
        req.setAttribute("dep", dep);
        List<Employee> emps = empService.list(id);
        req.setAttribute("emps", emps);
        req.getRequestDispatcher("/views/employees/list.jsp").forward(req, resp);
    }

    private void renderEmployee(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParameter = req.getParameter("id");
        String depid = req.getParameter("depid");

        if (idParameter != null && !"".equals(idParameter)) {
            int id = Integer.parseInt(idParameter);
            empService.list(Integer.parseInt(depid));
            Employee employee = empService.findById(id);
            req.setAttribute("employee", employee);
        }
        req.setAttribute("depid", depid);
        req.getRequestDispatcher("/views/employees/edit.jsp").forward(req, resp);
    }

    private void deleteEmployee(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParameter = req.getParameter("id");
        Employee employee = empService.findById(Integer.parseInt(idParameter));
        String name = employee.getName();
        int id = Integer.parseInt(idParameter);
        empService.remove(id);
        req.setAttribute("empName", name);
        req.getRequestDispatcher("/employees/list").forward(req, resp);
    }

    private void createOrUpdateEmployee(HttpServletRequest req, HttpServletResponse resp) throws IOException, ParseException, ServletException {
        String idParameter = req.getParameter("id");
        String name = req.getParameter("name");
        String familyname = req.getParameter("familyname");
        String email = req.getParameter("email");
        String date = req.getParameter("date");
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        String zp = req.getParameter("zp");
        String depid = req.getParameter("depid");
        
        if (idParameter == null || "".equals(idParameter)) {
            empService.add(name, familyname, email, date1, Integer.parseInt(zp), Integer.parseInt(depid));
        } else {
            empService.edit(Integer.parseInt(idParameter), name, familyname, email, date1, Integer.parseInt(zp), Integer.parseInt(depid));
        }
        req.getRequestDispatcher("/employees/list").forward(req, resp);
    }
}