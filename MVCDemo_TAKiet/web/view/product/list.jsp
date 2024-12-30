<%@page import="entities.Product"%>
<%@page import="entities.Category"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product Page</title>
    </head>
    <body>
        <!-- Search by category(drop down) and name (text box) - Add -->
        <h1>Product Page</h1>
        <br>

        <!--SEARCH FORM-->
        <form action="Product" method="GET">
            <label>Product Name:</label>
            <input type="text" name="productName" value="${requestScope.productName}">

            <label>Category:</label>
            <select name="category" id="category">
                <%
                    List<Category> categories = (List<Category>) request.getAttribute("categories");
                    if (categories != null) {
                        for (Category category : categories) {
                %>
                <option value="<%= category.getId()%>"><%= category.getName()%></option>
                <%
                        }
                    }
                %>
                <option value="<%=""%>"><%= "All categories"%></option>
            </select>

            <label>Price: </label>
            <select name="priceCategory" id="priceCategory">
                <option value="<%=""%>"><%= "All priceCategories"%></option>
                <option value="<%="1"%>"><%= "above 1.000"%></option>
                <option value="<%="2"%>"><%= "500-1.000"%></option>
                <option value="<%="3"%>"><%= "200-500"%></option>
                <option value="<%="4"%>"><%= "100-200"%></option>
                <option value="<%="5"%>"><%= "50-100"%></option>
                <option value="<%="6"%>"><%= "below 50"%></option>
            </select>
            <input type="submit" value="search">
        </form>

        <br>

        <!--PRODUCT LIST-->
        <%
            List<Product> products = (List<Product>) request.getAttribute("products");
            if (products == null) {
                String msg = (String) request.getAttribute("msg");
        %>
        <h3><%= msg%></h3>
        <%
        } else {
            int count = 1;
        %>
        <table>
            <tr>
                <th>No. </th>
                <th>Product Name</th>
                <th>Price</th>
                <th>Product Year</th>
                <th>Image</th>
                <th>Category</th>
                <th>Update</th> 
                <th>Delete</th> 
                

            </tr>
            <%
                for (Product p : products) {
            %>
            <tr>
                <td><%= count++%></td>
                <td><%= p.getName()%></td>
                <td><%= p.getPrice()%></td>
                <td><%= p.getProductYear()%></td>
                <td><img src="<%= p.getImage()%>" alt="product image" width="100" height="100"></td>
                <td><%= p.getCategory().getName()%></td>
                <td>
                    <form action="Product" method="GET">
                        <input type="hidden" name="action" value="prepare-update">
                        <input type="hidden" name="productId" value="<%= p.getId()%>">
                        <button type="submit">Edit</button>
                    </form>
                </td> 
                <td>
                    <form action="Product" method="POST">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="productId" value="<%= p.getId()%>">
                        <button type="submit">Delete</button>
                    </form>
                </td>
            </tr>
            <%
                }
            %>
        </table>
        <%
            }
        %>
        <br>
        <form action="Product" method="GET">
            <input type="hidden" name="action" value="prepare-add">
            <button type="submit">Add</button>
        </form>

        <!-- JS -->
        <script>
            const selectedCategoryId = '<%= request.getAttribute("category")%>';
            const selectElement = document.getElementById('category');

            if (selectedCategoryId) {
                selectElement.value = selectedCategoryId;
            } else {
                selectElement.value = "";
            }
        </script>

    </body>
</html>
