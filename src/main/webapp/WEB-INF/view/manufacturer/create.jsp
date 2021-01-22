<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Create Manufacturer</title>
</head>
<body>
<h1>Provide details to create new manufacturer</h1>
<form method="post" action="${pageContext.request.contextPath}/manufacturers/create">
    please provide manufacturer's name: <input type="text" name="name"><br>
    please provide manufacturer's country: <input type="text" name="country"><br>
    <button type="submit">Create</button>
</form>
</body>
</html>
