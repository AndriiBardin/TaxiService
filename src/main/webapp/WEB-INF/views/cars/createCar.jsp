<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Create Car</title>
</head>
<body>
<h1>Provide details to create new car</h1>
<form method="post" action="${pageContext.request.contextPath}/cars/create">
    please provide car model: <input type="text" name="name">
    please provide car's manufacturer id: <input type="number" name="manufacturer_id">
    <button type="submit">Create</button>
</form>
</body>
</html>
