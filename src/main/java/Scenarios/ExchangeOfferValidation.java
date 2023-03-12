package Scenarios;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import Utilities.Utility;

public class ExchangeOfferValidation extends Utility {

	public ExchangeOfferValidation(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = "div[id*=core] span[class='a-price-whole']")
	WebElement initialPrice;

	@FindBy(css = "input[aria-labelledby*=applyBuyBack]")
	WebElement applyButton;

	@FindBy(id = "buyBackAccordionRow")
	WebElement exchangeRow;

	@FindBy(name = "buyBackDropDown1")
	WebElement dropdown1;

	@FindBy(xpath = "//div[@id='leftColumnContent']")
	WebElement content;

	@FindBy(xpath = "//*[@id='physicalBody_noDamageColumn']//i")
	WebElement noDamage;

	@FindBy(xpath = "//*[@id='valueCommensurateDiscountPrice']")
	WebElement exchangePrice;

	@FindBy(id = "buyBackIMEITextInput")
	WebElement iMEITextBox;

	@FindBy(id = "buyBackApplyButton")
	WebElement verifyIMEI;

	By element1 = By.xpath("//*[@id='buyBackAccordionRow']//div[@class='a-box-inner a-accordion-row-container']");

	By element2 = By.xpath("div[@class='a-box-inner a-accordion-row-container']");

	public void validateExchangeOffer(String model, String IMEI, String productName) throws Exception {
		Set<String> ids = driver.getWindowHandles();
		Iterator<String> itr = ids.iterator();
		while (itr.hasNext()) {
			driver.switchTo().window(itr.next());
		}
		String price = initialPrice.getText();
		System.out.println("Initail price is " + price);
		getScreenShot(productName);
		waitForElement(element1);
		exchangeRow.findElement(element2).click();
		waitForElement(By.id("chooseButton"));
		exchangeRow.findElement(By.id("chooseButton")).click();

		Select sel = new Select(dropdown1);
		sel.selectByValue(model.split("-")[0]);
		Thread.sleep(1000);
		Select sel1 = new Select(driver.findElement(By.name(model.split("-")[0])));
		sel1.selectByValue(model.split("-")[1]);
		waitForElement(By.xpath("//div[@id='leftColumnContent']"));
		content.click();
		noDamage.click();
		String exchange_value = exchangePrice.getText();
		iMEITextBox.sendKeys(IMEI);
		verifyIMEI.click();
		waitForElement(By.cssSelector("input[aria-labelledby*=applyBuyBack]"));
		applyButton.click();
		waitForElement(By.id("priceAfterBuyBackDiscount"));
		String final_price = driver.findElement(By.id("priceAfterBuyBackDiscount")).getText();
		setData(productName, price, exchange_value, final_price);
	}
}