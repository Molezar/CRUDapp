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

public class DepartmentsServlet extends HttpServlet {
    private DepContext depContext = DepContext.getInstance();
    private DepService depService = new DepService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        switch (uri) {
            case "/list":
                List<Department> deps = depContext.list();
                req.setAttribute("deps", deps);
                req.getRequestDispatcher("views/departments/list.jsp").forward(req, resp);
                break;
            case "/department":
                String idParameter = req.getParameter("id");
                if (idParameter != null) {
                    int id = Integer.parseInt(idParameter);
                    Department department = depContext.findById(id);
                    req.setAttribute("department", department);
                }
                req.getRequestDispatcher("views/departments/edit.jsp").forward(req, resp);

                break;
            default:
                throw new IllegalArgumentException(uri + " is not supported here");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        switch (uri) {
            case "/update":
                List<Department> deps = depContext.list();
                req.setAttribute("deps", deps);
                req.getRequestDispatcher("views/departments/list.jsp").forward(req, resp);
                break;
            case "/delete":
                String idParameter = req.getParameter("id");
                    int id = Integer.parseInt(idParameter);
                    depService.remove(id);
                    resp.sendRedirect("/list");
                break;
            default:
                throw new IllegalArgumentException(uri + " is not supported here");
        }
    }


    //    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//        String action = req.getParameter("action");
//        String id = req.getParameter("depId");
//        String name = req.getParameter("depName");
//
//        switch (action) {
//            case "Delete":
//                new DepService().remove(Integer.parseInt(id));
//                req.setAttribute("depName", name);
//
//                DepContext depContext = DepContext.getInstance();
//                List<Department> deps = depContext.list();
//                req.setAttribute("deps", deps);
//
//                doGet(req, resp);
//
//            case "Edit":
//                req.getRequestDispatcher("edit").forward(req, resp);
//            case "Emplist":
//                req.setAttribute("rDepId", id);
//                req.getRequestDispatcher("emplist").forward(req, resp);
//
//        }
//    }
}