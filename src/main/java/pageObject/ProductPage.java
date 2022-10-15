package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductPage {

    WebDriver driver;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@class='inventory_details_name large_size']")
    WebElement title;

    @FindBy(xpath = "//button[@name='back-to-products']")
    WebElement back;

    @FindBy (xpath = "//div[@class='inventory_details_desc_container']/button")
    WebElement add;

    public WebElement productTitle() { return title; }

    public WebElement backButton() { return back; }

    public WebElement addToCartButton() { return add; }

}
