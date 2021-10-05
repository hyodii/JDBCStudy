/*===========================
		Test001.java
	- 쿼리문 전송 실습
=============================*/

package com.test;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.util.DBConn;

public class Test001
{
	public static void main(String[] args)
	{
		try
		{
			Connection conn = DBConn.getConnection();
			
			if(conn != null)
			{
				System.out.println("데이터베이스 연결 성공~!!!");
				
				try
				{
					/*
					Statement stmt = conn.createStatement();
					
					String sql = "INSERT INTO TBL_MEMBER(SID, NAME, TEL) "
							+ "VALUES(MEMBERSEQ.NEXTVAL, '정효진','010-6666-6666')";
					int result = stmt.executeUpdate(sql);
					if(result>0)
						System.out.println("데이터 입력 성공~!!!");
					stmt.close();
					DBConn.close();
					*/
					
					// preparedStatement 는 위의 구조로는 못만든다!
					// 쿼리문 준비 → 작업 객체 생성순서 변경!	양파를 먼저깎으나 당근을 먼저 깍으나 재료 손질은 똑같아서 실행잘됨!!				
					/*
					String sql = "INSERT INTO TBL_MEMBER(SID, NAME, TEL) "
							+ "VALUES(MEMBERSEQ.NEXTVAL, '손다정','010-7777-7777')";
					
					Statement stmt = conn.createStatement();
					
					int result = stmt.executeUpdate(sql);
					if(result>0)
						System.out.println("데이터 입력 성공~!!!");
					stmt.close();
					DBConn.close();
					*/
					
					// preparedStatement 사용!!
					String sql = "INSERT INTO TBL_MEMBER(SID, NAME, TEL) "
							+ "VALUES(MEMBERSEQ.NEXTVAL, ?,?)";			// ★ '?' 이거 아님!! ?이거만 적는게 약속!(문자, 날짜, 숫자 다 똑같음!)
					
					/*
					PreparedStatement pstmt = conn.prepareStatement(sql);
					
					// IN 매개변수 넘겨주니 
					pstmt.setString(1,"박효빈");			// check~!!
					pstmt.setString(2, "010-8888-8888");	// check~!!
					
					int result = pstmt.executeUpdate();		//★ check~!! 매개변수 넘겨주지 않음!!!
					
					if(result>0)
						System.out.println("데이터 입력 성공~!!!");
					pstmt.close();
					DBConn.close();
					*/
					
					PreparedStatement pstmt = conn.prepareStatement(sql);
					
					// IN 매개변수 넘겨주니 
					pstmt.setString(1,"최현정");			// check~!!
					pstmt.setString(2, "010-9999-9999");	// check~!!
					
					System.out.println(sql);
					
					int result = pstmt.executeUpdate();		//★ check~!! 매개변수 넘겨주지 않음!!!
					
					if(result>0)
						System.out.println("데이터 입력 성공~!!!");
					pstmt.close();
					DBConn.close();
					/*
					// 이렇게 나옴!!!
					데이터베이스 연결 성공~!!!
					INSERT INTO TBL_MEMBER(SID, NAME, TEL) VALUES(MEMBERSEQ.NEXTVAL, ?,?)
					데이터 입력 성공~!!!
					*/
					
					
					
				} catch (Exception e)
				{
					System.out.println(e.toString());
				}
					
			}
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
}
