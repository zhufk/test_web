<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>index</title>
</head>
<body>
	<div style="border: 1px red solid; padding: 10px">
		<form action="${pageContext.request.contextPath}/file/addUser2"
			method="post" enctype="multipart/form-data">

			姓名：<input type="text" name="name" /> </br> 年级：<input type="text"
				name="age" /> </br> 文件 : <input type="file" name="file" multiple /> </br> <input
				type="submit" value="上传" />
		</form>
	</div>

	<div style="border: 1px red solid; padding: 10px">
		<form action="${pageContext.request.contextPath}/file/upload_file"
			method="post" enctype="multipart/form-data">
			
			文件 : <input type="file" name="file" multiple /> </br> <input
				type="submit" value="上传" />
		</form>
	</div>

</body>
</html>