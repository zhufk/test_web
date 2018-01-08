<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<% String contenxtPath = request.getContextPath(); %>
<c:set var="contenxtPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>聊天</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- css -->
    <jsp:include page="/WEB-INF/common/css_common.jsp"/>
    <!-- js -->
    <jsp:include page="/WEB-INF/common/js_common.jsp"/>
</head>

<body>
<div class="content-wrapper" style="min-height: 540px;">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1 style="font-family: KaiTi_GB2312">聊天</h1>
<!--         <ol class="breadcrumb"> -->
<%--             <li><a href="${pageContext.request.contextPath}/index"><i --%>
<!--                     class="fa fa-dashboard"></i> 首页</a></li> -->
<!--             <li><a href="#">人工客服</a></li> -->
<!--             <li class="active">聊天</li> -->
<!--         </ol> -->
    </section>

    <!-- Main content -->
    <section class="content">
        <div class="row">
            <button id="connect" class="btn btn-primary flat"
                    type="button">
                <i class="fa fa-search"></i> 连接
            </button>
            <button id="disconnect" class="btn btn-primary flat"
                    type="button">
                <i class="fa fa-search"></i> 断开
            </button>
        </div>
        
        <div style="width:800px;height:500px;border:1px red solid">
        	<div style="width:200px;height:500px; border:1px red solid; position:absolute">
        		<ul id="robotList" >
        		</ul>
        	</div>
        	<div style="width:600px;height:500px;border:1px red solid;position:relative;left:200px">
        		<div id="chatContent" style="width:600px;height:400px;border:1px red solid">
        		</div>
        		<div id="sendContent" style="width:600px;height:100px;border:1px red solid">
        			<input id="messageInput" class="form-control" type="text"
						name="message" onkeyup="eKeyup(event)" />
					<button id="send" class="btn btn-primary flat" type="button" >
						<i class="fa fa-refresh"></i> 发送
					</button>
        		</div>
        	</div>
        	<div id="historyList">
        	</div>
        </div>
    </section>
</div>

</body>

<script src="../static/zfk/js/common.js"></script>
<script src="../static/zfk/js/stomp.js"></script>

