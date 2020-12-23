package app.servlets;

import app.services.EmpService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EmpaddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/empadd.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");

        boolean test;
        test = new EmpService().add(name);
        if (test != false) {
            req.setAttribute("empName", name);
            doGet(req, resp);
        } else {
            String sameName = "same";
            req.setAttribute("empName", name);
            req.setAttribute("sameEmpName", sameName);
            doGet(req, resp);
        }
    }
}