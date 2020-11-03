<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<meta charset='UTF-8'>
<head>
    <title>欢迎光临！</title>
</head>
<body>
<div style="text-align: left;margin-left: 36%;margin-top: 20px" id="app">
    <form id="form">
        用户名<input type="text" name="name" v-model="user.name"/><br/><br/>
        工资：<input type="text" name="salary" v-model="user.salary"/><br/><br/>
        年龄：<input type="text" name="age" v-model="user.age"/><br/><br/>
        <input type="button" id="btn" value="保存" @click="addUser" class="button btn-success"/>
    </form>
</div>
<%--<script>
  $('#btn').click(function () {
      $.ajax({
          url:'${path}/user/addUser',
          type:'POST',
          data:$('#form').serialize(),
          dataType:'json',
          success:function (data) {
            if (data.code==200){
                alert(data.msg)
            }
          }
      });
  });
</script>--%>
<script>
    var vm = new Vue({
        el: '#app',
        data: {
            user: {}
        },
        methods: {
            addUser: function () {
                var _this = this;
                axios({
                    url: '${path}/user/addUser',
                    method: 'post',
                    data: _this.user
                }).then(function (value) {
                    console.log(value.data);
                    alert(value.data.msg);
                    window.location.href = "${path}/user/getListUser";
                }).catch(function (reason) {
                    console.log(reason)
                });
            }
        },
        created: function () {

        }
    });
</script>
</body>
</html>
