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
import java.util.List;

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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = req.getRequestURI();
        switch (uri) {
            case "/employees/update":
                createOrUpdateEmployee(req, resp);
                break;
            default:
                throw new IllegalArgumentException(uri + " is not supported here");
        }
    }


    private void renderEmployeesList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String depid = req.getParameter("id");
        Department dep = depService.findById(Integer.parseInt(depid));
        int id = Integer.parseInt(depid);
        req.setAttribute("dep", dep);

        List<Employee> emps = employeeDao.list(id);
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

    private void createOrUpdateEmployee(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParameter = req.getParameter("id");
        String name = req.getParameter("name");
        String depid = req.getParameter("depid");

        if (idParameter == null || "".equals(idParameter)) {
            empService.add(name);
        } else {
            int id = Integer.parseInt(idParameter);
            empService.edit(id, name, Integer.parseInt(idParameter));
        }
        resp.sendRedirect("/employees/list");
    }
}