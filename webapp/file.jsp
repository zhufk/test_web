<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>index</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/fileC/addUser" method="post"
		enctype="multipart/form-data">

		姓名：<input type="text" name="name" /> </br> 
		年级：<input type="text" name="age" /> </br> 
		文件 : <input type="file" name="file" multiple/> </br> 
			<input type="submit" value="上传" />

	</form>
</body>
</html>