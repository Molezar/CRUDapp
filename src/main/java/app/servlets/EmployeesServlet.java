package app.servlets;

import app.entities.Department;
import app.entities.Employee;
import app.services.DepService;
import app.services.EmpService;
import app.services.RegexService;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Map;

public class EmployeesServlet extends HttpServlet {

    private RegexService regex = new RegexService();
    private EmpService empService = new EmpService();
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
            case "/employees/delete":
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
        Map<String, String> errors = new HashMap<>();
        String depid = req.getParameter("depid");

        int id = Integer.parseInt(depid);
        try {
            Department dep = depService.findById(id);
            req.setAttribute("dep", dep);
            List<Employee> emps = empService.list(id);
            req.setAttribute("emps", emps);
        } catch (SQLException e) {
            String sqlerror = e.getMessage();
            errors.put("name", sqlerror);
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/views/employees/error.jsp").forward(req, resp);
            return;
        }

        req.getRequestDispatcher("/views/employees/list.jsp").forward(req, resp);
    }

    private void renderEmployee(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> errors = new HashMap<>();
        String idParameter = req.getParameter("id");
        String depid = req.getParameter("depid");

        if (idParameter != null && !"".equals(idParameter)) {
            int id = Integer.parseInt(idParameter);
            try {
                empService.list(Integer.parseInt(depid));
                Employee employee = empService.findById(id);
                req.setAttribute("employee", employee);
            } catch (SQLException e) {
                String sqlerror = e.getMessage();
                errors.put("name", sqlerror);
                req.setAttribute("errors", errors);
                req.getRequestDispatcher("/views/employees/error.jsp").forward(req, resp);
                return;
            }
        }
        req.setAttribute("depid", depid);
        req.setAttribute("id", idParameter);

        req.getRequestDispatcher("/views/employees/edit.jsp").forward(req, resp);
    }

    private void deleteEmployee(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> errors = new HashMap<>();
        String idParameter = req.getParameter("id");
        String depid = req.getParameter("depid");

        int id = Integer.parseInt(idParameter);
        try {
            Employee employee = empService.findById(id);
            req.setAttribute("employee", employee);
            req.setAttribute("depid", depid);
            empService.remove(id);
        } catch (SQLException e) {
            String sqlerror = e.getMessage();
            errors.put("name", sqlerror);
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/views/employees/error.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("/employees/list").forward(req, resp);
    }

    private void createOrUpdateEmployee(HttpServletRequest req, HttpServletResponse resp) throws IOException, ParseException, ServletException {
        Map<String, String> errors = new HashMap<>();
        String idParameter = req.getParameter("id");
        String name = req.getParameter("name");
        String familyname = req.getParameter("familyname");
        String email = req.getParameter("email");
        String date = req.getParameter("date");
        String zp = req.getParameter("zp");
        String depid = req.getParameter("depid");


        if (idParameter == null || "".equals(idParameter)) {
            if (name == null || name == "" || !regex.match(1, name)) {
                errors.put("name", "Name is empty or doesn't match minimal requirements");
                req.setAttribute("errors", errors);
                req.setAttribute("depid", depid);
                req.getRequestDispatcher("/views/employees/edit.jsp").forward(req, resp);
                return;
            }
            if (familyname == null || familyname == "" || !regex.match(1, familyname)) {
                errors.put("familyname", "Familyname is empty or doesn't match minimal requirements");
                req.setAttribute("errors", errors);
                req.setAttribute("depid", depid);
                req.setAttribute("name", name);
                req.getRequestDispatcher("/views/employees/edit.jsp").forward(req, resp);
                return;
            }
            if (email == null || email == "" || !regex.match(2, email)) {
                errors.put("email", "email is empty or doesn't match minimal requirements");
                req.setAttribute("errors", errors);
                req.setAttribute("depid", depid);
                req.getRequestDispatcher("/views/employees/edit.jsp").forward(req, resp);
                return;
            }
            if (date == null || date == "" || !regex.match(3, date)) {
                errors.put("date", "date is empty or doesn't match minimal requirements");
                req.setAttribute("errors", errors);
                req.setAttribute("depid", depid);
                req.getRequestDispatcher("/views/employees/edit.jsp").forward(req, resp);
                return;
            }
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            if (zp == null || zp == "" || !regex.match(4, zp)) {
                errors.put("zp", "zp is empty or doesn't match minimal requirements");
                req.setAttribute("errors", errors);
                req.setAttribute("depid", depid);
                req.getRequestDispatcher("/views/employees/edit.jsp").forward(req, resp);
                return;
            }
            try {
                empService.add(name, familyname, email, date1, Integer.parseInt(zp), Integer.parseInt(depid));
            } catch (SQLException e) {
                String sqlerror = e.getMessage();
                errors.put("name", sqlerror);
                req.setAttribute("errors", errors);
                req.getRequestDispatcher("/views/employees/error.jsp").forward(req, resp);
                return;
            } catch (IllegalArgumentException ex) {
                if (ex.getMessage().equals("email")) {
                    errors.put("email", "employee with this email already exists");
                    req.setAttribute("errors", errors);
                    req.setAttribute("depid", depid);
                    req.getRequestDispatcher("/views/employees/edit.jsp").forward(req, resp);
                    return;
                }
                errors.put("name", ex.getMessage());
                req.setAttribute("errors", errors);
                req.setAttribute("depid", depid);
                req.getRequestDispatcher("/views/employees/edit.jsp").forward(req, resp);
                return;
            }
            req.setAttribute("depid", depid);
            req.setAttribute("newEmpName", name);
        } else {
            if (name == null || name == "" || !regex.match(1, name)) {
                errors.put("name", "Name is empty or doesn't match minimal requirements");
                req.setAttribute("errors", errors);
                req.setAttribute("depid", depid);
                req.setAttribute("id", idParameter);
                try {
                    int id = Integer.parseInt(idParameter);
                    Employee employee = empService.findById(id);
                    req.setAttribute("employee", employee);
                } catch (SQLException e) {
                    String sqlerror = e.getMessage();
                    errors.put("name", sqlerror);
                    req.setAttribute("errors", errors);
                    req.getRequestDispatcher("/views/employees/error.jsp").forward(req, resp);
                    return;
                }
                req.getRequestDispatcher("/views/employees/edit.jsp").forward(req, resp);
                return;
            }
            if (familyname == null || familyname == "" || !regex.match(1, familyname)) {
                errors.put("familyname", "Familyname is empty or doesn't match minimal requirements");
                req.setAttribute("errors", errors);
                req.setAttribute("depid", depid);
                req.setAttribute("id", idParameter);
                try {
                    int id = Integer.parseInt(idParameter);
                    Employee employee = empService.findById(id);
                    req.setAttribute("employee", employee);
                } catch (SQLException e) {
                    String sqlerror = e.getMessage();
                    errors.put("name", sqlerror);
                    req.setAttribute("errors", errors);
                    req.getRequestDispatcher("/views/employees/error.jsp").forward(req, resp);
                    return;
                }
                req.getRequestDispatcher("/views/employees/edit.jsp").forward(req, resp);
                return;
            }
            if (email == null || email == "" || !regex.match(2, email)) {
                errors.put("email", "email is empty or doesn't match minimal requirements");
                req.setAttribute("errors", errors);
                req.setAttribute("depid", depid);
                req.setAttribute("id", idParameter);
                try {
                    int id = Integer.parseInt(idParameter);
                    Employee employee = empService.findById(id);
                    req.setAttribute("employee", employee);
                } catch (SQLException e) {
                    String sqlerror = e.getMessage();
                    errors.put("name", sqlerror);
                    req.setAttribute("errors", errors);
                    req.getRequestDispatcher("/views/employees/error.jsp").forward(req, resp);
                    return;
                }
                req.getRequestDispatcher("/views/employees/edit.jsp").forward(req, resp);
                return;
            }
            if (date == null || date == "" || !regex.match(3, date)) {
                errors.put("date", "date is empty or doesn't match minimal requirements");
                req.setAttribute("errors", errors);
                req.setAttribute("depid", depid);
                req.setAttribute("id", idParameter);
                try {
                    int id = Integer.parseInt(idParameter);
                    Employee employee = empService.findById(id);
                    req.setAttribute("employee", employee);
                } catch (SQLException e) {
                    String sqlerror = e.getMessage();
                    errors.put("name", sqlerror);
                    req.setAttribute("errors", errors);
                    req.getRequestDispatcher("/views/employees/error.jsp").forward(req, resp);
                    return;
                }
                req.getRequestDispatcher("/views/employees/edit.jsp").forward(req, resp);
                return;
            }
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);

            if (zp == null || zp == "" || !regex.match(4, zp)) {
                errors.put("zp", "zp is empty or doesn't match minimal requirements");
                req.setAttribute("errors", errors);
                req.setAttribute("depid", depid);
                req.setAttribute("id", idParameter);
                try {
                    int id = Integer.parseInt(idParameter);
                    Employee employee = empService.findById(id);
                    req.setAttribute("employee", employee);
                } catch (SQLException e) {
                    String sqlerror = e.getMessage();
                    errors.put("name", sqlerror);
                    req.setAttribute("errors", errors);
                    req.getRequestDispatcher("/views/employees/error.jsp").forward(req, resp);
                    return;
                }

                req.getRequestDispatcher("/views/employees/edit.jsp").forward(req, resp);
                return;
            }
            try {
                empService.edit(Integer.parseInt(idParameter), name, familyname, email, date1, Integer.parseInt(zp), Integer.parseInt(depid));
            } catch (SQLException e) {
                String sqlerror = e.getMessage();
                errors.put("name", sqlerror);
                req.setAttribute("errors", errors);
                req.getRequestDispatcher("/views/employees/error.jsp").forward(req, resp);
                return;
            } catch (IllegalArgumentException ex) {
                errors.put("name", ex.getMessage());
                req.setAttribute("errors", errors);
                req.setAttribute("depid", depid);
                req.setAttribute("id", idParameter);
                try {
                    int id = Integer.parseInt(idParameter);
                    Employee employee = empService.findById(id);
                    req.setAttribute("employee", employee);
                } catch (SQLException e) {
                    String sqlerror = e.getMessage();
                    errors.put("name", sqlerror);
                    req.setAttribute("errors", errors);
                    req.getRequestDispatcher("/views/employees/error.jsp").forward(req, resp);
                    return;
                }
                req.getRequestDispatcher("/views/employees/edit.jsp").forward(req, resp);
                return;
            }
            req.setAttribute("depid", depid);
            req.setAttribute("editedEmpName", name);
        }
        req.getRequestDispatcher("/employees/list").forward(req, resp);
    }
}