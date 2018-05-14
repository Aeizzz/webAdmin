<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>图书详情</title>

</head>
<body>
<script>

    function subForm() {
        $("#inputForm").form('submit', {
            url: '${ctx}/lhl/category/save',
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
<form:form id="inputForm" modelAttribute="category" action="${ctx}/lhl/book/save"
           method="post" cssStyle="padding:20px;">
    <form:hidden path="id"/>
    <div style="margin-bottom: 20px">
        <input id="name"
               data-options="required:true,label:'分类名称'"
               name="name" type="text"
               value="${category.name}" htmlEscape="false" class="easyui-textbox"
               style="width:80%">
        <span class="help-inline"><font color="red">*</font> </span>
    </div>


    <div style="margin-bottom:20px;text-align: center">
        <a href="#" class="easyui-linkbutton"
           data-options="iconCls:'icon-save'" onclick="subForm();">保 存</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-undo'"
           onclick="closeForm()">返回</a>
    </div>
</form:form>
</body>
</html>
