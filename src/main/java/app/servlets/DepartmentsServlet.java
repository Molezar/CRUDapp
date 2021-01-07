package app.servlets;

import app.dao.DepartmentDao;
import app.entities.Department;
import app.services.DepService;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DepartmentsServlet extends HttpServlet {
    private DepartmentDao departmentDao = DepartmentDao.getInstance();
    private DepService depService = new DepService();


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
            case "/departments/delete" :
                deleteDepartment(req, resp);
                break;
            default:
                throw new IllegalArgumentException(uri + " is not supported here");
        }
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = req.getRequestURI();
        switch (uri) {
            case "/departments/update":
                createOrUpdateDepartment(req, resp);
                break;


//            case "/departments/delete":
//                deleteDepartment(req, resp);
//                break;
            default:
                throw new IllegalArgumentException(uri + " is not supported here");
        }
    }


    private void renderDepartmentsList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Department> deps = departmentDao.list();
        req.setAttribute("deps", deps);
        req.getRequestDispatcher("/views/departments/list.jsp").forward(req, resp);
    }

    private void renderDepartment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParameter = req.getParameter("id");
        if (idParameter != null && !"".equals(idParameter)) {
            int id = Integer.parseInt(idParameter);
            Department department = departmentDao.findById(id);
            req.setAttribute("department", department);
        }
        req.getRequestDispatcher("/views/departments/edit.jsp").forward(req, resp);
    }

    private void deleteDepartment(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParameter = req.getParameter("id");
        int id = Integer.parseInt(idParameter);
        depService.remove(id);
        resp.sendRedirect("/departments/list");
    }

    private void createOrUpdateDepartment(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParameter = req.getParameter("id");
        String name = req.getParameter("name");
        if (idParameter == null || "".equals(idParameter)) {
            depService.add(name);
        } else {
            int id = Integer.parseInt(idParameter);
            depService.edit(id, name);
        }
        resp.sendRedirect("/departments/list");
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