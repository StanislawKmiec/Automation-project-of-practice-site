import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObject.LoginPage;
import resources.Basic;

import java.util.NoSuchElementException;
import java.util.UUID;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class LoginPageTests extends Basic {

    @BeforeMethod
    public void driverStart() throws Exception {
        driver = invokeDriver();
        driver.get(data.getProperty("url"));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
  /* @BeforeMethod
    public String credidentials() {
        String pass = data.getProperty("passwordForEachAcc");
        return pass;
    }*/
    @Test
    public void correctLogin() {
        String login = data.getProperty("standardUser");
        String pass = data.getProperty("passwordForEachAcc");
        LoginPage lp = new LoginPage(driver);
        lp.UserName().sendKeys(login);
        lp.Password().sendKeys(pass);
        lp.Login().click();
        String url = driver.getCurrentUrl();
        boolean exists = url.contains("inventory");
        Assert.assertTrue(exists, String.valueOf(true));
    }

    static String usingRandomUUID() {

        UUID randomUUID = UUID.randomUUID();

        return randomUUID.toString().replaceAll("-", "");
    }

    @Test
    public void wrongCredentials() {
        Random randomDAta = new Random();
        String LongLogin = usingRandomUUID();
        String LongPass = usingRandomUUID();
        String login = LongLogin.substring(0,8);
        String pass = LongPass.substring(0,8);
        LoginPage lp = new LoginPage(driver);
        lp.UserName().sendKeys(login);
        lp.Password().sendKeys(pass);
        lp.Login().click();
        String text = lp.ErrorText().getText();
        Assert.assertEquals(data.getProperty("wrongCredentialsMsg"), text);
    }

    @Test
    public void lockedOutUser() {
        String login = data.getProperty("lockedUser");
        String pass = data.getProperty("passwordForEachAcc");
        LoginPage lp = new LoginPage(driver);
        lp.UserName().sendKeys(login);
        lp.Password().sendKeys(pass);
        lp.Login().click();
        String text = lp.ErrorText().getText();
        Assert.assertEquals(data.getProperty("lockedMsg"), text);
    }

    @Test
    public void resetErrorMsg() {
        String login = data.getProperty("lockedUser");
        String pass = data.getProperty("passwordForEachAcc");
        LoginPage lp = new LoginPage(driver);
        lp.UserName().sendKeys(login);
        lp.Password().sendKeys(pass);
        lp.Login().click();
        lp.exit().click();
        Boolean condition = lp.exitSvg().size() > 0;
        Assert.assertFalse(condition, "Still displayed");
    }

    @Test
    public void emptyFields() {
        LoginPage lp = new LoginPage(driver);
        lp.Login().click();
        String text = lp.ErrorText().getText();
        Assert.assertEquals(data.getProperty("emptyCred"), text);
    }

    @Test
    public void placeHoldersCheck() {
        LoginPage lp = new LoginPage(driver);
        String user = lp.UserName().getAttribute("placeholder");
        String pass = lp.Password().getAttribute("placeholder");
        Assert.assertEquals(user, data.getProperty("usernamePlaceHolder"));
        Assert.assertEquals(pass, data.getProperty("passwordPlaceHolder"));
    }

    @AfterMethod
    public void driverEnd() {
        driver.close();
        driver.quit();
    }
}
