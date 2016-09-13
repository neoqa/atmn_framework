package com.framework.core.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.framework.core.web.elements.Button;
import com.framework.core.web.elements.Custom;
import com.framework.core.web.elements.Link;
import com.framework.core.web.elements.Text;
import com.framework.core.web.elements.TextInput;

public abstract class CommonElements 
{
	// ������ ��������
	public static class Other_Elements
	{
		// ������ ������ �� �������
		public Button userOut_Button(WebDriver driver)
		{
			return new Button(driver, By.xpath("//a[@href='/Account/LogOff']"));
		}
		
		// ������ �������� �� ������� ���������(� header(�))
		public Link backToMain_Link(WebDriver driver)
		{
			return new Link(driver, By.className("header-link"));
		}
		
		// <div> � ������� ����� ������� ����
		public WebElement mainMenu_Div(WebDriver driver)
		{
			return driver.findElement(By.className("main-menu"));
		}
	}
	
	// ������ �������������
	public static class UnitsTree_Elements
	{
		// ���������
		public Custom units_Accordion(WebDriver driver)			{ return new Custom(driver, By.id("departments-accordion")); }
		
		// ���� ������
		public Custom tree_Div(WebDriver driver)				{ return new Custom(driver, By.id("tree")); }
	}

	// ��������� ����������
	public static class FiltrationControl_Elements
	{
		// ���������
		public Custom accordion_Div(WebDriver driver)			{ return new Custom(driver, By.id("generalFilter")); }
		
		// �������� ���� � �����
		public Select fieldName_Select(WebDriver driver)		{ return new Select(driver.findElement(By.xpath("//select[contains(@id, 'name')]"))); }
		
		// ��� ������������
		public Select matchType_Select(WebDriver driver)		{ return new Select(driver.findElement(By.xpath("//select[contains(@id, 'operation')]"))); }
	
		// ��������
		public TextInput value_TextInput(WebDriver driver)		{ return new TextInput(driver, By.xpath("//input[contains(@id, 'value')]")); }
	
		// ������ '������'
		public Button search_Button(WebDriver driver)   		{ return new Button(driver, By.id("buttonSearch")); }	
		
		// ������ '��������'
		public Button clear_Button(WebDriver driver)   			{ return new Button(driver, By.id("buttonClear")); }	
	}
	
	// ���� ��������
	public static class BaseGrid_Elements
	{
		// ������� "������"
		public Button add_Button(WebDriver driver)				{ return new Button(driver, By.id("btnAdd")); }
		
		// "������������"
		public WebElement grid_Body(WebDriver driver)			{ return driver.findElement(By.xpath("//table[@id='grid']/tbody")); }
		
		// "������������"
		public Custom download_Div(WebDriver driver)			{ return new Custom(driver, By.id("load_grid")); }
		
		// ������ ���������
		public Button view_Button(WebDriver driver)   			{ return new Button(driver, By.xpath("//td[@aria-describedby='grid_view']")); }
		
		// ������ ��������������
		public Button edit_Button(WebDriver driver)   			{ return new Button(driver, By.xpath("//td[@aria-describedby='grid_edit']")); }
			
		// ������ ��������
		public Button delete_Button(WebDriver driver)   		{ return new Button(driver, By.xpath("//td[@aria-describedby='grid_del']")); }
		
		// �������� ����������
		public static class Pager_Elements
		{
			// ������ ��������
			public TextInput pageNumber_Input(WebDriver driver){return new TextInput(driver, By.className("ui-pg-input"));}
		}
	}
	
	// �������� ��������
	public static class Card_Elements
	{
		// ����� ��������
		public static class General_Elements
		{
			// ����� �������(������ + ���� ���.)
			public Text card_Header(WebDriver driver)							{ return new Text(driver, By.xpath("//*[contains(@class, 'doc-header')]")); }
			
			// ������ '��������'
			public Button save_Button(WebDriver driver)   						{ return new Button(driver, By.id("saveBtnTop")); }	
			
			// ������ '�������'
			public Button close_Button(WebDriver driver)   						{ return new Button(driver, By.id("cancelBtnTop")); }	
			
			// ������ '��������� ���������� ������'
			public WebElement numGenerate_Button(WebDriver driver) 				{ return driver.findElement(By.id("btnGenerateNum")); }
			
			// ������� ���� ��������
			public WebElement inset_Link(WebDriver driver, String inset_Number)	{ return driver.findElement(By.xpath("(//div[@class='item'])[" + inset_Number + "]")); }  
														  						// ���, 'InsetId' - ����� ������� 
			
			// ������ '������' �������� � �������
			public Button dictAdd_Button(WebDriver driver, String id) 			{ return new Button(driver, By.id(id + "_btn_add")); }	
		}
		
		// �������� �������������� ����� ������ ��������
		public static class Grid
		{			
			// ������ ����������
			public Button add_Button(WebDriver driver, String grid_Id)   		{ return new Button(driver, By.id("btnAdd" + grid_Id)); }
		
