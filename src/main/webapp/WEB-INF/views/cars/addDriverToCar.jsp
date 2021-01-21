<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Add driver to car</title>
</head>
<body>
<h1> Input driver id and car id you want to pair</h1>
<form method="post" action="${pageContext.request.contextPath}/cars/drivers/add">
    Driver ID<input type="number" name="driver_id">
    Car ID<input type="number" name="car_id">
    <button type="submit">Add driver</button>
</form>
</body>
</html>
