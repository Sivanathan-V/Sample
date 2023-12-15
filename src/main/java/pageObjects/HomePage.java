package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import utilities.Reporter;
import utilities.WebDriverActions;

public class HomePage {
	WebDriverActions ui=new WebDriverActions();
	
	public By breadCrumbs=By.xpath("//div[contains(@class,'breadcrumbs')]/ul");
	
	public By languageSelect=By.xpath("//div[@class='langSlct']/a");
	
	public By marketStatus=By.xpath("//div[@class='mktStatus']/span[@class='grnIcon'] | //div[@class='mktStatus']/span[@class='redIcon']");
	
	public By marketStatusLink=By.xpath("(//strong[contains(text(),'Market Status')])[1]");
	
	public By marketStatusMsg=By.xpath("//strong/i[contains(text(),'Market Open - Trading ')] | //strong/i[contains(text(),'Market Closed ')]");
	
	public By closeButton=By.xpath("//div[@class='graphBoxClose']");
	
	public By allLinks=By.tagName("a");
	
	
	public By footerLinks=By.xpath("//div[@class='ftrNav']/ul/li/a");
	
	public boolean languageSelect() throws Exception {
		if(ui.isDisplayed(languageSelect)) {
			if(ui.getText(languageSelect).equalsIgnoreCase("English"))
			{
				Thread.sleep(1000);
				ui.click(languageSelect);
				return true;
			}
			else
				return false;
			
		}
		return false;
		
	}
	
	public void selectMainMenu(String varText) throws Exception {
		By dynamicMenu=By.xpath("//a/span[contains(text(),'" + varText + "')]");
		Thread.sleep(2000);
		ui.click(dynamicMenu);
	}
	
	public void selectSubMenu(String varText) throws Exception {
		By dynamicText = By.xpath("//ul[@class='subMenu']//a[text()='" + varText + "']");
		ui.scrollByVisibilityOfElement(dynamicText);
		Thread.sleep(2000);
		ui.click(dynamicText);
	}

	public boolean verifyNavigation(List<String> expected) throws Exception {
		boolean flag = false;
		By dynamicText = By.xpath("//div[contains(@class,'breadcrumbs')]//ul/li");
		List<String> actual = ui.extractText(dynamicText);
		List<String> sorted = expected.stream().sorted().toList();

		Reporter.pass(" actual :" + actual);
		Reporter.pass("expected :" + sorted);
		if (actual.equals(sorted)) {

			flag = true;
		}

		return flag;
	}

	public void printTableData() throws Exception {
		By rowsCount = By.xpath("//table[@id='unlistedBondsMarketWatch']//tr");
		int count = ui.ElementsCount(rowsCount);

		By colsCount = By.xpath("//table[@id='unlistedBondsMarketWatch']//tr[1]/td");
		int colCount = ui.ElementsCount(colsCount);

		Reporter.pass("No. of columns: " + colCount);

		Reporter.pass("No. of rows : " + count);

		for (int i = 1; i <= count; i++) {

			By dynamicRowItem = By.xpath("//table[@id='unlistedBondsMarketWatch']//tr[" + i + "]/td");

			ui.scrollByVisibilityOfElement(dynamicRowItem);
			List<String> actual = ui.extractText(dynamicRowItem);
			Reporter.pass("Row " + i + ": " + actual);
		}

	}

	
	public String checkMarketStatus() throws Exception {
		ui.explicitWait_Visibility(marketStatus);
		if(ui.isDisplayed(marketStatus)) {
			ui.click(marketStatusLink);
			Reporter.pass("Market Status :"+ui.getText(marketStatusMsg),true);
			ui.click(closeButton);
			return ui.getText(marketStatusMsg);
		}
	//	else
		//	return ui.getText(marketStatusMsg);
		return ui.getText(marketStatusMsg);
	}
	
	public int getAllLinksCount() {
		return ui.ElementsCount(allLinks);
	}
	
	public int getFooterLinksCount() {
		return ui.ElementsCount(footerLinks);
	}
	
	//Arslan
	
	public By navigationTabs = By.xpath("//div[contains(@class,'breadcrumbs')]//ul/li");

	



	
}
