package com.framework.core.web.pages.some;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.framework.core.web.CustomMethods;
import com.framework.core.web.WebPage;
import com.framework.core.web.elements.Button;
import com.framework.core.web.elements.Text;
import com.framework.core.web.elements.TextInput;


public class Mail_Page extends WebPage<Mail_Page> 
{
private static final String PAGE_URL = "https://mail.google.com/mail/";
	
	public Mail_Page(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public Mail_Page load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return new Elements().new Managing_Tools().write_Button().isAvailable();
	}
	
	/*___________________________________________________ Actions _______________________________________________________*/	
	
	// Action which lead us to log in page
	public Authorization_Page redirectTo_LogInPage()
	{
		driver.get(PAGE_URL);
		return new Authorization_Page(driver).waitUntilAvailable();
	}
	
	// Send mail
	public void mail_Send()
	{
		/*___ Variables ___*/
		String receiver = new Elements().new NewMail_PopUp().new Values().receiver;
		String subject = new Elements().new NewMail_PopUp().new Values().subject;
		String fileName = new Elements().new NewMail_PopUp().new Values().fileName;
		String filePath = BASE_DIR + "\\storage\\files\\test_data\\" + fileName;
		String fileInfo = new Elements().new Mail_Body().new Values().fileMessage;
		/*________________*/
		
		
		// Set receiver
		new Elements().new Managing_Tools().write_Button().click();
		new Elements().new NewMail_PopUp().receiver_TextInput().waitUntilAvailable();
		new Elements().new NewMail_PopUp().receiver_TextInput().inputText(receiver);
		new CustomMethods().simpleWait(2);
		sendKeys("\t");
		
		// Set subject 
		new Elements().new NewMail_PopUp().subject_TextInput().inputText(subject);
		
		// Create file and click upload button
		new CustomMethods().new WorkWith_TextFiles().file_Create(filePath, fileInfo);
		new Elements().new NewMail_PopUp().addFile_Button().click();
		
		// Attach file
		new CustomMethods().new WorkWith_TextFiles().file_Upload(filePath);
		new Elements().new NewMail_PopUp().fileName_Text().waitUntilAvailable();
		assertThat(new Elements().new NewMail_PopUp().fileName_Text().getText(), is(equalTo(fileName)));
		
		// Send mail
		new Elements().new NewMail_PopUp().send_Button().click();
		new Elements().new Managing_Tools().sendNotification_Text().waitUntilAvailable();
	}
	
	// Find mail
	public void mail_Find()
	{
		/*___ Variables ___*/
		String subject = new Elements().new NewMail_PopUp().new Values().subject;
		String senderEmail = new Elements().new Mail_Body().new Values().senderEmail;
		/*________________*/
		
		// Find mail
		new Elements().new Managing_Tools().searchQuery_TextInput().inputText("subject:" + subject);
		new Elements().new Managing_Tools().search_Button().click();
		new Elements().new Mail_Body().mailSubject_Text().waitUntilAvailable();
		
		// Check found mail
		assertThat(new Elements().new Mail_Body().mailSubject_Text().getAttribute("email"), is(equalTo(senderEmail)));
	}
	
	// Open mail
	public void mail_Open()
	{
		/*___ Variables ___*/
		String senderEmail = new Elements().new Mail_Body().new Values().senderEmail;
		/*________________*/
		
		// Click(via JS) on subject
		new CustomMethods().new Js_Actions().webElement_Click(driver, new Elements().new Mail_Body().mailOpener_Custom());
		
		// Check opened email
		new Elements().new Mail_Body().mailSender_Text().waitUntilAvailable();
		assertThat(new Elements().new Mail_Body().mailSender_Text().getAttribute("email"), is(equalTo(senderEmail)));
	}
	
