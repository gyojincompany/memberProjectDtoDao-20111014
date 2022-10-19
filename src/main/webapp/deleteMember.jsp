<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.gyojincompany.member.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 탈퇴</title>
</head>
<body>
	<%
		MemberDao dao = new MemberDao();
	
		String id = (String) session.getAttribute("memberId");
	
		int deleteFlag = dao.delete(id);
		
		if(deleteFlag == 1) {
			session.invalidate();//모든 세션 삭제		
			out.println("회원 탈퇴 성공! 그동안 감사했습니다. 안녕히 가세요.");
		} else {
			out.println("회원 탈퇴 실패! 다시 확인해 주세요.");
		}
	
	%>
	<a href="main.jsp">메인 화면으로 가기</a>
</body>
</html>