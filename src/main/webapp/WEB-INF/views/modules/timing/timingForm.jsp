<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/modules/timing/cron.jsp"%>
<html>
<head>
    <title>Title</title>

</head>
<body>
<form:form id="inputForm" modelAttribute="timing" action="${ctx}/timing/timing/save" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div style="margin-bottom: 20px">
        <table width="100%">
            <tr>
                <td>
                    <input id="workId" data-options="label:'任务ID'" name="workId" type="text"
                           value="${timing.workId}" htmlEscape="false" class="easyui-textbox" style="width:80%">
                    <span class="help-inline"><font color="red">*</font> </span>
                </td>
            </tr>
            <tr>
                <td>
                    <input id="content" data-options="label:'任务描述'" name="content" type="text"
                           value="${timing.content}" htmlEscape="false" class="easyui-textbox" style="width:80%">
                    <span class="help-inline"><font color="red">*</font> </span>
                </td>
            </tr>
            <tr>
                <td> <input id="cron"   data-options="label:'cron表达式'," name="cron" type="text"
                            value="${timing.cron}" htmlEscape="false" class="easyui-textbox" style="width:80%">
                    <span class="help-inline"><font color="red">*</font> </span>
                </td>
            </tr>
        </table>

    </div>
    <div style="margin-bottom:20px;text-align: center">
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="subForm()">保 存</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-undo'" onclick="closeForm()">返回</a>
    </div>
</form:form>

<script type="text/javascript">

    $(document).ready(function() {
//        $('#cron').validatebox({
//            required: true,
//            validType: 'crons'
//        });
//        $.extend($.fn.validatebox.defaults.rules, {
//            crons: {
//                validator:cronValidate ,
//                message: 'cron表达式错误.'
//            }
//        });

    });
//

    //提交数据
    function subForm(){
        $("#inputForm").form('submit',{
            url : '${ctx}/timing/timing/save',
            onSubmit : function() {
                var validate = $("#inputForm").form("validate");
                if(cronValidate()){
                    if (!validate) {
                        $.messager.show({
                            title:'提示信息',
                            msg:'输入有误，请完善输入信息',
                            timeout:5000,
                            showType:'slide'
                        });
                        return false;
                    }
                }else{
                    $.messager.show({
                        title:'提示信息',
                        msg:'cron表达式格式错误',
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
    };
    //关闭当前窗口
    function closeForm(){
        $.modalDialog.handler.dialog('close');
    }

</script>
</body>
</html>
