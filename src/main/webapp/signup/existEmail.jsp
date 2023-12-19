<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 18/12/2023
  Time: 23:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Netflix</title>
</head>
<body>
<a href="">Sign Out</a>
<p>Step 1 of 3</p>
<h1>Account Created</h1>
<p>Use this email to access your account</p>
<br>
<p>${email}</p>
<br><br>
<form action="/signup/regform" method = "post">
    <input type="submit" value="Next">
</form>
</body>
</html>
