package rozetkaTests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages_for_Rozetka.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class RozetkaCompareMonitorPageTest extends BaseTestClass {

    RozetkaHomePage rozetkaHomePage;
    SearchPage searchPage;
    ProductPage productPage;
    CompareListPage compareListPage;
    HeaderFunctionsPage headerFunctionsPage;

    @BeforeMethod
    public void setupBrowserAndPages(){
        super.setupBrowserAndPages();
        rozetkaHomePage = new RozetkaHomePage(driver);
        searchPage = new SearchPage(driver);
        productPage = new ProductPage(driver);
        compareListPage = new CompareListPage(driver);
        headerFunctionsPage = new HeaderFunctionsPage(driver);
    }

    @Test
    public void testCompareMonitors(){
        rozetkaHomePage.searchForMonitors();
        assertTrue(searchPage.findProductWithPriceLessThan("4000"));

        String priceOfFirstProduct = productPage.getPrice();
        String nameOfFirstProduct = productPage.getName();
        productPage.addToCompareList();
        assertEquals(headerFunctionsPage.getCompareListCounter(), "1");
        productPage.navigateBack();

        assertTrue(searchPage.findProductWithPriceLessThan(priceOfFirstProduct));
        String priceOfSecondProduct = productPage.getPrice();
        String nameOfSecondProduct = productPage.getName();
        productPage.addToCompareList();
        assertEquals(headerFunctionsPage.getCompareListCounter(), "2");
        productPage.navigateToCompareList();

        assertEquals(compareListPage.getNumberOfProducts(), 2);
        assertEquals(compareListPage.getPriceByNumber(1), priceOfFirstProduct);
        assertEquals(compareListPage.getNameByNumber(1), nameOfFirstProduct);
        assertEquals(compareListPage.getPriceByNumber(2), priceOfSecondProduct);
        assertEquals(compareListPage.getNameByNumber(2), nameOfSecondProduct);
    }
}