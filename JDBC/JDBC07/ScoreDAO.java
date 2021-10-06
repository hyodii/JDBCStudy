/*===================================
	ScoreDAO.java
	- 데이터베이스 액션처리전용 객체
=====================================*/

/*
====[ 성적 처리 ]====
1. 성적 입력
2. 성적 전체 출력
3. 이름 검색 출력
4. 성적 수정
5. 성적 삭제
======================
>> 선택(1~5, -1종료) : 
*/


package com.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.util.DBConn;

public class ScoreDAO
{
   // 주요 속성 구성
   private Connection conn;
   
   // 데이터베이스 연결 메소드
   public Connection connection() 
   {
      conn = DBConn.getConnection();
      return conn;
   }
   
   // 데이터베이스 연결 종료 메소드
   public void close()
   {
      DBConn.close();
   }
   
   
   // 데이터 입력 메소드
   public int add(ScoreDTO dto) throws SQLException
   {
      int result = 0;
      
      String sql = "INSERT INTO TBL_SCORE(SID, NAME, KOR, ENG, MAT) VALUES(SCORESEQ.NEXTVAL, ?, ?, ?, ?)";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      
      pstmt.setString(1, dto.getName());
      pstmt.setInt(2, dto.getKor());
      pstmt.setInt(3, dto.getEng());
      pstmt.setInt(4, dto.getMat());
      
      result = pstmt.executeUpdate();
      pstmt.close();
            
      return result;
   }
   
   
   // 인원 수 확인 담당 메소드
   public int count() throws SQLException
   {
      int result = 0;
      
      String sql = "SELECT COUNT(*) AS COUNT FROM TBL_SCORE";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      ResultSet rs = pstmt.executeQuery();
      
      while (rs.next())               
         result = rs.getInt("COUNT");   
      
      rs.close();
      pstmt.close();      
      
      return result;
   }
      
   
   // 전체 리스트 출력 메소드
   public ArrayList<ScoreDTO> lists() throws SQLException
   {
      ArrayList<ScoreDTO> result = new ArrayList<ScoreDTO>();
      
      String sql = "SELECT SID, NAME, KOR, ENG, MAT"
            + ", (KOR + ENG + MAT) AS TOT, (KOR + ENG + MAT)/3 AS AVG"
            + ", RANK() OVER(ORDER BY (KOR + ENG + MAT) DESC) AS RANK" 
            + " FROM TBL_SCORE" 
            + " ORDER BY SID ASC";
      
      PreparedStatement pstmt = conn.prepareStatement(sql);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next())
      {
         ScoreDTO dto = new ScoreDTO();

         dto.setSid(rs.getString("SID"));
         dto.setName(rs.getString("NAME"));
         dto.setKor(rs.getInt("KOR"));
         dto.setEng(rs.getInt("ENG"));
         dto.setMat(rs.getInt("MAT"));
         dto.setTot(rs.getInt("TOT"));
         dto.setAvg(rs.getDouble("AVG"));
         dto.setRank(rs.getInt("RANK"));

         result.add(dto);         
      }
      
      rs.close();
      pstmt.close();      
      
