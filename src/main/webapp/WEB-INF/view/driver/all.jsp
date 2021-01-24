<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>All drivers</title>
</head>
<body>
<h1>All Drivers Page</h1>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Licence</th>
        <th>Login</th>
    </tr>
    <c:forEach var="driver" items="${drivers}">
        <tr>
            <td>
                <c:out value="${driver.id}"/>
            </td>
            <td>
                <c:out value="${driver.name}"/>
            </td>
            <td>
                <c:out value="${driver.licenceNumber}"/>
            </td>
            <td>
                <c:out value="${driver.login}"/>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>