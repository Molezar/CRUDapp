<%@ page import="java.util.List" %>
<%@ page import="app.entities.Department" %>
<%@ page import="app.entities.Employee" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Employee list</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>

<body class="w3-light-grey">
<div class="w3-container w3-blue-grey w3-opacity w3-right-align">
    <h1>Departments</h1>
</div>

<div class="w3-container w3-center w3-margin-bottom w3-padding">
    <c:set  var="department" value="${requestScope.dep}"/>
    <a href="${pageContext.request.contextPath}/employees/employee?depid=${department.depID}" class="w3-button w3-blue w3-center w3-round-large w3-hover-red w3-border w3-border-blue w3-hover-border-grey">Add</a>

    <div class="w3-card-4">
            <div class="w3-container w3-light-blue">

                <h2>Employees of <c:out value="${department.depName}"/></h2>
            </div>

        <table>
            <c:forEach var="employee" items="${requestScope.emps}">
                <tr class="w3-panel w3-lightblue w3-card-4 w3-round">
                    <td class="w3-container w3-center w3-green w3-round-large w3-hover-blue w3-border w3-border-red">
                            ${employee.name}
                    </td>

                    <td class="w3-container w3-center">
                        <a href="${pageContext.request.contextPath}/employees/employee?id=${employee.empID}&depid=${employee.depID}">Render Employee</a>
                    </td>
                    <td class="w3-container w3-center">
                        <a href="${pageContext.request.contextPath}/employees/employee?id=${employee.empID}&depid=${employee.depID}">Update</a>
                    </td>
                    <td class="w3-container w3-center">
                        <a href="${pageContext.request.contextPath}/employees/delete?id=${employee.empID}">Delete</a>

                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='../departments/list'">Back to departments</button>
</div>
</body>
</html>