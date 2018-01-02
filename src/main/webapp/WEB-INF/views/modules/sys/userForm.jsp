<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="easyui"/>
</head>
<body>
<script type="text/javascript">
	$.extend($.fn.validatebox.defaults.rules, {
		equals: {
			validator: function(value,param){
				return value == $(param[0]).val();
			},
			message: '输入与上面相同的密码'
		}
	});
	function checkLogin(){
		alert("-----");
	}

	 function subForm(){

		 var loginName = $("#inputForm input[name='loginName']").val();
		 $.post("${ctx}/sys/user/checkLoginName?oldLoginName="+encodeURIComponent('${user.loginName}'+"&loginName="+loginName
		 ),function(data){
			 if(data==false){
				 $.messager.show({
					 title : '提示信息',
					 msg : '用户登录名已存在',
					 timeout : 1000 * 2
				 });
				 return false;
			 }
			 return data;
		 });

		$("#inputForm").form('submit',{
			url : '${ctx}/sys/user/save',
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

	<form:form id="inputForm" modelAttribute="user" action="#" method="post" cssStyle="padding:20px;">
		<input type="hidden" id="id" name="id" value="${user.id}">
		<sys:message content="${message}"/>

		<div style="margin-bottom:20px">
			<select id="company_id" name="company.id" label="归属公司" class="easyui-combotree"
					style="width: 80%;"
					data-options="required:true,
					url:'${ctx}/sys/office/treeData?type=1',
			 		id:'id',
			 		textField:'name',
					parentField:'pId',
			 		state:'closed',
					multiple:false,
	        		panelHeight:'auto',
	        		onLoadSuccess:function(){
				 			$('#company_id').combotree('setValue','${user.company.id}');
	             	  		$('#company_id').combotree('tree').tree('expandAll');
	           }">
			</select>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>

		<div style="margin-bottom:20px">
			<select id="office_id" name="office.id" label="归属部门" class="easyui-combotree"
					style="width: 80%;"
					data-options="required:true,
					url:'${ctx}/sys/office/treeData?type=2',
			 		id:'id',
			 		textField:'name',
			 		parentField:'pId',
			 		state:'closed',
			 		multiple:false,
	        		onLoadSuccess:function(){
				 			$('#office_id').combotree('setValue','${user.office.id }');
	             	  		$('#office_id').combotree('tree').tree('collapseAll');
	                },
	                onSelect:function(node){
	                	<%--var nodeIsLeaf = $('#office_id').combotree('tree').tree('isLeaf', node.target);--%>
	                	<%--if(!nodeIsLeaf){--%>
						   <%--$('#office_id').combotree('clear');--%>
						   <%--$.messager.show({--%>
								<%--title : '提示信息',--%>
								<%--msg : '不能选择父节点，请重新选择！',--%>
								<%--timeout : 1000 * 2--%>
							<%--});--%>
	                	<%--}--%>
	                }
	                ">
			</select>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>

		<div style="margin-bottom: 20px">
			<input id="no" data-options="required:true,label:'工&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号'" name="no" type="text"
				   value="${user.no}" htmlEscape="false" class="easyui-textbox easyui-validatebox" maxlength="50" style="width:80%;">
			<span class="help-inline"><font color="red">*</font> </span>
		</div>

		<div style="margin-bottom: 20px">
			<input id="name" data-options="required:true,label:'姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名'" name="name" type="text"
				   value="${user.name}" htmlEscape="false" class="easyui-textbox easyui-validatebox"
				    style="width:80%">
		</div>
		<div style="margin-bottom: 20px">
			<input id="oldLoginName" name="oldLoginName" type="hidden" value="${user.loginName}">
			<input id="loginName" name="loginName" data-options="required:true,label:'登&nbsp;录&nbsp;名'" type="text"
						value="${user.loginName}"  class="easyui-textbox easyui-validatebox" style="width:80%"/>
		</div>

		<div style="margin-bottom: 20px">
			<input id="newPassword" data-options="label:'密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码',
			<c:if test="${empty user.id}">required:true</c:if>" name="newPassword" type="text"
				   value="${user.newPassword}" htmlEscape="false" class="easyui-textbox easyui-validatebox" style="width:80%">
			<c:if test="${empty user.id}"><span class="help-inline"><font color="red">*</font> </span></c:if>
			<c:if test="${not empty user.id}"> <span class="help-inline">若不修改密码，请留空。</span></c:if>

		</div>

		<div style="margin-bottom: 20px">
			<input id="confirmNewPassword"  autocomplete="off"  data-options="label:'确认密码',
			<c:if test="${empty user.id}">required:true</c:if>"
				   name="confirmNewPassword" type="text"
				   value=""   validType="equals['#newPassword']"    htmlEscape="false" class="easyui-textbox easyui-validatebox" style="width:80%">

			<c:if test="${empty user.id}"><span class="help-inline"><font color="red">*</font> </span></c:if>
		</div>

		<div style="margin-bottom: 20px">
			<input id="email" data-options="label:'邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱'" name="email" type="text"
				   value="${user.email}" htmlEscape="false" class="easyui-textbox easyui-validatebox "
				   data-options="validType:'email'"  style="width:80%">
		</div>
		<div style="margin-bottom: 20px">
			<input id="phone" data-options="label:'电&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;话'" name="phone" type="text"
				   value="${user.phone}" htmlEscape="false" class="easyui-textbox" style="width:80%">
		</div>
		<div style="margin-bottom: 20px">
			<input id="mobile" data-options="label:'手&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机'" name="mobile" type="text"
				   value="${user.mobile}" htmlEscape="false" class="easyui-textbox" style="width:80%">
		</div>

		<div style="margin-bottom: 20px">
			<form:select path="loginFlag" style="width: 80%;" label="是否允许登录" class="easyui-combobox">
				<form:options items="${fns:getDictList('yes_no')}"  itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
				<br>
			<span class="help-inline"><font color="red">*</font> “是”代表此账号允许登录，“否”则表示此账号不允许登录</span>
		</div>



		<div style="margin-bottom: 20px">
			<label>用户角色</label>
			<%--<select name="" style="width: 80%;" label="用户角色" class="easyui-combobox"
					data-options="required:true,multiple:true">
				<option value=""></option>
				<c:forEach items="${allRoles}" var="role">
					<option value="${role.id}">${role.name}</option>
				</c:forEach>
			</select>--%>
			<form:checkboxes path="roleIdList" items="${allRoles}" itemLabel="name"
							 itemValue="id" htmlEscape="false"/>
			<span class="help-inline"><font color="red">*</font> </span>

		</div>

		<div style="margin-bottom:20px">
			<input id="remarks" name="remarks" data-options="label:'备注',multiline:true" class="easyui-textbox"
				   value="${user.remarks}" style="width:80%;height:100px">
		</div>


		<c:if test="${not empty user.id}">
		<div style="margin-bottom:20px">
			<input  data-options="label:'创建时间'" class="easyui-datebox"
				   value="${user.createDate}" style="width:80%;" type="text" >
		</div>

			<div style="margin-bottom:20px">
				<input  data-options="label:'最后登陆IP ${user.loginIp}'" class="easyui-datebox"
						value="${user.loginDate}" style="width:80%;" type="text" >
			</div>
		</c:if>

		<div style="margin-bottom:20px;text-align: center">
			<shiro:hasPermission name="sys:user:edit">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="subForm()">保 存</a>
			</shiro:hasPermission>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-undo'" onclick="closeForm()">返回</a>
		</div>


	</form:form>
</body>
</html>