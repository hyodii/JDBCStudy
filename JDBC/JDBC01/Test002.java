/*===========================================
 	Test002.java
 	- main() 메소드를 포함하는 테스트 클래스
=============================================*/

package com.test;

import java.sql.Connection;

import com.util.DBConnBackup;

public class Test002
{
	public static void main(String[] args)
	{
		Connection conn = DBConnBackup.getConnection();
		// ※ DB 연결 과정이 가장 부하가 크기 때문에
		//	  한 번 연결된 객체를 계속 사용할 수 있도록 Singleton 패턴 적용
		
		// getConnection() 메소드를 통해 정상적인 연결이 이루어진 상황이라면...
		if (conn != null)
		{
			System.out.println("데이터베이스 연결 성공~!!!");
		}
		

		DBConnBackup.close();
		//-- close() 메소드 호출을 통해 연결 종료
		
		
	}
}
