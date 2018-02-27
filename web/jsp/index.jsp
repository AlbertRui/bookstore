<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/2/25
  Time: 20:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/jsp/common.jsp" %>
<html>
<head>
    <title>$Title$</title>
</head>
<body>
<%
    response.sendRedirect(request.getContextPath() + "/bookServlet?method=getBooks");
%>
</body>
</html>
