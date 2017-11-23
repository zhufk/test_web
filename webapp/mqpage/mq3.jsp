<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>(stomp-mq)</title>
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<jsp:include page="../commonpage/css_common.jsp"></jsp:include>
<jsp:include page="../commonpage/js_common.jsp" />
<script src="../static/zfk/js/common.js"></script>
<script src="../static/zfk/js/stomp.js"></script>

<script type="text/javascript">
	

	function send() {
		var time = new Date();
		var content = $("#messageInput").val();
		var data = {
			robotId : "robotId1",
			robotName : "小龙人1号",
			content : content,
			time : time
		}
		//发送信息
		

		$("#messageInput").val('');
		$("#messageContentInput").append('朱富昆    ' + dateFormat(time) + '\n');
		$("#messageContentInput").append(content + '\n');
		//至底
		var scrollTop = $("#messageContentInput")[0].scrollHeight;
		$("#messageContentInput").scrollTop(scrollTop);

	}
	//回车发送信息
	function eKeyup(e) {
		e = e ? e : (window.event ? window.event : null);
		if (e.keyCode == 13)//Enter
		{
			send();
		}
	}
</script>
</head>

<body>
	<div class="row" style="padding: 20px">
		<div class="form-group">
			<label for="messageContentInput" class="col-sm-2 control-label">内容:</label>
			<div class="col-sm-8">
				<textarea id="messageContentInput" class="form-control" rows="10"></textarea>
			</div>
		</div>
	</div>
	<div class="row" style="padding: 20px">
		<div class="form-group">
			<label for="messageInput" class="col-sm-2 control-label">
				发送消息：</label>
			<div class="col-sm-8">
				<input id="messageInput" class="form-control" type="text"
					name="message" onkeyup="eKeyup(event)" />
			</div>
		</div>
	</div>
	<div class="row text-right" style="padding: 20px">
		<div class="form-group col-sm-10">
			<button class="btn btn-primary flat" type="button" id="resetButton"
				onclick="javascript:send();">
				<i class="fa fa-refresh"></i> 提交
			</button>
			<!-- 			<button class="btn btn-primary flat" type="button" id="resetButton" -->
			<!-- 				onclick="javascript:receive();"> -->
			<!-- 				<i class="fa fa-refresh"></i> 接受 -->
			<!-- 			</button> -->
		</div>
	</div>


</body>
</html>