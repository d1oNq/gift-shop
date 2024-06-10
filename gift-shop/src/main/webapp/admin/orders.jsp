<%@ page import="com.giftshop.entity.User" %>
<%@ page import="com.giftshop.db.DBConnect" %>
<%@ page import="com.giftshop.dao.OrdersDAOImpl" %>
<%@ page import="com.giftshop.entity.Orders" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>
<html>
<head>
    <title>Orders</title>
    <%@include file="../components/allCss.jsp" %>
    <link rel="stylesheet" href="../components/style.css">
</head>
<body>
<c:if test="${empty user}">
    <c:redirect url="../login.jsp"/>
</c:if>

<%@include file="navbar.jsp" %>

<table class="table table-striped">
    <thead class="bg-primary text--custom">
    <tr>
        <th scope="col">Id</th>
        <th scope="col">User Name</th>
        <th scope="col">Email</th>
        <th scope="col">Phone</th>
        <th scope="col">Address</th>
        <th scope="col">Product names</th>
        <th scope="col">Price</th>
        <th scope="col">Weight</th>
        <th scope="col">Payment type</th>
    </tr>
    </thead>
    <tbody>
    <%
        OrdersDAOImpl dao = new OrdersDAOImpl(DBConnect.getConn());
        List<Orders> ordersList = dao.getOrders();
        for (Orders order : ordersList) {
    %>
    <tr>
        <th><%=order.getId()%>
        </th>
        <td><%=order.getUserName()%>
        </td>
        <td><%=order.getEmail()%>
        </td>
        <td><%=order.getPhone()%>
        </td>
        <td><%=order.getAddress()%>
        </td>
        <td><%=order.getProductName()%>
        </td>
        <td><%=order.getPrice()%>
        </td>
        <td><%=order.getWeight()%>
        </td>
        <td><%=order.getPayment()%>
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
