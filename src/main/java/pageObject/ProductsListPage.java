package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductsListPage {

    WebDriver driver;

    public ProductsListPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    By socials = By.xpath("//ul/li/a");

    By items = By.xpath("//div[@class='inventory_item_name']");

    By adding = By.xpath("//div[@class='pricebar']/button");

    @FindBy (xpath = "//span[@class='shopping_cart_badge']")
    WebElement inCart;

    @FindBy (xpath = "//div[@class='pricebar']/button[@id='remove-sauce-labs-backpack']")
    WebElement removeButton;

    @FindBy (xpath = "//button[@id='react-burger-menu-btn']" )
    WebElement hamburgerMenu;

    @FindBy(xpath = "//a[@id='logout_sidebar_link']")
    WebElement logOut;

    @FindBy (xpath = "//select[@class='product_sort_container']")
    WebElement rightFilter;

    By options = By.xpath("//option");

    By productsName = By.xpath("//div[@class='inventory_item_name']");

    By productsPrice = By.xpath("//div[@class='inventory_item_price']");

    @FindBy(xpath = "//button[@id='react-burger-menu-btn']")
    WebElement sideMenuButton;

    @FindBy (xpath = "//nav[@class='bm-item-list']")
    WebElement sideMenu;

    @FindBy (xpath =  "//button[@id='react-burger-cross-btn']")
    WebElement closeButton;

    By sideMenuOptions = By.xpath("//nav/a");

    @FindBy (xpath = "//button[@id='add-to-cart-sauce-labs-backpack']")
    WebElement addBackPack;

    By Cart = By.xpath("//a/span");

    public List<WebElement> socialLinks() { return driver.findElements(socials); }

    public List <WebElement> numberItems() { return driver.findElements(items); }

    public List <WebElement> addButton() { return driver.findElements(adding); }

    public WebElement countInCart() { return inCart; }

    public WebElement removeFromCartButton() { return removeButton; }

    public WebElement sideMenu() { return hamburgerMenu; }

    public WebElement logOutButton() { return logOut; }

    public WebElement filterOptions() { return rightFilter; }

    public List<WebElement> chooseOption() { return driver.findElements(options); }

    public List<WebElement> productsNamesList() { return driver.findElements(productsName); }

    public List<WebElement> productsPriceList() { return driver.findElements(productsPrice); }

    public WebElement showSideMenu() { return sideMenuButton; }

    public WebElement wholeSideMenu() { return sideMenu; }

    public WebElement closeSideMenu() { return closeButton; }

    public List<WebElement> sideOptionsList() { return driver.findElements(sideMenuOptions); }

    public WebElement addToCart() { return addBackPack; }

    public WebElement quantityInCart() { return driver.findElement(Cart); }

    public List<WebElement> checkEmptyCart() { return driver.findElements(Cart); }
}