	// Check file info
	public void attachedFile_Check()
	{
		/*___ Variables ___*/
		String fileName = new Elements().new NewMail_PopUp().new Values().fileName;
		String filePath = BASE_DIR + "\\storage\\files\\downloaded_files\\" + fileName;
		String fileMessage = new Elements().new Mail_Body().new Values().fileMessage;
		/*________________*/
		
		// Download file
		Actions act = new Actions(driver);
		act.moveToElement(new Elements().new Mail_Body().attachedFile_Custom()).build().perform();
		new CustomMethods().simpleWait(2);
		new Elements().new Mail_Body().fileDownload_Button().click();
		new CustomMethods().simpleWait(6);
		
		// Check file
		String fileInfo = new CustomMethods().new WorkWith_TextFiles().file_Read(filePath);
		assertThat(fileInfo, is(equalTo(fileMessage)));
	}
	
	// Log out
	public Authorization_Page log_Out()
	{
		new Elements().new UserInfo_Section().userOptions_Button().click();
		new Elements().new UserInfo_Section().exit_Button().waitUntilAvailable();
		new Elements().new UserInfo_Section().exit_Button().click();
		
		return new Authorization_Page(driver).waitUntilAvailable();
	}
	
	
	/*__________________________________________________ Elements _______________________________________________________*/	
	
	private class Elements
	{
		private class Managing_Tools
		{
			// Mail search text input
			private TextInput searchQuery_TextInput() 	{ return new TextInput(driver, By.id("gbqfq")); }
			
			// Mail search button
			private Button search_Button() 				{ return new Button(driver, By.id("gbqfb")); }
			
			// 'Write' button
			private Button write_Button()				{ return new Button(driver, By.xpath("//div[@class='z0']/div")); }
			
			// Mail send notification
			private Text sendNotification_Text() 		{ return new Text(driver, By.id("link_vsm")); }
		}
		
		private class NewMail_PopUp
		{
			// 'Receiver' text input
			private TextInput receiver_TextInput() 		{ return new TextInput(driver, By.xpath("//textarea[@name='to']")); }
			
			// 'Subject' text input
			private TextInput subject_TextInput() 		{ return new TextInput(driver, By.xpath("//input[@name='subjectbox']")); }
			
			// 'Add file' button
			private TextInput addFile_Button()			{ return new TextInput(driver, By.xpath("//div[@command='Files' and @role='button']")); }
			
			// Attached file name
			private Text fileName_Text()			  	{ return new Text(driver, By.xpath("//a[@class='dO']/div[@class='vI']")); }
			
			// 'Send' button
			private Button send_Button()				{ return new Button(driver, By.xpath("//td[@class='gU Up']/div/div[@role='button']")); }
			
			// Text values which will be used in mail pop-up 
			private class Values
			{
				private String receiver = "lxtest.user1";
				private String subject = "Тест";
				private String fileName = "TextFile.txt";
			}
		}
		
		private class Mail_Body
		{
			// Element for mail opening
			private WebElement mailOpener_Custom()				{ return driver.findElement(By.className("zF")); }
			
			// Mail subject at mails list
			private Text mailSubject_Text()						{ return new Text(driver, By.xpath("//*[@class='zF' and @name='я']")); }
			
			// Attached file name
			private Text mailSender_Text()						{ return new Text(driver, By.className("gD")); }
			
			// Attached file
			private WebElement attachedFile_Custom()			{ return driver.findElement(By.className("aSH")); }
			
			// 'Send' button
			private Button fileDownload_Button()				{ return new Button(driver, By.xpath("//div[@data-tooltip-class='a1V' and @role='button']")); }
			
			// Text values which will be used in mail pop-up 
			private class Values
			{
				private String senderEmail = "lxtest.user1@gmail.com";
				private String fileMessage = "Every day may not be a good day,\r\nbut there is something good in every day.";
			}
		}
		
		private class UserInfo_Section
		{
			// User options button
			private Button userOptions_Button()		{return new Button(driver, By.xpath("//a[contains(@href, 'accounts.google.com/SignOutOptions')]"));}
			
			// 'Exit' button
			private Button exit_Button()			{return new Button(driver, By.xpath("//a[contains(@href, 'accounts.google.com/Logout')]"));}
		}
	}
}
