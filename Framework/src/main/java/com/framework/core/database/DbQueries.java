package com.framework.core.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.testng.Assert;

import com.framework.core.web.CustomMethods;

public class DbQueries 
{	
	private static String queriesPath = "C:\\Selenium_TestData\\Projects\\DocsFlow\\SQL\\SQL_Queries\\";
	private static String TextFiles_Path = System.getProperties().get("basedir").toString() + "\\storage\\files\\temp_files\\text_files\\";
	
	// ����� �� ����� '���������'
	public static class DocsTests
	{
		public static class Incoming_Docs
		{
			public static class Select_Queries
			{
				// ����������� ������, ������� ����� �������� � ������ ������� �������
			    public static String IndexesCountDefine_ErrorMessage = "\r\n\r\n��������� ������ ��� ������� ������� ���������� ���������� �� �� �� ������������ ������.\r\n����� ������:\r\n";
 			 			    
			    // ����������� ������ �������
			    public static String IndexesCount_Define = "select dc.DNM_NUM" + "\r\n" +
			    								           "from [dbo].[DocNum] dc"  + "\r\n" +
			    								           "join [dbo].Tree_Staff tf on dc.STT_IDP = tf.STT_IDP"  + "\r\n" +
			    								           "where dc.DNM_YER = 2020 and tf.STT_LNAM = '�������������'";
			}
			
			public static class Deletion_Queries
			{
				// ����������� ������, ������� ����� �������� � ������ ������� �������
			    public static String DocDeletion_ErrorMessage = "\r\n\r\n��������� ������ ��� ������� �������� ��������� ���������.\r\n����� ������:\r\n";
 			 			    
			    // ����������� ������ �������
			    public static String DocDeletion_Statement()
			    {
			    	String statement = new CustomMethods().new WorkWith_TextFiles().file_Read(queriesPath + "incoming_doc_deletion.sql");
			    	String docIndex = new CustomMethods().new WorkWith_TextFiles().file_Read(TextFiles_Path + "IncomingDoc_Index");
			    	statement = statement.replace("&", docIndex);
			    	return statement;
			    }
			}	
		}
	}
	
	// ����� �� ����� '����'
	public static class CnapTests
	{
		// �������
		public static class Registers
		{
			// ������ '��'
			public static class Individuals
			{
				// ������� ��� �������� ������
				public static class Deletion_Queries
				{
					// ����������� ������, ������� ����� �������� � ������ ������� ��������
				    public static String FoDeletion_ErrorMessage = "\r\n\r\n��������� ������ ��� ������� �������� �������� � ������� '��'.\r\n����� ������:\r\n";
				    public static String RegPlaceDeletion_ErrorMessage = "\r\n\r\n��������� ������ ��� ������� �������� ������, �������, ������ � ��. �� ������� ���.\r\n����� ������:\r\n";		    
				    
				    // ����������� ����� ��������
				    public static String FoDeletion_Statement = new CustomMethods().new WorkWith_TextFiles().file_Read(queriesPath + "fo_deletion.sql");
				    public static String RegPlaceDeletion_Statement = new CustomMethods().new WorkWith_TextFiles().file_Read(queriesPath + "registration_place_deletion.sql");
				}	
			}
			
			// ������ '���'
			public static class Entrepreneurs
			{
				// ������� ��� �������� ������
				public static class Deletion_Queries
				{
					// ����������� ������, ������� ����� �������� � ������ ������� ��������
				    public static String FoDeletion_ErrorMessage = "\r\n\r\n��������� ������ ��� ������� �������� �������� � ������� '���'.\r\n����� ������:\r\n";				   		   
				    
				    // ����������� ����� ��������
				    public static String FoDeletion_Statement = new CustomMethods().new WorkWith_TextFiles().file_Read(queriesPath + "fop_deletion.sql");				    
				}	
			}
		}
	}
	
	public static class DictionaryTests
	{
		// ������� ��� �������� ������
		public static class Deletion_Queries
		{
			// ����������� ������, ������� ����� �������� � ������ ������� ��������
		    public static String cacheUpdateValueDeletion_ErrorMessage = "\r\n\r\n��������� ������ ��� ������� �������� ������, �������, ������ � ��. �� ������� ���(��� ���������� ����).\r\n����� ������:\r\n";
		    
		    // ����������� ������ �������
		    public static String cacheUpdateValueDeletion_Statement = new CustomMethods().new WorkWith_TextFiles().file_Read(queriesPath + "handbook_cleanup_for_cached_data.sql");
		}
	}
	
	public static class AdministrationTests
	{
		public static class UsersAndAuditQueries
		{
			public static class DeletionQueries
			{
				// ����������� ������, ������� ����� �������� � ������ ������� �������
			    public static final String ErrorMessage = "\r\n\r\n��������� ������ ��� ������� �������� ������������.\r\n����� ������:\r\n";
 			 			    
			    // ����������� ������ �������
			    public static String UserDeletionStatement = readFile(queriesPath + "user_deletion.sql");
			}
		}
	}	
	
	// ��������� ����� �� �����
	private static String readFile(String path)
	{
		File file = new File(path);
		Reader input = null;
		StringWriter output = new StringWriter();
		try 
		{
			input = new FileReader(file);
		} 
		catch (FileNotFoundException e1) 
		{
			e1.printStackTrace();
			Assert.fail("�� ������� ����� ���� ��� ������� ����������� SQL �������.");
		}
		try 
		{
		  IOUtils.copy(input, output);
		  input.close();
		}	  
		catch (Exception e) 
	    {
			e.printStackTrace();
	    }
		String fileContents = output.toString();
		return fileContents;
	}
}
