/*========================================
   			MemberDAO.java
   	- 데이터베이스 액션처리전용 객체 
==========================================*/
// 주요 속성 정의
// 메소드 정의
// 결과값으로 반환할 변수 선언 및 초기화
// 작업 객체 생성
// 쿼리문 준비
// 생성된 작업 객체를 활용하여 쿼리문 실행
// ResultSet 처리해야하면 처리 → while
// 최종 결과 값 반환



package com.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import com.util.DBConn;

public class MemberDAO
{
	// 주요 속성 정의
	private Connection conn;
	
	//데이터베이스에 연결 메소드 정의 (완성)
	public Connection Connection()
	{
		conn = DBConn.getConnection();
		return conn;
	}
	
	
	//직원 정보 입력 메소드 정의 (완성)
	public int add(MemberDTO dto) throws SQLException
	{
		int result = 0;
		Statement stmt = conn.createStatement();
		String sql = String.format("INSERT INTO TBL_EMP "
							+ "(EMP_ID, EMP_NAME, SSN, IBSADATE, CITY_ID, TEL, BUSEO_ID, JIKWI_ID, BASICPAY, SUDANG) "
							+ "VALUES (EMPSEQ.NEXTVAL, '%s', '%s', '%s', %d, '%s', %d, %d, %d, %d)"
							, dto.getEmpname(), dto.getSsn(), dto.getIbsadate(), dto.getCityid(), dto.getTel(), dto.getBuseoid()
							, dto.getJikweid(), dto.getBasicpay(), dto.getSudang());
		result = stmt.executeUpdate(sql);
		stmt.close();
		return result;	
		
	}// end add()
	
	
	
