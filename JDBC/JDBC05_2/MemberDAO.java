/*=========================================
		MemberDAO.java
	- 데이터베이스 액션 처리 전용 클래스
==========================================*/

package com.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.sun.glass.ui.Pixels.Format;
import com.util.DBConn;

public class MemberDAO
{
	// 주요 속성 구성
	private Connection conn;
	
	// 데이터베이스 연결
	public Connection connection()
	{
		conn = DBConn.getConnection();
		return conn;
		
	
	}
	
	// 데이터 베이스 연결 종료
	public void close()
	{
		DBConn.close();
	}
	
	
	// 1. 직원 데이터 입력
	public int add(MemberDTO dto) throws SQLException
	{
		int result = 0;
		Statement stmt = conn.createStatement();
		// dto작업할때 dto.getEmpName() -> String 이네? -> %s로 바꿔 이렇게 작업하면 좋음
		String sql = String.format("INSERT INTO TBL_EMP"
				+ "(EMP_ID, EMP_NAME, SSN, IBSADATE,CITY_ID"
				+ ", TEL, BUSEO_ID, JIKWI_ID, BASICPAY, SUDANG)"
				+ " VALUES(EMPSEQ.NEXTVAL,'%s','%s', TO_DATE('%s','YYYY-MM-DD') "
				+ ", (SELECT CITY_ID FROM TBL_CITY WHERE CITY_NAME = '%s') "
				+ ", '%s' "
				+ ", (SELECT BUSEO_ID FROM TBL_BUSEO WHERE BUSEO_NAME = '%s')"
				+ ", (SELECT JIKWI_ID FROM TBL_JIKWI WHERE JIKWI_NAME = '%s')"
				+ ", %d, %d)"
				, dto.getEmpName(),dto.getSsn(),dto.getIbsaDate()
				,dto.getCityName(),dto.getTel()
				,dto.getBuseoName(),dto.getJikwiName()
				,dto.getBasicPay(), dto.getSudang());
		
		result = stmt.executeUpdate(sql);
		stmt.close();
		return result;
		
	}// end add()
	
	
	// 전체 직원 수 조회
	public int memberCount() throws SQLException
	{
		// 반환할 결과 변수 선언 및 초기화
		int result = 0;
		
		// 작업 객체 생성
		Statement stmt = conn.createStatement();
		
		// 쿼리문 준비
		String sql = "SELECT COUNT(*) AS COUNT FROM TBL_EMP";
		
		// 쿼리문 실행 → select →executeQuery() →ResultSet반환
		ResultSet rs = stmt.executeQuery(sql);
		
		// ResultSet 처리 → 반복문 구성(단일값일 경우 조건문도 가능) → 결과값 수신
		while(rs.next())
		{
			result = rs.getInt("COUNT");
		}
		//리소스 반납
		rs.close();
		stmt.close();
		
		// 최종 결과값 반환
		return result;
		
	}// end memberCount()
	
	
	// 검색 결과 직원 수 조회
	// ★매개변수로 하나만 넘겨받는게 아님!!★ 어떤항목으로 검색할건지까지 넘겨받는 것
	// EMP_ID=1001			→ key : EMP_ID  	 /  value : 1001
	// EMP_NAME='송해덕'	→ key : EMP_NAME    /  value : '송해덕'
	// BUSEO_NAME='개발부'	→ key : BUSEO_NAME  /  value : '개발부'
	// JIKWI_NAME='대리'	→ key : JIKWI_NAME  /  value : '대리'
	//														----- 이만큼이 입력받는 값!!
	// 이름조건으로 값이 얼마 이러니까 이름과 값 매개변수 2개 받아야함!!
	public int memberCount(String key, String value) throws SQLException
	{
		int result = 0;
		Statement stmt = conn.createStatement();
		String sql = "";
		
		if(key.equals("EMP_ID"))
			sql = String.format
					("SELECT COUNT(*) AS COUNT FROM EMPVIEW WHERE %s=%s", key, value); 		//홑따옴표 차이!★
		else
			sql = String.format
					("SELECT COUNT(*) AS COUNT FROM EMPVIEW WHERE %s='%s'", key, value); // check~!!★
																	// 전부 String 이지만 int값받으면 알아서 변환됨!
																	// 그래서 else에 있는 구문만 써도 되지만! 못믿겠으면 지금처럼하면됨!
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next())
		{
			result = rs.getInt("COUNT");
		}
		
		rs.close();
		stmt.close();
		
