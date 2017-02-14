<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script language="javascript">
        function deleteSelected(tableID) {
            var ids = new Array();
            var table = document.getElementById(tableID);
            var rowCount = table.rows.length;

            for (var i = 0; i < rowCount; i++) {
                var row = table.rows[i];
                var checkbox = row.cells[1].childNodes[0];
                if ((null != checkbox) && (true == checkbox.checked))
                    ids.push(checkbox.value);
            }
            if (ids.length > 0) {
                var data = { 'toDelete[]' : ids};
                $.post("/delete_selected", data, function(data, status) {
                    window.location.reload();
                });
            } else {
                alert("Photos not selected")
            }
        }
    </script>

    <title>Prog.kiev.ua</title>
</head>
<body>
<div align="center">
    <c:choose>
        <c:when test="${not empty photo_ids}">
            <form id="MainForm" name="MainForm" method="post">
                <table id="dataTable" border="1">
                    <c:forEach items="${photo_ids}" var="id">
                        <tr>
                            <td><c:out value="${id}"></c:out></td>
                            <td><input type="checkbox" name="toDelete[]" value="${id}" id="checkbox_${id}"></td>
                        </tr>
                    </c:forEach>
                </table>
                <br>
                <input language="javascript" type="button" id="delSelBtn" value="Delete selected"
                       onclick="deleteSelected('dataTable')">
            </form>
        </c:when>
        <c:otherwise>
            <h2>Empty list!</h2>
        </c:otherwise>
    </c:choose>
    <form action="\" method="post">
        <input type="submit" value="Return to Main">
    </form>
</div>
</body>
</html>
