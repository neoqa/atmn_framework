package com.framework.tests.some;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.framework.core.BaseTest;
import com.framework.core.web.CustomMethods;
import com.framework.core.web.pages.some.Authorization_Page;
import com.framework.core.web.pages.some.Mail_Page;

public class Gmail_Test extends BaseTest 
{	
	@BeforeMethod(alwaysRun = true, dependsOnMethods = {"setUp"})
	public void file_Delete()
	{
        // Delete text file
        String filePath = base_dir +"\\storage\\files\\downloaded_files\\TextFile.txt";
		new CustomMethods().new WorkWith_TextFiles().file_Delete(filePath);
	}
	
	@Test(groups = { "Gmail_Test" })
	public void GmailTask_TestMethod()
	{	
			// Log in
			Authorization_Page authorization_Page = new Mail_Page(driver).redirectTo_LogInPage();
			Mail_Page mail_Page = authorization_Page.logIn();
			
			// Send mail and log out
			mail_Page.mail_Send();
			authorization_Page = mail_Page.log_Out();
			
			// Log in by 2nd user(i don't have one, so will use same user :D)
			mail_Page = authorization_Page.reLogIn();
			
			// Find and read mail
			mail_Page.mail_Find();
			mail_Page.mail_Open();
			
			// File check
			mail_Page.attachedFile_Check();
			
			// Log out
			mail_Page.log_Out();
	}
}
