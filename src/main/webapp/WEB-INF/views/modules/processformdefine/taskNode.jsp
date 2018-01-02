<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>驳回节点选择</title>
 	<meta name="decorator" content="default"/>
	<style type="text/css">
	</style>
</head>
<body>
	<div>
		<p>请选择要回退的节点</p>
		<c:forEach items="${rejectNode}" var="map">
			<input id="${map.key}" type="radio" name="radio" value="${map.value}"/>
			<label>${map.value}</label>
		</c:forEach>
	</div>
	<div>
		<input id="back" class="btn btn-primary" type="button" value="确定驳回" />
	</div>
	
	<script type="text/javascript">
		$("#back").click(function(){
			var nodeName=$('input:radio:checked').val();
			var nodeKey=$('input:radio:checked').attr("id");
			window.location.href="${ctx}/processFormDefine/commons/completeRejectTask?taskId="+'${taskId}'+"&nodeKey="+nodeKey+"&opinion="+'${opinion}'+"&tableName="+'${tableName}'+"&pass=0";
		});
	</script>
	
</body>
</html>