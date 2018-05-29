<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>测试TreeView</title>
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">

<jsp:include page="/WEB-INF/common/css_common.jsp"></jsp:include>
<jsp:include page="/WEB-INF/common/js_common.jsp" />

<link rel="stylesheet" href="${appPath}/css/zTreeStyle/zTreeStyle.css"
	type="text/css">

</head>
<body class="hold-transition skin-blue sidebar-mini">

	<div style="color: red">请看下面</div>
	<ul id="tree" class="ztree" style="width: 260px; overflow: auto;"></ul>
	<input value="" onchange="inputChange(this)">

	<script type="text/javascript" src="${appPath}/js/jquery.ztree.all.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$.fn.zTree.init($("#tree"), setting, zNodes);
		});

		
		var setting = {
				data: {
					key: {
						name:"name",
						isParent:"isParent",
						children:"children",
						title : "name"
					},
					simpleData: {
						enable: false
					}
				},
				edit: {
					enable: true,
					showRemoveBtn: true,
					showRenameBtn: true,
					removeTitle: "删除",
					renameTitle: "编辑",
				},
				view: {
					addHoverDom: addHoverDom,
					removeHoverDom: removeHoverDom,
					selectedMulti: false
				},
				callback: {
					onClick: onClick,
					beforeEditName: beforeEditName,
					beforeRemove: beforeRemove,
					//beforeRename: beforeRename,
					//onRemove: onRemove,
					//onRename: onRename
				}
			};
		
		
		function onClick(event, treeId, treeNode, clickFlag){
			console.log(treeNode);
		}
		
		function addHoverDom(treeId, treeNode) {
			var sObj = $("#" + treeNode.tId + "_span");
			if ($("#addBtn_"+treeNode.tId).length>0) return;
			var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
				+ "' title='add node' onfocus='this.blur();'></span>";
			sObj.after(addStr);
			var btn = $("#addBtn_"+treeNode.tId);
			if (btn) btn.bind("click", function(){
				var zTree = $.fn.zTree.getZTreeObj("tree");
				zTree.cancelSelectedNode();//先取消所有的选中状态
				var newNode = {id:"xxx", name:"newNode1"};
				newNode = zTree.addNodes(treeNode, newNode);
				zTree.selectNode(newNode,true);//将指定ID的节点选中
				return true;
			});
			if(treeNode.level == 0){
				if ($("#addRootBtn_"+treeNode.tId).length>0) return;
				var addStr = "<span class='button addroot' id='addRootBtn_" + treeNode.tId
					+ "' title='add root node' onfocus='this.blur();'></span>";
				sObj.after(addStr);
				var btn2 = $("#addRootBtn_"+treeNode.tId);
				if (btn2) btn2.bind("click", function(){
					var zTree = $.fn.zTree.getZTreeObj("tree");
					zTree.cancelSelectedNode();//先取消所有的选中状态
					var newNode = {id:"xxx", name:"newNode1"};
					newNode = zTree.addNodes(null, newNode);
					zTree.selectNode(newNode,true);//将指定ID的节点选中
					return true;
				});
			}
		};
		function removeHoverDom(treeId, treeNode) {
			$("#addBtn_"+treeNode.tId).unbind().remove();
			$("#addRootBtn_"+treeNode.tId).unbind().remove();
		};
		
		function beforeEditName(treeId, treeNode) {
			debugger
			treeNode.name = "我变了";
			$('#'+treeNode.tId+"_span").text("我变了");
			return false;
		}
		function beforeRemove(treeId, treeNode) {
			if (confirm("确认删除节点 -- " + treeNode.name + " 吗？")) {
					alert("delete");
			}
			return false;
		}
		
		
		function inputChange(dom){
			debugger
			var zTree = $.fn.zTree.getZTreeObj("tree");
			var node = zTree.getNodeByParam("id", dom.value);
			zTree.cancelSelectedNode();//先取消所有的选中状态
			zTree.selectNode(node,true);//将指定ID的节点选中
			zTree.expandNode(node, true, false);//将指定ID节点展开
			
			//增
			var newNode = {id:"xxx", name:"newNode1"};
			newNode = zTree.addNodes(node, newNode);
			
			//改
			node.name = "就是你";
			zTree.editName(node);
			zTree.selectNode(node,true);//将指定ID的节点选中
// 			node.name = "我变了";
// 			$('#'+node.tId+"_span").text("我变了");
			
			return
			//删
			zTree.removeNode(newNode);
		}
		
		var zNodes =[
			{ id:"1", name:"父节点1 - 展开", open:true,
				children: [
					{  id:"11",name:"父节点11 - 折叠",
						children: [
							{  id:"111",name:"叶子节点111"},
							{  id:"112",name:"叶子节点112"},
							{  id:"113",name:"叶子节点113"},
							{  id:"114",name:"叶子节点114"}
						]},
					{id:"12", name:"父节点12 - 折叠",
						children: [
							{ id:"121",name:"叶子节点121"},
							{ id:"122",name:"叶子节点122"},
							{ id:"123",name:"叶子节点123"},
							{ id:"124",name:"叶子节点124"}
						]},
					{id:"13", name:"父节点13 - 没有子节点", isParent:true}
				]},

		];


	
	</script>
</body>
</html>