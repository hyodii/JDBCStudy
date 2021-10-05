/*===========================
		Test003.java
	- 쿼리문 전송 실습
	pstmt.executeQuery()
=============================*/
package com.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.util.DBConn;

public class Test003
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
					String sql = "SELECT SID, NAME, TEL FROM TBL_MEMBER ORDER BY SID";
					
					PreparedStatement pstmt = conn.prepareStatement(sql);
					
					ResultSet rs = pstmt.executeQuery();
					
					while (rs.next())
					{
						int sid = rs.getInt("SID");
						String name = rs.getString("NAME");
						String tel = rs.getString("TEL");
						
						String str = String.format("%3d %7s %10s", sid,name,tel);
						System.out.println(str);
					}
					rs.close();
					pstmt.close();
					
				} catch (Exception e)
				{
					System.out.println(e.toString());
				}
			}
			DBConn.close();
			System.out.println("\n데이터베이스 연결 닫힘~!!!");
			System.out.println("프로그램 종료됨~!!!");
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
}


/*
데이터베이스 연결 성공~!!!
  1     김진희 010-1111-1111
  2     이찬호 010-2222-2222
  3     박혜진 010-3333-3333
  4     윤유동 010-4444-4444
  5     박혜진 010-5555-5555
  6     정효진 010-6666-6666
  7     손다정 010-7777-7777
  8     박효빈 010-8888-8888
  9     최현정 010-9999-9999
 10     손범석 010-1010-1010
 11     최수지 010-1100-1100
 12     채지윤 010-1212-1212

데이터베이스 연결 닫힘~!!!
프로그램 종료됨~!!!
*/
