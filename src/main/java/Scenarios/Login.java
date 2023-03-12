package Scenarios;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Utilities.Utility;

public class Login extends Utility {
	
	@FindBy(css="a[data-nav-role='signin']")
	WebElement signIn;
	
	@FindBy(css="input[type=email]")
	WebElement text1;
	
	@FindBy(css="input[type=password]")
	WebElement text2;
	
	By signInButton=By.cssSelector("a[data-nav-role='signin']");
	By phone=By.cssSelector("input[type=email]");
	By Password=By.cssSelector("input[type=password]");
	
	public Login(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void LoginToamazon(String number, String pass) throws IOException {
		waitForElement(signInButton);
		signIn.click();
		waitForElement(phone);
		text1.sendKeys(number + Keys.ENTER);
		waitForElement(Password);
		text2.sendKeys(pass + Keys.ENTER);
	}
}