      return result;
   }
   
   
   // 이름 검색 출력 메소드
   public ArrayList<ScoreDTO> lists(String name) throws SQLException
   {
      ArrayList<ScoreDTO> result = new ArrayList<ScoreDTO>();
      
      String sql = "SELECT"
            + " * FROM"
            + " ( SELECT SID, NAME, KOR, ENG, MAT, (KOR + ENG + MAT) AS TOT"
            + ", (KOR + ENG + MAT)/3 AS AVG" 
            + ", RANK() OVER(ORDER BY (KOR + ENG + MAT) DESC) AS RANK"
            + " FROM TBL_SCORE )"
            + " WHERE NAME = ?";
      
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, name);
      
      ResultSet rs = pstmt.executeQuery();
      while (rs.next())
      {
         ScoreDTO dto = new ScoreDTO();

         dto.setSid(rs.getString("SID"));
         dto.setName(rs.getString("NAME"));
         dto.setKor(rs.getInt("KOR"));
         dto.setEng(rs.getInt("ENG"));
         dto.setMat(rs.getInt("MAT"));
         dto.setTot(rs.getInt("TOT"));
         dto.setAvg(rs.getDouble("AVG"));
         dto.setRank(rs.getInt("RANK"));

         result.add(dto);
      }
      
      rs.close();
      pstmt.close();      
      
      return result;
   }
   
   
   // 번호 검색 출력 메소드
   public ArrayList<ScoreDTO> lists(int sid) throws SQLException
   {
      ArrayList<ScoreDTO> result = new ArrayList<ScoreDTO>();
      
      String sql = "SELECT"
            + " * FROM"
            + " ( SELECT SID, NAME, KOR, ENG, MAT, (KOR + ENG + MAT) AS TOT"
            + ", (KOR + ENG + MAT)/3 AS AVG" 
            + ", RANK() OVER(ORDER BY (KOR + ENG + MAT) DESC) AS RANK"
            + " FROM TBL_SCORE )"
            + " WHERE SID = ?";
      
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, sid);
      
      ResultSet rs = pstmt.executeQuery();
      while (rs.next())
      {
         ScoreDTO dto = new ScoreDTO();

         dto.setSid(rs.getString("SID"));
         dto.setName(rs.getString("NAME"));
         dto.setKor(rs.getInt("KOR"));
         dto.setEng(rs.getInt("ENG"));
         dto.setMat(rs.getInt("MAT"));
         dto.setTot(rs.getInt("TOT"));
         dto.setAvg(rs.getDouble("AVG"));
         dto.setRank(rs.getInt("RANK"));

         result.add(dto);
      }
      
      rs.close();
      pstmt.close();      
      
      return result;
   }
   
   
   // 데이터 수정 담당 메소드
   public int modify(ScoreDTO dto) throws SQLException
   {
      int result = 0;
      
      String sql = "UPDATE TBL_SCORE SET NAME=?, KOR=?, ENG=?, MAT=? WHERE SID = ?";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      
      pstmt.setString(1, dto.getName());
      pstmt.setInt(2, dto.getKor());
      pstmt.setInt(3, dto.getEng());
      pstmt.setInt(4, dto.getMat());
      pstmt.setInt(5, Integer.parseInt(dto.getSid()));
      
      result = pstmt.executeUpdate();
      
      pstmt.close();
      
      return result;
   }
   

   // 데이터 삭제 담당 메소드
   public int remove(int sid) throws SQLException
   {
      int result = 0;
      
      String sql = "DELETE FROM TBL_SCORE WHERE SID = ?";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      
      pstmt.setInt(1, sid);
      
      result = pstmt.executeUpdate();
      pstmt.close();
      
      return result;      
   }   
   
}















