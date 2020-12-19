package app.servlets;

import app.dbContext.DepContext;
import app.entities.Department;
import app.services.DepService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DepContext depContext = DepContext.getInstance();
        List<Department> deps = depContext.list();
        req.setAttribute("deps", deps);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/list.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");
        String id = req.getParameter("depId");
        String name = req.getParameter("depName");

        switch (action) {
            case "Delete":
                new DepService().remove(Integer.parseInt(id));
                req.setAttribute("depName", name);

                DepContext depContext = DepContext.getInstance();
                List<Department> deps = depContext.list();
                req.setAttribute("deps", deps);

                doGet(req, resp);

            case "Edit":
                req.getRequestDispatcher("edit").forward(req, resp);
        }
    }
}