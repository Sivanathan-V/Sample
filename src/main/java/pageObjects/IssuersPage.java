package pageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import utilities.Reporter;
import utilities.WebDriverActions;

public class IssuersPage {
	
	WebDriverActions ui=new WebDriverActions();

	//public By publisherDropdown=By.xpath("//h2[contains(text(),'By Publisher')]/following-sibling::div/div/button");
	
	public By publisherDropdown=By.id("searchType");
	
	public By financialAdvData=By.xpath("//ul[@id='announcementResultsDivId']/a/li");
	
	public By fincialData=By.xpath("(//ul[@id='announcementResultsDivId']/a/li)[1]");
	
	public By OneDay=By.id("1D");
	

	
	
}
