package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.List;

public class GCPage {

    private Page page;

    //Webelements
    private String whyGCPage = "//*[text()='Why GCPay']";
    private String genericContractors = "//a[text()='Helping General Contractors']";
    private String features = "//div[@class='elementor-accordion-item']";
    private String activeFeature = "//div[@class='elementor-tab-content elementor-clearfix elementor-active']";



    public GCPage(Page page) {
        this.page = page;
    }

    public void gotToFeaturesPage() throws InterruptedException {
        page.locator(whyGCPage).first().click();
        page.locator(genericContractors).first().click();
        Thread.sleep(5000);
        page.waitForSelector(features).isVisible();
    }
    public int getFeaturesCount() {
        return page.locator(features).count();
    }

    public int checkSingleFeatureIsActive(){
        int expandedState=0;
        Locator featureList= page.locator(features);
        for (int i=0;i<featureList.count();i++) {
            if(featureList.nth(i).locator(activeFeature).isVisible()){
                expandedState++;
            }
        }
        return expandedState;
    }

    public String getFirstFeatureContent(){
        return page.locator(features).nth(0).textContent().toString().trim();

    }


}
