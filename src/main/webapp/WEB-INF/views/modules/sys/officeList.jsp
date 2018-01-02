<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机构管理</title>
	<meta name="decorator" content="easyui"/>
	<%--<%@include file="/WEB-INF/views/include/treetable.jsp" %>--%>
	<script type="text/javascript">
		var dataObject;

		$(function(){
			dataObject=$("#dataList");
			var id = '${office.id}';
			var parentIds = '${office.parentIds}';
			if('${flag}'=="1"){
                dataObject.treegrid({
                    url:'${ctx}/sys/office/dataList?parent.id='+id,
                    fitColumns:true,
                    fit:true,
                    border:true,
                    idField:'id',
                    treeField:'name',
                    parentField:'parentId',
                    columns:[[
                        {
                            field : "id",
                            hidden:true
                        },{
                            field:'name',
                            title:'机构名称',
                            width:100
                        }
                        ,{
                            field:'code',
                            title:'机构编码',
                            width:100,
                            align:'right'
                        },{
                            field:'type',
                            title:'机构类型',
                            width:100,
                            align:'right',
                            formatter:function(value,row,index){
                                if(row.type == '1'){
                                    return "公司";
                                }else if(row.type=='2'){
                                    return "部门";
                                }else{
                                    return "科室";
                                }
                            }

                        },{
                            field:'remarks',
                            title:'备注',
                            width:100
                        },{
                            field:'操作',
                            title:'操作',
                            width:100,
                            align:'right',
                            formatter: function(value,row,index){
                                var btn ="";
                                //<shiro:hasPermission name='sys:office:edit'>
                                btn += "<a class='easyui-linkbutton' style='margin-left:3px;margin-right:3px;' href='javascript:void(0)' onclick='add(\""+row.id+"\")'>添加下级机构</a>";
                                btn += "<a href='javascript:void(0);' style='margin-left:3px;margin-right:3px;'  onclick='edit(\""+row.id+"\")'  class=\"easyui-linkbutton\" iconCls=\"icon-edit\" plain=\"true\"'>编辑</a>";
                                btn += "<a class='easyui-linkbutton' style='margin-left:3px;margin-right:3px;' href='javascript:void(0)' onclick='deleteById(\""+row.id+"\")'>删除</a>";
                                //</shiro:hasPermission>
                                return btn;
                            }
                        }
                    ]]
                });
            }else{
                dataObject.treegrid({
                    url:'${ctx}/sys/office/dataListByParent?parent=0',
                    fitColumns:true,
                    fit:true,
                    border:true,
                    idField:'id',
                    treeField:'name',
                    parentField:'parentId',
                    onBeforeExpand:function(row){
                        dataObject.treegrid("options").url='${ctx}/sys/office/dataListByParent?parent='+row.id;
                        return true;
                    },
                    columns:[[
                        {
                            field : "id",
                            hidden:true
                        },{
                            field:'name',
                            title:'机构名称',
                            width:100
                        }
                        ,{
                            field:'code',
                            title:'机构编码',
                            width:100,
                            align:'right'
                        },{
                            field:'type',
                            title:'机构类型',
                            width:100,
                            align:'right',
                            formatter:function(value,row,index){
                                if(row.type == '1'){
                                    return "公司";
                                }else if(row.type=='2'){
                                    return "部门";
                                }else{
                                    return "科室";
                                }
                            }

                        },{
                            field:'remarks',
                            title:'备注',
                            width:100
                        },{
                            field:'操作',
                            title:'操作',
                            width:100,
                            align:'right',
                            formatter: function(value,row,index){
                                var btn ="";
                                //<shiro:hasPermission name='sys:office:edit'>
                                btn += "<a class='easyui-linkbutton' style='margin-left:3px;margin-right:3px;' href='javascript:void(0)' onclick='add(\""+row.id+"\")'>添加下级机构</a>";
                                btn += "<a href='javascript:void(0);' style='margin-left:3px;margin-right:3px;'  onclick='edit(\""+row.id+"\")'  class=\"easyui-linkbutton\" iconCls=\"icon-edit\" plain=\"true\"'>编辑</a>";
                                btn += "<a class='easyui-linkbutton' style='margin-left:3px;margin-right:3px;' href='javascript:void(0)' onclick='deleteById(\""+row.id+"\")'>删除</a>";
                                //</shiro:hasPermission>
                                return btn;
                            }
                        }
                    ]]
                });
            }
		});

		function add(id){
			$.modalDialog.openner=dataObject;
			$.modalDialog({
				title : "新增",
				width : 500,
				height : 500,
				href : "${ctx}/sys/office/form?parent.id="+id
			});
		}

		function edit(id){
			$.modalDialog.openner=dataObject;
			$.modalDialog({
				title : "编辑",
				width : 500,
				height : 500,
				href : "${ctx}/sys/office/form?id="+id
			});
		}
		function deleteById(id){
			$.messager.confirm('确认','您确认想要删除该机构及所有子机构吗？',
					function(r) {
						if (r) {
							var url = '${ctx}/sys/office/delete?id='+id;
							$.get(url, function(data) {
								if(200==data.code){
									$.messager.show({
										title : '提示信息',
										msg : data.msg,
										timeout : 1000 * 2
									});
									dataObject.treegrid("reload");
								} else {
									$.messager.show({
										title : '提示信息',
										msg : data.msg,
										timeout : 1000 * 2
									});

								}
							});
						}
					});
		}



	</script>
</head>
<body>
	<table id="dataList"></table>
</body>
</html>