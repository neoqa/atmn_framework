package com.framework.core.web;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.framework.core.web.elements.Button;
import com.framework.core.web.elements.Custom;
import com.framework.core.web.elements.TextInput;

public class CommonActions 
{
	public void simpleWait(int Seconds)
	{
		try
		{
			Thread.sleep(Seconds * 1000);
		}
		catch(InterruptedException e){throw new RuntimeException(e);}
	}
	
/*	public LogInPage userOut(WebDriver driver)
	{
		new Elements().userOut_Button(driver).click();
		return new LogInPage(driver).waitUntilAvailable();
	}
	
	public MainPage backToMainPage(WebDriver driver)
	{
		new Elements().backToMain_Link(driver).click();
		return new MainPage(driver).waitUntilAvailable();
	}*/
	
	public void autoCompleteValue_Set(WebDriver driver, TextInput element, int stepsDown_Count)
	{
		try
		{
			element.click();
			Thread.sleep(1000);
			Actions action = new Actions(driver);
			
			for(int i = 0; i< stepsDown_Count; i++)
			{
				action.sendKeys(Keys.DOWN).build().perform();
				Thread.sleep(1000);
			}
			
			action.sendKeys(Keys.ENTER).build().perform();
			Thread.sleep(1000);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// ��������� �� �������
	public void menu_Handler(WebDriver driver, int mainMenu_Num,  int subMenu_Num)
	{
		// ����������� ���� ������� �������� ����
		List<WebElement> main_Items = new Elements().mainMenu_Div(driver).findElements(By.xpath("//div[contains(@class, 'menu-item left')]"));
		
		// ����������� ������� �������� �������� ����
		WebElement main_Item = main_Items.get(mainMenu_Num - 1);
	
		// ���� �� �������� ������ ����
		main_Item.click();
		try {Thread.sleep(3000);} catch (InterruptedException e) {e.printStackTrace();}
		
		// ���������� ������ ���� ���� ����������
		if (subMenu_Num != 0)
		{
			// ������� <li> �������
			List<WebElement> sub_Items = main_Item.findElement(By.className("sub-menu-item")).findElements(By.tagName("li"));
			
			//// ���� �� ��� ����
			////sub_Items.get(subMenu_Num - 1).click(); // �� ��������, ��
			
			// ������� �� URL, ������� ����� � ������ ����
			String page_Url = sub_Items.get(subMenu_Num - 1).findElement(By.tagName("a")).getAttribute("href");
			driver.get(page_Url);
		}
	}
	
	// ��������� �� ������ �������������
	public void tree_Handler(WebDriver driver, int[] tree_Path)  // ��� tree_Path - ������ ������� ��������� � ������ �� ������� ����� ������.
	{														     // �������������� ���������� ��������� � ������� = ������� ��������
		// ������
		Actions builder = new Actions(driver);
		
		// ���������� �� �������� tree_Path, ������ �� ��������� ������ � ������������ � tree_Path
		for (int i = 0; i < tree_Path.length; i++)
		{				
			// ���� ARROW_DOWN
			for(int j = 0; j < tree_Path[i]; j++)
			{
				builder.sendKeys(Keys.ARROW_DOWN).build().perform();
				simpleWait(1);
			}
			
			// ��� ������ ������� �������� ���������������� �������� - ARROW_RIGHT
			builder.sendKeys(Keys.ARROW_RIGHT).build().perform();
			simpleWait(1);
		}
		
		// ������� �������������
		builder.sendKeys(Keys.ENTER).build().perform();
		simpleWait(1);
	}
	
	// ���������� �����
	public void grid_Filtration(WebDriver driver, String fieldName, String matchType, String value)
	{
		// ������� ���������
		new Elements().new FiltrationAccordion().accordion_Div(driver).click();
		
		// ��������� ��������� ����������
		new Elements().new FiltrationAccordion().fieldName_Select(driver).selectByVisibleText(fieldName);
		new Elements().new FiltrationAccordion().matchType_Select(driver).selectByVisibleText(matchType);
		new Elements().new FiltrationAccordion().value_TextInput(driver).inputText(value);
		
		//����������� ����
		new Elements().new FiltrationAccordion().search_Button(driver).click();
		simpleWait(1);
	}
	
	// �������� ������ ��������
	public void cardHeader_Check(WebDriver driver, String expected_Header)
	{
		assertThat(new Elements().new Card_Elements().card_Header(driver).getText(), is(equalTo(expected_Header)));
	}
	
	// ���������� ������ � ������� �� ��������
	public void dictValue_Add(WebDriver driver, Button add_Button, String askMessage, String successMessage)
	{
		//region Variables
		Custom info_PopUp = new Elements().new PopUps().info_PopUp(driver);
		//endregion
		
		// ������ ������ ����������
		add_Button.click();
		simpleWait(1);
		info_PopUp.waitUntilAvailable();

		// �������� ���������
		assertThat(info_PopUp.getText(), is(equalTo(askMessage)));
		
		// ������ '���'
		new Elements().new PopUps().yes_Button(driver).click();
		new CustomMethods().simpleWait(1);
		info_PopUp.waitUntilAvailable();
		
		// �������� ��������� �� �������� ����������
		assertThat(info_PopUp.getText(), is(equalTo(successMessage)));
		
		// �������
		new Elements().new PopUps().close_Button(driver).click();
		add_Button.waitUntilAvailable();
	}
	
	/*______________________________ �������� ________________________________*/
	
	private class Elements extends CommonElements.Other_Elements
	{		
		// ��������� ����������
		private class FiltrationAccordion extends CommonElements.FiltrationControl_Elements{}
		
		// �������� ��������
		private class Card_Elements extends CommonElements.Card_Elements.General_Elements{}
		
		// ���-���
		private class PopUps extends CommonElements.General_PopUps{}
	}
}
