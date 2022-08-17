package rozetkaTests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

public class BaseTestClass {
    WebDriver driver;
    final String initialUrl = "https://rozetka.com.ua/";


    @BeforeClass
    public void setupDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    }

    @AfterClass
    public void quitDriver() {
        driver.quit();
    }

    @BeforeMethod
    public void setupBrowserAndPages(){
        driver.get(initialUrl);
    }

}
