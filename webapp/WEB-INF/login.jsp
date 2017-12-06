<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en" style="height: auto;">
<head>
<title>和美（深圳）智能机器人知识库系统-登录</title>
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<!-- META BEGIN -->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta id="login">
<link rel="shortcut icon" href="${appPath}/img/logo.png">
<!-- META END -->
<!-- Bootstrap 3.3.7 -->
<link rel="stylesheet" type="text/css"
	href="static/public/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="static/public/css/AdminLTE.min.css">
<style type="text/css">
body {
	background: url("static/public/zfk/img/1.jpg") top center no-repeat;
	background-size: cover
}

.display-hide {
	display: none;
}
</style>
<script src="static/public/js/jquery.min.js"></script>
<script src="static/zfk/js/common.js?v=1"></script>
<script>
	jQuery(document).ready(function() {
		if ($("#returnMessaStatus").val()) {
			var error1 = $('.alert-danger', $('#' + loginForm));
			error1.show();
			// Metronic.scrollTo(error1, -200);
		}

		$("#loginButton").focus();
		// key enter event
		$(document).keydown(function(e) {
			if (e.keyCode == 13) {
				$("#loginButton").click();
			}

		});
	});
</script>
</head>
<!-- END HEAD -->

<!-- BEGIN BODY -->
<body scroll=no class="contentBody">
	<div>
		<!-- BEGIN LOGIN FORM -->
		<form action="login" method="post" id="loginForm">
			<div class="row"
				style="text-align: center; margin-top: 100px; margin-right: 0px;">
				<div class="col-sm-4 col-sm-offset-4 form-box" id="content"
					style="background-color: #424542; background-color: rgba(0, 0, 0, 0.35); border-radius: 5px; height: 450px; width: 33.33%;">
					<table style="width: 100%; padding: 40px;">
						<tr>
							<td width="30%" rowspan="2"><img
								style="margin: 20px 0 0 20px;" src="static/zfk/img/hmlogo.png" />
							</td>
							<td width="40%" align="center"><h3 style="color: white;">智能机器人知识库服务平台</h3></td>
							<td width="30%">&nbsp;</td>
						</tr>
						<tr>
							<td colspan="2" style="color: white; padding-left: 20px">
								&nbsp;</td>
						</tr>
					</table>
					<hr />
					<!-- 错误提示 -->
					<div class="alert alert-danger display-hide"
						style="padding: 10px; margin-bottom: 10px;">
						<button class="close" data-close="alert"></button>
						<input type="hidden" id="returnMessaStatus" value="${status }" />
						<div id="errorMsg" style="text-align: left;">${message }</div>
					</div>
					<table style="width: 100%; padding: 40px;">
						<tr>
							<td align="center"><input class="form-control"
								style="margin: 15px; height: 46px; width: 390px;" id="username"
								name="username" data-toggle="tooltips" data-placement="bottom"
								title="请输入帐号" placeholder="请输入帐号"></td>

						</tr>
						<tr>
							<td align="center"><input type="password"
								class="form-control"
								style="margin: 15px; height: 46px; width: 390px;" id="password"
								name="password" type="text" data-toggle="tooltips"
								data-placement="bottom" title="请输入密码" placeholder="请输入密码">
							</td>
						</tr>
						<tr>
							<td align="center"><input id="loginBtn"
								class="btn btn-primary  btn-lg"
								style="width: 390px; margin: 15px" type="submit"
								data-loading-text="登录中，请稍候..." data-complete-text="登录"
								value="登录" /></td>
						</tr>
					</table>
				</div>

			</div>
		</form>
		<p class="text-center" style="color: white; margin: 30px 0;">
			©2017 和美（深圳）信息技术股份有限公司 版权所有 V1.0.0</p>
	</div>


</body>
<!-- END BODY -->
</html>