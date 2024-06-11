<%@ page import="com.giftshop.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>

<html>
<head>
    <title>Редагувати Профіль</title>
    <%@include file="components/allCss.jsp" %>
</head>
<body>
<c:if test="${empty user}">
    <c:redirect url="login.jsp"/>
</c:if>

<%@include file="components/navbar.jsp" %>

<div class="container p-2">
    <div class="row">
        <div class="col-md-4 offset-md-4">
            <div class="card">
                <div class="card-body">
                    <h3 class="text-center">Редагувати Профіль</h3>
                    <c:if test="${not empty successMsg}">
                        <h5 class="text-center text-success">${successMsg}</h5>
                        <c:remove var="successMsg" scope="session"/>
                    </c:if>

                    <c:if test="${not empty failedMsg}">
                        <h5 class="text-center text-danger">${failedMsg}</h5>
                        <c:remove var="failedMsg" scope="session"/>
                    </c:if>

                    <%
                        User user = (User) session.getAttribute("user");
                    %>
                    <form class="mt-4" action="update_profile" method="post">
                        <input type="hidden" value="<%=user.getId()%>" name="id">
                        <div class="form-group">
                            <label for="inputName">Ім'я</label>
                            <input type="text" class="form-control" id="inputName"
                                   required="required" placeholder="Ім'я" name="name" value="<%=user.getName()%>">
                        </div>
                        <div class="form-group">
                            <label for="inputEmail">Електронна пошта</label>
                            <input type="email" class="form-control" id="inputEmail"
                                   aria-describedby="emailHelp" required="required" placeholder="Електронна пошта" name="email"
                                   value="<%=user.getEmail()%>">
                        </div>
                        <div class="form-group">
                            <label for="inputPhone">Телефон</label>
                            <input type="tel" class="form-control" id="inputPhone" required="required"
                                   placeholder="Телефон" name="phone"
                                   value="<%= user.getPhone() != null ? user.getPhone() : "" %>">
                        </div>
                        <label for="inputPassword">Пароль</label>
                        <div class="form-group password-container">
                            <input type="password" class="form-control" id="inputPassword" required="required"
                                   placeholder="Пароль" name="password" value="<%=user.getPassword()%>">
                            <span class="toggle-password" onclick="togglePasswordVisibility()">Show</span>
                        </div>
                        <div class="text-center mt-3">
                            <button type="submit" class="btn btn-primary">Оновити</button>
                        </div>
                        <div class="text-center mt-2">
                            <a href="home.jsp">Назад</a>
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
