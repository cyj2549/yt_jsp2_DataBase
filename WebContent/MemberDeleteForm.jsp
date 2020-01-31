<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>삭제</title>
</head>

<body>
	<h2>회원삭제하기</h2>

	<form action="MemberDeleteProc.jsp" method="post">
		<table width="400" border="1">
			<tr height="50">
				<td align="center" width="150">아이디</td>
				<td align="center"><%=request.getParameter("id")%></td>
			</tr>
			
			<tr height="50">
			<td align="center" width="150">패스워드</td>
			<td width="250"><input type="password" name="pass1"></td>
			</tr>
			
			<tr height="50">
			<td align="center" colsapn="2">
			<input type="hidden" name="id" value="<%=request.getParameter("id")%>">
			<input type="submit" value="회원삭제하기">&nbsp;&nbsp;	</form>
			<button onclick ="location.href='MemberList.jsp'">회원전체보기</button>
			</td>
			</tr>

		</table>




</body>
</html>