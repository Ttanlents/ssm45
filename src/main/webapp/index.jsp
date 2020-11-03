<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set value="${pageContext.request.contextPath}" var="path"></c:set>
<html>
<body>
<h2>Hello World!</h2>
<a href="${path}/user/getUser?id=1">点击</a>
<a href="${path}/user/getListUser">查询</a>
</body>
</html>
