package pages_for_Rozetka;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SearchWithFiltersPage {
    WebDriver webDriver;
    WebDriverWait wait;

    By minPrice = By.cssSelector("input[formcontrolname=min]");
    By maxPrice = By.cssSelector("input[formcontrolname=max]");
    By readyToDeliver = By.cssSelector("a[href*=gotovo-k-otpravke]");
    By productPrice = By.cssSelector("span.goods-tile__price-value");
    By productName = By.cssSelector("span.goods-tile__title");
    By availability = By.cssSelector("div.goods-tile__availability");
    By linkToProductPage = By.cssSelector("div.goods-tile__inner>a");

    public SearchWithFiltersPage (WebDriver webDriver) {
        this.webDriver = webDriver;
        wait = new WebDriverWait(webDriver, 10);
    }

    public void addFilterByManufacturer(String company) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("label[for="+company+"]"))).click();
    }

    public void setMinPrice(String price){
        webDriver.findElement(minPrice).sendKeys(Keys.chord(Keys.CONTROL, "a"), price);
    }

    public void setMaxPrice(String price){
        webDriver.findElement(maxPrice).sendKeys(Keys.chord(Keys.CONTROL, "a"), price, Keys.ENTER);
    }

    public void setFilterReadyToDeliver(){
        webDriver.findElement(readyToDeliver).click();
    }

    public String checkPriceDiapason(String min, String max){
        wait.until(ExpectedConditions.elementToBeClickable(linkToProductPage));
        List<WebElement> listOfElements = webDriver.findElements(productPrice);
        String check = "true";
        for(WebElement webElem : listOfElements){
            String priceOfProduct = webElem.getText().replaceAll(" ", "");
            if(Integer.parseInt(priceOfProduct)>Integer.parseInt(max) || Integer.parseInt(priceOfProduct)<Integer.parseInt(min)) {
                check =String.format("false: %s < %s < %s", min, priceOfProduct, max);
                break;
            }
        }
        return check;
    }

    public boolean isProductAvailable(){
        wait.until(ExpectedConditions.elementToBeClickable(linkToProductPage));
        List<WebElement> listOfElements = wait.until(ExpectedConditions.visibilityOfAllElements(webDriver.findElements(availability)));
        boolean check = true;
        for(WebElement webElem : listOfElements){
            if(!webElem.getText().contains("Готов к отправке")) {check =false;}
        }
        return check;
    }

    public String checkProductsManufacturer(String... arr){
        wait.until(ExpectedConditions.elementToBeClickable(linkToProductPage));
        List<WebElement> listOfElements = wait.until(ExpectedConditions.visibilityOfAllElements(webDriver.findElements(productName)));
        String response = "true";
        boolean check = false;
        for(WebElement webElem : listOfElements){
            String nameOfProduct = webElem.getText();
            for(String word : arr){
                check=check || nameOfProduct.contains(word);
            }
            if(!check) {
                response = String.format("false: name of product: \"%s\"; product's number: \"%d\"", nameOfProduct, listOfElements.indexOf(webElem));
                break;
            }

        }
        return response;
    }
}
