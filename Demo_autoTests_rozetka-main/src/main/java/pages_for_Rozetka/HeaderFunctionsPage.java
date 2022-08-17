package pages_for_Rozetka;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HeaderFunctionsPage {
    WebDriver webDriver;
    WebDriverWait wait;

    By compareListCounter = By.cssSelector("rz-comparison span.counter");
    By basketCounter = By.cssSelector("rz-cart span.counter");
    By basketButton = By.cssSelector("header button[opencart]");


    public HeaderFunctionsPage(WebDriver webDriver){
        this.webDriver = webDriver;
        wait = new WebDriverWait(webDriver, 10);
    }

    public String getCompareListCounter(){
        return webDriver.findElement(compareListCounter).getText();
    }

    public String getBasketCounter() {
        return webDriver.findElement(basketCounter).getText();
    }

    public boolean checkBasketCounterNotDisplayed() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(basketCounter));
        List<WebElement> list= webDriver.findElements(basketCounter);
        return list.size() == 0;
    }

    public void openBasket(){
        webDriver.findElement(basketButton).click();
    }
}
