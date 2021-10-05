/*===========================================
			MemberProcess.java
	-콘솔 기반 서브 메뉴 입출력 전용 클래스
=============================================*/



package com.test;

import java.util.ArrayList;
import java.util.Scanner;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class MemberProcess
{
	// 주요 속성 구성
	private MemberDAO dao;
	
	// 생성자 정의
	public MemberProcess()
	{
		dao = new MemberDAO();
	}
	
	// main 에서 스위치문 써서 전부 호출만 할꺼기 때문에 void로 구성!!
	// 1. 직원 정보 입력 메소드 정의
	// 원래는 안내메세지를 먼저 출력해주지만 여기서는
	// 안내하기 위해서 db 연결해서 지역,부서,직위리스트를 가져와야함!
	// 이런 생각의 순서를 알고있는게 먼저다! 코드보다!!
	public void memberInsert()
	{
		// 사용자에게 안내 메세지 출려(보여지는 화면 처리)
		Scanner sc = new Scanner(System.in);
		
		try
		{
			// 데이터베이스 연결
			dao.connection();
			
			/*
			"강원" + "/" + "경기"
			"강원/경기"
			"강원/경기/"
			"강원/경기/경남"
			"강원/경기/경남/경북"
			- 이런식으로 계속 뭔가 변경되는 문자열이라면!! 
			- Stringbuilder Stringbuffer쓰면됨!(Stringbuilder가 성능이 더 좋다!)
			*/
			// 지역 리스트 구성
			ArrayList<String> citys = dao.searchCity();		// 지역들이니까 citys!로 이름지음
			StringBuilder cityStr = new StringBuilder();
			for (String city : citys)
			{
				cityStr.append(city + "/");
			}
			//-- "강원/경기/경남/경북/부산/서울/인천/전남/전북/제주/충남/충북/" 의 스트링빌더객체(문자열)가 만들어짐!
			
			
			// 부서 리스트 구성
			ArrayList<String> buseos = dao.searchBuseo();
			StringBuilder buseoStr = new StringBuilder();
			for (String buseo : buseos)
				buseoStr.append(buseo + "/");
			//-- 개발부/기획부/영업부/인사부/자재부/총무부/홍보부/ 의 스트링빌더객체 완성!
			
			// 직위 리스트 구성
			ArrayList<String> jikwis = dao.searchJikwi();
			StringBuilder jikwiStr = new StringBuilder();
			for (String jikwi : jikwis)
				jikwiStr.append(jikwi + "/");
			//- 사장/전무/상무/이사/부장/차장/과장/대리/사원/ 의 스트링빌더객체 완성!
			
			
			// 사용자에게 보여지는 화면 처리
			/*
			직원 정보 입력 --------------------------------------------------
			이름 : 김진희
			주민등록번호(yymmdd-nnnnnnn) : 990320-2234567
			입사일(yyyy-mm-dd) : 2019-07-14
			지역(강원/경기/경남/경북/부산/서울/인천/전남/전북/제주/충남/충북/) : 경기
			전화번호 : 010-1111-1111
			부서(개발부/기획부/영업부/인사부/자재부/총무부/홍보부/) : 개발부
			직위(사장/전무/상무/이사/부장/차장/과장/대리/사원/) : 대리
			기본급(최소 400000원 이상) : 500000
			수당 : 200000
			
			직원 정보 입력 완료~!!!
			-------------------------------------------------- 직원 정보 입력
			*/
			
			System.out.println("직원 정보 입력 --------------------------------------------------");
			System.out.print("이름 : ");
			String empName = sc.next();
			System.out.print("주민등록번호(yymmdd-nnnnnnn) : ");
			String ssn = sc.next();
			System.out.print("입사일(yyyy-mm-dd) : ");
			String ibsaDate = sc.next();
			System.out.printf("지역(%s) : ",cityStr.toString());	// check~!!★
			String cityName = sc.next();
			System.out.print("전화번호 : ");
			String tel = sc.next();
			System.out.printf("부서(%s) : ", buseoStr.toString());
			String buseoName = sc.next();
			System.out.printf("직위(%s) : ", jikwiStr.toString());
			String jikwiName = sc.next();
			System.out.printf("기본급(최소 %d원 이상) : ",dao.searchBasicPay(jikwiName));
			int basicPay = sc.nextInt();	// check~!!★
			System.out.print("수당 : ");
			int sudang = sc.nextInt();		// check~!!★
			System.out.println("");
			
			MemberDTO dto = new MemberDTO();
			dto.setEmpName(empName);
			dto.setSsn(ssn);
			dto.setIbsaDate(ibsaDate);
			dto.setCityName(cityName);
			dto.setTel(tel);
			dto.setBuseoName(buseoName);
			dto.setJikwiName(jikwiName);
			dto.setBasicPay(basicPay);
			dto.setSudang(sudang);
			
			int result = dao.add(dto);
			if(result > 0)
				System.out.println("직원 정보 입력 완료~!!!");
			System.out.println("-------------------------------------------------- 직원 정보 입력");
			
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally
		{
			try
			{
				dao.close();
				
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		
	}// end memberInsert()
	
	
	
	// 2. 직원 전체 출력 메소드 정의
	public void memberLists()
	{
		Scanner sc = new Scanner(System.in);
		
		// 서브 메뉴 출력(안내)
		System.out.println();
		System.out.println("1. 사번 정렬");				// EMP_ID
		System.out.println("2. 이름 정렬");				// EMP_NAME
		System.out.println("3. 부서 정렬");				// BUSEO_NAME
		System.out.println("4. 직위 정렬");				// JIKWI_NAME
		System.out.println("5. 급여 내림차순 정렬");	// PAY DESC
		System.out.print(">> 항목 선택(1~5, -1종료) : ");
		String menuStr = sc.next();
		
		try
		{
			int menu = Integer.parseInt(menuStr);
			if(menu==-1)
				return;		// 여기서의 return 은 memberLists의 종료! → 원래의 메뉴로 돌아감!
			
			String key = "";
			switch (menu)
			{
				case 1:
					key = "EMP_ID";
					break;
	
				case 2:
					key = "EMP_NAME";
					break;
					
				case 3:
					key = "BUSEO_NAME";
					break;
					
				case 4:
					key = "JIKWI_NAME";
					break;
				case 5:
					key = "PAY DESC";
					break;
			}
			
			// 데이터베이스 연결
			dao.connection();
			
			// 직원 리스트 출력
			System.out.println();
			System.out.printf("전체 인원 : %d명\n", dao.memberCount());
			System.out.println("사번	이름	주민번호	입사일	지역	전화번호	부서	직위	기본급	수당	급여");
			ArrayList<MemberDTO> memList = dao.lists(key);
			for (MemberDTO memberDTO : memList)
			{
				System.out.printf("%5d %4s %14s %10s %3s %12s %4s %4s %8d %8d %8d\n"
								,memberDTO.getEmpId(),memberDTO.getEmpName()
								,memberDTO.getSsn(),memberDTO.getIbsaDate()
								,memberDTO.getCityName(),memberDTO.getTel()
								,memberDTO.getBuseoName(),memberDTO.getJikwiName()
								,memberDTO.getBasicPay(),memberDTO.getSudang(),memberDTO.getPay());
			}
			
			
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally
		{
			try
			{
				dao.close();
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		
	}// end memberList()
	
	
	
	// 3. 직원 검색 출력 메소드 정의
	public void memberSearch()
	{
		Scanner sc = new Scanner(System.in);
		
		// 서브 메뉴 구성
		System.out.println();
		System.out.println("1. 사번 검색");		// EMP_ID
		System.out.println("2. 이름 검색");		// EMP_NAME
		System.out.println("3. 부서 검색");		// BUSEO_NAME
		System.out.println("4. 직위 검색");		// JIKWI_NAME
		System.out.print(">> 항목 선택(1~4,-1종료) : ");
		String menuStr = sc.next();
		
		try
		{
			int menu = Integer.parseInt(menuStr);
			if(menu == -1)
				return;
			String key = "";
			String value = "";
			switch (menu)
			{
				case 1:
					key = "EMP_ID";
					System.out.print("검색할 사원번호 입력 : ");
					value = sc.next();
					break;
				case 2:
					key = "EMP_NAME";
					System.out.print("검색할 사원이름 입력 : ");
					value = sc.next();
					break;
				case 3:
					key = "BUSEO_NAME";
					System.out.print("검색할 부서명 입력 : ");
					value = sc.next();
					break;
				case 4:
					key = "JIKWI_NAME";
					System.out.print("검색할 직위명 입력 : ");
					value = sc.next();
					break;
			}
			// 데이터베이스 연결
			dao.connection();
			
			// 검색 결과 출력
			System.out.println();
			System.out.printf("검색 인원 : %d\n명",dao.memberCount(key, value));
			System.out.println("사번	이름	주민번호	입사일	지역	전화번호	부서	직위	기본급	수당	급여");
			ArrayList<MemberDTO> memList = dao.searchLists(key, value);
			for (MemberDTO memberDTO : memList)
			{
				System.out.printf("%5d %4s %14s %10s %3s %12s %4s %4s %8d %8d %8d\n"
						,memberDTO.getEmpId(),memberDTO.getEmpName()
						,memberDTO.getSsn(),memberDTO.getIbsaDate()
						,memberDTO.getCityName(),memberDTO.getTel()
						,memberDTO.getBuseoName(),memberDTO.getJikwiName()
						,memberDTO.getBasicPay(),memberDTO.getSudang(),memberDTO.getPay());
			}
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally 
		{
			try
			{
				dao.close();
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		
		
	}// end memberSearch()
	
	
	
	// 4. 직원 정보 수정 메소드 정의
	public void memberUpdate()
	{
		Scanner sc = new Scanner(System.in);
		
		try
		{
			// 수정할 대상 입력받기
			System.out.print("수정할 직원의 사원번호 입력 : ");
			String value = sc.next();
			
			// 데이터베이스 연결
			dao.connection();
			
			ArrayList<MemberDTO> members = dao.searchLists("EMP_ID", value);
			
			if (members.size() > 0)
			{
				// 지역 리스트 구성
				ArrayList<String> citys = dao.searchCity();
				StringBuilder cityStr = new StringBuilder();
				for (String city : citys)
					cityStr.append(city + "/");
				
				// 부서 리스트 구성
				ArrayList<String> buseos = dao.searchBuseo();
				StringBuilder buseoStr = new StringBuilder();
				for (String buseo : buseos)
					buseoStr.append(buseo + "/");
				
				// 직우 리스트 구성
				ArrayList<String> jikwis = dao.searchJikwi();
				StringBuilder jikwiStr = new StringBuilder();
				for (String jikwi : jikwis)
					jikwiStr.append(jikwi + "/");
				
				// 사용자에게 보여지는 화면 처리
				/*  하이픈(-) 없이 진행된다면! 수정하는 값 1개여도 전부 다 수정해야함!
				직원 정보 수정 --------------------------------------------------
				기존 이름 : 김진희
				수정 이름 : - 			// ← 그대로 쓸꺼면 - 쓰기로! check~!!
				기존 주민등록번호(yymmdd-nnnnnnn) : 990320-2234567
				수정 주민등록번호(yymmdd-nnnnnnn) : 
				기존입사일(yyyy-mm-dd) : 2019-07-14
				수정입사일(yyyy-mm-dd) : 
				기존 지역: 경기
				수정 지역(강원/경기/경남/경북/부산/서울/인천/전남/전북/제주/충남/충북/) :
				기존 전화번호 : 010-1111-1111
				수정 전화번호 : 
				기존 부서 : 개발부
				수정 부서(개발부/기획부/영업부/인사부/자재부/총무부/홍보부/) :
				기존 직위 : 대리
				수정 직위(사장/전무/상무/이사/부장/차장/과장/대리/사원/) :
				기존 기본급 : 500000
				수정 기본급(최소 400000원 이상) :
				기존 수당 : 200000
				수정 수당 : 
				
				직원 정보 수정 완료~!!!
				-------------------------------------------------- 직원 정보 수정
				*/
				MemberDTO mMember = members.get(0);		// emp_id 받으면 어차피 1명검색됨! 그래서 members 어레이리스트니까 0 받아와! (오버로딩)
				int mEmpId = mMember.getEmpId();
				String mEmpName = mMember.getEmpName();
				String mSsn = mMember.getSsn();
				String mIbsaDate = mMember.getIbsaDate();
				String mCityName = mMember.getCityName();
				String mTel = mMember.getTel();
				String mBuseoName = mMember.getBuseoName();
				String mJikwiName = mMember.getJikwiName();
				int mBasicPay = mMember.getBasicPay();
				int mSudang = mMember.getSudang();
				
				System.out.println();
				System.out.println("직원 정보 수정 --------------------------------------------------");
				System.out.printf("기존 이름 : %s\n", mEmpName);
				System.out.print("수정 이름 : ");
				String empName = sc.next();
				if(empName.equals("-"))
					empName = mEmpName;
				
				System.out.printf("기존 주민등록번호(yymmdd-nnnnnnn) : %s\n",mSsn);
				System.out.print("수정 주민등록번호(yymmdd-nnnnnnn) :");
				String empSsn = sc.next();
				if(empSsn.equals("-"))
					empSsn = mSsn;
				
				System.out.printf("기존 입사일(yyyy-mm-dd) : %s\n",mIbsaDate);
				System.out.print("수정 입사일(yyyy-mm-dd) : ");
				String ibsaDate = sc.next();
				if(ibsaDate.equals("-"))
					ibsaDate = mIbsaDate;
				
				System.out.printf("기존 지역 : %s\n",mCityName);
				System.out.printf("수정 지역(%s) : ", cityStr);
				String cityName = sc.next();
				if (cityName.equals("-"))
					cityName = mCityName;
				
				System.out.printf("기존 전화번호 : %s%n",mTel);
				System.out.print("수정 전화번호 : ");
				String tel = sc.next();
				if (tel.equals("-"))
					tel = mTel;
				
				System.out.printf("기존 부서 : %s%n",mBuseoName);
				System.out.printf("수정 부서(%s) : ",buseoStr);
				String buseoName = sc.next();
				if(buseoName.equals("-"))
					buseoName = mBuseoName;
				
				System.out.printf("기존 직위 : %s%n", mJikwiName);
				System.out.printf("수정 직위(%s) : ",jikwiStr);
				String jikwiName = sc.next();
				if(jikwiName.equals("-"))
					jikwiName = mJikwiName;
				
				System.out.printf("기존 기본급 %d%n",mBasicPay);
				System.out.printf("수정 기본급(최소 %d이상) : ",dao.searchBasicPay(jikwiName));		//★ check~!!
				String basicPayStr = sc.next();		// ★ check~!! int로 받아오면 안됨! "-" 처리 해야하기 때문에!!
				int basicPay = 0;								// ★ check~!!
				if(basicPayStr.equals("-"))
					basicPay = mBasicPay;
				else
					basicPay = Integer.parseInt(basicPayStr);	// ★ check~!!
				
				System.out.printf("기존 수당 :%d%n",mSudang);
				System.out.print("수정 수당 : ");
				String sudangStr = sc.next();
				int sudang = 0;
				if(sudangStr.equals("-"))
					sudang = mSudang;
				else
					sudang = Integer.parseInt(sudangStr);
				
				//-- 여기까지 변경할 속성값 다 받아옴!
				
				// 다시 DTO 구성해서 modify메소드 호출하기!
				// 새로 입력받은(수정한) 내용을 통해 DTO 구성
				MemberDTO member = new MemberDTO();
				member.setEmpId(mEmpId);
				member.setEmpName(empName);
				member.setSsn(empSsn);
				member.setIbsaDate(ibsaDate);
				member.setCityName(cityName);
				member.setTel(tel);
				member.setBuseoName(buseoName);
				member.setJikwiName(jikwiName);
				member.setBasicPay(basicPay);
				member.setSudang(sudang);
				
				int result = dao.modify(member);
				if(result > 0)
					System.out.println("직원 정보 수정 완료~!!!");
				System.out.println("-------------------------------------------------- 직원 정보 수정");
				
			}
			else 
			{
				System.out.println("수정 대상을 검색하지 못했습니다.");
			}
			
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally
		{
			try
			{
				dao.close();
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		
		
	}// end memberUpdate()
	
	// 5. 직원 정보 삭제 메소드 정의
	public void memberDelete()
	{
		Scanner sc = new Scanner(System.in);
		
		try
		{
			System.out.print("삭제할 직원의 사원번호 입력 : ");
			String value = sc.next();
			
			// 직원 정보 확인 후 삭제 여부 결정
			
			dao.connection();
			ArrayList<MemberDTO> members = dao.searchLists("EMP_ID", value);
			
			if(members.size() > 0)
			{
				System.out.println();
				System.out.println("사번	이름	주민번호	입사일	지역	전화번호	부서	직위	기본급	수당	급여");
				for (MemberDTO memberDTO : members)
				{
					System.out.printf("%5d %4s %14s %10s %3s %12s %4s %4s %8d %8d %8d\n"
							,memberDTO.getEmpId(),memberDTO.getEmpName()
							,memberDTO.getSsn(),memberDTO.getIbsaDate()
							,memberDTO.getCityName(),memberDTO.getTel()
							,memberDTO.getBuseoName(),memberDTO.getJikwiName()
							,memberDTO.getBasicPay(),memberDTO.getSudang(),memberDTO.getPay());
				}
				System.out.print("\n정말 삭제하시겠습니까(Y/N) : ");
				String response = sc.next();
				if(response.equals("Y")|| response.equals("y"))
				{
					int result = dao.remove(Integer.parseInt(value));		// ★ check~!!
					if(result > 0)
						System.out.println("직원 정보가 정상적으로 삭제되었습니다.");
				}
			}
			else
			{
				System.out.println("삭제 대상을 검색하지 못하였습니다.");
			}
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally
		{
			try
			{
				dao.close();
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		
	}// end memberDelete()
	
	
	
}// end class



/*
참고: ResultSet의 next()함수라 get…() 함수를 사용할땐 항상 Connection이 열려있어야 한다.
그렇지 않을경우 예외 발생 java.sql.SQLRecoverableException: 접속 종료
conn.close()함수로 강제로 끊으면 rs의 모든 함수를 즉시 사용불가능되지만 서비스에서 내리면 조금 출력하다가 예외 발생.
*/
















