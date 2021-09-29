/*================================
	DBConn.java
	- try-catch 말고 throws 사용
==================================*/

package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn
{
	private static Connection dbConn;
	
	public static Connection getConnection() throws SQLException, ClassNotFoundException
	{
		if(dbConn == null)
		{
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "scott";
			String pwd = "tiger";
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			dbConn = DriverManager.getConnection(url, user, pwd);
			
		}
		return dbConn;
	}
	
	public static Connection getConnection(String url, String user, String pwd) throws SQLException, ClassNotFoundException
	{
		if(dbConn == null)
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			dbConn = DriverManager.getConnection(url, user, pwd);
		}
		return dbConn;
	}
	
	public static void close() throws SQLException
	{
		// 연결이 되어있을 때만 클로즈 하겠다.
		if(dbConn != null)
		{
			if(!dbConn.isClosed())
				dbConn.close();
		}
		dbConn = null;
	}

}
