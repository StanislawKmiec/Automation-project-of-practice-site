import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObject.CartPage;
import pageObject.CheckoutPage;
import pageObject.LoginPage;
import pageObject.ProductsListPage;
import resources.Basic;

import java.nio.DoubleBuffer;
import java.sql.SQLOutput;
import java.util.concurrent.TimeUnit;

public class CheckoutTests extends Basic {

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
        ProductsListPage p = new ProductsListPage(driver);
        p.addButton().get(0).click();
        p.addButton().get(1).click();
        p.enterCart().click();
        CartPage cp = new CartPage(driver);
        cp.checkoutButton().click();
    }

    @Test
    public void cancelClick () {
        CheckoutPage c = new CheckoutPage(driver);
        c.cancelButton().click();
        String url = driver.getCurrentUrl();
        boolean exists = url.contains("cart");
        CartPage cp = new CartPage(driver);
        Assert.assertTrue(exists, String.valueOf(true));
        Assert.assertTrue(cp.itemsInCart().size()>0, "User is not in cart view");
    }

    @Test
    public void checkValidation() {
        CheckoutPage c = new CheckoutPage(driver);
        c.submitButton().click();
        Assert.assertTrue(c.errorMessage().getText().contains("First Name"), "Wrong error message");
        c.firstNameInput().sendKeys("James");
        c.submitButton().click();
        Assert.assertTrue(c.errorMessage().getText().contains("Last Name"), "Wrong error message");
        c.lastNameInput().sendKeys("Smith");
        c.submitButton().click();
        Assert.assertTrue(c.errorMessage().getText().contains("Postal Code"), "Wrong error message");
    }

    @Test
    public void resetMessages() {
        CheckoutPage c = new CheckoutPage(driver);
        c.submitButton().click();
        c.resetButton().click();
        try {
            if (c.errorMessage().isDisplayed()) {
                boolean wrong = false;
                System.out.println("Something went wrong");
                Assert.assertEquals(wrong, true);
            }
        }
          catch (NoSuchElementException e) {
              boolean good = true;
              Assert.assertEquals(good, true);
            }
    }

    @Test
    public void correctCheckoutData() {
        CheckoutPage c = new CheckoutPage(driver);
        c.firstNameInput().sendKeys("James");
        c.lastNameInput().sendKeys("Smith");
        c.postalCodeInput().sendKeys("54");
        c.submitButton().click();
        Assert.assertTrue(c.finishButton().isDisplayed(), "Something is wrong");
    }

    @Test
    public void checkCalculations() {
        CheckoutPage c = new CheckoutPage(driver);
        c.firstNameInput().sendKeys("James");
        c.lastNameInput().sendKeys("Smith");
        c.postalCodeInput().sendKeys("54");
        c.submitButton().click();
        ProductsListPage p = new ProductsListPage(driver);
        double firstProductPrice = Double.parseDouble(p.productsPriceList().get(0).getText().replaceAll("\\$", ""));
        double secondProductPrice = Double.parseDouble(p.productsPriceList().get(1).getText().replaceAll("\\$", ""));
        double systemPriceNoTax = Double.parseDouble(c.priceWithoutTax().getText().substring(13));
        Assert.assertEquals(firstProductPrice + secondProductPrice, systemPriceNoTax);
    }

    @Test
    public void finishCheckout() throws InterruptedException {
        CheckoutPage c = new CheckoutPage(driver);
        c.firstNameInput().sendKeys("James");
        c.lastNameInput().sendKeys("Smith");
        c.postalCodeInput().sendKeys("54");
        c.submitButton().click();
        c.finishButton().click();
        Assert.assertTrue(c.finalHeader().getText().contains("COMPLETE"));
        Assert.assertEquals(c.finalText().getText(), data.getProperty("finalText"));
        c.backHome().click();
        Thread.sleep(5000);
        String url = driver.getCurrentUrl();
        boolean exists = url.contains("inventory");
        Assert.assertTrue(exists, String.valueOf(true));
    }

    @AfterMethod
    public void driverEnd() {
        driver.close();
        driver.quit();
    }
}
