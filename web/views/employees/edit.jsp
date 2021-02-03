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
    <c:set  var="id" value="${requestScope.id}"/>


    <form method="post" action="${pageContext.request.contextPath}/employees/update">

        <input type="hidden" value="${depid}" name="depid"/>
        <input type="hidden" value="${id}" name="id"/>

        <p>&nbsp Name
        <input type="text" minlength="2" maxlength="32" pattern="[A-Za-z]{2,32}" value="${employee.name}" required name="name"/>
        </p>
        <c:if test="${errors.name != null}">
        <span class="error" style="color:#ff0000">>
          &nbsp Issue occured:  ${errors.name} <br><br>
        </span>
        </c:if>

        <p>&nbsp Family Name
        <input type="text" value="${employee.familyName}" name="familyname"/>
        </p>
        <c:if test="${errors.familyName != null}">
        <span class="error" style="color:#ff0000">>
          &nbsp Issue occured:  ${errors.familyName} <br><br>
        </span>
        </c:if>

        <p>&nbsp Email
        <input type="email" value="${employee.email}" name="email"/>
        </p>
        <c:if test="${errors.email != null}">
        <span class="error" style="color:#ff0000">>
          &nbsp Issue occured:  ${errors.email} <br><br>
        </span>
        </c:if>

        <p>&nbsp Date
        <input type="date" value="${employee.date}" name="date"/>
        </p>
        <c:if test="${errors.date != null}">
        <span class="error" style="color:#ff0000">>
          &nbsp Issue occured:  ${errors.date} <br><br>
        </span>
        </c:if>

        <p>&nbsp ZP (min=10000)
        <input type="number" min="10000" max="9999999" value="${employee.ZP}" name="zp"/>
        </p>
        <c:if test="${errors.zp != null}">
        <span class="error" style="color:#ff0000">>
          &nbsp Issue occured:  ${errors.zp} <br><br>
        </span>
        </c:if>

        <button type="submit">Submit</button>
    </form>
</div>
<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='../employees/list?depid=${depid}'">Back to employees</button>
</div>
</body>
</html>
