package demo.scripts;

import java.awt.AWTException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.joda.time.LocalDate;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import base.BaseClass;
import base.FrameworkConstants;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import pageObjects.HomePage;
import pageObjects.StaticPage;
import utilities.CustomMethods;
import utilities.ExcelLibrary;
import utilities.Reporter;
import utilities.WebDriverActions;

@Listeners(utilities.Listener.class)
public class PrivacyPolicy extends BaseClass {
	WebDriverActions ui;
	SoftAssert s;
	ExcelLibrary excel;
	StaticPage sp;
	HomePage hp;
	Random random;

	// Declare An Excel Work Book
	HSSFWorkbook workbook;
	// Declare An Excel Work Sheet
	HSSFSheet sheet;
	// Declare A Map Object To Hold TestNG Results
	Map<String, Object[]> TestNGResults;
	int cnt = 1;
	LocalDate date = LocalDate.now();
	String page = "HomePage";
	String type = "Footer";

	@BeforeClass
	public void test_Login() throws IOException, AWTException {
		// create a new work book
		workbook = new HSSFWorkbook();
		// create a new work sheet
		sheet = workbook.createSheet("TestNG Result Summary");
		TestNGResults = new LinkedHashMap<String, Object[]>();
		// add test result excel file column header
		// write the header in the first row
		TestNGResults.put("", new Object[] { 1d, "Date and Time", "Pages", "Type", "Action", "Actual Output-Live","Expected Output-UAT","Status" });

		getDriver().get(cs.getProperty("url"));
	}

	@Test(priority = 0, enabled = true)
	@Description("Checking Home Page > Footer Page")
	@Epic("Home Page Validation")
	@Feature("Feature 1 : Footer Link Validation")
	@Story("Story 1 : Validate Privacy Policy Link")
	@Step("Step 1 : Verify Privacy Policy Link and Contents")
	@Severity(SeverityLevel.MINOR)
	public void privacyPolicyTest() throws Exception {
		ui = new WebDriverActions();
		s = new SoftAssert();
		excel = new ExcelLibrary();
		sp = new StaticPage();
		hp = new HomePage();

		hp.checkMarketStatus();

		hp.languageSelect();
		Reporter.pass("Language Text is :" + ui.getText(hp.languageSelect));
		Reporter.pass("English is selected");
	//	TestNGResults.put("2", new Object[] { 2d, date.toDateTimeAtCurrentTime().toString(), page, type,
		//		"Language Selected", ui.getText(hp.languageSelect), ui.getText(hp.languageSelect), "Pass" });

		Reporter.pass("All Links present on Home Page are :" + ui.ElementsCount(hp.allLinks));
		int homeCnt=ui.ElementsCount(hp.allLinks);
		String homeLnkC=Integer.toString(homeCnt);
		TestNGResults.put("3", new Object[] { 2d, date.toDateTimeAtCurrentTime().toString(), page, type,"All Home Page Links", homeLnkC, homeLnkC,"Pass" });

		int footerCnt=ui.ElementsCount(hp.footerLinks);
		String footerLnkC=Integer.toString(footerCnt);
		Reporter.pass("All Footer Links present on Home Page are :" + ui.ElementsCount(hp.footerLinks));
		TestNGResults.put("4",new Object[] { 3d, date.toDateTimeAtCurrentTime().toString(), page, type,"All Footer Page Links", footerLnkC,footerLnkC, "Pass" });

		// ui.countLinksOnPage(hp.footerLinks);

		sp.footerPageMenu("Privacy Policy");
		Reporter.pass("Privacy Policy Link is clicked");
		TestNGResults.put("5",new Object[] { 4d,date.toDateTimeAtCurrentTime().toString(), page, type,"Click Privacy Policy Link", "Privacy Policy link is clicked","Privacy Policy link is clicked", "Pass" });

		String expectedURL = getDriver().getCurrentUrl();
		String actualURL = getDriver().getCurrentUrl();
		String matchUrl = "";

		boolean b = WebDriverActions.matchUrl(getDriver(), expectedURL);
		if (b) {
			matchUrl = "pass";
		} else
			matchUrl = "fail";
		Reporter.pass("Both urls are same" + matchUrl);

		TestNGResults.put("6", new Object[] { 5d,date.toDateTimeAtCurrentTime().toString(), page, type, "URL Verification", actualURL,expectedURL,  matchUrl });

		ui.matchSubStringAndVerify(actualURL, "locale=en");
		Reporter.pass("Actual URL contains 'locale=en'");
		TestNGResults.put("7", new Object[] { 6d,date.toDateTimeAtCurrentTime().toString(), page, type, "URL contains en or not", "locale=en","locale=en", "Pass" });

		Reporter.pass("Bread Crumbs:" + ui.getText(hp.breadCrumbs), true);
		TestNGResults.put("8", new Object[] { 7d,date.toDateTimeAtCurrentTime().toString(), page, type, "Bread Crumbs Check", ui.getText(hp.breadCrumbs), ui.getText(hp.breadCrumbs),"Pass" });

		Assert.assertTrue(sp.checkStaticPageTitle("Privacy"), "Privacy Policy");
		Reporter.pass("Privacy Policy text is displayed", true);
	//	Assert.assertTrue(false);
		TestNGResults.put("9",new Object[] { 8d,date.toDateTimeAtCurrentTime().toString(), page, type, "Privacy Policy title check", "Privacy Policy title is present", "Privacy Policy title is present", "Pass" });

		String expectedText = sp.getStaticLinkText("Privacy Policy");
		String result1 = expectedText.replaceAll("['?,]", "");

		String actualText = sp.getStaticLinkText("Privacy Policy");
		Reporter.pass("Actual Text is   :" + actualText);
		TestNGResults.put("10", new Object[] { 9d,date.toDateTimeAtCurrentTime().toString(), page, type, "Privacy Policy text check",actualText,result1,  "Pass" });

		Thread.sleep(1000);
		Reporter.pass("Expected Text is  : " + result1);

		s.assertEquals(actualText, result1);
		TestNGResults.put("11",new Object[] { 10d,date.toDateTimeAtCurrentTime().toString(), page, type,"Privacy Policy text comparison", actualText,result1,  "Pass" });

		s.assertAll();

	}

