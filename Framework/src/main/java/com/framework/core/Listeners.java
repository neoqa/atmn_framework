package com.framework.core;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

public class Listeners extends TestListenerAdapter  
{	
	@Override
	public void onTestStart(ITestResult tr)
	{
		// ���������� WebDriver
        Object currentClass = tr.getInstance();
        String webDriverName = ((BaseTest) currentClass).getDriver().toString();

		// ���� RemoteWebDriver, �� ����� ���. ����������� � ���������
		if(!webDriverName.contains("RemoteWebDriver"))
		{
			// ����� ������ �����
			new ScreenRecording().startRecording(tr.getTestClass().getRealClass().getSimpleName());
		}
	}
	
	@Override
	public void onTestSuccess(ITestResult tr)
	{
		// �������� �����
        String testName = tr.getTestClass().getRealClass().getSimpleName();
		
		// ���������� ������ ����� + ������� �����
        new ScreenRecording().stopRecording();
		new ScreenRecording().video_Delete(testName);
        
        // ������� � ��� ��������� � ������� �������
		Reporter.log("Test '" + testName + "' <font color=\"green\">PASSED</font>.");
	}
	
	@Override
	public void onTestFailure(ITestResult tr) 
	{
		// �������� �����
        String testName = tr.getTestClass().getRealClass().getSimpleName();
        
        // ������� � ��� ��������� � ������� �����
		Reporter.log("Test '" + testName + "' <font color=\"red\">FAILED</font>.");
        
		/*------------------------------------------ ����� ------------------------------------------*/
		// ���������� ������ �����
        new ScreenRecording().stopRecording();
		
		// ��������� � ����� 'surefire-reports' + ������� ������ � ��� �������
		String videoName = new ScreenRecording().video_Copy(testName);
	    Reporter.log("<br><span>Video - </><a href=\"videos\\"+ videoName +"\">Recorded Video</a>");
	    
	    // ������� �����
		new ScreenRecording().video_Delete(testName);
		/*-------------------------------------------------------------------------------------------*/
		

		/*------------------------------------------ �������� ------------------------------------------*/
		// ���������� WebDriver
        Object currentClass = tr.getInstance();
        WebDriver webDriver = ((BaseTest) currentClass).getDriver();
		
        // ��������� � ����� 'surefire-reports' + ������� ������ � ��� �������
		String screenShotName = new ScreenRecording().screen_Shot(testName, webDriver);
	    Reporter.log("<br><span>Screen Shot - </><a href=\"screenshots\\"+ screenShotName +"\">Captured ScreenShot</a>");
		/*-------------------------------------------------------------------------------------------*/
	}
}
