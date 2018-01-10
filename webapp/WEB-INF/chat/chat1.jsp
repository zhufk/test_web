<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<% String contenxtPath = request.getContextPath(); %>
<c:set var="contenxtPath" value="${pageContext.request.contextPath}"/>

<style>
	.robotLi{
		border:1px blue solid;
		list-style:none;
	}
	
	.robotSel{
		background-color: yellow;
	}
	
	.robotHover{
		background-color: lightblue;
	}
	
	.textDiv{
		background-color: white;
		width:100%;
		height:100%;
		overflow:auto;
	}
	
	.userMsg{
		color: blue;
		word-wrap: break-word;
	}
	
	.robotMsg{
		word-wrap: break-word;
	}
	
	
</style>


<div class="content-wrapper" style="min-height: 540px;">

    <!-- Main content -->
    <section class="content">
        <div class="row">
            <button id="connect" type="button">
                <i class="fa fa-search"></i> 连接
            </button>
            <button id="disconnect" type="button">
                <i class="fa fa-search"></i> 断开
            </button>
        </div>
        
        <div style="width:1000px;height:400px;border:1px red solid">
        	<div id="robotList" style="width:200px;height:400px; border:1px red solid; float:left">
        	</div>
        	<div style="width:500px;height:400px;border:1px red solid; float:left">
        		<div id="contentList" style="width:500px;height:350px;border:1px red solid">
        		</div>
        		<div id="sendContent" style="width:500px;height:50px;border:1px red solid">
        			<input id="messageInput" type="text" style="width:300px"
						name="message" onkeyup="eKeyup(event)" />
					<button id="send" type="button" >
						<i class="fa fa-refresh"></i> 发送
					</button>
					<button id="history" type="button" >
						<i class="fa fa-refresh"></i> 历史记录
					</button>
        		</div>
        	</div>
        	<div id="historyList" style="width:298px;height:400px; border:1px red solid; float:left">
        	</div>
        </div>
    </section>
</div>

