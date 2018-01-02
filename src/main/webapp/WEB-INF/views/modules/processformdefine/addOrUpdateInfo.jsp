<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>新增数据</title>
 	<meta name="decorator" content="default"/>
	<style type="text/css">
		td{
			text-align:left;
			padding-left:50px;
		}
		.input-large{
			width:220px
		}
		.inputStyle{
			width:216px;
			margin-bottom:10px;
		}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a>新增</a></li>
	</ul>
	<sys:message content="${message}"/>
	<form id="inputForm" action="${ctx}/processFormDefine/commons/saveInfo"  method="post" >
		<input type="hidden" name="tableName" value="${tableName}" />
		<c:forEach items="${chineseNameMap}" var="map">
			<div>
				<c:forEach items="${infoMap}" var="infoMap">
					<c:choose>
						<c:when test="${map.key=='id' && infoMap.key=='id'}">
							<input style="display:none" name="${map.key}"
								value="${infoMap.value}" class="inputStyle" />
						</c:when>
						<c:otherwise>
							<c:if test="${map.key==infoMap.key}">
								<label>${map.value}</label>
								<input name="${map.key}" value="${infoMap.value}"
									class="inputStyle" />
							</c:if>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
		</c:forEach>
		<div>
			<input id="btnSubmit" class="btn btn-primary" type="submit"
				value="保存" />&nbsp; <input id="btnCancel" class="btn" type="button"
				value="返 回" onclick="history.go(-1)" />
		</div>
	</form>
</body>

</html>