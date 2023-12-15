package demo.scripts;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import base.BaseClass;
import pageObjects.HomePage;
import pageObjects.IssuersPage;
import pageObjects.StaticPage;
import utilities.ExcelLibrary;
import utilities.Reporter;
import utilities.WebDriverActions;

@Listeners(utilities.Listener.class)
public class MarketNewsReports extends BaseClass{
	
	WebDriverActions ui;
	SoftAssert s;
	ExcelLibrary excel;
	HomePage hp;
	IssuersPage ip;
	
	@BeforeClass
	public void test_Login() throws Exception {
		hp=new HomePage();
		
		getDriver().get(cs.getProperty("url"));	
	//	hp.languageSelect();
	}
	
	@Test(priority=0, enabled=true)
	public void MarketNewsReportsTest() throws Exception {
		ui=new WebDriverActions();
		s=new SoftAssert();
		excel=new ExcelLibrary();
		hp=new HomePage();
		ip=new IssuersPage();
		
		hp.selectMainMenu("Market News & Reports");
		Reporter.pass("Market News & Reports is selected");
		
		hp.selectSubMenu("Issuer & Financial Advisor Announcements");
		Reporter.pass("Issuer & Financial Advisor Announcements is selected");
		Thread.sleep(2000);
		
		Reporter.pass("Bread Crumbs:"+ui.getText(hp.breadCrumbs),true);
		
		ui.scrollByVisibilityOfElement(ip.publisherDropdown);
		Reporter.pass("Financial Advisor Announcements option is selected",true);
		
		ui.click(ip.publisherDropdown);
		Reporter.pass("Publisher drop down is clicked");
		Thread.sleep(2000);
		
		ui.selectByIndex(ip.publisherDropdown,1);
		ui.click(ip.OneDay);
		
		Thread.sleep(2000);
		
		ui.displayListData(ip.financialAdvData);
	
	}
		

}
