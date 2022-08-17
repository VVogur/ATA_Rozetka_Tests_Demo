package pages_for_Rozetka;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RozetkaHomePage {
    WebDriver webDriver;
    WebDriverWait wait;
    Actions actions;

    By menuElementComputers = By.xpath("//sidebar-fat-menu//a[contains (@href, 'computers-notebooks')]");
    By smartPhonesTV = By.xpath("//sidebar-fat-menu//a[contains (@href, 'telefony-tv')]");
    By submenuElementMonitors = By.xpath("//div[@class='menu__main-cats']//a[contains (@href, 'monitors')]");
    By submenuElementNotebooks = By.cssSelector("a.menu__hidden-title[href*='/notebooks/']");
    By submenuElementPhones = By.cssSelector("a.menu__hidden-title[href*='/mobile-phones/']");
    By searchField = By.name("search");
    By catalogButton = By.cssSelector("button#fat-menu");

    public RozetkaHomePage (WebDriver webDriver) {
        this.webDriver = webDriver;
        wait = new WebDriverWait(webDriver, 10);
        actions = new Actions(webDriver);
    }

    public void searchForMonitors() {
        webDriver.findElement(catalogButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(submenuElementMonitors)).click();
    }

    public void searchForNotebooks() {
        webDriver.findElement(catalogButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(submenuElementNotebooks)).click();
    }

    public void searchForPhones() {
        webDriver.findElement(catalogButton).click();
        WebElement linkSmartPhones = webDriver.findElement(smartPhonesTV);
        actions.moveToElement(linkSmartPhones).perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(submenuElementPhones)).click();
    }

    public void searchByName(String name){
        webDriver.findElement(searchField).sendKeys(name + Keys.ENTER);
    }
}
