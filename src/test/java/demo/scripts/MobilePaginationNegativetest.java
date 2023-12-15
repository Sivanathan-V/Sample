package demo.scripts;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
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
public class MobilePaginationNegativetest extends BaseClass  {
	

		MobileActions MA;
		SoftAssert s;
		ExcelLibrary excel;
		MobileHomePage hp;
		IssuersPage ip;
		
		@BeforeClass
		public void test_Login() throws Exception {
			
			getandroidDriver().get(cs.getProperty("url"));
		
		}

		@Test(priority=0, enabled=true)
		public void PaginationObject_Verification() throws Exception{
			
			MA=new MobileActions();
			s=new SoftAssert();
			excel=new ExcelLibrary();
			hp=new MobileHomePage();
			ip=new IssuersPage();
			Reporter.pass("Home page",true);
			hp.selectMainMenu();
			hp.languageSelect();
	        hp.selectMarketNewsMenu();
	        Reporter.pass("Market News & Reports is selected",true);
			hp.selectIssuerMenu();
			Reporter.pass("Issuer & Financial Advisor Announcements is selected",true);
			Reporter.pass("Bread Crumbs:" + MA.gettext(hp.breadCrumbs), true);
		    hp.verifyPagination();
		    
		    Reporter.fail("Pagination is not visible");
			
		}
		

	}



