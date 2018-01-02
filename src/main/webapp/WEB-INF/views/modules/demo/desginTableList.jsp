<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>设计表</title>
    <meta name="decorator" content="easyui"/>
    <script type="text/javascript">
        var dataObject;
        $(function(){
            var tableName='${desginTable.tableName}'
            var id='${desginTable.id}';
            $('#dialog').dialog('close');
            dataObject=$("#userList");
            dataObject.datagrid({
                url:'${ctx}/demo/desginTable/dataList?desginTable.id='+id,
                rownumbers:true,
                fitColumns:true,
                singleSelect:true,
                fit:true,
                border:true,
                pagination:true,
                remoteSort:true,
                pageSize:30,
                pageList:[30,60,100],
                idField:'id',
                columns:[[
                    {field:'id',hidden:true,width:50},
                    {field:'isDbsynch',hidden:true,width:50},
                    {field:'tableName',title:'表名',width:60},
                    {field:'querymode',title:'查询模式',width:60},
                    {field:'remarks',title:'表描述',width:60},
                    {field:'formVersion',title:'表单版本号',width:60},
                    {field:'操作',title:'操作',width:100,formatter: function(value,row,index){
                        var btn="";
                        //<shiro:hasPermission name="sys:role:edit"><td>
                        btn += "<a  class='button-edit  button-info' href='javascript:void(0);' onclick='editUser(\""+row.id+"\")' >编辑</a>";
                        btn += "<span style='margin-left:15px;margin-right:15px;'></span>";
                        btn += "<a class='button-delete button-danger'  href='javascript:void(0)' onclick='deleteUser(\""+row.id+"\")'>删除</a>";
                        btn += "<span style='margin-left:15px;margin-right:15px;'></span>";
                        btn += "<a class='button-delete button-danger'  href='javascript:void(0)' onclick='updateTable(\""+row.id+"\")'>更新</a>";
                         if(row.isDbsynch==0) {
                        btn += "<span style='margin-left:15px;margin-right:15px;'></span>";
                        btn += "<a class='button-delete button-danger'  href='javascript:void(0)' onclick='createTable(\"" + row.id + "\")'>数据库新建表</a>";
                            }
                        //</shiro:hasPermission>
                        return btn;
                    }
                    }
                ]],
                onLoadSuccess:function(){
                    $('.button-edit').linkbutton({
                    });
                    $('.button-delete').linkbutton({
                    });
                },
                toolbar: '#toolbar'
            });
        })

        function queryData(){
            dataObject.datagrid({'queryParams':{
                'tableName':$("#tableName").val()

            }})
        }
        function createTable(Id){
            $.messager.confirm('确认','您确认想把表格同步到数据库吗？',
                function(r) {
                    if (r) {
                        var url = "${ctx}/demo/desginTable/createTable?id="+Id;
                        $.get(url, function(data) {
                            if(200==data.code){
                                $.messager.show({
                                    title : '提示信息',
                                    msg : data.msg,
                                    timeout : 1000 * 2
                                });
                                dataObject.datagrid("reload");
                            } else {
                                $.messager.show({
                                    title : '提示信息',
                                    msg : data.msg,
                                    timeout : 1000 * 2
                                });

                            }
                        });
                    }
                });
        }
        function deleteUser(Id){
            $.messager.confirm('确认','您确认想要删除该条数据吗？',
                function(r) {
                    if (r) {
                        var url = "${ctx}/demo/desginTable/delete?id="+Id;
                        $.get(url, function(data) {
                            if(200==data.code){
                                $.messager.show({
                                    title : '提示信息',
                                    msg : data.msg,
                                    timeout : 1000 * 2
                                });
                                dataObject.datagrid("reload");
                            } else {
                                $.messager.show({
                                    title : '提示信息',
                                    msg : data.msg,
                                    timeout : 1000 * 2
                                });

                            }
                        });
                    }
                });
        }
        function updateTable(Id){
            $.messager.confirm('确认','您确认同步到数据库吗？',
                function(r) {
                    if (r) {
                        var url = "${ctx}/demo/desginTable/updateTable?id="+Id;
                        $.get(url, function(data) {
                            if(200==data.code){
                                $.messager.show({
                                    title : '提示信息',
                                    msg : data.msg,
                                    timeout : 1000 * 2
                                });
                                dataObject.datagrid("reload");
                            } else {
                                $.messager.show({
                                    title : '提示信息',
                                    msg : data.msg,
                                    timeout : 1000 * 2
                                });

                            }
                        });
                    }
                });
        }


        function importUser(){
            $('#dialog').dialog('open');
        }

        function addTable(){
            $.modalDialog.openner=dataObject;
            $.modalDialog({
                title : "新增",
                width :800,
                height :500,
                href : "${ctx}/demo/desginTable/form?"
            });
        }
        function editUser(id){
            $.modalDialog.openner=dataObject;
            $.modalDialog({
                title : "编辑",
                width :800,
                height : 500,
                href : "${ctx}/demo/desginTable/form?tableId="+tableName+"&id="+id
            });
        }

    </script>
</head>
<body>

<table id="userList">

</table>
<div id="toolbar">
    <form:form id="searchForm" modelAttribute="desginTable" action="" method="post" class="breadcrumb form-search ">
        <label>表名：</label><input id="tableName" name="tableName" type="text" maxlength="50" class="easyui-textbox" value="${desginTable.tableName}"/>
        <a href="#" class="easyui-linkbutton"  onclick="queryData();"  iconCls="icon-search" plain="true">查询</a>
        <a href="#" class="easyui-linkbutton"  onclick="addTable('');"  iconCls="icon-add" plain="true">新增</a>
    </form:form>
</div>

</body>
</html>