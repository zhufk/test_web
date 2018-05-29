<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>在线Cron表达式生成器</title>
<!-- <meta name="description" -->
<!-- 	content="通过这个生成器,您可以在线生成任务调度比如Quartz的Cron表达式,对Quartz Cron 表达式的可视化双向解析和生成." /> -->
<!-- <meta name="keywords" -->
<!-- 	content="cron creater,generate Cron Expression,Cron Expression online,Quartz Cron Expresssion,cron在线生成工具" /> -->
<!-- <link href="Cron/themes/bootstrap/easyui.min.css" rel="stylesheet" -->
<!-- 	type="text/css" /> -->

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">

<link href="src=" ${appPath}/crontab/easyui.min.css" rel="stylesheet"
	type="text/css" />
<link href="src=" ${appPath}/crontab/icon.css" rel="stylesheet"
	type="text/css" />
<jsp:include page="/WEB-INF/common/css_common.jsp"></jsp:include>
<jsp:include page="/WEB-INF/common/js_common.jsp" />

<style type="text/css">
.line {
	height: 25px;
	line-height: 25px;
	margin: 3px;
}

.imp {
	padding-left: 25px;
}

.col {
	width: 95px;
}

ul {
	list-style: none;
	padding-left: 10px;
}

li {
	height: 20px;
}
</style>
</head>
<body>
	<center>
		<div class="easyui-layout layout"
			style="width: 830px; height: 560px; border: 1px rgb(202, 196, 196) solid; border-radius: 5px;">
			<div style="height: 100%;" class="panel-noscroll">
				<div class="easyui-tabs tabs-container"
					data-options="fit:true,border:false"
					style="width: 830px; height: 560px;">
					<div class="tabs-header tabs-header-noborder" style="width: 830px;">
						<div class="tabs-scroller-left" style="display: none;"></div>
						<div class="tabs-scroller-right" style="display: none;"></div>
						<div class="tabs-wrap"
							style="margin-left: 0px; margin-right: 0px; width: 830px;">
							<ul class="tabs">
								<li class="tabs-selected"><a href="javascript:void(0)"
									class="tabs-inner"><span class="tabs-title">秒</span><span
										class="tabs-icon"></span></a></li>
								<li><a href="javascript:void(0)" class="tabs-inner"><span
										class="tabs-title">分钟</span><span class="tabs-icon"></span></a></li>
								<li><a href="javascript:void(0)" class="tabs-inner"><span
										class="tabs-title">小时</span><span class="tabs-icon"></span></a></li>
								<li><a href="javascript:void(0)" class="tabs-inner"><span
										class="tabs-title">日</span><span class="tabs-icon"></span></a></li>
								<li><a href="javascript:void(0)" class="tabs-inner"><span
										class="tabs-title">月</span><span class="tabs-icon"></span></a></li>
								<li><a href="javascript:void(0)" class="tabs-inner"><span
										class="tabs-title">周</span><span class="tabs-icon"></span></a></li>
								<li><a href="javascript:void(0)" class="tabs-inner"><span
										class="tabs-title">年</span><span class="tabs-icon"></span></a></li>
							</ul>
						</div>
					</div>
					<div class="tabs-panels tabs-panels-noborder"
						style="height: 531px; width: 830px;">
						<div class="panel" style="display: block; width: 830px;">
							<div title=""
								class="panel-body panel-body-noheader panel-body-noborder"
								style="width: 830px; height: 531px;">
								<div class="line">
									<input type="radio" checked="checked" name="second"
										onclick="everyTime(this)"> 每秒 允许的通配符[, - * /]
								</div>
								<div class="line">
									<input type="radio" name="second" onclick="cycle(this)">
									周期从 <span class="spinner" style="width: 58px; height: 20px;"><input
										class="numberspinner numberspinner-f spinner-text spinner-f validatebox-text numberbox-f"
										style="width: 36px; height: 20px; line-height: 20px;"
										data-options="min:1,max:58" value="1" id="secondStart_0"><input
										type="hidden" value="1"><span class="spinner-arrow"
										style="height: 20px;"><span class="spinner-arrow-up"
											style="height: 10px;"></span><span class="spinner-arrow-down"
											style="height: 10px;"></span></span></span> - <span class="spinner"
										style="width: 58px; height: 20px;"><input
										class="numberspinner numberspinner-f spinner-text spinner-f validatebox-text numberbox-f"
										style="width: 36px; height: 20px; line-height: 20px;"
										data-options="min:2,max:59" value="2" id="secondEnd_0"><input
										type="hidden" value="2"><span class="spinner-arrow"
										style="height: 20px;"><span class="spinner-arrow-up"
											style="height: 10px;"></span><span class="spinner-arrow-down"
											style="height: 10px;"></span></span></span> 秒
								</div>
								<div class="line">
									<input type="radio" name="second" onclick="startOn(this)">
									从 <span class="spinner" style="width: 58px; height: 20px;"><input
										class="numberspinner numberspinner-f spinner-text spinner-f validatebox-text numberbox-f"
										style="width: 36px; height: 20px; line-height: 20px;"
										data-options="min:0,max:59" value="0" id="secondStart_1"><input
										type="hidden" value="0"><span class="spinner-arrow"
										style="height: 20px;"><span class="spinner-arrow-up"
											style="height: 10px;"></span><span class="spinner-arrow-down"
											style="height: 10px;"></span></span></span> 秒开始,每 <span class="spinner"
										style="width: 58px; height: 20px;"><input
										class="numberspinner numberspinner-f spinner-text spinner-f validatebox-text numberbox-f"
										style="width: 36px; height: 20px; line-height: 20px;"
										data-options="min:1,max:59" value="1" id="secondEnd_1"><input
										type="hidden" value="1"><span class="spinner-arrow"
										style="height: 20px;"><span class="spinner-arrow-up"
											style="height: 10px;"></span><span class="spinner-arrow-down"
											style="height: 10px;"></span></span></span> 秒执行一次
								</div>
								<div class="line">
									<input type="radio" name="second" id="sencond_appoint">
									指定
								</div>
								<div class="imp secondList">
									<input type="checkbox" value="0">00 <input
										type="checkbox" value="1">01 <input type="checkbox"
										value="2">02 <input type="checkbox" value="3">03
									<input type="checkbox" value="4">04 <input
										type="checkbox" value="5">05 <input type="checkbox"
										value="6">06 <input type="checkbox" value="7">07
									<input type="checkbox" value="8">08 <input
										type="checkbox" value="9">09
								</div>
								<div class="imp secondList">
									<input type="checkbox" value="10">10 <input
										type="checkbox" value="11">11 <input type="checkbox"
										value="12">12 <input type="checkbox" value="13">13
									<input type="checkbox" value="14">14 <input
										type="checkbox" value="15">15 <input type="checkbox"
										value="16">16 <input type="checkbox" value="17">17
									<input type="checkbox" value="18">18 <input
										type="checkbox" value="19">19
								</div>
								<div class="imp secondList">
									<input type="checkbox" value="20">20 <input
										type="checkbox" value="21">21 <input type="checkbox"
										value="22">22 <input type="checkbox" value="23">23
									<input type="checkbox" value="24">24 <input
										type="checkbox" value="25">25 <input type="checkbox"
										value="26">26 <input type="checkbox" value="27">27
									<input type="checkbox" value="28">28 <input
										type="checkbox" value="29">29
								</div>
								<div class="imp secondList">
									<input type="checkbox" value="30">30 <input
										type="checkbox" value="31">31 <input type="checkbox"
										value="32">32 <input type="checkbox" value="33">33
									<input type="checkbox" value="34">34 <input
										type="checkbox" value="35">35 <input type="checkbox"
										value="36">36 <input type="checkbox" value="37">37
									<input type="checkbox" value="38">38 <input
										type="checkbox" value="39">39
								</div>
								<div class="imp secondList">
									<input type="checkbox" value="40">40 <input
										type="checkbox" value="41">41 <input type="checkbox"
										value="42">42 <input type="checkbox" value="43">43
									<input type="checkbox" value="44">44 <input
										type="checkbox" value="45">45 <input type="checkbox"
										value="46">46 <input type="checkbox" value="47">47
									<input type="checkbox" value="48">48 <input
										type="checkbox" value="49">49
								</div>
								<div class="imp secondList">
									<input type="checkbox" value="50">50 <input
										type="checkbox" value="51">51 <input type="checkbox"
										value="52">52 <input type="checkbox" value="53">53
									<input type="checkbox" value="54">54 <input
										type="checkbox" value="55">55 <input type="checkbox"
										value="56">56 <input type="checkbox" value="57">57
									<input type="checkbox" value="58">58 <input
										type="checkbox" value="59">59
								</div>
							</div>
						</div>
						<div class="panel" style="display: none;">
							<div title=""
								class="panel-body panel-body-noheader panel-body-noborder" id="">
								<div class="line">
									<input type="radio" checked="checked" name="min"
										onclick="everyTime(this)"> 分钟 允许的通配符[, - * /]
								</div>
								<div class="line">
									<input type="radio" name="min" onclick="cycle(this)">
									周期从 <span class="spinner" style="width: 58px; height: 20px;"><input
										class="numberspinner numberspinner-f spinner-text spinner-f validatebox-text numberbox-f"
										style="width: 36px; height: 20px; line-height: 20px;"
										data-options="min:1,max:58" value="1" id="minStart_0"><input
										type="hidden" value="1"><span class="spinner-arrow"
										style="height: 20px;"><span class="spinner-arrow-up"
											style="height: 10px;"></span><span class="spinner-arrow-down"
											style="height: 10px;"></span></span></span> - <span class="spinner"
										style="width: 58px; height: 20px;"><input
										class="numberspinner numberspinner-f spinner-text spinner-f validatebox-text numberbox-f"
										style="width: 36px; height: 20px; line-height: 20px;"
										data-options="min:2,max:59" value="2" id="minEnd_0"><input
										type="hidden" value="2"><span class="spinner-arrow"
										style="height: 20px;"><span class="spinner-arrow-up"
											style="height: 10px;"></span><span class="spinner-arrow-down"
											style="height: 10px;"></span></span></span> 分钟
								</div>
								<div class="line">
									<input type="radio" name="min" onclick="startOn(this)">
									从 <span class="spinner" style="width: 58px; height: 20px;"><input
										class="numberspinner numberspinner-f spinner-text spinner-f validatebox-text numberbox-f"
										style="width: 36px; height: 20px; line-height: 20px;"
										data-options="min:0,max:59" value="0" id="minStart_1"><input
										type="hidden" value="0"><span class="spinner-arrow"
										style="height: 20px;"><span class="spinner-arrow-up"
											style="height: 10px;"></span><span class="spinner-arrow-down"
											style="height: 10px;"></span></span></span> 分钟开始,每 <span class="spinner"
										style="width: 58px; height: 20px;"><input
										class="numberspinner numberspinner-f spinner-text spinner-f validatebox-text numberbox-f"
										style="width: 36px; height: 20px; line-height: 20px;"
										data-options="min:1,max:59" value="1" id="minEnd_1"><input
										type="hidden" value="1"><span class="spinner-arrow"
										style="height: 20px;"><span class="spinner-arrow-up"
											style="height: 10px;"></span><span class="spinner-arrow-down"
											style="height: 10px;"></span></span></span> 分钟执行一次
								</div>
								<div class="line">
									<input type="radio" name="min" id="min_appoint"> 指定
								</div>
								<div class="imp minList">
									<input type="checkbox" value="0">00 <input
										type="checkbox" value="1">01 <input type="checkbox"
										value="2">02 <input type="checkbox" value="3">03
									<input type="checkbox" value="4">04 <input
										type="checkbox" value="5">05 <input type="checkbox"
										value="6">06 <input type="checkbox" value="7">07
									<input type="checkbox" value="8">08 <input
										type="checkbox" value="9">09
								</div>
								<div class="imp minList">
									<input type="checkbox" value="10">10 <input
										type="checkbox" value="11">11 <input type="checkbox"
										value="12">12 <input type="checkbox" value="13">13
									<input type="checkbox" value="14">14 <input
										type="checkbox" value="15">15 <input type="checkbox"
										value="16">16 <input type="checkbox" value="17">17
									<input type="checkbox" value="18">18 <input
										type="checkbox" value="19">19
								</div>
								<div class="imp minList">
									<input type="checkbox" value="20">20 <input
										type="checkbox" value="21">21 <input type="checkbox"
										value="22">22 <input type="checkbox" value="23">23
									<input type="checkbox" value="24">24 <input
										type="checkbox" value="25">25 <input type="checkbox"
										value="26">26 <input type="checkbox" value="27">27
									<input type="checkbox" value="28">28 <input
										type="checkbox" value="29">29
								</div>
								<div class="imp minList">
									<input type="checkbox" value="30">30 <input
										type="checkbox" value="31">31 <input type="checkbox"
										value="32">32 <input type="checkbox" value="33">33
									<input type="checkbox" value="34">34 <input
										type="checkbox" value="35">35 <input type="checkbox"
										value="36">36 <input type="checkbox" value="37">37
									<input type="checkbox" value="38">38 <input
										type="checkbox" value="39">39
								</div>
								<div class="imp minList">
									<input type="checkbox" value="40">40 <input
										type="checkbox" value="41">41 <input type="checkbox"
										value="42">42 <input type="checkbox" value="43">43
									<input type="checkbox" value="44">44 <input
										type="checkbox" value="45">45 <input type="checkbox"
										value="46">46 <input type="checkbox" value="47">47
									<input type="checkbox" value="48">48 <input
										type="checkbox" value="49">49
								</div>
								<div class="imp minList">
									<input type="checkbox" value="50">50 <input
										type="checkbox" value="51">51 <input type="checkbox"
										value="52">52 <input type="checkbox" value="53">53
									<input type="checkbox" value="54">54 <input
										type="checkbox" value="55">55 <input type="checkbox"
										value="56">56 <input type="checkbox" value="57">57
									<input type="checkbox" value="58">58 <input
										type="checkbox" value="59">59
								</div>
							</div>
						</div>
						<div class="panel" style="display: none;">
							<div title=""
								class="panel-body panel-body-noheader panel-body-noborder" id="">
								<div class="line">
									<input type="radio" checked="checked" name="hour"
										onclick="everyTime(this)"> 小时 允许的通配符[, - * /]
								</div>
								<div class="line">
									<input type="radio" name="hour" onclick="cycle(this)">
									周期从 <span class="spinner" style="width: 58px; height: 20px;"><input
										class="numberspinner numberspinner-f spinner-text spinner-f validatebox-text numberbox-f"
										style="width: 36px; height: 20px; line-height: 20px;"
										data-options="min:0,max:23" value="0" id="hourStart_0"><input
										type="hidden" value="0"><span class="spinner-arrow"
										style="height: 20px;"><span class="spinner-arrow-up"
											style="height: 10px;"></span><span class="spinner-arrow-down"
											style="height: 10px;"></span></span></span> - <span class="spinner"
										style="width: 58px; height: 20px;"><input
										class="numberspinner numberspinner-f spinner-text spinner-f validatebox-text numberbox-f"
										style="width: 36px; height: 20px; line-height: 20px;"
										data-options="min:2,max:23" value="2" id="hourEnd_1"><input
										type="hidden" value="2"><span class="spinner-arrow"
										style="height: 20px;"><span class="spinner-arrow-up"
											style="height: 10px;"></span><span class="spinner-arrow-down"
											style="height: 10px;"></span></span></span> 小时
								</div>
								<div class="line">
									<input type="radio" name="hour" onclick="startOn(this)">
									从 <span class="spinner" style="width: 58px; height: 20px;"><input
										class="numberspinner numberspinner-f spinner-text spinner-f validatebox-text numberbox-f"
										style="width: 36px; height: 20px; line-height: 20px;"
										data-options="min:0,max:23" value="0" id="hourStart_1"><input
										type="hidden" value="0"><span class="spinner-arrow"
										style="height: 20px;"><span class="spinner-arrow-up"
											style="height: 10px;"></span><span class="spinner-arrow-down"
											style="height: 10px;"></span></span></span> 小时开始,每 <span class="spinner"
										style="width: 58px; height: 20px;"><input
										class="numberspinner numberspinner-f spinner-text spinner-f validatebox-text numberbox-f"
										style="width: 36px; height: 20px; line-height: 20px;"
										data-options="min:1,max:23" value="1" id="hourEnd_1"><input
										type="hidden" value="1"><span class="spinner-arrow"
										style="height: 20px;"><span class="spinner-arrow-up"
											style="height: 10px;"></span><span class="spinner-arrow-down"
											style="height: 10px;"></span></span></span> 小时执行一次
								</div>
								<div class="line">
									<input type="radio" name="hour" id="hour_appoint"> 指定
								</div>
								<div class="imp hourList">
									AM: <input type="checkbox" value="0">00 <input
										type="checkbox" value="1">01 <input type="checkbox"
										value="2">02 <input type="checkbox" value="3">03
									<input type="checkbox" value="4">04 <input
										type="checkbox" value="5">05 <input type="checkbox"
										value="6">06 <input type="checkbox" value="7">07
									<input type="checkbox" value="8">08 <input
										type="checkbox" value="9">09 <input type="checkbox"
										value="10">10 <input type="checkbox" value="11">11
								</div>
								<div class="imp hourList">
									PM: <input type="checkbox" value="12">12 <input
										type="checkbox" value="13">13 <input type="checkbox"
										value="14">14 <input type="checkbox" value="15">15
									<input type="checkbox" value="16">16 <input
										type="checkbox" value="17">17 <input type="checkbox"
										value="18">18 <input type="checkbox" value="19">19
									<input type="checkbox" value="20">20 <input
										type="checkbox" value="21">21 <input type="checkbox"
										value="22">22 <input type="checkbox" value="23">23
								</div>
							</div>
						</div>
						<div class="panel" style="display: none;">
							<div title=""
								class="panel-body panel-body-noheader panel-body-noborder" id="">
								<div class="line">
									<input type="radio" checked="checked" name="day"
										onclick="everyTime(this)"> 日 允许的通配符[, - * / L W]
								</div>
								<div class="line">
									<input type="radio" name="day" onclick="unAppoint(this)">
									不指定
								</div>
								<div class="line">
									<input type="radio" name="day" onclick="cycle(this)">
									周期从 <span class="spinner" style="width: 58px; height: 20px;"><input
										class="numberspinner numberspinner-f spinner-text spinner-f validatebox-text numberbox-f"
										style="width: 36px; height: 20px; line-height: 20px;"
										data-options="min:1,max:31" value="1" id="dayStart_0"><input
										type="hidden" value="1"><span class="spinner-arrow"
										style="height: 20px;"><span class="spinner-arrow-up"
											style="height: 10px;"></span><span class="spinner-arrow-down"
											style="height: 10px;"></span></span></span> - <span class="spinner"
										style="width: 58px; height: 20px;"><input
										class="numberspinner numberspinner-f spinner-text spinner-f validatebox-text numberbox-f"
										style="width: 36px; height: 20px; line-height: 20px;"
										data-options="min:2,max:31" value="2" id="dayEnd_0"><input
										type="hidden" value="2"><span class="spinner-arrow"
										style="height: 20px;"><span class="spinner-arrow-up"
											style="height: 10px;"></span><span class="spinner-arrow-down"
											style="height: 10px;"></span></span></span> 日
								</div>
								<div class="line">
									<input type="radio" name="day" onclick="startOn(this)">
									从 <span class="spinner" style="width: 58px; height: 20px;"><input
										class="numberspinner numberspinner-f spinner-text spinner-f validatebox-text numberbox-f"
										style="width: 36px; height: 20px; line-height: 20px;"
										data-options="min:1,max:31" value="1" id="dayStart_1"><input
										type="hidden" value="1"><span class="spinner-arrow"
										style="height: 20px;"><span class="spinner-arrow-up"
											style="height: 10px;"></span><span class="spinner-arrow-down"
											style="height: 10px;"></span></span></span> 日开始,每 <span class="spinner"
										style="width: 58px; height: 20px;"><input
										class="numberspinner numberspinner-f spinner-text spinner-f validatebox-text numberbox-f"
										style="width: 36px; height: 20px; line-height: 20px;"
										data-options="min:1,max:31" value="1" id="dayEnd_1"><input
										type="hidden" value="1"><span class="spinner-arrow"
										style="height: 20px;"><span class="spinner-arrow-up"
											style="height: 10px;"></span><span class="spinner-arrow-down"
											style="height: 10px;"></span></span></span> 天执行一次
								</div>
								<div class="line">
									<input type="radio" name="day" onclick="workDay(this)">
									每月 <span class="spinner" style="width: 58px; height: 20px;"><input
										class="numberspinner numberspinner-f spinner-text spinner-f validatebox-text numberbox-f"
										style="width: 36px; height: 20px; line-height: 20px;"
										data-options="min:1,max:31" value="1" id="dayStart_2"><input
										type="hidden" value="1"><span class="spinner-arrow"
										style="height: 20px;"><span class="spinner-arrow-up"
											style="height: 10px;"></span><span class="spinner-arrow-down"
											style="height: 10px;"></span></span></span> 号最近的那个工作日
								</div>
								<div class="line">
									<input type="radio" name="day" onclick="lastDay(this)">
									本月最后一天
								</div>
								<div class="line">
									<input type="radio" name="day" id="day_appoint"> 指定
								</div>
								<div class="imp dayList">
									<input type="checkbox" value="1">1 <input
										type="checkbox" value="2">2 <input type="checkbox"
										value="3">3 <input type="checkbox" value="4">4
									<input type="checkbox" value="5">5 <input
										type="checkbox" value="6">6 <input type="checkbox"
										value="7">7 <input type="checkbox" value="8">8
									<input type="checkbox" value="9">9 <input
										type="checkbox" value="10">10 <input type="checkbox"
										value="11">11 <input type="checkbox" value="12">12
									<input type="checkbox" value="13">13 <input
										type="checkbox" value="14">14 <input type="checkbox"
										value="15">15 <input type="checkbox" value="16">16
								</div>
								<div class="imp dayList">
									<input type="checkbox" value="17">17 <input
										type="checkbox" value="18">18 <input type="checkbox"
										value="19">19 <input type="checkbox" value="20">20
									<input type="checkbox" value="21">21 <input
										type="checkbox" value="22">22 <input type="checkbox"
										value="23">23 <input type="checkbox" value="24">24
									<input type="checkbox" value="25">25 <input
										type="checkbox" value="26">26 <input type="checkbox"
										value="27">27 <input type="checkbox" value="28">28
									<input type="checkbox" value="29">29 <input
										type="checkbox" value="30">30 <input type="checkbox"
										value="31">31
								</div>
							</div>
						</div>
						<div class="panel" style="display: none;">
							<div title=""
								class="panel-body panel-body-noheader panel-body-noborder" id="">
								<div class="line">
									<input type="radio" checked="checked" name="mouth"
										onclick="everyTime(this)"> 月 允许的通配符[, - * /]
								</div>
								<div class="line">
									<input type="radio" name="mouth" onclick="unAppoint(this)">
									不指定
								</div>
								<div class="line">
									<input type="radio" name="mouth" onclick="cycle(this)">
									周期从 <span class="spinner" style="width: 58px; height: 20px;"><input
										class="numberspinner numberspinner-f spinner-text spinner-f validatebox-text numberbox-f"
										style="width: 36px; height: 20px; line-height: 20px;"
										data-options="min:1,max:12" value="1" id="mouthStart_0"><input
										type="hidden" value="1"><span class="spinner-arrow"
										style="height: 20px;"><span class="spinner-arrow-up"
											style="height: 10px;"></span><span class="spinner-arrow-down"
											style="height: 10px;"></span></span></span> - <span class="spinner"
										style="width: 58px; height: 20px;"><input
										class="numberspinner numberspinner-f spinner-text spinner-f validatebox-text numberbox-f"
										style="width: 36px; height: 20px; line-height: 20px;"
										data-options="min:2,max:12" value="2" id="mouthEnd_0"><input
										type="hidden" value="2"><span class="spinner-arrow"
										style="height: 20px;"><span class="spinner-arrow-up"
											style="height: 10px;"></span><span class="spinner-arrow-down"
											style="height: 10px;"></span></span></span> 月
								</div>
								<div class="line">
									<input type="radio" name="mouth" onclick="startOn(this)">
									从 <span class="spinner" style="width: 58px; height: 20px;"><input
										class="numberspinner numberspinner-f spinner-text spinner-f validatebox-text numberbox-f"
										style="width: 36px; height: 20px; line-height: 20px;"
										data-options="min:1,max:12" value="1" id="mouthStart_1"><input
										type="hidden" value="1"><span class="spinner-arrow"
										style="height: 20px;"><span class="spinner-arrow-up"
											style="height: 10px;"></span><span class="spinner-arrow-down"
											style="height: 10px;"></span></span></span> 日开始,每 <span class="spinner"
										style="width: 58px; height: 20px;"><input
										class="numberspinner numberspinner-f spinner-text spinner-f validatebox-text numberbox-f"
										style="width: 36px; height: 20px; line-height: 20px;"
										data-options="min:1,max:12" value="1" id="mouthEnd_1"><input
										type="hidden" value="1"><span class="spinner-arrow"
										style="height: 20px;"><span class="spinner-arrow-up"
											style="height: 10px;"></span><span class="spinner-arrow-down"
											style="height: 10px;"></span></span></span> 月执行一次
								</div>
								<div class="line">
									<input type="radio" name="mouth" id="mouth_appoint"> 指定
								</div>
								<div class="imp mouthList">
									<input type="checkbox" value="1">1 <input
										type="checkbox" value="2">2 <input type="checkbox"
										value="3">3 <input type="checkbox" value="4">4
									<input type="checkbox" value="5">5 <input
										type="checkbox" value="6">6 <input type="checkbox"
										value="7">7 <input type="checkbox" value="8">8
									<input type="checkbox" value="9">9 <input
										type="checkbox" value="10">10 <input type="checkbox"
										value="11">11 <input type="checkbox" value="12">12
								</div>
							</div>
						</div>
						<div class="panel" style="display: none;">
							<div title=""
								class="panel-body panel-body-noheader panel-body-noborder" id="">
								<div class="line">
									<input type="radio" checked="checked" name="week"
										onclick="everyTime(this)"> 周 允许的通配符[, - * / L #]
								</div>
								<div class="line">
									<input type="radio" name="week" onclick="unAppoint(this)">
									不指定
								</div>
								<div class="line">
									<input type="radio" name="week" onclick="startOn(this)">
									周期 从星期<span class="spinner" style="width: 58px; height: 20px;"><input
										class="numberspinner numberspinner-f spinner-text spinner-f validatebox-text numberbox-f"
										style="width: 36px; height: 20px; line-height: 20px;"
										data-options="min:1,max:7" id="weekStart_0" value="1"><input
										type="hidden" value="1"><span class="spinner-arrow"
										style="height: 20px;"><span class="spinner-arrow-up"
											style="height: 10px;"></span><span class="spinner-arrow-down"
											style="height: 10px;"></span></span></span> - <span class="spinner"
										style="width: 58px; height: 20px;"><input
										class="numberspinner numberspinner-f spinner-text spinner-f validatebox-text numberbox-f"
										style="width: 36px; height: 20px; line-height: 20px;"
										data-options="min:2,max:7" value="2" id="weekEnd_0"><input
										type="hidden" value="2"><span class="spinner-arrow"
										style="height: 20px;"><span class="spinner-arrow-up"
											style="height: 10px;"></span><span class="spinner-arrow-down"
											style="height: 10px;"></span></span></span>
								</div>
								<div class="line">
									<input type="radio" name="week" onclick="weekOfDay(this)">
									第<span class="spinner" style="width: 58px; height: 20px;"><input
										class="numberspinner numberspinner-f spinner-text spinner-f validatebox-text numberbox-f"
										style="width: 36px; height: 20px; line-height: 20px;"
										data-options="min:1,max:4" value="1" id="weekStart_1"><input
										type="hidden" value="1"><span class="spinner-arrow"
										style="height: 20px;"><span class="spinner-arrow-up"
											style="height: 10px;"></span><span class="spinner-arrow-down"
											style="height: 10px;"></span></span></span> 周 的星期<span class="spinner"
										style="width: 58px; height: 20px;"><input
										class="numberspinner numberspinner-f spinner-text spinner-f validatebox-text numberbox-f"
										style="width: 36px; height: 20px; line-height: 20px;"
										data-options="min:1,max:7" id="weekEnd_1" value="1"><input
										type="hidden" value="1"><span class="spinner-arrow"
										style="height: 20px;"><span class="spinner-arrow-up"
											style="height: 10px;"></span><span class="spinner-arrow-down"
											style="height: 10px;"></span></span></span>
								</div>
								<div class="line">
									<input type="radio" name="week" onclick="lastWeek(this)">
									本月最后一个星期<span class="spinner"
										style="width: 58px; height: 20px;"><input
										class="numberspinner numberspinner-f spinner-text spinner-f validatebox-text numberbox-f"
										style="width: 36px; height: 20px; line-height: 20px;"
										data-options="min:1,max:7" id="weekStart_2" value="1"><input
										type="hidden" value="1"><span class="spinner-arrow"
										style="height: 20px;"><span class="spinner-arrow-up"
											style="height: 10px;"></span><span class="spinner-arrow-down"
											style="height: 10px;"></span></span></span>
								</div>
								<div class="line">
									<input type="radio" name="week" id="week_appoint"> 指定
								</div>
								<div class="imp weekList">
									<input type="checkbox" value="1">1 <input
										type="checkbox" value="2">2 <input type="checkbox"
										value="3">3 <input type="checkbox" value="4">4
									<input type="checkbox" value="5">5 <input
										type="checkbox" value="6">6 <input type="checkbox"
										value="7">7
								</div>
							</div>
						</div>
						<div class="panel" style="display: none;">
							<div title=""
								class="panel-body panel-body-noheader panel-body-noborder" id="">
								<div class="line">
									<input type="radio" checked="checked" name="year"
										onclick="unAppoint(this)"> 不指定 允许的通配符[, - * /] 非必填
								</div>
								<div class="line">
									<input type="radio" name="year" onclick="everyTime(this)">
									每年
								</div>
								<div class="line">
									<input type="radio" name="year" onclick="cycle(this)">周期
									从 <span class="spinner" style="width: 88px; height: 20px;"><input
										class="numberspinner numberspinner-f spinner-text spinner-f validatebox-text numberbox-f"
										style="width: 66px; height: 20px; line-height: 20px;"
										data-options="min:2013,max:3000" id="yearStart_0" value="2013"><input
										type="hidden" value="2013"><span class="spinner-arrow"
										style="height: 20px;"><span class="spinner-arrow-up"
											style="height: 10px;"></span><span class="spinner-arrow-down"
											style="height: 10px;"></span></span></span> - <span class="spinner"
										style="width: 88px; height: 20px;"><input
										class="numberspinner numberspinner-f spinner-text spinner-f validatebox-text numberbox-f"
										style="width: 66px; height: 20px; line-height: 20px;"
										data-options="min:2014,max:3000" id="yearEnd_0" value="2014"><input
										type="hidden" value="2014"><span class="spinner-arrow"
										style="height: 20px;"><span class="spinner-arrow-up"
											style="height: 10px;"></span><span class="spinner-arrow-down"
											style="height: 10px;"></span></span></span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="panel layout-panel layout-panel-south"
				style="left: 0px; top: 310px; width: 830px;">
				<div data-options="region:'south',border:false"
					style="height: 250px; width: 830px;" title=""
					class="panel-body panel-body-noheader panel-body-noborder layout-body">
					<fieldset style="border-radius: 3px; height: 220px;">
						<legend>表达式</legend>
						<table style="height: 100px;">
							<tbody>
								<tr>
									<td></td>
									<td align="center">秒</td>
									<td align="center">分钟</td>
									<td align="center">小时</td>
									<td align="center">日</td>
									<td align="center">月<br>
									</td>
									<td align="center">星期</td>
									<td align="center">年</td>
								</tr>
								<tr>
									<td>表达式字段:</td>
									<td><input type="text" name="v_second" class="col"
										value="*" readonly="readonly"></td>
									<td><input type="text" name="v_min" class="col" value="*"
										readonly="readonly"></td>
									<td><input type="text" name="v_hour" class="col" value="*"
										readonly="readonly"></td>
									<td><input type="text" name="v_day" class="col" value="*"
										readonly="readonly"></td>
									<td><input type="text" name="v_mouth" class="col"
										value="*" readonly="readonly"></td>
									<td><input type="text" name="v_week" class="col" value="?"
										readonly="readonly"></td>
									<td><input type="text" name="v_year" class="col"
										readonly="readonly"></td>
								</tr>
								<tr>
									<td>Cron 表达式:</td>
									<td colspan="6"><input type="text" name="cron"
										style="width: 100%;" value="* * * * * ?" id="cron"></td>
									<td><input type="button" value="反解析到UI " id="btnFan"
										onclick="btnFan()"></td>
								</tr>
								<tr>
									<td colspan="8">最近5次运行时间:</td>
								</tr>
								<tr>
									<td colspan="8" id="runTime"></td>
								</tr>
							</tbody>
						</table>
					</fieldset>
					<div style="text-align: center; margin-top: 5px;">
						<script type="text/javascript">
							
						</script>
						<div></div>
					</div>
				</div>
			</div>
			<div></div>
			<div class="layout-split-proxy-h"></div>
			<div class="layout-split-proxy-v"></div>
		</div>
	</center>
	<!-- 	<script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js" -->
	<!-- 		type="text/javascript"></script> -->
	<script type="text/javascript">
		/*960*90 创建于 2015-08-27*/
		var cpro_id = "u2283154";
	</script>
	<script src="${appPath}/crontab/jquery.easyui.min.js"></script>
	<script src="${appPath}/crontab/crontab.js"></script>
</body>
</html>
