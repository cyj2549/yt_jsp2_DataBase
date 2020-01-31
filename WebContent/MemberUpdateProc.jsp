<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="model.MemberDAO"%>
<html>
<head>
<title>회원정보수정을 가져오는곳</title>
</head>

<body>
	<%
		request.setCharacterEncoding("utf-8");
	%>

	<jsp:useBean id="mbean" class="model.MemberBean">
		<jsp:setProperty name="mbean" property="*"/></jsp:useBean>

	<%
		String id = request.getParameter("id");

		MemberDAO mdao = new MemberDAO();

		String pass = mdao.getPass(id);
		//1.스트링타입으로 저장되어 있는 패스워드를 가져옴(데이터베이스에서 가져온 pass 값이 저장)

		//2.수정하기 위해서 작성한 패스워드값과 기존 데이터베이스에서 가져온 패스워드값을 비교하는 문장 작성
		//스트링타입이니까 equals를씀.
		if (mbean.getPass1().equals(pass)) { //기존 패스워드와 데이터베이스 패스워드가 같다면 member테이블을 수정

			//MemberDAO클래스의 회원수정 메소드를 호출
			mdao.updateMember(mbean);
			response.sendRedirect("MemberList.jsp");
			
			
		} else {
	%>
	<script type="text/javascript">
	alert("패스워드가 맞지 않습니다. 다시 확인해 주세요");
	history.go(-1);
	</script>


	<%
		}
	%>

</body>
</html>