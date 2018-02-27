<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/2/27
  Time: 21:32
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
    <form action="userServlet?method=getUser" method="post">
        username:
        <label>
            <input type="text" name="username"/>
        </label>
        <input type="submit" value="submit"/>
    </form>
</div>
</body>
</html>
