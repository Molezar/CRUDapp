<%@ page import="java.util.List" %>
<%@ page import="app.entities.Department" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Edit</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>

<body class="w3-light-grey">
<div class="w3-container w3-blue-grey w3-opacity w3-right-align">
    <h1>${department eq null ? 'Add':'Edit'}</h1>
</div>

<div>
    <form method="post" action="${pageContext.request.contextPath}/departments/update">
        <input type="hidden" value="${department.depID}" name="id"/>
        <p>&nbsp Department name:
        <input type="text" value="${department.depName}" name="name"/>
        </p>

<%--        <c:set var="newDepName" value="${requestScope.newDepName}"/>--%>
        <c:if test="${errors != null}">
        <span class="error">
          &nbsp Issue occured:  ${errors.name} <br><br>
        </span>
        </c:if>

        <button type="submit">Submit</button>
    </form>
</div>
<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='../departments/list'">Back to departments</button>
</div>
</body>
</html>
