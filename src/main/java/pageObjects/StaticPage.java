package pageObjects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import utilities.Reporter;
import utilities.WebDriverActions;

public class StaticPage {
	
	WebDriverActions ui=new WebDriverActions();
	
	public By footerMenuList=By.xpath("//div[@class='ftrNav']/ul/li");
	
	public By footerConnectOptions=By.xpath("//div[@class='connect']/ul/li");
	
	public By footerDropdown=By.id("groupListEnId");
	
	public By twitterI=By.xpath("//*[local-name()='svg']//*[local-name()='g' and @id='Ellipse_32']");
	
	public By twitterIcon=By.xpath("//*[local-name()='svg']//*[local-name()='g' and @id]");

	
	
	/* List<WebElement> simpleTable = driver.findElements(By.xpath("(//*[name()='svg'])[2]//*[name()='text']//*[name()='tspan' and (@dy='4.40625' or @dy='3.609375')]"));
	    System.out.println("size is " + simpleTable.size());
	    for (int i=0;i<simpleTable.size();i++)
	        System.out.println(simpleTable.get(i).getText());*/
	
	
	
	public void footerPageMenu(String varText) throws Exception {
		By dynamicText=By.xpath("//*/li/a[contains(text(),'" + varText + "')]");
		ui.scrollByVisibilityOfElement(dynamicText);
		Thread.sleep(2000);
		ui.click(dynamicText);
	}
	
	public boolean checkStaticPageTitle(String varText) {
		By dynamicText=By.xpath("//*/li/a[contains(text(),'" + varText + "')]");
		return ui.isDisplayed(dynamicText);
	}
	
	public String getStaticLinkText(String varText) {
		By dynamicLinkText=By.xpath("//h1[text()='" + varText + "']/following-sibling::p[1]");
		return ui.getText(dynamicLinkText).replaceAll("[^0-9a-zA-Z. ]", "");
	}
	
	public int getFooterMenuCount() {
		
		return ui.ElementsCount(footerMenuList);
	
		
	}
	
	public int getFooterConnectCount() {
		return ui.ElementsCount(footerConnectOptions);
	}
	
	public void getFooterIcon() {
	
	Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("SVG");
	while (readers.hasNext()) {
	    Reporter.pass("reader: " + readers.next());
	}
	
	}

}
