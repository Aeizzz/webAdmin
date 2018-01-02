<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>工单申请</title>
 	<meta name="decorator" content="default"/>

	<style type="text/css">
		td{
			text-align:right;
		}
		.input-large{
			width:220px
		}
		hr{
			margin:10px 0px;
		}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a>工单申请</a></li>
	</ul>
	<ul class="ul-form btn-arr">
		<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="添加" onclick="addInfo()"/></li>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<c:forEach items="${pageColumnNameList}" var="columns">
					<c:forEach items="${chineseNameMap}" var="nameMap">
						<c:if test="${columns == nameMap.key}">
							<c:choose>
								<c:when test="${nameMap.key=='id'}">
									<th style="display:none">${nameMap.value}</th>
								</c:when>
								<c:otherwise>
									<th>${nameMap.value}</th>
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:forEach>
				</c:forEach>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${columnList}" var="row">
			<tr>
				<c:forEach items="${pageColumnNameList}" var="columns">
					<c:forEach items="${row}" var="map">
						<c:choose>
							<c:when test="${map.key== columns && map.key=='id'}">
								<td style="display:none" class="id">${map.value}</td>
							</c:when>
							<c:otherwise>
								<c:if test="${map.key== columns}">
									<%-- <td>${map.value}</td> --%>
									<td>${fns:getDictLabel(map.value,map.key,'')}</td>
								</c:if>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</c:forEach>
				<td>
					<a href="#" class="editBtn">编辑</a>
					<a href="#" class="deleteBtn">删除</a>
					<a href="#" class="startProcess">启动流程</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<script type="text/javascript">
		
		/* 进入数据添加页面 */
		function addInfo(){
			window.location.href="${ctx}/processFormDefine/commons/addInfo?tableName="+'${tableName}'+"&id="+"";
		}
		
		/* 编辑指定数据 */
		$(".editBtn").click(function(){ 
			var id=$(this).parents("tr").find(".id").text().trim();
			window.location.href="${ctx}/processFormDefine/commons/editInfo?tableName="+'${tableName}'+"&id="+id;
		});
		
		/* 删除指定数据 */
		$(".deleteBtn").click(function(){
			var id=$(this).parents("tr").find(".id").text().trim();
			top.$.jBox.confirm("确认要删除数据吗？","系统提示",function(v,h,f){
				if(v=="ok"){
					window.location.href="${ctx}/processFormDefine/commons/delete?tableName="+'${tableName}'+"&id="+id;
				}
			});
		});
		
		
	</script>
</body>

</html>