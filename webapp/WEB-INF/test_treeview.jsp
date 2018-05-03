<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>测试TreeView</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    
    <jsp:include page="/WEB-INF/common/css_common.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/common/js_common.jsp"/>
</head>
<body class="hold-transition skin-blue sidebar-mini">

<div style="color:red">请看下面</div>
<div id="tree"></div>

<script type="text/javascript">
// 	(function(){
// 		$('#tree').treeview({data: getTreeData()});   
// 	})()

	$('#tree').treeview({
		data: getTreeData(),
		backColor:'lightblue',
		levels : 1,
		showCheckbox:true,
		multiSelect: false,
		onNodeSelected: function(event, data) {
		    debugger
			alert(JSON.stringify(data));
		}              
	});   
	/*
	可用事件列表
	nodeChecked (event, node)：一个节点被checked。
	nodeCollapsed (event, node)：一个节点被折叠。
	nodeDisabled (event, node)：一个节点被禁用。
	nodeEnabled (event, node)：一个节点被启用。
	nodeExpanded (event, node)：一个节点被展开。
	nodeSelected (event, node)：一个节点被选择。
	nodeUnchecked (event, node)：一个节点被unchecked。
	nodeUnselected (event, node)：取消选择一个节点。
	searchComplete (event, results)：搜索完成之后触发。
	searchCleared (event, results)：搜索结果被清除之后触发。
	*/
	
	function getTreeData() {
		var data = [
		            {
		              id:"1",
		              text:"Parent 1",
		              icon: "glyphicon glyphicon-stop",
		              //selectedIcon: "glyphicon glyphicon-stop",
		              nodes: [
		                {
		                	id:"1",
		                  text:"Child 1",
		                  nodes: [
		                    {
		                    	id:"1",
		                      text:"Grandchild 1"
		                    },
		                    {
		                    	id:"1",
		                      text:"Grandchild 2"
		                    }
		                  ]
		                },
		                {
		                  text:"Child 2"
		                }
		              ]
		            },
		            {
		              text:"Parent 2"
		            },
		            {
		              text:"Parent 3"
		            },
		            {
		              text:"Parent 4"
		            },
		            {
		              text:"Parent 5"
		            }
		          ];
	    return data;
	}
</script>	
</body>
</html>