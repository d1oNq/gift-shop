<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Домашня</title>
    <%@include file="components/allCss.jsp" %>
</head>
<body>
<div class="wrapper">
<%@include file="components/navbar.jsp" %>

<c:if test="${empty user}">
    <c:redirect url="login.jsp"/>
</c:if>

<div class="container content">
    <div class="row p-5">
        <div class="col-md-3 d-flex align-items-stretch">
            <a href="edit_profile.jsp" class="w-100">
                <div class="card h-100">
                    <div class="card-body d-flex flex-column justify-content-center align-items-center text-center text-primary">
                        <div>
                            <i class="fa-solid fa-address-card fa-3x"></i><br>
                            <h4>Редагувати Профіль</h4>
                        </div>
                    </div>
                </div>
            </a>
        </div>
        <div class="col-md-3 d-flex align-items-stretch">
            <a href="user_address.jsp" class="w-100">
                <div class="card h-100">
                    <div class="card-body d-flex flex-column justify-content-center align-items-center text-center text-warning">
                        <div>
                            <i class="fa-solid fa-location-dot fa-3x"></i><br>
                            <h4>Адреса</h4>
                        </div>
                    </div>
                </div>
            </a>
        </div>
        <div class="col-md-3 d-flex align-items-stretch">
            <a href="orders.jsp" class="w-100">
                <div class="card h-100">
                    <div class="card-body d-flex flex-column justify-content-center align-items-center text-center text-danger">
                        <div>
                            <i class="fa-solid fa-box-open fa-3x"></i><br>
                            <h4>Мої Замовлення</h4>
                        </div>
                    </div>
                </div>
            </a>
        </div>
        <div class="col-md-3 d-flex align-items-stretch">
            <a href="logout" class="w-100">
                <div class="card h-100">
                    <div class="card-body d-flex flex-column justify-content-center align-items-center text-center text-info">
                        <div>
                            <i class="fa-solid fa-right-from-bracket fa-3x"></i><br>
                            <h4>Вийти</h4>
                        </div>
                    </div>
                </div>
            </a>
        </div>
    </div>
</div>

<%@include file="components/footer.jsp" %>
</div>
</body>
</html>
