/*=============================
 	ScoreProcess.java
===============================*/

package com.test;

import java.util.ArrayList;
import java.util.Scanner;

public class ScoreProcess
{
   // 주요 속성 구성 → 데이터베이스 액션 처리 전담 객체 → ScoreDAO
   private ScoreDAO dao = new ScoreDAO();
   
   
   // 성적 입력 기능
   public void sungjukInsert()
   {
      try
      {
         // 데이터베이스 연결
         dao.connection();
         
         int count = dao.count();
         Scanner sc = new Scanner(System.in);
         
         do
         {
            System.out.println();
            System.out.printf("%d번 학생 성적 입력(이름 국어 영어 수학): ", (++count));
            String name = sc.next();
            
            if (name.equals("."))
               break;
            
            int kor = sc.nextInt();
            int eng = sc.nextInt();
            int mat = sc.nextInt();
            
            // ScoreDTO 객체 구성
            ScoreDTO dto = new ScoreDTO();
            dto.setName(name);
            dto.setKor(kor);
            dto.setEng(eng);
            dto.setMat(mat);
            
            // dao 의 add() 메소드 호출
            int result = dao.add(dto);
            
            if (result > 0)
               System.out.println("성적 입력이 완료되었습니다.");            
            
         } while (true);
         
         // 데이터베이스 연결 종료
         dao.close();
         
      } catch (Exception e)
      {
         System.out.println(e.toString());
      }
      
   }//end sungjukInsert()
   
   
   // 성적 전체 출력 기능
   public void sungjukSelectAll()
   {
      try
      {
         // dao 의 connection() 메소드 호출 → 데이터베이스 연결
         dao.connection();
         
         // dao 의 count() 메소드 호출 → 인원 수 확인
         int count = dao.count();
         
         System.out.println();
         System.out.printf("전체 인원: %d명\n", count);
         System.out.println("번호  이름  국어  영어  수학  총점  평균  석차");
         
         for (ScoreDTO dto : dao.lists())
         {
            System.out.printf("%3s %4s %4d %5d %5d %5d %5.1f %5d\n"
                        , dto.getSid(), dto.getName()
                        , dto.getKor(), dto.getEng(), dto.getMat()
                        , dto.getTot(), dto.getAvg(), dto.getRank());
         }
         
         // dao 의 close() 메소드 호출 → 데이터베이스 연결 종료
         dao.close();
         
      } catch (Exception e)
      {
         System.out.println(e.toString());
      }
      
   } // end sungjukSelectAll()
   
   
   // 이름 검색 출력 기능
   public void sungjukSearchName()
   {
      try
      {
         // 검색할 이름 받기
         Scanner sc = new Scanner(System.in);
         System.out.print("\n검색할 이름 입력: ");
         String name = sc.next();
         
         //-- 필요할 경우 이 과정에서 프로그래밍적으로 검증(검사) 수행
         
         // 데이터베이스 연결
         dao.connection();
         
         // dao 의 lists() 메소드 호출 → 매개변수로 검색할 이름을 문자열 형태로 넘겨주기
         ArrayList<ScoreDTO> arrayList = dao.lists(name);
         
         if (arrayList.size() > 0)
         {
            // 수신된 내용 출력
            System.out.println("번호  이름  국어  영어  수학  총점  평균  석차");
            
            for (ScoreDTO dto : arrayList)
            {
               System.out.printf("%3s %4s %4d %5d %5d %5d %5.1f %5d\n"
                           , dto.getSid(), dto.getName()
                           , dto.getKor(), dto.getEng(), dto.getMat()
                           , dto.getTot(), dto.getAvg(), dto.getRank());
            }
            
         }
         else
            System.out.println("검색 결과가 존재하지 않습니다.");
         
         // 데이터베이스 종료
         dao.close();
         
      } catch (Exception e)
      {
         System.out.println(e.toString());
      }
      
   } // end sungjukSearchName()
   
   
   // 성적 수정 기능
   public void sungjukUpdate()
   {
      try
      {
         // 수정할 대상의 번호 입력받기
         Scanner sc = new Scanner(System.in);
         System.out.print("수정할 번호를 입력하세요: ");
         int sid = sc.nextInt();
         
         //-- 입력받은 번호로 체크해야 할 로직 적용 삽입 가능
         
         // 데이터 베이스 연결
         dao.connection();
         
         // 수정할 대상 수신 → 데이터 수정을 위해 대상 검색
         ArrayList<ScoreDTO> arrayList = dao.lists(sid);
         
         if (arrayList.size() > 0)
         {
            // 수신된 내용 출력
            System.out.println("번호  이름  국어  영어  수학  총점  평균  석차");
            
            for (ScoreDTO dto : arrayList)
            {
               System.out.printf("%3s %4s %4d %5d %5d %5d %5.1f %5d\n"
                           , dto.getSid(), dto.getName()
                           , dto.getKor(), dto.getEng(), dto.getMat()
                           , dto.getTot(), dto.getAvg(), dto.getRank());
            }
            
            // 변경할 내용 입력
            System.out.println();
            System.out.print("수정 데이터 입력(이름 국어 영어 수학): ");
            String name = sc.next();
            int kor = sc.nextInt();
            int eng = sc.nextInt();
            int mat = sc.nextInt();
            
            // dto 구성
            ScoreDTO dto = new ScoreDTO();
            dto.setName(name);
            dto.setKor(kor);
            dto.setEng(eng);
            dto.setMat(mat);
            dto.setSid(String.valueOf(sid));
            
            int result = dao.modify(dto);
            if (result > 0)
               System.out.println("수정이 완료되었습니다.");
            
         }
         else
            System.out.println("수정할 대상이 존재하지 않습니다.");
         
         // 데이터베이스 연결 종료
         dao.close();
         
      } catch (Exception e)
      {
         System.out.println(e.toString());
      }
      
   } // end sungjukUpdate()
   
      
   // 성적 삭제 기능
   public void sungjukDelete()
   {
      try
      {
         // 삭제할 대상의 번호 입력받기
         Scanner sc = new Scanner(System.in);
         System.out.print("삭제할 번호를 입력하세요: ");
         int sid = sc.nextInt();
         
         //-- 입력받은 번호로 체크해야 할 로직 적용 삽입 가능
         
         // 데이터베이스 연결
         dao.connection();
         
         // 삭제할 대상 수신 → 데이터 삭제를 위해 대상 검색
         ArrayList<ScoreDTO> arrayList = dao.lists(sid);
         
         if (arrayList.size() > 0)
         {
            // 수신된 내용 출력
            System.out.println("번호  이름  국어  영어  수학  총점  평균  석차");
            
            for (ScoreDTO dto : arrayList)
            {
               System.out.printf("%3s %4s %4d %5d %5d %5d %5.1f %5d\n"
                           , dto.getSid(), dto.getName()
                           , dto.getKor(), dto.getEng(), dto.getMat()
                           , dto.getTot(), dto.getAvg(), dto.getRank());
            }
            
            System.out.print(">> 정말 삭제하시겠습니까? (Y/N): ");
            String response = sc.next();
            
            if(response.equals("Y") || response.equals("y"))
            {   
               int result = dao.remove(sid);
               if (result > 0)
                  System.out.println("삭제가 완료되었습니다.");
            }
            else
               System.out.println("취소되었습니다.");
         }
         else 
            System.out.println("삭제할 대상이 존재하지 않습니다.");
         
         
      } catch (Exception e)
      {
         System.out.println(e.toString());
      }
      
   } //end sungjukDelete()
}


