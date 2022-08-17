package pages_for_Rozetka;

import helpClassesRozetka.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SearchPage {

    WebDriver webDriver;
    WebDriverWait wait;
    List<WebElement> listOfElements;

    By productOnSearchPage = By.xpath("//div[@class='goods-tile__inner']");
    By priceOfProduct = By.xpath(".//span[@class='goods-tile__price-value']");
    By linkToProductPage = By.xpath(".//a");
    By banner = By.cssSelector("a#rz-banner");
    By banner_close_button = By.cssSelector("span.exponea-close-cross");
    By addToBasketButton = By.cssSelector("button.goods-tile__buy-button");
    By productName = By.cssSelector("span.goods-tile__title");
    By productPrice = By.cssSelector("span.goods-tile__price-value");
    By buttonStateInCart = By.cssSelector("button.buy-button_state_in-cart");

    public SearchPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        wait = new WebDriverWait(webDriver, 10);
    }

    public boolean findProductWithPriceLessThan(String maxPrice) {
        listOfElements = webDriver.findElements(productOnSearchPage);
        boolean found = false;
        for (WebElement webElem : listOfElements) {
            String price = webElem.findElement(priceOfProduct).getText().replace(" ", "");
            if (Integer.parseInt(price) < Integer.parseInt(maxPrice)) {
                safeClick(webElem.findElement(linkToProductPage));
                found = true;
                break;
            }
        }
        return found;
    }

    private void safeClick(WebElement elem) {
        try {
            elem.click();
        } catch (ElementClickInterceptedException e) {
            if (webDriver.findElement(banner).isDisplayed()) {
                webDriver.findElement(banner_close_button).click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(banner));
                elem.click();
            }
        }
    }

    public void chooseProductCategory(String category) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li>a[href*=" + category + "]"))).click();
    }

    public Product addToBasket(int number) {
        listOfElements = wait.until(ExpectedConditions.visibilityOfAllElements(webDriver.findElements(productOnSearchPage)));
        WebElement webElem = listOfElements.get(number).findElement(addToBasketButton);
        safeClick(webElem);
        wait.until(ExpectedConditions.visibilityOf(listOfElements.get(number).findElement(buttonStateInCart)));
        return new Product(listOfElements.get(number).findElement(productName).getText(),
                Integer.parseInt(listOfElements.get(number).findElement(productPrice).getText().replace(" ", "")));
    }

    public void clickToTheFirstPhone() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(productOnSearchPage)).click();
    }
}
