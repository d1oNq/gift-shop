<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>

<nav class="navbar navbar-expand-lg navbar-dark bg--custom text--custom">
    <a class="navbar-brand" href="index.jsp"><h3><i class="fa-solid fa-gift"></i> Gift Shop</h3></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="candies.jsp"><i class="fa-solid fa-candy-cane"></i> Цукерки</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="cookies.jsp"><i class="fa-solid fa-cookie-bite"></i> Печиво</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="gift_boxes.jsp"><i class="fa-solid fa-gifts"></i> Подарункові Набори</a>
            </li>
        </ul>
        <div class="col-md-6">
            <form class="form-inline my-2 my-lg-0" action="search.jsp" method="post">
                <input class="form-control mr-sm-2" type="search" placeholder="Пошук" aria-label="Search" name="search">
                <button class="btn btn-primary my-2 my-sm-0" type="submit" name="search-btn">Шукати</button>
            </form>
        </div>
        <form class="form-inline my-2 my-lg-0">
            <div class="col-md-3 d-flex align-items-center">
                <c:if test="${not empty user}">
                    <a href="cart.jsp"><i class="fa-solid fa-cart-plus fa-2x text-light"></i></a>
                    <a class="btn btn-success ml-4" href="home.jsp">
                        <i class="fa-solid fa-user"></i> ${user.name}
                    </a>
                    <a class="btn btn-primary ml-2" href="logout">
                        <i class="fa-solid fa-right-from-bracket"></i> Вийти
                    </a>

                </c:if>

                <c:if test="${empty user}">
                    <a class="btn btn-success" href="login.jsp">
                        <i class="fa-solid fa-right-to-bracket"></i> Вхід
                    </a>
                    <a class="btn btn-primary ml-2" href="register.jsp">
                        <i class="fa-solid fa-user-plus"></i> Реєстрація
                    </a>
                </c:if>
            </div>
        </form>
    </div>
</nav>
