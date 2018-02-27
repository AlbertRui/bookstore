<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/2/27
  Time: 10:42
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div style="text-align: center;">
    <br><br>
    您一共买了 ${sessionScope.ShoppingCart.bookNumber } 本书
    <br>
    应付: ￥${ sessionScope.ShoppingCart.totalMoney}
    <br><br>
    <c:if test="${!empty requestScope.errors}">
        <span style="color: red;">${requestScope.errors}</span>
    </c:if>
    <form action="${pageContext.request.contextPath}/bookServlet?method=cash" method="post">
        <table cellpadding="10">
            <tr>
                <td>姓名</td>
                <td><input type="text" name="username" title="username"/></td>
            </tr>
            <tr>
                <td>信用卡账号</td>
                <td><input type="text" name="accountId" title="accountId"/></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="submit"/></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
