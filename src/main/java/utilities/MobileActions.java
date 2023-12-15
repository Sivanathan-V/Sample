package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.BaseClass;
import base.FrameworkConstants;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;

import static java.time.Duration.ofMillis;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static io.appium.java_client.touch.offset.PointOption.point;

public final class MobileActions extends BaseClass {
	public AndroidDriver androiddriver= (AndroidDriver) getandroidDriver();
	public void click(By by) throws Exception {
		try {
			System.out.println("in click action");
			WebElement element=androiddriver.findElement(by);
			element.click();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("not able to click");
		}
	}
	public int ElementsCount(By by) {

		List<WebElement> elements = androiddriver.findElements(by);
		int count = elements.size();
		return count;
	}
	public List<String> extractText(By by) {
		List<WebElement> elements = androiddriver.findElements(by);
		List<String> texts = elements.stream().map(element -> element.getText()).collect(Collectors.toList());
		List<String> text = texts.stream().sorted().toList();
		return text;

	}
	public void Waitforelementvisibility(By by) {
		Wait<AndroidDriver> wait = null;
		try {
			WebElement element = androiddriver.findElement(by);
			wait = new FluentWait<AndroidDriver>((AndroidDriver)androiddriver)
					.withTimeout(Duration.ofSeconds(FrameworkConstants.getExplicit() * 3))
					.pollingEvery(Duration.ofSeconds(3)).ignoring(Exception.class);
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {

		}
	}
	public String gettext(By by) throws Exception {
		
			WebElement element = androiddriver.findElement(by);
			String text = element.getText();
			return text;
			
	}
	public boolean isDisplayed(By by) {
		WebElement element = androiddriver.findElement(by);
		boolean bn= element.isDisplayed();
		if(bn==false) {
			System.out.println("Element is not in the page");
		}
		else {
			System.out.println("Element is visible");
		}
		return bn;
	}
	
	public void scrolldown() {
		Dimension size = androiddriver.manage().window().getSize();
		int width = size.width / 2;	
		int startPoint = (int) (size.getHeight() * 0.20);
		int endPoint = (int) (size.getHeight() * 0.80);		
		for(int i=0;i<2;i++) {		
		new TouchAction(androiddriver).press(PointOption.point(width, startPoint)).waitAction(waitOptions(ofMillis(2000)))
		.moveTo(PointOption.point(width, endPoint)).release().perform();	
		}
	}
	public void scrolltoelement(By by){
		androiddriver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
		WebElement element=androiddriver.findElement(by);
		JavascriptExecutor js = (JavascriptExecutor)getandroidDriver();
		js.executeScript("arguments[0].scrollIntoView(true);",element);
		System.out.println("Element exist");
	}
	

}
