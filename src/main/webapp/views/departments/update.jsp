<%--@elvariable id="department" type="app.dto.DepartmentDto"--%>
<%--@elvariable id="validationReport" type="app.dto.ValidationReport"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Edit</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <style>
        label {
            margin-left: 10px;
        }

        #label {
            text-align: left;
            color: darkblue;
        }
    </style>
</head>

<body class="w3-light-grey">
<div class="w3-container w3-blue-grey w3-opacity w3-right-align">
    <h1>${department.depID eq null ? 'Add':'Edit'}</h1>
</div>

<div>
    <form method="post" action="${pageContext.request.contextPath}/departments/update">
        <input type="hidden" value="${department.depID}" name="id"/>
        <p id="label">
            <label>Department name:</label>
            <input type="text" minlength="2" maxlength="32" pattern="[A-Za-z]{2,32}" value="${department.depName}"
                   required name="name"/>
        </p>
        <c:if test="${samename != null}">
            <div class="error" style="color:#ff0000">${samename}</div>
        </c:if>

        <c:if test="${validationReport.errors['name'] != null}">
            <div class="error" style="color:#ff0000">${validationReport.errors['name'].error}</div>
        </c:if>

        <button style="margin-left: 10px" type="submit">Submit</button>
    </form>
</div>

<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='../departments/list'">Back to departments</button>
</div>
</body>
</html>
