package app.servlets;

import app.dto.EmployeeDto;
import app.dto.ValidFieldsReport;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
        String depid = req.getParameter("depid");

        String editedEmpName = req.getParameter("editedEmpName");
        req.setAttribute("editedEmpName", editedEmpName);
        String newEmpName = req.getParameter("newEmpName");
        req.setAttribute("newEmpName", newEmpName);
        String deletedEmpName = req.getParameter("deletedEmpName");
        req.setAttribute("deletedEmpName", deletedEmpName);

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
        String samepers = req.getParameter("samepers");

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setDepID(depid);

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

            ValidationReport report = (ValidationReport) req.getSession().getAttribute("emp" + idParameter);
            if (report != null) {
                if (report.hasError("email")) {
                    employeeDto.setEmail(report.getValue("email"));
                }
                if (report.hasError("name")) {
                    employeeDto.setName(report.getValue("name"));
                }
                if (report.hasError("familyname")) {
                    employeeDto.setFamilyName(report.getValue("familyname"));
                }
                if (report.hasError("date")) {
                    employeeDto.setDate(report.getValue("date"));
                }
                if (report.hasError("zp")) {
                    employeeDto.setZP(report.getValue("zp"));
                }
                req.setAttribute("validationReport", report);
            }

            req.setAttribute("employee", employeeDto);
            req.setAttribute("samepers", samepers);

            req.getRequestDispatcher("/views/employees/update.jsp").forward(req, resp);
        }

        ValidationReport report = (ValidationReport) req.getSession().getAttribute("notvalidsaddemp");
        if (report != null) {
            if (report.hasError("email")) {
                employeeDto.setEmail(report.getValue("email"));
            }
            if (report.hasError("name")) {
                employeeDto.setName(report.getValue("name"));
            }
            if (report.hasError("familyname")) {
                employeeDto.setFamilyName(report.getValue("familyname"));
            }
            if (report.hasError("date")) {
                employeeDto.setDate(report.getValue("date"));
            }
            if (report.hasError("zp")) {
                employeeDto.setZP(report.getValue("zp"));
            }
            req.setAttribute("validationReport", report);
        }

        ValidFieldsReport vReport = (ValidFieldsReport) req.getSession().getAttribute("validsaddemp");
        if (vReport != null) {
            if (vReport.hasValids("email")) {
                employeeDto.setEmail(vReport.getValue("email"));
            }
            if (vReport.hasValids("name")) {
                employeeDto.setName(vReport.getValue("name"));
            }
            if (vReport.hasValids("familyname")) {
                employeeDto.setFamilyName(vReport.getValue("familyname"));
            }
            if (vReport.hasValids("date")) {
                employeeDto.setDate(vReport.getValue("date"));
            }
            if (vReport.hasValids("zp")) {
                employeeDto.setZP(vReport.getValue("zp"));
            }
        }
        req.setAttribute("samepers", samepers);
        req.setAttribute("employee", employeeDto);
        req.getRequestDispatcher("/views/employees/update.jsp").forward(req, resp);
    }

    private void deleteEmployee(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParameter = req.getParameter("id");
        String depid = req.getParameter("depid");

        int id = Integer.parseInt(idParameter);

        Employee employee = empService.findById(id);
        String deletedEmpName = employee.getName();
        empService.remove(id);
        resp.sendRedirect("/employees/list?depid=" + depid + "&deletedEmpName=" + deletedEmpName);
    }

    private void createOrUpdateEmployee(HttpServletRequest req, HttpServletResponse resp) throws IOException, ParseException {
        String idParameter = req.getParameter("id");
        String name = req.getParameter("name");
        String familyname = req.getParameter("familyname");
        String email = req.getParameter("email");
        String dateParam = req.getParameter("date");
        String zpParam = req.getParameter("zp");
        String depidParam = req.getParameter("depid");


        ValidationReport report = new ValidationReport();
        ValidFieldsReport vReport = new ValidFieldsReport();

        if (StringUtils.isBlank(name)) {
            report.addError("name", name, "Name is empty");
        } else if (!regexService.matchName(name)) {
            report.addError("name", name, "Name doesn't match minimal requirements");
        }
        vReport.addValids("name", name);

        if (StringUtils.isBlank(familyname)) {
            report.addError("familyname", familyname, "Familyname is empty");
        } else if (!regexService.matchName(name)) {
            report.addError("familyname", familyname, "Familyname doesn't match minimal requirements");
        }
        vReport.addValids("familyname", familyname);

        if (StringUtils.isBlank(email)) {
            report.addError("email", email, "Email is empty");
        } else if (!regexService.matchEmail(email)) {
            report.addError("email", email, "Email has incorrect format");
        }
        vReport.addValids("email", email);

        if (StringUtils.isBlank(dateParam)) {
            report.addError("date", dateParam, "Date is empty");
        } else if (!regexService.matchDate(dateParam)) {
            report.addError("date", dateParam, "Date has incorrect format");
        }
        vReport.addValids("date", dateParam);

        if (StringUtils.isBlank(zpParam)) {
            report.addError("zp", zpParam, "Zp is empty");
        } else if (!regexService.matchZp(zpParam)) {
            report.addError("zp", zpParam, "Zp has incorrect format");
        }
        vReport.addValids("zp", zpParam);

        if (!report.isValid()) {
            if ((StringUtils.isNotBlank(idParameter))) {
                req.getSession().setAttribute("emp" + idParameter, report);
            } else {
                req.getSession().setAttribute("notvalidsaddemp", report);
                req.getSession().setAttribute("validsaddemp", vReport);
            }
            resp.sendRedirect("/employees/employee?id=" + idParameter + "&depid=" + depidParam);
        } else {
            Date date = SIMPLE_DATE_FORMAT.parse(dateParam);
            int zp = Integer.parseInt(zpParam);
            int depid = Integer.parseInt(depidParam);
                if (StringUtils.isBlank(idParameter)) {
                    if (!empService.add(name, familyname, email, date, zp, depid)) {
                        String samepers = "this email is already registered in system";
                        req.getSession().setAttribute("validsaddemp", vReport);
                        resp.sendRedirect("/employees/employee?samepers=" + samepers + "&depid=" + depidParam);
                    } else {
                        String newEmpName = name;
                        req.getSession().removeAttribute("notvalidsaddemp");
                        req.getSession().removeAttribute("validsaddemp");
                        resp.sendRedirect("/employees/list?depid=" + depidParam + "&newEmpName=" + newEmpName);
                    }
                } else {
                    int id = Integer.parseInt(idParameter);
                    if (!empService.edit(id, name, familyname, email, date, zp, depid)) {
                        String samepers = "this email is already registered in system";
                        resp.sendRedirect("/employees/employee?id=" + id + "&samepers=" + samepers + "&depid=" + depidParam);
                    } else {
                        String editedEmpName = name;
                        req.getSession().removeAttribute("emp" + id);
                        resp.sendRedirect("/employees/list?depid=" + depidParam + "&editedEmpName=" + editedEmpName);
                    }
                }
        }
    }
}