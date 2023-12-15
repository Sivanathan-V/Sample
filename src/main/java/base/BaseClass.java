package base;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.xml.DOMConfigurator;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import utilities.ConfigurationSupport;
import utilities.Reporter;

public class BaseClass {

	// public static WebDriver driver;
	public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	public static AndroidDriver androiddriver=null;
	
	
	public static DesiredCapabilities dc=null;
	public static ConfigurationSupport mp=new ConfigurationSupport(FrameworkConstants.getMobilepropertiespath());
	
	public static WebDriver getDriver() {
		return driver.get();
	}
	public static AndroidDriver getandroidDriver() {
		 try {
			 if(androiddriver==null) {
		    	androiddriver=new AndroidDriver(new URL(mp.getProperty("appiumserver_url")),dc);
			 }
		    }
		    catch(Exception e) {
		    	System.out.println("Device setup error:"+e);
		    }
		 return androiddriver;
	}

	public static void setDriver(WebDriver ref) {
		//TestEnvironment="Web";
		driver.set(ref);
	}

	public ConfigurationSupport cs = new ConfigurationSupport(FrameworkConstants.getGlobalpropertiespath());
	String browser = cs.getProperty("browser");
	public String TestEnvironment=cs.getProperty("TestEnvironment");

	// String env=cs.getProperty("testdata");
	// String ver=cs.getProperty("version");

	@BeforeSuite(alwaysRun = true)
	public void report() {
		Reporter.generateReport();
		Reporter.extent.setSystemInfo("Browser used to run : ", browser);
		// Reporter.extent.setSystemInfo("Environment : ", env);
		// Reporter.extent.setSystemInfo("Version : ", ver);

	}
	@BeforeSuite(alwaysRun = true)
	public void mobileSetUp() throws MalformedURLException{
		if(TestEnvironment.contains("Mobile")) {
				
			dc=new DesiredCapabilities();
			dc.setCapability("device",mp.getProperty("device"));
		    dc.setCapability(CapabilityType.PLATFORM_NAME,mp.getProperty("platform"));
		    dc.setCapability(MobileCapabilityType.UDID,mp.getProperty("Udid"));
		    dc.setCapability("deviceName",mp.getProperty("Devicename"));
		    dc.setCapability("platformVersion",mp.getProperty("platformversion"));
		    dc.setCapability(CapabilityType.BROWSER_NAME,mp.getProperty("browsername"));
		    dc.setCapability("chromedriverExecutable",mp.getProperty("chromeexecutablepath"));
	}
		   
		
	}

	@BeforeSuite(alwaysRun = true)
	public void setUp() throws MalformedURLException {
       if(TestEnvironment=="Web") {
		if (Objects.isNull(getDriver()))
			switch (browser) {

			case "edge":
				System.setProperty("webdriver.edge.driver", FrameworkConstants.getEdgedriverpath());
				EdgeOptions eoptions = new EdgeOptions();
				Map<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("download.default_directory", FrameworkConstants.getDownloadpath());
				eoptions.setExperimentalOption("prefs", prefs);
				eoptions.setAcceptInsecureCerts(true);
				driver.set(new EdgeDriver(eoptions));
				getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				break;

			case "chrome":
				System.setProperty("webdriver.chrome.driver", FrameworkConstants.getChromedriverpath());
				ChromeOptions coptions = new ChromeOptions();
				Map<String, Object> pref = new HashMap<String, Object>();
				pref.put("download.default_directory", FrameworkConstants.getDownloadpath());
				coptions.setExperimentalOption("prefs", pref);
				coptions.setAcceptInsecureCerts(true);
				coptions.addArguments("--remote-allow-origins=*");
				driver.set(new ChromeDriver(coptions));
				getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				break;

			default:
				System.out.println("No browser selected");

			}
		getDriver().manage().window().maximize();
	}
	}
	@AfterSuite(alwaysRun = true)
	public void tearDown() {
		System.out.println("browser closed");
		Reporter.endReport();
		setDriver(null);
	}
}
