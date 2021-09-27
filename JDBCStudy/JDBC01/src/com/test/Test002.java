/*===========================================
 	Test002.java
 	- main() 메소드를 포함하는 테스트 클래스
=============================================*/

package com.test;

import java.sql.Connection;

import com.util.DBConn;

public class Test002
{
	public static void main(String[] args)
	{
		Connection conn = DBConn.getConnextion();
		
		if (conn != null)
		{
			System.out.println("데이터베이스 연결 성공~!!!");
		}
		DBConn.close();
		
		
	}
}
