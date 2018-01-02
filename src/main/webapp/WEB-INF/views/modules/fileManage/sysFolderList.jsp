<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title></title>
    <meta name="decorator" content="easyui"/>
    <script type="text/javascript">
        var dataGrid;
        var id = '${sysFolder.id}';
        var url;

        $(document).ready(function () {
            dataGrid = $("#dataList");
            init(dataGrid);
            dataTree = $("#tree");
            initTree(dataTree);
        });
//        初始化树
        function initTree(dataTree,type) {
            dataTree.tree({
                url: '${ctx}/fileManage/sysFolder/tree',
                onBeforeExpand: function (node) {
                    if (node) {
                        dataTree.tree('options').url = "${ctx}/fileManage/sysFolder/tree";
                    }
                },
                onClick: function (node) {
                    if (node) {
                        id = node.id;
                        if("move"==type){

                        }else{
                            url = '${ctx}/fileManage/sysFolder/datalist?id=' + id;
                            init(dataGrid);
                            dataGrid.datagrid("clearSelections");
                        }
                        dataTree.tree('expand', node.target);
                    }
                }
            });
        }
//        初始化表格
        function init(dataGrid) {
            dataGrid.datagrid({
                url: url,
                checkOnSelect: true,
                fitColumns: true,
                singleSelect: false,
                fit: true,
                border: false,
                pagination: true,
                remoteSort: true,
                pageSize: 30,
                pageList: [30, 60, 100],
                idField: 'id',
                columns: [[
                    {field: 'ck', checkbox: true},
                    {field: 'id', hidden: true},
                    {field: 'type', hidden: true},
                    {field: 'name', title: '文件名', width: 50,align:'center'},
                    {field: 'createName', title: '创建人', width: 30,align:'center'},
                    {field: 'createDate', title: '创建时间', width: 60,align:'center'},
                    {field: 'fileSize', title: '大小', width:40,align:'center',
                        formatter: function (value, row, index) {
                            var i = 0;
                            if (row.fileSize) {
                                while (value > 1024) {
                                    value = value / 1024;
                                    i++;
                                }
                                value = toDecimal(value);
                                switch (i) {
                                    case 0:
                                        return value + "B";
                                    case 1:
                                        return value + "KB";
                                    case 2:
                                        return value + "MB";
                                    case 3:
                                        return value + "GB";
                                    default:
                                        return value;
                                }
                            } else {
                                return 0;
                            }
                        }
                    },
                    {field: 'version', title: '版本号', width: 20,align:'center'},
                    {field: 'clickNum', title: '点击量', width: 20,align:'center'},
                    {field: 'downloadNum', title: '下载量', width: 20,align:'center'},
                    {field: '操作', title: '操作', width: 100, formatter: function (value, row, index) {
                        var btn = "";
                        btn += "<a  class='button-edit  button-info' href='javascript:void(0);' onclick='editData(\"" + row.id + "\",\"" + row.type + "\")' >编辑</a>";
                        if (row.type == 1) {
                            btn += "<span style='margin-left:15px;margin-right:15px;'></span>";
                            btn += "<a class='button-delete button-danger'  href='javascript:void(0);' onclick='down(\"" + row.id + "\")'>下载</a>";
                        }
                        return btn;
                    }
                    }
                ]],
                onDblClickRow: function (rowIndex, rowData) {
                    id = rowData.id;
                    if (rowData.type == 0) {
                        url = '${ctx}/fileManage/sysFolder/datalist?id=' + id;
                        init(dataGrid);
                        dataGrid.datagrid("clearSelections");
                    } else {
                    //文件点击查看
                    }
                    addRecord(id, rowData.type);

                },
                onLoadSuccess: function () {
                    $('.button-edit').linkbutton({});
                    $('.button-delete').linkbutton({});
                },
                toolbar: '#toolbar'
            });
        }
//        增加点击或下载记录
        function addRecord(id, type) {
            $.post("${ctx}/fileManage/sysFolder/addRecord?id=" + id + "&type=" + type
            )
        }
        function down(id) {
            window.location.href = "${ctx}/fileManage/sysFile/download?id=" + id
        }
        //新增数据
        function addData() {
            $.modalDialog.openner = dataGrid;
            $.modalDialog({
                title: "新建文件夹",
                width: 500,
                height: 500,
                href: "${ctx}/fileManage/sysFolder/form"
            });
        }
        //打开移动窗口，并初始化
        function move() {
            var rows = $("#dataList").datagrid('getSelections');
            if(rows.length>0){
                $('#moveWin').window('open');
                dataTree = $("#moveTree");
                initTree(dataTree,"move");
            }else{
                $.messager.alert("提示", "请选择要移动的文件或文件夹", "error");
            }

        }
