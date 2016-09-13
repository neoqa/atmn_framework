package com.framework.core.web.pages.some;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.framework.core.web.WebPage;
import com.framework.core.web.elements.Button;
import com.framework.core.web.elements.Custom;
import com.framework.core.web.elements.Text;
import com.framework.core.web.elements.TextInput;



public class Authorization_Page extends WebPage<Authorization_Page> 
{
	private static final String PAGE_URL = BASE_URL + "/Account/LogOn";
	
	public Authorization_Page(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public Authorization_Page load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return new Elements().banner().isAvailable();
	}

	/*___________________________________________________ Actions _______________________________________________________*/	
	
	public Mail_Page logIn()
	{
		/*___ Variables ___*/
		String email = new Elements().new Values().email;
		String password = new Elements().new Values().password;
		/*________________*/
		
		// Input email and go next
		new Elements().email_Input().inputText(email);
		new Elements().next_Button().click();
		
		// Assert for email being correct and input password
		new Elements().password_Input().waitUntilAvailable();
		assertThat(new Elements().emailDisplay_Text().getText(), is(equalTo(email)));
		new Elements().password_Input().inputText(password);
		
		// Log in
		new Elements().enter_Button().click();
		return new Mail_Page(driver).waitUntilAvailable();
	}
	
	public Mail_Page reLogIn()
	{
		/*___ Variables ___*/
		String email = new Elements().new Values().email;
		String password = new Elements().new Values().password;
		/*________________*/
		
		// Assert for email being correct and input password
		assertThat(new Elements().reAuthEmailDisplay_Text().getText(), is(equalTo(email)));
		new Elements().password_Input().inputText(password);
		
		// Log in
		new Elements().enter_Button().click();
		return new Mail_Page(driver).waitUntilAvailable();
	}
	
	
	
	/*__________________________________________________ Elements _______________________________________________________*/	
	
	private class Elements
	{
		private Custom banner()
		{
			return new Custom(driver, By.className("banner"));
		}
		
		private TextInput email_Input()
		{
			return new TextInput(driver, By.id("Email"));
		}
		
		private Text emailDisplay_Text()
		{
			return new Text(driver, By.id("email-display"));
		}
		
		private Text reAuthEmailDisplay_Text()
		{
			return new Text(driver, By.id("reauthEmail"));
		}
		
		private TextInput password_Input()
		{
			return new TextInput(driver, By.id("Passwd"));
		}
		
		// 'Next' button
		private Button next_Button()
		{
			return new Button(driver, By.id("next"));
		}
		
		// Enter button
		private Button enter_Button()
		{
			return new Button(driver, By.id("signIn"));
		}
		
		// Text values which will be used in mail pop-up 
		private class Values
		{
			private String email = "lxtest.user1@gmail.com";
			private String password = "user1_pw";
		}
	}
}

