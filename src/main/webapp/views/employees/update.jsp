<%--@elvariable id="employee" type="app.dto.EmployeeDto"--%>
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
    <h1>${employee.empID eq null ? 'Add':'Edit'}</h1>
</div>

<div>
    <form method="post" action="${pageContext.request.contextPath}/employees/update">
        <input type="hidden" value="${employee.depID}" name="depid"/>
        <input type="hidden" value="${employee.empID}" name="id"/>

        <p id="label">
            <label>Name</label>
            <input type="text" minlength="2" maxlength="32" pattern="[A-Za-z]{2,32}" value="${employee.name}" required
                   name="name"/>
        </p>
        <c:if test="${validationReport.errors['name'] != null}">
            <div class="error" style="color:#ff0000">${validationReport.errors['name'].error}</div>
        </c:if>

        <p id="label">
            <label>Family Name</label>
            <input type="text" value="${employee.familyName}" name="familyname"/>
        </p>
        <c:if test="${validationReport.errors['familyName'] != null}">
            <div class="error" style="color:#ff0000">${validationReport.errors['familyName'].error}</div>
        </c:if>

        <p id="label">
            <label>Email</label>
            <input type="email" value="${employee.email}" name="email"/>
        </p>
        <c:if test="${validationReport.errors['email'] != null}">
            <div class="error" style="color:#ff0000">${validationReport.errors['email'].error}</div>
        </c:if>
        <c:if test="${samepers != null}">
            <div class="error" style="color:#ff0000">${samepers}</div>
        </c:if>


        <p id="label">
            <label>Date</label>
            <input type="date" value="${employee.date}" name="date"/>
        </p>
        <c:if test="${validationReport.errors['date'] != null}">
            <div class="error" style="color:#ff0000">${validationReport.errors['date'].error}</div>
        </c:if>

        <p id="label">
            <label>ZP (min=10000)</label>
            <input type="number" min="10000" max="9999999" value="${employee.ZP}" name="zp"/>
        </p>
        <c:if test="${validationReport.errors['zp'] != null}">
            <div class="error" style="color:#ff0000">${validationReport.errors['zp'].error}</div>
        </c:if>

        <button style="margin-left: 10px" type="submit">Submit</button>
    </form>
</div>

<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='../employees/list?depid=${employee.depID}'">Back to
        employees
    </button>
</div>
</body>
</html>
