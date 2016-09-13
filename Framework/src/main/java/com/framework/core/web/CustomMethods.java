package com.framework.core.web;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.framework.core.web.custom_classes.Excell_Parser;
import com.framework.core.web.custom_classes.GridCheck;
import com.framework.core.web.custom_classes.WorkWith_Files;
import com.framework.core.web.elements.CheckBox;
import com.framework.core.web.elements.TextInput;

public class CustomMethods 
{
	// Base maven directory
	public String BASE_DIR = System.getProperties().get("basedir").toString();
	
	// Actions with 'Grids'
	public class Grid extends GridCheck{}
	
	// Managing text files
	public class WorkWith_TextFiles extends WorkWith_Files {}
	
	// Work with Excell files
	public class WorkWith_Excel extends Excell_Parser{}
	
	public class Attribute_Checkers
	{
		public void checkBoxAttribute_Check(CheckBox checkBox, String attribute, Boolean expectedCondition)
		{
			// ���������� ��� ����������
			Boolean actualCondition = true;
			
			// ���������� � ��������� ��������
			String attributeValue = checkBox.getAttribute(attribute);
			
			// ���� ������� �� ���������� - ������� 'false'
			if(attributeValue == null)
			{
				actualCondition = false;
			}
			
			// ���� �������� �������� != ����������, �� ������� ������
			if(actualCondition != expectedCondition)
			{
				System.err.println("������ �� ����� �������� ��������. ��������� �������� �������� != ���������." +
		   		   		   		   "\r\n��������� �������� �������� '" + attribute + "' = " + expectedCondition.toString() + 
				   		   		   "\r\n���-���� = " + checkBox.findByMethod.toString());
				Assert.fail();
			}
		}
		
		public void textInput_Attribute_Check(TextInput textInput, String attribute, Boolean expectedCondition)
		{
			// ���������� ��� ����������
			Boolean actualCondition = true;
			
			// ���������� � ��������� ��������
			String attributeValue = textInput.getAttribute(attribute);
			
			// ���� ������� �� ���������� - ������� 'false'
			if(attributeValue == null)
			{
				actualCondition = false;
			}
			
			// ���� �������� �������� != ����������, �� ������� ������
			if(actualCondition != expectedCondition)
			{
				System.err.println("������ �� ����� �������� ���� ��� �����. ��������� �������� �������� != ���������." +
				   		   		   "\r\n��������� �������� �������� '" + attribute + "' = " + expectedCondition.toString() + 
				   		   		   "\r\n���� ��� ����� = " + textInput.findByMethod.toString());
				Assert.fail();
			}
		}
	}
	
	public String getCurrentDate()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date date = new Date();
		String currentDate = dateFormat.format(date);
		return currentDate;
	}
	
	public String getChangedDate(int changer)
	{
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int daysToChange = changer;
		cal.add(Calendar.DATE, daysToChange);
		date = cal.getTime();
		
		String changedDate = dateFormat.format(date);
		return changedDate;
	}
	
    public String StringSpacesCut(String StringToCut)
    {
    	// ���� null, �� ������� ������
    	if (StringToCut == null) StringToCut = "";
    	
    	// ���� ������ ������, �� ������ �� ������
        if (!StringToCut.isEmpty())
        {
            // ��������� �������� ����� ������
            int lastIndex = StringToCut.length() - 1;
            String lastChar = Character.toString(StringToCut.charAt(lastIndex));
            for (int i = 0; lastChar.equals(" ") && (i < 5); i++)
            {      	
            	StringBuilder sb = new StringBuilder(StringToCut);
            	sb.deleteCharAt(lastIndex);
            	StringToCut = sb.toString();
            	lastIndex = StringToCut.length() - 1;
            	lastChar = Character.toString(StringToCut.charAt(lastIndex));
            }

            // ��������� �������� ����� �������
            String firstChar = Character.toString(StringToCut.charAt(0));
            for (int i = 0; firstChar.equals(" ") && (i < 5); i++)
            {
                StringToCut = StringToCut.substring(1);
                firstChar = Character.toString(StringToCut.charAt(0));
            }
        }
        return StringToCut;
    }
	
	public void leaveWarningCheck(WebDriver driver, String browser)
	{
		// �������� �� ����� �������� �� �������
		driver.findElement(By.className("header_link")).click();
		try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
		
		// ���������� ���������� ��� ������ '������'
		String alertText = null;
		
		// ������ ������ � �������� ������
		try
		{
		Alert alert = driver.switchTo().alert();
		alertText = alert.getText();
		alert.dismiss();
		}
		catch(Exception e)
		{
    		System.err.println("������ ��� �������� '������' � ����� �� ���������. �� ������� ����� '�����'." +
 			   		   		   "\r\n������: = \r\n" + e.getMessage() + 
 			   		   		   "\r\n����: = \r\n" + e.getStackTrace());
    		Assert.fail();
		}
		
		// �������� ������ ������
		if(browser.equals("chrome"))
		{
			assertThat("�� �������� �������� ������������� ������. ��������� �� �������� ��� ���������� ��������� ������?", is(equalTo(alertText)));
		}
		else if(browser.equals("firefox"))
		{
			assertThat("��� �������� ������ ��� �����������, ��� �� ������ ���� � ��� ���� �������� ���� ������ ����� �� �����������.", is(equalTo(alertText)));
		}
		else if(browser.equals("ie"))
		{
			
		}
	}
	
	public void elementExistenceCheck(WebElement element, boolean Exists)
	{
		// �������� ���������� ��� ��������
		boolean elementExistence = false;	   
		
		// �������� ������������� ���������
		if(element.isDisplayed()) elementExistence = true;
		
		// ���� ��������� �������� �� ����� ��������� - ������
		if(elementExistence != Exists)
		{
    		System.err.println("������ ��� �������� ������������� ��������." +
	   		   		   "\r\n�������: = \r\n" + element.toString() + 
	   		   		   "\r\n��������� �������� �������������: = \r\n" + Exists + 
	   		   		   "\r\n������� �������� �������������: = \r\n" + elementExistence);
    		Assert.fail();
		}
	}
	
	public void simpleWait(int Seconds)
	{
		try
		{
			Thread.sleep(Seconds * 1000);
		}
		catch(InterruptedException e){throw new RuntimeException(e);}
	}
	
	// Managing JS actions
	public class Js_Actions
	{
		public void webElement_Click(WebDriver driver, WebElement element)
		{
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", element);
		}
		
		public void webElement_SendText(WebDriver driver, WebElement element, String text)
		{
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].innerHTML = '<span>" + text + "</>';", element);
		}
	}
	
	// ����� ��� ������ � ��������� ����������
	public class WorkWith_TextEditor
	{
		public void setTextValue(WebDriver driver, WebElement frame, String text)
		{
			driver.switchTo().frame(frame);
			WebElement body = driver.findElement(By.cssSelector("body"));
			body.sendKeys(text);
			driver.switchTo().defaultContent();
		}
		
		public String getTextValue(WebDriver driver, WebElement frame)
		{
			driver.switchTo().frame(frame);
			WebElement body = driver.findElement(By.cssSelector("body"));
			String value = body.getText();
			driver.switchTo().defaultContent();
			
			return value;
		}
	}
}
