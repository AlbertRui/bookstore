<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/2/26
  Time: 18:51
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-1.7.2.js"></script>
    <script type="text/javascript">
        $(function () {
            $(".delete").click(function () {
                var $tr = $(this).parent().parent();
                var title = $.trim($tr.find("td:first").text());
                return confirm("确定要删除" + title + "的信息吗?");
            });
        });
    </script>
</head>
<body>
<div style="text-align: center;">
    您的购物车中共有 ${sessionScope.ShoppingCart.bookNumber} 本书
    <br><br>

    <table cellpadding="10" align="center">
        <tr>
            <th>Title</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>&nbsp;</th>
        </tr>
        <c:forEach items="${sessionScope.ShoppingCart.items}" var="item">
            <tr>
                <td>${item.book.title}</td>
                <td>${item.book.price}</td>
                <td>${item.quantity}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/bookServlet?method=remove&pageNo=${param.pageNo}&id=${item.book.id}"
                       class="delete">删除</a>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="4">总金额：${sessionScope.ShoppingCart.totalMoney}</td>
        </tr>
        <tr>
            <td colspan="4">
                <a href="">继续购物</a>&nbsp;&nbsp;
                <a href="${pageContext.request.contextPath}/bookServlet?method=clear">清空购物车</a>&nbsp;&nbsp;
                <a href="">结账</a>&nbsp;&nbsp;
            </td>
        </tr>
    </table>
</div>
<br><<br>
</body>
</html>
