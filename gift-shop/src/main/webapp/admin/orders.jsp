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
    <%@include file="../components/allCss.jsp" %>
    <link rel="stylesheet" href="../components/style.css">
</head>
<body>
<c:if test="${empty user}">
    <c:redirect url="../login.jsp"/>
</c:if>
<div class="wrapper">
    <%@include file="navbar.jsp" %>

    <c:if test="${not empty successMsg}">
        <h5 class="text-center text-success">${successMsg}</h5>
        <c:remove var="successMsg" scope="session"/>
    </c:if>

    <c:if test="${not empty failedMsg}">
        <h5 class="text-center text-danger">${failedMsg}</h5>
        <c:remove var="failedMsg" scope="session"/>
    </c:if>
    <div class="content">
        <table class="table table-striped">
            <thead class="bg-primary text--custom">
            <tr>
                <th scope="col">Id</th>
                <th scope="col">Ім'я</th>
                <th scope="col">Email</th>
                <th scope="col">Телефон</th>
                <th scope="col">Адреса</th>
                <th scope="col" style="width: 40%">Товари</th>
                <th scope="col">Ціна</th>
                <th scope="col">Вага</th>
                <th scope="col">Спосіб оплати</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <% OrdersDAOImpl dao = new OrdersDAOImpl(DBConnect.getConn());
                List<Orders> ordersList = dao.getOrders();
                for (Orders order : ordersList) { %>
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
                <td>
                    <a href="edit_order.jsp?id=<%=order.getOrderId() %>" class="btn btn-sm btn-primary"><i
                            class="fa-solid fa-pen-to-square"></i> </a>
                    <a href="../delete_order?id=<%=order.getOrderId() %>" class="btn btn-sm btn-danger"><i
                            class="fa-solid fa-trash"></i> </a>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>

    </div>

    <%@include file="../components/footer.jsp" %>
</div>

</body>
</html>
