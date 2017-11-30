<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>
<% String contenxtPath = request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>mqttws-mq(发布/订阅)</title>
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<jsp:include page="../commonpage/css_common.jsp" />
<jsp:include page="../commonpage/js_common.jsp" />
<script src="../static/zfk/js/common.js"></script>
<script src="../static/zfk/js/mqttws31.js"></script>

<script type="text/javascript">
	var host = "127.0.0.1";
	var port = 61614;
	var sendDest = "chat-two";
	var receiveDest = "chat-one"
	var clientId = "myMq1";
	var userName = "admin";
	var password = "admin";

	window.onload = function() {
		//获取历史消息
		$.ajax({
			type : 'post',
			url : '<%=contenxtPath%>/mq/list_history_message',
			data : {
				userId : "userId1",
				size : 10
			},//朱富昆
			dataType : 'json',
			success : function(result) {
				console.log("收到历史消息:" + JSON.stringify(result));
				for (var i = 0; i < result.length; i++) {
					appendMessage(result[i]);
				}
				//初始化客户端
				initClient();
			}
		});

		//初始化客户端
		function initClient() {
			window.client = new Messaging.Client(host, Number(port), clientId);
			client.connect({
				userName : userName,
				password : password,
				onSuccess : onConnect
			});//连接服务器并注册连接成功处理事件  
			client.onConnectionLost = onConnectionLost;//注册连接断开处理事件  
			client.onMessageArrived = onMessageArrived;//注册消息接收处理事件  
			function onConnect() {
				console.log("onConnected");
				client.subscribe(receiveDest);//订阅主题  
			}
			;

			function onConnectionLost(responseObject) {
				if (responseObject.errorCode !== 0) {
					console.log("onConnectionLost:"
							+ responseObject.errorMessage);
					console.log("连接已断开");
				}
			}
			;
			function onMessageArrived(message) {
				console.log("收到消息:" + message.payloadString);
				var data = JSON.parse(message.payloadString);

				appendMessage(data);
			}
		}
	}

	//添加信息到内容框
	function appendMessage(data) {
		var time = data.time;
		if (typeof time == 'number') {
			time = dateFormat(new Date(time));
		} else if (time instanceof Date) {
			time = dateFormat(time);
		}
		$("#messageContentInput").append(
				data.robotName + '  ' + time + '\n' + data.content + '\n');
		//至底
		var scrollTop = $("#messageContentInput")[0].scrollHeight;
		$("#messageContentInput").scrollTop(scrollTop);
	}

	//发送信息
	function send() {
		var time = new Date();
		var content = $("#messageInput").val();
		var data = {
			robotId : "robotId2",
			robotName : "小龙人2号",
			userId : "userId1",
			userName : "朱富昆",
			content : content,
			time : time
		}
		//发送信息
		message = new Messaging.Message(JSON.stringify(data));
		message.destinationName = sendDest;
		client.send(message);

		$("#messageInput").val('');

		appendMessage(data);

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