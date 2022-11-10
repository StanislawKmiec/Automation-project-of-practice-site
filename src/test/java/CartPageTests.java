import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pageObject.CartPage;
import pageObject.LoginPage;
import pageObject.ProductsListPage;
import resources.Basic;
import java.util.concurrent.TimeUnit;

public class CartPageTests extends Basic {
    public ExtentSparkReporter spark;
    public ExtentReports extent;
    public ExtentTest logger;

    @BeforeTest
    public void setUp() {
        spark = new ExtentSparkReporter("report.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Creator", "Stanislaw Kmiec");
        extent.setSystemInfo("Environment", "Production");
        spark.config().setTheme(Theme.DARK);
        spark.config().setReportName("Cart page tests");
    }

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
    public void checkProductInCart() {
        logger = extent.createTest("Adding product to cart", "This test checks if product added to cart is the one which was chosen");
        ProductsListPage p = new ProductsListPage(driver);
        String addedProduct = p.productsNamesList().get(0).getText();
        String priceAddedProduct = p.productsPriceList().get(0).getText();
        p.addButton().get(0).click();
        logger.pass("Added product to cart");
        p.enterCart().click();
        logger.pass("User in cart view");
        logger.info("Checking product name added to cart");
        //logger.createNode("Correct product name");
        if(p.productsNamesList().get(0).getText().equals(addedProduct)) {
            logger.log(Status.PASS, "Correct product name in cart");
        } else {
            logger.log(Status.FAIL, "Wrong product name in cart");
        }
        logger.info("Checking product price added to cart");
        //logger.createNode("Correct product quantity");
        if(p.productsPriceList().get(0).getText().equals(priceAddedProduct)) {
            logger.log(Status.PASS, "Correct product price in cart");
        } else {
            logger.log(Status.FAIL, "Wrong product price in");
        }
        Assert.assertEquals(p.productsNamesList().get(0).getText(), addedProduct);
        Assert.assertEquals(p.productsPriceList().get(0).getText(), priceAddedProduct);
    }

   @Test
   public void returnToShoppingList() {
       logger = extent.createTest("Return to shopping list", "This test checks if user returns from cart to PLP");
       ProductsListPage p = new ProductsListPage(driver);
       p.enterCart().click();
       logger.pass("User is in cart");
       CartPage c = new CartPage(driver);
       c.ContinueShop().click();
       logger.pass("user is back on PLP");
       if(p.productsNamesList().size()>0) {
           logger.log(Status.PASS, "User is back on PLP");
       } else {
           logger.log(Status.FAIL, "User isn't back on PLP");
       }
       Assert.assertTrue(p.productsNamesList().size()>0, "Something is wrong");
   }

   @Test
    public void removeProductFromCart() {
       logger = extent.createTest("Remove product form cart", "This test checks if product removed form cart disappears from cart");
       ProductsListPage p = new ProductsListPage(driver);
       p.addButton().get(0).click();
       p.addButton().get(1).click();
       p.addButton().get(2).click();
       logger.pass("User added 3 products to cart");
       p.enterCart().click();
       logger.pass("User is in cart");
       CartPage c = new CartPage(driver);
       int amountInCart = c.itemsInCart().size();
       c.removeFromCart().click();
       logger.pass("User removed product form cart");
       if(c.itemsInCart().size() == amountInCart-2){
           logger.log(Status.PASS, "User removed one product from cart");
       } else {
           logger.log(Status.FAIL, "Removing product form cart failed");
       }
       Assert.assertEquals(c.itemsInCart().size(), amountInCart-2);
   }

   @Test public void proceedToCheckout() {
       logger = extent.createTest("Proceed to checkout", "This test checks if product removed form cart disappears from cart");
       ProductsListPage p = new ProductsListPage(driver);
       p.addButton().get(0).click();
       logger.pass("User added 1 product to cart");
       p.enterCart().click();
       logger.pass("User is in cart");
       CartPage c = new CartPage(driver);
       c.checkoutButton().click();
       String url = driver.getCurrentUrl();
       if (url.contains("checkout")) {
           logger.log(Status.PASS, "User is on checkout page");
       } else {
           logger.log(Status.FAIL, "User isn't on checkout page");
       }
       boolean exists = url.contains("checkout");
       Assert.assertTrue(exists, String.valueOf(true));
   }

    @AfterMethod
    public void getResult(ITestResult result) {
        if(result.getStatus() == ITestResult.FAILURE){
            logger.fail("Test Case Failed");
        } else if(result.getStatus() == ITestResult.SKIP){
            logger.log(Status.SKIP, "Test skipped");
        }
        else if(result.getStatus() == ITestResult.SUCCESS) {
            logger.log(Status.PASS, "Test passed");
        }
        driver.close();
        driver.quit();
    }

    @AfterTest
    public void tearDown() {
        extent.flush();
    }
}
