<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<meta charset='UTF-8'>
  <head>
    <title>欢迎光临！</title>
  </head>
  <body>
   <div id="app" style="text-align: left;margin-left: 36%;margin-top: 20px">
     <form  id="form">
       <input type="hidden" name="id" v-model="user.id" value="" />
       用户名<input type="text" name="name" v-model="user.name" /><br/><br/>
       工资：<input type="text" v-model="user.salary" name="salary" /><br/><br/>
       年龄：<input type="text" name="age" v-model="user.age" /><br/><br/>
       <input type="button" id="btn" value="修改" @click="updateUser" class="button btn-danger"/>
     </form>
   </div>
  <%-- <script>
       $('#btn').click(function () {
           $.ajax({
               url:'${path}/user/updateUser',
               type:'POST',
               data:$('#form').serialize(),
               dataType:'json',
               success:function (data) {
                   alert(data)
                   if (data.code==200){
                       alert(data.msg)
                   }
               }
           });
       });
   </script>--%>

   <script>
       var userId=${id};
       var vm=new Vue({
           el:'#app',
           data:{
               user:{"id":userId}
           },
           methods:{
               updateUser:function () {
                   var _this=this;
                   axios({
                       url:'${path}/user/updateUser',
                       method:'post',
                       data:_this.user
                   }).then(function (value) {
                       console.log(value.data)
                       alert(value.data.msg)
                       window.location.href = "${path}/user/getListUser"
                   }).catch(function (reason) {
                       console.log(reason)
                   });
               },
               getUser:function () {
                   var _this=this;
                   axios({
                       url:'${path}/user/getUserById',
                       method:'get',
                       params:{id:userId},
                   }).then(function (value) {
                       console.log(value.data)
                        _this.user=value.data;
                   }).catch(function (reason) {
                       console.log(reason)
                   });
               }
           },
           created : function() {
               this.getUser();
           }
       });
   </script>
  </body>
</html>
