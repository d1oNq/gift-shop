<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>

<html>
<head>
    <title>Успішне Замовлення</title>
    <%@include file="components/allCss.jsp" %>
</head>
<body>
<c:if test="${empty user}">
    <c:redirect url="login.jsp"/>
</c:if>
<div class="wrapper">
    <%@include file="components/navbar.jsp" %>
    <div class="content">
        <div class="container text-center p-4 mt-4">
            <i class="fas fa-check-circle fa-5x text-success mb-2"></i>
            <h2 class="mb-3">Ваше замовлення успішно оформлено</h2>
            <h5 class="mb-3">Очікуйте на посилку протягом 2-3 днів</h5>
            <a href="index.jsp" class="btn btn-secondary mt-3">Головна</a>
            <a href="orders.jsp" class="btn btn btn-primary mt-3">Переглянути замовлення</a>
        </div>
    </div>
    <%@include file="components/footer.jsp" %>
</div>
</body>
</html>
