package rozetkaTests;

import helpClassesRozetka.Product;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages_for_Rozetka.BasketPage;
import pages_for_Rozetka.HeaderFunctionsPage;
import pages_for_Rozetka.RozetkaHomePage;
import pages_for_Rozetka.SearchPage;

import static org.testng.Assert.*;

public class BasketTests extends BaseTestClass {

    RozetkaHomePage rozetkaHomePage;
    SearchPage searchPage;
    HeaderFunctionsPage headerFunctionsPage;
    BasketPage basketPage;

    @BeforeMethod
    public void setupBrowserAndPages(){
        super.setupBrowserAndPages();
        rozetkaHomePage = new RozetkaHomePage(driver);
        searchPage = new SearchPage(driver);
        headerFunctionsPage = new HeaderFunctionsPage(driver);
        basketPage = new BasketPage(driver);
    }

    @AfterMethod
    public void cleanCookies(){
        driver.manage().deleteAllCookies();
    }

    @Test
    public void testAddProductsToBasket() {
        rozetkaHomePage.searchForNotebooks();
        Product product1 = searchPage.addToBasket(0);
        assertEquals(headerFunctionsPage.getBasketCounter(), "1");
        headerFunctionsPage.openBasket();
        assertTrue(basketPage.containsProduct(product1, 1));
        basketPage.increaseQuantity(product1);
        assertTrue(basketPage.containsProduct(product1, 2));
        basketPage.closeBasket();
        assertEquals(headerFunctionsPage.getBasketCounter(), "2");
        driver.get(initialUrl);
        rozetkaHomePage.searchForPhones();
        Product product2 = searchPage.addToBasket(0);
        assertEquals(headerFunctionsPage.getBasketCounter(), "3");
        headerFunctionsPage.openBasket();
        assertTrue(basketPage.containsProduct(product2, 1));
        assertEquals(basketPage.getNumberOfItems(), 2);
    }

    @Test
    public void testRemoveProductsFromBasket() {
        rozetkaHomePage.searchForNotebooks();
        Product product1 = searchPage.addToBasket(0);
        Product product2 = searchPage.addToBasket(1);
        Product product3 = searchPage.addToBasket(2);
        assertEquals(headerFunctionsPage.getBasketCounter(), "3");
        headerFunctionsPage.openBasket();
        assertTrue(basketPage.containsProduct(product1, 1));
        assertTrue(basketPage.containsProduct(product2, 1));
        assertTrue(basketPage.containsProduct(product3, 1));
        basketPage.deleteProduct(product1);
        assertEquals(headerFunctionsPage.getBasketCounter(), "2");
        assertTrue(basketPage.containsProduct(product2, 1));
        assertTrue(basketPage.containsProduct(product3, 1));
        assertEquals(basketPage.getTotalPrice(), product2.getPrice()+product3.getPrice());
        basketPage.deleteProduct(product2);
        basketPage.deleteProduct(product3);
        assertTrue(headerFunctionsPage.checkBasketCounterNotDisplayed());
        assertTrue(basketPage.isBasketEmpty());
    }

    @Test
    public void testAdditionalServices() {
        rozetkaHomePage.searchForPhones();
        Product product1 = searchPage.addToBasket(0);
        headerFunctionsPage.openBasket();
        int priceOfService1 = basketPage.addServiceByNumber(0);
        assertEquals(basketPage.getTotalPrice(), priceOfService1 + product1.getPrice());
        int priceOfService2 = basketPage.addServiceByNumber(1);
        assertEquals(basketPage.getTotalPrice(), priceOfService1 + priceOfService2 + product1.getPrice());
        basketPage.deleteServiceByNumber(0);
        assertEquals(basketPage.getTotalPrice(), priceOfService2 + product1.getPrice());
        basketPage.deleteServiceByNumber(1);
        assertEquals(basketPage.getTotalPrice(), product1.getPrice());
    }
}
