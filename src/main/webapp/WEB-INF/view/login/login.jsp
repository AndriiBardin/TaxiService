<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>Login page</h1>
<h3 style="color: crimson">${message}</h3>
<form action="${pageContext.request.contextPath}/login" method="post">
    Enter your login: <input type="text" name="login">
    Enter your password: <input type="password" name="password">
    <button type="submit">Log in</button>
</form>
<a href="${pageContext.request.contextPath}/drivers/create">Create new driver</a><br>
</body>
</html>
