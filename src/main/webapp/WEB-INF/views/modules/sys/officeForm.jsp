<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机构管理</title>
	<meta name="decorator" content="easyui"/>
</head>
<body>
	<script>
		function subForm(){

			$("#inputForm").form('submit',{
				url : '${ctx}/sys/office/save',
				onSubmit : function() {
					var validate = $("#inputForm").form("validate");
					if (!validate) {
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


	<form:form id="inputForm" modelAttribute="office" action="" method="post" cssStyle="padding:20px;">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>


		<div style="margin-bottom:20px">
			 <select id="office_id" name="parent.id" label="上级机构" class="easyui-combotree"
					style="width: 80%;"
					data-options="
					url:'${ctx}/sys/office/treeData',
			 		id:'id',
			 		textField:'name',
			 		editable:true,
					parentField:'pId',
			 		state:'closed',
					multiple:false,
	        		onLoadSuccess:function(){
				 			$('#office_id').combotree('setValue','${office.parent.id}');
	             	  		$('#office_id').combotree('tree').tree('expandAll');

	           }">
			</select>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>

		<%--<div style="margin-bottom:20px">--%>
			<%--<select id="area" name="area.id" label="归属区域" class="easyui-combotree"--%>
					<%--style="width: 80%;"--%>
					<%--data-options="required:true,--%>
					<%--url:'${ctx}/sys/area/treeData',--%>
			 		<%--id:'id',--%>
			 		<%--textField:'name',--%>
					<%--parentField:'pId',--%>
			 		<%--state:'closed',--%>
					<%--multiple:false,--%>
	        		<%--onLoadSuccess:function(){--%>
				 			<%--$('#area').combotree('setValue','${office.area.id}');--%>
	             	  		<%--$('#area').combotree('tree').tree('expandAll');--%>

	           <%--}">--%>
			<%--</select>--%>
			<%--<span class="help-inline"><font color="red">*</font> </span>--%>
		<%--</div>--%>

		<div style="margin-bottom: 20px">
			<input id="name" data-options="required:true,label:'机构名称'" name="name" type="text"
				   value="${office.name}" htmlEscape="false" class="easyui-textbox" style="width:80%">
			<span class="help-inline"><font color="red">*</font> </span>
		</div>

		<div style="margin-bottom: 20px">
			<input id="code" data-options="label:'机构编码'" name="code" type="text"
				   value="${office.code}" htmlEscape="false" class="easyui-textbox"   style="width:80%;">
			<span class="help-inline"><font color="red">*</font> </span>
		</div>

		<div style="margin-bottom: 20px">
			<form:select path="type" style="width: 80%;" label="机构类型" class="easyui-combobox">
				<form:options items="${fns:getDictList('sys_office_type')}"  itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
		</div>

		<div style="margin-bottom: 20px">
			<form:select path="grade" style="width: 80%;" label="机构级别" class="easyui-combobox">
				<form:options items="${fns:getDictList('sys_office_grade')}"  itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
		</div>

		<div style="margin-bottom: 20px">
			<form:select path="useable" style="width: 80%;" label="是否可用" class="easyui-combobox">
				<form:options items="${fns:getDictList('yes_no')}"  itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
		</div>
		<%--<div class="control-group">
			<label class="control-label">主负责人:</label>
			<div class="controls">
				<sys:treeselect id="primaryPerson" name="primaryPerson.id" value="${office.primaryPerson.id}" labelName="office.primaryPerson.name" labelValue="${office.primaryPerson.name}"
								title="用户" url="/sys/office/treeData?type=3" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>--%>
		<%--<div class="control-group">
			<label class="control-label">副负责人:</label>
			<div class="controls">
				<sys:treeselect id="deputyPerson" name="deputyPerson.id" value="${office.deputyPerson.id}" labelName="office.deputyPerson.name" labelValue="${office.deputyPerson.name}"
								title="用户" url="/sys/office/treeData?type=3" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>--%>
		<div style="margin-bottom: 20px">
			<input id="address" data-options=" label:'联系地址'" name="address" type="text"
				   value="${office.address}" htmlEscape="false" class="easyui-textbox"   style="width:80%;">
			<span class="help-inline"><font color="red">*</font> </span>
		</div>

		<div style="margin-bottom: 20px">
			<input id="zipCode" data-options=" label:'邮政编码'" name="zipCode" type="text"
				   value="${office.zipCode}" htmlEscape="false" class="easyui-textbox"   style="width:80%;">
			<span class="help-inline"><font color="red">*</font> </span>
		</div>

		<div style="margin-bottom: 20px">
			<input id="master" data-options=" label:'负 &nbsp;责 &nbsp;人'" name="master" type="text"
				   value="${office.master}" htmlEscape="false" class="easyui-textbox"   style="width:80%;">
			<span class="help-inline"><font color="red">*</font> </span>
		</div>

		<div style="margin-bottom: 20px">
			<input id="phone" data-options=" label:'电&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;话'" name="phone" type="text"
				   value="${office.phone}" htmlEscape="false" class="easyui-textbox"   style="width:80%;">
			<span class="help-inline"><font color="red">*</font> </span>
		</div>

		<div style="margin-bottom: 20px">
			<input id="fax" data-options=" label:'传&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;真'" name="fax" type="text"
				   value="${office.fax}" htmlEscape="false" class="easyui-textbox"   style="width:80%;">
			<span class="help-inline"><font color="red">*</font> </span>
		</div>

		<div style="margin-bottom: 20px">
			<input id="email" data-options=" label:'邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱'" name="email" type="text"
				   value="${office.email}" htmlEscape="false" class="easyui-textbox"   style="width:80%;">
			<span class="help-inline"><font color="red">*</font> </span>
		</div>

		<div style="margin-bottom:20px">
			<input id="remarks" name="remarks" data-options="label:'备注',multiline:true" class="easyui-textbox"
				   value="${office.remarks}" style="width:80%;height:100px">
		</div>

		<c:if test="${empty office.id}">
			<div style="margin-bottom: 20px">
				<form:select path="childDeptList" style="width: 80%;" label="快速添加下级部门" class="easyui-combobox">
					<form:options items="${fns:getDictList('sys_office_common')}"  itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</c:if>
		<div style="margin-bottom:20px;text-align: center">
			<shiro:hasPermission name="sys:office:edit">
				<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="subForm()">保 存</a>
			</shiro:hasPermission>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-undo'" onclick="closeForm()">返回</a>
		</div>




	</form:form>
</body>
</html>