<%@ page import="java.util.List" %>
<%@ page import="app.entities.Employee" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Edit</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>

<body class="w3-light-grey">
<div class="w3-container w3-blue-grey w3-opacity w3-right-align">
    <h1>${employee eq null ? 'Add':'Edit'}</h1>
</div>


<div>
    <form method="post" action="${pageContext.request.contextPath}/employees/update">
        <input type="hidden" value="${employee.empID}" name="id"/>
        <input type="text" value="${employees.empName}" name="name"/>
        <button type="submit">Submit</button>
    </form>
</div>



</body>
</html>
