package utilities;

import java.awt.Desktop;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.time.Duration;
import java.time.LocalDate;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import base.BaseClass;
import base.FrameworkConstants;
import utilities.Reporter;

public final class WebDriverActions extends BaseClass {

	public WebDriver driver = getDriver();

	// opens Url
	public static void openUrl(WebDriver driver, String URL) {

		try {
			
			driver.get(URL);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Not able to open url: ");
		}
	}

	// returns current window url
	public static String getUrl(WebDriver driver) {
		String url = driver.getCurrentUrl();
		return url;
	}

	// returns current window title
	public static String getWindowTitle(WebDriver driver) {
		String title = driver.getTitle();
		return title;
	}

	// navigates to last webpage
	public static void previousPage(WebDriver driver) {
		try {
			driver.navigate().back();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("not able to go to back page");
		}
	}

	// navigates to next webpage if available
	public static void nextPage(WebDriver driver) {
		try {
			driver.navigate().forward();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("not able to go to forward page");
		}
	}

	// opens new url in already opened browser
	public static void navigateToUrl(WebDriver driver, String url) {
		try {
			driver.navigate().to(url);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("not able to go to navigate to url");
		}
	}

	// refreshes current webpage
	public static void refreshPage(WebDriver driver) {
		try {
			driver.navigate().refresh();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// checks if current matches with expected and returns true if matches
	public static boolean matchUrl(WebDriver driver, String expected) {

		boolean flag = false;
		String actual = driver.getCurrentUrl();
		if (actual.equals(expected)) {
			flag = true;
		}
		return flag;
	}

	// closes browser
	public static void closeBrowser(WebDriver driver) {
		try {
			driver.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("not able to close browser");
		}
	}

	// adds cookie to browser site
	public void addCookie(Cookie cookie) {
		driver.manage().addCookie(cookie);
	}

	public Cookie getCookie() {
		return driver.manage().getCookieNamed("userCrnList");
	}

	// delete specific cookie from browser
	public void deleteCookie(String cookiename) {
		driver.manage().deleteCookieNamed(cookiename);
	}

	// static method to wait till specified time when loading page
	public void pageLoadTimeOut(WebDriver driver, int timeOut) {
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(timeOut));
	}

	// implicit wait method
	public void implicitMinWait() {
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(FrameworkConstants.getImplicit()));
	}

