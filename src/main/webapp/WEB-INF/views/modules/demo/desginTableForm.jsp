<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script type="text/javascript">
    var datagrid;
    var fields;
    $(function(){
        // var datagrid; 定义全局变量datagrid
        var editRow = undefined; //定义全局变量：当前编辑的行
        var tableName='${desginTable.tableName}';

        $('#dialog').dialog('close');
        var formData=[{
            "id":"text",
            "text":"text"
        },{
            "id":"select",
            "text":"select"
        },{
            "id":"radio",
            "text":"radio"
        },{
            "id":" textarea",
            "text":" textarea"
        }
        ];
        var json=[{
                    "id":"varchar",
                    "text":"varchar"
                },{
                    "id":"datetime",
                    "text":"datetime"
                },{
                    "id":"int",
                    "text":"int"
                },{
                "id":"double",
                "text":"double"
              },
            {
                "id":"text",
                "text":"text"
            }
        ];
        datagrid=$("#fieldList");
        datagrid.datagrid({
            url:'${ctx}/demo/desginField/dataList?tableId='+tableName,
            rownumbers:true,
            fitColumns:true,
            singleSelect:true,
            fit:true,
            border:true,
            pagination:true,
            remoteSort:true,
            pageSize:30,
            pageList:[30,60,100],
            idField:'id',
            columns:[[
                {field:'id',hidden:true,width:50},
                {field:'tableId',hidden:true,width:50},
                {field:'fieldName',title:'字段名',width:60, editor: { type: 'validatebox', options: { required: true}} },
                {field:'type',title:'字段类型',width:60,
                    editor:{
                        type:'combobox',
                        options:{
                            valueField:'id',
                            textField:'text',
                            data:json,
                            required:true
                        }
                    }
                },
                {field:'length',title:'字段长度',width:60,editor: { type: 'numberbox', options: { required: false}}},
                {field:'showType',title:'页面字段类型',width:60,
                    editor:{
                        type:'combobox',
                        options:{
                            valueField:'id',
                            textField:'text',
                            data:formData,
                            required:true
                        }
                    }
                },
                {field:'content',title:'字段描述',width:60,editor: { type: 'validatebox', options: { required: true}}}
            ]],
            toolbar: [{ text: '添加', iconCls: 'icon-add', handler: function () {//添加列表的操作按钮添加，修改，删除等
                //添加时先判断是否有开启编辑的行，如果有则把开户编辑的那行结束编辑
                if (editRow != undefined) {
                    datagrid.datagrid("endEdit", editRow);
                }
                //添加时如果没有正在编辑的行，则在datagrid的第一行插入一行
                if (editRow == undefined) {
                    var data=datagrid.datagrid('getData');
                    var num=data.rows.length
                  //  alert('总数据量:'+data.total)//注意你的数据源一定要定义了total，要不会为undefined，datagrid分页就是靠这个total定义
                //    alert('当前页数据量:'+data.rows.length)
                    datagrid.datagrid("insertRow", {
                        index: num, // index start with 0
                        row: {

                        }
                    });
                    //将新插入的那一行开户编辑状态
                    datagrid.datagrid("beginEdit", num);
                    //给当前编辑的行赋值
                    editRow = num;
                }
            }
            }, '-',
                { text: '删除', iconCls: 'icon-remove', handler: function () {
                    //删除时先获取选择行
                    var row = datagrid.datagrid("getSelected");
                var  Id=row.id;

                  if(row){
                      var rowIndex =datagrid.datagrid('getRowIndex', row);

                      datagrid.datagrid('deleteRow', rowIndex);
                      if (Id) {
                          $.messager.confirm("提示", "你确定要删除吗?",
                              function (r) {
                              if(r){
                                  var url = "${ctx}/demo/desginField/delete?Id="+Id;
                                  $.get(url, function(data) {
                                      if(200==data.code){
                                          $.messager.show({
                                              title : '提示信息',
                                              msg : data.msg,
                                              timeout : 1000 * 2
                                          });
                                          datagrid.datagrid("reload");
                                      } else {
                                          $.messager.show({
                                              title : '提示信息',
                                              msg : data.msg,
                                              timeout : 1000 * 2
                                          });
                                      }
                                  } );
                              }
                              });
                          datagrid.datagrid("reload");
                      }

                  }
                    else {
                        $.messager.alert("提示", "请选择要删除的行", "error");
                    }
                }
                }, '-',
                { text: '修改', iconCls: 'icon-edit', handler: function () {
                    //修改时要获取选择到的行
                    var rows = datagrid.datagrid("getSelections");
                    //如果只选择了一行则可以进行修改，否则不操作
                    if (rows.length == 1) {
                        //修改之前先关闭已经开启的编辑行，当调用endEdit该方法时会触发onAfterEdit事件
                        if (editRow != undefined) {
                            datagrid.datagrid("endEdit", editRow);
                        }
                        //当无编辑行时
                        if (editRow == undefined) {
                            //获取到当前选择行的下标
                            var index = datagrid.datagrid("getRowIndex", rows[0]);
                            //开启编辑
                            datagrid.datagrid("beginEdit", index);
                            //把当前开启编辑的行赋值给全局变量editRow
                            editRow = index;
                            //当开启了当前选择行的编辑状态之后，
                            //应该取消当前列表的所有选择行，要不然双击之后无法再选择其他行进行编辑
                            datagrid.datagrid("unselectAll");
                        }
                    }else{
                        $.messager.alert("提示", "请选择一条要修改的行", "error");
                    }
                }
                }, '-',
                { text: '结束编辑', iconCls: 'icon-save', handler: function () {
                    //保存时结束当前编辑的行，自动触发onAfterEdit事件如果要与后台交互可将数据通过Ajax提交后台
                    datagrid.datagrid("endEdit", editRow);
                }
                }, '-',
                { text: '取消编辑', iconCls: 'icon-redo', handler: function () {
                    //取消当前编辑行把当前编辑行罢undefined回滚改变的数据,取消选择的行
                    editRow = undefined;
                    datagrid.datagrid("rejectChanges");
                    datagrid.datagrid("unselectAll");
                }
                }, '-'],
            onAfterEdit: function (rowIndex, rowData, changes) {

                editRow = undefined;
            },
            onDblClickRow: function (rowIndex, rowData) {
                //双击开启编辑行
                if (editRow != undefined) {
                    datagrid.datagrid("endEdit", editRow);
                }
                if (editRow == undefined) {
                    datagrid.datagrid("beginEdit", rowIndex);
                    editRow = rowIndex;
                }
            }
        });
    });

    function subForm(){
        fields=$('#fieldList').datagrid('getRows');
        $("#inputForm").form('submit',{
            url : '${ctx}/demo/desginTable/save',
            onSubmit : function() {
                $("#desginFieldList").val(JSON.stringify(fields));
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
                    $.modalDialog.openner.datagrid('reload');
                    $.modalDialog.handler.dialog('close');
                }
            }
        });
    }
    function closeForm(){
        $.modalDialog.handler.dialog('close');
    }
