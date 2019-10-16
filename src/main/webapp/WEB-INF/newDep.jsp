
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New Department</title>
    <script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
</head>
<body>
    <table>
        <td>
            <tr>Name: </tr>
            <tr><input type="text" id="name" name = "name" required/></tr>
        </td>
    </table>
    <button type="button" id="add">Add</button>
</body>

<script>
    $("#add").on("click", function () {

        var name = $("#name").val();
        var dep = {"name" : name};

        $.ajax({
            type: "POST",
            contentType: 'application/json',
            url: "/newDepartment",
            data: JSON.stringify(dep),
            processData: false
        }).done(function () {
            window.location = '/employees';
        }).fail(function () {
            alert("An Error occured");
        });
    })
</script>

</html>
