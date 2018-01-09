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

<div class="content-wrapper" style="min-height: 540px;" onunload="goodbye()">
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
        <div id="chatDiv" style="width:1000px;height:400px;border:1px red solid">
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

<script type="text/javascript">
	
	$(function(){
		
		initChatPage();
		
		//发送
	    $('#send').click(function() {
	    	sendMessage();
	    });
		$('#history').click(function() {
	     	//取最近50条历史记录
	     	listHistoryUserAndRobot(currentUserId, currentRobotId, 50);
	    });
		
	});
	
	
	
	
</script>

