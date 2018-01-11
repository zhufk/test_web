
CHAT = {
	//新信息计数
	newMessageCount : 0,
	//历史信息数组
	messageList : [],
	//机器人对象信息
	robotObjList : [],

	//当前用户
	currentUserId : "userId1",
	currentUserName : "朱富昆",
	//当前聊天机器人
	currentRobotId : null,
	currentRobotName : null,

	client : null,
	url : 'ws://localhost:61614',
	login : 'admin',
	passcode : 'admin',
	receiveDest : '/topic/robot-user',
	sendDest : '/topic/user-robot',
	
	//初始化聊天功能
	initChat : function() {
		if(window.WebSocket) {
	    	//连接
	        CHAT.client = Stomp.client(CHAT.url);

	        CHAT.client.debug = function(str) {
	        	//$("#debug").append(document.createTextNode(str + "\n"));
	        };
	      
	        //连接服务器
	        CHAT.client.connect(CHAT.login, CHAT.passcode, function(frame) {
	    		CHAT.client.debug("connected to Stomp");
	        	//订阅主题并注册消息接收处理事件  
	        	CHAT.client.subscribe(CHAT.receiveDest, function(message) {
	        		console.log(message.body);
	        		var data = JSON.parse(message.body);
	        		if(data.userId == CHAT.currentUserId){
	        			if($("#chatDiv").length == 0){
	        				//标记是否已读
	        				data.isRead = false;
	        				//没有打开聊天界面，信息提示加1
	            			CHAT.newMessageCount++;
	                		$('#msgTip').text(CHAT.newMessageCount);
	            		}else{
	            			//标记是否已读
	            			if(data.robotId == CHAT.currentRobotId){
	            				data.isRead = true;
	            			}else{
	            				data.isRead = false;
	            			}
	            			//已经打开聊天界面
	            			CHAT.receiveMessage(data);
	            			
	            		}
	        			//添加到前端历史库
	        			CHAT.messageList.push(data);
	        			//保留100条前端历史
	        			if(CHAT.messageList.length>100){
	        				CHAT.messageList.shift();
	        			}
	        		}
	        	});
	      	});
	    
	  	}
	},
	//初始化聊天界面
	initChatPage : function (){
		//清空机器人列表
		CHAT.robotObjList = [];
		CHAT.currentRobotId = null;
		CHAT.currentRobotName = null;
		
		//加入收到的信息
		for(var i=0;i<CHAT.messageList.length;i++){
			CHAT.receiveMessage(CHAT.messageList[i]); 
		}
		
		//新信息提示清空
		CHAT.newMessageCount = 0;
		$('#msgTip').text('');
	},

	//获取用户历史消息
	listHistoryUser : function(userId, size){
		if(userId != null){
	 		$.ajax({
	 			type : 'post',
	 			url : 'chat/list_history_user',
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
	},

	//获取用户机器人历史消息
	listHistoryUserAndRobot : function(userId, robotId, size){
		if(robotId != null){
			$("#historyList>div#"+robotId).empty();
	  		$.ajax({
	  			type : 'post',
	  			url : 'chat/list_history_user_robot',
	  			data : JSON.stringify({
	  				"userId" : userId,
	  				"robotId" : robotId,
	  				"size" : size
	  			}),
	  			contentType: 'application/json;charset=utf-8',
	  			success : function(result) {
	  				console.log("收到历史消息:" + JSON.stringify(result));
	  				CHAT.appendUserAndRobotHistoryMessages(result);
	  			}
	  		});
		}
	},

	//加入历史消息
	addHistoryUserAndRobot : function(data){
		$.ajax({
			type : 'post',
			url : 'chat/add_history_user_robot',
			data : JSON.stringify(data),
			contentType: 'application/json;charset=utf-8',
			success : function(result) {
				console.log("加入历史消息:" + JSON.stringify(result));
			}
		});
	},

	/////////////////////////////////////////////////////////////
	//发送
	sendMessage : function() {
		if(CHAT.currentRobotId != null){
			var time = (new Date()).format("yyyy-MM-dd HH:mm:ss");
			var content = $("#messageInput").val();
			var data = {
				"robotId" : CHAT.currentRobotId,
				"robotName" : CHAT.currentRobotName,
				"userId" : CHAT.currentUserId,
				"userName" : CHAT.currentUserName,
				"time" : time,
				"type" : "1",
				"content" : content
			}
			//发送信息
			CHAT.client.send(CHAT.sendDest, {}, JSON.stringify(data));
			
			$("#messageInput").val('');
			//标记是否已读
			data.isRead = true;
			CHAT.appendMessage(data);
			
			//添加到前端历史库
			CHAT.messageList.push(data);
			if(CHAT.messageList.length>10){
				CHAT.messageList.shift();
			}
			//添加到后端历史库
			CHAT.addHistoryUserAndRobot(data);
		}
		
	},

	//回车发送信息
	eKeyup : function (e) {
		e = e ? e : (window.event ? window.event : null);
		if (e.keyCode == 13)//Enter
		{
			CHAT.sendMessage();
		}
	},

	///////////////////////////////////////////////////////
	//接收
	receiveMessage : function(data){
		if(data.userId == CHAT.currentUserId){
			var robotId = data.robotId;
			var robotName = data.robotName;
			
			var robotObj = null;
			for(var i=0; i<CHAT.robotObjList.length;i++){
				if(CHAT.robotObjList[i].robotId == robotId){
					robotObj = CHAT.robotObjList[i];
					break;
				}
			}
			//机器人信息不存在
			if (robotObj == null){
				robotObj = {
					robotId : robotId,
					robotName : robotName,
					count : 0
				};
				if(data.isRead == false){
					robotObj.count++;
				}
				CHAT.robotObjList.push(robotObj);
				
				if(CHAT.currentRobotId == null){
					CHAT.currentRobotId = robotId;
					CHAT.currentRobotName = robotName;
					//添加机器人列表，并选中
					CHAT.addRobotList(robotObj, true);	
				}else{
					//添加机器人列表，不选中
					CHAT.addRobotList(robotObj, false);	
				}
			}else{//机器人信息存在
				//更新机器人列表
				if(robotObj.robotId != CHAT.currentRobotId && data.isRead == false){
					robotObj.count++;
					$('li#'+robotObj.robotId).text(robotObj.robotName+"("+robotObj.count+")");
				}
			}
			//信息加入到聊天口
			CHAT.appendMessage(data);
		}
	},

	//添加机器人列表
	addRobotList : function(robotObj, isSelect){
		if(isSelect == true){
			$('li.robotLi').removeClass("robotSel");
			$('div.textDiv').hide();
			$('#robotList').append($("<li class='robotLi robotSel' id='"+robotObj.robotId+"'>"+robotObj.robotName+"</li>"));
			$('#contentList').append($("<div class='textDiv' id='"+robotObj.robotId+"'></div>"));
			$('#historyList').append($("<div class='textDiv' id='"+robotObj.robotId+"'></div>"));
		}else{
			var text = robotObj.robotName+"("+robotObj.count+")";
			if(robotObj.count == 0){
				text = robotObj.robotName;
			}
			$('#robotList').append($("<li class='robotLi' id='"+robotObj.robotId+"'>"+text+"</li>"));
			$('#contentList').append($("<div class='textDiv' id='"+robotObj.robotId+"' style='display:none'></div>"));
			$('#historyList').append($("<div class='textDiv' id='"+robotObj.robotId+"' style='display:none'></div>"));
		}
		CHAT.robotClick();
	},

	//删除聊天
	deleteRobotList : function(robotId){
		//$('#'+robotId).remove();
	},

	//机器人点击
	robotClick : function(){
		$('li.robotLi').hover(function()
	    {
	   		//$('li.robotLi').removeClass('robotSel');
	        $(this).addClass('robotHover');
	    }, function()
	    {
	        $(this).removeClass('robotHover');
	    }).on('click',function(){
	    	//样式修改
	    	$('li.robotLi').removeClass('robotSel');
	    	$(this).addClass('robotSel');
	    	//当前聊天机器人修改
	    	var robotId = $(this)[0].id;
			var robotName = $(this).text();
			CHAT.currentRobotId = robotId;
			CHAT.currentRobotName = robotName;
			//机器人列表未读信息数清空
			var index = robotName.lastIndexOf("(")
			if(index > 0){
				robotName = robotName.substring(0,index);
			}
			$('li#'+robotId).text(robotName);
			for(var i=0; i<CHAT.robotObjList.length;i++){
				if(CHAT.robotObjList[i].robotId == robotId){
					CHAT.robotObjList[i].count = 0;
					break;
				}
			}
			//此机器人的所有历史消息标记已读
			for(var i=0;i<CHAT.messageList.length;i++){
				if(CHAT.messageList[i].robotId == robotId){
					CHAT.messageList[i].isRead = true;
				}
			}
			
			//聊天内容与历史窗口切换
			$('div.textDiv').hide();
			$('div#'+robotId).show();
		});
	},

	//添加信息到内容框
	appendMessage : function(data) {
		var time = data.time;
		if (typeof time == 'number') {
			time = (new Date(time)).format("yyyy-MM-dd HH:mm:ss");
		} else if (time instanceof Date) {
			time = time.format("yyyy-MM-dd HH:mm:ss");
		}
		if(data.type == 1){
			$("#contentList>div#"+data.robotId).append('<div class="userMsg">'+
					data.userName + '   ' + time + '<br>' + data.content + '</div>');
		}else{
			$("#contentList>div#"+data.robotId).append('<div class="robotMsg">'+
					data.robotName + '   ' + time + '<br>' + data.content + '</div');
		}
		
		//至底
		var scrollTop = $("#contentList>div#"+data.robotId)[0].scrollHeight;
		$("#contentList>div#"+data.robotId).scrollTop(scrollTop);
	},

	//添加历史信息到内容框
	appendUserAndRobotHistoryMessages : function(datas){
		for (var i = 0; i < datas.length; i++) {
			var data = datas[i];
			
			var time = data.time;
			if (typeof time == 'number') {
				time = (new Date(time)).format("yyyy-MM-dd HH:mm:ss");
			} else if (time instanceof Date) {
				time = time.format("yyyy-MM-dd HH:mm:ss");
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
}





