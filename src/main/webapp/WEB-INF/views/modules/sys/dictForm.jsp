<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>字典管理</title>
	<meta name="decorator" content="easyui"/>
	<%--<script type="text/javascript">
		$(document).ready(function() {
			$("#value").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>--%>
</head>
<body>

	<script>
		function subForm(){

			$("#inputForm").form('submit',{
				url : '${ctx}/sys/dict/save',
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
						$.modalDialog.openner.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_datagrid这个对象，是因为页面预定义好了
						$.modalDialog.handler.dialog('close');
					}
				}
			});
		}
		function closeForm(){
			$.modalDialog.handler.dialog('close');
		}
	</script>

	<form:form id="inputForm" modelAttribute="dict" action="" method="post" cssStyle="padding:20px;">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>

		<div style="margin-bottom: 20px">
			<input id="value" data-options="required:true,label:'键&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;值'" name="value" type="text"
				   value="${dict.value}" htmlEscape="false" class="easyui-textbox" style="width:80%">
			<span class="help-inline"><font color="red">*</font> </span>
		</div>

		<div style="margin-bottom: 20px">
			<input id="label" data-options="required:true,label:'标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;签'" name="label" type="text"
				   value="${dict.label}" htmlEscape="false" class="easyui-textbox" style="width:80%">
			<span class="help-inline"><font color="red">*</font> </span>
		</div>

		<div style="margin-bottom: 20px">
			<input id="type" data-options="required:true,label:'类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型'" name="type" type="text"
				   value="${dict.type}" htmlEscape="false" class="easyui-textbox" style="width:80%">
			<span class="help-inline"><font color="red">*</font> </span>
		</div>

		<div style="margin-bottom: 20px">
			<input id="description" data-options="required:true,label:'描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述'" name="description" type="text"
				   value="${dict.description}" htmlEscape="false" class="easyui-textbox" style="width:80%">
			<span class="help-inline"><font color="red">*</font> </span>
		</div>

		<div style="margin-bottom: 20px">
			<input id="sort" data-options="required:true,label:'排&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;序'" name="sort" type="text"
				   value="${dict.sort}" htmlEscape="false" class="easyui-textbox" style="width:80%">
			<span class="help-inline"><font color="red">*</font> </span>
		</div>

		<div style="margin-bottom:20px">
			<input id="remarks" name="remarks" data-options="label:'备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注',multiline:true" class="easyui-textbox"
				   value="${dict.remarks}" style="width:80%;height:100px">
		</div>


		<div style="margin-bottom:20px;text-align: center">
			<shiro:hasPermission name="sys:dict:edit">
				<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="subForm()">保 存</a>
			</shiro:hasPermission>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-undo'" onclick="closeForm()">返回</a>
		</div>
	</form:form>
</body>
</html>