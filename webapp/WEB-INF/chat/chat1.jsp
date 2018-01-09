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
		background-color: blue;
	}
	
	.robotDisSel{
		background-color: gray;
	}
	
	
	
</style>

<div class="content-wrapper" style="min-height: 540px;">
    <!-- Content Header (Page header) -->
    <section class="content-header">
<!--         <h1 style="font-family: KaiTi_GB2312">聊天</h1> -->
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
            <button id="connect" type="button">
                <i class="fa fa-search"></i> 连接
            </button>
            <button id="disconnect" type="button">
                <i class="fa fa-search"></i> 断开
            </button>
        </div>
        
        <div style="width:900px;height:400px;border:1px red solid">
        	<div id="robotList" style="width:200px;height:400px; border:1px red solid; float:left">
<!--         		<ul id="robotList" style="padding:2px"> -->
<!--         		</ul> -->
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
        	<div id="historyList" style="width:198px;height:400px; border:1px red solid; float:left">
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
	var receiveDest;
	var Dest;
	
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
	        	send();
	        });
	        $('#history').click(function() {
	        	listHistoryUserAndRobot(currentUserId,currentRobotId,10);
	        });
        
      	} else {
	    	$("body").html("<h1>Your browser does not support WebSockets. This example will not work properly.");
	  	}
    });
  	
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
  			$("#historyList>textarea#"+robotId).empty();
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
	function send() {
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
		debugger
		if(isSelect == true){
			$('li.robotLi').removeClass("robotSel");
			$('textarea.text').hide();
			$('#robotList').append($("<li class='robotLi robotSel' id='"+robotObj.robotId+"'>"+robotObj.robotName+"("+robotObj.count+")</li>"));
			$('#contentList').append($("<textarea class='text' id='"+robotObj.robotId+"' style='width:100%; height:100%;'></textarea>"));
			$('#historyList').append($("<textarea class='text' id='"+robotObj.robotId+"' style='width:100%; height:100%;'></textarea>"));
		}else{
			$('#robotList').append($("<li class='robotLi' id='"+robotObj.robotId+"'>"+robotObj.robotName+"("+robotObj.count+")</li>"));
			$('#contentList').append($("<textarea class='text' id='"+robotObj.robotId+"' style='width:100%; height:100%; display:none'></textarea>"));
			$('#historyList').append($("<textarea class='text' id='"+robotObj.robotId+"' style='width:100%; height:100%; display:none'></textarea>"));
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
					$('#contentList').append($("<textarea class='text' id='"+robotObj.robotId+"' style='width:100%; height:100%;'></textarea>"));
					$('#historyList').append($("<textarea class='text' id='"+robotObj.robotId+"' style='width:100%; height:100%;'></textarea>"));
				}else{
					$('#contentList').append($("<textarea class='text' id='"+robotObj.robotId+"' style='width:100%; height:100%; display:none'></textarea>"));
					$('#historyList').append($("<textarea class='text' id='"+robotObj.robotId+"' style='width:100%; height:100%; display:none'></textarea>"));
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
            //$(this).addClass('robotSel');
        }, function()
        {
            //$(this).removeClass('robotSel');
        }).on('click',function(){
        	$('li.robotLi').removeClass('robotSel');
        	$(this).addClass('robotSel');
        	var robotId = $(this)[0].id;
    		var text = $(this).text();
    		var robotName = text.substring(0,text.lastIndexOf("("));
    		currentRobotId = robotId;
    		currentRobotName = robotName;
    		$('textarea.text').hide();
    		$('textarea#'+robotId).show();
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
			$("#contentList>textarea#"+data.robotId).append(
					data.userName + '  ' + time + '\n' + data.content + '\n');
		}else{
			$("#contentList>textarea#"+data.robotId).append(
					data.robotName + '  ' + time + '\n' + data.content + '\n');
		}
		
		//至底
		var scrollTop = $("#contentList>textarea#"+data.robotId)[0].scrollHeight;
		$("#contentList>textarea#"+data.robotId).scrollTop(scrollTop);
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
				$("#historyList>textarea#"+data.robotId).append(
						data.userName + '  ' + time + '\n' + data.content + '\n');
			}else{
				$("#historyList>textarea#"+data.robotId).append(
						data.robotName + '  ' + time + '\n' + data.content + '\n');
			}
			
			//至底
			var scrollTop = $("#historyList>textarea#"+data.robotId)[0].scrollHeight;
			$("#historyList>textarea#"+data.robotId).scrollTop(scrollTop);
		}
	}
	
</script>

