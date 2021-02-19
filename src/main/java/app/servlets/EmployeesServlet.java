package app.servlets;

import app.dto.EmployeeDto;
import app.dto.ValidationReport;
import app.entities.Department;
import app.entities.Employee;
import app.services.DepService;
import app.services.EmpService;
import app.services.RegexService;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeesServlet extends HttpServlet {
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private RegexService regexService = new RegexService();
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
        String idParameter = req.getParameter("id");
        String depid = req.getParameter("depid");

        EmployeeDto employeeDto = new EmployeeDto();

        ValidationReport report = (ValidationReport) req.getSession().getAttribute("emp" + idParameter);
        if (report != null) {
            employeeDto.setEmpID(StringUtils.isBlank(idParameter) ? null : Integer.parseInt(idParameter));
            employeeDto.setEmail(report.getValue("email"));
            employeeDto.setName(report.getValue("name"));
            employeeDto.setFamilyName(report.getValue("familyname"));
            employeeDto.setDate(report.getValue("date"));
            employeeDto.setZP(report.getValue("zp"));
            employeeDto.setDepID(depid);
            req.setAttribute("validationReport", report);
        } else {
            if (StringUtils.isNotBlank(idParameter)) {
                int id = Integer.parseInt(idParameter);
                Employee employee = empService.findById(id);
                employeeDto.setEmpID(employee.getEmpID());
                employeeDto.setEmail(employee.getEmail());
                employeeDto.setName(employee.getName());
                employeeDto.setFamilyName(employee.getFamilyName());
                employeeDto.setDate(employee.getDate() != null ? SIMPLE_DATE_FORMAT.format(employee.getDate()) : null);
                employeeDto.setZP(String.valueOf(employee.getZP()));
                employeeDto.setDepID(String.valueOf(employee.getDepID()));
            } else {
                employeeDto.setDepID(depid);
            }
        }

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

    private void createOrUpdateEmployee(HttpServletRequest req, HttpServletResponse resp) throws IOException, ParseException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String familyname = req.getParameter("familyname");
        String email = req.getParameter("email");
        String dateParam = req.getParameter("date");
        String zpParam = req.getParameter("zp");
        String depidParam = req.getParameter("depid");


        ValidationReport report = new ValidationReport();

        if (StringUtils.isBlank(name)) {
            report.addError("name", name, "Name is empty");
        } else if (!regexService.matchName(name)) {
            report.addError("name", name, "Name doesn't match minimal requirements");
        }

        if (StringUtils.isBlank(familyname)) {
            report.addError("familyname", name, "Familyname is empty");
        } else if (!regexService.matchName(name)) {
            report.addError("familyname", name, "Familyname doesn't match minimal requirements");
        }

        if (StringUtils.isBlank(email)) {
            report.addError("email", email, "Email is empty");
        } else if (!regexService.matchEmail(name)) {
            report.addError("email", email, "Email has incorrect format");
        }

        if (StringUtils.isBlank(dateParam)) {
            report.addError("date", dateParam, "Date is empty");
        } else if (!regexService.matchDate(dateParam)) {
            report.addError("date", dateParam, "Date has incorrect format");
        }

        if (StringUtils.isBlank(zpParam)) {
            report.addError("zp", zpParam, "Zp is empty");
        } else if (!regexService.matchZp(dateParam)) {
            report.addError("zp", zpParam, "Zp has incorrect format");
        }


        if (report.isValid()) {
            req.getSession().setAttribute("emp" + id, report);
            resp.sendRedirect("/employees/employee");
        } else {
            Date date = SIMPLE_DATE_FORMAT.parse(dateParam);
            int zp = Integer.parseInt(zpParam);
            int depid = Integer.parseInt(depidParam);
            if (StringUtils.isBlank(id)) {
                empService.add(name, familyname, email, date, zp, depid);
            } else {
                empService.edit(Integer.parseInt(id), name, familyname, email, date, zp, depid);
            }
            req.getSession().removeAttribute("emp" + id);
            resp.sendRedirect("/employees/list");
        }
    }
}