//       保存移动
        function moveSave(){
            var rows = $("#dataList").datagrid('getSelections');
            var fileIds = "";
            var folderIds = "";
//            将文件和文件夹分类
            if (rows.length > 0) {
                for (var i = 0; i < rows.length; i++) {
                    if (rows[i].type == "0") {
                        folderIds += rows[i].id + ",";
                    } else {
                        fileIds += rows[i].id + ",";
                    }
                }
            } else {
                alert("移动目标消失");
                return false;
            }
            var treeRow=$("#moveTree").tree('getSelected');
            if(treeRow){
                var url = '${ctx}/fileManage/sysFolder/moveSave?folderIds=' + folderIds + "&fileIds=" + fileIds+"&parentId=" + treeRow.id;
                $.get(url, function (data) {
                    if (200 == data.code) {
                        $.messager.show({
                            title: '提示信息',
                            msg: data.msg,
                            timeout: 1000 * 2
                        });
                        dataGrid.datagrid("reload");
                        $('#tree').tree('reload');

                    } else {
                        $.messager.show({
                            title: '提示信息',
                            msg: data.msg,
                            timeout: 1000 * 2
                        });
                        dataGrid.datagrid("clearSelections");
                        dataGrid.datagrid("reload");
                        $('#tree').tree('reload');
                    }
                });
                $('#moveWin').window('close');
            }else{
                $.messager.alert("提示", "请选择要移动的目标目录", "error");
            }
        }
        function moveCancel(){
            $('#moveWin').window('close');
        }
        //编辑
        function editData(id, type) {
            $.modalDialog.openner = dataGrid;
            $.modalDialog({
                title: "编辑",
                width: 500,
                height: 500,
                href: "${ctx}/fileManage/sysFolder/form?id=" + id+"&type="+type
            });
            $('#tree').tree('reload');
        }
        //删除
        function deleteDate() {
            var rows = $("#dataList").datagrid('getSelections');
            var fileIds = "";
            var folderIds = "";
            if (rows.length > 0) {
                for (var i = 0; i < rows.length; i++) {
                    if (rows[i].type == "0") {
                        folderIds += rows[i].id + ",";
                    } else {
                        fileIds += rows[i].id + ",";
                    }
                }
                $.messager.confirm('确认', '您确认要删除该文件吗？',
                    function (r) {
                        if (r) {
                            if (folderIds != "" || fileIds != "") {
                                var url = '${ctx}/fileManage/sysFolder/delete?folderIds=' + folderIds + "&fileIds=" + fileIds;
                                deleteFile(url);
                                dataGrid.datagrid("clearSelections");
                            }
                        }
                    });
            }
            else {
                $.messager.alert("提示", "请选择要删除的文件", "error");
            }

        }
        function deleteFile(url) {
            $.get(url, function (data) {
                if (200 == data.code) {
                    $.messager.show({
                        title: '提示信息',
                        msg: data.msg,
                        timeout: 1000 * 2
                    });
                    dataGrid.datagrid("reload");
                    $('#tree').tree('reload');
                } else {
                    $.messager.show({
                        title: '提示信息',
                        msg: data.msg,
                        timeout: 1000 * 2
                    });
                }
            });
        }
//        上传文件
        function upload() {
            $.modalDialog.openner = dataGrid;
            $.modalDialog({
                title: "上传文件",
                width: 500,
                height: 200,
                href: "${ctx}/fileManage/sysFolder/upload?id=" + id
            });
        }
        //		文件大小格式初始化
        function toDecimal(x) {
            var f = parseFloat(x);
            if (isNaN(f)) {
                return;
            }
            f = Math.round(x * 100) / 100;
            return f;
        }
    </script>
</head>
<body>
<div id="toolbar">
    <a href="#" class="easyui-linkbutton" onclick="addData();" iconCls="icon-add" plain="true">新建文件夹</a>
    <a href="#" class="easyui-linkbutton" onclick="move();" iconCls="icon-redo" plain="true">移动</a>
    <a href="#" class="easyui-linkbutton" onclick="upload();" iconCls="icon-add" plain="true">上传文件</a>
    <a href="#" class="easyui-linkbutton" onclick="deleteDate();" iconCls="icon-remove" plain="true">删除</a>
</div>

<div id="cc" class="easyui-layout" style="width:100%;height:100%;">
    <div data-options="region:'west',title:'文件管理',split:true" style="width:150px;"
    >
        <ul id="tree"></ul>
    </div>
    <div data-options="region:'center',title:'文件目录'" style="padding:5px;background:#eee;">
        <table id="dataList"></table>
    </div>
</div>
<div id="moveWin" class="easyui-window" style="width:400px;height:500px" closed="true" data-options="modal:true" title="选择移动目录">
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'center'">
            <ul id="moveTree"></ul>
        </div>
        <div data-options="region:'south',split:true" style="height:100px">
            <a href="#" class="easyui-linkbutton" onclick="moveSave();" iconCls="icon-add" plain="true">保存</a>
            <a href="#" class="easyui-linkbutton" onclick="moveCancel();" iconCls="icon-add" plain="true">取消</a>
        </div>
    </div>
    </div>
</body>
</html>