package demo.scripts;

import static org.testng.AssertJUnit.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class UIMapLoginTest {
	public WebDriver driver;
	public UIMap uimap;
	public UIMap datafile;
	public String workingDir;
	public static String driverPath = "C:\\Users\\12943\\git\\TS\\executables\\chromedriver.exe";

	@Test
	public void login() throws Exception {

		// Get object map file
		uimap = new UIMap(workingDir + "\\resources\\locator.properties");

		// Get the username element
		WebElement username = driver.findElement(uimap.getLocator("Username_field"));
		username.sendKeys(datafile.getData("username"));

		// Get the password element
		WebElement password = driver.findElement(uimap.getLocator("Password_field"));
		password.sendKeys(datafile.getData("password"));

		// Click on the Login button
		WebElement login = driver.findElement(uimap.getLocator("Login_button"));
		login.click();

		Thread.sleep(3000);
		// Assert the user login by checking the Online user
//		WebElement onlineuser = driver.findElement(uimap.getLocator("online_user"));
//		assertEquals("Hi, John Smith", onlineuser.getText());
	}

	@BeforeMethod
	public void setUp() throws Exception {

		// Get current working directory and load data file
		workingDir = System.getProperty("user.dir");
		datafile = new UIMap(workingDir + "\\resources\\datafile.properties");

		// Create a new instance of the Firefox driver
		System.setProperty("webdriver.chrome.driver",driverPath);

		ChromeOptions coptions = new ChromeOptions();
		coptions.addArguments("--remote-allow-origins=*");
		driver = new ChromeDriver(coptions);
		driver.get("http://phptravels.net/login");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
	}

	@AfterMethod
	public void afterMethod() throws Exception {
		driver.quit();
	}

}
