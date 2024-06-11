<%@ page import="com.giftshop.dao.ProductDAOImpl" %>
<%@ page import="com.giftshop.db.DBConnect" %>
<%@ page import="com.giftshop.entity.Product" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>

<html>
<head>
    <title>Усі товари</title>
    <%@include file="../components/allCss.jsp" %>
    <link rel="stylesheet" href="../components/style.css">
</head>
<body>
<%@include file="navbar.jsp" %>

<c:if test="${empty user}">
    <c:redirect url="../login.jsp"/>
</c:if>

<c:if test="${not empty successMsg}">
    <h5 class="text-center text-success">${successMsg}</h5>
    <c:remove var="successMsg" scope="session"/>
</c:if>

<c:if test="${not empty failedMsg}">
    <h5 class="text-center text-danger">${failedMsg}</h5>
    <c:remove var="failedMsg" scope="session"/>
</c:if>
<table class="table table-striped">
    <thead class="bg-primary text-white">
    <tr>
        <th scope="col">Id</th>
        <th scope="col">Фото</th>
        <th scope="col">Назва</th>
        <th scope="col">Категорія</th>
        <th scope="col">Вага</th>
        <th scope="col">Ціна</th>
        <th scope="col">Статус</th>
        <th scope="col"></th>
    </tr>
    </thead>
    <tbody>
    <%
        ProductDAOImpl dao = new ProductDAOImpl(DBConnect.getConn());
        List<Product> allProduct = dao.getAllProduct();
        for (Product product : allProduct) {
    %>
    <tr>
        <td><%= product.getProductId() %></td>
        <td><img src="../product/<%= product.getPhoto() %>" width="70px"></td>
        <td><%= product.getProductName() %></td>
        <td><%= product.getCategory() %></td>
        <td><%= product.getWeight() %></td>
        <td><%= product.getPrice() %></td>
        <td><%= product.getStatus() %></td>
        <td>
            <a href="edit_product.jsp?id=<%=product.getProductId() %>" class="btn btn-sm btn-primary"><i class="fa-solid fa-pen-to-square"></i> </a>
            <a href="../delete?id=<%=product.getProductId() %>" class="btn btn-sm btn-danger"><i class="fa-solid fa-trash"></i> </a>
        </td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>

<%@include file="../components/footer.jsp" %>
</body>
</html>
