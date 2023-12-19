<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 17/12/2023
  Time: 21:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Forget Password</title>
</head>
<body>
<%
    String email = request.getParameter("username");
    if (email == null) {
        email = "";
    }
%>
<h1>Forgot Email/Password</h1>
<a href="${pageContext.request.contextPath}/login">Sign in</a>
<form action="${pageContext.request.contextPath}/login/formValidate" method="post">
    <p>How would you like to reset your password</p>
    <label>
        <input type="radio" name="form" value="email">Email
    </label><br><br>
    <label>
        <input type="radio" name="form" value="sms">Text Message(SMS)
    </label>
    <p>We will email you with instructions on how to reset password.</p>
    <label>
        <input type="email" size="100" name="emailAddress" placeholder="name@example.com" value="<%=email%>"><br><br>
    </label>
    <input type="submit" value="Email Me">
</form>

</body>
</html>
