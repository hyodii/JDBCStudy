/*===================================
	ScoreDTO.java
	- 데이터베이스 액션처리전용 객체
=====================================*/

//전체 주석처리
// 전체 잡고 : ctrl + shift + /
// 해제하는 법 : ctrl + shift + \
/*
 * acdkf; acdkf; acdkf; acdkf; acdkf; acdkf; acdkf; acdkf; acdkf; acdkf;
 */

// 쿼리문에 문제가 있을 때는 99퍼센트는 DAO 에서 난다! 그리고 나머지 는 DBConn일 수 있음

package com.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.util.DBConn;

public class ScoreDAO
{
	// 주요 속성 구성
	// db연결
	private Connection conn;

	// 데이터베이스 연결 담당 메소드
	//     ---------Connection 을 반환해주는 것(리턴자료형)
	public Connection connection() throws ClassNotFoundException, SQLException
	{
		conn = DBConn.getConnection();
		return conn;
	}

	// 데이터 입력 담당 메소드
	public int add(ScoreDTO dto) throws SQLException
	{
		int result = 0;
		Statement stmt = conn.createStatement();

		// 여기서는 insert 니까 컬럼에 입력하는 값만 넘겨주면 됨!!
		String sql = String.format(
				"INSERT INTO TBL_SCORE(SID, NAME, KOR, ENG, MAT) VALUES(SCORESEQ.NEXTVAL, '%s', %d, %d, %d)",
				dto.getName(), dto.getKor(), dto.getEng(), dto.getMat());

		result = stmt.executeUpdate(sql);
		stmt.close();
		return result;
	}

	// 전체 리스트 출력 담당 메소드
	// ------------------- 이부분 다시보기!!! ok
	public ArrayList<ScoreDTO> lists() throws SQLException
	{
		ArrayList<ScoreDTO> result = new ArrayList<ScoreDTO>();

		Statement stmt = conn.createStatement();

		String sql = "SELECT SID, NAME, KOR, ENG,MAT" 
				+ ", (KOR+ENG+MAT) AS TOT" 
				+ ", (KOR+ENG+MAT)/3 AS AVG"
				+ ", RANK() OVER(ORDER BY (KOR+ENG+MAT) DESC) AS RANK " 
				+ "FROM TBL_SCORE " 
				+ "ORDER BY SID ASC";

		ResultSet rs = stmt.executeQuery(sql);
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
		stmt.close();

		return result;
	}

	// 이름 검색 담당 메소드 → 오버 로딩 → 같은이름을 가졌는데 다른기능을 하는것
	// 오버로딩 성립하려면 리턴자료형 같아야함
	// ScoreDTO 여러개 받는게 아니고 ScoreDTO 하나 받고 싶으면 이름 lists면 안댐!
	// public ScoreDTO list(String name) 
	public ArrayList<ScoreDTO> lists(String name) throws SQLException
	{
		ArrayList<ScoreDTO> result = new ArrayList<ScoreDTO>();
		Statement stmt = conn.createStatement();

		String sql = String.format("SELECT * "
				+ "FROM "
				+ "(SELECT SID, NAME, KOR, ENG, MAT, (KOR+ENG+MAT) AS TOT"
				+ ", (KOR+ENG+MAT)/3 AS AVG, RANK() OVER(ORDER BY (KOR+ENG+MAT) DESC) AS RANK "
				+ "FROM TBL_SCORE) "
				+ "WHERE NAME = '%s'",name);

		ResultSet rs = stmt.executeQuery(sql);
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
		stmt.close();
		
		
		return result;

	}// end lists(String name)
	
	
	
	// 번호 검색 담당 메소드 → 오버로딩
	// 한명의 학생이 보여지겠지만 오버로딩하겠다 했으니까 한명의 자료 아님!
	// 지금은 매개변수가 없거나 문자형 매개변수 넘겨받을 수 없는 상태! int로 가자!
	public ArrayList<ScoreDTO> lists(int sid) throws SQLException
	{							   //---  → int sid 는 String sql에 값 넣을 때 사용하고 그 이후로는 안쓴다!★
		ArrayList<ScoreDTO> result = new ArrayList<ScoreDTO>();
		
		Statement stmt = conn.createStatement();
		
		String sql = String.format("SELECT "
				+ "* FROM "
				+ "(SELECT SID, NAME, KOR, ENG, MAT"
				+ ", (KOR+ENG+MAT) AS TOT, (KOR+ENG+MAT)/3 AS AVG"
				+ ", RANK() OVER(ORDER BY (KOR+ENG+MAT) DESC) AS RANK "
				+ "FROM TBL_SCORE) "
				+ "WHERE SID = %d", sid);
		
		ResultSet rs = stmt.executeQuery(sql);
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
		stmt.close();
		
		
		return result;
		
	}// end lists(ing sid)
	
	
	// 인원 수 확인 담당 메소드
	public int count() throws SQLException
	{
		int result = 0;
		
		Statement stmt = conn.createStatement();
		String sql = "SELECT COUNT(*) AS COUNT FROM TBL_SCORE";
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next())	// 한번 도니까 얘만큼은  // if(rs.next()) 해도 됨!
			result = rs.getInt("COUNT");			 //	rs.getInt(1);
		rs.close();
		stmt.close();
		
		return result;
	}
	
	// 데이터 수정 담당 메소드
	// 생각을 잘해야함! → 매개변수로 받아와야할 자료형 int(X)
	// 이름 영어 국어 수학 이걸 전부 수정해야하니까! → dto타입 받아와야함!!
	// 바꿔야할 모든 속성을 받아와야함!
	// 반환해야할 값은 executeUpdate니까 int
	public int modify(ScoreDTO dto) throws SQLException	//-- check~!!!★
	{
		int result = 0;
		//coreDTO dto = new ScoreDTO();
		//ArrayList<ScoreDTO> result = new ArrayList<ScoreDTO>();
		
		Statement stmt = conn.createStatement();
		String sql = String.format("UPDATE TBL_SCORE SET NAME = '%s', KOR=%d, ENG=%d, MAT=%d WHERE SID = %s"
					, dto.getName(), dto.getKor(), dto.getEng(), dto.getMat(), dto.getSid());
		// ★ 이름에는 홑따옴표가 있어야하고 sid는 문자열일지언정 홑따옴표 안씌우면 숫자타입으로 들어가서 괜찮다!!
		// ★ 값받아올 때는 dto.get 사용!!!
		
		result = stmt.executeUpdate(sql);
		stmt.close();
		
		return result;
		
	}
	
	
	
	
	// 데이터 삭제 담당 메소드
	public int remove(int sid) throws SQLException
	{
		int result = 0;
		Statement stmt = conn.createStatement();
		String sql = String.format("DELETE FROM TBL_SCORE WHERE SID=%d", sid);
		result =  stmt.executeUpdate(sql);
		stmt.close();
		return result;
		
	}
	
	
	// 데이터베이스 연결 종료 담당 메소드
	public void close() throws SQLException
	{
		DBConn.close();
	}
	
	
	

}
