package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage {

    WebDriver driver;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy (xpath = "//button[@id='cancel']")
    WebElement cancel;

    @FindBy (xpath = "//input[@type='submit']")
    WebElement submit;

    @FindBy (xpath = "//h3")
    WebElement error;

    @FindBy (xpath = "//input[@id='first-name']")
    WebElement firstName;

    @FindBy (xpath = "//input[@id='last-name']")
    WebElement lastName;

    @FindBy (xpath = "//input[@id='postal-code']")
    WebElement postalCode;

    @FindBy (xpath = "//button[@class='error-button']")
    WebElement reset;

    @FindBy (xpath = "//button[@id='finish']")
    WebElement finish;

    @FindBy (xpath = "//div[@class='summary_subtotal_label']")
    WebElement price1;

    @FindBy (xpath = "//span")
    WebElement header;

    @FindBy (xpath = "//div[@class='complete-text']")
    WebElement text;

    @FindBy (xpath = "//button[@class='btn btn_primary btn_small']")
    WebElement backButton;


    public WebElement cancelButton() { return cancel; }

    public WebElement submitButton() { return submit; }

    public WebElement errorMessage() { return error; }

    public WebElement firstNameInput() { return firstName; }

    public WebElement lastNameInput() { return lastName; }

    public WebElement postalCodeInput() { return postalCode; }

    public WebElement resetButton() { return reset; }

    public WebElement finishButton() { return finish; }

    public WebElement priceWithoutTax() { return price1; }

    public WebElement finalHeader() { return header; }

    public WebElement finalText() { return text; }

    public WebElement backHome() { return backButton; }
}
