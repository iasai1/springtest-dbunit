<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ishipitc
  Date: 13.10.2019
  Time: 3:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Employes</title>
    <script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
</head>
<body>

    <table>
        <tr>
            <th>Name</th>
            <th>Phone</th>
            <th>Options</th>
        </tr>
        <c:forEach items="${employees}" var="employee">
            <tr>
                <td>${employee.name}</td>
                <td>${employee.phone}</td>
                <td><button type="button" id="${employee.id}" class="btnInfo">View Details</button></td>
            </tr>
        </c:forEach>
        <tr>
            <td><button type="button" id="newEmp">New Employee</button></td>
            <td><td><button type="button" id="deps">Departmentы</button></td></td>
        </tr>
    </table>
</body>

<script>

    $("#deps").on("click", function () {
        window.location = "/departments";
    });

    $("#newEmp").on("click", function () {
        window.location = "/newEmployee";
    });

    $(".btnInfo").on("click", function () {

        var id = $(this).attr("id");

        $.ajax({
            type: "POST",
            contentType: 'application/json',
            url: "/employeeInfo" + id
        }).done(function (data) {
            alert(JSON.parse(data));
        }).fail(function () {
            alert("An Error occured");
        });
    })

</script>

</html>
