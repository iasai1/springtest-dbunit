<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Departments</title>
    <script src="https://code.jquery.com/jquery-2.1.4.min.js"></script>
    <script src="https://code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.11.4/jquery-ui.css" />

</head>
<body>
    <table id="depTable">
        <tr>
            <th>Name</th>
            <th>Options</th>
        </tr>

        <c:forEach items="${departments}" var="department">
            <tr>
                <td>${department.name}</td>
                <td>
                    <button type="button" name="${department.id}" class="btnRename">Rename</button>
                </td>
            </tr>
        </c:forEach>

        <td><td><button type="button" id="newDep">New Department</button></td></td>
        <td><td><button type="button" id="emps">Employees</button></td></td>

        <div id="inputDialog" style="display: none">
            Enter new name:
            <input type="text" id="txtInput" class="txtInput"/>
        </div>
    </table>
</body>

<script>
    $("#newDep").on("click", function () {
        window.location = "/newDepartment";
    });

    $("#emps").on("click", function () {
        window.location = "/employees";
    });

    $(".btnRename").on("click", function () {
        var id = $(this).attr("name");
        OpenInputDialog(id);
    });

    function OpenInputDialog(id) {
        $("#inputDialog").dialog({
            autoOpen: false,
            modal: true,
            title: "Input value",
            closeOnEscape: true,
            buttons: [{
                text: "Save",
                id: "save",
                click: function () {

                    var name = $(".txtInput").val();

                    var dep = {
                        "id" : id,
                        "name" : name
                    };

                    $.ajax({
                        type: "PUT",
                        contentType: 'application/json',
                        url: "/renameDep",
                        data: JSON.stringify(dep),
                        processData: false
                    }).done(function () {
                        window.location = '/departments';
                    }).error(function (data, textStatus, xhr) {
                        alert(data.responseText);
                    });
                }
            }],
            close: function () {
                $(this).dialog("close");
                $(".txtInput").val("");
            },
            show: { effect: "clip", duration: 200 }
        });
        $("#inputDialog").dialog("open");
    };

</script>

</html>
