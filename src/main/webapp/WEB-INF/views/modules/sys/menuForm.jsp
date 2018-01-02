<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<script>
		function subForm(){
			$("#inputForm").form('submit',{
				url : '${ctx}/sys/menu/save',
				onSubmit : function() {
					/*$.messager.progress({
					 title : '提示',
					 text : '数据处理中，请稍后....'
					 });*/
					var validate = $("#inputForm").form("validate");
					if (!validate) {
						//$.messager.progress("close");
						$.messager.show({
							title:'提示信息',
							msg:'输入有误，请完善输入信息',
							timeout:5000,
							showType:'slide'
						});
						return false;
					}

					return validate;
				},
				success : function(result) {
					result=JSON.parse(result);
					$.messager.show({
						title : '提示信息',
						msg : result.msg,
						timeout : 1000 * 2
					});
					if(200==result.code){
						$.modalDialog.openner.treegrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_datagrid这个对象，是因为页面预定义好了
						$.modalDialog.handler.dialog('close');
					}
				}
			});
		}
		function closeForm(){
			$.modalDialog.handler.dialog('close');
		}
	</script>


	<form:form id="inputForm" modelAttribute="menu" action="${ctx}/sys/menu/save" method="post" cssStyle="padding:20px;">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>

		<div style="margin-bottom:20px">
			<select id="menu_id" name="parent.id" label="上级机构" class="easyui-combotree"
					style="width: 80%;"
					data-options="required:true,
					url:'${ctx}/sys/menu/treeData',
			 		id:'id',
			 		textField:'name',
			 		editable:true,
					parentField:'pId',
			 		state:'closed',
					multiple:false,
	        		onLoadSuccess:function(){
				 			$('#menu_id').combotree('setValue','${menu.parent.id}');
	             	  		$('#menu_id').combotree('tree').tree('expandAll');

	           }">
			</select>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>




		<div style="margin-bottom: 20px">
			<input id="name" data-options="required:true,label:'名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称'" name="name" type="text"
				   value="${menu.name}" htmlEscape="false" class="easyui-textbox" style="width:80%">
			<span class="help-inline"><font color="red">*</font> </span>
		</div>


		<div style="margin-bottom: 20px">
			<input id="href" data-options="label:'链&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;接'" name="href" type="text"
				   value="${menu.href}" htmlEscape="false" class="easyui-textbox" style="width:80%">

			<%--<span class="help-inline">点击菜单跳转的页面</span>--%>
		</div>

		<div style="margin-bottom: 20px">
			<input id="target" data-options="label:'目&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;标'" name="target" type="text"
				   value="${menu.target}" htmlEscape="false" class="easyui-textbox" style="width:80%">

			<%--<span class="help-inline">链接地址打开的目标窗口，默认：mainFrame</span>--%>
		</div>

		<div style="margin-bottom: 20px">
			<label>图标:</label>
			 <sys:iconselect id="icon" name="icon" value="${menu.icon}"/>
		</div>


		<div style="margin-bottom: 20px">
			<input id="sort" data-options="label:'排&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;序'" name="sort" type="text"
				   value="${menu.sort}" htmlEscape="false" class="easyui-textbox" style="width:80%">

			<%--<span class="help-inline">排列顺序，升序。</span>--%>
		</div>


		<div style="margin-bottom: 20px">
			<label>可见:</label>
			 <form:radiobuttons path="isShow" items="${fns:getDictList('show_hide')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
			<%--<span class="help-inline">该菜单或操作是否显示到系统菜单中</span>--%>
		</div>


		<div style="margin-bottom: 20px">
			<input id="permission" data-options="label:'权限标识'" name="permission" type="text"
				   value="${menu.permission}" htmlEscape="false" class="easyui-textbox" style="width:80%">
			<%--<span class="help-inline">控制器中定义的权限标识，如：@RequiresPermissions("权限标识")</span>--%>
		</div>



		<div style="margin-bottom:20px">
			<input id="remarks" name="remarks" data-options="label:'备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注',multiline:true" class="easyui-textbox"
				   value="${menu.remarks}" style="width:80%;height:100px">
		</div>



		<div style="margin-bottom:20px;text-align: center">
			<shiro:hasPermission name="sys:menu:edit">
				<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="subForm()">保 存</a>
			</shiro:hasPermission>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-undo'" onclick="closeForm()">返回</a>
		</div>
	</form:form>
