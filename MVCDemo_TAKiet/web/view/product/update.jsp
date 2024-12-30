<%-- 
    Document   : update
    Created on : Dec 30, 2024, 3:16:44 PM
    Author     : kiet
--%>
<%@page import="entities.Product"%>
<%@page import="java.util.List"%>
<%@page import="entities.Category"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Product</title>
    </head>
    <body>
        <h1>Update Product</h1>
        <%
            String error = (String) request.getAttribute("msg");
            Product product = (Product) request.getAttribute("product");
        %>
        <h1>Login Page</h1> 
        <%
            if (error != null) {
        %>
        <p><%=error%></p>
        <%
            }
        %>
        <form action="Product" method="POST">
            <input type="hidden" name="productId" value="${product.getId()}">

            <label for="name">Product Name:</label>
            <input type="text" id="name" name="name" value="${product.name} " required><br><br>

            <label for="price">Price:</label>
            <input type="number" id="price" name="price" step="0.01" value="${product.price}" required><br><br>

            <label for="productYear">Product Year:</label>
            <input type="number" id="productYear" name="productYear" value="${product.productYear}" required><br><br>

            <label for="image">Image URL:</label>
            <input type="text" id="image" name="image" value="${product.image}" ><br><br>

            <label for="category">Category:</label>
            <select id="category" name="category" required>
                <%
                    List<Category> categories = (List<Category>) request.getAttribute("categories");
                    if (categories != null) {
                        for (Category category : categories) {
                            String selected = (category.getId() == product.getCategory().getId()) ? "selected" : "";
                %>
                <option value="<%= category.getId()%>" <%= selected%>><%= category.getName()%></option>
                <%
                        }
                    }
                %>
            </select><br><br>
            <input type="hidden" name="action" value="update"> 
            <button type="submit">Update Product</button>
        </form>
    </body>
</html>
