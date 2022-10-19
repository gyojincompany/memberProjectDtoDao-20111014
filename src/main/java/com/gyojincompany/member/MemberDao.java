package com.gyojincompany.member;

import java.sql.*;

public class MemberDao {
	
	static String driverName = "com.mysql.jdbc.Driver";
	static String url = "jdbc:mysql://localhost:3306/serverdb";
	static String user = "root";
	static String pass = "12345";
	
	public int insertMember(MemberDto dto) {
		
		String id = dto.getId();
		String pw = dto.getPw();
		String name = dto.getUsername();
		String email = dto.getEmail();
		
		String sql = "INSERT INTO members(id, pw, username, email) VALUES('"+id+"','"+pw+"','"+name+"','"+email+"')";
		
		Connection conn = null;
		Statement stmt = null;
		int dbFlag = 0;
		
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, user, pass);
			
			stmt = conn.createStatement();
			dbFlag = stmt.executeUpdate(sql); // 성공하면 1이 반환
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(stmt != null) {				
					stmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return dbFlag;//성공여부 값 반환(1이면 성공)
	}
	
	public int idCheck(String id) { //아이디의 중복 가입 여부(같은 아이디가 이미 존재하는지 여부 확인)
		
		int idFlag = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql="SELECT * FROM members WHERE id=?";
		
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, user, pass);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				idFlag= 1;//이미 가입하려는 아이디가 존재함
			} else {
				idFlag = 0;//가입하려는 아이디와 같은 아이디가 없으므로 가입 가능 
			}
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return idFlag;// 가입하려는 아이디가 이미 존재하면 1이 반환, 아니면 0이 반환
	}
	
	public int userCheck(String id, String pw) {
		
		int userFlag = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql="SELECT pw FROM members WHERE id=?";
		
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, user, pass);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();		
			
			
			if(rs.next()) {
				String dbPw = rs.getString("pw");
				if(dbPw.equals(pw)) {
					userFlag= 1;//아이디와 비번이 모두 일치하므로 로그인 성공
				} else {
					userFlag= 2;//아이디는 존재하지만 비번이 틀림
				}
				
			} else {
				userFlag = 0;//아이디가 존재하지 않으므로 비회원임 
			}
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return userFlag;
		//1이 반환되면 로그인 성공, 0이 반환되면 회원아님(id존재하지않음), 2가 반환되면 비밀번호만 틀림
	}
	
	public MemberDto getMemberInfo(String id) { //아이디로 테이블을 검색하여 해당 아이디의 모든 정보를 반환		
		
		MemberDto dto = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql="SELECT * FROM members WHERE id=?";
		
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, user, pass);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				dto = new MemberDto();
				
				String dbID = rs.getString("id");
				String dbPw = rs.getString("pw");
				String dbUsername = rs.getString("username");
				String dbEmail = rs.getString("email");
				String dbSignuptime = rs.getString("signuptime");
				
				dto.setId(dbID);
				dto.setPw(dbPw);
				dto.setUsername(dbUsername);
				dto.setEmail(dbEmail);
				dto.setSignuptime(dbSignuptime);
				
			} 
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return dto;
		
	}
	
	public int modifyMemberInfo(MemberDto dto) {//modify.jsp에서 보내준 수정된 회원정보를 DB에 다시 넣기(update)
		int updateFlag = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "UPDATE members SET pw=?, username=?, email=? WHERE id=?";
		
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, user, pass);
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getPw());
			pstmt.setString(2, dto.getUsername());
			pstmt.setString(3, dto.getEmail());
			pstmt.setString(4, dto.getId());
			
			updateFlag  = pstmt.executeUpdate();//수정 성공이면 1이 반환, 아니면 다른 값 반환
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) {				
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return updateFlag;//1이 반환되면 정보수정 성공, 아니면 정보수정 실패		
	}
	
	public int delete(String id) {
		int deleteFlag = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "DELETE FROM members WHERE id=?";
		
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, user, pass);
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,id);
			
			
			deleteFlag  = pstmt.executeUpdate();//수정 성공이면 1이 반환, 아니면 다른 값 반환
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) {				
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		return deleteFlag;// 회원탈퇴 성공하면 1 아니면 0을 반환
	}
	
	
}
