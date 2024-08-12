package para.bank.cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;


@CucumberOptions(
        features = "src/test/resources/features/create_account_page.feature",
        glue = "para.bank.cucumber.steps",
        plugin = {"pretty", "html:target/cucumber-reports"}
//        tags = "@createacc"
)
public class CucumberTestRunner extends AbstractTestNGCucumberTests {
    private WebDriver driver;

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}