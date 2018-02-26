<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/2/26
  Time: 14:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="book" scope="request" type="me.bookstore.domain.Book"/>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-1.7.2.js"></script>
    <%@include file="/jsp/queryCondition.jsp" %>
</head>
<body>
<input type="hidden" name="minPrice" value="${param.minPrice}"/>
<input type="hidden" name="maxPrice" value="${param.maxPrice}"/>
<div style="text-align: center;">
    <br><br>
    Title: ${book.title}
    <br><br>
    Author: ${book.author}
    <br><br>
    Price: ${book.price}
    <br><br>
    PublishingDate: ${book.publishingDate}
    <br><br>
    Remark: ${book.remark}
    <br><br>
    <a href="bookServlet?method=getBooks&pageNo=${param.pageNo}">继续购物</a>
</div>
</body>
</html>