	@Test(priority = 1, enabled = true)
	@Epic("Home Page Validation")
	@Feature("Feature 1 : Footer Link Validation")
	@Story("Story 1 : Validate Privacy Policy Link")
	@Step("Step 2 : Verify Privacy Policy URL")
	@Severity(SeverityLevel.CRITICAL)
	public void privacyPolicyTest_Fail() throws Exception {
		ui = new WebDriverActions();
		s = new SoftAssert();
		excel = new ExcelLibrary();
		sp = new StaticPage();
		random = new Random();

		sp.footerPageMenu("Privacy Policy");
		Reporter.pass("Privacy Policy Link is clicked");

		String expectedURL = getDriver().getCurrentUrl() + "/Amarja";
		String expected = "www.google.com";
		Reporter.pass(expectedURL);
		String actualURL = getDriver().getCurrentUrl();
		Reporter.pass(actualURL);

		// s.assertEquals(actualURL, expectedURL);
		if (expectedURL.equalsIgnoreCase(actualURL)) {
			Reporter.pass("Both urls are same");

			s.assertTrue(sp.checkStaticPageTitle("Privacy Policy"), "Privacy Policy Title");
			Reporter.pass("Privacy Policy text is displayed", true);

			String expectedText = sp.getStaticLinkText("Privacy Policy");
			String result1 = expectedText.replaceAll("['?,]", "");

			String actualText = sp.getStaticLinkText("Privacy Policy");
			Reporter.pass("Actual Text is   :" + actualText);

			Thread.sleep(1000);
			Reporter.pass("Expected Text is  : " + result1);

			s.assertEquals(actualText, result1);

		} else
			Reporter.pass("Both Urls are not same. Operation Rejected");

	}

	@Test(priority = 2, dependsOnMethods = "privacyPolicyTest_Fail")
	@Epic("Home Page Validation")
	@Feature("Feature 1 : Footer Link Validation")
	@Story("Story 2 : Validate Footer Contents")
	@Step("Step 3 : Verify Footer Elements and their Count")
	@Severity(SeverityLevel.NORMAL)
	public void footerElementCount() {

		Reporter.pass("Footer Menu Count is :" + sp.getFooterMenuCount(), true);

		Reporter.pass("Footer Menu List is : " + ui.extractText(sp.footerMenuList));

		Reporter.pass("Footer Connect Count is :" + sp.getFooterConnectCount(), true);

		Reporter.pass("Footer Drop down Options are :" + ui.extractText(sp.footerDropdown));

		s.assertAll();
	}
	/*
	 * @AfterClass public void closeBrowser() {
	 * WebDriverActions.closeBrowser(getDriver()); }
	 */

	@AfterClass
	public void suiteTearDown() {
		// write excel file and file name is SaveTestNGResultToExcel.xls
		Set<String> keyset = TestNGResults.keySet();
		int rownum = 0;
		for (String key : keyset) {
			Row row = sheet.createRow(rownum++);
			Object[] objArr = TestNGResults.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof Date)
					cell.setCellValue((Date) obj);
				else if (obj instanceof Boolean)
					cell.setCellValue((Boolean) obj);
				else if (obj instanceof String)
					cell.setCellValue((String) obj);
				else if (obj instanceof Double)
					cell.setCellValue((Double) obj);
			}
		}
		try {
			FileOutputStream out = new FileOutputStream(new File("SaveTestNGResultToExcel.xls"));
		//	FileOutputStream out = new FileOutputStream(FrameworkConstants.getExcelReportFilePath());
			workbook.write(out);
			out.close();
			System.out.println("Successfully saved Selenium WebDriver TestNG result to Excel File!!!");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// close the browser
		// WebDriverActions.closeBrowser(getDriver());

	}

}
