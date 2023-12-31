<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 14/12/2023
  Time: 09:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Netflix</title>
</head>
<body>
<h1>reg form</h1>
<h1>Step 1 of 3</h1>
<h1>Create a password to start your membership</h1>
<p>Just a few more steps and you're done!</p>
<p>We hate paperwork, too.</p>
<form id="myForm" action="${pageContext.request.contextPath}/signup/regform" method="post">
    <label>
        <input type="text" name="email" placeholder="Enter email" value="${email}">
    </label><br><br>
    <c:if test="${existEmail == true}">
        <p style="color: red">Email exist</p>
    </c:if>
    <c:if test="${emailCheck == false}">
        <p style="color:red">Wrong email syntax</p>
    </c:if>
    <label>
        <input type="password" name="password" id="password" placeholder="Enter password" oninput="check();change()">
    </label>
    <p id="check" style="color: red; display: none">Enter pass between 6 and 60 characters</p>
    <label>
        <input type="checkbox" name="notSendOffers" value="true"> Please do not email me Netflix special offers.
    </label><br><br>
    <input type="submit" value="Next">
</form>

<script>
    function check1() {
        let pass = document.getElementById("password").value;

        if (pass.length > 0 && (pass.length < 6 || pass.length > 60)) {
            document.getElementById("check").style.display = "block";
        }
    }

    function check() {
        setTimeout(check1, 1000);
    }

    function change() {
        document.getElementById("check").style.display = "none";
    }

    function handleSubmit(event) {
        event.preventDefault();
        check();
        let pass = document.getElementById("password").value;
        if (pass.length >= 6 && pass.length <= 60) {
            event.target.submit();
        }
    }

    let myForm = document.getElementById("myForm");

    myForm.addEventListener("submit", handleSubmit);


</script>
</body>
</html>
