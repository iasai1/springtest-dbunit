<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ishipitc
  Date: 14.10.2019
  Time: 19:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New Employee</title>
    <script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
</head>
<body>
    <table>
        <td>
            <tr>Name: </tr>
            <tr><input type="text" id="name" name = "name" required/></tr>
        </td>
        <td>
            <tr>Phone: </tr>
            <tr><input type="text" id="phone" name = "phone" required/></tr>
        </td>
        <td>
            <tr>City: </tr>
            <tr><input type="text" id="city" name = "city" required/></tr>
        </td>
        <td>
            <tr>Street: </tr>
            <tr><input type="text" id="street" name = "street" required/></tr>
        </td>
        <td>
            <select name = "depName" id="depName" required>
                <c:forEach items="${departments}" var = "dep">
                    <option value="${dep.name}">${dep.name}</option>
                </c:forEach>
            </select>
        </td>
    </table>
    <button type="button" id="add">Add</button>
</body>

<script>
    $("#add").on("click", function () {

        var name = $("#name").val();
        var phone = $("#phone").val();
        var city = $("#city").val();
        var street = $("#street").val();
        var depName = $("#depName").val();
        var employee = {
            "name" : name,
            "phone" : phone,
            "city" : city,
            "street" : street,
            "depName" : depName
        };

        $.ajax({
            type: "POST",
            contentType: 'application/json',
            url: "/newEmployee",
            data: JSON.stringify(employee),
            processData: false
        }).done(function () {
            window.location = '/employees';
        }).fail(function () {
            alert("An Error occured");
        });
    })
</script>

</html>
