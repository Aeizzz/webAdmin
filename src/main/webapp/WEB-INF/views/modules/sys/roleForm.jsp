<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script type="text/javascript">
        function subForm(){
            $("#inputForm").form('submit',{
                url : '${ctx}/sys/role/save',
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

<form:form id="inputForm" modelAttribute="role" action="#" method="post"
	cssStyle="padding:20px;">

	<input type="hidden" id="id" name="id" value="${role.id}">
	<sys:message content="${message}" />

	<div style="margin-bottom:20px">
		<select id="office_id" name="office.id" label="归属机构"
			class="easyui-combotree" style="width: 80%;"
			data-options="required:true,
					url:'${ctx}/sys/office/treeData',
			 		id:'id',
			 		textField:'name',
					parentField:'pId',
			 		state:'closed',
					multiple:false,
	        		onLoadSuccess:function(){
				 			$('#office_id').combotree('setValue','${role.office.id}');
	             	  		$('#office_id').combotree('tree').tree('expandAll');
	           }">
		</select> <span class="help-inline"><font color="red">*</font> </span>
	</div>




	<div style="margin-bottom: 20px">
		<input id="oldName" name="oldName" type="hidden" value="${role.name}">
		<input id="name" data-options="required:true,label:'角色名称'" name="name"
			type="text" value="${role.name}" htmlEscape="false"
			class="easyui-textbox" style="width:80%"> <span
			class="help-inline"><font color="red">*</font> </span>
	</div>


	<div style="margin-bottom: 20px">
		<input id="oldEnname" name="oldEnname" type="hidden"
			value="${role.enname}"> <input id="enname"
			data-options="required:true,label:'英文名称'" name="enname" type="text"
			value="${role.enname}" htmlEscape="false" class="easyui-textbox"
			style="width:80%"> <span class="help-inline"><font
			color="red">*</font> </span>
	</div>



	<div style="margin-bottom: 20px">
		<form:select path="roleType" style="width: 80%;" label="角色类型"
			class="easyui-combobox" data-options="multiple:true">
			<option value="assignment">任务分配</option>
			<option value="security-role">管理角色</option>
			<option value="user">普通角色</option>
		</form:select>
		<span class="help-inline"><font color="red">*</font> </span>

	</div>



	<div style="margin-bottom: 20px">
		<form:select path="sysData" style="width: 80%;" label="是否系统数据"
			class="easyui-combobox">
			<form:options items="${fns:getDictList('yes_no')}" itemLabel="label"
				itemValue="value" htmlEscape="false" />
		</form:select>
	</div>



	<div style="margin-bottom: 20px">
		<form:select path="useable" style="width: 80%;" label="是否可用"
			class="easyui-combobox">
			<form:options items="${fns:getDictList('yes_no')}" itemLabel="label"
				itemValue="value" htmlEscape="false" />
		</form:select>
	</div>


	<div style="margin-bottom: 20px">
		<form:select path="dataScope" style="width: 80%;" label="数据范围"
			class="easyui-combobox">
			<form:options items="${fns:getDictList('sys_data_scope')}"
				itemLabel="label" itemValue="value" htmlEscape="false" />
		</form:select>
	</div>


	<div style="margin-bottom:20px">
		<select id="menuIds" name="menuIds" label="角色授权"
			class="easyui-combotree" style="width: 80%;"
			data-options="required:true,
					url:'${ctx}/sys/menu/treeData',
			 		id:'id',
			 		textField:'name',
					parentField:'pId',
			 		state:'closed',
					multiple:true,
					cascadeCheck: false,
                	onCheck: function (node, checked) {
                    if (checked) {
                        var parentNode = $('#menuIds').combotree('tree').tree('getParent', node.target);
                        if (parentNode != null) {
                           $('#menuIds').combotree('tree').tree('check', parentNode.target);
                        }
                    } else {
                        var childNode = $('#menuIds').combotree('tree').tree('getChildren', node.target);
                        if (childNode.length > 0) {
                            for (var i = 0; i < childNode.length; i++) {
                                $('#menuIds').combotree('tree').tree('uncheck', childNode[i].target);
                            }
                        }
                    }
                },
	        		onLoadSuccess:function(){
				 			$('#menuIds').combotree('setValues','${role.menuIds}');
	             	  		$('#menuIds').combotree('tree').tree('expandAll');
	           }">
		</select><span class="help-inline"><font color="red">*</font> </span>
	</div>


	<div style="margin-bottom:20px">
		<input id="remarks" name="remarks"
			data-options="label:'备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注',multiline:true"
			class="easyui-textbox" value="${role.remarks}"
			style="width:80%;height:100px">
	</div>



	<div style="margin-bottom:20px;text-align: center">
		<c:if
			test="${(role.sysData eq fns:getDictValue('是', 'yes_no', '1') && fns:getUser().admin)||!(role.sysData eq fns:getDictValue('是', 'yes_no', '1'))}">

			<shiro:hasPermission name="sys:role:edit">
				<a href="#" class="easyui-linkbutton"
					data-options="iconCls:'icon-save'" onclick="subForm()">保 存</a>
			</shiro:hasPermission>
		</c:if>
		<a href="#" class="easyui-linkbutton"
			data-options="iconCls:'icon-undo'" onclick="closeForm()">返回</a>
	</div>

</form:form>
