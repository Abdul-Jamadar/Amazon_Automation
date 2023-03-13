package StepDefinitions;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;

import Utilities.Utility;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinition extends Utility {


	String productName;
	
	@Given("I am on Amazon homepage {string} and  I am already a registered user.")
	public void i_am_on_flipkart_homepage_and_i_am_already_a_registered_user(String string) throws Exception {
		setup();
		driver.get(string);
	}

	@When("I login using my phone number and password")
	public void i_login_using_my_phone_number() throws Exception {
		login.LoginToamazon(mobileNumber,urPassword);
	}

	@Then("My username should be visible.")
	public void my_username_should_be_visible() {
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='nav-line-1-container']")).getText()
				.contains(UserName));
	}

	@Then("I search for smartphone {string} and navigate to that products page.")
	public void i_search_for_smartphone_and_navigate_to_that_product_s_page(String productName) throws IOException {
		this.productName = productName;
		product.productsSearch(productName);
	}

	@When("I apply exchange offer for my smartphone model and IMEI number")
	public void i_apply_exchange_offer_with_my_pincode_and_smartphone_model_and_confirm()
			throws Exception {
		exchange.validateExchangeOffer(model, iMEI, productName);
	}

	@Then("I should be able to see the final price.")
	public void i_should_be_able_to_see_the_final_price() {
		waitForElement(By.id("priceAfterBuyBackDiscount"));
		System.out.println(
				"Final Price with exchange is " + driver.findElement(By.id("priceAfterBuyBackDiscount")).getText());
		
	}
	
	@Then("I should be able get the most profitable purchase with highest exchange price")
	public void i_should_be_able_get_the_most_profitable_purchase_with_highest_exchange_price() {
		tearDown();
	}
	}
