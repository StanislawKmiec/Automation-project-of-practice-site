import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObject.LoginPage;
import pageObject.ProductsPage;
import resources.Basic;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ProductsPageTests extends Basic {

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
    public void footerCheckList() throws IOException { // Response code is 999 for linkedin it's code that block the user-agent, probably because of automation
        ProductsPage p = new ProductsPage(driver);
        for (int i = 0; i < p.socialLinks().size(); i++) {
            String url = p.socialLinks().get(i).getAttribute("href");
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();
            int responseCode = connection.getResponseCode();
            Assert.assertTrue(responseCode<400, "This link is broken " + p.socialLinks().get(i).getText() + " is broken has code " + responseCode);
            System.out.println(responseCode);
        }
    }

    @Test
    public void checkNumberOfItems() {
        ProductsPage p = new ProductsPage(driver);
        Assert.assertEquals(p.numberItems().size(), 6);
    }

    @Test
    public void addProductsToCart() {
        ProductsPage p = new ProductsPage(driver);
        p.addButton().get(0).click();
        Assert.assertEquals(p.countInCart().getText(), "1");
        p.addButton().get(1).click();
        p.addButton().get(2).click();
        p.addButton().get(5).click();
        Assert.assertEquals(p.countInCart().getText(), "4");
    }

    @Test
    public void removeProductFromCart() {
        ProductsPage p = new ProductsPage(driver);
        p.addButton().get(0).click();
        Assert.assertEquals(p.removeFromCartButton().getText(), data.getProperty("removeButton"));
        p.removeFromCartButton().click();
        Boolean exist = p.checkEmptyCart().size() == 0;
        Assert.assertEquals(exist, true);
    }

    @Test
    public void logOut() {
        ProductsPage p = new ProductsPage(driver);
        p.sideMenu().click();
        p.logOutButton().click();
        LoginPage lp = new LoginPage(driver);
        Assert.assertTrue(lp.Login().isDisplayed(), "Not logged out");
    }

    @Test
    public void filterReverseAlphatebical() {
        ProductsPage p = new ProductsPage(driver);
        p.filterOptions().click();
        List<String> productNames = new ArrayList<>();

        for(int i =0; i<p.productsNamesList().size(); i++) {
            productNames.add(p.productsNamesList().get(i).getText());
        }

        List<String> reversOrder = productNames.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());

        for (int i = 0; i <p.chooseOption().size(); i++) {
            if (p.chooseOption().get(i).getAttribute("value").equalsIgnoreCase("za"))
            {
                p.chooseOption().get(i).click();
            }
        }

        List clickedProductNames = new ArrayList();

        for(int i =0; i<p.productsNamesList().size(); i++) {
            clickedProductNames.add(p.productsNamesList().get(i).getText());
        }

        Assert.assertEquals(clickedProductNames, reversOrder);
        p.filterOptions().click(); ///dokoncz ten test tutaj
        for (int i = 0; i <p.chooseOption().size(); i++) {
            if (p.chooseOption().get(i).getAttribute("value").equalsIgnoreCase("az"))
            {
                p.chooseOption().get(i).click();
            }
        }

        List clickedProductNames2 = new ArrayList();

        for(int i =0; i<p.productsNamesList().size(); i++) {
            clickedProductNames2.add(p.productsNamesList().get(i).getText());
        }

        Assert.assertEquals(clickedProductNames2, productNames);
    }

    @Test
    public void filterByPriceHiToLo() {
        ProductsPage p = new ProductsPage(driver);
        List<String> pricesWithDollar = new ArrayList<>();

        for(int i =0; i<p.productsPriceList().size(); i++) {
            pricesWithDollar.add(p.productsPriceList().get(i).getText());
        }

        List<String> pricesWithoutDollar = pricesWithDollar.stream().map(s -> s.replaceAll("\\$", "")).collect(Collectors.toList());
        List<BigDecimal> prices = pricesWithoutDollar.stream().map(BigDecimal::new).collect(Collectors.toList());
        Collections.sort(prices);

        p.filterOptions().click();
        for (int i = 0; i <p.chooseOption().size(); i++) {
            if (p.chooseOption().get(i).getAttribute("value").equalsIgnoreCase("hilo"))
            {
                p.chooseOption().get(i).click();
            }
        }

        List<String> clickedList = new ArrayList<>();
        for(int i =0; i<p.productsPriceList().size(); i++) {
            clickedList.add(p.productsPriceList().get(i).getText());
        }

        List<String> clickedListWithoutDollar = clickedList.stream().map(s -> s.replaceAll("\\$", "")).collect(Collectors.toList());
        List<BigDecimal> toDecimal = clickedListWithoutDollar.stream().map(BigDecimal::new).collect(Collectors.toList());
        Assert.assertEquals(toDecimal.get(0), prices.get(prices.size()-1));
    }

    @Test
    public void filterByPriceLoToHi() {
        ProductsPage p = new ProductsPage(driver);
        List<String> pricesWithDollar = new ArrayList<>();

        for(int i =0; i<p.productsPriceList().size(); i++) {
            pricesWithDollar.add(p.productsPriceList().get(i).getText());
        }

        List<String> pricesWithoutDollar = pricesWithDollar.stream().map(s -> s.replaceAll("\\$", "")).collect(Collectors.toList());
        List<BigDecimal> prices = pricesWithoutDollar.stream().map(BigDecimal::new).collect(Collectors.toList());
        Collections.sort(prices);

        p.filterOptions().click();
        for (int i = 0; i <p.chooseOption().size(); i++) {
            if (p.chooseOption().get(i).getAttribute("value").equalsIgnoreCase("lohi"))
            {
                p.chooseOption().get(i).click();
            }
        }

        List<String> clickedList = new ArrayList<>();
        for(int i =0; i<p.productsPriceList().size(); i++) {
            clickedList.add(p.productsPriceList().get(i).getText());
        }

        List<String> clickedListWithoutDollar = clickedList.stream().map(s -> s.replaceAll("\\$", "")).collect(Collectors.toList());
        List<BigDecimal> toDecimal = clickedListWithoutDollar.stream().map(BigDecimal::new).collect(Collectors.toList());
        Assert.assertEquals(toDecimal.get(0), prices.get(0));
    }

    @Test
    public void openCloseSideMenu() throws InterruptedException {
        ProductsPage p = new ProductsPage(driver);
        p.showSideMenu().click();
        List<String> options = new ArrayList<>();
        Assert.assertTrue(p.wholeSideMenu().isDisplayed(), "Side menu not open");

        for(int i =0; i<p.sideOptionsList().size(); i++) {
            options.add(p.sideOptionsList().get(i).getText());
        }

        Assert.assertEquals(options.size(),  Integer.parseInt(data.getProperty("sideMenuOptionsNumber")));
        p.closeSideMenu().click();
        Thread.sleep(5000);
        Assert.assertFalse(p.wholeSideMenu().isDisplayed(), "Side menu not closed");
    }

    @AfterMethod
    public void driverEnd() {
        driver.close();
        driver.quit();
    }
}
