<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>菜单管理</title>
	<meta name="decorator" content="easyui"/>
	<script type="text/javascript">
		var dataObject;
		$(function(){
			dataObject=$("#dataList");
			dataObject.treegrid({
				url:'${ctx}/sys/menu/dataList',
				fitColumns:true,
				fit:true,
				border:true,
				idField:'id',
				treeField:'name',
				parentField:'parentId',
				columns:[[
					{
						field : "id",
						hidden:true
					},{
						field:'name',
						title:'名称',
						width:100
					},{
						field:'href',
						title:'链接',
						width:100
					},{
						field:'sort',
						title:'排序',
						width:100,
						align:'center'
					},{
						field:'isShow',
						title:'可见',
						width:100,
						align:'center',
						formatter:function(value,row,index){
							if(row.isShow == '1'){
								return "显示";
							}else{
								return "隐藏";
							}
						}

					},{
						field:'permission',
						title:'权限标识',
						width:100
					},{
						field:'操作',
						title:'操作',
						width:100,
						align:'center',
						formatter: function(value,row,index){
							var btn ="";

							btn += "<a class='button-add button-primary'  style='margin-left:3px;margin-right:3px;' href='javascript:void(0)' onclick='add(\""+row.id+"\")'>添加下级菜单</a>";
							btn += "<a href='javascript:void(0);'class='button-edit  button-info' style='margin-left:3px;margin-right:3px;'  onclick='edit(\""+row.id+"\")' >编辑</a>";
							btn += "<a class='button-delete  button-danger' style='margin-left:3px;margin-right:3px;' href='javascript:void(0)' onclick='deleteById(\""+row.id+"\")'>删除</a>";

							return btn;
						}
					}

				]],
                onLoadSuccess:function(){
                    $('.button-add').linkbutton({
                    });
                    $('.button-edit').linkbutton({
                    });
                    $('.button-delete').linkbutton({
                    });
                },
			toolbar: '#toolbar'
			});
		});

		function add(id){
			$.modalDialog.openner=dataObject;
			$.modalDialog({
				title : "新增",
				width : 500,
				height : 500,
				href : "${ctx}/sys/menu/form?parent.id="+id
			});
		}

		function edit(id){
			$.modalDialog.openner=dataObject;
			$.modalDialog({
				title : "编辑",
				width : 500,
				height : 500,
				href : "${ctx}/sys/menu/form?id="+id
			});
		}
		function deleteById(id){
			$.messager.confirm('确认','您确认想要删除该菜单吗？',
					function(r) {
						if (r) {
							var url = '${ctx}/sys/menu/delete?id='+id;
							$.get(url, function(data) {
								if(200==data.code){
									$.messager.show({
										title : '提示信息',
										msg : data.msg,
										timeout : 1000 * 2
									});
									dataObject.treegrid("reload");
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

<div id="toolbar">
	<form:form id="searchForm" modelAttribute="menu" action="" method="post" class="breadcrumb form-search ">
		<a href="#" class="easyui-linkbutton"  onclick="add('');"  iconCls="icon-add" plain="true">新增</a>
	</form:form>
</div>

<%--<form id="listForm" method="post">
		<table id="treeTable" class="table table-striped table-bordered table-condensed hide">
			<thead><tr><th>名称</th><th>链接</th><th style="text-align:center;">排序</th><th>可见</th><th>权限标识</th><shiro:hasPermission name="sys:menu:edit"><th>操作</th></shiro:hasPermission></tr></thead>
			<tbody><c:forEach items="${list}" var="menu">
				<tr id="${menu.id}" pId="${menu.parent.id ne '1'?menu.parent.id:'0'}">
					<td nowrap><i class="icon-${not empty menu.icon?menu.icon:' hide'}"></i><a href="${ctx}/sys/menu/form?id=${menu.id}">${menu.name}</a></td>
					<td title="${menu.href}">${fns:abbr(menu.href,30)}</td>
					<td style="text-align:center;">
						<shiro:hasPermission name="sys:menu:edit">
							<input type="hidden" name="ids" value="${menu.id}"/>
							<input name="sorts" type="text" value="${menu.sort}" style="width:50px;margin:0;padding:0;text-align:center;">
						</shiro:hasPermission><shiro:lacksPermission name="sys:menu:edit">
							${menu.sort}
						</shiro:lacksPermission>
					</td>
					<td>${menu.isShow eq '1'?'显示':'隐藏'}</td>
					<td title="${menu.permission}">${fns:abbr(menu.permission,30)}</td>
					<shiro:hasPermission name="sys:menu:edit"><td nowrap>
						<a href="${ctx}/sys/menu/form?id=${menu.id}">修改</a>
						<a href="${ctx}/sys/menu/delete?id=${menu.id}" onclick="return confirmx('要删除该菜单及所有子菜单项吗？', this.href)">删除</a>
						<a href="${ctx}/sys/menu/form?parent.id=${menu.id}">添加下级菜单</a> 
					</td></shiro:hasPermission>
				</tr>
			</c:forEach></tbody>
		</table>
		<shiro:hasPermission name="sys:menu:edit"><div class="form-actions pagination-left">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保存排序" onclick="updateSort();"/>
		</div></shiro:hasPermission>
	 </form>--%>
</body>
</html>