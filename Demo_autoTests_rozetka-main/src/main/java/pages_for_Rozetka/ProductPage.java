package pages_for_Rozetka;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;

public class ProductPage {
    WebDriver webDriver;
    WebDriverWait wait;
    String imageFirstSrc;
    String imageSecondSrc;
    String zoomImgFirstPositionString;
    String zoomImgFSecondPositionString;


    By compareListIcon = By.xpath("//button[@aria-label='Списки сравнения']");
    By linkOnModalWindow = By.cssSelector("div.modal__holder a");
    By compareButton = By.cssSelector("button.compare-button");
    By compareButtonStateActive = By.cssSelector("button.compare-button_state_active");
    By priceOfProduct = By.cssSelector("p.product-prices__big");
    By nameOfProduct = By.className("product__title");

    By colorWrapperFirst = By.cssSelector(".var-options__list > li:nth-child(1)");
    By imagePhoneScrollBar = By.cssSelector(".scrollbar__inner > li:nth-child(1) > a > img");
    By imagePhoneZoom = By.cssSelector(".product-photo > div >figure> img ");//By.className("product-photo__picture");
    By zoomActive = By.className("zoom-img");
    By zoomedImage = By.className("zoom-sticky");
    By zoomedImagePosition = By.cssSelector(".zoom-container-main > div > img");
    By headerOnPage = By.cssSelector(".product-tabs__content > h2");

    public ProductPage(WebDriver webDriver){
        this.webDriver = webDriver;
        wait = new WebDriverWait(webDriver, 10);
    }

    public void navigateBack() {
        webDriver.navigate().back();
    }

    public void navigateToCompareList() {
        webDriver.findElement(compareListIcon).click();
        wait.until(ExpectedConditions.elementToBeClickable(linkOnModalWindow)).click();
    }

    public String getPrice() {
        return webDriver.findElement(priceOfProduct).getText().replaceAll("[ ₴]", "");
    }

    public String getName() {
        return webDriver.findElement(nameOfProduct).getText();
    }

    public void addToCompareList() {
        wait.until(ExpectedConditions.elementToBeClickable(compareButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(compareButtonStateActive));
    }

    public boolean checkScrollBarImg() {
        WebElement pictureFirstColor = webDriver.findElement(imagePhoneScrollBar);
        imageFirstSrc = pictureFirstColor.getAttribute("src");
        wait.until(ExpectedConditions.elementToBeClickable(colorWrapperFirst)).click();
        WebElement pictureSecondColor = webDriver.findElement(imagePhoneScrollBar);
        imageSecondSrc = pictureSecondColor.getAttribute("src");

        return !imageFirstSrc.equals(imageSecondSrc);
    }

    public void robotMouseMove(int x, int y) {
        try {
            Robot robot = new Robot();
            robot.mouseMove(x, y);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public boolean moveToPicturePhone() {
        WebElement picPhone = wait.until(ExpectedConditions.elementToBeClickable(imagePhoneZoom));
        int x = picPhone.getLocation().getX();
        int y = picPhone.getLocation().getY();
        robotMouseMove(x+150, y+200);
        wait.until(ExpectedConditions.elementToBeClickable(zoomedImagePosition));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".var-options__list > li")));
        boolean isPresent = webDriver.findElements(zoomActive).size() > 0;
        return isPresent;
    }

    public boolean checkChangingWhenCoursorMove() {
        WebElement zoomImgFirstPosition = webDriver.findElement(zoomedImagePosition);
        zoomImgFirstPositionString = zoomImgFirstPosition.getAttribute("style");
        WebElement picPhone = wait.until(ExpectedConditions.elementToBeClickable(imagePhoneZoom));
        int x = picPhone.getLocation().getX();
        int y = picPhone.getLocation().getY();
        robotMouseMove(x+150, y+350);

        WebElement zoomImgSecondPosition = wait.until(ExpectedConditions.elementToBeClickable(zoomedImagePosition));
        zoomImgFSecondPositionString = zoomImgSecondPosition.getAttribute("style");

        return !zoomImgFirstPositionString.equals(zoomImgFSecondPositionString);
    }

    public boolean checkWhenCoursorMoveAway() {
        wait.until(ExpectedConditions.elementToBeClickable(imagePhoneZoom));
        robotMouseMove(0, 0);
        boolean isPresent = webDriver.findElements(zoomedImage).size() > 0;
        return !isPresent;
    }

    public boolean headerCheckChar() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".tabs__list > li:nth-child(2)"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(headerOnPage));
        WebElement headerCharacteristics = webDriver.findElement(headerOnPage);
        String headerCharacteristicStr = headerCharacteristics.getText();
        return headerCharacteristicStr.contains("Характеристики");
    }

    public boolean headerCheckReviews() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".tabs__list > li:nth-child(3)"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".product-comments__header > h2")));
        WebElement headerReviews = webDriver.findElement(By.cssSelector(".product-comments__header > h2"));
        String headerReviewsStr = headerReviews.getText();
        return headerReviewsStr.contains("Отзывы");
    }

    public boolean headerCheckQuestions() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".tabs__list > li:nth-child(4)"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".product-questions__header > h2")));
        WebElement headerQuestions = webDriver.findElement(By.cssSelector(".product-questions__header > h2"));
        String headerQuestionsStr = headerQuestions.getText();
        return headerQuestionsStr.contains("Вопросы");
    }

    public boolean headerCheckVideo() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".tabs__list > li:nth-child(5)"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".layout > h3")));
        WebElement headerVideo = webDriver.findElement(By.cssSelector(".layout > h3"));
        String headerVideoStr = headerVideo.getText();
        return headerVideoStr.contains("Видео");
    }

    public boolean headerCheckPhoto() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".tabs__list > li:nth-child(6)"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(headerOnPage));
        WebElement headerPhoto = webDriver.findElement(headerOnPage);
        String headerPhotoStr = headerPhoto.getText();
        return headerPhotoStr.contains("Фотографии");
    }

    public boolean headerCheckAccessories() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".tabs__list > li:nth-child(7)"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".layout > h2")));
        WebElement headerAccessories = webDriver.findElement(By.cssSelector(".layout > h2"));
        String headerAccessoriesStr = headerAccessories.getText();
        return headerAccessoriesStr.contains("Аксессуары");
    }
}


