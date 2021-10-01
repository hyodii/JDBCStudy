/*=============================
 	Process.java
===============================*/

// 데이터베이스와 직접소통하지 않고 DAO하고만 소통하면됨 

package com.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class MemberProcess
{
	private MemberDAO dao;
	
	// 생성자 정의
	public MemberProcess()
	{
		MemberDAO dao = new MemberDAO();
	}
	
	// 1.직원 정보 입력
	/*
	직원 정보 입력 --------------------------------------------------
	이름 : 김진희
	주민등록번호(yymmdd-nnnnnnn) : 990320-2234567
	입사일(yyyy-mm-dd) : 2019-07-14
	지역(강원/경기/경남/경북/부산/서울/인천/전남/전북/제주/충남/충북/) : 경기
	전화번호 : 010-1111-1111
	부서(개발부/기획부/영업부/인사부/자재부/총무부/홍보부/) : 개발부
	직위(사장/전무/상무/이사/부장/차장/과장/대리/사원) : 대리
	기본급(최소 400000원 이상) : 500000
	수당 : 200000
	
	직원 정보 입력 완료~!!!
	-------------------------------------------------- 직원 정보 입력
	*/
	public void memberInsert()
	{
		try
		{
			dao.Connection();
			
			int count = dao.count();
			Scanner sc = new Scanner(System.in);
			
			do
			{
				System.out.println("직원 정보 입력 --------------------------------------------------");
				System.out.print("이름 : ");
				String empname = sc.next();
				
				if (empname.equals("."))
					break;
				
				System.out.print("주민등록번호(yymmdd-nnnnnnn) : ");
				String ssn = sc.next();
				System.out.print("입사일(yyyy-mm-dd) : ");
				String ibsadate = sc.next();
				System.out.print("지역(강원/경기/경남/경북/부산/서울/인천/전남/전북/제주/충남/충북/) : ");
				String city = sc.next();
				System.out.print("전화번호 : ");
				String tel = sc.next();
				System.out.print("부서(개발부/기획부/영업부/인사부/자재부/총무부/홍보부/) : ");
				String buseo = sc.next();
				System.out.print("직위(사장/전무/상무/이사/부장/차장/과장/대리/사원) : ");
				String jikwi = sc.next();
				System.out.print("기본급(최소 400000원 이상) : ");
				int basicpay = sc.nextInt();
				System.out.print("수당 : ");
				int sudang = sc.nextInt();
				System.out.println("-------------------------------------------------- 직원 정보 입력");
				
				// MemberDTO 객체 구성
				MemberDTO dto = new MemberDTO();
				dto.setEmpname(empname);
				dto.setSsn(ssn);
				dto.setIbsadate(ibsadate);
				dto.setCityid(city);
				dto.setTel(tel);
				dto.setBuseoid(buseo);
				dto.setJikweid(jikwi);
				dto.setBasicpay(basicpay);
				dto.setSudang(sudang);
				
				int result = dao.add(dto);
				
				if(result > 0)
					System.out.println("직원 정보 입력이 완료되었습니다.");
				
			} while (true);
			
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
	// 2.직원 전체 출력
	// 하위메뉴 있음
	public void memberSelectAll() throws SQLException
	{
		dao.Connection();
		int count = dao.count();
		
		System.out.println();
		System.out.printf("전체 인원 : %d명",count);
		System.out.println("사번	이름	주민번호	입사일	지역	전화번호	부서	직위	기본급	수당	급여");
		
		for (MemberDTO dto : dao.lists())
		{
			System.out.printf("%3s %4d %15s %10s %4s %14s %5s %5s %d %d %d"
					,dto.getEmpid(), dto.getEmpname(), dto.getSsn(), dto.getIbsadate(), dto.getCityid(), dto.getTel()
					,dto.getBuseoid(),dto.getJikweid(), dto.getBasicpay(), dto.getSudang(), dto.getSalary());
		}
		
		dao.close();
				
	}
	
	// 3.직원 검색 출력
	// 하위 메뉴 있음
	public void memberSearch() throws SQLException
	{
		// 검색할 이름 입력받기
		Scanner sc = new Scanner(System.in);
		System.out.println("검색할 메뉴");
		String menus = sc.next();
		
		//-- 필요할 경우 이 과정에서 프로그래밍적으로 검증(검사) 수행
		
		// 데이터베이스 연경
		dao.Connection();
		
		// dao 의 lists() 메소드 호출 → 매개변수로 검색할 이름을 문자열 형태로 넘겨주기
		ArrayList<MemberDTO> arrayList = dao.lists(/*값??*/);
		
		if (arrayList.size() > 0)
		{
			// 수신된 내용 출력
			System.out.println("사번	이름	주민번호	입사일	지역	전화번호	부서	직위	기본급	수당	급여");
			
			for (MemberDTO dto : arrayList)
			{
				System.out.printf("%3s %4d %15s %10s %4s %14s %5s %5s %d %d %d"
						,dto.getEmpid(), dto.getEmpname(), dto.getSsn(), dto.getIbsadate(), dto.getCityid(), dto.getTel()
						,dto.getBuseoid(),dto.getJikweid(), dto.getBasicpay(), dto.getSudang(), dto.getSalary());
			}
			
		}
		else	// 일치하는 이름이 없는 경우
		{
			// 수신된 내용이 없다는 상황 안내
			System.out.println("검색 결과가 존재하지 않습니다.");
		}
		
		// 데이터베이스 연결 종료
		dao.close();
	}
	
	// 4.직원 정보 수정
	public void memberUpdate() throws SQLException
	{
		// 수정할 대상의 번호 입력받기
		Scanner sc = new Scanner(System.in);
		System.out.print("수정할 번호를 입력하세요 : ");
		int sid = sc.nextInt();
		
		//-- 입력받은 번호로 체크해야 할 로직 적용 삽입 가능
		
		// 데이터베이스 연결
		 dao.Connection();
		 
		// 수정할 대상 수신 → 데이터 수정을 위해 대상 검색
		ArrayList<MemberDTO> arrayList = dao.lists(sid);
		
		if(arrayList.size() > 0)
		{
			// 수신된 내용 출력
			System.out.println("사번	이름	주민번호	입사일	지역	전화번호	부서	직위	기본급	수당	급여");
			
			for (MemberDTO dto : arrayList)
			{
				System.out.printf("%3s %4d %15s %10s %4s %14s %5s %5s %d %d %d"
						,dto.getEmpid(), dto.getEmpname(), dto.getSsn(), dto.getIbsadate(), dto.getCityid(), dto.getTel()
						,dto.getBuseoid(),dto.getJikweid(), dto.getBasicpay(), dto.getSudang(), dto.getSalary());
			}
			System.out.println();
			System.out.print("수정 데이터 입력(사번 이름 주민번호 입사일 지역 전화번호 부서 직위 기본급 수당 급여) : ");
			String id = sc.next();
			String name = sc.next();
			String ssn = sc.next();
			String ibsadate = sc.next();
			String city = sc.next();
			String tel = sc.next();
			String buseo = sc.next();
			String jikwi = sc.next();
			int basicpay = sc.nextInt();
			int sudang = sc.nextInt();
			
			
			// dao에서 우리가 수정하겠다고 정의한 modify 호출해야하는데
			// 모디파이가 필요로 하는건 → dto → 구성
			MemberDTO dto = new MemberDTO();
			dto.setEmpname(name);
			dto.setSsn(ssn);
			dto.setIbsadate(ibsadate);
			dto.setCityid(city);
			dto.setTel(tel);
			dto.setBuseoid(buseo);
			dto.setJikweid(jikwi);
			dto.setBasicpay(basicpay);
			dto.setSudang(sudang);
			dto.setEmpid(String.valueOf(sid));  
			// ★sid 는 원래 String인데 int로 받음! 그래서 String 으로 바꿔서 넘겨줘야함!!★
			
			int result = dao.modify(dto);
			
			if(result > 0)
			{
				System.out.println("수정이 완료되었습니다.");
			}
			
		}
		else
		{
			System.out.println("수정 대상이 존재하지 않습니다.");
		}
		
		// 데이터베이스 연결 종료
		dao.close();
		
		
	}
	
	
	// 5.직원 정보 삭제
	public void memberDelete() throws SQLException
	{
		// 삭제할 대상의 번호 입력받기
		Scanner sc = new Scanner(System.in);
		System.out.println("삭제할 번호를 입력하세요.");
		int empid = sc.nextInt();
		
		//-- 입력받은 번호로 체크해야 할 로직 적용 삽입 가능
		
		// 데이터베이스 연결
		dao.Connection();
		
		// 수정할 대상 수신 → 데이터 수정을 위해 대상 검색 (dao 의 lists() 메소드 호출)
		ArrayList<MemberDTO> arrayList = dao.lists(empid);
		
		if(arrayList.size() > 0)
		{
			// 수신된 내용 출력
			System.out.println("사번	이름	주민번호	입사일	지역	전화번호	부서	직위	기본급	수당	급여");
			
			for (MemberDTO dto : arrayList)
			{
				System.out.printf("%3s %4d %15s %10s %4s %14s %5s %5s %d %d %d"
						,dto.getEmpid(), dto.getEmpname(), dto.getSsn(), dto.getIbsadate(), dto.getCityid(), dto.getTel()
						,dto.getBuseoid(),dto.getJikweid(), dto.getBasicpay(), dto.getSudang(), dto.getSalary());
			}
			
			System.out.println(">> 정말로 삭제하시겠습니까?(Y/N) : ");
			String response = sc.next();
			
			if ( response.equals("y") || response.equals("Y"))
			{
				int result = dao.remove(empid);
				if(result > 0)
					System.out.println("삭제가 완료되었습니다.");
				
			}	
			else
				System.out.println("취소되었습니다");
		}
		else
			System.out.println("삭제 대상이 존재하지 않습니다.");
		
		// 데이터베이스 연결 종료
		dao.close();
		
		
	}
	
	
}

























