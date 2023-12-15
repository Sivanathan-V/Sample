package pageObjects;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;


import utilities.MobileActions;

public class MobileHomePage {
	MobileActions MA=new MobileActions();
	public By footerConnectOptions=By.xpath("//div[@class='connect']/ul/li");
	public By breadCrumbs=By.xpath("//div[contains(@class,'breadcrumbs')]/ul");
	public By Mainmenu=By.xpath("//*[@class='menuIcon']");
	public By footerDropdown=By.id("groupListEnId");
	public By MarketNewsandReportMenu=By.xpath("//*[contains(text(),'Market News & Reports')]");
	public By selectedpaginationnum =By.xpath("//li/child::a[contains(@class,\"select\") and @data-page]");
	public By issuerandfinancial=By.xpath("//*[@class='subMenu3']/child::a[contains(text(),'Issuer & Financial Advisor Announcements')]");
	
	public By PageNavigatorbtn=By.xpath("//*[@id='pagination-ul']/descendant::a[@data-page='3']");
	
	public By language=By.xpath("(//*[@class=\"nav-link lag\"])[2]");
	public By pagination=By.xpath("//a[contains(text(),'3') and @data-page='3']");
	public By element=By.xpath("//*[@id='prev-toggle-id' ] /child::a[@data-page='2']");
	public By allLinks=By.tagName("a");
	public By footerLinks=By.xpath("//div[@class='ftrNav']/ul/li/a");
	public By pagenavlink=By.xpath("//li/a[@data-page]");
	public By footerMenuList=By.xpath("//div[@class='ftrNav']/ul/li");
	public boolean languageSelect() throws Exception {
		
		Thread.sleep(4000);
		if(MA.isDisplayed(language))
		{
			if(MA.gettext(language).equalsIgnoreCase("English")) {
				
				Thread.sleep(1000);
				MA.click(language);
				MA.click(Mainmenu);
				
				
				
				return true;
			}
			else
			{
				return false;
			}
			
		}
		return false;
		
		
	}
	public void selectpagination()throws Exception{
		MA.Waitforelementvisibility(pagination);
		MA.click(pagination);
	}
	public void selectMainMenu() throws Exception {
		Thread.sleep(4000);
		System.out.println("selectMainMenu");
    Thread.sleep(2000);
	MA.click(Mainmenu);
	System.out.println("Mainmenu Clicked");
	Thread.sleep(2000);
	}
     public int getFooterMenuCount() {
		
		return MA.ElementsCount(footerMenuList);
	
		
	}
     public int getPageNavlinkCount() {
 		
 		return MA.ElementsCount(pagenavlink);
 	
 		
 	}
     public int getFooterConnectCount() {
 		return MA.ElementsCount(footerConnectOptions);
 	}
	public void selectMarketNewsMenu() throws Exception {
		Thread.sleep(1000);
		MA.click( MarketNewsandReportMenu);
		System.out.println("MarketNews and Report menu selected");
		
	}
	public void selectIssuerMenu() throws Exception {
		Thread.sleep(1000);
		MA.click(issuerandfinancial);
		System.out.println("Issuer and Financial menu clicked");
		
	}
	public void scrolltonavigate() throws Exception {
		Thread.sleep(1000);
		
		
		MA.scrolltoelement(PageNavigatorbtn);
    
	}
	public void verifyPagination() throws Exception {
		
		MA.isDisplayed(PageNavigatorbtn);
	}
public void verifyPage() throws Exception {
		
		MA.isDisplayed(element);
	}
}
