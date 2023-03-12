package Scenarios;

import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Utilities.Utility;

public class SearchForProducts extends Utility {
	
	@FindBy(id="twotabsearchtextbox")
	WebElement searchBox;
	
	@FindBy(css="[class='a-size-medium a-color-base a-text-normal']")
	List<WebElement> products;
	
	public SearchForProducts(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void productsSearch(String productName) {
		searchBox.sendKeys(productName + Keys.ENTER);
		for (WebElement e : products) {
			if (e.getText().contains(productName)) {
				e.click();
				break;
			}
		}
	}
}
