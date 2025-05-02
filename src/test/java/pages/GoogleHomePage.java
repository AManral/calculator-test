package pages;

import com.microsoft.playwright.Page;
import io.qameta.allure.Step;


public class GoogleHomePage {
    private Page page;

    // String Locators - Page Object Repository
    private String searchBox = "//*[@title='Search']";
    private String searchButton = "(//input[@value='Google Search'])[1]";
    private String calculatorSearch = "//h2[text()='Calculator Result']";
    private String buttonType = "//div[@role='button']";
    private String answer = "//div[@role='presentation' and @tabindex='0']/../preceding-sibling::div/div/span";
    private String currentResult = " //div[@role='presentation' and @tabindex='0']/div/span";
    private String clearEntry = "//div[text()='CE']";
    private String allClear = "//div[text()='AC']";


    //Initialize page using constructor
    public GoogleHomePage(Page page) {
        this.page = page;
    }

    //Page Methods
    @Step("Get page title")
    public String getPageTitle(){
        String title = page.title();
        System.out.println("Page title is: " + title);
        return title;
    }

    @Step("Search the input term on google home page")
    public void searchTerm(String input) throws InterruptedException {
        if(page.locator(searchBox).isVisible()){
            page.locator(searchBox).click();
            page.locator(searchBox).fill(input);
            page.keyboard().press("Enter");
            page.waitForSelector(calculatorSearch).isVisible();
        }
    }


}
