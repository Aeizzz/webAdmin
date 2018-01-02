<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>表单模版</title>
<meta name="decorator" content="easyui"/>
<script type="text/javascript">
		$(document).ready(function() {

		});
        //提交数据
        function subForm(){
            $("#inputForm").form('submit',{
                url : '${ctx}/sysForm/save',
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
                    }
                }
            });
        };


	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="sysForm" action="${ctx}/sysForm/save" method="post" class="form-horizontal">
        <form:hidden path="id"/>
        <sys:message content="${message}"/>
        <input id="templateName" data-options="label:'模板名称'" name="templateName" type="text"
               value="${sysForm.templateName}" htmlEscape="false" class="easyui-textbox" style="width:80%">
        <span class="help-inline"><font color="red">*</font> </span>
        <textarea name="ftlContent" id="ftlContent"></textarea>
        <sys:ckeditor replace="ftlContent" uploadPath="/cms/article" />

        <div style="margin-bottom:20px;text-align: center">
            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="subForm()">保 存</a>
            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-undo'" onclick="closeForm()">返回</a>
        </div>
	</form:form>
</body>
</html>