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
<title>stomp-mq(发布/订阅)</title>
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<jsp:include page="../commonpage/css_common.jsp"/>
<jsp:include page="../commonpage/js_common.jsp" />
<script src="../static/zfk/js/common.js"></script>
<script src="../static/zfk/js/stomp.js"></script>

<script type="text/javascript">
	
	var client;
	var receiveDest;
	var sendDest;
	
  $(document).ready(function() {
      if(window.WebSocket) {
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
  				//initClient();
  			}
  		});
    	  //连接
        $('#connect').click(function() {
          var url = $("#connect_url").val();
          var login = $("#connect_login").val();
          var passcode = $("#connect_passcode").val();
          
          receiveDest = $("#receiveDest").val();
          sendDest = $("#sendDest").val();

          client = Stomp.client(url);

          client.debug = function(str) {
            $("#debug").append(document.createTextNode(str + "\n"));
          };
          
          client.connect(login, passcode, function(frame) {
            client.debug("connected to Stomp");
            client.subscribe(receiveDest, function(message) {
            	var data = JSON.parse(message.body);
            	//data = {robotName:"小龙人1号",content:message.body};
            	
            	appendMessage(data);
            });
          });
          $('#connect').attr("disabled", true);
          $('#disconnect').attr("disabled", false);
          
        });
  
    	  //断开
        $('#disconnect').click(function() {
          client.disconnect(function() {
            $("#messageContentInput").html("");
            $("#debug").text("");
          });
          $('#connect').attr("disabled", false);
          $('#disconnect').attr("disabled", true);
         
        });
   
    	 //发送
        $('#send').click(function() {
        	send();
        });
        
      } else {
        $("#chat").html("\
        		<h1>Your browser does not support WebSockets. This example will not work properly.<br>\
                Please use a Web Browser with WebSockets support (WebKit or Google Chrome)</h1>\
        		");
      }
    });

  	//发送
	function send() {
		var time = dateFormat(new Date());
		var content = $("#messageInput").val();
		var data = {
			"robotId" : "robotId1",
			"robotName" : "小龙人1号",
			"userId" : "userId1",
			"userName" : "朱富昆",
			"time" : time,
			"content" : content
		}
		//发送信息
		client.send(sendDest, {}, JSON.stringify(data));
		
		$("#messageInput").val('');
		appendMessage(data);
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
	<div id="server" class="col-md-4">
		<div class="page-header">
			<h2>Server Login</h2>
		</div>
			<div class="control-group">
				<label>WebSocket URL</label>
				<div class="controls">
					<input name=url id='connect_url' value='ws://localhost:61614'
						type="text">
				</div>
			</div>
			<div class="control-group">
				<label>User</label>
				<div class="controls">
					<input id='connect_login' placeholder="User Login" value="admin"
						type="text">
				</div>
			</div>
			<div class="control-group">
				<label>Password</label>
				<div class="controls">
					<input id='connect_passcode' placeholder="User Password"
						value="password" type="password">
				</div>
			</div>
			<div class="control-group">
				<label>receiveDest</label>
				<div class="controls">
					<input id='receiveDest' placeholder="receiveDest"
						value="/topic/chat-one" type="text">
				</div>
			</div>
			<div class="control-group">
				<label>sendDest</label>
				<div class="controls">
					<input id='sendDest' placeholder="sendDest"
						value="/topic/chat-two" type="text">
				</div>
			</div>
			<div class="form-actions">
				<button id='connect' type="button"
					class="btn btn-large btn-primary">连接</button>
				<button id='disconnect' type="button" 
					class="btn btn-large btn-primary">断开</button>
			</div>

	</div>


	<div id="chat" class="col-md-8">
		<div class="page-header">
			<h2>Chat room</h2>
		</div>
		<div class="control-group">
			<label for="messageContentInput" class="control-label">内容:</label>
			<div class="controls">
				<textarea id="messageContentInput" class="form-control" rows="10"></textarea>
			</div>
		</div>

		<div class="control-group">
			<label for="messageInput" class="control-label">
				发送消息：</label>
			<div class="controls">
				<input id="messageInput" class="form-control" type="text"
					name="message" onkeyup="eKeyup(event)" />
			</div>
		</div>
		<div class="control-group">
			<button id="send" class="btn btn-primary flat" type="button" >
				<i class="fa fa-refresh"></i> 发送
			</button>
		</div>
	</div>

	<div class="span4">
			<div class="page-header">
				<h2>Debug Log</h2>
			</div>
			<pre id="debug"></pre>
		</div>

</body>
</html>