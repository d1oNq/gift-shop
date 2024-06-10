<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page isELIgnored="false" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Admin: Add</title>
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
                    <h3 class="text-center">Додати новий товар</h3>

                    <c:if test="${not empty successMsg}">
                        <p class="text-center text-success">${successMsg}</p>
                        <c:remove var="successMsg" scope="session"/>
                    </c:if>

                    <c:if test="${not empty failedMsg}">
                        <p class="text-center text-danger">${failedMsg}</p>
                        <c:remove var="failedMsg" scope="session"/>
                    </c:if>

                    <form class="mt-4" action="../add_product" method="post" enctype="multipart/form-data">
                        <div class="form-group">
                            <label for="inputName">Назва</label>
                            <input type="text" class="form-control" id="inputName"
                                   required="required" placeholder="Назва" name="name">
                        </div>
                        <div class="form-group">
                            <label for="inputCategory">Категорія</label>
                            <select class="form-control" id="inputCategory" name="category"
                                    required="required">
                                <option selected disabled>Оберіть Кетегорію</option>
                                <option value="Цукерки">Цукерки</option>
                                <option value="Печиво">Печиво</option>
                                <option value="Подарункові Набори">Подарункові Набори</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="inputWeight">Вага</label>
                            <input type="text" class="form-control" id="inputWeight" required="required"
                                   placeholder="Вага" name="weight">
                        </div>
                        <div class="form-group">
                            <label for="inputPrice">Ціна</label>
                            <input type="text" class="form-control" id="inputPrice" required="required"
                                   placeholder="Ціна" name="price">
                        </div>
                        <div class="form-group">
                            <label for="inputStatus">Статус</label>
                            <select class="form-control" id="inputStatus" name="status"
                                    required="required">
                                <option selected disabled>Оберіть Статус</option>
                                <option value="Активний">Активний</option>
                                <option value="Не активний">Не активний</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="inputImg">Фото</label>
                            <input type="file" class="form-control-file" id="inputImg" required="required"
                                   placeholder="Image" name="image">
                        </div>
                        <div class="text-center mt-3">
                            <button type="submit" class="btn btn-primary">Додати</button>
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
