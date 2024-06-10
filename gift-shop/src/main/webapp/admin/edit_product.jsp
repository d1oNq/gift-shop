<%@ page import="com.giftshop.dao.ProductDAOImpl" %>
<%@ page import="com.giftshop.db.DBConnect" %>
<%@ page import="com.giftshop.entity.Product" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page isELIgnored="false" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Admin: Edit</title>
    <%@include file="../components/allCss.jsp" %>
    <link rel="stylesheet" href="../components/style.css">
</head>
<body>
<%@include file="navbar.jsp" %>

<c:if test="${empty user}">
    <c:redirect url="../login.jsp"/>
</c:if>

<div class="container p-2">
    <div class="row">
        <div class="col-md-4 offset-md-4">
            <div class="card">
                <div class="card-body">
                    <h3 class="text-center">Редагувати Продукт</h3>
                    <%
                        int id = Integer.parseInt(request.getParameter("id"));
                        ProductDAOImpl dao = new ProductDAOImpl(DBConnect.getConn());
                        Product product = dao.getProductById(id);

                    %>

                    <form class="mt-4" action="../edit_product" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="id" value="<%=product.getProductId()%>">
                        <div class="form-group">
                            <label for="inputName">Назва</label>
                            <input type="text" class="form-control" id="inputName"
                                   required="required" placeholder="Назва" name="name"
                                   value="<%=product.getProductName() %>">
                        </div>
                        <div class="form-group">
                            <label for="inputCategory">Категорія</label>
                            <select class="form-control" id="inputCategory" name="category"
                                    required="required">
                                <%
                                    if ("Цукерки".equals(product.getCategory())) {
                                %>
                                <option value="Цукерки" selected>Цукерки</option>
                                <option value="Печиво">Печиво</option>
                                <option value="Подарункові Набори">Подарункові Набори</option>
                                <%
                                } else if ("Печиво".equals(product.getCategory())) {
                                %>
                                <option value="Печиво" selected>Печиво</option>
                                <option value="Цукерки">Цукерки</option>
                                <option value="Подарункові Набори">Подарункові Набори</option>
                                <%
                                } else {
                                %>
                                <option value="Подарункові Набори" selected>Подарункові Набори</option>
                                <option value="Цукерки">Цукерки</option>
                                <option value="Печиво">Печиво</option>
                                <%
                                    }
                                %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="inputWeight">Вага</label>
                            <input type="text" class="form-control" id="inputWeight" required="required"
                                   placeholder="Вага" name="weight" value="<%=product.getWeight() %>">
                        </div>
                        <div class="form-group">
                            <label for="inputPrice">Ціна</label>
                            <input type="text" class="form-control" id="inputPrice" required="required"
                                   placeholder="Price" name="price" value="<%=product.getPrice() %>">
                        </div>
                        <div class="form-group">
                            <label for="inputStatus">Статус</label>
                            <select class="form-control" id="inputStatus" name="status"
                                    required="required">
                                <%
                                    if ("Активний".equals(product.getStatus())) {
                                %>
                                <option value="Активний" selected>Активний</option>
                                <option value="Не активний">Не активний</option>
                                <%
                                } else {
                                %>
                                <option value="Не активний" selected>Не активний</option>
                                <option value="Активний">Активний</option>
                                <%
                                    }
                                %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="inputStatus">Фото</label>
                            <input type="file" class="form-control-file" id="inputImg" name="image">
                        </div>
                        <div class="text-center mt-3">
                            <button type="submit" class="btn btn-primary">Оновити</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="../components/footer.jsp" %>

</body>
</html>
