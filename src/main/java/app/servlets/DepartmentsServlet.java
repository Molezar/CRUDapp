package app.servlets;

import app.dto.DepartmentDto;
import app.dto.EmployeeDto;
import app.dto.ValidationReport;
import app.entities.Department;
import app.entities.Employee;
import app.services.DepService;
import app.services.RegexService;
import org.apache.commons.lang3.StringUtils;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DepartmentsServlet extends HttpServlet {
    private DepService depService = new DepService();
    private RegexService regexService = new RegexService();


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
            case "/departments/update":
                createOrUpdateDepartment(req, resp);
                break;
            default:
                throw new IllegalArgumentException(uri + " is not supported here");
        }
    }


    private void renderDepartmentsList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String editedDepName = req.getParameter("editedDepName");
        req.setAttribute("editedDepName", editedDepName);
        String newDepName = req.getParameter("newDepName");
        req.setAttribute("newDepName", newDepName);
        String deletedDepName = req.getParameter("deletedDepName");
        req.setAttribute("deletedDepName", deletedDepName);


        List<Department> deps = depService.list();
        req.setAttribute("deps", deps);
        req.getRequestDispatcher("/views/departments/list.jsp").forward(req, resp);
    }

    private void renderDepartment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParameter = req.getParameter("id");

        DepartmentDto departmentDto = new DepartmentDto();

        if (StringUtils.isNotBlank(idParameter)) {
            int id = Integer.parseInt(idParameter);
            Department department = depService.findById(id);
            departmentDto.setDepID(department.getDepID());
            departmentDto.setDepName(department.getDepName());
        }

        ValidationReport report = (ValidationReport) req.getSession().getAttribute("dep" + idParameter);

        if (report != null) {
            if (report.hasError("name")) {
                departmentDto.setDepName(report.getValue("name"));
            }
            req.setAttribute("validationReport", report);
        }

        req.setAttribute("department", departmentDto);
        req.getRequestDispatcher("/views/departments/edit.jsp").forward(req, resp);
    }

    private void deleteDepartment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParameter = req.getParameter("id");

        int id = Integer.parseInt(idParameter);
        Department department = depService.findById(id);
        String deletedDepName = department.getDepName();
        depService.remove(id);
        resp.sendRedirect("/departments/list?depid=" + idParameter + "&deletedDepName=" + deletedDepName);
    }

    private void createOrUpdateDepartment(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String idParameter = req.getParameter("id");
        String name = req.getParameter("name");

        ValidationReport report = new ValidationReport();

        if (StringUtils.isBlank(name)) {
            report.addError("name", name, "Name is empty");
        } else if (!regexService.matchName(name)) {
            report.addError("name", name, "Name doesn't match minimal requirements");
        }

        if (!report.isValid()) {
            req.getSession().setAttribute("dep" + idParameter, report);
            resp.sendRedirect("/departments/department?id=" + idParameter);
        } else {
            if (StringUtils.isBlank(idParameter)) {
                depService.add(name);
                String newDepName = name;
                req.getSession().removeAttribute("dep" + idParameter);
                resp.sendRedirect("/departments/list?depid=" + idParameter + "&newDepName=" + newDepName);

            } else {
                depService.edit(Integer.parseInt(idParameter), name);
                String editedDepName = name;
                req.getSession().removeAttribute("dep" + idParameter);
                resp.sendRedirect("/departments/list?depid=" + idParameter + "&editedDepName=" + editedDepName);
            }
        }


//        if (idParameter == null || "".equals(idParameter)) {
//            if (name == null || name == "" || !regex.match(1, name)) {
//                errors.put("name", "Name is empty or doesn't match minimal requirements");
//                req.setAttribute("errors", errors);
//                req.getRequestDispatcher("/views/departments/edit.jsp").forward(req, resp);
//                return;
//            }
//            try {
//                depService.add(name);
//            } catch (SQLException e) {
//                String sqlerror = e.getMessage();
//                errors.put("name", sqlerror);
//                req.setAttribute("errors", errors);
//                req.getRequestDispatcher("/views/departments/error.jsp").forward(req, resp);
//                return;
//            } catch (IllegalArgumentException ex) {
//                errors.put("name", ex.getMessage());
//                req.setAttribute("errors", errors);
//                req.getRequestDispatcher("/views/departments/edit.jsp").forward(req, resp);
//                return;
//            }
//            req.setAttribute("newDepName", name);
//        } else {
//            if (name == null || name == "" || !regex.match(1, name)) {
//                errors.put("name", "Name is empty or doesn't match minimal requirements");
//                req.setAttribute("errors", errors);
//                try {
//                    Department department = depService.findById(Integer.parseInt(idParameter));
//                    req.setAttribute("department", department);
//                } catch (SQLException ex) {
//                    errors.put("name", ex.getMessage());
//                    req.setAttribute("errors", errors);
//                    req.getRequestDispatcher("/views/departments/error.jsp").forward(req, resp);
//                    return;
//                }
//                req.getRequestDispatcher("/views/departments/edit.jsp").forward(req, resp);
//                return;
//            }
//            try {
//                int id = Integer.parseInt(idParameter);
//                depService.edit(id, name);
//            } catch (SQLException e) {
//                String sqlerror = e.getMessage();
//                errors.put("name", sqlerror);
//                req.setAttribute("errors", errors);
//                req.getRequestDispatcher("/views/departments/error.jsp").forward(req, resp);
//                return;
//            } catch (IllegalArgumentException ex) {
//                errors.put("name", ex.getMessage());
//                req.setAttribute("errors", errors);
//                try {
//                    Department department = depService.findById(Integer.parseInt(idParameter));
//                    req.setAttribute("department", department);
//                } catch (SQLException e) {
//                    String sqlerror = e.getMessage();
//                    errors.put("name", sqlerror);
//                    req.setAttribute("errors", errors);
//                    req.getRequestDispatcher("/views/departments/error.jsp").forward(req, resp);
//                    return;
//                }
//                req.getRequestDispatcher("/views/departments/edit.jsp").forward(req, resp);
//                return;
//            }
//            req.setAttribute("editedDepName", name);
//        }
//        req.getRequestDispatcher("/departments/list").forward(req, resp);
    }
}