<%--<%@ page import="java.util.List" %>--%>
<%--<%@ page import="app.entities.Department" %>--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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

    <a href="${pageContext.request.contextPath}/departments/department"
       class="w3-button w3-blue w3-center w3-round-large w3-hover-red w3-border w3-border-blue w3-hover-border-grey">Add</a>

    <div class="w3-card-4">
        <div class="w3-container w3-light-blue">
            <h2>Departments</h2>
        </div>

        <c:if test="${errors != null}">
        <span class="error" style="color:#ff0000">>
          &nbsp Issue occured:  ${errors.name} <br><br>
        </span>
        </c:if>

        <c:set var="newDepName" value="${requestScope.newDepName}"/>
        <c:if test="${newDepName != null}">
            <div class="w3-panel w3-green w3-display-container w3-card-4 w3-round">
                <span onclick="this.parentElement.style.display='none'"
                      class="w3-button w3-margin-right w3-display-right w3-round-large w3-hover-green w3-border w3-border-green w3-hover-border-grey">×</span>
                <h5>Department: <c:out value="${newDepName}"/> has been added successfully!</h5>
            </div>
        </c:if>

        <c:set var="editedDepName" value="${requestScope.editedDepName}"/>
        <c:if test="${editedDepName != null}">
            <div class="w3-panel w3-green w3-display-container w3-card-4 w3-round">
                <span onclick="this.parentElement.style.display='none'"
                      class="w3-button w3-margin-right w3-display-right w3-round-large w3-hover-green w3-border w3-border-green w3-hover-border-grey">×</span>
                <h5>Department: <c:out value="${editedDepName}"/> has been edited successfully!</h5>
            </div>
        </c:if>

        <c:set var="department" value="${requestScope.department}"/>
        <c:if test="${department.depName != null}">
            <div class="w3-panel w3-green w3-display-container w3-card-4 w3-round">
                <span onclick="this.parentElement.style.display='none'"
                      class="w3-button w3-margin-right w3-display-right w3-round-large w3-hover-green w3-border w3-border-green w3-hover-border-grey">×</span>
                <h5>Department: <c:out value="${department.depName}"/> has been removed successfully!</h5>
            </div>
        </c:if>

        <table>
            <c:forEach var="department" items="${requestScope.deps}">
                <tr class="w3-panel w3-lightblue w3-card-4 w3-round">
                    <td class="w3-container w3-center w3-green w3-round-large w3-hover-blue w3-border w3-border-red">
                            ${department.depName}
                    </td>

                    <td class="w3-container w3-center">
                        <a href="${pageContext.request.contextPath}/employees/list?depid=${department.depID}">Employee
                            List</a>
                    </td>
                    <td class="w3-container w3-center">
                        <a href="${pageContext.request.contextPath}/departments/department?id=${department.depID}">Update</a>
                    </td>
                    <td class="w3-container w3-center">
                        <a href="${pageContext.request.contextPath}/departments/delete?id=${department.depID}">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='../..'">Back to main</button>
</div>
</body>
</html>