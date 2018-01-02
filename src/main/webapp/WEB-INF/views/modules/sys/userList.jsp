<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="easyui"/>
	<script type="text/javascript">
		var dataObject;
		$(function(){
			var id='${user.office.id}';
			var name = '${user.office.name}';
			$('#dialog').dialog('close');
			dataObject=$("#userList");
			dataObject.datagrid({
				url:'${ctx}/sys/user/dataList?office.id='+id+'&office.name='+name,
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
                    {field:'no',title:'员工编号',width:60},
                    {field:'name',title:'姓名',width:80},
					{field:'companyName',title:'归属公司',width:60},
					{field:'officeName',title:'隶属部门',width:60},
					{field:'loginName',title:'登录名',width:80},
//					{field:'roleList',title:'角色',width:80},
					{field:'操作',title:'操作',width:100,formatter: function(value,row,index){
						var btn="";
						//<shiro:hasPermission name="sys:role:edit"><td>
						btn += "<a  class='button-edit  button-info' href='javascript:void(0);' onclick='editUser(\""+row.id+"\")' >编辑</a>";
						btn += "<span style='margin-left:5px;margin-right:5px;'></span>";
						btn += "<a class='button-delete button-danger'  href='javascript:void(0)' onclick='deleteUser(\""+row.id+"\")'>删除</a>";
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
				'loginName':$("#loginName").val(),
				'name':$("#name").val()
			}})
		}

		function deleteUser(userId){
			$.messager.confirm('确认','您确认想要删除该用户吗？',
					function(r) {
						if (r) {
							var url = '${ctx}/sys/user/delete?id='+userId;
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

		function exportUser(){
			$.messager.confirm('确认','确认要导出用户数据吗？',
					function(r) {
						if (r) {
							$("#searchForm").attr("action","${ctx}/sys/user/export");
							$("#searchForm").submit();
						}
					});
		}

		function importUser(){
			$('#dialog').dialog('open');
		}

		function addUser(id){
			$.modalDialog.openner=dataObject;
			$.modalDialog({
				title : "新增",
				width : 500,
				height : 500,
				href : "${ctx}/sys/user/form?"
			});
		}
		function editUser(id){
			$.modalDialog.openner=dataObject;
			$.modalDialog({
				title : "编辑",
				width : 500,
				height : 500,
				href : "${ctx}/sys/user/form?id="+id
			});
		}

	</script>
</head>
<body>
<div  id="dialog" class="easyui-dialog"
	 data-options="title:'导入数据',buttons:'#bb',modal:true">
	<div id="importBox" >
		<form id="importForm" action="${ctx}/sys/user/import" method="post" enctype="multipart/form-data"
			  class="form-search" style="padding-left:20px;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" /><br/><br/>　
			<div style="margin-bottom: 30px">　
			<input id="btnImportSubmit"  type="submit" value="   导    入   "  style="background-color: #2fa4e7;color: white;padding: 8px"/>
			<a href="${ctx}/sys/user/import/template" style="color: #2fa4e7">下载模板</a></div>
		</form>
	</div>
</div>
<div id="bb" style="text-align: left;">
	<span>导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件!</span>
	<a href="#" onclick="$('#dialog').dialog('close');" class="easyui-linkbutton"  style="margin-right: 0px">关闭</a>
</div>



<table id="userList"></table>
<div id="toolbar">
	<form:form id="searchForm" modelAttribute="user" action="" method="post" class="breadcrumb form-search ">
		<label>登录名：</label><input id="loginName" name="loginName" type="text" maxlength="50" class="easyui-textbox" value="${user.loginName}"/>
		<label>姓名：</label><input id="name" name="name" type="text" maxlength="50" class="easyui-textbox" value="${user.name}"/>
		<a href="#" class="easyui-linkbutton"  onclick="queryData();"  iconCls="icon-search" plain="true">查询</a>
		<a href="#" class="easyui-linkbutton"  onclick="addUser('');"  iconCls="icon-add" plain="true">新增</a>
		<a href="#" class="easyui-linkbutton"  onclick="importUser()"  iconCls="icon-undo" plain="true">导入</a>
		<a href="#" class="easyui-linkbutton"  onclick="exportUser()"  iconCls="icon-redo" plain="true">导出</a>
	</form:form>
</div>

</body>
</html>