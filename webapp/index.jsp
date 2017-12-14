<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>INDEX</title>
<script type="text/javascript" src="static/public/js/jquery.min.js"></script>
<script type="text/javascript">
	$(function() {
		//init();
	});

	function init() {
		var url = "http://127.0.0.1:8080";
		$.ajax({
			type : 'get',
			url : url,
			success : function(data) {
				alert(data);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				// 				alert(XMLHttpRequest.status);
				// 				alert( XMLHttpRequest.readyState);
				alert(textStatus);
			}
		});
	}
</script>

</head>
<body>
	<div>
		<h1>完整demo</h1>
	</div>

</body>
</html>