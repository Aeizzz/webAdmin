<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>图书列表</title>
    <meta name="decorator" content="easyui"/>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#dataList").datagrid({
                url:'${ctx}/lhl/publishing/datalist',
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
                    {field:'name',title:'出版社',width:40},
                    {field:'操作',title:'操作',width:100,formatter: function(value,row,index){
                        var btn="";
                        btn += "<a class='button-edit  button-info'  href='javascript:void(0)' onclick='edit(\"" + row.id + "\")'>编辑</a>&nbsp;&nbsp;&nbsp;";
                        btn += "<a class='button-delete  button-danger'  href='javascript:void(0)' onclick='deleteDate(\"" + row.id + "\")'>删除</a>";
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
        });

        function deleteDate(id) {
            $.messager.confirm('确认','您确认想要删除该信息吗？',
                function(r) {
                    if (r) {
                        var url = '${ctx}/lhl/publishing/delete?id='+id;
                        $.post(url, function(data) {
                            if(200==data.code){
                                $.messager.show({
                                    title : '提示信息',
                                    msg : data.msg,
                                    timeout : 1000 * 2
                                });
                                $("#dataList").datagrid("reload");
                                $("#dataList").datagrid("clearSelections");
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

        function edit(id) {
            $.modalDialog.openner=$("#dataList");
            $.modalDialog({
                title : "编辑",
                width : 500,
                height : 500,
                href : "${ctx}/lhl/publishing/form?id="+id
            });
        }

        function addData() {
            $.modalDialog.openner=$("#dataList");
            $.modalDialog({
                title : "新增",
                width : 500,
                height : 500,
                href : "${ctx}/lhl/publishing/form"
            });
        }
    </script>
</head>
<body>
<div id="toolbar">
    15021140002 刘洪磊
    <a href="#" class="easyui-linkbutton"  onclick="addData();"  iconCls="icon-add" plain="true">新增</a>
</div>
<table id="dataList">
</table>
</body>
</html>
