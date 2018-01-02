<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>运行中的流程</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function(){
			top.$.jBox.tip.mess = null;
		});
		function page(n,s){
        	location = '${ctx}/act/process/running/?pageNo='+n+'&pageSize='+s;
        }

		function deleteProcinst(procinstId) {
			alert(procinstId);
            var url = '${ctx}/act/process/deleteProc?procInstId='+procinstId;
            $.get(url, function(data) {
                if(200==data.code){
                    $.messager.show({
                        title : '提示信息',
                        msg : data.msg,
                        timeout : 1000 * 2
                    });
//                    dataObject.treegrid("reload");
                } else {
                    $.messager.show({
                        title : '提示信息',
                        msg : data.msg,
                        timeout : 1000 * 2
                    });

                }
            });
        }
	</script>

</head>
<body>
	<%--<form id="searchForm" action="${ctx}/act/process/running/" method="post" class="breadcrumb form-search">--%>
		<%--<label>流程实例ID：</label><input type="text" id="procInstId" name="procInstId" value="${procInstId}" class="input-medium"/>--%>
		<%--<label>流程定义Key：</label><input type="text" id="procDefKey" name="procDefKey" value="${procDefKey}" class="input-medium"/>--%>
		<%--&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>--%>
	<%--</form>--%>
	<sys:message content="${message}"/>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>执行ID</th>
				<th>流程实例ID</th>
				<th>发起人</th>
				<th>当前环节</th>
				<th>流程名称</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="act">
				<tr>
					<td>${act.procIns.id}</td>
					<td>${act.procIns.processInstanceId}</td>
					<td>${act.businessId}</td>
					<td>
						<c:if test="${act.procIns.activityId eq 'superior'}">直接上级审批</c:if>
						<c:if test="${act.procIns.activityId eq 'departmentHR'}">部门人力资源管理员审批</c:if>
						<c:if test="${act.procIns.activityId eq 'companyDepManager'}">部门人力资源管理员审批</c:if>
						<c:if test="${act.procIns.activityId eq 'committee'}">委员会评审</c:if>
						<c:if test="${act.procIns.activityId eq 'companyCommittee'}">委员会评审</c:if>
						<c:if test="${act.procIns.activityId eq 'depHrCollect'}">部门人力资源汇总</c:if>
						<c:if test="${act.procIns.activityId eq 'depManager'}">部门负责人审批</c:if>
						<c:if test="${act.procIns.activityId eq 'companyDepLeader'}">部门负责人审批</c:if>
						<c:if test="${act.procIns.activityId eq 'companyHR'}">人力资源部</c:if>
						<c:if test="${act.procIns.activityId eq 'modifyApply'}">修改申请表</c:if>
					</td>
					<td>${act.procIns.processDefinitionName}</td>
					<td>
						<shiro:hasPermission name="act:process:edit">
							<a href="${ctx}/act/process/deleteProc?procInstId=${act.procIns.processInstanceId}" >删除流程</a>
						</shiro:hasPermission>&nbsp;
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
