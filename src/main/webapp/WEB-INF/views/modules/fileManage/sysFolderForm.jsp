<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>文件管理</title>
    <meta name="decorator" content="easyui"/>
</head>
<body>
<script type="text/javascript">
    function subForm() {
        var type='${sysFolder.type}';
        $("#inputForm").form('submit', {
            url: '${ctx}/fileManage/sysFolder/save?type='+type,
            onSubmit: function () {
                var validate = $("#inputForm").form("validate");
                if (!validate) {
                    $.messager.show({
                        title: '提示信息',
                        msg: '输入有误，请完善输入信息',
                        timeout: 5000,
                        showType: 'slide'
                    });
                    return false;
                }
                return validate;
            },
            success: function (result) {
             result = JSON.parse(result);
                $.messager.show({
                    title: '提示信息',
                    msg: result.msg,
                    timeout: 1000 * 2
                });
                if (200 == result.code) {
                    $.modalDialog.openner.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_datagrid这个对象，是因为页面预定义好了
                    $.modalDialog.handler.dialog('close');
                }
            }
        });
    }
    function closeForm() {
        $.modalDialog.handler.dialog('close');
    }
</script>

<form:form id="inputForm" modelAttribute="sysFolder"
           action="#" method="post"
           cssStyle="padding:20px;">
    <input type="hidden" id="id" name="id" value="${sysFolder.id}">
    <sys:message content="${message}"/>

    <div style="margin-bottom:20px">
        <select id="parentId" name="parent.id" label="选择上级目录" class="easyui-combotree"
                style="width: 80%;"
                data-options="required:true,
					url:'${ctx}/fileManage/sysFolder/tree',
					multiple:false,
	        		panelHeight:'auto',
                    onLoadSuccess:function(){
				 			$('#parentId').combotree('setValue','${sysFolder.parent.id}');
	             	  		$('#parentId').combotree('tree').tree('expandAll');
	           }">
        </select>
        <span class="help-inline"><font color="red">*</font> </span>
    </div>
    <div style="margin-bottom: 20px">
        <input id="name" data-options="required:true,label:'文&nbsp;&nbsp;&nbsp;件&nbsp;&nbsp;&nbsp;名'"
               name="name" type="text"
               value="${sysFolder.name}" htmlEscape="false" class="easyui-textbox easyui-validatebox"
               style="width:80%">
    </div>
    <div style="margin-bottom:20px">
        <input id="remarks" name="remarks" data-options="label:'文件描述',multiline:true" class="easyui-textbox"
               value="${sysFolder.remarks}" style="width:80%;height:100px">
    </div>
    <div style="margin-bottom:20px;text-align: center">
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="subForm()">保 存</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-undo'" onclick="closeForm()">返回</a>
    </div>
</form:form>
</body>
</html>