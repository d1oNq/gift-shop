<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Вхід</title>
    <%@include file="components/allCss.jsp" %>
</head>
<body>
<div class="wrapper">
    <%@include file="components/navbar.jsp" %>


    <div class="container p-2 content content--center">
        <div class="content-box">
            <div class="row mt-2">
                <div class="col-md-4 offset-md-4">
                    <div class="card">
                        <div class="card-body">
                            <h3 class="text-center">Вхід</h3>

                            <c:if test="${not empty failedMsg}">
                                <h5 class="text-center text-danger">${failedMsg}</h5>
                                <c:remove var="failedMsg" scope="session"/>
                            </c:if>

                            <form class="mt-4" action="login" method="post">
                                <div class="form-group">
                                    <input type="email" class="form-control" id="inputEmail"
                                           aria-describedby="emailHelp" required="required" placeholder="Електронна пошта"
                                           name="email">
                                </div>
                                <div class="form-group password-container">
                                    <input type="password" class="form-control" id="inputPassword" required="required"
                                           placeholder="Пароль" name="password">
                                    <span class="toggle-password" onclick="togglePasswordVisibility()">Show</span>
                                </div>
                                <div class="text-center">
                                    <button type="submit" class="btn btn-primary mb-2">Увійти</button>
                                    <br>
                                    <a href="register.jsp">Створити обліковий запис</a>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%@include file="components/footer.jsp" %>
</div>

</body>
</html>
