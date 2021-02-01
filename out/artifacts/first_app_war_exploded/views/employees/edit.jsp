<%@ page import="java.util.List" %>
<%@ page import="app.entities.Department" %>
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
    <c:set  var="depid" value="${requestScope.depid}"/>
    <form method="post" action="${pageContext.request.contextPath}/employees/update">

        <input type="text" value="${depid}" name="depid"/>
        <input type="text" value="${employee.empID}" name="id"/>
        <p>&nbsp Name
        <input type="text" minlength="2" maxlength="32" pattern="[A-Za-z]{2,32}" value="${employee.name}" required name="name"/>
        </p>
        <p>&nbsp Family Name
        <input type="text" value="${employee.familyName}" name="familyname"/>
        </p>
        <p>&nbsp Email
        <input type="email" value="${employee.email}" name="email"/>
        </p>
        <p>&nbsp Date
        <input type="date" value="${employee.date}" name="date"/>
        </p>
        <p>&nbsp ZP
        <input type="number" min="10000" value="${employee.ZP}" name="zp"/>
        </p>
        <button type="submit">Submit</button>
    </form>
</div>
<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='../employees/list?id=${depid}'">Back to employees</button>
</div>
</body>
</html>
