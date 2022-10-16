package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage {

    WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy (xpath = "//button[@id='continue-shopping']")
    WebElement shopping;

    @FindBy (xpath = "//div[@class='item_pricebar']/button")
    WebElement remove;

    By cartList = By.xpath("//div[@class='cart_item']");

    @FindBy (xpath = "//button[@id='checkout']")
    WebElement checkout;


    public WebElement ContinueShop() { return shopping; }

    public WebElement removeFromCart() { return remove; }

    public List<WebElement> itemsInCart() { return driver.findElements(cartList); }

    public WebElement checkoutButton() { return checkout; }
}
