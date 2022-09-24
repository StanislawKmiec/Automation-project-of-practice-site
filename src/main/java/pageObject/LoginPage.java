package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class LoginPage {

    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "user-name")
    WebElement username;

    @FindBy (id = "password")
    WebElement password;

    @FindBy (id = "login-button")
    WebElement login;

    @FindBy (xpath = "//h3")
    WebElement wrongCredentialsError;

    @FindBy (xpath = "//button[@class='error-button']")
    WebElement exitButton;

    By exitImage = By.xpath("//*[local-name()='svg']");

    /*@FindBy (xpath = "//*[local-name()='svg']")
    WebElements exitImage;*/


    public WebElement UserName() {
        return username;
    }

    public WebElement Password() {
        return password;
    }

    public WebElement Login() { return login; }

    public WebElement ErrorText() { return wrongCredentialsError; }

    public WebElement exit() {return exitButton; }

    public List <WebElement> exitSvg() { return driver.findElements(exitImage);}

   // public List<WebElement> exitSvg() { return exitImage; }


}