		return result;
		
	}// end memberCount()
	
	
	// 2. 직원 데이터 전체 조회 (사번/이름/부서/직위/급여내림차순)
	// 어떤정렬을 할지 매개변수 하나 받아야함!!
	// 만약 오름차순 정렬할지 내림차순할지도 항목을 설정한다면 매개변수 2개!!
	public ArrayList<MemberDTO> lists(String key) throws SQLException
	{
		// 반환할 결과값 선언 및 초기화
		ArrayList<MemberDTO> result = new ArrayList<MemberDTO>();
		
		// 작업객체 생성
		Statement stmt = conn.createStatement();
		
		//쿼리문 준비
		/*
		String sql = String.format("SELECT EMP_ID,EMP_NAME,SSN"
				+ ",TO_CHAR(IBSADATE,'YYYY-MM-DD') AS IBSADATE"
				+ ",CITY_NAME,TEL,BUSEO_NAME,JIKWI_NAME"
				+ ",BASICPAY,SUDANG,PAY"
				+ " FROM EMPVIEW"
				+ " ORDER BY %s", key);
		*/
		String sql = String.format("SELECT EMP_ID,EMP_NAME,SSN"
				+ ",TO_CHAR(IBSADATE,'YYYY-MM-DD') AS IBSADATE"
				+ ",CITY_NAME,NVL(TEL,'-') AS TEL, BUSEO_NAME,JIKWI_NAME"
				+ ",BASICPAY,SUDANG,PAY"
				+ " FROM EMPVIEW"
				+ " ORDER BY %s", key);
		
		// 쿼리문 실행
		ResultSet rs = stmt.executeQuery(sql);
		
		while(rs.next())	// Resultset에서 next 값이 없을때까지 반복!
		{
			// MemberDTO 인스턴스 생성해서 빈공간을 하나 만들어줘야함!
			// 1개의 MemberDTO 생성 → 비어있는 상태
			MemberDTO dto = new MemberDTO();
			// 생성된 MemberDTO 에 값 채워넣기 → 값이 채워진 MemberDTO
			dto.setEmpId(rs.getInt("EMP_ID"));	//dto.setEmpId(rs.getInt(1)); 이렇게 써도됨!
			dto.setEmpName(rs.getString("EMP_NAME"));
			dto.setSsn(rs.getString("SSN"));
			dto.setIbsaDate(rs.getString("IBSADATE"));
			dto.setCityName(rs.getString("CITY_NAME"));
			dto.setTel(rs.getString("TEL"));
			dto.setBuseoName(rs.getString("BUSEO_NAME"));
			dto.setJikwiName(rs.getString("JIKWI_NAME"));
			dto.setBasicPay(rs.getInt("BASICPAY"));
			dto.setSudang(rs.getInt("SUDANG"));
			dto.setPay(rs.getInt("PAY"));
			
			// ArrayList 에 요소로 추가
			result.add(dto);			
		}
		
		// 리소스 반납
		rs.close();
		stmt.close();
		
		// 최종 결과값 반환
		return result;
		
	}// end lists()
	
	
	// 3. 직원 데이터 검색 조회 (사번/이름/부서/직위)
	// 
	public ArrayList<MemberDTO> searchLists(String key, String value) throws SQLException
	{
		ArrayList<MemberDTO> result = new ArrayList<MemberDTO>();
		Statement stmt = conn.createStatement();
		
		// 아까랑 결과는 같은데 temp사용해보기!
		String sql = "";

		/* 이 방식으로 하면 쿼리문을 하나만 써도됨!	// 홑따옴표 묶는거 다시보기!
		String value;
		if(key.equals("EMP_ID"))
			value = "value";
		else
			value = "'" + value + "'";
		*/ 
		// 지금은 만들기 편하려고 다있는값 불러오지만 
		// 나중에는 ..LIKE %%%s% 이런식으로 해서 이름검색할때 정효만 입력해도 정효진나오게!
		
		if(key.equals("EMP_ID"))
		{
			sql = String.format("SELECT EMP_ID,EMP_NAME,SSN"
					+ ",TO_CHAR(IBSADATE,'YYYY-MM-DD') AS IBSADATE"
					+ ",CITY_NAME,TEL,BUSEO_NAME,JIKWI_NAME"
					+ ",BASICPAY,SUDANG,PAY "
					+ "FROM EMPVIEW "
					+ "WHERE %s=%s", key,value);
		}
		else
		{
			sql = String.format("SELECT EMP_ID,EMP_NAME,SSN"
					+ ",TO_CHAR(IBSADATE,'YYYY-MM-DD') AS IBSADATE"
					+ ",CITY_NAME,TEL,BUSEO_NAME,JIKWI_NAME"
					+ ",BASICPAY,SUDANG,PAY "
					+ "FROM EMPVIEW "
					+ "WHERE %s='%s'", key,value);
		}
		
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next())
		{
			MemberDTO dto = new MemberDTO();
			
			dto.setEmpId(rs.getInt("EMP_ID"));	//dto.setEmpId(rs.getInt(1)); 이렇게 써도됨!
			dto.setEmpName(rs.getString("EMP_NAME"));
			dto.setSsn(rs.getString("SSN"));
			dto.setIbsaDate(rs.getString("IBSADATE"));
			dto.setCityName(rs.getString("CITY_NAME"));
			dto.setTel(rs.getString("TEL"));
			dto.setBuseoName(rs.getString("BUSEO_NAME"));
			dto.setJikwiName(rs.getString("JIKWI_NAME"));
			dto.setBasicPay(rs.getInt("BASICPAY"));
			dto.setSudang(rs.getInt("SUDANG"));
			dto.setPay(rs.getInt("PAY"));
			
			result.add(dto);
		}
		rs.close();
		stmt.close();
		
		return result;
		
	}// end searchLists()
	
	
	// 지역 리스트 조회
	// 지역이름들을 반환하는 거니까 자료구조형은 String
	public ArrayList<String> searchCity() throws SQLException
	{
		ArrayList<String> result = new ArrayList<String>();
		
		Statement stmt = conn.createStatement();
		String sql = "SELECT CITY_NAME FROM TBL_CITY";
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next())
			result.add(rs.getString("CITY_NAME"));
		rs.close();
		stmt.close();
		
		return result;
	}
	
	// 부서 리스트 조회
	public ArrayList<String> searchBuseo() throws SQLException
	{
		ArrayList<String> result = new ArrayList<String>();
		
		Statement stmt = conn.createStatement();
		String sql = "SELECT BUSEO_NAME FROM TBL_BUSEO";
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next())
			result.add(rs.getString("BUSEO_NAME"));
		rs.close();
		stmt.close();
		
		return result;
	}
	
	// 직원 리스트 조회
	public ArrayList<String> searchJikwi() throws SQLException
	{
		ArrayList<String> result = new ArrayList<String>();
		
		Statement stmt = conn.createStatement();
		String sql = "SELECT JIKWI_NAME FROM TBL_JIKWI";
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next())
			result.add(rs.getString("JIKWI_NAME"));
		rs.close();
		stmt.close();
		
		return result;
	}
	
	
	// 직위에 따른 최소 기본급 검색
	public int searchBasicPay(String jikwi) throws SQLException
	{
		int result = 0;
		Statement stmt = conn.createStatement();
		String sql = String.format("SELECT MIN_BASICPAY FROM TBL_JIKWI WHERE JIKWI_NAME='%s'", jikwi);
		
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next())
		{
			result = rs.getInt("MIN_BASICPAY");
		}
		rs.close();
		stmt.close();
		
		return result;
				
	}
	
	// 4. 직원 수정
	// 넘겨주는건 멤버디티오 리턴값 int → 왜이런지 다시보기!!
	// 왜냐면 update니까 executeUpdate 돌아가는데 이거는 int 반환해줌! 그러니까 int해야함!
	public int modify(MemberDTO dto) throws SQLException
	{
		int result = 0;
		Statement stmt = conn.createStatement();
		String sql = String.format("UPDATE TBL_EMP"
				+ " SET EMP_NAME='%s',SSN='%s' ,IBSADATE=TO_DATE('%s','YYYY-MM-DD')"
				+ ",CITY_ID=(SELECT CITY_ID FROM TBL_CITY WHERE CITY_NAME='%s')"
				+ ",TEL='%s'"
				+ ",BUSEO_ID=(SELECT BUSEO_ID FROM TBL_BUSEO WHERE BUSEO_NAME='%s')"
				+ ",JIKWI_ID=(SELECT JIKWI_ID FROM TBL_JIKWI WHERE JIKWI_NAME='%s')"
				+ ",BASICPAY=%d,SUDANG=%d "
				+ "WHERE EMP_ID=%d" 
				, dto.getEmpName(),dto.getSsn(),dto.getIbsaDate()
				,dto.getCityName(), dto.getTel(),dto.getBuseoName(),dto.getJikwiName()
				,dto.getBasicPay(),dto.getSudang(),dto.getEmpId());
		
		result = stmt.executeUpdate(sql);
		stmt.close();

		return result;
	}
	
	// 5. 직원 삭제
	public int remove(int empId) throws SQLException
	{
		int result = 0;
		Statement stmt = conn.createStatement();
		String sql = String.format("DELETE FROM TBL_EMP WHERE EMP_ID=%d", empId);
		result = stmt.executeUpdate(sql);
		stmt.close();
		
		return result;
	}// end remove()
	

}


// 풀이보면서 느낀점
// PAY를 따로 DTO에서 계산식으로 만들지 않고 그냥 불러오고 오라클에서 계산처리를 해준방식으로 처리!
// 이러면 따로 dto.setPay(rs.getBasicpay("BASICPAY") + rs.getSudang("SUDANG")); 이렇게 처리 안해도 됨!


































