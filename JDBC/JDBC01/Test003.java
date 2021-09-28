/*===========================================
 	Test002.java
 	- 데이터베이스 연결 실습
 	- 데이터 입력 실습
=============================================*/

package com.test;

import java.sql.Connection;
import java.sql.Statement;

import com.util.DBConn;

public class Test003
{
	public static void main(String[] args)
	{
		Connection conn = DBConn.getConnection();
		
		if(conn == null)
		{
			System.out.println("데이터 베이스 연결 실패~!!!");
			System.exit(0);
		}
		
		//System.out.println("데이터베이스 연결 성공~!!!");
		
		try
		{
			// 작업 객체 준비
			Statement stmt = conn.createStatement();
			
			// 데이터 입력 쿼리 실행 과정
			// 한 번 실행하면 다시 실행하지 못하는 상황이다.
			// 기본키 제약조건이 설정되어 있으므로
			// 동일한 키 값이 중복될 수 없기 때문이다.
			
			// 쿼리문 준비
			String sql = "INSERT INTO TBL_MEMBER(SID, NAME, TEL) VALUES (2, '채지윤', '010-2222-2222')";
			//-- 주의. 쿼리문 끝에 『;』 붙이지 않는다.
			//-- 주의. 자바에서 실행한 DML 구문은 내부적으로 자동 commit 된다.
			//-- 주의. 오라클에서 트랜잭션 처리가 끝나지 않으면
			//		   즉, 커밋을 끝내지 않으면 데이터 액션 처리가 이루어지지 않는다.
			
			// 이거 두개만 기억!
			// stmt.executeQuery() - SELECT 구문을 수행할 때 사용되는 함수
			// stmt.executeUpdate() - INSERT, UPDATE, DELETE 쿼리문(오라클안에서 변경 수정되면 Update)
			
			int result = stmt.executeUpdate(sql);	// 반환자료형 int (5개 넣었으면 5 / 10개 삭제했으면 10)
			
			if(result>0)
			{
				System.out.println("테이터 입력 성공~!!!");
			}
			else
			{
				System.out.println("입력 실패 ㅠ_ㅠ");
			}
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		DBConn.close();
	}
}

// 실행 결과
/*
데이터 입력 성공~!!! 
*/









