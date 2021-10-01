/*========================================
   		ScoreDAO.java
   	- 데이터베이스 액션처리전용 객체 
==========================================*/
// 매소드 정의
// 결과값으로 반환할 변수 선언 및 초기화
// 작업 객체 생성
// 쿼리문 준비
// 생성된 작업 객체를 활용하여 쿼리문 실행 → select → executeQuery() → ResultSet 반환
// ResultSet 처리 → 일반적 반복문 활용
//최종 결과값 반환

package com.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.util.DBConn;

public class ScoreDAO
{
	// 주요 변수 선언 → DB 연결 객체
	private Connection conn;
	
	// 생성자 정의
	public ScoreDAO()  
	{
		conn = DBConn.getConnection();
	}
		
	// 메소드 정의 → 데이터를 입력하는 기능 → insert
	public int add(ScoreDTO dto) throws SQLException
	{
		int result = 0;
		
		// 작업객체 생성
		Statement stmt = conn.createStatement();
		
		// 쿼리문 준비
		String sql = String.format("INSERT INTO TBL_SCORE(SID,NAME,KOR,ENG,MAT) VALUES(SCORESEQ.NEXTVAL,'%s',%d,%d,%d)"
					, dto.getName(), dto.getKor(), dto.getEng(), dto.getMat());
			
		
		// 쿼리문 실행
		result = stmt.executeUpdate(sql);
		
		// 사용한 리소스 반납
		stmt.close();
		
		// 최종 결과값 반환
		return result;
	}
	
	
	// 메소드 정의 → 전체 인원수 확인 가능
	public int count() throws SQLException
	{
		// 작업 객체 생성
		Statement stmt = conn.createStatement();
		int result = 0;
		
		// 쿼리문 준비
		String sql = "SELECT COUNT(*) AS COUNT FROM TBL_SCORE";
		
		// 쿼리문 실행 → ResultSet 있으면 무조건 while!
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next())
		{
			result = rs.getInt(1);
		}
		
		// 리소스 반납
		rs.close();
		stmt.close();
		
		// 최종 결과값 반환
		return result;
	}
	
	// 메소드 정의 → 전체 리스트 조회 기능
	public ArrayList<ScoreDTO> lists() throws SQLException
	{
		// 결과값으로 반환할 변수 선언 및 초기화
		ArrayList<ScoreDTO> result = new ArrayList<ScoreDTO>();
		
		// 작업 객체 생성
		Statement stmt = conn.createStatement();
		
		// 쿼리문 준비
		String sql = "SELECT SID, NAME, KOR, ENG, MAT, KOR+ENG+MAT AS TOT, (KOR+ENG+MAT)/3.0 AS AVG FROM TBL_SCORE";
		
		// 쿼리문 실행
		ResultSet rs = stmt.executeQuery(sql);
		
		/*
		Arraylist result
		[      ] - [      ] - [      ]
		ScoreDTO   ScoreDTO	  ScoreDTO
		   |
 		  ↓
 		 dto [rs.getInt("SID") / rs.getString("NAME") / * / * / * / * / * /]  
 		 
 		// result.add(dto); 를 해주면
 		 
 		Arraylist result
		[  dto  ] - [  dto  ] - [  dto  ]
		
 		 */ 
 				  
		while (rs.next())
		{
			// dto 빈공간 만들어주는 것
			ScoreDTO dto = new ScoreDTO();
			
			// 그 빈공간에 있는 값 넣어주는 것
			dto.setSid(rs.getInt("SID"));
			dto.setName(rs.getString("NAME"));
			dto.setKor(rs.getInt("KOR"));
			dto.setEng(rs.getInt("ENG"));
			dto.setMat(rs.getInt("MAT"));
			dto.setTot(rs.getInt("KOR"), rs.getInt("ENG"), rs.getInt("MAT"));
			dto.setAvg(rs.getInt("KOR"), rs.getInt("ENG"), rs.getInt("MAT"));
			
			// ArrayList.add
			// 어레이리스트에 값을 집어넣는것
			result.add(dto);
			
		}
		
		// 리소스 반납
		rs.close();
		stmt.close();
		
		// 결과값 반환
		return result;
	}
	
	// 데이터베이스 연결 종료
	public void close() //throws SQLException
	{
		DBConn.close();
	}
}














