<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>${fns:getConfig('productName')} 登录</title>
	<meta name="decorator" content="blank"/>
	<meta content="text/x">
	<link rel="stylesheet" href="${ctxStatic}/common/login.css">
	<script type="text/javascript">
		$(document).ready(function() {
			$("#loginForm").validate({
				rules: {
					validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
				},
				messages: {
					username: {required: "请填写用户名."},password: {required: "请填写密码."},
					validateCode: {remote: "验证码不正确.", required: "请填写验证码."}
				},
				errorLabelContainer: "#messageBox",
				errorPlacement: function(error, element) {
					error.appendTo($("#loginError").parent());
				} 
			});
		});
		// 如果在框架或在对话框中，则弹出提示并跳转到首页
		if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
			alert('未登录或登录超时。请重新登录，谢谢！');
			top.location = "${ctx}";
		}
	</script>
</head>
<body>
	<!--[if lte IE 6]><br/><div class='alert alert-block' style="text-align:left;padding-bottom:10px;"><a class="close" data-dismiss="alert">x</a><h4>温馨提示：</h4><p>你使用的浏览器版本过低。为了获得更好的浏览体验，我们强烈建议您 <a href="http://browsehappy.com" target="_blank">升级</a> 到最新版本的IE浏览器，或者使用较新版本的 Chrome、Firefox、Safari 等。</p></div><![endif]-->
	<div class="header">
		<div id="messageBox" class="alert alert-error ${empty message ? 'hide' : ''}"><button data-dismiss="alert" class="close">×</button>
			<label id="loginError" class="error">${message}</label>
		</div>
	</div>
	<div class="auth_content">

		<h1 class="form-signin-heading">${fns:getConfig('productName')}</h1>
		<form id="loginForm" class="form-signin" action="${ctx}/login" method="post">
			<label class="input-label" for="username">登录名</label>
			<input type="text" id="username" name="username" class="input-block-level required" value="${username}">
			<label class="input-label" for="password">密码</label>
			<input type="password" id="password" name="password" class="input-block-level required">
			<c:if test="${isValidateCodeLogin}"><div class="validateCode">
				<label class="input-label mid" for="validateCode">验证码</label>
				<sys:validateCode name="validateCode" inputCssStyle="margin-bottom:0;"/>
			</div></c:if>
			<input class="btn btn-large btn-primary" type="submit" value="登 录"/>&nbsp;&nbsp;
			<label for="rememberMe" title="下次不需要再登录"><input type="checkbox" id="rememberMe" name="rememberMe" ${rememberMe ? 'checked' : ''}/> 记住我（公共场所慎用）</label>
		</form>
	</div>
	<script src="${ctxStatic}/flash/zoom.min.js" type="text/javascript"></script>
	<style type="text/css">
		.com-zoom-flash{
			position: absolute;
			bottom: 20px;
			right: 0;
			height: 10px;
			width: 10px;
		}
	</style>
</body>
</html>