<%@ page import="com.giftshop.db.DBConnect" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="com.giftshop.dao.ProductDAOImpl" %>
<%@ page import="com.giftshop.entity.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="com.giftshop.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Gift Shop</title>
    <%@include file="components/allCss.jsp" %>
</head>
<body>

<%
    User user = (User) session.getAttribute("user");
%>

<c:if test="${not empty cart}">
    <div id="snackbar">${cart}</div>

    <script type="text/javascript">
        showSnackbar();

        function showSnackbar() {
            // Get the snackbar DIV
            const x = document.getElementById("snackbar");

            // Add the "show" class to DIV
            x.className = "show";

            // After 3 seconds, remove the show class from DIV
            setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
        }
    </script>
    <c:remove var="cart" scope="session" />
</c:if>

<%@include file="components/navbar.jsp" %>

<div class="container-fluid bg-img">
    <div class="text-box text-center">
        <p class="text">Виберіть солодощі, які вам сподобаються і ми сформуємо з них подорунковий бокс</p>
        <p class="text">Також ви можете обрати уже готовий варіант</p>
    </div>
</div>

<div class="container text-center">
    <h2 class="text-center">Цукерки</h2>
    <div class="row">
        <%
            ProductDAOImpl dao = new ProductDAOImpl(DBConnect.getConn());
            List<Product> candies = dao.getCandy();
            for (Product product : candies) {
        %>
        <div class="col-md-3">
            <div class="card h-100 card--hover">
                <a href="product.jsp?id=<%=product.getProductId()%>">
                    <div class="card-body text-center">
                        <img src="product/<%=product.getPhoto() %>" alt="" style="width: 150px; height: 150px;">
                        <p style="font-weight: 700;"><%=product.getProductName() %>, <%=product.getWeight() %>
                            г</p>
                        <p><%=product.getPrice()%> грн.</p>
                        <div class="text-center">
                            <%
                                if (user == null) {
                            %>
                            <a href="login.jsp" class="btn btn-outline-primary btn-sm">
                                <i class="fa-solid fa-cart-shopping"></i> Додати в кошик
                            </a>
                            <%
                            } else {
                            %>
                            <a  href="cart?productId=<%=product.getProductId()%>&&userId=<%=user.getId()%>" class="btn btn-outline-primary btn-sm">
                                <i class="fa-solid fa-cart-shopping"></i> Додати в кошик
                            </a>
                            <%
                                }
                            %>
                        </div>
                    </div>
                </a>
            </div>
        </div>
        <%
            }
        %>
    </div>
    <div class="text-center mt-3">
        <a href="all_candy.jsp" class="button btn btn-outline-dark dtn-sm">Переглянути Всі</a>
    </div>
</div>

<hr>

<div class="container text-center">
    <h2 class="text-center">Cookies</h2>
    <div class="row">
        <%
            List<Product> cookies = dao.getCookies();
            for (Product product : cookies) {
        %>
        <div class="col-md-3">
            <div class="card card--hover">
                <a href="product.jsp?id=<%=product.getProductId()%>">
                    <div class="card-body text-center">
                        <img src="product/<%=product.getPhoto() %>" alt="" style="width: 150px; height: 150px;">
                        <p style="font-weight: 700;"><%=product.getProductName() %>, <%=product.getWeight() %>
                            г</p>
                        <p><%=product.getPrice()%> грн.</p>
                        <div class="text-center">
                            <%
                                if (user == null) {
                            %>
                            <a href="login.jsp" class="btn btn-outline-primary btn-sm">
                                <i class="fa-solid fa-cart-shopping"></i> Додати в кошик
                            </a>
                            <%
                            } else {
                            %>
                            <a  href="cart?productId=<%=product.getProductId()%>&&userId=<%=user.getId()%>" class="btn btn-outline-primary btn-sm">
                                <i class="fa-solid fa-cart-shopping"></i> Додати в кошик
                            </a>
                            <%
                                }
                            %>
                        </div>
                    </div>
                </a>
            </div>
        </div>
        <%
            }
        %>
    </div>
    <div class="text-center mt-3">
        <a href="all_cookies.jsp" class="button btn btn-outline-dark dtn-sm">Переглянути Всі</a>
    </div>
</div>

<hr>

<div class="container text-center">
    <h2 class="text-center">Gift Boxes</h2>
    <div class="row">
        <%
            List<Product> giftBoxes = dao.getGiftBox();
            for (Product product : giftBoxes) {
        %>
        <div class="col-md-3">
            <div class="card card--hover">
                <a href="product.jsp?id=<%=product.getProductId()%>">
                    <div class="card-body text-center">
                        <img src="product/<%=product.getPhoto() %>" alt="" style="width: 150px; height: 150px;">
                        <p style="font-weight: 700;"><%=product.getProductName() %>, <%=product.getWeight() %>
                            г</p>
                        <p><%=product.getPrice()%> грн.</p>
                        <div class="text-center">
                            <%
                                if (user == null) {
                            %>
                            <a href="login.jsp" class="btn btn-outline-primary btn-sm">
                                <i class="fa-solid fa-cart-shopping"></i> Додати в кошик
                            </a>
                            <%
                            } else {
                            %>
                            <a  href="cart?productId=<%=product.getProductId()%>&&userId=<%=user.getId()%>" class="btn btn-outline-primary btn-sm">
                                <i class="fa-solid fa-cart-shopping"></i> Додати в кошик
                            </a>
                            <%
                                }
                            %>
                        </div>
                    </div>
                </a>
            </div>
        </div>
        <%
            }
        %>
    </div>
    <div class="text-center mt-3">
        <a href="all_gift_boxes.jsp" class="button btn btn-outline-dark dtn-sm">Переглянути Всі</a>
    </div>
</div>


<%@include file="components/footer.jsp" %>
</body>
</html>
