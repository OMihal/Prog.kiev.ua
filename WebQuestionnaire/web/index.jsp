<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Prog.kiev.ua</title>
</head>
<body>
    <h2>Enter your name</h2>
    <hr>
<form name="regForm" action="/register" method="get">
    <br>
    First Name <input type="text" name="fname" required><br>
    Last  Name <input type="text" name="lname" required><br><br>
    Age <select name="age">
    <% for (int i = 18; i <=100; i++) {%>
        <option value="<%= i%>"><%= i%></option>
    <%}%>
    </select>
    <hr><input type="submit" value="Register">
</form>
</body>
</html>