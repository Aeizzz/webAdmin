<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>保存模板管理</title>
	<meta name="decorator" content="easyui"/>
	<script type="text/javascript">
        var dataGrid;
		$(document).ready(function() {
			dataGrid=$("#dataList");
            dataGrid.datagrid({
                url:'${ctx}/sysForm/dataList',
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

                    {field:'ck',checkbox:true},
                    {field:'id',hidden:true,width:50},
                    {field:'isActivate',hidden:true,width:50},
                    {field:'templateNo',title:"模板编号",width:20},
                    {field:'templateName',title:'模板名称',width:40},
                    {field:'path',title:'word路径',width:40},
                    {field:'操作',title:'操作',width:100,formatter: function(value,row,index){

                        var btn="";
                        //<shiro:hasPermission name="sysfrom:sysForm:edit">
						if(row.isActivate==1) {
                            btn += "<a class='button-edit  button-info'  href='javascript:void(0)' onclick='updateData2(\"" + row.id + "\")'>已激活</a>";
                            btn += "<span style='margin-left:15px;margin-right:15px;'></span>";
                        }else{
                            btn += "<a class='button-edit  button-info'  href='javascript:void(0)' onclick='deleteData(\"" + row.id + "\")'>删除</a>";
                            btn += "<span style='margin-left:15px;margin-right:15px;'></span>";
                            btn += "<a class='button-edit  button-info'  href='javascript:void(0)' onclick='updateData(\"" + row.id + "\")'>激活</a>";
                            btn += "<span style='margin-left:15px;margin-right:15px;'></span>";
						}
                        btn += "<a  class='button-edit  button-info' href='javascript:void(0);' onclick='viewTpl(\""+row.id+"\")' >模板预览</a>";
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
		});
        //查询
//        function queryData(){
//            dataGrid.datagrid({'queryParams':{
//                'type':$("#type").val()
//            }})
//        }
        //新增数据
        function addData(){
            addTabPage("模版编辑","${ctx}/sysForm/form",true,$(this),true);
        }
        //预览界面
		function viewTpl(id){
            addTabPage("模版预览","${ctx}/sysForm/viewTpl?id="+id,true,$(this),true);
		}

        //编辑
        function editData(){
            var row=dataGrid.datagrid("getSelected");
            if(row){
                addTabPage("模版编辑","${ctx}/sysForm/form?id="+row.id,true,$(this),true);
            }else{
                $.messager.alert("提示", "请选择要修改的表单", "error");
            }
        }
        function updateData(id){
            $.messager.confirm('确认','您确认想要激活该模板吗？',
                function(r) {
                    if (r) {
                        var url = '${ctx}/sysForm/update?id='+id;
                        $.get(url, function(data) {
                            if(200==data.code){
                                $.messager.show({
                                    title : '提示信息',
                                    msg : data.msg,
                                    timeout : 1000 * 2
                                });
                                dataGrid.datagrid("reload");
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
        function updateData2(id){
            $.messager.confirm('确认','您确认想要取消激活吗？',
                function(r) {
                    if (r) {
                        var url = '${ctx}/sysForm/update?id='+id;
                        $.get(url, function(data) {
                            if(200==data.code){
                                $.messager.show({
                                    title : '提示信息',
                                    msg : data.msg,
                                    timeout : 1000 * 2
                                });
                                dataGrid.datagrid("reload");
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
        //删除
        function deleteData(id){
            $.messager.confirm('确认','您确认想要删除该信息吗？',
                    function(r) {
                        if (r) {
                            var url = '${ctx}/sysForm/delete?id='+id;
                            $.get(url, function(data) {
                                if(200==data.code){
                                    $.messager.show({
                                        title : '提示信息',
                                        msg : data.msg,
                                        timeout : 1000 * 2
                                    });
                                    dataGrid.datagrid("reload");
                                    dataGrid.datagrid("clearSelections");
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

	</script>
</head>
<body>
<div id="toolbar">
    <a href="#" class="easyui-linkbutton"  onclick="addData();"  iconCls="icon-add" plain="true">创建模板</a>
	<a href="#" class="easyui-linkbutton"  onclick="editData();"  iconCls="icon-edit" plain="true">编辑模板</a>
	<a href="#" class="easyui-linkbutton"  onclick="queryData();"  iconCls="icon-add" plain="true">上传word文档</a>
</div>
<table id="dataList">
</table>