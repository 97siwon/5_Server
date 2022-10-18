package edu.kh.project.member.model.dao;

import static edu.kh.project.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import edu.kh.project.main.model.dao.MainDAO;
import edu.kh.project.member.model.vo.Member;

public class MemberDAO {

	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Properties prop;
	
	public MemberDAO() {
		try {
			prop = new Properties();
            
            String filePath 
            	= MemberDAO.class.getResource("/edu/kh/project/sql/member-sql.xml").getPath();
            
            prop.loadFromXML( new FileInputStream(filePath) );
				
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 로그인 DAO
	 * @param conn
	 * @param member
	 * @return loginMember
	 * @throws Exception
	 */
	public Member login(Connection conn, Member member) throws Exception {
		Member loginMember = null;
		
		try {
			String sql = prop.getProperty("login");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,  member.getMemberEmail());
			pstmt.setString(2, member.getMemberPw());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				loginMember = new Member();
				
				loginMember.setMemberNo(rs.getInt("MEMBER_NO"));
				loginMember.setMemberEmail(rs.getString("MEMBER_EMAIL"));
				loginMember.setMemberNickname(rs.getString("MEMBER_NICKNAME"));
				loginMember.setMemberTel(rs.getString("MEMBER_TEL"));
				loginMember.setMemberAddress(rs.getString("MEMBER_ADDRESS"));
				loginMember.setProfileImage(rs.getString("PROFILE_IMG"));
				loginMember.setAuthority(rs.getInt("AUTHORITY"));
				loginMember.setEnrollDate(rs.getString("ENROLL_DATE"));
			
				
				
				loginMember = new Member(rs.getInt("MEMBER_NO"),
						                 "MEMBER_EMAIL",
						                 rs.getString("MEMBER_NICKNAME"),
						                 rs.getString("MEMBER_TEL"),
						                 rs.getString("MEMBER_ADDRESS"),
						                 rs.getString("PROFILE_IMG"), 
						                 rs.getString("ENROLL_DATE"),
						                 rs.getInt("AUTHORITY"));
			}
			
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		
		return loginMember;
	}

	
}
