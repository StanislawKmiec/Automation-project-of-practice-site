import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObject.LoginPage;
import pageObject.ProductPage;
import pageObject.ProductsListPage;
import resources.Basic;

import java.util.concurrent.TimeUnit;

public class ProductPageTests extends Basic {

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
    public void enterFirstProductPage() {
        ProductsListPage p = new ProductsListPage(driver);
        String chosenProduct = p.productsNamesList().get(0).getText();
        p.productsNamesList().get(0).click();
        ProductPage pp = new ProductPage(driver);
        Assert.assertEquals(pp.productTitle().getText(), chosenProduct);
    }

    @Test
    public void backToProductsList() {
        ProductsListPage p = new ProductsListPage(driver);
        p.productsNamesList().get(1).click();
        ProductPage pp = new ProductPage(driver);
        pp.backButton().click();
        Assert.assertTrue(p.productsNamesList().size()>0, "You are still on PDP");
    }

    @Test
    public void addToCart() {
        ProductsListPage p = new ProductsListPage(driver);
        p.productsNamesList().get(2).click();
        ProductPage pp = new ProductPage(driver);
        pp.addToCartButton().click();
        Assert.assertEquals(p.countInCart().getText(), "1");
    }

    @Test
    public void removeProductFromCart() {
        ProductsListPage p = new ProductsListPage(driver);
        p.productsNamesList().get(2).click();
        ProductPage pp = new ProductPage(driver);
        pp.addToCartButton().click();
        pp.addToCartButton().click();
        Boolean exist = p.checkEmptyCart().size() == 0;
        Assert.assertEquals(exist, true);
    }
}
