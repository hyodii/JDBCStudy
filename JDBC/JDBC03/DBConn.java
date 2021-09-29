/*=============================
 	DBConn.java
 	- try ~ catch 로 예외 처리
===============================*/

package com.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConn
{
	private static Connection dbConn;
	
	public static Connection getConnection()
	{
		if (dbConn == null)
		{
			try
			{
				String url = "jdbc:oracle:thin:@localhost:1521:xe";
				String user = "scott";
				String pwd = "tiger";
				
				Class.forName("oracle.jdbc.driver.OracleDriver");
				
				dbConn = DriverManager.getConnection(url,user, pwd);
				
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		return dbConn;
	}
	
	public static Connection getConnction(String url, String user, String pwd)
	{
		if(dbConn == null)
		{
			try
			{
				
				Class.forName("orcle.jdbc.driver.OracleDriver");
				dbConn = DriverManager.getConnection(url, user, pwd);
				
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		return dbConn;
	}
	
	public static void close()
	{
		if(dbConn != null)
		{
			try
			{
				if(!dbConn.isClosed())
					dbConn.close();
				
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		dbConn = null;
	}

	
}
