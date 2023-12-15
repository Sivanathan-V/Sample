package utilities;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import base.BaseClass;
import base.FrameworkConstants;
import io.qameta.allure.Attachment;


public class Reporter extends BaseClass {
	
	private Reporter() {
		
	}
	
	public static ExtentReports extent;
	static ExtentTest parentTest;
	static ExtentSparkReporter sparkReporterAll, sparkReporterFail; 
//	static ExtentAventReporter avent;
//	ExtentKlovReporter klove;
	public static ConfigurationSupport cs=	new ConfigurationSupport(FrameworkConstants.getGlobalpropertiespath());
//	static String env=cs.getProperty("testdata");
	
	public static void generateReport() {
		
		if(Objects.isNull(extent)) {
			extent=new ExtentReports();
		/*	sparkReporterFail=new ExtentSparkReporter(FrameworkConstants.getExtentReportFilePath())
					.filter()
					.statusFilter()
					.as(new Status[] { Status.FAIL})
					.apply();*/
		
			sparkReporterAll=new ExtentSparkReporter(FrameworkConstants.getExtentReportFilePath());
			extent.attachReporter(sparkReporterAll);
			sparkReporterAll.config().setTheme(Theme.STANDARD);
		//	sparkReporter.config().setReportName("Demo UI Automation Report - "+env);
			sparkReporterAll.config().setDocumentTitle("Demo Report");
		}
		
	}
	public static void endReport() {
		if(Objects.nonNull(extent)) {
			extent.flush();
		}
	}
		
	public static void pass(String message) {
		parentTest.pass(message).assignAuthor("Amarja").assignCategory("Regression").assignDevice("chrome");
		
	}
		
	public static void info(String message) {
		parentTest.info(message);
		
	}	
	
	public static void fail(String message) {
		parentTest.fail(message,MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64Image()).build());
		
	}	
	
	public static void skip(String message) {
		parentTest.skip(message);
		
	}	
	
	public static void pass(String message,boolean isScreenshotrequired) {
		if(isScreenshotrequired) {
			parentTest.pass(message,MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64Image()).build());
		} else {
			pass(message);
		}
	}
	
	public static String getBase64Image() {
		
		
		return ((TakesScreenshot)getandroidDriver()).getScreenshotAs(OutputType.BASE64);
		
		
	}
	
	public static void info(String message,boolean isScreenshotrequired) {
		if(isScreenshotrequired) {
			parentTest.pass(message,MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64Image()).build());
		} else {
			info(message);
		}
	}
	
	

}
