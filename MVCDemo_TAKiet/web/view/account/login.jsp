<%-- 
    Document   : login
    Created on : Dec 26, 2024, 10:47:50 PM
    Author     : vothimaihoa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title> 
    </head>
    <body>
        <%
            String error = (String) request.getAttribute("error");
        %>
        <h1>Login Page</h1> 
        <%
            if (error != null) {
        %>
        <p><%=error%></p>
        <%
            }
        %>
        <form action="Auth" method="POST">

            <label>Username: </label>
            <input type="text" name="username">

            <label>Password: </label>
            <input type="password" name="password">

            <input type="submit" value="Login">
        </form>
    </body>
</html>
