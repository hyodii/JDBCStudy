/*=======================
 	DBConn.java
=========================*/


package com.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConn
{
	public static Connection dbConn;
	
	public static Connection getConnextion()
	{
		if(dbConn == null)
		{
			try
			{
				String url = "jdbc:oracle:thin@lacalhost:1521:xe";
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
	public static void close()
	{
		if(dbConn != null)
		{
			try
			{
				if (!dbConn.isClosed())
					dbConn.close();
			}catch (Exception e)
			{
				System.out.println(e.toString());
			}
		
		}
		
		dbConn = null;
	}
	
}
