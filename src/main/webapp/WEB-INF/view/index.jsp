<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Taxi Application</title>
</head>
<body>
<h1>Taxi Application</h1>

<a href="${pageContext.request.contextPath}/cars">Display all cars </a><br>
<a href="${pageContext.request.contextPath}/cars/create">Create new car</a><br>
<a href="${pageContext.request.contextPath}/cars/driver/add">Add driver to car</a><br>
<a href="${pageContext.request.contextPath}/drivers">Display all drivers</a><br>
<a href="${pageContext.request.contextPath}/drivers/create">Create new driver</a><br>
<a href="${pageContext.request.contextPath}/manufacturers">Display all manufacturers</a><br>
<a href="${pageContext.request.contextPath}/manufacturers/create">Create new manufacturer</a><br>

</body>
</html>
