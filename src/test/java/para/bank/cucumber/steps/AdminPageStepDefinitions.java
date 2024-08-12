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

    @When("the user navigates to the admin page")
    public void UserNavigatesToTheAdminPage() {
        landingPage.openAdminPage();
    }

    @And("cleans the database")
    public void cleansTheDatabase() {
        adminPage.cleanDatabase();
        waitUntilElementStatic(adminPage, adminPage.getTable(), Duration.ofSeconds(5));
    }

    @And("sets the running status")
    public void setsTheRunningStatus() {
        adminPage.setRunningStatus();
        waitUntilElementStatic(adminPage, adminPage.getTable(), Duration.ofSeconds(5));
    }

    @And("sets the balance")
    public void setsTheBalance() {
        adminPage.setBalance();
    }

    @And("sets the threshold")
    public void setsTheTreshold() {
        adminPage.setThreshold();
    }

    @And("submits the changes")
    public void submitsTheChanges() {
        adminPage.clickSubmitButton();
        waitUntilElementStatic(adminPage, adminPage.getTable(), Duration.ofSeconds(5));
    }

    @Then("the success message {string} should be displayed")
    public void theSuccessMessageShouldBeDisplayed(String expectedMessage) {
        softAssert.assertEquals(adminPage.getSuccessMessage(), expectedMessage,
                "Changes for Admin Page were not saved");
        softAssert.assertAll();
    }
}
