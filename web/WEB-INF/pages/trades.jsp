<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/2/27
  Time: 22:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/jsp/common.jsp" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div style="text-align: center;">
    <br><br>
    <h4>User: ${requestScope.user.username }</h4>

    <br><br>
    <table align="center">
        <c:forEach items="${requestScope.user.trades }" var="trade">

            <tr>
                <td>
                    <table border="1" cellpadding="10" cellspacing="0" align="center">

                        <tr>
                            <td colspan="3">TradTime: ${trade.tradeTime }</td>
                        </tr>
                        <tr>
                            <td>book</td>
                            <td>price</td>
                            <td>quantity</td>
                        </tr>
                        <c:forEach items="${trade.items }" var="item">

                            <tr>
                                <td>${item.book.title }</td>
                                <td>${item.book.price }</td>
                                <td>${item.quantity }</td>
                            </tr>

                        </c:forEach>

                    </table>
                    <br><br>
                </td>
            </tr>

        </c:forEach>
    </table>

</div>
</body>
</html>
