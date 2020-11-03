<%--
  Created by IntelliJ IDEA.
  User: FT
  Date: 2020/10/15
  Time: 12:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form id="form2">
    <input type="text" name="name" id="name" placeholder="请输入用户名"
           value="<c:if test="${fn:length(name)>0}">${username}</c:if>"/>
</form>
&nbsp;
<input class="btn btn-success" type="button" value="查询" id="selectBtn"/> &nbsp;
<a href="${path}/user/toAddUser" class="btn btn-success">添加</a><br/>
<table class="table table-bordered" id="myTable">
    <tr>
        <th>序号</th>
        <th>姓名</th>
        <th>工资</th>
        <th>年龄</th>
        <th>操作</th>
    </tr>
    <c:forEach var="user" items="${list}" varStatus="status">
        <tr>
            <td>${status.count}</td>
            <td>${user.name}</td>
            <td>${user.salary}</td>
            <td>${user.age}</td>
            <td>
                <a href="${path}/user/toUpdateUser?id=${user.id}" class="btn btn-success">修改</a>&nbsp;
                <a href="${path}/user/deleteUser?id=${user.id}" class="btn btn-danger">删除</a>
            </td>
        </tr>
    </c:forEach>
</table>
<script>
    $('#selectBtn').click(function () {
       var myHtml="<tr>\n" +
           "        <th>序号</th>\n" +
           "        <th>姓名</th>\n" +
           "        <th>工资</th>\n" +
           "        <th>年龄</th>\n" +
           "        <th>操作</th>\n" +
           "    </tr>";
        $.ajax({
            url: '${path}/user/getUsersByName',
            type: 'POST',
            dataType: 'json',
            data: $('#form2').serialize(),
            success: function (data) {
              $('#myTable').empty();
              console.log(JSON.stringify(data))
              for (var i=0;i<data.length;i++){
                  myHtml+="<tr>\n" +
                      "        <td>"+data[i].id+"</td>\n" +
                      "        <td>"+data[i].name+"</td>\n" +
                      "        <td>"+data[i].salary+"</td>\n" +
                      "        <td>"+data[i].age+"</td>\n" +
                      "        <td>"+
                      " <a href=\"${path}/user/toUpdateUser?id="+data[i].id+"\" class=\"btn btn-success\">修改</a>&nbsp;\n" +
                      "                <a href=\"${path}/user/deleteUser?id="+data[i].id+"\" class=\"btn btn-danger\">删除</a>"
                      +"</td>\n" +
                      "    </tr>"
              }
                $('#myTable').append(myHtml)
            }
        });
    });
</script>
</body>
</html>
