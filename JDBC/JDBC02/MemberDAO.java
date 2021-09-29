/*====================
	MemberDAO.java
======================*/
/*
<DAO 에 있어야 하는 것>

	- DB 연결
	- 인원 수 확인
	- 데이터 입력 쿼리문
	- 리스트 전체 조회

*/

//Database 에 Access 하는 기능
// → DBConn 활용

// 데이터 입력하는 기능 → INSERT

// 인원수 확인하는 기능
// 대상 테이블(TBL_MEMBER)의 레코드 카운팅 기능 → SELECT

// 전체 리스트 조회하는 기능
// 대상 테이블(TBL_MEMBER)의 데이터를 조회하는 기능 → SELECT

package com.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.util.DBConn;

public class MemberDAO
{
	//  주요 변수 선언 → DB 연결 객체
	private Connection conn;
	
	// 생성자 정의 (해서 나중에 인스턴스 호출할때 자동으로 DBConn 연결되게!)
	public MemberDAO() throws ClassNotFoundException, SQLException
	{
		conn = DBConn.getConnection();
	}
	
	
	
	// 메소드 정의 → 데이터를 입력하는 기능 → insert
	// conn 객체 활용 Statement  executeUpdate()		insert 쿼리 수행	int 적용된 행의 갯수 반환
	public int add(MemberDTO dto) throws SQLException
	{
		// 반환할 결과값을 담아낼 변수(적용된 행의 갯수)
		int result = 0;
		
		// 작업 객체 생성
		Statement stmt = conn.createStatement();
		
		/*
		※ Statement 의 종류
			- Statement
			  하나의 쿼리를 사용하고 나면 더 이상 사용할 수 없다.
			- PreparedStatement
			  하나의 PreparedStatement 로 쿼리를 여러 번 처리할 수 있다.
			- CallableStatement
			  데이터베이스 내의 스토어드 프로시저나 함수 등을 호출할 수 있다.
			  
			  보안성 때문에 PreparedStatement가 거의 대부분 쓰임!!
			  
		※ Statement 의 의미
		   자바에서 사용되는 3가지 종류의 작업 객체들은
		   데이터베이스로 쿼리를 담아서 보내는 그릇 정도로 생각하자.
		   즉, 작업 객체에 쿼리를 실어 데이터베이스로 보내버리면
		   그 내용이 데이터베이스에서 처리되는 것이다.
		   이 때, 한 번 사용하고 버리는 그릇은 Statement 이며,
		   재사용이 가능한 그릇은 PreparedStatement 이다.
		   
		 */
		
		// 쿼리문 준비
		//			 ------------- 문자열의 형태를 반환하는거 printf로 생각!
		String sql = String.format("INSERT INTO TBL_MEMBER(SID, NAME, TEL) VALUES(MEMBERSEQ.NEXTVAL,'%s', '%s')"
								, dto.getName(), dto.getTel());
		
		// 작업 객체를 활용하여 쿼리문 실행(전달)
		result = stmt.executeUpdate(sql);
		
		// 사용한 리소스 반납
		stmt.close();
		
		// 최종 결과값 반환
		return result;
		
	}// end add()
	
	
	// 메소드 정의 → 전체 인원 수 확인 기능
	// 특정인원수 확인하는게 아니라 전체인원수 확인이니까 
	// 매개변수 넘겨 받는 것 없음!! -- check~!!
	public int count() throws SQLException
	{
		// 반환할 결과값을 담아낼 변수 선언 및 초기화(적용된 행의 갯수)
		int result = 0;
		
		// 작업 객체 생성
		Statement stmt = conn.createStatement();
		
		// 쿼리문 준비 → select
		String sql = "SELECT COUNT(*) AS COUNT FROM TBL_MEMBER";
	
		
		// 테스트
		//System.out.println(sql);
		
		// 작업 객체를 활용하여 쿼리문 실행(전달)
		// ResultSet 실행 → select 쿼리문 → executeQuery() → ResultSet 반환 → 일반적 반복 처리
		ResultSet rs = stmt.executeQuery(sql);
		
		// ResultSet 처리 → 반복문 구성 → 결과값 수신
		// ResultSet 을 썻네? → while 쓰자!
		while (rs.next())	// if (rs.next())
		{
			result = rs.getInt("COUNT");		//rs.getInt(1);		// ※ 컬럼 인덱스는 1 부터~!!!
			// COUNT 컬럼의 값을 정수형태로 받겠다
		}
				
		// 사용한 리소스 반납(반납할땐 먼저 얻어쓴걸 나중에 반납!)
		rs.close();
		stmt.close();
		
		// 최종 결과값 반환
		return result;		
		
	}
	
	// 메소드 정의 → 전체 리스트 조회 기능
	//public MemberDTO들 lists() throws SQLException
	public ArrayList<MemberDTO> lists() throws SQLException
	{
		// 결과값으로 변환할 변수 선언 및 초기화
		ArrayList<MemberDTO> result = new ArrayList<MemberDTO>();
		
		// 작업객체 생성
		Statement stmt = conn.createStatement();
		
		// 쿼리문 준비
		String sql = "SELECT SID, NAME, TEL FROM TBL_MEMBER ORDER BY SID";
		
		// 생성된 작업 객체를 활용하여 쿼리문 실행 → select → executeQuery() → ResultSet 반환
		ResultSet rs = stmt.executeQuery(sql);
		
		// ResultSet 처리 → 일반적 반복문 활용 → MemberDTO 인스턴스 생성 → 속성 구성 → Arraylist 에 적재
		while (rs.next())
		{
			MemberDTO dto = new MemberDTO();
			
			dto.setSid(rs.getString("SID"));
			dto.setName(rs.getString("NAME"));
			dto.setTel(rs.getString("TEL"));
			
			result.add(dto);
			
		}
		// 리소스 반납		
		rs.close();
		stmt.close();
		
		//최종 결과값 반환
		return result;
		
	}// end lists()	
	
	// 메소드 정의 → 데이터베이스 연결 종료 → DBConn 활용
	public void close() throws SQLException
	{
		DBConn.close();
	}
	
}
