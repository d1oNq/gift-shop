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
    <title>Замовлення</title>
    <%@include file="components/allCss.jsp" %>
</head>
<body>
<c:if test="${empty user}">
    <c:redirect url="login.jsp"/>
</c:if>
<div class="wrapper">
    <%@include file="components/navbar.jsp" %>

    <div class="content">
        <h3 class="text-center mt-4 mb-4">Мої Замовлення</h3>
        <table class="table table-striped">
            <thead class="bg-primary text-white">
            <tr>
                <th scope="col">Id</th>
                <th scope="col">Ім'я</th>
                <th scope="col">Електронна пошта</th>
                <th scope="col">Телефон</th>
                <th scope="col">Адреса</th>
                <th scope="col" style="width: 40%">Товари</th>
                <th scope="col">Ціна</th>
                <th scope="col">Вага</th>
                <th scope="col">Спосіб Оплати</th>
            </tr>
            </thead>
            <tbody>
            <%
                User user = (User) session.getAttribute("user");
                OrdersDAOImpl dao = new OrdersDAOImpl(DBConnect.getConn());
                List<Orders> ordersList = dao.getOrders(user.getEmail());
                for (Orders order : ordersList) {
            %>
            <tr>
                <th><%=order.getOrderId()%>
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
    </div>

    <%@include file="components/footer.jsp" %>
</div>

</body>
</html>