</script>


<form:form id="inputForm" modelAttribute="desginTable" action="${ctx}/demo/desginTable/save" method="post" cssStyle="padding:20px;">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <table width="100%">
        <tr>
            <td> <div style="margin-bottom: 20px">
                <input id="tableName" data-options="required:true,label:'表名'" name="tableName" type="text"
                       value="${desginTable.tableName}" htmlEscape="false" class="easyui-textbox" style="width:80%">
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
            </td>
            <td> <div style="margin-bottom: 20px">
                <input id="remarks" data-options="label:'表描述'" name="remarks" type="text"
                       value="${desginTable.remarks}" htmlEscape="false" class="easyui-textbox" style="width:80%">
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
            </td>
        </tr>
        <tr>
            <td><div style="margin-bottom: 20px">
                <input id="formVersion" data-options="label:'表单版本号'" name="formVersion" type="text"
                       value="${desginTable.formVersion}" htmlEscape="false" class="easyui-textbox" style="width:80%">
                <span class="help-inline"><font color="red">*</font> </span>
            </div></td>
            <td> <div style="margin-bottom: 20px">
                <input id="querymode" data-options="label:'查询模式'" name="querymode" type="text"
                       value="${desginTable.querymode}" htmlEscape="false" class="easyui-textbox" style="width:80%">
                <span class="help-inline"><font color="red">*</font> </span>

            </div>

            </td>
        </tr>
        <tr>
            <td>  <div style="margin-bottom: 20px">
                <input id="isCheckbox" data-options="label:'是否带checkbox'" name="isCheckbox" type="text"
                       value="${desginTable.isCheckbox}" htmlEscape="false" class="easyui-textbox" style="width:80%">
                <span class="help-inline"><font color="red">*</font> </span>
            </div></td>
            <td>  </td>
        </tr>
    </table>

    <table id="fieldList">
    </table>
    <input id="desginFieldList" name="desginFieldList" type="text"
           htmlEscape="false" hidden="true">

    <br>

    <div style="margin-bottom:20px;text-align: center">
        <shiro:hasPermission name="sys:menu:edit">
            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="subForm()">保 存</a>
        </shiro:hasPermission>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-undo'" onclick="closeForm()">返回</a>
    </div>
</form:form>
