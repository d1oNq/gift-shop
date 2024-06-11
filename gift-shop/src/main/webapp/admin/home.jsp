<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Домашня</title>
    <%@include file="../components/allCss.jsp" %>
    <link rel="stylesheet" href="../components/style.css">
</head>
<body>
<div class="wrapper">
    <%@include file="navbar.jsp" %>

    <c:if test="${empty user}">
        <c:redirect url="../login.jsp"/>
    </c:if>

    <div class="container content">
        <div class="row p-5">
            <div class="col-md-3">
                <a class="add" href="add_product.jsp">
                    <div class="card">
                        <div class="card-body text-center text-success">
                            <i class="fa-solid fa-square-plus fa-3x"></i><br>
                            <h4>Додати товар</h4>
                        </div>
                    </div>
                </a>
            </div>
            <div class="col-md-3">
                <a href="products.jsp">
                    <div class="card">
                        <div class="card-body text-center text-danger">
                            <i class="fa-solid fa-gift fa-3x"></i><br>
                            <h4>Усі товари</h4>
                        </div>
                    </div>
                </a>
            </div>
            <div class="col-md-3">
                <a href="orders.jsp">
                    <div class="card">
                        <div class="card-body text-center text-warning">
                            <i class="fa-solid fa-box-open fa-3x"></i><br>
                            <h4>Замовлення</h4>
                        </div>
                    </div>
                </a>
            </div>
            <div class="col-md-3">
                <a href="../logout">
                    <div class="card">
                        <div class="card-body text-center text-info">
                            <i class="fa-solid fa-right-from-bracket fa-3x"></i><br>
                            <h4>Вийти</h4>
                        </div>
                    </div>
                </a>
            </div>
        </div>
    </div>

    <%@include file="../components/footer.jsp" %>
</div>

</body>
</html>
