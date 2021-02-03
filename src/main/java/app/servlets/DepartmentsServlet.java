package app.servlets;

import app.entities.Department;
import app.services.DepService;
import app.services.RegexService;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentsServlet extends HttpServlet {
    private DepService depService = new DepService();
    private RegexService regex = new RegexService();
    private Map<String, String> errors = new HashMap<>();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String uri = req.getRequestURI();
        switch (uri) {
            case "/departments/list":
                renderDepartmentsList(req, resp);
                break;
            case "/departments/department":
                renderDepartment(req, resp);
                break;
            case "/departments/delete":
                deleteDepartment(req, resp);
                break;
            default:
                throw new IllegalArgumentException(uri + " is not supported here");
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String uri = req.getRequestURI();
        switch (uri) {
            case "/departments/list":
                renderDepartmentsList(req, resp);
                break;
            case "/departments/update":
                createOrUpdateDepartment(req, resp);
                break;
            default:
                throw new IllegalArgumentException(uri + " is not supported here");
        }
    }


    private void renderDepartmentsList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Department> deps = depService.list();
            req.setAttribute("deps", deps);
        } catch (SQLException ex) {
            errors.put("name", ex.getMessage());
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/views/departments/list.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("/views/departments/list.jsp").forward(req, resp);
    }

    private void renderDepartment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParameter = req.getParameter("id");
        if (idParameter != null && !"".equals(idParameter)) {
            int id = Integer.parseInt(idParameter);
            try {
                depService.list();
                Department department = depService.findById(id);
                req.setAttribute("department", department);
            } catch (SQLException ex) {
                errors.put("name", ex.getMessage());
                req.setAttribute("errors", errors);
                req.getRequestDispatcher("/views/departments/edit.jsp").forward(req, resp);
                return;
            }
        }
        req.getRequestDispatcher("/views/departments/edit.jsp").forward(req, resp);
    }

    private void deleteDepartment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParameter = req.getParameter("id");
        int id = Integer.parseInt(idParameter);
        try {
            Department department = depService.findById(Integer.parseInt(idParameter));
            depService.remove(id);
            req.setAttribute("department", department);
        } catch (SQLException ex) {
            errors.put("name", ex.getMessage());
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/departments/list").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("/departments/list").forward(req, resp);
    }

    private void createOrUpdateDepartment(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String idParameter = req.getParameter("id");
        String name = req.getParameter("name");

        if (idParameter == null || "".equals(idParameter)) {
            if (name == null || name == "" || !regex.match(1, name)) {
                errors.put("name", "Name is empty or doesn't match minimal requirements");
                req.setAttribute("errors", errors);
                req.getRequestDispatcher("/views/departments/edit.jsp").forward(req, resp);
                return;
            }
            try {
                depService.add(name);
            } catch (SQLException e) {
                String sqlerror = e.getMessage();
                errors.put("name", sqlerror);
                req.setAttribute("errors", errors);
                req.getRequestDispatcher("/views/departments/error.jsp").forward(req, resp);
                return;
            } catch (IllegalArgumentException ex) {
                errors.put("name", ex.getMessage());
                req.setAttribute("errors", errors);
                req.getRequestDispatcher("/views/departments/edit.jsp").forward(req, resp);
                return;
            }
            req.setAttribute("newDepName", name);
        } else {
            if (name == null || name == "" || !regex.match(1, name)) {
                errors.put("name", "Name is empty or doesn't match minimal requirements");
                req.setAttribute("errors", errors);
                try {
                    Department department = depService.findById(Integer.parseInt(idParameter));
                    req.setAttribute("department", department);
                } catch (SQLException ex) {
                    errors.put("name", ex.getMessage());
                    req.setAttribute("errors", errors);
                    req.getRequestDispatcher("/views/departments/error.jsp").forward(req, resp);
                    return;
                }
                req.getRequestDispatcher("/views/departments/edit.jsp").forward(req, resp);
                return;
            }
            try {
                int id = Integer.parseInt(idParameter);
                depService.edit(id, name);
            } catch (SQLException e) {
                String sqlerror = e.getMessage();
                errors.put("name", sqlerror);
                req.setAttribute("errors", errors);
                req.getRequestDispatcher("/views/departments/error.jsp").forward(req, resp);
                return;
            } catch (IllegalArgumentException ex) {
                errors.put("name", ex.getMessage());
                req.setAttribute("errors", errors);
                try {
                    Department department = depService.findById(Integer.parseInt(idParameter));
                    req.setAttribute("department", department);
                } catch (SQLException e) {
                    String sqlerror = e.getMessage();
                    errors.put("name", sqlerror);
                    req.setAttribute("errors", errors);
                    req.getRequestDispatcher("/views/departments/error.jsp").forward(req, resp);
                    return;
                }
                req.getRequestDispatcher("/views/departments/edit.jsp").forward(req, resp);
                return;
            }
            req.setAttribute("editedDepName", name);
        }
        req.getRequestDispatcher("/departments/list").forward(req, resp);
    }
}