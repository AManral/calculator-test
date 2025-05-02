package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

public class CalculatorPage {

    private Page page;

    // String Locators - Page Object Repository
    private String calculatorSearch = "//h2[text()='Calculator Result']";
    private String buttonType = "//div[@role='button']";
    private String googleSearchButton= "//button[@type='submit']";
    private String answer = "//div[@role='presentation' and @tabindex='0']/../preceding-sibling::div/div/span";
    private String currentResult = " //div[@role='presentation' and @tabindex='0']/div/span";
    private String clearEntry = "//div[text()='CE']";
    private String allClear = "//div[text()='AC']";


    //Initialize page using constructor
    public CalculatorPage(Page page) {
        this.page = page;
    }

    //Page Methods

    @Step("Enter operations")
    public void enterOperations(String input){

        for (char c : input.toCharArray()) {
            String label = switch (c) {
                case '*' -> "×";
                case '/' -> "÷";
                case '-' -> "−";
                default  -> String.valueOf(c);
            };
//            Locator entry=page.locator(page.textContent(label)).and(page.locator(buttonType));
            Locator entry=page.locator("//div[text()='"+label+"']");
            entry.click();
        }
    }

    @Step("Get current result")
    public String getCurrentResult(){
        return page.locator(currentResult).textContent();
    }

    @Step("Get Answer text")
    public String getAnswerText(){
        return page.locator(answer).textContent();
    }

    @Step("Check Clear Entry locator is visible")
    public boolean isClearEntryButtonVisible(){
        return page.locator(clearEntry).isVisible();
    }

    @Step("Check All Clear locator is visible")
    public boolean isAllClearButtonVisible(){
        return page.locator(allClear).isVisible();
    }

    @Step("Click Clear Entry button")
    public void clickClearEntryButton(){
        page.locator(clearEntry).click();
    }

    @Step("Click All Clear button")
    public void clickAllClearButton(){
        page.locator(allClear).click();
    }

    @Step("Clear all entries")
    public void clearAllEntries(){
        char[] entry= getCurrentResult().toCharArray();
        for (char c:entry) {
            clickClearEntryButton();
        }
    }

    @Step("Refresh calculator page")
    public void clickGoogleSearchButton(){
        page.locator(googleSearchButton).click();
        page.waitForLoadState();
    }
}
