<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
    //延迟加载
    $(function(){
        pageInit();
    });
    //创建表格
    function pageInit(){
        $("#adminTable").jqGrid(
            {
                url :"${path}/admin/queryAdminPage",  //接收  page=当前页   rows=每页展示条数   返回  page=当前页   rows=[admin,admin]数据    tolal=总页数   records=总条数
                editurl:"${path}/admin/edit",  //增删改走的路径  oper:add ,edit,del
                datatype : "json", //数据格式
                rowNum : 5, //每页展示条数
                rowList : [ 1, 2, 3 ,4],  //可选每页展示条数
                pager : '#adminPage', //分页工具栏
                sortname : 'id', //排序
                mtype : "post", //请求方式
                styleUI:"Bootstrap", //使用Bootstrap
                autowidth:true, //宽度自动
                height:"auto", //高度自动
                viewrecords : true, //是否展示总条数
                colNames : [ 'Id', '用户名','密码','状态','盐' ],
                colModel : [
                    {name : 'id',width : 55},
                    {name : 'username',editable:true,width : 100},
                    {name : 'password',editable:true,width : 100},
                    {name : 'status',width : 80,align : "center",
                        formatter:function(cellvalue, options, rowObject){
                            if(cellvalue==1){
                                //正常  展示冻结（绿色）
                                return "<button class='btn btn-success' onclick='updateAdminStatus(\""+rowObject.id+"\",\""+rowObject.status+"\")'>冻结</button>";
                            }else{
                                //冻结  展示解除冻结（红色）
                                return "<button class='btn btn-danger' onclick='updateAdminStatus(\""+rowObject.id+"\",\""+rowObject.status+"\")'>解除冻结</button>";
                            }
                        }
                    },
                    {name : 'salt',width : 150},
                ],

            });
        //分页工具栏
        $("#adminTable").jqGrid('navGrid', '#adminPage',
            {edit : true,add : true,del : true,addtext:"添加",edittext:"编辑",deltext:"删除"},
            {
                closeAfterEdit:true,  //关闭对话框   修改之后的额外操作
            },
            {   //添加之后的额外操作
                closeAfterAdd: true //关闭对话框
            }

        );
    }

    //修改用户状态
    function updateAdminStatus(id,status){
        if(status == 0){
            $.post("${path}/admin/edit",{"id":id,status:"1",oper:"edit"},function(data){
                //刷新表单
                $("#adminTable").trigger("reloadGrid");
            })
        }else {
            $.post("${path}/admin/edit",{"id":id,status:"0",oper:"edit"},function(data){
                //刷新表单
                $("#adminTable").trigger("reloadGrid");
            })
        }
    }
</script>

<%--创建一个面板--%>
<div class="panel panel-info">

    <%--面板头--%>
    <div class="panel panel-heading">
        <span>用户信息</span>
    </div>

    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">用户信息</a></li>
    </ul><br>

    <div>
        <button class="btn btn-info">导出用户信息</button>
        <button class="btn btn-info">测试</button>
    </div><br>

    <%--创建表格--%>
    <table id="adminTable" />

    <%--分页工具栏--%>
    <div id="adminPage"/>

</div>