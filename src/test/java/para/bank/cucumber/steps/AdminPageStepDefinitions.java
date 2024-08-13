package para.bank.cucumber.steps;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;
import para.bank.AbstractParaBankPageTest;
import para.bank.pages.AdminPage;
import para.bank.pages.LandingPage;

import java.time.Duration;

public class AdminPageStepDefinitions extends AbstractParaBankPageTest {
    WebDriver driver = getDriver();
    AdminPage adminPage = new AdminPage(driver);
    LandingPage landingPage = new LandingPage(driver);
    SoftAssert softAssert = new SoftAssert();

    @When("I navigate to the admin page")
    public void navigateToAdminPage() {
        landingPage.openAdminPage();
    }

    @And("I clean the database")
    public void cleanDatabase() {
        adminPage.cleanDatabase();
        waitUntilElementStatic(adminPage, adminPage.getTable(), Duration.ofSeconds(5));
    }

    @And("I set the running status")
    public void setRunningStatus() {
        adminPage.setRunningStatus();
        waitUntilElementStatic(adminPage, adminPage.getTable(), Duration.ofSeconds(5));
    }

    @And("I set the balance")
    public void setBalance() {
        adminPage.setBalance();
    }

    @And("I set the threshold")
    public void setTreshold() {
        adminPage.setThreshold();
    }

    @And("I submit the changes")
    public void submitChanges() {
        adminPage.clickSubmitButton();
        waitUntilElementStatic(adminPage, adminPage.getTable(), Duration.ofSeconds(5));
    }

    @Then("I see the success message {string} should be displayed")
    public void successMessageIsDisplayed(String expectedMessage) {
        softAssert.assertEquals(adminPage.getSuccessMessage(), expectedMessage,
                "Changes for Admin Page were not saved");
        softAssert.assertAll();
    }
}
