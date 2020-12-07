package app.servlets;

import app.services.DepService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/edit.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String newName = req.getParameter("newDepName");
        if (newName!=null){
            String id = req.getParameter("NewDepId");
            req.setAttribute("rDepName", newName);
            req.setAttribute("rDepId", id);
            req.setAttribute("newDepName", newName);

            //        new DepService().edit(id);
            doGet(req, resp);

        }
        String id = req.getParameter("depId");
        String name = req.getParameter("depName");

        req.setAttribute("rDepName", name);
        req.setAttribute("rDepId", id);

        //        new DepService().edit(id);
        doGet(req, resp);
    }
}