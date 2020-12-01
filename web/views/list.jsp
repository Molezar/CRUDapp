<%@ page import="java.util.List" %>
<%@ page import="app.entities.Department" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <title>Departments list</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>

<body class="w3-light-grey">
<div class="w3-container w3-blue-grey w3-opacity w3-right-align">
    <h1>Departments</h1>
</div>

<div class="w3-container w3-center w3-margin-bottom w3-padding">
    <div class="w3-card-4">
        <div class="w3-container w3-light-blue">
            <h2>Departments</h2>
        </div>

        <%
            String depName = (String) request.getAttribute("depName");

            if (depName != null) {
                out.println("<div class=\"w3-panel w3-green w3-display-container w3-card-4 w3-round\">\n" +
                        "   <span onclick=\"this.parentElement.style.display='none'\"\n" +
                        "   class=\"w3-button w3-margin-right w3-display-right w3-round-large w3-hover-green w3-border w3-border-green w3-hover-border-grey\">×</span>\n" +
                        "   <h5>Department '" + request.getAttribute("depName") + "' has been removed!</h5>\n" +
                        "</div>");
            }
        %>
        <table>
        <c:forEach var="department" items="${requestScope.deps}">
            <tr class="w3-panel w3-lightblue w3-card-4 w3-round">
                <td class="w3-container w3-center w3-green w3-round-large w3-hover-blue w3-border w3-border-red">
                        ${department.getDepName()}
                </td>

<%--                <td class="w3-container w3-center w3-green w3-round-large w3-hover-blue w3-border w3-border-red">--%>
<%--                    <form method="get" action="/edit.jsp">--%>
<%--                        <input type="hidden" value="${department.getDepName()}" name="depName">--%>
<%--                        <input type="hidden" value="${department.getDepID()}" name="depId">--%>
<%--                        <input class="w3-button w3-blue w3-center--%>
<%--                               w3-round-large w3-hover-red w3-border--%>
<%--                               w3-border-blue w3-hover-border-grey"--%>
<%--                               type="submit" value="${department.getDepName()}" name="action">--%>
<%--                    </form>--%>
<%--                </td>--%>


                <td class="w3-container w3-center">
                    <form method="post">
                        <input type="hidden" value="${department.getDepName()}" name="depName">
                        <input type="hidden" value="${department.getDepID()}" name="depId">
                        <input class="w3-button w3-blue w3-center
                               w3-round-large w3-hover-red w3-border
                               w3-border-blue w3-hover-border-grey"
                               type="submit" value="Edit" name="action">
                    </form>

                </td>
                <td class="w3-container w3-center">
                    <form method="post" class="w3-center">
                        <input type="hidden" value="${department.getDepName()}" name="depName">
                        <input type="hidden" value="${department.getDepID()}" name="depId">
                        <input class="w3-button w3-blue
                               w3-round-large w3-hover-red w3-border
                               w3-border-blue w3-hover-border-grey"
                               type="submit" value="Delete" name="action">
                    </form>
                </td>
            </tr>
        </c:forEach>
        </table>
        
<%--        <%--%>
<%--            List<Department> deps = (List<Department>) request.getAttribute("deps");--%>

<%--            if (deps != null && !deps.isEmpty()) {--%>
<%--                out.println("<ul class=\"w3-ul\">");--%>
<%--                for (Department s : deps) {--%>
<%--                    out.println("<li class=\"w3-hover-sand\">" + s.getDepName() +--%>
<%--                            "<form method=\"post\" class=\"w3-selection w3-light-grey w3-padding\">" +--%>
<%--                            "<input type=\"hidden\" name=\"depId\" value=\"" + s.getDepID() + "\">" +--%>
<%--                            "<input type=\"hidden\" name=\"depName\" value=\"" + s.getDepName() + "\">" +--%>

<%--                            "<button name=\"remove\" type=\"submit\" " +--%>
<%--                            " class=\"w3-btn w3-pale-red w3-hover-red w3-round-large\">Remove</button></form>" +--%>
<%--                            "<button name=\"edit\" type=\"submit\" value=\"" + s.getDepID() + "\"" +--%>
<%--                            " class=\"w3-btn w3-pale-red w3-hover-red w3-round-large\">Edit</button></form>" +--%>
<%--                            "</li>");--%>
<%--                }--%>
<%--                out.println("</ul>");--%>

<%--            } else out.println("<div class=\"w3-panel w3-red w3-display-container w3-card-4 w3-round\">\n"--%>
<%--                    +--%>
<%--                    "   <span onclick=\"this.parentElement.style.display='none'\"\n" +--%>
<%--                    "   class=\"w3-button w3-margin-right w3-display-right w3-round-large w3-hover-red w3-border w3-border-red w3-hover-border-grey\">×</span>\n" +--%>
<%--                    "   <h5>There are no Departments yet!</h5>\n" +--%>
<%--                    "</div>");--%>
<%--        %>--%>
    </div>
</div>

<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='/'">Back to main</button>
</div>
</body>
</html>