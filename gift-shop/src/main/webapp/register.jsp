<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Реєстрація</title>
    <%@include file="components/allCss.jsp" %>
    <style>

    </style>
</head>
<body>
<%@include file="components/navbar.jsp" %>

<div class="container p-2">
    <div class="row">
        <div class="col-md-4 offset-md-4">
            <div class="card">
                <div class="card-body">
                    <h3 class="text-center">Зареєструватися</h3>

                    <c:if test="${not empty successMsg}">
                        <h5 class="text-center text-success">${successMsg}</h5>
                        <c:remove var="successMsg" scope="session"/>
                    </c:if>

                    <c:if test="${not empty failedMsg}">
                        <h5 class="text-center text-danger">${failedMsg}</h5>
                        <c:remove var="failedMsg" scope="session"/>
                    </c:if>

                    <form class="mt-4" action="register" method="post">
                        <div class="form-group">
                            <input type="text" class="form-control" id="inputName"
                                   required="required" placeholder="Повне Ім'я" name="name">
                        </div>
                        <div class="form-group">
                            <input type="email" class="form-control" id="inputEmail"
                                   aria-describedby="emailHelp" required="required" placeholder="Email" name="email">
                        </div>
                        <div class="form-group password-container">
                            <input type="password" class="form-control" id="inputPassword" required="required"
                                   placeholder="Пароль" name="password">
                            <span class="toggle-password" onclick="togglePasswordVisibility()">Show</span>
                        </div>
                        <div class="form-check">
                            <input type="checkbox" class="form-check-input" id="checkPolicy" name="check">
                            <label class="form-check-label" for="checkPolicy">Accept the privacy policy</label>
                        </div>
                        <div class="text-center mt-3">
                            <button type="submit" class="btn btn-primary mb-2">Зареєструватися</button>
                            <br>
                            <a class="mt-2" href="login.jsp">Уже маю аккаунт</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="components/footer.jsp" %>

</body>
</html>