<script type="text/javascript">
	
	var robotObjList = [];
	
	var userId = "userId1";
	var userName = "朱富昆";
	var currentRobotId = '';
	var currentRobotName = '';
	
	var client;
	var receiveDest;
	var sendDest;
	
  	$(document).ready(function() {
  		if(window.WebSocket) {
	    	//连接
	        $('#connect').click(function() {
	        	var url = 'ws://localhost:61614';
	            var login = 'admin';
	            var passcode = 'admin';
	          
	            receiveDest = '/topic/chat-one';
	            sendDest = '/topic/chat-two';
	
	            client = Stomp.client(url);
	
	            client.debug = function(str) {
	            	//$("#debug").append(document.createTextNode(str + "\n"));
	            };
	          
	            //连接服务器
	            client.connect(login, passcode, function(frame) {
	        		client.debug("connected to Stomp");
	            	//订阅主题并注册消息接收处理事件  
	            	client.subscribe(receiveDest, function(message) {
	            		var data = JSON.parse(message.body);
	            		receiveMessage(data);
	            	});
	          	});
	          	$('#connect').attr("disabled", true);
	          	$('#disconnect').attr("disabled", false);
	        });
	  
	    	  //断开
	        $('#disconnect').click(function() {
	        	client.disconnect(function() {
	            	//$("#messageContentInput").html("");
	            	//$("#debug").text("");
	          	});
	          	$('#connect').attr("disabled", false);
	          	$('#disconnect').attr("disabled", true);
	        });
	   
	    	//发送
	        $('#send').click(function() {
	        	send();
	        });
        
      	} else {
	    	$("body").html("<h1>Your browser does not support WebSockets. This example will not work properly.");
	  	}
    });
  	
  	function listHistoryUser(userId, size){
  		//获取历史消息
  		$.ajax({
  			type : 'post',
  			url : '<%=contenxtPath%>/chat/list_history',
  			data : JSON.stringify({
  				"userId" : userId,
  				"size" : size
  			}),
  			dataType : 'json',
  			success : function(result) {
  				console.log("收到历史消息:" + JSON.stringify(result));
  				for (var i = 0; i < result.length; i++) {
  					appendMessage(result[i]);
  				}
  			}
  		});
  	}
  	
  	function listHistoryUserAndRobot(userId, robotId, size){
  		//获取历史消息
  		$.ajax({
  			type : 'post',
  			url : '<%=contenxtPath%>/chat/list_history',
  			data : JSON.stringify({
  				"userId" : userId,
  				"robotId" : robotId,
  				"size" : size
  			}),
  			dataType : 'json',
  			success : function(result) {
  				console.log("收到历史消息:" + JSON.stringify(result));
  				for (var i = 0; i < result.length; i++) {
  					appendMessage(result[i]);
  				}
  			}
  		});
  	}
  	
  	/////////////////////////////////////////////////////////////
  	//发送
	function send() {
  		if(currentRobotId != ''){
  			var time = dateFormat(new Date());
  			var content = $("#messageInput").val();
  			var data = {
  				"robotId" : currentRobotId,
  				"robotName" : currentRobotName,
  				"userId" : userId,
  				"userName" : userName,
  				"time" : time,
  				"content" : content
  			}
  			//发送信息
  			client.send(sendDest, {}, JSON.stringify(data));
  			
  			$("#messageInput").val('');
  			appendMessage(data);
  		}
		
	}
  	
	//回车发送信息
	function eKeyup(e) {
		e = e ? e : (window.event ? window.event : null);
		if (e.keyCode == 13)//Enter
		{
			send();
		}
	}

	///////////////////////////////////////////////////////
  	//接收
  	function receiveMessage(data){
  		if(data.userId == userId){
  			var robotId = data.robotId;
  			
  			var robotObj = null;
  			for(obj in robotObjList){
  				if(obj.robotId = data.robotId){
  					robotObj = obj;
  					break;
  				}
  			}
  			//不存在
  			if (robotObj == null){
  				robotObj = {
  					robotId : data.robotId,
  					robotName : data.robotName,
  					count : 0
  				};
  				robotObjList.push(robotObj);
  				
  				if(currentRobotId == ''){
  					currentRobotId = data.robotId;
  					addRobotList(robotObj, true);	
  				}else{
  					addRobotList(robotObj, false);	
  				}
  			}else{//存在
  				robotObj.count++;
  				updateRobotList(robotObj);
  			}
			//加入到聊天口
			appendMessage(data);
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
		$("div#"+robotId).append(
				data.robotName + '  ' + time + '\n' + data.content + '\n');
		//至底
		var scrollTop = $("div#"+robotId)[0].scrollHeight;
		$("div#"+robotId).scrollTop(scrollTop);
	}
	
	//添加机器人
	function addRobotList(robotObj, isSelect){
		if(isSelect){
			$('li.robotLi').css({"border":"1px blue solid"});
			$('div.contentDiv').hide();
			$('#robotList').append($("<li class='robotLi' style='border:1px red solid' id='"+robotObj.robotId+"'>"+robotObj.robotName+"("+robotObj.count+")</li>"));
			$('#chatContent').append($("<div class='contentDiv' id='"+robotObj.robotId+"' style='width:100%; height:100%;'></div>"));
		}else{
			$('#robotList').append($("<li class='robotLi' style='border:1px blue solid' id='"+robotObj.robotId+"'>"+robotObj.robotName+"("+robotObj.count+")</li>"));
			$('#chatContent').append($("<div class='contentDiv' id='"+robotObj.robotId+"' style='width:100%; height:100%; display:none'></div>"));
		}
		$('li.robotLi').on('click',function(){
			robotClick();
		});
	}
	
	//更新
	function updateRobotList(robotObj){
		$('li#'+robotObj.robotId).text(robotObj.robotName+"("+robotObj.count+")");
	}
	
	//删除
	function deleteRobotList(robotId){
		$('#'+robotId).remove();
	}
	
	//加载机器人列表
	function initRobotList(selectRobotId){
		//列表
		$('#robotList').empty();
		for(robotObj in robotObjList){
			if(robotObj.robotId = selectRobotId){
				$('#robotList').append($("<li class='robotLi' style='border:1px red solid' id='"+robotObj.robotId+"'>"+robotObj.robotName+"("+robotObj.count+")</li>"));
			}else{
				$('#robotList').append($("<li class='robotLi' style='border:1px blue solid' id='"+robotObj.robotId+"'>"+robotObj.robotName+"("+robotObj.count+")</li>"));
			}
			//聊天口
			if($("#"+robotObj.robotId) == undefined){
				if(robotObj.robotId = selectRobotId){
					$('#chatContent').append($("<div class='contentDiv' id='"+robotObj.robotId+"' style='width:100%; height:100%;'></div>"));
				}else{
					$('#chatContent').append($("<div class='contentDiv' id='"+robotObj.robotId+"' style='width:100%; height:100%; display:none'></div>"));
				}
			}
		}
		$('li.robotLi').on('click',function(){
			robotClick();
		});
	}
	
	//机器人点击
	function robotClick(){
		
	}
	
</script>

</html>