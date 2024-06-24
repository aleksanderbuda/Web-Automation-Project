package para.bank;

import com.zebrunner.agent.core.annotation.TestCaseKey;
import com.zebrunner.carina.core.registrar.ownership.MethodOwner;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import para.bank.pages.AdminPage;
import para.bank.pages.LandingPage;

import java.time.Duration;

public class AdminPageTest extends AbstractParaBankPageTest {

    @Test(description = "Admin Page Setup is necessary before testing on this particular website")
    @TestCaseKey("ID")
    @MethodOwner(owner = "abuda")
    public void userSetsUpAdminPage() {
        SoftAssert softAssert = new SoftAssert();
        WebDriver driver = getDriver();

        LandingPage landingPage = new LandingPage(driver);
        landingPage.open();

        AdminPage adminPage = landingPage.openAdminPage();
        adminPage.cleanDatabase();
        waitUntilElementStatic(adminPage, adminPage.getTable(), Duration.ofSeconds(5));
        adminPage.setRunningStatus();
        waitUntilElementStatic(adminPage, adminPage.getTable(), Duration.ofSeconds(5));
        adminPage.setBalance();
        adminPage.setThreshold();
        adminPage.clickSubmitButton();

        waitUntilElementStatic(adminPage, adminPage.getTable(), Duration.ofSeconds(5));
        softAssert.assertEquals(adminPage.getSuccessMessage(), "Settings saved successfully.",
                "Changes for Admin Page were not saved");

        softAssert.assertAll();
    }
}
