package rozetkaTests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages_for_Rozetka.RozetkaHomePage;
import pages_for_Rozetka.SearchPage;
import pages_for_Rozetka.SearchWithFiltersPage;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class RozetkaFiltersPageTest extends BaseTestClass{

    private RozetkaHomePage rozetkaHomePage;
    private SearchPage searchPage;
    private SearchWithFiltersPage searchWithFiltersPage;

    @BeforeMethod
    public void setupBrowserAndPages() {
        super.setupBrowserAndPages();
        rozetkaHomePage = new RozetkaHomePage(driver);
        searchPage = new SearchPage(driver);
        searchWithFiltersPage = new SearchWithFiltersPage(driver);
    }

    @Test
    public void test1FilterByManufacturer() {
        rozetkaHomePage.searchByName("samsung");
        searchPage.chooseProductCategory("mobile-phones");
        searchWithFiltersPage.addFilterByManufacturer("Apple");
        searchWithFiltersPage.addFilterByManufacturer("Huawei");
        String response = searchWithFiltersPage.checkProductsManufacturer("Samsung", "Apple", "Huawei");
        assertEquals(response, "true", response);
    }

    @Test
    public void test2FilterByPrice() {
        rozetkaHomePage.searchByName("samsung");
        searchPage.chooseProductCategory("mobile-phones");
        searchWithFiltersPage.setMinPrice("5000");
        searchWithFiltersPage.setMaxPrice("15000");
        String response = searchWithFiltersPage.checkPriceDiapason("5000", "15000");
        assertEquals(response, "true", response);
    }

    @Test
    public void test3FilterByAvailability() {
        rozetkaHomePage.searchByName("samsung");
        searchPage.chooseProductCategory("mobile-phones");
        searchWithFiltersPage.setFilterReadyToDeliver();
        assertTrue(searchWithFiltersPage.isProductAvailable());
    }
}

