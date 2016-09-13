package com.framework.core.web.custom_classes;

import java.io.File;
import java.io.IOException;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.framework.core.web.CustomMethods;
import com.framework.core.web.elements.Button;

public abstract class Excell_Parser 
{
	public String[][] GetHeadPart_ForExcel(WebElement thead)
	{
		// ����������� ���������� �����
		List<WebElement> rows = thead.findElements(By.tagName("tr"));
		
		// ����������� ������� ������������� �������
		String[][] GridValues = new String[rows.size()][];
		
		// ���������� ����
		for(int rnum=0; rnum < rows.size(); rnum++)
		{
			// ���������� ���������� �������
			List<WebElement> columns=rows.get(rnum).findElements(By.tagName("th"));
			int sizeBeforeCut = columns.size();
						
			// ���������� ������� �����
			for(int cnum= (sizeBeforeCut - 1); cnum >= 0; cnum--)
			{
				if(columns.get(cnum).getCssValue("display").equals("none"))
				{
					columns.remove(cnum);
				}
			}
			
			// �������� ������ ��������
			columns.remove(columns.size() - 1);
			columns.remove(0);
			
			// ����������� ������� ������� � ���������� �����
			String[] ColumnValues = new String[columns.size()];
			
			// ���������� �������� ����� � ������
			for(int cnum=0; cnum<columns.size(); cnum++)
			{
				// ����� ����� �� ��������
				ColumnValues[cnum] = columns.get(cnum).findElement(By.tagName("div")).getAttribute("textContent");   // ����� ����� ��������� �������� 'innerText', 'innerHTML'
			}
			
			// �������� �������� ������� � ��� 
			GridValues[rnum] = ColumnValues;
		}
		return GridValues;
	}
	
	public String[][] GetAllRows_ForExcel(WebElement tbody)
	{
		// ����������� ���������� �����
		List<WebElement> rows = tbody.findElements(By.tagName("tr"));
		
		// �������� �������� ����
		rows.remove(0);
		
		// ����������� ������� ������������� �������
		String[][] GridValues = new String[rows.size()][];
		
		// ���������� ����
		for(int rnum=0; rnum < rows.size(); rnum++)
		{
			// ���������� ���������� �������
			List<WebElement> columns=rows.get(rnum).findElements(By.tagName("td"));
			int sizeBeforeCut = columns.size();
						
			// ���������� ������� �����
			for(int cnum= (sizeBeforeCut - 1); cnum >= 0; cnum--)
			{
				if(columns.get(cnum).getCssValue("display").equals("none"))
				{
					columns.remove(cnum);
				}
			}
			
			// �������� ������ ��������
			columns.remove(columns.size() - 1);
			columns.remove(0);
			
			// ����������� ������� ������� � ���������� �����
			String[] ColumnValues = new String[columns.size()];
			
			// ���������� �������� ����� � ������
			for(int cnum=0; cnum<columns.size(); cnum++)
			{
				ColumnValues[cnum] = columns.get(cnum).getText();
			}
			
			// �������� �������� ������� � ��� 
			GridValues[rnum] = ColumnValues;
		}
		return GridValues;
	}
	
	public String[][] get_ExcelValues(Button DownloadButton, String ExpectedFileName)
	{
		// �������
		DownloadButton.click();	
		new CustomMethods().simpleWait(5);
		
		// �����, ���� ������ �������� �����
		String folderPath = new CustomMethods().BASE_DIR + "\\storage\\files\\downloaded_files";
		
		// ����� ��� ����� � �����
		File folderToScan = new File(folderPath); 
	    File[] listOfFiles = folderToScan.listFiles();
	    int filesCount = listOfFiles.length;
		
    	// ���� �� ������� ����� ���� �� ���������� ����� - ������� ��� ����� � ������ ������
    	if(filesCount != 1)
    	{
    		// ������� ��� �����, ���� ��� ����
    		if(filesCount != 0)
    		{
        		for(int i = 0; i < filesCount; i++)
        		{
        			File tempFileVariable = listOfFiles[i];
        			tempFileVariable.delete();
        		}
    		}
    		
    		// ������� ������
    		System.err.println("������ �������� ����� Excel ��� ��������. ���������� ��������� ������ != ����������." +
 		   		   			   "\r\n��������� ���� = " + ExpectedFileName + 
 		   		   			   "\r\n���������� ��������� ������ = " + filesCount);
    		Assert.fail();
    	}
    	
    	// �������� ���� �� �������
    	File file = listOfFiles[0];
    	
    	// ����������� ������� ��� ��������
    	String[][] data = null;
    	
    	try
    	{
    		data = getExcelValues(file);
    	}  	
		catch(Exception e)
		{
    		System.err.println("������ ��� ���������� �������� �� Excel �����." +
   		   			   		   "\r\n����� ������ = " + e.getMessage() +
   		   			   		   "\r\n���� = " + e.getStackTrace());
		}
		finally
		{
    		file.delete();
		}
    	
    	return data;
	}
	
	private String[][] getExcelValues(File excelFile)
	{
		Workbook w;
	    String[][] data = null;

        try 
        {
        	// ���������� �����
            w = Workbook.getWorkbook(excelFile);
            
            // ����� ������ ����
            Sheet sheet = w.getSheet(0);    
        
            // ����������� ������� �������
            data = new String[sheet.getRows()][sheet.getColumns()];
            
            // ������� �����
            for (int i = 0; i <sheet.getRows(); i++) 
            {
            	//������� ������� + ������ ����� � ������
                for (int j = 0; j < sheet.getColumns(); j++) 
                {
                	// ����� �������� �� ������
                    Cell cell = sheet.getCell(j, i);
                    String cellValue = cell.getContents();
                    
                    // ������ ������ �������('-' � ';')
                    cellValue.replace("-", "");
                    cellValue.replace(";", "");
                    cellValue = new CustomMethods().StringSpacesCut(cellValue);
                    // ������ �������� � ������
                    data[i][j] = cellValue;
                }
            }

        } 
        
        catch (BiffException e) {e.printStackTrace();} 
        catch (IOException e) {e.printStackTrace();}
        
        return data;
	}
}
