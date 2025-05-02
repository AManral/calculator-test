package base;

import com.microsoft.playwright.Page;
import org.calculator.factory.PlaywrightFactory;
import org.testng.ITestListener;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import pages.CalculatorPage;
import pages.GoogleHomePage;

import java.util.logging.Logger;

@Listeners(BaseTest.class)
public class BaseTest implements ITestListener {

    PlaywrightFactory pf;
    static  Page page;
    protected GoogleHomePage googleHomePage;
    protected CalculatorPage calculatorPage;
    static Logger log = Logger.getLogger(BaseTest.class.getName());


    @BeforeTest
    public void setup() throws InterruptedException {
        pf  = new PlaywrightFactory();
        page = pf.initBrowser("firefox");
        googleHomePage = new GoogleHomePage(page);
        calculatorPage = new CalculatorPage(page);


        try {
            googleHomePage.searchTerm("calculator");
        }catch (Exception e){
            log.severe("Error while opening google calculator");
        }
    }

    @AfterTest
    public void tearDown(){
        page.context().browser().close();
    }
}
