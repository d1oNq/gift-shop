<%@ page import="com.giftshop.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Адреса</title>
    <%@include file="components/allCss.jsp" %>
</head>
<body>
<%@include file="components/navbar.jsp" %>

<div class="container p-2">
    <div class="row">
        <div class="col-md-4 offset-md-4">
            <div class="card">
                <div class="card-body">
                    <h3 class="text-center mb-4">Адреса</h3>
                    <%
                        User user = (User) session.getAttribute("user");
                    %>
                    <form action="update_address" method="post">
                        <input type="hidden" value="<%=user.getId()%>" name="id">
                        <div class="form-group">
                            <label for="inputAddress">Адреса</label>
                            <input type="text" class="form-control" id="inputAddress" placeholder="Адреса" name="address"
                                   value="<%= user.getAddress() != null ? user.getAddress() : "" %>">
                        </div>
                        <div class="form-group">
                            <label for="inputCity">Місто</label>
                            <input type="text" class="form-control" id="inputCity" placeholder="Місто" name="city"
                                   value="<%= user.getCity() != null ? user.getCity() : "" %>">
                        </div>
                        <div class="form-group">
                            <label for="inputZip">Поштовий Індекс</label>
                            <input type="text" class="form-control" id="inputZip" placeholder="Індекс" name="zip"
                                   value="<%= user.getZip() != null ? user.getZip() : "" %>">
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
