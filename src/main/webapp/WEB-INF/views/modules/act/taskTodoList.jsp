<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>待办任务</title>
    <meta name="decorator" content="easyui"/>
    <script src="${ctxStatic}/utils/leftMenuNum.js"></script>
    <script type="text/javascript">
        var dataObject;
        $(function () {
            leftMenuNum.addNum(${count});
            $('#dialog').dialog('close');
            dataObject = $("#taskList");
            dataObject.datagrid({
                url: '${ctx}/act/task/getTaskList',
                rownumbers: true,
                singleSelect: true,
                fitColumns: true,
                scrollbarSize :0,
                fit: true,
                border: true,
                pagination: true,
                remoteSort: true,
                pageSize: 30,
                pageList: [30, 60, 100],
                idField: 'id',
                columns: [[
                    {field: 'processName', title: '流程名称', width: 130,
                    	formatter: function(value,row,index){
                    		return row.act.procDefName;
                    	}
                    },
                    {
                        field: 'applyNumber', title: '申请编号', width:150,
                        formatter: function (value, row, index) {
//                            return "<a style='color:blue' href='javascript:void(0)' onclick=\"batchApprove('"+row.act.taskId+"','"+row.act.taskName+"','"+row.act.taskDefKey+"','"+row.act.procInstId+"','"+row.act.procDefId+"','"+row.act.status+"','"+row.act.assignee+"')\">"+value+"</a>";
                            var segment = ""
                            if(row.act.taskName=="直接上级审批"){
                                segment="1";
                            }else if(row.act.taskName=="部门人力资源管理员审批"){
                                segment="2";
                            }else if(row.act.taskName=="委员会评审"){
                                segment="3";
                            }else if(row.act.taskName=="部门人力资源汇总"){
                                return '<a style="color:blue" href="${ctx}/business/depHrSum/list?taskId='+row.act.taskId+'&procInsId='+row.act.procInstId+'&taskDefKey='+row.act.taskDefKey+'">' + value + '</a>';
                            }else if(row.act.taskName=="部门负责人审批"&&row.applyType=="1"){
                                return '<a style="color:blue" href="${ctx}/business/depApprove/list?taskId='+row.act.taskId+'&procInsId='+row.act.procInstId+'&taskDefKey='+row.act.taskDefKey+'">' + value + '</a>';
                            }else if(row.act.taskName=="部门负责人审批"&&row.applyType=="0"){
                                segment="4";
                            }else if(row.act.taskName=="人力资源部"){
                                segment="5";
                            }else if(row.act.taskName=="修改申请表"){
                                return '<a style="color:blue" href="${ctx}/business/standard/list?taskId='+row.act.taskId+'&baseInfoId='+row.id+'">' + value + '</a>';
                            }
                            return '<a style="color:blue" href="${ctx}/business/pending/form?no=' + value + '&taskId='+row.act.taskId+'&procInsId='+row.act.procInstId+'&taskDefKey='+row.act.taskDefKey+'&segment='+segment+'">' + value + '</a>';
                        }
                    },
                    {field: 'userName', title: '员工姓名', width: 60},
                    {field: 'userNo', title: '员工编号', width: 60},
                    {field: 'office', title: '隶属部门', width: 60},
                    {field: 'department', title: '科室', width: 60},
                    {field: 'education', title: '学历', width: 60},
                    {field: 'workTime', title: '参加工作时间', width: 100},
                    {field: 'applyPostName', title: '申请岗位', width: 60},
                    {field: 'post', title: '当前岗层岗级', width: 100},
                    {field: 'applyPost', title: '申请岗层岗级', width: 100},
//					{field:'status',title:'状态',width:80,
//						formatter: function(value,row,index){
//							if(row.act.status=="todo"){
//								return "办理中";
//							}else{
//								return "已完成";
//							}
//                    	}
//					},
					{field:'taskName',title:'当前环节',width:150,
						formatter: function(value,row,index){
                    		return row.act.taskName;
                    	}
					},
                    {field: 'processStartTime', title: '流程发起时间', width: 100,
						formatter: function(value,row,index){
							return row.act.vars.map.processStartTime;
                    	}
                    }
//                    {field:'操作',title:'操作',width:100,
//                    	formatter: function(value,row,index){
//							var btn="";
//							if(row.act.assignee==""){
//								btn="<a style='color:blue' href='javascript:void(0)' onclick=\"batchApprove('"+row.act.taskId+"','"+row.act.taskName+"','"+row.act.taskDefKey+"','"+row.act.procInstId+"','"+row.act.procDefId+"','"+row.act.status+"','"+row.act.assignee+"')\">任务签收</a>"
//							}else{
//								btn="<a style='color:blue' href='javascript:void(0)' onclick=\"batchApprove('"+row.act.taskId+"','"+row.act.taskName+"','"+row.act.taskDefKey+"','"+row.act.procInstId+"','"+row.act.procDefId+"','"+row.act.status+"','"+row.act.assignee+"')\">任务办理</a>"
//							}
//							return btn;
//						}
//					}
                ]],
                onLoadSuccess: function () {
                    $('.button-edit').linkbutton({});
                    $('.button-delete').linkbutton({});
                },
                toolbar: '#toolbar'
            });
        })

        /* 查询 */
		function queryData(){
			dataObject.datagrid({'queryParams':{
				'receiptNo':$("#receiptNo").val(),
				'partNo':$("#partNo").val(),
				'vendorName':$("#vendorName").val(),
				'partName':$("#partName").val()
			}})
		}
		
		/* 重置 */
		function reset(){
			$("#searchForm").form("reset");
		}
		
		/* 进入批次结论修改审批页面 */
		function batchApprove(taskId,taskName,taskDefKey,procInstId,procDefId,status,assignee){
			$.modalDialog.openner=dataObject;
			
			if(assignee==""){
				$.modalDialog({
					title : "批次结论修改审批",
					width : 1050,
					height : 500,
					href : "${ctx}/act/task/claimForm?taskId="+taskId+"&taskName="+taskName+"&taskDefKey="+taskDefKey+"&procInstId="+procInstId+"&procDefId="+procDefId+"&status="+status+"&assignee="+assignee
				});
			}else{
				$.modalDialog({
					title : "批次结论修改审批",
					width : 1050,
					height : 500,
					href : "${ctx}/act/task/form?taskId="+taskId+"&taskName="+taskName+"&taskDefKey="+taskDefKey+"&procInstId="+procInstId+"&procDefId="+procDefId+"&status="+status
				});
			}
		}
        

    </script>
</head>
<body>

<table id="taskList">
</table>
<div id="toolbar">

</div>

</body>
</html>