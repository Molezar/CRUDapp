package app.servlets;

import app.dbContext.DepContext;
import app.entities.Department;
import app.services.DepService;
import app.dbContext.EmpContext;
import app.entities.Employee;
import app.services.EmpService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class EmplistServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("rDepId");
        EmpContext empContext = EmpContext.getInstance();
        List<Employee> emps = empContext.list(Integer.parseInt(id));
        req.setAttribute("emps", emps);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/emplist.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");
        String id = req.getParameter("empID");
        String name = req.getParameter("empName");
        String familyname = req.getParameter("empFamilyName");
        String email = req.getParameter("empName");
        String date = req.getParameter("empDate");
        String zp = req.getParameter("ZP");
        String depid = req.getParameter("depID");

        switch (action) {
            case "Delete":
                new EmpService().remove(Integer.parseInt(id));
                req.setAttribute("empName", name);

                EmpContext empContext = EmpContext.getInstance();
                List<Employee> emps = empContext.list();
                req.setAttribute("emps", emps);

                doGet(req, resp);

            case "Edit":
                req.getRequestDispatcher("empedit").forward(req, resp);
            case "Emplist":
                req.getRequestDispatcher("emplist").forward(req, resp);

        }
    }
}