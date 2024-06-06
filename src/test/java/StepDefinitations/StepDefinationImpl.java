package StepDefinitations;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import PageObject.CartPage;
import PageObject.CheckoutPage;
import PageObject.ConfirmationPage;
import PageObject.LandingPage;
import PageObject.ProductCatalog;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lib.BrowserSetUp;

public class StepDefinationImpl extends BrowserSetUp{

	public LandingPage landingPage;
	public ProductCatalog productCatalog;
	public CartPage cartPage;
	CheckoutPage checkoutPage;
	ConfirmationPage confirmationPage;
	
	@Given("I had landed on Ecommerce page")
	public void I_had_landed_on_Ecommerce_page() throws IOException {
		landingPage = launchApplication();	
	}
	
	@Given("^Logged in with username (.+) and password (.+)$")
	public void logged_in_username_and_password(String username, String password) {
		productCatalog = landingpage.loginApplication(username, password);
	}
	
	@When("^I add product (.+) to Cart$")
	public void i_add_product_to_Cart(String productname) {
		List<WebElement> products = productCatalog.getproductList();
		productCatalog.addProductToCart(productname);
	}
	
	@When("^Checkout (.+) and submit the order$")
	public void Checkout_submit_the_order(String productName) {
		System.out.println(productName);
		cartPage = productCatalog.gotToCartPage();
		
		Assert.assertTrue(cartPage.VerifyProductDisplay(productName), "Selected item is not aaded in cart.");
		
		checkoutPage = cartPage.gotoCheckOutPage();
		checkoutPage.selectCountry("India");
		confirmationPage = checkoutPage.submitOrder();
	}
	
	@Then("{string} message is displayed on ConfirmationPage")
	public void meesage_displayed_comfirmationPage(String string) {
		Assert.assertTrue(confirmationPage.getConfirmationMessage(), "Order confirmation message not displayed.");
	}
	
}
