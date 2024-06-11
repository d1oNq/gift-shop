<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>

<nav class="navbar navbar-expand-lg bg--custom text--custom">
    <a class="navbar-brand" href="home.jsp"><h3><i class="fa-solid fa-gift"></i> Gift Shop</h3></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="home.jsp"><i class="fas fa-home"></i> Головна <span
                        class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="add_product.jsp"><i class="fa-solid fa-square-plus"></i> Додати товар <span
                        class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="products.jsp"><i class="fa-solid fa-gift"></i> Усі товари <span
                        class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="orders.jsp"><i class="fa-solid fa-box-open"></i> Замовлення <span
                        class="sr-only">(current)</span></a>
            </li>
        </ul>
    </div>
    <div>
        <c:if test="${not empty user}">
            <a class="btn btn-success" href="home.jsp"><i class="fa-solid fa-user"></i> ${user.name}</a>
            <a class="btn btn-primary" href="../logout"><i class="fa-solid fa-right-from-bracket"></i> Вийти</a>
        </c:if>
        <c:if test="${empty user}">
            <a class="btn btn-success" href="../login.jsp"><i class="fa-solid fa-right-to-bracket"></i> Вхід</a>
            <a class="btn btn-primary" href="../register.jsp"><i class="fa-solid fa-user-plus"></i> Реєстрація</a>
        </c:if>
    </div>

</nav>

