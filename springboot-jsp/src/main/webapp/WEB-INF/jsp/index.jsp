<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html >
<head>
	<title>learn Resources</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>

<div style="text-align: center;margin:0 auto;width: 1000px; ">
	<h1>学习资源大奉送，爱我就关注嘟嘟公众号：嘟爷java超神学堂（javaLearn）</h1>
	<table width="100%" border="1" cellspacing="1" cellpadding="0">
		<tr>
			<td>作者</td>
			<td>教程名称</td>
			<td>地址</td>
		</tr>
		<c:forEach var="learn" items="${learnList}">
			<tr class="text-info">
				<td>${learn.author}</td>
				<td>${learn.title}</td>
				<td><a href="${learn.url}" class="btn btn-search btn-green" target="_blank"><span>点我</span></a>
				</td>
			</tr>
		</c:forEach>
	</table>
</div>
</body>
</html>