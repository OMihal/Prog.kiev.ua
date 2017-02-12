<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Prog.kiev.ua</title>
</head>
<body>
<div align="center">
    <c:if test="${not empty photo_ids}">
        <table border="1">
            <c:forEach items="${photo_ids}" var="id">
                <tr>
                    <td><c:out value="${id}"></c:out></td>
                    <td><input type="checkbox" name="selected_for_delete" value="${id}"></td>
                </tr>
            </c:forEach>
        </table>
        <br>
        <form action="/delete_selected" method="post">
            <input type="submit" value="Delete selected">
        </form>
    </c:if>
</div>
</body>
</html>
