<%--<%@ page import="java.util.List" %>--%>
<%--<%@ page import="app.entities.Department" %>--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Errors</title>
</head>
<div>
    <c:if test="${errors != null}">
        <span class="error">
          &nbsp Issue occured:  ${errors.name} <br><br>
        </span>
    </c:if>
</div>
<body>
<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='../departments/list'">Back to departments</button>
</div>
</body>
</html>
