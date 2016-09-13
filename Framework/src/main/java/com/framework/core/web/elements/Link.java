package com.framework.core.web.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.framework.core.web.WebComponent;

public class Link extends WebComponent<Link>
{
	public Link(WebDriver driver, By findByMethod) 
	{
		super(driver, findByMethod);
	}
}
