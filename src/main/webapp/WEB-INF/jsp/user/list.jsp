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
<div id="app">
<form id="form2">
    <input type="text" name="name" v-model="name" placeholder="请输入用户名"
           value="<c:if test="${fn:length(name)>0}">${username}</c:if>" />
</form>
&nbsp;
<input class="btn btn-success" type="button" value="查询" @click="listAll(1,name)"/> &nbsp;
<input type="button" @click="toAddUser()" class="btn btn-success" value="添加">
<table class="table table-bordered" >
    <tr>
        <th>序号</th>
        <th>姓名</th>
        <th>工资</th>
        <th>年龄</th>
        <th>操作</th>
    </tr>

    <tr v-for="(user,index) in page.data">
        <td>{{user.id}}</td>
        <td>{{user.name}}</td>
        <td>{{user.salary}}</td>
        <td>{{user.age}}</td>
        <td>
            <input type="button" value="修改" @click="toUpdate(user.id)" class="btn btn-success"/>&nbsp;
            <input type="button" value="删除" @click="toDelete(user.id)" class="btn btn-danger"/>
        </td>
    </tr>
</table>
    <br/>
    <div id="div4" class="box-tools pull-left">
        <input type="button" value="首页" @click="listAll(1,name)" class="btn btn-success"/>
        <input type="button" value="上一页" @click="listAll(page.pageCurrent-1,name)" class="btn btn-success"/>
        <input type="button" value="下一页" @click="listAll(page.pageCurrent+1,name)" class="btn btn-success"/>
        <input type="button" value="尾页" @click="listAll(page.pageTotal,name)" class="btn btn-success"/>
        <br/>
        当前{{page.pageCurrent}}页，每页<select name="pageSize" v-model="page.pageSize">
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                            <option value="4">4</option>
                                            <option value="5">5</option>
                                        </select>条，共{{page.count}}条记录，共{{page.pageTotal}}页
    </div>
</div>
    <script>
        var vm=new Vue({
            el:'#app',
            data:{
                page:{pageCurrent:"1",pageSize:"5",count:"",pageTotal:"",data:[]},
                name:""
            },
            methods:{
               listAll:function (pageCurrent,name) {
                   var _this=this;
                   if (pageCurrent!=null){
                       _this.page.pageCurrent=pageCurrent;
                       _this.name=name;
                   }
                   axios({
                       url:'${path}/user/getListAll',
                       method:'post',
                      // param:{""},
                       data:{"page":_this.page,"name":_this.name}
                   }).then(function (value) {
                       console.log(value.data)
                       _this.page=value.data
                   }).catch(function (reason) {
                       console.log(reason)
                   });
               },
                toUpdate:function (id) {
                   window.location.href="${path}/user/toUpdateUser?id="+id+"&pageCurrent="
                       +this.page.pageCurrent+"&pageSize="+this.page.pageSize+"&name="+this.name;

                },
                toDelete:function (id) {
                    window.location.href="${path}/user/deleteUser?id="+id+"&pageCurrent="
                        +this.page.pageCurrent+"&pageSize="+this.page.pageSize+"&name="+this.name;
                },
                toAddUser:function () {
                    window.location.href="${path}/user/toAddUser";
                }
                },
            created : function() {
                this.listAll();
            }
        });
    </script>
</body>
</html>
