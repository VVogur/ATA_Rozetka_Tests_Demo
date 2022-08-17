package pages_for_Rozetka;

import helpClassesRozetka.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class BasketPage {
    WebDriver webDriver;
    WebDriverWait wait;
    Actions actions;

    By closeButton = By.cssSelector("button.modal__close");
    By cartItem = By.cssSelector("li.cart-list__item");
    By priceOfProduct = By.cssSelector("p.cart-product__price");
    By nameOfProduct = By.cssSelector("a.cart-product__title");
    By plusButton = By.cssSelector("button.cart-counter__button~button");
    By totalPrice = By.cssSelector("div.cart-receipt__sum-price>span");
    By productMenu = By.cssSelector("button.context-menu__toggle");
    By optionDelete = By.cssSelector("button.context-menu-actions__button");
    By additionalService = By.cssSelector("li.cart-services__item");
    By priceOfService = By.cssSelector("span.cart-service__prices");
    By serviceLabel = By.cssSelector("div>label.cart-service__item");

    public BasketPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        wait = new WebDriverWait(webDriver, 10);
        actions = new Actions(webDriver);
    }

    public void closeBasket(){
        webDriver.findElement(closeButton).click();

    }

    public int getNumberOfItems(){
        List<WebElement> listOfElements = wait.until(ExpectedConditions.visibilityOfAllElements(webDriver.findElements(cartItem)));
        return listOfElements.size();
    }

    public void increaseQuantity(Product p) {
        List<WebElement> listOfElements = wait.until(ExpectedConditions.visibilityOfAllElements(webDriver.findElements(cartItem)));
        for( WebElement elem : listOfElements){
            if(elem.findElement(nameOfProduct).getText().equals(p.getName())) {
                int sum = getTotalPrice();
                elem.findElement(plusButton).click();
                waitUntilTotalPriceUpdated(p.getPrice(), sum);
                break;
            }
        }
    }

    private void waitUntilTotalPriceUpdated(int difference, int total) {
        String text=Integer.toString(total + difference);
        if(!text.equals("0")){
            wait.until(ExpectedConditions.textToBePresentInElementLocated(totalPrice, text));
        }
    }

    public boolean containsProduct(Product p, int amount){
        boolean check = false;
        List<WebElement> listOfElements = wait.until(ExpectedConditions.visibilityOfAllElements(webDriver.findElements(cartItem)));
        for( WebElement elem : listOfElements){
            String price = elem.findElement(priceOfProduct).getText();
            price =price.substring(0, price.length()-2).replaceAll(" ", "");
            if(elem.findElement(nameOfProduct).getText().equals(p.getName()) && price.equals(p.getPrice()*amount +"")) {
                check = true;
                break;
            }
        }
        return check;
    }

    public void deleteProduct(Product p) {
        int sum = getTotalPrice();
        List<WebElement> listOfElements = wait.until(ExpectedConditions.visibilityOfAllElements(webDriver.findElements(cartItem)));
        for( WebElement elem : listOfElements){
            if(elem.findElement(nameOfProduct).getText().equals(p.getName())){
                ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", elem.findElement(productMenu));
                elem.findElement(productMenu).click();
                webDriver.findElement(optionDelete).click();
                break;
            }
        }
        waitUntilTotalPriceUpdated(-p.getPrice(), sum);
    }

    public int getTotalPrice() {
        String price = webDriver.findElement(totalPrice).getText();
        return Integer.parseInt(price);
    }

    public boolean isBasketEmpty(){
        return webDriver.findElements(cartItem).size() == 0;
    }

    public int addServiceByNumber(int number){
        int total = getTotalPrice();
        int priceOfService = clickOnServiceByNumber(number);
        waitUntilTotalPriceUpdated(priceOfService, total);
        return priceOfService;
    }

    public void deleteServiceByNumber(int number) {
        int total = getTotalPrice();
        int priceOfService = clickOnServiceByNumber(number);
        waitUntilTotalPriceUpdated(-priceOfService, total);
    }

    private int clickOnServiceByNumber(int number) {
        WebElement service = wait.until(ExpectedConditions.visibilityOfAllElements(webDriver.findElements(additionalService))).get(number);
        String s = service.findElement(priceOfService).getText();
        s=s.substring(0, s.length()-2).replaceAll(" ", "");
        service.findElement(serviceLabel).click();
        return Integer.parseInt(s);
    }
}
