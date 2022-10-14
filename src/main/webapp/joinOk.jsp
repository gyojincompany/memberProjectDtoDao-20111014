<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.gyojincompany.member.MemberDao" %>
<%@ page import="com.gyojincompany.member.MemberDto" %>
<% request.setCharacterEncoding("utf-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>회원 가입 성공 여부</h3>
	<hr>
	<%	
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		
		MemberDto dto = new MemberDto();
		dto.setId(id);
		dto.setPw(pw);
		dto.setUsername(name);
		dto.setEmail(email);		
		
		MemberDao dao = new MemberDao();
		int joinCheck = dao.insertMember(dto);
		// int joinCheck = dao.insertMember(id, pw, name, email);
		
		
		if(joinCheck == 1) {
			out.println("회원 가입 성공! 가입을 축하드립니다.");
		} else {
			out.println("회원 가입 실패! 가입사항을 다시 확인해주세요.");
		}
	%>
</body>
</html>