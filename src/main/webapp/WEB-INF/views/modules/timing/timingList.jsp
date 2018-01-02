<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>保存成功管理</title>
	<meta name="decorator" content="easyui"/>
	<script type="text/javascript">
        var dataGrid;
		$(document).ready(function() {
			dataGrid=$("#dataList");
            dataGrid.datagrid({
                url:'${ctx}/timing/timing/dataList',
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
                    {field:'workId',title:'任务Id',width:20},
                    {field:'content',title:'任务描述',width:60},
                    {field:'cron',title:'cron表达式',width:60},
                    {field:'isEffect',title:'是否生效',width:60,
                        formatter : function(value, rowData,rowIndex) {
                            if(value==null){
                                return "";
                            }else{
                                if(value==1){
                                    return "生效";
                                }else{
                                    return "未生效";
                                }
                            } } },
                    {field:'status',title:'运行状态',width:60,
			formatter : function(value, rowData,rowIndex) {
                if(value==null){
                    return "";
                }else{
                    if(value==1){
                        return "运行";
                    }else{
                        return "停止";
                    }
                } } },
                    {field:'操作',title:'操作',width:100,formatter: function(value,row,index){
                        var btn="";
                        //<shiro:hasPermission name="timing:timing:edit">
                        if(row.status==1) {
                            btn += "<a  class='button-edit  button-info' href='javascript:void(0);' onclick='stopData(\""+row.id+"\")' >停止</a>";
                            btn += "<span style='margin-left:15px;margin-right:15px;'></span>";
                        }else{
                            btn += "<a  class='button-edit  button-info' href='javascript:void(0);' onclick='stopData2(\""+row.id+"\")' >运行</a>";
                            btn += "<span style='margin-left:15px;margin-right:15px;'></span>";
                        }
                        if(row.isEffect==1) {
                            btn += "<a  class='button-edit  button-info' href='javascript:void(0);' onclick='effect(\""+row.id+"\")' >失效</a>";
                            btn += "<span style='margin-left:15px;margin-right:15px;'></span>";
                        }else{
                            btn += "<a  class='button-edit  button-info' href='javascript:void(0);' onclick='effect2(\""+row.id+"\")' >生效</a>";
                            btn += "<span style='margin-left:15px;margin-right:15px;'></span>";
                        }
                        btn += "<a  class='button-edit  button-info' href='javascript:void(0);' onclick='editData(\""+row.id+"\")' >编辑</a>";
                        btn += "<span style='margin-left:15px;margin-right:15px;'></span>";
                        btn += "<a class='button-delete button-danger'  href='javascript:void(0)' onclick='deleteData(\""+row.id+"\")'>删除</a>";
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
        function queryData(){
            dataGrid.datagrid({'queryParams':{
                'type':$("#type").val()
            }})
        }
        //新增数据
        function addData(){
            $.modalDialog.openner=dataGrid;
            $.modalDialog({
                title : "新增",
                width :500,
                height : 500,
                href : "${ctx}/timing/timing/form"
            });
        }
        //编辑
        function editData(id){
            $.modalDialog.openner=dataGrid;
            $.modalDialog({
                title : "编辑",
                width : 500,
                height : 500,
                href : "${ctx}/timing/timing/form?id="+id
            });
        }
        //删除
        function deleteData(id){
            $.messager.confirm('确认','您确认想要删除该信息吗？',
                    function(r) {
                        if (r) {
                            var url = '${ctx}/timing/timing/delete?id='+id;
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
        function effect(id){
            $.messager.confirm('确认','确认失效？',
                function(r) {
                    if (r) {
                        var url = '${ctx}/timing/timing/isEffect?id='+id;
                        update(r,url);
                    }
                });
        }
        function effect2(id){
            $.messager.confirm('确认','确认更新任务时间？',
                function(r) {
                    if (r) {
                        var url = '${ctx}/timing/timing/isEffect?id='+id;
                        update(r,url);
                    }
                });
        }
//        确定停止
        function stopData(id){
            $.messager.confirm('确认','您确认想要停止任务吗？',
                function(r) {
                    if (r) {
                        var url = '${ctx}/timing/timing/isStop?id='+id;
                    	 update(r,url);
                    }
                });
        }
        function stopData2(id){
            $.messager.confirm('确认','您确认想要运行吗？',
                function(r) {
                    if (r) {
                        var url = '${ctx}/timing/timing/isStop?id='+id;
                        update(r,url);
                    }
                });
        }
        function update(r,url){
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
	</script>
</head>
<body>
<div id="toolbar">
    <a href="#" class="easyui-linkbutton"  onclick="addData();"  iconCls="icon-add" plain="true">新增</a>
</div>

<table id="dataList">
</table>