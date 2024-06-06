package test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import PageObject.CartPage;
import PageObject.CheckoutPage;
import PageObject.ConfirmationPage;
import PageObject.OrderPage;
import PageObject.ProductCatalog;
import lib.BrowserSetUp;
import pages.LoginPage;

public class LoginPageScript extends BrowserSetUp{

	String productName = "ZARA COAT 3";
	
	@Test(dataProvider = "getData", groups = {"Purchase"})
	public void SubmitOrder(HashMap<String, String> input) throws IOException {
		
		String page_ActualHeading = "Rahul Shetty Academy";
		String page_LoginHeading = "Log in";	
		
		Assert.assertEquals(driver.findElement(LoginPage.homePageHeading).getText(), page_ActualHeading, "User not landed on correct page.");
		Assert.assertEquals(driver.findElement(LoginPage.loginHeading).getText(), page_LoginHeading, "Login page is not displayed.");
		
		ProductCatalog productCatalog = landingpage.loginApplication(input.get("email"), input.get("password"));
		 
		List<WebElement> products = productCatalog.getproductList();
		productCatalog.addProductToCart(input.get("product"));
		
		CartPage cartPage = productCatalog.gotToCartPage();
		
		Assert.assertTrue(cartPage.VerifyProductDisplay(input.get("product")), "Selected item is not aaded in cart.");
		
		CheckoutPage checkoutPage = cartPage.gotoCheckOutPage();
		checkoutPage.selectCountry("India");
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
	
		Assert.assertTrue(confirmationPage.getConfirmationMessage(), "Order confirmation message not displayed.");
		
		landingpage.naviagte_LandingPage_AfterSignOut();
		Assert.assertTrue(driver.findElement(LoginPage.LogoutMessage).isDisplayed(), "User not able to logout successfully.");

	}
	
	@Test(dependsOnMethods = "SubmitOrder")
	public void OrderHistory() throws IOException {
		
		ProductCatalog productCatalog = landingpage.loginApplication("Aayush@gmail.com", "Aayush@123");
		OrderPage orderpage = productCatalog.gotToOrderPage();
		Assert.assertTrue(orderpage.VerifyOrderDisplay(productName), "Ordered product is not displayed.");
	}
	
	@DataProvider
	public Object[][] getData() throws IOException{

		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"//src//test//java//Data//PurchaseOrder.json");
		return new Object[][] {{data.get(0)}, {data.get(1)}};
	}

}
