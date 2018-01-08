<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/tlds/base.tld" prefix="cn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="content-wrapper">
    <!-- 头部开始 -->
    <section class="content-header">
        <h1>
            实时数据
            <small id="nowDateTimeSpan">${onlineInfo.currentDate}</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-dashboard"></i> 首页</a></li>
            <!-- <li class="active">Dashboard</li> -->
        </ol>
    </section>
    <!--头部结束  -->

    <!-- 列表开始 -->
    <section class="content">
        <!-- Info boxes -->
        <div class="row">
            <div class="col-md-3 col-sm-6 col-xs-12">
                <div class="info-box" style="height:250px">
                    <span class="info-box-icon bg-aqua"><i class="fa fa-child"></i></span>

                    <div class="info-box-content clearfix">
                        <h3 style="padding-left:20px">机 器 人</h3>
                    </div>

                    <div class="description-block">
                        <span class="description-text">在线</span>
                        <span class="description-header" style="margin-left:20px;font-size:20px">${onlineInfo.robotOnLineNum}</span>

                    </div>
                    <div class="description-block">
                        <span class="description-text">总数</span>
                        <span class="description-header" style="margin-left:20px;font-size:20px">${onlineInfo.robotRunNum}</span>
                    </div>

                    <div>

                    </div>
                    <!-- /.info-box-content -->
                </div>
                <!-- /.info-box -->
            </div>
            <!-- /.col -->
            <div class="col-md-3 col-sm-6 col-xs-12">
                <div class="info-box" style="height:250px">
                    <span class="info-box-icon bg-red"><i class="fa fa-book"></i></span>

                    <div class="info-box-content clearfix">
                        <h3 style="padding-left:20px">知 识 库</h3>
                    </div>
                    <div class="description-block">
                        <span class="description-text">业务访问</span>
                        <span class="description-header" style="margin-left:20px;font-size:20px">${onlineInfo.flowVisitNum}<small>次</small></span>
                    </div>
                    <div class="description-block">
                        <span class="description-text">技能访问</span>
                        <span class="description-header" style="margin-left:20px;font-size:20px">${onlineInfo.skillVisitNum}<small>次</small></span>
                    </div>
                    <div class="description-block">
                        <span class="description-text">问答访问</span>
                        <span class="description-header" style="margin-left:20px;font-size:20px">${onlineInfo.questionVisitNum}<small>次</small></span>
                    </div>
                    <div class="description-block">
                        <span class="description-text">其他访问</span>
                        <span class="description-header" style="margin-left:20px;font-size:20px">${onlineInfo.thirdVisitNum}<small>次</small></span>
                    </div>

                    <!-- /.info-box-content -->
                </div>
                <!-- /.info-box -->
            </div>
            <!-- /.col -->

            <!-- fix for small devices only -->
            <div class="clearfix visible-sm-block"></div>

            <div class="col-md-3 col-sm-6 col-xs-12">
                <div class="info-box" style="height:250px">
                    <span class="info-box-icon bg-green"><i class="fa fa-street-view"></i></span>

                    <div class="info-box-content clearfix">
                        <h3 style="padding-left:20px">到 访 人 数</h3>
                    </div>

                    <%--<div class="description-block">--%>
                        <%--<span class="description-text">当前到访</span>--%>
                        <%--<span class="description-header" style="margin-left:20px;font-size:20px">${onlineInfo.robotOnLineNum}<small>人</small></span>--%>
                    <%--</div>--%>
                    <div class="description-block">
                        <span class="description-text">今日到访</span>
                        <span class="description-header" style="margin-left:20px;font-size:20px">${onlineInfo.robotVisitNum}<small>人</small></span>
                    </div>
                    <!-- /.info-box-content -->

                </div>
                <!-- /.info-box -->
            </div>
            <!-- /.col -->
            <div class="col-md-3 col-sm-6 col-xs-12">
                <div class="info-box" style="height:250px">
                    <span class="info-box-icon bg-yellow"><i class="fa fa-user-plus"></i></span>

                    <div class="info-box-content clearfix">
                        <h3 style="padding-left:20px">人 工 服 务</h3>
                    </div>

                    <div class="description-block">
                        <span class="description-text">在线</span>
                        <span class="description-header" style="margin-left:20px;font-size:20px">${onlineInfo.robotOnLineNum}<small>人</small></span>
                    </div>
                    <div class="description-block">
                        <span class="description-text">回答</span>
                        <span class="description-header" style="margin-left:20px;font-size:20px">${onlineInfo.artificialVisitNum}<small>人</small></span>
                    </div>

                </div>
                <!-- /.info-box -->
            </div>
            <!-- /.col -->
        </div>
        <!-- /.row -->
    </section>
    <!-- 列表结束 -->
</div>
<script type="text/javascript">
    window.clearInterval(autoFushTimer);
    var autoFushTimer;  // 定时器
    function autoRefresh() {
        startTime();
        autoFushTimer = window.setInterval(
            function () {
                if($("#nowDateTimeSpan").length >0 ){
                    indexInit();
                }
            }, 30000);
    };
    autoRefresh();

    function startTime() {
        var today = new Date();
        var hh = today.getHours();
        var mm = today.getMinutes();
        var ss = today.getSeconds();
        mm = checkTime(mm);
        ss = checkTime(ss);
        $("#nowDateTimeSpan").html(hh + ":" + mm + ":" + ss + "   ");
        setTimeout('startTime()', 1000);
    }

    function checkTime(i) {
        if (i < 10) {
            i = "0" + i;
        }
        return i;
    }
</script>
