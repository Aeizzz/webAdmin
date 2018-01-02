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
        $("#inputForm").form('submit', {
            url: '${ctx}/fileManage/sysFile/save',
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

<form:form id="inputForm" modelAttribute="sysFile"
           enctype="multipart/form-data"
           action="#"
           method="post"
           cssStyle="padding:20px;" >
    <input type="hidden" id="parentId" name="parentId" value="${sysFolder.id}">
    <input type="hidden" id="parentIds" name="parentIds" value="${sysFolder.parentIds}">
    <sys:message content="${message}"/>

    <div style="margin-bottom: 20px">
        <input id="file" data-options="required:true,prompt:'请选择文件'"
               name="file" type="file"
               htmlEscape="false" class="easyui-validatebox"
               style="width:80%">
    </div>
    <div style="margin-bottom:20px;text-align: center">
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="subForm()">保 存</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-undo'" onclick="closeForm()">返回</a>
    </div>
</form:form>
</body>
</html>