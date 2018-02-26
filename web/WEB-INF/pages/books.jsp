<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/2/26
  Time: 11:34
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
            $("#pageNo").change(function () {
                var val = $(this).val();
                val = $.trim(val);

                //1. 校验 val 是否为数字 1, 2, 而不是 a12, b
                var flag = false;
                var reg = /^\d+$/g;
                var pageNo = 0;

                if (reg.test(val)) {
                    //2. 校验 val 在一个合法的范围内： 1-totalPageNumber
                    pageNo = parseInt(val);
                    if (pageNo >= 1 && pageNo <= parseInt("${requestScope.page.totalPageNumber }")) {
                        flag = true;
                    }
                }

                if (!flag) {
                    alert("您输入的页码不合法.");
                    $(this).val("");
                    return;
                }

                //3. 页面跳转
                window.location.href = "bookServlet?method=getBooks&pageNo=" + pageNo + "&" + $(":hidden").serialize();
            });
        });
    </script>
    <%@include file="/jsp/queryCondition.jsp" %>
</head>
<body>
<div style="text-align: center;">
    <br><br>
    <form action="bookServlet?method=getBooks" method="post">
        price:
        <label>
            <input type="text" size="1" name="minPrice"/>-
        </label>
        <label>
            <input type="text" size="1" name="maxPrice"/>
        </label>

        <input type="submit" value="submit"/>
    </form>
    <br><br>

    <table cellpadding="10" align="center">
        <c:forEach items="${requestScope.page.list}" var="book">
            <tr>
                <td>
                    <a href="bookServlet?method=getBook&pageNo=${requestScope.page.pageNo }&id=${book.id}">${book.title}</a><br>
                        ${book.author}
                </td>
                <td>${book.price}</td>
                <td><a href="">加入购物车</a></td>
            </tr>
        </c:forEach>
    </table>

    <br><br>

    共 ${requestScope.page.totalPageNumber} 页
    &nbsp;&nbsp;
    当前第 ${requestScope.page.pageNo} 页
    &nbsp;&nbsp;
    <c:if test="${requestScope.page.hasPrev}">
        <a href="bookServlet?method=getBooks&pageNo=1">首页</a>
        &nbsp;&nbsp;
        <a href="bookServlet?method=getBooks&pageNo=${requestScope.page.prevPage}">上一页</a>
        &nbsp;&nbsp;
    </c:if>

    <c:if test="${requestScope.page.hasNext}">
        <a href="bookServlet?method=getBooks&pageNo=${requestScope.page.nextPage}">下一页</a>
        &nbsp;&nbsp;
        <a href="bookServlet?method=getBooks&pageNo=${requestScope.page.totalPageNumber}">末页</a>
        &nbsp;&nbsp;
    </c:if>

    转到 <label for="pageNo"></label><input type="text" size="1" id="pageNo"/> 页

</div>
</body>
</html>
