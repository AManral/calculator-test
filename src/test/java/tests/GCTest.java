package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.GCPage;

public class GCTest extends BaseTest {


    @Test
    public void TestGCPage() throws InterruptedException {

//        page.navigate("https://ww3.gcpay.com/");
        System.out.println("This is test");
        gcPage.gotToFeaturesPage();

        Assert.assertEquals(gcPage.getFeaturesCount(),4);
        Assert.assertEquals(gcPage.checkSingleFeatureIsActive(),1);

        Assert.assertTrue(gcPage.getFirstFeatureContent().contains("1. Streamline Your Payment Processes"));
        Assert.assertTrue(gcPage.getFirstFeatureContent().contains("GCPay integrates payment applications directly into individual projects, ensuring streamlined progress payments and reducing the time spent on manual processing by up to 70%"));


    }
}
