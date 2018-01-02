<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>字典管理</title>
	<meta name="decorator" content="easyui"/>

	<script type="text/javascript">
		var dataObject;
		$(function(){
			$('#dialog').dialog('close');
			dataObject=$("#dataList");
			dataObject.datagrid({
				url:'${ctx}/processFormDefine/commons/myRuningProcessList',
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
					{field:'processDefinitionName',title:'流程名称',width:60},
					{field:'processDefinitionId',title:'流程编号',width:60},
					{field:'processInstanceId',title:'流程实例ID',width:80},
					{field:'startUserId',title:'流程发起人',width:80},
					{field:'startTime',title:'开始日期',width:80,
						formatter : function(value){
							if(value!=""&&value!=null){
								var date = new Date(value);
		                        var y = date.getFullYear();
		                        var m = date.getMonth() + 1;
		                        var d = date.getDate();
		                        var h = date.getHours();  
		                        var i = date.getMinutes();  
		                        var s = date.getSeconds(); 
		                        return y + '-' +m + '-' + d +" " + h + ":" + i + ":" + s;
							}
	                    }
					},
					{field:'endTime',title:'结束日期',width:80,
						formatter : function(value){
							if(value!=""&&value!=null){
								var date = new Date(value);
		                        var y = date.getFullYear();
		                        var m = date.getMonth() + 1;
		                        var d = date.getDate();
		                        var h = date.getHours();  
		                        var i = date.getMinutes();  
		                        var s = date.getSeconds(); 
		                        return y + '-' +m + '-' + d +" " + h + ":" + i + ":" + s;
							}
	                    }
					},
					{field:'操作',title:'操作',width:100,formatter: function(value,row,index){
						var btn ="";
						//<shiro:hasPermission name='sys:dict:edit'>
						btn += "<a class='easyui-linkbutton' style='margin-left:3px;margin-right:3px;' href='javascript:void(0)' onclick='getBackProcess(\""+row.id+"\")'>取回流程</a>";
						btn += "<a class='easyui-linkbutton history' style='margin-left:3px;margin-right:3px;' href='javascript:void(0)' onclick='getHistory(\""+row.id+"\")'>历史</a>";
						//</shiro:hasPermission>
						return btn;
					}
					}
				]],
				toolbar: '#toolbar'
			});
		})

		/* 取回流程 */
		function getBackProcess(id){
			$.messager.confirm('确认','您确认要取回流程吗？',
					function(r) {
						if (r) {
							var url = '${ctx}/processFormDefine/commons/getBackProcess?processInstId='+id;
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
	</script>
</head>
<body>

<table id="dataList"></table>
<div class="pagination">${page}</div>
<%-- <div id="toolbar">
	<form:form id="searchForm" modelAttribute="dict" action="" method="post" class="breadcrumb form-search ">

		<select path="type" style="width: 20%;" label="类型" class="easyui-combobox"
				data-options="multiple:true">
			<option value=""></option>
			<c:forEach items="${typeList}" var="type">
				<option value="${type}">${type}</option>
			</c:forEach>
		</select>
		<input id="description" name="description" data-options="label:'描述'" class="easyui-textbox"
			   value="${user.remarks}" style="width:20%;height:100px">
		<a href="#" class="easyui-linkbutton"  onclick="queryData();"  iconCls="icon-search" plain="true">查询</a>
		<a href="#" class="easyui-linkbutton"  onclick="add('');"  iconCls="icon-add" plain="true">新增</a>
	</form:form>
</div> --%>
</body>
</html>