/*
package com.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.util.DBConn;

public class ScoreDAO
{
	// 주요 속성 구성
	// 데이터베이스 연결
	private Connection conn;
		
	
	// 데이터베이스 연결을 담당하는 메소드
	public Connection connection() throws ClassNotFoundException, SQLException			// check~!! 예외처리!!
	{
		conn = DBConn.getConnection();
		return conn;
	}
	
	
	// 성적 입력 담당 메소드
	public int add(ScoreDTO dto) throws SQLException
	{
		int result = 0;
		String sql = "INSERT INTO TBL_SCORE(SID, NAME, KOR, ENG, MAT) "
				+ "VALUES(?, ?, ?, ?, ?)";			// SID 부분 처리????
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, dto.getSid());
		pstmt.setString(2, dto.getName());
		pstmt.setInt(3, dto.getKor());
		pstmt.setInt(4, dto.getEng());
		pstmt.setInt(5, dto.getMat());
		
		result = pstmt.executeUpdate();
			
		pstmt.close();
		return result;
		
	}// end add()
	
	
	// 전체 리스트 출력 담당 메소드
	public ArrayList<ScoreDTO> lists() throws SQLException
	{
		ArrayList<ScoreDTO> result = new ArrayList<ScoreDTO>();
		String sql = "SELECT SID, NAME, KOR, ENG,MAT" 
				+ ", (KOR+ENG+MAT) AS TOT" 
				+ ", (KOR+ENG+MAT)/3 AS AVG"
				+ ", RANK() OVER(ORDER BY (KOR+ENG+MAT) DESC) AS RANK " 
				+ "FROM TBL_SCORE " 
				+ "ORDER BY SID ASC";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next())
		{
			ScoreDTO dto = new ScoreDTO();
			dto.setSid(rs.getInt("SID"));
			dto.setName(rs.getString("NAME"));
			dto.setKor(rs.getInt("KOR"));
			dto.setEng(rs.getInt("ENG"));
			dto.setMat(rs.getInt("MAT"));
			dto.setTot(rs.getInt("TOT"));
			dto.setAvg(rs.getDouble("AVG"));
			dto.setRank(rs.getInt("RANK"));
			
			result.add(dto);
		}
		
		rs.close();
		pstmt.close();
		
		return result;
		
	}
	
	
	// 이름 검색 담당 메소드
	public ArrayList<ScoreDTO> lists(String name) throws SQLException
	{
		ArrayList<ScoreDTO> result = new ArrayList<ScoreDTO>();
		String sql = "SELECT * "
				+ "FROM "
				+ "(SELECT SID, NAME, KOR, ENG, MAT, (KOR+ENG+MAT) AS TOT"
				+ ", (KOR+ENG+MAT)/3 AS AVG, RANK() OVER(ORDER BY (KOR+ENG+MAT) DESC) AS RANK "
				+ "FROM TBL_SCORE) "
				+ "WHERE NAME = ?";			// 이부분 받는거 어떻게 처리해야할지 모르겠음
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, name);			// ???????????
		

		ResultSet rs = pstmt.executeQuery();
		while (rs.next())
		{
			ScoreDTO dto = new ScoreDTO();
			dto.setSid(rs.getInt("SID"));
			dto.setName(rs.getString("NAME"));
			dto.setKor(rs.getInt("KOR"));
			dto.setEng(rs.getInt("ENG"));
			dto.setMat(rs.getInt("MAT"));
			dto.setTot(rs.getInt("TOT"));
			dto.setAvg(rs.getDouble("AVG"));
			dto.setRank(rs.getInt("RANK"));
			
			result.add(dto);	
		}
		
		rs.close();
		pstmt.close();
		
		
		return result;

	} 
	
	
	//번호 검색 담당 메소드
	public ArrayList<ScoreDTO> lists(int sid) throws SQLException
	{							
		ArrayList<ScoreDTO> result = new ArrayList<ScoreDTO>();
		String sql = "SELECT "
				+ "* FROM "
				+ "(SELECT SID, NAME, KOR, ENG, MAT"
				+ ", (KOR+ENG+MAT) AS TOT, (KOR+ENG+MAT)/3 AS AVG"
				+ ", RANK() OVER(ORDER BY (KOR+ENG+MAT) DESC) AS RANK "
				+ "FROM TBL_SCORE) "
				+ "WHERE SID = ?";		// 이부분 받는거 어떻게 처리해야할지 모르겠음
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, sid);			// ??????????
		
		ResultSet rs = pstmt.executeQuery();
		while (rs.next())
		{
			ScoreDTO dto = new ScoreDTO();
			
			dto.setSid(rs.getInt("SID"));
			dto.setName(rs.getString("NAME"));
			dto.setKor(rs.getInt("KOR"));
			dto.setEng(rs.getInt("ENG"));
			dto.setMat(rs.getInt("MAT"));
			dto.setTot(rs.getInt("TOT"));
			dto.setAvg(rs.getDouble("AVG"));
			dto.setRank(rs.getInt("RANK"));
			
			result.add(dto);
		}
		
		rs.close();
		pstmt.close();
		
		
		return result;
	}
	
	
	
	// 인원 수 확인 담당 메소드
	public int count() throws SQLException
	{
		int result = 0;
		String sql = "SELECT COUNT(*) AS COUNT FROM TBL_SCORE";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next())
		{
			result = rs.getInt("COUNT");
		}
		
		rs.close();
		pstmt.close();
		
		return result;
	}
	
	
	
	// 성적 수정 담당 메소드
	public int modify(ScoreDTO dto) throws SQLException
	{
		int result = 0;
		String sql = "UPDATE TBL_SCORE SET NAME = ?, KOR=?, ENG=?, MAT=? WHERE SID = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, dto.getName());
		pstmt.setInt(2, dto.getKor());
		pstmt.setInt(3, dto.getEng());
		pstmt.setInt(4, dto.getMat());
		pstmt.setInt(5, dto.getSid());
		
		result = pstmt.executeUpdate();
		
		pstmt.close();
		
		return result;
		
	}
	
	
	
	// 성적 삭제 담당 메소드
	public int remove(String sid) throws SQLException
	{
		int result = 0;
		String sql = "DELETE FROM TBL_SCORE WHERE SID=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, sid);
		result = pstmt.executeUpdate();
		pstmt.close();		
		
		return result;
		
	}
	
	
	// 데이터베이스 연결 종료 담당 메소드
	public void close() throws SQLException
	{
		DBConn.close();
	}
	
	
	
}
*/