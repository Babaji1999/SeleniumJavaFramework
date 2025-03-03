package PageObject;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import CommonPage.AbstractComponents;
import pages.LoginPage;

public class CartPage extends AbstractComponents{

	WebDriver driver;
	
	public CartPage(WebDriver driver) {
		super(driver);
//		initilization
		this.driver = driver;
//		Why this method is created--Before going to any method first this constructor will execute
		PageFactory.initElements(driver, this);
	}

	// PageFactory
	@FindBy(xpath = "//*[@class='cartSection']/h3")
	List<WebElement> itemsInCart;
	
	@FindBy(xpath = "//button[text()='Checkout']")
	WebElement checkout_Button;
	
	public Boolean VerifyProductDisplay(String productName) {		
		System.out.println("1"+productName);
		System.out.println(itemsInCart);
		Boolean match = itemsInCart.stream().anyMatch(item -> item.getText().equalsIgnoreCase(productName));
		return match;	
	}
	
	public CheckoutPage gotoCheckOutPage() {
		checkout_Button.click();
		waitforElementToBeClickable(LoginPage.placeOrder_Button);
		return new CheckoutPage(driver);
	}

}
