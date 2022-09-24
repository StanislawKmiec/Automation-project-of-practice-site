package resources;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Basic {
    public WebDriver driver;
    public Properties data;

    public WebDriver invokeDriver() throws Exception {
        data = new Properties();
        FileInputStream files = new FileInputStream("C:\\Users\\User\\Desktop\\Swaglabsproject\\src\\main\\java\\resources\\data.properties" );
        data.load(files);
        String browserName = data.getProperty("browser");
        switch (browserName) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\chromedriver.exe");
                driver = new ChromeDriver();
                break;
            case "firefox":
                System.setProperty("webdriver.gecko.driver", "C:\\Program Files\\geckodriver.exe");
                driver = new FirefoxDriver();
                break;
            case "edge":
                System.setProperty("webdriver.edge.driver", "C:\\Program Files\\msedgedriver.exe");
                driver = new EdgeDriver();
                break;

            default:
                throw new Exception("Unsopported browser: " + browserName);
        }

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }
}
