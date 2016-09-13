package com.framework.core.web;

import org.openqa.selenium.WebDriver;

import com.framework.core.EnvironmentConfiguration;
import com.framework.core.YamlEnvironment;

public abstract class WebPage<T extends WebPage<T>> extends Component<T>
{
	private static final EnvironmentConfiguration CONFIG = EnvironmentConfiguration.getConfig();
	private static final YamlEnvironment ENVIRONMENT = CONFIG.getEnvironmentSettings();
	protected static final String BASE_URL = ENVIRONMENT.scheme + "://" + 
			       						     ENVIRONMENT.host; //+ ":" + 
			       						     //ENVIRONMENT.port;
	protected static final String BASE_DIR = System.getProperties().get("basedir").toString();
	protected static final String TextFiles_Path = BASE_DIR + "\\storage\\files\\temp_files\\text_files\\";
	
	public WebPage(WebDriver driver)
	{
		super(driver);
	}
	
	public abstract T load();
	
	public T loadAndWaitUntilAvailable()
	{
		load();
		return waitUntilAvailable();
	}
	
/*	// Возврат на главную страничку
	public MainPage backToMainPage()
	{
		return new CommonActions().backToMainPage(driver);
	}
	
	// Выход из системы
	public LogInPage user_Out()
	{
		return new CommonActions().userOut(driver);
	}*/
}