			// ����
			public WebElement grid_Body(WebDriver driver, String grid_Id)		{ return driver.findElement(By.xpath("//*[@id='grid" + grid_Id + "']/tbody")); }
			
			// Div '������������'
			public Custom download_Div(WebDriver driver, String grid_Id)  		{ return new Custom(driver, By.id("load_grid" + grid_Id)); }
				
			// ������ ��������������
			public Button edit_Button(WebDriver driver, String grid_Id)   		{ return new Button(driver, By.xpath("//td[@aria-describedby='grid" + grid_Id + "_edit']")); }
				
			// ������ ��������
			public Button delete_Button(WebDriver driver, String grid_Id)   	{ return new Button(driver, By.xpath("//td[@aria-describedby='grid" + grid_Id + "_del']")); }
		}
		
		// ���-���
		public static class Pop_Ups extends CommonElements.General_PopUps
		{
			// ��������� ��� �������� ������
			public String deletion_Message = "������� �������� ���� ��������, ����������?";
			
			// ��������� ��� ����� ��� ���������� ������
			public String goAway_Message = "��� �� ������� ���� �����. ������� �� ���� ������� ��� ���������� �����?";
			
			// ��������� ��� ����� ��� ���������� ������
			public String cardGoAway_Message = "��� �� ������� ���� �����. ������� ������ ��� ���������� �����?";
			
			// ��������� ��� ���������� ������ � �������
			public String dictValueAdd_AskMessage(String value) 
			{
				String message = "�� ����� ������ ������ �������� '" + value + "' � �������?";
				return message;
			}
			
			// ��������� ��� �������� ���������� ������ � �������
			public String dictValueAdd_SuccessMessage(String dictName) 
			{
				String message = "�������� ������ ������ � ������� '" + dictName + "'";
				return message;
			}
		}
		
		// ���� ������
		public static class Card_Files_Elements extends Pop_Ups
		{
			// ������ ��������������
			public Button fileDownload_Button(WebDriver driver)   		{ return new Button(driver, By.cssSelector(".btn.btn-filedownload-s")); }									
			
			// ������ �������� �����
			public TextInput fileUpload_Button(WebDriver driver)		{return new TextInput(driver, By.id("fileSource"));}
		}
		
		// ���� ��������� ����������
		public static class Card_LinkedDocs_Elements
		{
			// ������ '������ ��'����'
			public Button addLink_Button(WebDriver driver)   				{ return new Button(driver, By.id("btnAddLink")); }
			
			// ������ '�������� ��'����'
			public Button deleteLink_Button(WebDriver driver)   			{ return new Button(driver, By.id("btnDelLink")); }
			
			// ������
			public TextInput index_TextInput(WebDriver driver)				{return new TextInput(driver, By.id("index"));}
			
			// ���
			public TextInput year_TextInput(WebDriver driver)				{return new TextInput(driver, By.id("year"));}
			
			// ������ '�����'
			public Button search_Button(WebDriver driver)   				{ return new Button(driver, By.id("btnSearch")); }
			
			// ��������� ��������
			public Text findedDoc_Text(WebDriver driver, String docName)	{return new Text(driver, By.xpath("//*[contains(@id, '_anchor') " +
																											  "and text()='" + docName + "']"));}
			
			// ������ '����'����� ��������'
			public Button linkDoc_Button(WebDriver driver)   				{ return new Button(driver, By.id("btnLink")); }
			
			
			// ����������� ��������
			public Text addedDoc_Text(WebDriver driver)						{return new Text(driver, By.xpath("//a[@href='#' and contains(@id, '_anchor')]"));}
			
			// ���������� � ���������
			public WebElement docInfo_Div(WebDriver driver)					{return driver.findElement(By.id("linkPreview"));}
			
			// ���������� � ���������
			public WebElement docInfo_Tbody(WebDriver driver)				{return driver.findElement(By.xpath("//div[@id='linkPreview']/table/tbody"));}
		}
	}
	
	// ����� �������� ���-����
	public static class General_PopUps
	{
		// �������������� ���-��
		public Custom info_PopUp(WebDriver driver)   				{ return new Custom(driver, By.cssSelector(".ui-dialog-content.ui-widget-content")); }	
		
		// ������ '���'
		public Button yes_Button(WebDriver driver)   				{ return new Button(driver, By.xpath("//span[(@class='ui-button-text') and contains(text(), '���')]")); }
		
		// ������ 'ͳ'
		public Button no_Button(WebDriver driver)   				{ return new Button(driver, By.xpath("//span[(@class='ui-button-text') and contains(text(), 'ͳ')]")); }
		
		// ������ '�������'
		public Button close_Button(WebDriver driver)   				{ return new Button(driver, By.xpath("//span[(@class='ui-button-text') and contains(text(), '�������')]")); }
		
		// ������ ����������
		public Button save_Button(WebDriver driver)   				{ return new Button(driver, By.xpath("//span[@class='ui-button-text' and text()='��������']")); }
	}
}
