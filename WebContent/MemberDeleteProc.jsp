<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.MemberDAO"%>
<html>
<head>
<title></title>
</head>

<body>
	<%
		request.setCharacterEncoding("utf-8");
	%>
	<jsp:useBean id="mbean" class="model.MemberBean">
		<jsp:setProperty name="mbean" property="*" /></jsp:useBean>

	<%
		MemberDAO mdao = new MemberDAO();

		String pass = mdao.getPass(mbean.getId());
		// 스트링타입으로 저장되어 있는 패스워드를 가져옴.(데이터베이스에서 가져온 pass값이 저장)
			
		if (mbean.getPass1().equals(pass)) {
			
			//MemberDAO클래스이 회원삭제 메소드를 호출
			mdao.deleteMember(mbean.getId());
			response.sendRedirect("MemberList.jsp");
		} else {
	%>
	

	<script type="text/javascript">
	alert("패스워드가 일치하지 않습니다. 다시 확인해 주세요");
	history.go(-1);
	</script>

	<%
		}
	%>

</body>
</html>