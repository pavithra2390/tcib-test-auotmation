package com.temenos.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Footers {

	private WebDriver driver;
	
	@FindBy(css="sample_footer_element")
	private WebElement footer_header;

		
	public Footers(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
}
