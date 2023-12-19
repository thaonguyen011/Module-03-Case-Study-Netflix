<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 14/12/2023
  Time: 23:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Netflix</title>
</head>
<body>
<%
  String phoneNumber = (String) session.getAttribute("phone");
  if (phoneNumber == null) {
    phoneNumber="";
  }
%>
<h1>Mobile Wallet Option</h1>
<form action="/signup/mobileWalletOption" method="post">
  <label>
    <input type="text" name="phone" id="phone" placeholder="Mobile number"  onchange="save()" >
  </label>
  <p>Plan: ${plan}<a href="/signup/editPlan"> Change</a></p>
  <label>
    <input type="checkbox" name="agree" value="agree" required>
  </label>I agree
  <input type="submit" value="Submit">
</form>

<script>
  function save() {
    localStorage.setItem("phone", document.getElementById("phone").value);
    console.log("save");
    console.log(localStorage.getItem("phone"));
  }


  document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("phone").value = localStorage.getItem("phone");
  })
</script>
</body>
</html>
