package utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestRunner;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import base.BaseClass;
import base.FrameworkConstants;
import io.qameta.allure.Attachment;

public final class AllureListener implements ITestListener,ISuiteListener {

	//Action action= new Action();
//	public WebDriver driver;
	
	public void onTestStart(ITestResult result) {
		Reporter.parentTest = Reporter.extent.createTest(result.getName());
		Reporter.parentTest.assignCategory(result.getMethod().getGroups());
	}

	public void onTestSuccess(ITestResult result) {
		if (result.getStatus() == ITestResult.SUCCESS) {
			Reporter.parentTest.log(Status.PASS, 
					MarkupHelper.createLabel(result.getName() + " - Test Passed", ExtentColor.GREEN));
		}
	}

	public void onTestFailure(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			Reporter.parentTest.log(Status.FAIL, 
						MarkupHelper.createLabel(result.getName() + " - Test Failed", ExtentColor.RED));
			Reporter.parentTest.log(Status.FAIL, 
					MarkupHelper.createLabel(result.getThrowable().getMessage() , ExtentColor.RED));
			Reporter.fail("Failed");
			saveFailureScreenshot(BaseClass.getDriver());
			}
		saveTextLog(getTestMethodName(result));
	}

	public void onTestSkipped(ITestResult result) {
		if (result.getStatus() == ITestResult.SKIP) {
			Reporter.parentTest.log(Status.SKIP, 
					MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.BLUE));
		Reporter.parentTest.log(Status.SKIP, 
				MarkupHelper.createLabel(result.getThrowable().getMessage() + " - Test Case Skipped" , ExtentColor.BLUE));
		
		}
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		
	}
	
	public void onStart(ITestContext context) {
		
	}

	public void onFinish(ITestContext context) {
		
	}

	public static String screenShot(WebDriver driver, String filename) {
		String dateName = new SimpleDateFormat("HH_mm").format(new Date());
		
		TakesScreenshot takeScreenshot=(TakesScreenshot) driver;
		File source = takeScreenshot.getScreenshotAs(OutputType.FILE);
		String destination=FrameworkConstants.getScreenshotPath() + filename + "_" + dateName + ".png";
		

		try {
			FileUtils.copyFile(source, new File(destination));
		} catch (Exception e) {
			e.getMessage();
		}

		return destination;
	}
	
	@Override
	public void onStart(ISuite suite) {
		ConfigurationSupport cs=new ConfigurationSupport(FrameworkConstants.getGlobalpropertiespath());
		try {
			cs.setProperty("suite",suite.getName());
		}catch(Throwable e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onFinish(ISuite suite) {
		WebDriverActions ele=new WebDriverActions();
		try {
			ele.openFile(FrameworkConstants.getExtentReportFilePath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getTestMethodName(ITestResult result) {
		return result.getMethod().getConstructorOrMethod().getName();
	}
	
	@Attachment
	public byte[] saveFailureScreenshot(WebDriver driver) {
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}

	@Attachment(value = "{0}", type = "text/html")
	public static String saveTextLog(String message) {
		return message;
	}
	

}