/*
package com.test;

import java.util.ArrayList;
import java.util.Scanner;

public class ScoreProcess
{
	// 주요 속성 구성
	private ScoreDAO dao;
	
	public ScoreProcess()
	{
		dao = new ScoreDAO();
	}
	
	// 1. 성적 입력 기능
	public void sungjukInsert()
	{
		try
		{	// 데이터베이스 연결
			dao.connection();
			
			// 레코드 수 확인
			int count = dao.count();
			Scanner sc = new Scanner(System.in);
			
			do
			{
				System.out.println();
				System.out.printf("%d번 학생 성적 입력(이름 국어 영어 수학) : ",(++count));		// 번호를 받는것이 맞나?
				String name = sc.next();
				
				if(name.equals("."))
					break;
				//int sid = sc.nextInt();
				int kor = sc.nextInt();
				int eng = sc.nextInt();
				int mat = sc.nextInt();
				
				ScoreDTO dto = new ScoreDTO();
				//dto.setSid(String.valueOf(sid));
				dto.setName(name);
				dto.setKor(kor);
				dto.setEng(eng);
				dto.setMat(mat);
				
				int result = dao.add(dto);
				
				if (result > 0)
					System.out.println("성적 입력이 완료되었습니다~!!");
				
			} while (true);
			
			// 데이터베이스 연결 종료
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
	// 2. 성적 전체 출력
	public void sungjukSelectAll()
	{
		try
		{
			dao.connection();
			
			int count = dao.count();
			
			System.out.println();
			System.out.printf("전체 인원 : %d명\n", count);
			System.out.println("번호	이름	국어	영어	수학	총점	평균	석차");
			
			for (ScoreDTO dto : dao.lists())
			{
				System.out.printf("%3s	%4s	%4d	%5d	%5d	%5d	%5.1f	%5d\n"
								,dto.getSid(), dto.getName(), dto.getKor()
								,dto.getEng(),dto.getMat(),dto.getTot()
								,dto.getAvg(), dto.getRank());
			}
			
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
	

	// 3. 이름 검색 출력
	public void sungjukSearchName()
	{
		try
		{
			Scanner sc = new Scanner(System.in);
			System.out.print("검색할 이름 입력 : ");
			String name = sc.next();
			
			dao.connection();
			ArrayList<ScoreDTO> arrayList = dao.lists(name);
			
			if(arrayList.size() > 0)
			{
				for (ScoreDTO dto : dao.lists())
				{
					System.out.printf("%3s	%4s	%4d	%5d	%5d	%5d	%5.1f	%5d\n"
									,dto.getSid(), dto.getName(), dto.getKor()
									,dto.getEng(),dto.getMat(),dto.getTot()
									,dto.getAvg(), dto.getRank());
				}
			}
			else
				System.out.println("검색 결과가 존재하지 않습니다.");
			
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
	// 4. 성적 수정
	public void sungjukUpdate()
	{
		try
		{
			Scanner sc = new Scanner(System.in);
			System.out.print("수정할 번호를 입력하세요 : ");
			int sid = sc.nextInt();
			
			dao.connection();
			ArrayList<ScoreDTO> arrayList = dao.lists(sid);
			if(arrayList.size() > 0)
			{
				for (ScoreDTO dto : dao.lists())
				{
					System.out.printf("%3s	%4s	%4d	%5d	%5d	%5d	%5.1f	%5d\n"
									,dto.getSid(), dto.getName(), dto.getKor()
									,dto.getEng(),dto.getMat(),dto.getTot()
									,dto.getAvg(), dto.getRank());
				}
				System.out.println();
				System.out.print("수정 데이터 입력(이름 국어 영어 수학) : ");
				String name = sc.next();
				int kor = sc.nextInt();
				int eng = sc.nextInt();
				int mat = sc.nextInt();
				
				ScoreDTO dto = new ScoreDTO();
				dto.setName(name);
				dto.setKor(kor);
				dto.setEng(eng);
				dto.setMat(mat);
				dto.setSid(String.valueOf(sid));
				//dto.setSid(sid);
				
				int result = dao.modify(dto);
				if(result > 0)
					System.out.println("수정이 완료되었습니다.");
			}
			else
				System.out.println("수정할 대상이 존재하지 않습니다.");
			
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
	
	
	// 5. 성적 삭제
	public void sungjukDelete()
	{
		try
		{
			Scanner sc = new Scanner(System.in);
			System.out.print("삭제할 번호를 입력하세요 : ");
			String sid = sc.next();
			
			dao.connection();
			
			ArrayList<ScoreDTO> arrayList = dao.lists(sid);
			
			if(arrayList.size() > 0)
			{
				for (ScoreDTO dto : dao.lists())
				{
					System.out.printf("%3s	%4s	%4d	%5d	%5d	%5d	%5.1f	%5d\n"
									,dto.getSid(), dto.getName(), dto.getKor()
									,dto.getEng(),dto.getMat(),dto.getTot()
									,dto.getAvg(), dto.getRank());
				}
				System.out.println(">> 정말 삭제하시겠습니까(Y/N) : ");
				String response = sc.next();
				if(response.equals("Y")||response.equals("y"))
				{
					int result = dao.remove(sid);
					if (result > 0)
						System.out.println("삭제가 완료되었습니다.");
				}
				else
					System.out.println("취소되었습니다.");
			}
			else
				System.out.println("삭제 대상이 존재하지 않습니다.");
			
			dao.close();
					
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
	
	
}
*/