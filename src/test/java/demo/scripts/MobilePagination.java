package demo.scripts;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import utilities.MobileActions;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.joda.time.LocalDate;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.Reporter;
import base.BaseClass;
import pageObjects.MobileHomePage;
import org.testng.annotations.Listeners;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import pageObjects.HomePage;
import pageObjects.IssuersPage;
import pageObjects.MobileHomePage;
import utilities.ExcelLibrary;
import utilities.Listener;
import utilities.MobileActions;
import utilities.WebDriverActions;



@Listeners(utilities.Listener.class)
public class MobilePagination extends BaseClass {
	MobileActions MA;
	SoftAssert s;
	ExcelLibrary excel;
	MobileHomePage hp;
	IssuersPage ip;
	Map<String, Object[]> TestNGResults;
	String page = "HomePage";
	String type = "Footer";
	HSSFWorkbook workbook;
	HSSFSheet sheet;
	
	@BeforeClass
	public void test_Login() throws Exception {
		
		getandroidDriver().get(cs.getProperty("url"));
	
	}

	@Test(priority=0, enabled=true)
	public void PaginationObject_Verification() throws Exception{
		LocalDate date = LocalDate.now();
		TestNGResults = new LinkedHashMap<String, Object[]>();
		TestNGResults.put("", new Object[] { 1d, "Date and Time", "Pages", "Type", "Action", "Actual Output-Live","Expected Output-UAT","Status" });
		MA=new MobileActions();
		s=new SoftAssert();
		excel=new ExcelLibrary();
		hp=new MobileHomePage();
		ip=new IssuersPage();
		Reporter.pass("Home page"+MA.ElementsCount(hp.allLinks),true);
		int homeCnt=MA.ElementsCount(hp.allLinks);
		String homeLnkC=Integer.toString(homeCnt);
		
		int footerCnt=MA.ElementsCount(hp.footerLinks);
		String footerLnkC=Integer.toString(footerCnt);
		Reporter.pass("All Footer Links present on Home Page are :" + MA.ElementsCount(hp.footerLinks));
		hp.selectMainMenu();
		hp.languageSelect();
        hp.selectMarketNewsMenu();
        Reporter.pass("Market News & Reports is selected",true);
		hp.selectIssuerMenu();
		Reporter.pass("Issuer & Financial Advisor Announcements is selected",true);
		Reporter.pass("Bread Crumbs:" + MA.gettext(hp.breadCrumbs), true);
	    hp.scrolltonavigate();
	    Reporter.pass("All Pagenation Links present in the Issuer & Financial Advisor Page-"+MA.gettext(hp.selectedpaginationnum)+" are:"+MA.ElementsCount(hp.pagenavlink),true);
	    hp.selectpagination();
	    Reporter.pass("pagination is clicked",true);
	    hp.verifyPage();
	    Reporter.pass("All Pagenation Links present in the Issuer & Financial Advisor Page-"+MA.gettext(hp.selectedpaginationnum)+" are:"+MA.ElementsCount(hp.pagenavlink),true);
		
	}
	public void footerElementCount() {

		Reporter.pass("Footer Menu Count is :" + hp.getFooterMenuCount(), true);

		Reporter.pass("Footer Menu List is : " + MA.extractText(hp.footerMenuList));

		Reporter.pass("Footer Connect Count is :" + hp.getFooterConnectCount(), true);

		Reporter.pass("Footer Drop down Options are :" + MA.extractText(hp.footerDropdown));
        Reporter.pass("Pagination count are:"+hp.getPageNavlinkCount(),true);
        
		s.assertAll();
	}
	

}
