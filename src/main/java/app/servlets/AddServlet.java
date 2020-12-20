package app.servlets;

import app.services.DepService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/add.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");

        boolean test;
        test = new DepService().add(name);
        if (test != false) {
            req.setAttribute("depName", name);
            doGet(req, resp);
        } else {
            String sameName = "same";
            req.setAttribute("depName", name);
            req.setAttribute("sameDepName", sameName);
            doGet(req, resp);
        }
    }
}