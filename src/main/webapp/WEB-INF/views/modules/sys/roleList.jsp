]<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>角色管理</title>
	<meta name="decorator" content="easyui"/>
	<script type="text/javascript">
		var dataObject;
		$(function(){
			dataObject=$("#roleList");
			dataObject.datagrid({
				url:'${ctx}/sys/role/dataList',
				rownumbers:true,
				fitColumns:true,
				singleSelect:true,
				fit:true,
				border:true,
				columns:[[
					{field:'id',hidden:true,width:100},
					{field:'name',title:'角色名称',width:100},
					{field:'enname',title:'英文名称',width:100},
					{field:'officeName',title:'归属机构',width:100,
						formatter: function(value,row,index){
							return row.office.name;
						}
					},
					{field:'dataScopeStr',title:'数据范围',width:100},
					{field:'操作',title:'操作',width:100,align:'center',formatter: function(value,row,index){
						var btn="";
						//<shiro:hasPermission name="sys:role:edit"><td>
						btn += "<a href='javascript:void(0);' onclick='edit(\""+row.id+"\")'  class='button-edit  button-info'>修改</a>";
						btn += "<a href='javascript:void(0);' class = 'button-warning' style='margin-left:3px;margin-right:3px;'  onclick='assign(\""+row.id+"\")'>分配</a>";
						btn += "<a class='button-delete button-danger'  href='javascript:void(0)' onclick='deleteById(\""+row.id+"\")'>删除</a>";
						//</shiro:hasPermission>
						return btn;
					}
					}
				]],
				toolbar: '#toolbar',
                onLoadSuccess:function(){
                $('.button-edit').linkbutton({
                });
				$('.button-warning').linkbutton({
				});
                $('.button-delete').linkbutton({
                })
            }
			});
		})

		function add(id){
			$.modalDialog.openner=$("#roleList");
			$.modalDialog({
				title : "新增",
				width : 500,
				height : 500,
				href : "${ctx}/sys/role/form"
			});
		}
		function edit(id){
			$.modalDialog.openner=$("#roleList");
			$.modalDialog({
				title : "编辑",
				width : 500,
				height : 500,
				href : "${ctx}/sys/role/form?id="+id
			});
		}

		function assign(id){
			var href="${ctx}/sys/role/assign?id="+id;
			window.location.href= href;
			/*$.modalDialog.openner=dataObject;
			$.modalDialog({
				title : "分配",
				width : 500,
				height : 500,
				href : "${ctx}/sys/role/assign?id="+id
			});*/
		}

		function deleteById(id){
			$.messager.confirm('确认','您确认想要删除该角色吗？',
					function(r) {
						if (r) {
							var url = '${ctx}/sys/role/delete?id='+id;
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
		
		/* 查询 */
		function queryData(){
			dataObject.datagrid({'queryParams':{
				'roleName':$("#roleName").val()
			}})
		}
		
		/* 重置 */
		function reset(){
			$("#searchForm").form("reset");
		}
	</script>
</head>
<body >
   <table id="roleList" ></table>
	<div id="toolbar">
		<form:form id="searchForm" modelAttribute="role" action="" method="post" class="breadcrumb form-search ">
		<label>角色名称：</label><input class="easyui-textbox" id="roleName" name="roleName" type="text" maxlength="50">
		<a href="#" class="easyui-linkbutton"  onclick="queryData();"  iconCls="icon-search" plain="true">查询</a>
		<a href="#" class="easyui-linkbutton"  onclick="reset();"  iconCls="icon-refresh" plain="true">重置</a>
		<a href="#" class="easyui-linkbutton"  onclick="add('');"  iconCls="icon-add" plain="true">新增</a>
	</form:form>
	</div>
</div>
</body>
</html>