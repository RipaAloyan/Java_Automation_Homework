package seleniumTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.asserts.SoftAssert;
import pagesAmazon.AuthorPage;
import pagesAmazon.HomePage;
import pagesAmazon.SearchAuthor;

import java.util.List;
import java.util.Locale;

public class AmazonTest {
    private WebDriver driver;
    private final String authorFullName = "kurt vonnegut";
    private final String beDeliveredTo = "Deliver to Armenia";

    @BeforeSuite
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
    }

    @BeforeMethod
    public void driverInstance(){
        driver = new ChromeDriver();
        driver.get("https://www.amazon.com/");
    }

    @Test
    public void searchAuthorInBooks(){
        HomePage homePage = new HomePage(driver);
        homePage.waitUntilPageLoads();

        String assertingMessageForLocation = "The chosen location is different from your expected.";
        Assert.assertEquals(homePage.getDeliverLocation(), beDeliveredTo, assertingMessageForLocation);

        homePage.chooseBooksFromCategories();
        homePage.searchAuthor(authorFullName);
        SearchAuthor searchAuthor = new SearchAuthor(driver);
        searchAuthor.waitForPageLoads();

        String assertingMessageForAuthorName =String.format("In most results %s is not the author.", authorFullName) ;
        Assert.assertTrue(isAuthor(searchAuthor,authorFullName), assertingMessageForAuthorName);

        searchAuthor.getBooksAuthors().get(1).click();
        AuthorPage authorPage = new AuthorPage(driver);
        authorPage.waitUntilPageLoads();
        authorPage.choosePriceFilterLowToHigh();
        authorPage.waitUntilPageLoads();



    }

    public Boolean isAuthor(SearchAuthor searchAuthor, String authorFullName){
        int i = 0;
        for(WebElement author :searchAuthor.getBooksAuthors()){
            if(author.getText().toLowerCase().equals(authorFullName))
                i++; }
       if(i>=(searchAuthor.getCountOfBooks()/2)){ return true; }
       else return false;

    }
}