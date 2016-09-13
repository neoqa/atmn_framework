package com.framework.core.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.annotations.Parameters;

import com.framework.core.web.CustomMethods;

public class DbConnection 
{		
	protected static Connection connection;
	protected static Statement st;
	protected static String sqlPath = "C:\\Selenium_TestData\\Projects\\DocsFlow\\SQL\\";
	
	@Parameters({"environment"})
	public static Connection setDbConnection(String environment)
	{
		// ����������� URL, ������ � ������ ��� �����������
		String conUrl = defineConnection(environment);

		// ������� ����� �� �����
		String[] user = new CustomMethods().new WorkWith_TextFiles().file_Read(sqlPath + "db_user.txt").split("&");
		String login = user[1].trim().replace("\r\n", "");
		String password = user[2].trim();
		
		try 
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection(conUrl, login, password);
			st = connection.createStatement();
			st.close();
		} 
		
		catch (Exception e) 
		{
			System.out.println("������ ��� ����������� � ��: " + e);
		}
		
		return connection;
	}
	
	public static void closeConnection(Connection sqlConnection)
	{
		try 
		{
			sqlConnection.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	private static String defineConnection(String environment)
	{
		// ������� �������� �� �����
		String[] servers = new CustomMethods().new WorkWith_TextFiles().file_Read(sqlPath + "db_servers.txt").split("#");
		String dev = servers[1].trim().replace("\r\n", "");
		String release = servers[2].trim();
		
		// ���������� ����������
		String conUrl = null;
		
		// ���� 'dev' ��������
		if(environment.equals("dev"))
		{
			conUrl = dev;
		}
		
		// ���� 'release' ��������
		else if(environment.equals("release"))
		{
			conUrl = release;
		}
		
		// ���� ������, �� ������
		else
		{
			System.out.println("������ ��� ������� ����������� URL ��� ����������� � ��. ���������� 'environment' = " + environment);
		}
		
		// ������� URL �����������
		return conUrl;
	}
}
