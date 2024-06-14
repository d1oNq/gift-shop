<%@ page import="com.giftshop.dao.ProductDAOImpl" %>
<%@ page import="com.giftshop.db.DBConnect" %>
<%@ page import="com.giftshop.entity.Product" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<%
    int id = Integer.parseInt(request.getParameter("id"));

    ProductDAOImpl dao = new ProductDAOImpl(DBConnect.getConn());

    Product product = dao.getProductById(id);

%>
<head>
    <title><%=product.getProductName()%>
    </title>
    <%@include file="components/allCss.jsp" %>
</head>
<body>
<div class="wrapper">
    <%@include file="components/navbar.jsp" %>

    <div class="container p-3 content">
        <div class="row">
            <div class="col-md-6  text-center p-3">
                <img src="product/<%=product.getPhoto()%>" alt="" width="300px">
            </div>
            <div class="col-md-6 p-5">
                <h4><%=product.getProductName()%>
                </h4>
                <h5>Категорія: <%=product.getCategory()%>
                </h5>
                <h5>Вага: <%=product.getWeight()%> г.</h5>
                <h5>Ціна: <%=product.getPrice()%> грн.</h5>
                <a href="" class="btn btn-primary mt-4"><i class="fa-solid fa-cart-plus"></i> Додати в кошик</a>
                <a href="index.jsp" class="btn btn-success mt-4">Продовжити покупки</a>
            </div>
        </div>
    </div>

    <%@include file="components/footer.jsp" %>
</div>
</body>
</html>
