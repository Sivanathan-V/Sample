package demo.scripts;


import java.awt.AWTException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import base.BaseClass;
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
public class UnlistedCorporate extends BaseClass {
	WebDriverActions ui;
	SoftAssert s;
	ExcelLibrary excel;
	HomePage home;
	Random random;
	// Declare An Excel Work Book
		HSSFWorkbook workbook;
		// Declare An Excel Work Sheet
		HSSFSheet sheet;
		// Declare A Map Object To Hold TestNG Results
		Map<String, Object[]> TestNGResults;

	@BeforeClass
	public void test_Login() throws IOException, AWTException {
		// create a new work book
		workbook = new HSSFWorkbook();
		// create a new work sheet
		sheet = workbook.createSheet("TestNG Result Summary");
		TestNGResults = new LinkedHashMap<String, Object[]>();
		// add test result excel file column header
		// write the header in the first row
		TestNGResults.put("1", new Object[] {1d, "Test Step No.", "Action", "Expected Output", "Actual Output" });

		getDriver().get(cs.getProperty("url"));
	}

	@Test(priority = 0, enabled = true)
	@Description("Checking Home Page > Header Page")
	@Epic("Home Page Validation")
	@Feature("Feature 2: Header Validation")
	@Story("Story 1 : Validate Unlisted Corporate Sukuk")
	@Step("Step 1 : Home > Our Markets > Sukuk / Bonds > Unlisted Corporate Sukuk/Bonds")
	@Severity(SeverityLevel.BLOCKER)
	public void UnlistedCorporateSukuk() throws Exception {
		ui = new WebDriverActions();
		s = new SoftAssert();
		excel = new ExcelLibrary();
		home = new HomePage();

		home.selectMainMenu("Our Markets");
		Reporter.pass("Clicked on our markets"); 
		TestNGResults.put("2", new Object[] { 2d, "Clicked on Our Markets", "Our Markets gets Clicked", "Pass" });

		home.selectSubMenu("Unlisted Sukuk/Bonds");
		Reporter.pass("Clicked On Unlisted Corporate Sukuk/Bonds");
		TestNGResults.put("3", new Object[] { 3d, "Clicked on Unlisted Corporate Sukuk/Bonds", "Clicked on Unlisted Corporate Sukuk/Bonds", "Pass" });

		Thread.sleep(3000);

		List exp = new ArrayList<String>();
		exp = List.of("Home", "Our Markets", "Sukuk / Bonds", "Unlisted Corporate Sukuk/Bonds");
		s.assertTrue(home.verifyNavigation(exp), " Navigation is validated");


		home.printTableData();
		TestNGResults.put("4", new Object[] { 4d, "Table data is printed", exp.toString(), "Pass"});

	}
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
			workbook.write(out);
			out.close();
			System.out.println("Successfully saved Selenium WebDriver TestNG result to Excel File!!!");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// close the browser
	//	WebDriverActions.closeBrowser(getDriver());
	
	}

}
