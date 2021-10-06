/*=============================================
	ScoreMain.java

	- ScoreDTO, ScoreDAO Process 클래스를 활용
===============================================*/
/*
 <전반적인 흐름 순서 기억!!>
- 요구사항 분석
- 객체 구성(시퀀스 등)
- 쿼리문 준비
- 클래스 설계 및 구현
*/

/*
○ 성적 처리 → 데이터베이스 연동(데이터베이스 연결 및 액션 처리)
				ScoreDTO 클래스 확용(속성만 존재하는 클래스, getter / setter 구성)
				ScoreDAO 클래스 활용(데이터베이스 액션처리)
				ScoreProcess 클래스 활용(단위 기능 구성)
				
※ 단, 모~~~ 든 작업 객체는 ☆ PreparedStatement ☆ 를 활용한다.
				
				
여러 명의 이름, 국어점수, 영어점수, 수학점수를 입력받아
총점, 평균, 석차 등을 계산하여 출력하는 프로그램을 구현한다.
※ 서브 메뉴 구성 → Process 클래스 활용.

실행 예)

====[ 성적 처리 ]====
1. 성적 입력
2. 성적 전체 출력
3. 이름 검색 출력
4. 성적 수정
5. 성적 삭제
======================
>> 선택(1~5, -1종료) : 1			(6번까지 입력되어있다고 가정)

7번 학생 성적 입력(이름 국어 영어 수학) : 이다영 50 60 70
성적 입력이 완료되었습니다.

8번 학생 성적 입력(이름 국어 영어 수학) : 이지영 80 80 80
성적 입력이 완료되었습니다.

9번 학생 성적 입력(이름 국어 영어 수학) : .

====[ 성적 처리 ]====
1. 성적 입력
2. 성적 전체 출력
3. 이름 검색 출력
4. 성적 수정
5. 성적 삭제
======================
>> 선택(1~5, -1종료) : 2

전체 인원 : 8명
번호	이름	국어	영어	수학	총점	평균	석차
1
2
3
4
5						...
6
7
8	

====[ 성적 처리 ]====
1. 성적 입력
2. 성적 전체 출력
3. 이름 검색 출력
4. 성적 수정
5. 성적 삭제
======================
>> 선택(1~5, -1종료) : 3

누구이름 검색할지 물어보고
데이터 보여주고
수정하고

누구성적 수정할지 물어보고
데이터 보여주고
수정하고

삭제할 학생 번호 입력 물어보고
데이터 보여주고 삭제할꺼야?
삭제하고

====[ 성적 처리 ]====
1. 성적 입력
2. 성적 전체 출력
3. 이름 검색 출력
4. 성적 수정
5. 성적 삭제
======================
>> 선택(1~5, -1종료) : -1

프로그램이 종료되었습니다.
*/

package com.test;

import java.util.Scanner;

public class ScoreMain
{
	public static void main(String[] args)
	{
		ScoreProcess prc = new ScoreProcess();
		Scanner sc = new Scanner(System.in);
		
		do
		{
			System.out.println();
			System.out.println("====[ 성적 처리 ]====");
			System.out.println("1. 성적 입력");
			System.out.println("2. 성적 전체 출력");
			System.out.println("3. 이름 검색 출력");
			System.out.println("4. 성적 수정");
			System.out.println("5. 성적 삭제");
			System.out.println("======================");
			System.out.print(">> 선택(1~5, -1종료) :");
			String menus = sc.next();
			
			try
			{
				int menu = Integer.parseInt(menus);
				
				if(menu == -1)
				{
					System.out.println();
					System.out.println("프로그램이 종료되었습니다.");
					return;
				}
				switch (menu)
				{
					case 1:
						prc.sungjukInsert();
						break;
	
					case 2:
						prc.sungjukSelectAll();;
						break;
					case 3:
						prc.sungjukSearchName();
						break;
					case 4:
						prc.sungjukUpdate();
						break;
					case 5:
						prc.sungjukDelete();
						break;
				}
				
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
			
		} while (true);
	}
}




// java.sql.SQLException:인덱스에서 누락된 IN 또는 OUT 매개변수
//--> 이 메세지 나올때는 ps.setString() 하는 부분이 제대로 되어있는지 살펴본다. 
//--  method 매개인자 개수와 ps.setString 할때 숫자가 틀렸을때 혹은 변수가 틀릴때 나는 에러이다