	// implicit wait method
	public void implicitMediumWait() {
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(FrameworkConstants.getImplicit() * 2));
	}

	// implicit wait method
	public void implicitMaxWait() {
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(FrameworkConstants.getImplicit() * 3));
	}

	// explicit wait method
	public void explicitWait_Visibility(By by) {
		WebElement element = driver.findElement(by);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(FrameworkConstants.getExplicit() * 5));
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	// explicit wait method
	public void explicitWait_Clickability(By by) {
		WebElement element = driver.findElement(by);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(FrameworkConstants.getExplicit() * 2));
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	// fluent wait method
	public void fluentWaitforelementvisibility(By by) {
		Wait<WebDriver> wait = null;
		try {
			WebElement element = driver.findElement(by);
			wait = new FluentWait<WebDriver>((WebDriver) driver)
					.withTimeout(Duration.ofSeconds(FrameworkConstants.getExplicit() * 3))
					.pollingEvery(Duration.ofSeconds(3)).ignoring(Exception.class);
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {

		}
	}

	// used to perform click using actions class and javascript executor operation
	public void click(By by) throws Exception {
		try {
			WebElement ele = driver.findElement(by);
			Actions act = new Actions(driver);
			act.moveToElement(ele).click().build().perform();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("not able to click");
		}
	}

	public void JSclick(By by) throws Exception {
		try {
			WebElement ele = driver.findElement(by);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", ele);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("not able to java script click");
		}
	}

//used to type text in textbox after clearing it
	public void type(By by, String testData) {
		try {
			WebElement ele = driver.findElement(by);
			ele.clear();
			ele.sendKeys(testData);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("not able to type due to " + e);
		}
	}

//used to build mouseHover action on WebElement
	public void mouseover(By by) throws Exception {
		try {
			WebElement ele = driver.findElement(by);
			Actions ac = new Actions(driver);
			ac.moveToElement(ele).build().perform();

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("not able to mouse over ");
		}
	}

//used to build Keyboard Key combinations on WebElement
	public void keyboardCtrlOp(String keyChar) {
		Actions ac = new Actions(driver);
		ac.sendKeys(Keys.chord(Keys.CONTROL, keyChar)).build().perform();
	}

	public void keyboardShiftOp(String keyChar) {
		Actions ac = new Actions(driver);
		ac.sendKeys(Keys.chord(Keys.SHIFT, keyChar)).build().perform();
	}

	public void keyboardTab() {
		Actions ac = new Actions(driver);
		ac.sendKeys(Keys.TAB).build().perform();
	}

	public void keyboardDown() {
		Actions ac = new Actions(driver);
		ac.sendKeys(Keys.ARROW_DOWN).build().perform();
	}

	public void keyboardEnter() {
		Actions ac = new Actions(driver);
		ac.sendKeys(Keys.ENTER).build().perform();
	}

//used to retrieve text from WebElement
	public String getText(By by) {
		WebElement ele = driver.findElement(by);
		String text = ele.getText();
		return text;
	}

	public String getURL() {
		String text = driver.getCurrentUrl();
		return text;
	}

	public boolean isDisplayed(By by) {
		WebElement ele = driver.findElement(by);
		boolean t = ele.isDisplayed();
		return t;
	}

	public boolean isSelected(By by) {

		WebElement ele = driver.findElement(by);
		boolean t = ele.isSelected();
		return t;
	}

	public boolean isEnabled(By by) {

		WebElement ele = driver.findElement(by);
		boolean t = ele.isEnabled();
		return t;

	}

	public boolean isElementPresent(By by) {
		List<WebElement> list = driver.findElements(by);
		boolean present = false;
		if (list.size() > 0) {
			present = true;
		}
		return present;
	}

	// scrolls control to given webelement
	public void scrollByVisibilityOfElement(By by) {
		try {
			WebElement ele = driver.findElement(by);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView();", ele);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void selectByIndex(By by, int index) {
		try {
			WebElement ele = driver.findElement(by);
			Select s = new Select(ele);
			s.selectByIndex(index);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Not able to select by Index");
		}
	}

	public void selectByValue(By by, String value) {
		try {
			WebElement ele = driver.findElement(by);
			Select s = new Select(ele);
			s.selectByValue(value);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Not able to select by value");
		}
	}

//used to retrieve all options from dropdown using Select class
	public List<WebElement> getAllOptions(By by) {
		WebElement ele = driver.findElement(by);
		Select s = new Select(ele);
		List<WebElement> AllOptions = s.getOptions();
		return AllOptions;
	}

//used to check on webelement
	public boolean check(By by) throws Exception {
		WebElement ele = driver.findElement(by);
		boolean flag = false;
		if (ele.isSelected()) {
			flag = true;

		} else {
			ele.click();
			flag = true;
		}
		return flag;
	}

//used to uncheck element
	public boolean uncheck(By by) throws Exception {
		WebElement ele = driver.findElement(by);
		boolean flag = false;
		if (!ele.isSelected()) {

		} else {
			ele.click();
			flag = true;

		}
		return flag;
	}

	// get Attribute//check
	public String getAttribute(By by, String Attributename) {
		WebElement ele = driver.findElement(by);
		String AttrValue = ele.getAttribute(Attributename).toString();
		return AttrValue;
	}

	// Element Count
	public int ElementsCount(By by) {

		List<WebElement> elements = driver.findElements(by);
		int count = elements.size();
		return count;
	}

// verify wheather substring present
	public boolean matchSubStringAndVerify(String actualText, String expectedText) {

		boolean flag = false;
		if (actualText.contains(expectedText)) {
			flag = true;
		}
		return flag;

	}

//used to remove before and after white spaces from String
	public static String trimText(String text) {
		String trimStr = text.trim();
		return trimStr;
	}

//used to check whether String is starting with particular String or not
	public static boolean startsWithText(String text, String startsStr) {
		boolean flag = false;
		if (text.startsWith(startsStr)) {
			flag = true;
		}
		return flag;
	}

//used to replace all Special characters and Digits from String
	public static String replaceSpecialSymbol(String text) {
		String alphaOnly = text.replaceAll("[^a-zA-Z]+", "");
		return alphaOnly;
	}

//check
	public int getColumncount(By by) {
		WebElement row = driver.findElement(by);
		List<WebElement> columns = row.findElements(By.tagName("td"));
		int a = columns.size();
		return a;
	}

	// extracts text from list of webelements into another list
	public List<String> extractText(By by) {
		List<WebElement> elements = driver.findElements(by);
		List<String> texts = elements.stream().map(element -> element.getText()).collect(Collectors.toList());
		List<String> text = texts.stream().sorted().toList();
		return text;

	}

	// display list data
	public void displayListData(By by) throws Exception {
		List<WebElement> elements = driver.findElements(by);
		List<String> list = new ArrayList<String>();
		for (WebElement e : elements) {
			list.add(e.getText().toString());
			Reporter.pass("Row Data : " + e.getText());
		}

	}

//drag and drop
	public void DragAndDrop(WebElement From, WebElement To) {
		try {
			Actions ac = new Actions(driver);
			ac.dragAndDrop(From, To).build().perform();

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Not able to drag and drop");
		}
	}

	public void SwitchToChild() {
		try {
			String mainWindowHandle = driver.getWindowHandle();
			Set<String> allWindowHandles = driver.getWindowHandles();
			Iterator<String> iterator = allWindowHandles.iterator();

			while (iterator.hasNext()) {
				String ChildWindow = iterator.next();
				if (!mainWindowHandle.equalsIgnoreCase(ChildWindow)) {
					driver.switchTo().window(ChildWindow);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Can't switch to child window");
		}
	}

	public void SwitchBack() {
		try {
			String currentwindow = driver.getWindowHandle();
			Set<String> s1 = driver.getWindowHandles();
			Iterator<String> i1 = s1.iterator();

			while (i1.hasNext()) {
				String ChildWindow = i1.next();
				if (!currentwindow.equalsIgnoreCase(ChildWindow)) {
					driver.switchTo().window(ChildWindow);
					driver.close();
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Can't switch back");
		}
	}

	public void SwitchToParentFrame() {
		try {
			driver.switchTo().parentFrame();

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Can't switch to parent frame");
		}
	}

	public List<String> printTable(By by) {
		List<WebElement> allRows = driver.findElements(by);
		List<String> list = new ArrayList();
		for (WebElement row : allRows) {
			List<WebElement> cells = row.findElements(By.tagName("td"));

			for (WebElement cell : cells) {
				System.out.println();
				list.add(cell.getText());

			}
		}
		return list;
	}

	public String getRowIndex(String text) {
		WebElement table = driver.findElement(By.xpath(""));
		String dataRowIndex = table.getAttribute("data-Row-index");
		return dataRowIndex;
	}

	public boolean openFile(String FilePath) throws IOException {
		try {
			File file = new File(FilePath);
			Desktop desktop = Desktop.getDesktop();
			if (!Desktop.isDesktopSupported()) {
				return false;
			}
			if (file.exists()) {
				desktop.open(file);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void countLinksOnPage(By by) {
		 String homePage ="https://www.saudiexchange.sa/";
		String url = "";
		HttpURLConnection huc = null;
		int respCode = 200;
		int broken=0;
		int empty=0;
		int otherDomain=0;
		int otherLinks=0;
		int valid=0;
		List<WebElement> links = driver.findElements(By.tagName("a"));
		Iterator<WebElement> it = links.iterator();
		while (it.hasNext()) {
			url = it.next().getAttribute("href");
			System.out.println(url);

			/*if (url == null || url.isEmpty()) {
				System.out.println("URL is either not configured for anchor tag or it is empty");
				continue;
			}

			if (!url.startsWith(homePage)) {
				System.out.println("URL belongs to another domain, skipping it.");
				continue;
			}

			try {
				huc = (HttpURLConnection) (new URL(url).openConnection());

				huc.setRequestMethod("HEAD");

				huc.connect();

				respCode = huc.getResponseCode();

				if (respCode >= 400) {
					Reporter.pass(url+" is a broken link");
					broken++;
				} else {
					Reporter.pass(url+" is a valid link");
					valid++;
				}

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		Reporter.pass("Broken Link Count :"+broken);
		Reporter.pass("Valid Link Count :"+valid);*/
			
			if (url == null || url.isEmpty()) {
				System.out.println("URL is either not configured for anchor tag or it is empty");
				empty++;
				continue;
			}

			if (!url.startsWith(homePage)) {
				System.out.println("URL belongs to another domain, skipping it.");
				otherDomain++;
				continue;
			}

			try {
				huc = (HttpURLConnection) (new URL(url).openConnection());
				huc.setRequestMethod("HEAD");
				huc.connect();
				respCode = huc.getResponseCode();

				if (respCode >= 400) {
					Reporter.pass(url+" is a broken link");
					broken++;
				} if (respCode == 200){
					Reporter.pass(url+" is a valid link");
					valid++;
				}
				else if (respCode > 200 && respCode<400 ){
					Reporter.pass(url+" is a valid link");
					otherLinks++;
				}

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void verifyLink(String url) {
		/*
		 * try { URL link = new URL(url); HttpURLConnection httpURLConnection =
		 * (HttpURLConnection) link.openConnection();
		 * httpURLConnection.setConnectTimeout(3000); // Set connection timeout to 3
		 * seconds httpURLConnection.connect();
		 * 
		 * 
		 * if (httpURLConnection.getResponseCode() == 200) { Reporter.pass(url + " - " +
		 * httpURLConnection.getResponseMessage()); } else { Reporter.pass(url + " - " +
		 * httpURLConnection.getResponseMessage() + " - " + "is a broken link"); } }
		 * catch (Exception e) { Reporter.pass(url + " - " + "is a broken link"); }
		 */
	}
}
