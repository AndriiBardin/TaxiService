<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Create Driver</title>
</head>
<body>
<h1>Provide details to create new driver</h1>
<form method="post" action="${pageContext.request.contextPath}/drivers/create">
    please provide driver's name: <input type="text" name="name"><br>
    please provide driver's licence: <input type="text" name="licence"><br>
    <button type="submit">Create</button>
</form>
</body>
</html>