<script  src="${publicPath}/assets/js/stomp.js"></script>
<script type="text/javascript">
	
	var robotObjList = [];
	
	var currentUserId = "userId1";
	var currentUserName = "朱富昆";
	var currentRobotId = null;
	var currentRobotName = null;
	
	var client;
	var url = 'ws://localhost:61614';
    var login = 'admin';
    var passcode = 'admin';
    var receiveDest = '/topic/chat-one';
    var sendDest = '/topic/chat-two';
	
  	$(document).ready(function() {
  		if(window.WebSocket) {
	    	//连接
	        $('#connect').click(function() {
	
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
	            		setTimeout(function(){
	            			receiveMessage(data);
	            		},100);
	            		
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
	        	sendMessage();
	        });
	        $('#history').click(function() {
	        	//取最近50条历史记录
	        	listHistoryUserAndRobot(currentUserId, currentRobotId, 50);
	        });
        
      	} else {
	    	$("body").html("<h1>Your browser does not support WebSockets. This example will not work properly.");
	  	}
    });
  	
  	function goodbye(){
  		alert(client);
  		client.disconnect(function() {
      	});
  	}
  	
  	//获取用户历史消息
  	function listHistoryUser(userId, size){
  		if(userId != null){
	 		$.ajax({
	 			type : 'post',
	 			url : '<%=contenxtPath%>/chat/list_history_user',
	 			data : JSON.stringify({
	 				"userId" : userId,
	 				"size" : size
	 			}),
	 			contentType: 'application/json;charset=utf-8',
	 			success : function(result) {
	 				console.log("收到历史消息:" + JSON.stringify(result));
	 			}
	 		});
  		}
  	}
  	
    //获取用户机器人历史消息
  	function listHistoryUserAndRobot(userId, robotId, size){
  		if(robotId != null){
  			$("#historyList>div#"+robotId).empty();
  	  		$.ajax({
  	  			type : 'post',
  	  			url : '<%=contenxtPath%>/chat/list_history_user_robot',
  	  			data : JSON.stringify({
  	  				"userId" : userId,
  	  				"robotId" : robotId,
  	  				"size" : size
  	  			}),
  	  			contentType: 'application/json;charset=utf-8',
  	  			success : function(result) {
  	  				console.log("收到历史消息:" + JSON.stringify(result));
  	  				appendUserAndRobotHistoryMessages(result);
  	  			}
  	  		});
  		}
  	}
  	
  	//加入历史消息
  	function addHistoryUserAndRobot(data){
  		$.ajax({
  			type : 'post',
  			url : '<%=contenxtPath%>/chat/add_history_user_robot',
  			data : JSON.stringify(data),
  			contentType: 'application/json;charset=utf-8',
  			success : function(result) {
  				console.log("加入历史消息:" + JSON.stringify(result));
  			}
  		});
  	}
  	
  	/////////////////////////////////////////////////////////////
  	//发送
	function sendMessage() {
  		if(currentRobotId != null){
  			var time = dateFormat(new Date());
  			var content = $("#messageInput").val();
  			var data = {
  				"robotId" : currentRobotId,
  				"robotName" : currentRobotName,
  				"userId" : currentUserId,
  				"userName" : currentUserName,
  				"time" : time,
  				"type" : "1",
  				"content" : content
  			}
  			//发送信息
  			client.send(sendDest, {}, JSON.stringify(data));
  			
  			$("#messageInput").val('');
  			appendMessage(data, 1);
  			//添加到历史库
  			addHistoryUserAndRobot(data);
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
  		if(data.userId == currentUserId){
  			var robotId = data.robotId;
  			var robotName = data.robotName;
  			
  			var robotObj = null;
  			for(var i=0; i<robotObjList.length;i++){
  				if(robotObjList[i].robotId == robotId){
  					robotObj = robotObjList[i];
  					break;
  				}
  			}
  			//不存在
  			if (robotObj == null){
  				robotObj = {
  					robotId : robotId,
  					robotName : robotName,
  					count : 1
  				};
  				robotObjList.push(robotObj);
  				
  				if(currentRobotId == null){
  					currentRobotId = robotId;
  					currentRobotName = robotName;
  					addRobotList(robotObj, true);	
  				}else{
  					addRobotList(robotObj, false);	
  				}
  			}else{//存在
  				updateRobotList(robotObj);
  			}
			//加入到聊天口
			appendMessage(data, 0);
  		}
  	}

	//添加机器人
	function addRobotList(robotObj, isSelect){
		if(isSelect == true){
			$('li.robotLi').removeClass("robotSel");
			$('div.textDiv').hide();
			$('#robotList').append($("<li class='robotLi robotSel' id='"+robotObj.robotId+"'>"+robotObj.robotName+"("+robotObj.count+")</li>"));
			$('#contentList').append($("<div class='textDiv' id='"+robotObj.robotId+"'></div>"));
			$('#historyList').append($("<div class='textDiv' id='"+robotObj.robotId+"'></div>"));
		}else{
			$('#robotList').append($("<li class='robotLi' id='"+robotObj.robotId+"'>"+robotObj.robotName+"("+robotObj.count+")</li>"));
			$('#contentList').append($("<div class='textDiv' id='"+robotObj.robotId+"' style='display:none'></div>"));
			$('#historyList').append($("<div class='textDiv' id='"+robotObj.robotId+"' style='display:none'></div>"));
		}
		robotClick();
	}
	
	//更新
	function updateRobotList(robotObj){
		robotObj.count++;
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
		$('#contentList').empty();
		$('#historyList').empty();
		for(var i=0; i<robotObjList.length;i++){
			if(robotObjList[i].robotId == selectRobotId){
				$('#robotList').append($("<li class='robotLi robotSel' id='"+robotObj.robotId+"'>"+robotObj.robotName+"("+robotObj.count+")</li>"));
			}else{
				$('#robotList').append($("<li class='robotLi' id='"+robotObj.robotId+"'>"+robotObj.robotName+"("+robotObj.count+")</li>"));
			}
			//聊天口
			if($("#"+robotObj.robotId) == undefined){
				if(robotObj.robotId == selectRobotId){
					$('#contentList').append($("<div class='textDiv' id='"+robotObj.robotId+"'></div>"));
					$('#historyList').append($("<div class='textDiv' id='"+robotObj.robotId+"'></div>"));
				}else{
					$('#contentList').append($("<div class='textDiv' id='"+robotObj.robotId+"' style='display:none'></div>"));
					$('#historyList').append($("<div class='textDiv' id='"+robotObj.robotId+"' style='display:none'></div>"));
				}
			}
		}
		robotClick();
       	
	}
	
	//机器人点击
	function robotClick(){
		$('li.robotLi').hover(function()
        {
       		//$('li.robotLi').removeClass('robotSel');
            $(this).addClass('robotHover');
        }, function()
        {
            $(this).removeClass('robotHover');
        }).on('click',function(){
        	$('li.robotLi').removeClass('robotSel');
        	$(this).addClass('robotSel');
        	var robotId = $(this)[0].id;
    		var text = $(this).text();
    		var robotName = text.substring(0,text.lastIndexOf("("));
    		currentRobotId = robotId;
    		currentRobotName = robotName;
    		$('div.textDiv').hide();
    		$('div#'+robotId).show();
		});
	}
	
	//添加信息到内容框
	function appendMessage(data, type) {
		var time = data.time;
		if (typeof time == 'number') {
			time = dateFormat(new Date(time));
		} else if (time instanceof Date) {
			time = dateFormat(time);
		}
		if(type == 1){
			$("#contentList>div#"+data.robotId).append('<div class="userMsg">'+
					data.userName + '   ' + time + '<br>' + data.content + '</div>');
		}else{
			$("#contentList>div#"+data.robotId).append('<div class="robotMsg">'+
					data.robotName + '   ' + time + '<br>' + data.content + '</div');
		}
		
		//至底
		var scrollTop = $("#contentList>div#"+data.robotId)[0].scrollHeight;
		$("#contentList>div#"+data.robotId).scrollTop(scrollTop);
	}
	
	//添加历史信息到内容框
	function appendUserAndRobotHistoryMessages(datas){
		for (var i = 0; i < datas.length; i++) {
			var data = datas[i];
			
			var time = data.time;
			if (typeof time == 'number') {
				time = dateFormat(new Date(time));
			} else if (time instanceof Date) {
				time = dateFormat(time);
			}
			if(data.type == 1){
				$("#historyList>div#"+data.robotId).append('<div class="userMsg">'+
						data.userName + '   ' + time + '<br>' + data.content + '</div>');
			}else{
				$("#historyList>div#"+data.robotId).append('<div class="robotMsg">'+
						data.robotName + '   ' + time + '<br>' + data.content + '</div');
			}
			
			//至底
			var scrollTop = $("#historyList>div#"+data.robotId)[0].scrollHeight;
			$("#historyList>div#"+data.robotId).scrollTop(scrollTop);
		}
	}
	
</script>

