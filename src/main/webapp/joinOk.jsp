<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.gyojincompany.member.MemberDao" %>
<%@ page import="com.gyojincompany.member.MemberDto" %>
<jsp:useBean id="dto" class="com.gyojincompany.member.MemberDto"/>
<jsp:setProperty property="*" name="dto"/>
<% request.setCharacterEncoding("utf-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입 확인</title>
</head>
<body>
	<h3>회원 가입 성공 여부</h3>
	<hr>
	<%	
		//String id = request.getParameter("id");
		//String pw = request.getParameter("pw");
		//String name = request.getParameter("name");
		//String email = request.getParameter("email");
		
		//MemberDto dto = new MemberDto();
		//dto.setId(id);
		//dto.setPw(pw);
		//dto.setUsername(name);
		//dto.setEmail(email);		
		
		MemberDao dao = new MemberDao();
		
		int idResult = dao.idCheck(dto.getId());
		
		if(idResult == 1) {
			out.println("이미 가입된 아이디 입니다. 다른 아이디를 입력해주세요.");
		} else {
			int joinCheck = dao.insertMember(dto);
		// int joinCheck = dao.insertMember(id, pw, name, email);
			if(joinCheck == 1) {
			out.println("회원 가입 성공! 가입을 축하드립니다.");
			} else {
			out.println("회원 가입 실패! 가입사항을 다시 확인해주세요.");
			}
		}
	%>
</body>
</html>