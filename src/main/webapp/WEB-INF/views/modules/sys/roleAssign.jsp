<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分配角色</title>
	<meta name="decorator" content="easyui"/>
	<style>
		.panel{
			margin-right: 20px;
			font-size: medium;
			font-family: 'Courier New', Courier, monospace
		}
	</style>

	<script>
		var id = ${roleId};
		var roleName = ${roleName};
		var dataObject;
		$(function(){
			dataObject=$("#dataList");
			dataObject.datagrid({
				url:'${ctx}/sys/role/roleUserByPage?id='+id,
				rownumbers:true,
				fitColumns:true,
                pageSize: 30,
                pageList:[30,60,100],
                pagination: true,
				singleSelect:true,
				fit:true,
				border:true,
				columns:[[
					{field:'id',hidden:true,width:100},
					{field:'companyName',title:'归属公司',width:100},
					{field:'officeName',title:'归属部门',width:100},
					{field:'loginName',title:'登录名',width:100},
					{field:'name',title:'姓名',width:100},
					{field:'phone',title:'电话',width:100},
					{field:'mobile',title:'手机',width:100},
					{field:'操作',title:'操作',width:100,align:'center',formatter: function(value,row,index){

						var btn="";
						//<shiro:hasPermission name="sys:role:edit">
						 btn += "<a class='button-delete button-danger'  href='javascript:void(0)' onclick='outrole(\""+row.id+"\",\""+row.name+"\")'>移除</a>";
						//</shiro:hasPermission>
						return btn;
					}
					}
				]],
				toolbar: '#toolbar',
				onLoadSuccess:function () {
                    $('.button-delete').linkbutton({
                    })
                }
			});
		})

		function outrole(userId,name){

			var info="您确认想要把用户"+name+"从角色"+roleName+"中删除吗？";
			$.messager.confirm('确认',info,
					function(r) {
						if (r) {
							var url = '${ctx}/sys/role/outrole?userId='+userId+'&roleId='+id;
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

		<%--function assignUserToRole(){--%>
			<%--$.modalDialog.openner=dataObject;--%>
			<%--$.modalDialog({--%>
				<%--title : "分配角色",--%>
				<%--width : 500,--%>
				<%--height : 500,--%>
				<%--href : "${ctx}/sys/role/usertorole?id=${role.id}"--%>
			<%--});--%>
		<%--}--%>
	</script>
</head>
<body>
<table id="dataList" ></table>

	<div id="toolbar">
		<label>角色名称：</label><input class="easyui-textbox" readonly value="${role.name}" type="text" maxlength="50">
		<label>归属机构：</label><input class="easyui-textbox" readonly value="${role.office.name}" type="text" maxlength="50">
		<label>英文名称：</label><input class="easyui-textbox" readonly value="${role.enname}" type="text" maxlength="50">
		<label>角色类型：</label><input class="easyui-textbox" readonly value="${role.roleType}" type="text" maxlength="50">
		<label>数据范围：</label><input class="easyui-textbox" readonly value="${fns:getDictLabel(dictvalue, 'sys_data_scope', '')}" type="text" maxlength="50">
		<c:set var="dictvalue" value="${role.dataScope}" scope="page" />
		<br/>
		<a href="#" id="assignButton" class="easyui-linkbutton"  data-options="iconCls:'icon-edit'" plain="true">分配角色</a>
		<a href="${ctx}/sys/role/list" class="easyui-linkbutton"  data-options="iconCls:'icon-undo'" plain="true">返回角色管理</a>
	</div>
	<div class="breadcrumb">
		<form id="assignRoleForm" action="${ctx}/sys/role/assignrole" method="post" class="hide">
			<input type="hidden" name="id" value="${role.id}"/>
			<input id="idsArr" type="hidden" name="idsArr" value=""/>
		</form>
	</div>
	<script type="text/javascript">
		$("#assignButton").click(function(){
            top.$.jBox.open("iframe:${ctx}/sys/role/usertorole?id=${role.id}", "分配角色",810,$(top.document).height()-240,{
                buttons:{"确定分配":"ok", "清除已选":"clear", "关闭":true}, bottomText:"通过选择部门，然后为列出的人员分配角色。",submit:function(v, h, f){
                    var pre_ids = h.find("iframe")[0].contentWindow.pre_ids;
                    var ids = h.find("iframe")[0].contentWindow.ids;
                    //nodes = selectedTree.getSelectedNodes();
                    if (v=="ok"){
                        // 删除''的元素
                        if(ids[0]==''){
                            ids.shift();
                            pre_ids.shift();
                        }
                        if(pre_ids.sort().toString() == ids.sort().toString()){
                            top.$.jBox.tip("未给角色【${role.name}】分配新成员！", 'info');
                            return false;
                        };
                        // 执行保存
                        loading('正在提交，请稍等...');
                        var idsArr = "";
                        for (var i = 0; i<ids.length; i++) {
                            idsArr = (idsArr + ids[i]) + (((i + 1)== ids.length) ? '':',');
                        }
                        $('#idsArr').val(idsArr);
                        $('#assignRoleForm').submit();
                        closeLoading();
                        return true;
                    } else if (v=="clear"){
                        h.find("iframe")[0].contentWindow.clearAssign();
                        return false;
                    }
                }, loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                }
            });
        });
	</script>
</body>
</html>
