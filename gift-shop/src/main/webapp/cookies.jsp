<%@ page import="com.giftshop.dao.ProductDAOImpl" %>
<%@ page import="com.giftshop.db.DBConnect" %>
<%@ page import="com.giftshop.entity.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="com.giftshop.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>

<html>
<head>
    <title>Печиво</title>
    <%@include file="components/allCss.jsp" %>
    <link rel="stylesheet" href="components/cards.css">
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

<div class="container-fluid text-center">
    <div class="row p-3 product-container">
        <%
            ProductDAOImpl dao = new ProductDAOImpl(DBConnect.getConn());
            List<Product> candies = dao.getAllCookies();
            for (Product product : candies) {
        %>
        <div class="col-md-2 mb-4">
            <div class="card card--hover">
                <a href="product.jsp?id=<%=product.getProductId()%>">
                    <div class="card-body text-center">
                        <img src="product/<%=product.getPhoto() %>" alt="" style="width: 150px; height: 150px;">
                        <div class="card-text">
                            <p style="font-weight: 700;"><%=product.getProductName() %>, <%=product.getWeight() %>г</p>
                            <p><%=product.getPrice()%> грн.</p>
                        </div>
                        <div class="card-footer">
                            <%
                                if (user == null) {
                            %>
                            <a href="login.jsp" class="btn btn-outline-primary btn-sm">
                                <i class="fa-solid fa-cart-shopping"></i> Додати в кошик
                            </a>
                            <%
                            } else {
                            %>
                            <a href="cart?productId=<%=product.getProductId()%>&&userId=<%=user.getId()%>" class="btn btn-outline-primary btn-sm">
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
</div>

<%@include file="components/footer.jsp" %>
</body>
</html>
