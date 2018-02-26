<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/2/26
  Time: 15:18
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html;charset=UTF-8" %>
<script type="text/javascript">
    $(function () {
        $("a").click(function () {
            var serializeVal = $(":hidden").serialize();
            window.location.href = this.href + "&" + serializeVal;
            return false;
        });
    });
</script>
<input type="hidden" name="minPrice" value="${param.minPrice}"/>
<input type="hidden" name="maxPrice" value="${param.maxPrice}"/>
