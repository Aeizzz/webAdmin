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
				url:'${ctx}/sys/dict/dataList',
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
					{field:'value',title:'键值',width:60},
					{field:'label',title:'标签',width:60},
					{field:'type',title:'类型',width:80},
					{field:'description',title:'描述',width:80},
					{field:'sort',title:'排序',width:80},
					{field:'操作',title:'操作',width:100,formatter: function(value,row,index){
						var btn ="";
						//<shiro:hasPermission name='sys:dict:edit'>
						btn += "<a href='javascript:void(0);' style='margin-left:3px;margin-right:3px;'  onclick='edit(\""+row.id+"\")'  class=\"easyui-linkbutton\" iconCls=\"icon-edit\" plain=\"true\"'>修改</a>";
						btn += "<a class='easyui-linkbutton' style='margin-left:3px;margin-right:3px;' href='javascript:void(0)' onclick='deleteById(\""+row.id+"\")'>删除</a>";
						btn += "<a class='easyui-linkbutton' style='margin-left:3px;margin-right:3px;' href='javascript:void(0)' onclick='add(\""+row.id+"\")'>添加键值</a>";
						//</shiro:hasPermission>
						return btn;

					}
					}
				]],
				toolbar: '#toolbar'
			});
		})

		function queryData(){

			dataObject.datagrid({'queryParams':{
				'loginName':$("#loginName").val(),
				'name':$("#name").val()
			}})
		}

		function deleteById(id){
			$.messager.confirm('确认','您确认想要删除该数据吗？',
					function(r) {
						if (r) {
							var url = '${ctx}/sys/dict/delete?id='+id;
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


		function add(id){
			$.modalDialog.openner=dataObject;
			$.modalDialog({
				title : "新增",
				width : 500,
				height : 500,
				href : "${ctx}/sys/dict/form"
			});
		}
		function edit(id){
			$.modalDialog.openner=dataObject;
			$.modalDialog({
				title : "编辑",
				width : 500,
				height : 500,
				href : "${ctx}/sys/dict/form?id="+id
			});
		}

	</script>
</head>
<body>

<table id="dataList"></table>
<div id="toolbar">
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
</div>



	<%--<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>键值</th><th>标签</th><th>类型</th><th>描述</th><th>排序</th><shiro:hasPermission name="sys:dict:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="dict">
			<tr>
				<td>${dict.value}</td>
				<td><a href="${ctx}/sys/dict/form?id=${dict.id}">${dict.label}</a></td>
				<td><a href="javascript:" onclick="$('#type').val('${dict.type}');$('#searchForm').submit();return false;">${dict.type}</a></td>
				<td>${dict.description}</td>
				<td>${dict.sort}</td>
				<shiro:hasPermission name="sys:dict:edit"><td>
    				<a href="${ctx}/sys/dict/form?id=${dict.id}">修改</a>
					<a href="${ctx}/sys/dict/delete?id=${dict.id}&type=${dict.type}" onclick="return confirmx('确认要删除该字典吗？', this.href)">删除</a>
    				<a href="<c:url value='${fns:getAdminPath()}/sys/dict/form?type=${dict.type}&sort=${dict.sort+10}'><c:param name='description' value='${dict.description}'/></c:url>">添加键值</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>--%>
	<div class="pagination">${page}</div>
</body>
</html>