<%@ page import="com.giftshop.dao.CartDAOImpl" %>
<%@ page import="com.giftshop.db.DBConnect" %>
<%@ page import="com.giftshop.entity.User" %>
<%@ page import="com.giftshop.entity.Cart" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>

<html>
<head>
    <title>Кошик</title>
    <%@include file="components/allCss.jsp" %>
</head>
<body>
<div class="wrapper">
    <%@include file="components/navbar.jsp" %>

    <c:if test="${empty user}">
        <c:redirect url="login.jsp"/>
    </c:if>

    <c:if test="${not empty successMsg}">
        <div class="alert alert-success text-center" role="alert" style="font-size: 18px; font-weight: 700;">
                ${successMsg}
        </div>
        <c:remove var="successMsg" scope="session"/>
    </c:if>

    <c:if test="${not empty failedMsg}">
        <div class="alert alert-danger text-center" role="alert" style="font-size: 18px; font-weight: 700;">
                ${failedMsg}
        </div>
        <c:remove var="failedMsg" scope="session"/>
    </c:if>

    <div class="container-fluid content">
        <div class="row p-4">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <h3 class="text-center text-success">Список товарів</h3>
                        <table class="table">
                            <thead>
                            <tr>
                                <th scope="col">Назва</th>
                                <th scope="col">Категорія</th>
                                <th scope="col">Вага</th>
                                <th scope="col">Ціна</th>
                                <th scope="col"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <%
                                User user = (User) session.getAttribute("user");

                                CartDAOImpl dao = new CartDAOImpl(DBConnect.getConn());
                                List<Cart> cartList = dao.getProductByUser(user.getId());

                                Double totalWeight = 0.0;
                                Double totalPrice = 0.0;
                                DecimalFormat df = new DecimalFormat("#0.00");

                                for (Cart cart : cartList) {
                                    totalWeight += cart.getWeight();
                                    totalPrice += cart.getPrice();
                            %>
                            <tr>
                                <th><%=cart.getProductName()%>
                                </th>
                                <td><%=cart.getCategory()%>
                                </td>
                                <td><%=cart.getWeight()%>
                                </td>
                                <td><%=df.format(cart.getPrice())%>
                                </td>
                                <td>
                                    <a class="btn btn-sm btn-danger"
                                       href="remove?productId=<%=cart.getProductId()%>&&userId=<%=cart.getUserId()%>&&cartId=<%=cart.getCartId()%>">Видалити</a>
                                </td>
                            </tr>
                            <%
                                }
                            %>
                            </tbody>
                            <tfoot>
                            <tr>
                                <th></th>
                                <th></th>
                                <th>Вага: <%=df.format(totalWeight)%> г</th>
                                <th>Сума: <%=df.format(totalPrice)%> грн.</th>
                                <th></th>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>

            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <h3 class="text-center text-success">Деталі Замовлення</h3>
                        <form action="order" method="post">
                            <div class="form-row">
                                <input type="hidden" value="<%=user.getId()%>" name="id">
                                <div class="form-group col-md-12">
                                    <label for="inputName">Повне Ім'я</label>
                                    <input type="text" class="form-control" id="inputName" placeholder="Ім'я"
                                           name="name" required="required"
                                           value="<%= user.getName() %>">
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label for="inputEmail">Електронна пошта</label>
                                    <input type="email" class="form-control" id="inputEmail" name="email"
                                           required="required"
                                           value="<%= user.getEmail() %>" readonly>
                                </div>
                                <div class="form-group col-md-6">
                                    <label for="inputPhone">Телефон</label>
                                    <input type="tel" class="form-control" id="inputPhone" placeholder="Телефон"
                                           name="phone" required="required"
                                           value="<%= user.getPhone() != null ? user.getPhone() : "" %>">
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label for="inputAddress">Адреса</label>
                                    <input type="text" class="form-control" id="inputAddress" placeholder="Адреса"
                                           name="address" required="required"
                                           value="<%= user.getAddress() != null ? user.getAddress() : "" %>">
                                </div>
                                <div class="form-group col-md-4">
                                    <label for="inputCity">Місто</label>
                                    <input type="text" class="form-control" id="inputCity" placeholder="Місто"
                                           name="city" required="required"
                                           value="<%= user.getCity() != null ? user.getCity() : "" %>">
                                </div>
                                <div class="form-group col-md-2">
                                    <label for="inputZip">Поштовий Індекс</label>
                                    <input type="text" class="form-control" id="inputZip" placeholder="Індекс"
                                           name="zip" required="required"
                                           value="<%= user.getZip() != null ? user.getZip() : "" %>">
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="form-group col-md-12">
                                    <label for="selectPayment">Спосіб Оплати</label>
                                    <select id="selectPayment" class="form-control" name="payment">
                                        <option selected value="no-select">Виберіть спосіб оплати</option>
                                        <option value="Готівка">Готівкою при отримані</option>
                                        <option value="Карта">На карту</option>
                                    </select>
                                </div>
                            </div>
                            <div class="text-center">
                                <button class="btn btn-primary">Оформити Замовлення</button>
                                <a class="btn btn-success" href="index.jsp">Продовжити Покупки</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%@include file="components/footer.jsp" %>
</div>

</body>
</html>
