import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObject.CartPage;
import pageObject.LoginPage;
import pageObject.ProductsListPage;
import resources.Basic;
import java.util.concurrent.TimeUnit;

public class CartPageTests extends Basic {

    @BeforeMethod
    public void driverStart() throws Exception {
        driver = invokeDriver();
        driver.get(data.getProperty("url"));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        LoginPage lp = new LoginPage(driver);
        lp.UserName().sendKeys(data.getProperty("standardUser"));
        lp.Password().sendKeys(data.getProperty("passwordForEachAcc"));
        lp.Login().click();
    }

    @Test
    public void checkProductInCart() throws InterruptedException {
        ProductsListPage p = new ProductsListPage(driver);
        String addedProduct = p.productsNamesList().get(0).getText();
        String priceAddedProduct = p.productsPriceList().get(0).getText();
        p.addButton().get(0).click();
        p.enterCart().click();
        Assert.assertEquals(p.productsNamesList().get(0).getText(), addedProduct);
        Assert.assertEquals(p.productsPriceList().get(0).getText(), priceAddedProduct);
   }

   @Test
   public void returnToShopppingList() {
       ProductsListPage p = new ProductsListPage(driver);
       p.enterCart().click();
       CartPage c = new CartPage(driver);
       c.ContinueShop().click();
       Assert.assertTrue(p.productsNamesList().size()>0, "Something is worn");
   }

   @Test
    public void removeProductFromCart() {
       ProductsListPage p = new ProductsListPage(driver);
       p.addButton().get(0).click();
       p.addButton().get(1).click();
       p.addButton().get(2).click();
       p.enterCart().click();
       CartPage c = new CartPage(driver);
       int amountInCart = c.itemsInCart().size();
       c.removeFromCart().click();
       Assert.assertEquals(c.itemsInCart().size(), amountInCart-1);
   }

   @Test public void proceedToCheckout() {
       ProductsListPage p = new ProductsListPage(driver);
       p.addButton().get(0).click();
       p.enterCart().click();
       CartPage c = new CartPage(driver);
       c.checkoutButton().click();
       String url = driver.getCurrentUrl();
       boolean exists = url.contains("checkout");
   }

    @AfterMethod
    public void driverEnd() {
        driver.close();
        driver.quit();
    }
}
