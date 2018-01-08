<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>和美（深圳）智能机器人知识库系统-登录</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    
    <jsp:include page="/WEB-INF/common/css_common.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/common/js_common.jsp"/>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <header class="main-header">
        <!-- Logo -->
        <a href="javascript:go('index');" class="logo">
            <!-- mini logo for sidebar mini 50x50 pixels -->
            <span class="logo-mini"><b>和</b>美</span>
            <!-- logo for regular state and mobile devices -->
            <span class="logo-lg"><b>和美信息</b>WORKWAY</span>
        </a>
        <!-- Header Navbar: style can be found in header.less -->
        <nav class="navbar navbar-static-top">
            <!-- Sidebar toggle button-->
            <a href="#" onclick="hmResize();" class="sidebar-toggle" data-toggle="push-menu" role="button">
                <span class="sr-only">Toggle navigation</span>
            </a>

            <div class="navbar-custom-menu">
                <ul class="nav navbar-nav">
                    <!-- Messages: style can be found in dropdown.less-->
                    <li class="dropdown messages-menu">
                        <!-- Notifications: style can be found in dropdown.less -->
                    <li class="dropdown notifications-menu">
                        <a href="javascript:msgStomp();" class="dropdown-toggle" data-toggle="dropdown">
                            <i class="fa fa-bell-o"></i>
                            <span class="label label-warning" id="msgTip"></span>
                        </a>
                        <%--<ul class="dropdown-menu">--%>
                        <%--<li class="header">You have 10 notifications</li>--%>
                        <%--<li>--%>
                        <%--<!-- inner menu: contains the actual data -->--%>
                        <%--<ul class="menu">--%>
                        <%--<li>--%>
                        <%--<a href="#">--%>
                        <%--<i class="fa fa-users text-aqua"></i> 5 new members joined today--%>
                        <%--</a>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                        <%--<a href="#">--%>
                        <%--<i class="fa fa-warning text-yellow"></i> Very long description here that--%>
                        <%--may not fit into the--%>
                        <%--page and may cause design problems--%>
                        <%--</a>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                        <%--<a href="#">--%>
                        <%--<i class="fa fa-users text-red"></i> 5 new members joined--%>
                        <%--</a>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                        <%--<a href="#">--%>
                        <%--<i class="fa fa-shopping-cart text-green"></i> 25 sales made--%>
                        <%--</a>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                        <%--<a href="#">--%>
                        <%--<i class="fa fa-user text-red"></i> You changed your username--%>
                        <%--</a>--%>
                        <%--</li>--%>
                        <%--</ul>--%>
                        <%--</li>--%>
                        <%--<li class="footer"><a href="#">View all</a></li>--%>
                        <%--</ul>--%>
                    </li>
                    </li>

                    <!-- User Account: style can be found in dropdown.less -->
                    <li class="dropdown user user-menu">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <img src="${appPath}/img/user2-160x160.jpg" class="user-image" alt="User Image">
                            <span class="hidden-xs">${user.loginName}</span>
                        </a>
                        <ul class="dropdown-menu">
                            <!-- User image -->
                            <li class="user-header">
                                <img src="${appPath}/img/user2-160x160.jpg" class="img-circle" alt="User Image">
                                <p>
                                    <small>最后登录时间：<fmt:formatDate value="${user.loginTime}"
                                                                  pattern="yyyy-MM-dd HH:mm"/></small>
                                </p>
                            </li>
                            <!-- Menu Footer-->
                            <li class="user-footer">
                                <div class="pull-left">
                                    <a onclick="javascript:goSync('user/edit_pwd_view')" class="btn btn-default btn-flat">密码修改</a>
                                </div>
                                <div class="pull-right">
                                    <a href="javascript:logout();" class="btn btn-default btn-flat">退&nbsp;&nbsp;出</a>
                                </div>
                            </li>
                        </ul>
                    </li>

                </ul>
            </div>
        </nav>
    </header>
    <!-- Left side column. contains the logo and sidebar -->
    <aside class="main-sidebar">
        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">
            <!-- Sidebar user panel -->
            <div class="user-panel">
                <div class="pull-left image">
                    <img src="${appPath}/img/user2-160x160.jpg" class="img-circle" alt="User Image">
                </div>
                <div class="pull-left info">
                    <p>${user.loginName}</p>
                    <a href="#"><i class="fa fa-circle text-success"></i> 在线</a>
                </div>
            </div>

			<!-- sidebar menu: : style can be found in sidebar.less -->
            <ul class="sidebar-menu" data-widget="tree">
                <%--<li class="header">建行小龙机器人</li>--%>
                <li class="active treeview">
                    <a href="javaScript:;">
                        <i class="fa fa-gears"></i>
                        <span>测试</span>
                        <span class="pull-right-container">
                            <i class="fa fa-angle-left pull-right"></i>
                        </span>
                    </a>
                    <ul class="treeview-menu">
                        <li class="arrow">
                            <a href="chat/chat" class="ajaxify">
                                <i class="fa fa-circle-o"></i> 聊天
                            </a>
                        </li>
                        <li class="arrow">
                            <a href="page/mq" class="ajaxify">
                                <i class="fa fa-circle-o"></i> 聊天
                            </a>
                        </li>
                    </ul>

                </li>

                <c:set var="first" value="true"/>
                <c:forEach items="${menu}" var="item">
                    <li class="<c:if test='${first}'>active</c:if> treeview">
                        <a href="javaScript:;">
                            <i class="fa fa-comments-o ${item.icon}"></i>
                            <span>${item.caption}</span>
                            <span class="pull-right-container">
                                <i class="fa fa-angle-left pull-right"></i>
                            </span>
                        </a>
                        <ul class="treeview-menu">
                            <c:forEach items="${item.child}" var="child">
                                <li class="arrow">
                                    <a href="${child.url}" class="ajaxify">
                                        <i class="fa fa-circle-o"></i> ${child.caption}
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>

                    </li>
                    <c:set var="first" value="false"/>
                </c:forEach>

            </ul>
            
        </section>
        <!-- /.sidebar -->
    </aside>
    <!-- BEGIN CONTENT -->
    <div class="page-content-wrapper">
        <!-- Content Wrapper. Contains page content -->
        <div class="content-wrapper">

        </div>
        <!-- /.content-wrapper -->
    </div>
    <!-- END CONTENT -->

    <footer class="main-footer">
        <div class="pull-right hidden-xs">
            <b>Version</b> 2.4.0
        </div>
        <strong>Copyright &copy; 2017 <a href="#">和美（深圳）智能机器人知识库系统</a>.</strong> All rights
        reserved.
    </footer>
</div>
<!-- ./wrapper -->


<script type="text/javascript">
    Layout.init(); // init layout
    initHome();
    
    function initHome() {
        goSync("page/home");
    };
    
    //掉其他页面的hm_resize()方法
//     function hmResize() {
//         try {
//             if (typeof (hm_resize) == "function") {
//                 setTimeout(hm_resize,300);
//             }
//         } catch (e) {
//         }
//     }

</script>
</body>
</html>