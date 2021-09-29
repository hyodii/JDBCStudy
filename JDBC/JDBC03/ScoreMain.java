/*========================================
   			ScoreMain.java 
   	- ScoreDAO, ScoreDTO 클래스 활용
==========================================*/

/*
○ 성적 처리 프로그램을 구현 -> 데이터베이스 연동 -> ScoreDAO, ScoreDTO 활용

  여러 명의 이름, 국어점수, 영어점수, 수학점수를 입력받아
  총점, 평균을 연산하여 출력하는 프로그램을 구현한다.
  출력 시 번호(이름, 총점 등) 오름차순 정렬하여 출력한다.

실행 예)

1번 학생 성적 입력(이름 국어 영어 수학) : 김진령 80 75 60
2번 학생 성적 입력(이름 국어 영어 수학) : 이윤서 100 90 80
3번 학생 성적 입력(이름 국어 영어 수학) : 송해덕 80 85 80
4번 학생 성적 입력(이름 국어 영어 수학) : .
 
 ------------------------------------------------------
 번호	이름	국어	영어	수학	총점	평균
 ------------------------------------------------------
 1   김진령       80       75      60   xxx     xx.x
 2   이윤서      100       90      80   xxx     xx.x
 3   송해덕       80       85      80   xxx     xx.x
 ------------------------------------------------------
 */



package com.test;

import java.util.Scanner;

import com.util.DBConn;

public class ScoreMain
{
	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		
		try
		{
			// DAO 인스턴스 생성
			ScoreDAO dao = new ScoreDAO();
			int count = dao.count();
			
			do
			{
				System.out.printf("%d번 학생 성적 입력(이름 국어 영어 수학) : ",(++count));
				String name = sc.next();
				if(name.equals("."))
					break;
				
				int kor = sc.nextInt();
				int eng = sc.nextInt();
				int mat = sc.nextInt();
				
				// 입력받은 값으로 ScoreDTO 객체 구성
				ScoreDTO dto = new ScoreDTO();
				dto.setName(name);
				dto.setKor(kor);
				dto.setEng(eng);
				dto.setMat(mat);
				dto.setTot(kor, eng, mat);
				dto.setAvg(kor, eng, mat);
				
				// ScoreDTO 객체를 DB 에 입력
				// dao에 있는 add메소드를 불러오는 것 
				dao.add(dto);
				
				/*
				int result = dao.add(dto);	// ??
				
				if(result > 0)
					System.out.println(">> 회원 정보 입력 완료~!!!");
					
				1번 학생 성적 입력(이름 국어 영어 수학) : 김진령 80 75 6
				>> 회원 정보 입력 완료~!!!
				2번 학생 성적 입력(이름 국어 영어 수학) : 이윤서 100 90 80
				>> 회원 정보 입력 완료~!!!
				         :
				*/
				
				
			} while (true);
			
			
			System.out.println();
			System.out.println("------------------------------------------------------");
			System.out.printf("전체 회원 수 : %d\n",dao.count());
			System.out.println("------------------------------------------------------");
			System.out.printf("번호	이름	국어	영어	수학	총점	평균\n");
			
			// 리스트 가져와 출력
			for (ScoreDTO obj : dao.lists())
			{
				System.out.printf("%3d\t %3s\t %3d\t %3d\t %3d\t %3d\t %3f\t\n"
								,obj.getSid(), obj.getName(), obj.getKor(), obj.getEng(), obj.getMat(),obj.getTot(),obj.getAvg());
			}		
			
			System.out.println("------------------------------------------------------");
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally
		{
			try
			{
				DBConn.close();
				System.out.println("데이터베이스 연결 닫힘~!!!");
				System.out.println("프로그램 종료!!!");
				
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}// finally end
		
		
	}// main end
}// class end

/*
1번 학생 성적 입력(이름 국어 영어 수학) : 김진령 80 75 60
2번 학생 성적 입력(이름 국어 영어 수학) : 이윤서 100 90 80
3번 학생 성적 입력(이름 국어 영어 수학) : 송해덕 80 85 80
4번 학생 성적 입력(이름 국어 영어 수학) : 정효진 80 88 100
5번 학생 성적 입력(이름 국어 영어 수학) : .

------------------------------------------------------
전체 회원 수 : 4
------------------------------------------------------
번호	이름	국어	영어	수학	총점	평균
  1	 김진령	  80	  75	  60	 215	 71.666667	
  2	 이윤서	 100	  90	  80	 270	 90.000000	
  3	 송해덕	  80	  85	  80	 245	 81.666667	
  4	 정효진	  80	  88	 100	 268	 89.333333	
------------------------------------------------------
데이터베이스 연결 닫힘~!!!
프로그램 종료!!!
*/




