	//직원 전체 수 메소드 정의 (완성)
	public int count() throws SQLException
	{
		int result = 0;
		Statement stmt = conn.createStatement();
		String sql = "SELECT COUNT(*) AS COUNT FROM TBL_EMP";
		
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next())
			rs.getInt("COUNT");
		rs.close();
		stmt.close();
		return result;
		
	}// end count()
	
	
	//직원 전체 출력 메소드 정의
	// 1. 사번 정렬 2.이름 정렬 3.부서 정렬 4.직위 정렬 5.급여 내림차순 정렬
	public ArrayList<MemberDTO> lists() throws SQLException
	{
		ArrayList<MemberDTO> result = new ArrayList<MemberDTO>();
		
		Statement stmt = conn.createStatement();
		
		Scanner sc = new Scanner(System.in);
		
		do
		{
			System.out.println();
			System.out.println("1. 사번 정렬");
			System.out.println("2. 이름 정렬");
			System.out.println("3. 부서 정렬");
			System.out.println("4. 직위 정렬");
			System.out.println("5. 급여 내림차순 정렬");
			System.out.print(">> 선택(1~5, -1종료) :");
			String menus = sc.next();
			int menu = Integer.parseInt(menus);
			
			if(menu == -1)
			{
				System.out.println();
				System.out.println("프로그램이 종료되었습니다.");
				break;
			}
			
			switch (menu)
			{
				case 1:
					//직원 전체(1.사번 정렬)
					String sql = "SELECT EMP_ID,EMP_NAME,SSN,IBSADATE"
							+ ",(SELECT CITY_NAME FROM TBL_CITY C WHERE C.CITY_ID = T.CITY_ID) CITY_NAME"
							+ ",TEL"
							+ ",(SELECT BUSEO_NAME FROM TBL_BUSEO B WHERE B.BUSEO_ID = T.BUSEO_ID) BUSEO_NAME"
							+ ",(SELECT JIKWI_NAME FROM TBL_JIKWI J WHERE J.JIKWI_ID = T.JIKWI_ID) JIKWI_NAME"
							+ ",BASICPAY,SUDANG"
							+ ",(NVL(BASICPAY,0)*12)+SUDANG AS SALARY "
							+ "FROM TBL_EMP T "
							+ "ORDER BY T.EMP_ID";
					break;
	
				case 2:
					//직원 전체(2.이름 정렬)
					String sql = "SELECT EMP_ID,EMP_NAME,SSN,IBSADATE"
							+ ",(SELECT CITY_NAME FROM TBL_CITY C WHERE C.CITY_ID = T.CITY_ID) CITY_NAME"
							+ ",TEL"
							+ ",(SELECT BUSEO_NAME FROM TBL_BUSEO B WHERE B.BUSEO_ID = T.BUSEO_ID) BUSEO_NAME"
							+ ",(SELECT JIKWI_NAME FROM TBL_JIKWI J WHERE J.JIKWI_ID = T.JIKWI_ID) JIKWI_NAME"
							+ ",BASICPAY,SUDANG"
							+ ",(NVL(BASICPAY,0)*12)+SUDANG AS SALARY "
							+ "FROM TBL_EMP T "
							+ "ORDER BY T.EMP_NAME";
					break;
					
				case 3:
					//직원 전체(3.부서 정렬)
					String sql = "SELECT EMP_ID,EMP_NAME,SSN,IBSADATE"
							+ ",(SELECT CITY_NAME FROM TBL_CITY C WHERE C.CITY_ID = T.CITY_ID) CITY_NAME"
							+ ",TEL"
							+ ",(SELECT BUSEO_NAME FROM TBL_BUSEO B WHERE B.BUSEO_ID = T.BUSEO_ID) BUSEO_NAME"
							+ ",(SELECT JIKWI_NAME FROM TBL_JIKWI J WHERE J.JIKWI_ID = T.JIKWI_ID) JIKWI_NAME"
							+ ",BASICPAY,SUDANG"
							+ ",(NVL(BASICPAY,0)*12)+SUDANG AS SALARY "
							+ "FROM TBL_EMP T "
							+ "ORDER BY BUSEO_NAME";
					break;
					
				case 4:
					//직원 전체(4.직위 정렬)
					String sql = "SELECT EMP_ID,EMP_NAME,SSN,IBSADATE"
							+ ",(SELECT CITY_NAME FROM TBL_CITY C WHERE C.CITY_ID = T.CITY_ID) CITY_NAME"
							+ ",TEL"
							+ ",(SELECT BUSEO_NAME FROM TBL_BUSEO B WHERE B.BUSEO_ID = T.BUSEO_ID) BUSEO_NAME"
							+ ",(SELECT JIKWI_NAME FROM TBL_JIKWI J WHERE J.JIKWI_ID = T.JIKWI_ID) JIKWI_NAME"
							+ ",BASICPAY,SUDANG"
							+ ",(NVL(BASICPAY,0)*12)+SUDANG AS SALARY "
							+ "FROM TBL_EMP T "
							+ "ORDER BY JIKWI_NAME";
					break;
					
				case 5:
					//직원 전체(5.급여 내림차순 정렬)
					String sql = "SELECT EMP_ID,EMP_NAME,SSN,IBSADATE"
							+ ",(SELECT CITY_NAME FROM TBL_CITY C WHERE C.CITY_ID = T.CITY_ID) CITY_NAME"
							+ ",TEL"
							+ ",(SELECT BUSEO_NAME FROM TBL_BUSEO B WHERE B.BUSEO_ID = T.BUSEO_ID) BUSEO_NAME"
							+ ",(SELECT JIKWI_NAME FROM TBL_JIKWI J WHERE J.JIKWI_ID = T.JIKWI_ID) JIKWI_NAME"
							+ ",BASICPAY,SUDANG"
							+ ",(NVL(BASICPAY,0)*12)+SUDANG AS SALARY "
							+ "FROM TBL_EMP T "
							+ "ORDER BY SALARY DESC";
					break;
			}
			
			
		} while (true);
				
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next())
		{
			MemberDTO dto = new MemberDTO();
			
			dto.setEmpid(rs.getString("EMP_ID"));
			dto.setEmpname(rs.getString("EMP_NAME"));
			dto.setSsn(rs.getString("SSN"));
			dto.setIbsadate(rs.getString("IBSADATE"));
			dto.setCityid(rs.getString("CITY_NAME"));
			dto.setTel(rs.getString("TEL"));
			dto.setBuseoid(rs.getString("BUSEO_NAME"));
			dto.setJikweid(rs.getString("JIKWI_NAME"));
			dto.setBasicpay(rs.getInt("BASICPAY"));
			dto.setSudang(rs.getInt("SUDANG"));
			dto.setSalary(rs.getInt("BASICPAY"), rs.getInt("SUDANG"));
			
			result.add(dto);
			
		}
		rs.close();
		stmt.close();
		
	
		return result;
	}
	
	//직원 검색 출력 메소드 정의
	// int - 1.사번 검색 / String - 2.이름 검색 3.부서 검색 4.직위 검색
	public ArrayList<MemberDTO> lists(int empid)
	{
		ArrayList<MemberDTO> result = new ArrayList<MemberDTO>();
		Statement stmt = conn.createStatement();
		
		// 직원검색 출력(1.사번검색)
		String sql = String.format("SELECT E.EMP_ID,E.EMP_NAME,E.SSN,E.IBSADATE"
				+ ",C.CITY_NAME,TEL,B.BUSEO_NAME"
				+ ",J.JIKWI_NAME,E.BASICPAY,E.SUDANG"
				+ ",(NVL(E.BASICPAY,0)*12)+E.SUDANG AS SALARY "
				+ "FROM TBL_EMP E LEFT JOIN TBL_CITY C "
								+ "ON E.CITY_ID = C.CITY_ID "
								+ "JOIN TBL_BUSEO B "
								+ "ON E.BUSEO_ID = B.BUSEO_ID "
								+ "JOIN TBL_JIKWI J "
								+ "ON E.JIKWI_ID = J.JIKWI_ID "
				+ "WHERE EMP_ID =%d"
				, empid);
		
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next())
		{
			MemberDTO dto = new MemberDTO();
			
			dto.setEmpid(rs.getString("EMP_ID"));
			dto.setEmpname(rs.getString("EMP_NAME"));
			dto.setSsn(rs.getString("SSN"));
			dto.setIbsadate(rs.getString("IBSADATE"));
			dto.setCityid(rs.getString(columnIndex));	// ???
		}
		
		return result;
	}
	
	
	//직원 정보 수정 메소드 정의 (수정쿼리문 못만들었음!)
	// 사번 이름 주민번호 입사일 지역... 이걸 전부 수정해야하니까! → dto타입 받아와야함!!
	public int modify(MemberDTO dto) throws SQLException
	{
		int result = 0;
		Statement stmt = conn.createStatement();
		
		// 쿼리문 준비
		String sql = String.format(""
					, args);
		
		result = stmt.executeUpdate(sql);
		stmt.close();
		return result;
		
	}
	
	//직원 정보 삭제 메소드 정의 (완성)
	public int remove(int empid) throws SQLException
	{
		int result = 0;
		Statement stmt = conn.createStatement();
		
		// 쿼리문 준비
		String sql = String.format("DELETE FROM TBL_EMP WHERE EMP_ID = '%d'", empid);
		
		result = stmt.executeUpdate(sql);
		stmt.close();
		
		return result;		
	}
	
	//데이터베이스 종료 메소드 정의 (완성)
	public void close() throws SQLException
	{
		DBConn.close();
	}
	
	
	
}

// 문제풀이 하면서 의문
// 하위메뉴는 스위치를 써서 하는데 String sql 변수명 중복처리는 어떻게 하지? 
// 쿼리문 해결 못함 DTO에는 11개의 속성값있는데 여기서 필요한 건 직위ID가 아니라 직위NAME 이런건 어쩌지?
// update 쿼리문 작성 못해서 Process 진행 불가

// 입사DATE 는 TO_CAHR()변환해서 가지고 와야함!




















