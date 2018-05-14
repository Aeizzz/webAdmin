<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>图书详情</title>

</head>
<body>
<script>
    $(function(){
        $.ajax({
        url:"${ctx}/lhl/category/list",
        dataType:"json",
        type:"GET",
        data:{
            "type":"audit_state"
        },
        success:function(data){
            //绑定第一个下拉框
            $('#category').combobox({
                data: data,
                valueField: 'id',
                textField: 'name'}
            );
        },
        error:function(error){
            alert("初始化下拉控件失败");
        }
    })
    $.ajax({
        url:"${ctx}/lhl/publishing/list",
        dataType:"json",
        type:"GET",
        data:{
            "type":"audit_state"
        },
        success:function(data){
            //绑定第一个下拉框
            $('#publishing').combobox({
                data: data,
                valueField: 'id',
                textField: 'name'}
            );
        },
        error:function(error){
            alert("初始化下拉控件失败");
        }
    })
    }
    )


    function subForm() {
        $("#inputForm").form('submit', {
            url: '${ctx}/lhl/publishing/save',
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
<form:form id="inputForm" modelAttribute="publishing" action="${ctx}/lhl/publishing/save"
           method="post" cssStyle="padding:20px;">
    <form:hidden path="id"/>
    <div style="margin-bottom: 20px">
        <input id="name"
               data-options="required:true,label:'出版社名'"
               name="name" type="text"
               value="${publishing.name}" htmlEscape="false" class="easyui-textbox"
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
