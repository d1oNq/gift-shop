<%@ page import="com.giftshop.dao.OrdersDAOImpl" %>
<%@ page import="com.giftshop.db.DBConnect" %>
<%@ page import="com.giftshop.entity.Orders" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Редагувати замовлення</title>
    <%@ include file="../components/allCss.jsp" %>
    <link rel="stylesheet" href="../components/style.css">
    <style>
        textarea {
            resize: none;
        }

        .form-group {
            position: relative;
        }

        .toggle-button {
            position: absolute;
            top: 5px;
            right: 10px;
            cursor: pointer;
        }
    </style>
    <script>
        document.addEventListener('DOMContentLoaded', function () {
            const textarea = document.getElementById('areaProductName');
            const toggleButton = document.getElementById('toggleButton');
            let isExpanded = false;

            function autoResizeTextarea(textarea) {
                textarea.style.height = 'auto';
                textarea.style.height = textarea.scrollHeight + 'px';
            }

            textarea.style.height = '100px';
            textarea.style.overflowY = 'scroll';

            toggleButton.addEventListener('click', function () {
                if (isExpanded) {
                    textarea.style.height = '100px';
                    textarea.style.overflowY = 'scroll';
                    toggleButton.textContent = 'Розгорнути';
                } else {
                    textarea.style.overflowY = 'hidden';
                    autoResizeTextarea(textarea);
                    toggleButton.textContent = 'Згорнути';
                }
                isExpanded = !isExpanded;
            });
        });
    </script>
</head>
<body>
<div class="wrapper">
    <%@ include file="navbar.jsp" %>

    <c:if test="${empty user}">
        <c:redirect url="../login.jsp"/>
    </c:if>

    <div class="container p-2 content">
        <div class="row">
            <div class="col-md-4 offset-md-4">
                <div class="card">
                    <div class="card-body">
                        <h3 class="text-center">Редагувати Замовлення</h3>
                        <%
                            int orderId = Integer.parseInt(request.getParameter("id"));
                            OrdersDAOImpl dao = new OrdersDAOImpl(DBConnect.getConn());
                            Orders order = dao.getOrderById(orderId);
                        %>

                        <form class="mt-4" action="../edit_order" method="post">
                            <input type="hidden" name="orderId" value="<%=order.getOrderId()%>">
                            <div class="form-group">
                                <label for="inputUserName">Ім'я користувача</label>
                                <input type="text" class="form-control" id="inputUserName"
                                       required="required" placeholder="Ім'я користувача" name="userName"
                                       value="<%=order.getUserName() %>">
                            </div>
                            <div class="form-group">
                                <label for="inputEmail">Електронна пошта</label>
                                <input type="email" class="form-control" id="inputEmail"
                                       required="required" placeholder="Електронна пошта" name="email"
                                       value="<%=order.getEmail() %>">
                            </div>
                            <div class="form-group">
                                <label for="inputPhone">Телефон</label>
                                <input type="text" class="form-control" id="inputPhone"
                                       required="required" placeholder="Телефон" name="phone"
                                       value="<%=order.getPhone() %>">
                            </div>
                            <div class="form-group">
                                <label for="inputAddress">Адреса</label>
                                <input type="text" class="form-control" id="inputAddress"
                                       required="required" placeholder="Адреса" name="address"
                                       value="<%=order.getAddress() %>">
                            </div>
                            <div class="form-group">
                                <label for="areaProductName">Товари</label>
                                <span id="toggleButton" class="toggle-button">Розгорнути</span>
                                <textarea class="form-control" id="areaProductName" required="required"
                                          placeholder="Назва продукту"
                                          name="productName"><%=order.getProductName() %>
                                </textarea>
                            </div>
                            <div class="form-group">
                                <label for="inputPrice">Ціна</label>
                                <input type="text" class="form-control" id="inputPrice"
                                       required="required" placeholder="Ціна" name="price"
                                       value="<%=order.getPrice() %>">
                            </div>
                            <div class="form-group">
                                <label for="inputWeight">Вага</label>
                                <input type="text" class="form-control" id="inputWeight"
                                       required="required" placeholder="Вага" name="weight"
                                       value="<%=order.getWeight() %>">
                            </div>
                            <div class="form-group">
                                <label for="selectPayment">Спосіб Оплати</label>
                                <select class="form-control" id="selectPayment"
                                        required="required" name="payment">
                                    <%
                                        if ("Готівка".equals(order.getPayment())) {
                                    %>
                                    <option value="Готівка" selected>Готівкою при отримані</option>
                                    <option value="Карта">На карту</option>
                                    <%
                                    } else {
                                    %>
                                    <option value="Карта" selected>На карту</option>
                                    <option value="Готівка">Готівкою при отримані</option>
                                    <%
                                        }
                                    %>
                                </select>
                            </div>
                            <div class="text-center mt-3">
                                <button type="submit" class="btn btn-primary">Оновити</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%@ include file="../components/footer.jsp" %>
</div>

</body>
</html>
