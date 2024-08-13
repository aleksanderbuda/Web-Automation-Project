package para.bank.cucumber.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import para.bank.AbstractParaBankPageTest;
import para.bank.pages.AboutPage;
import para.bank.pages.AccountsOverviewPage;
import para.bank.pages.LandingPage;
import para.bank.auth.CreateAccountPage;
import para.bank.constants.Constants;
import java.time.Duration;

public class LandingPageStepDefinitions extends AbstractParaBankPageTest {

    WebDriver driver = getDriver();
    LandingPage landingPage = new LandingPage(driver);
    AboutPage aboutPage = new AboutPage(driver);
    CreateAccountPage createAccountPage = new CreateAccountPage(driver);
    AccountsOverviewPage accountsOverviewPage = new AccountsOverviewPage(driver);
    private String username;
    private String password;


    @Given("I am on the landing page")
    public void verifyPresenceOfLandingPage() {
        landingPage.open();
    }

    @And("I click on the {string} link")
    public void openAboutPage() {
        waitUntilElementStatic(landingPage, landingPage.getOpenAboutPage(), Duration.ofSeconds(5));
        landingPage.openAboutPage();
        waitUntilElementStatic(aboutPage, aboutPage.getParasoftLink(), Duration.ofSeconds(10));
    }

    @When("I check if the {string} link should is positioned at the bottom of the page")
    public void theAboutLinkShouldBePositionedAtTheBottomOfPage() {
        long pageContentHeight = landingPage.getContentPageHeight();
        long aboutLinkPositionY = getBottomY(landingPage.getOpenAboutPage());
        long actualDelta = pageContentHeight - aboutLinkPositionY;
        int expectedDelta = 220;
        Assert.assertTrue(actualDelta < expectedDelta && actualDelta > 160,
                "'About' is not situated at the bottom of the page");
    }

    @And("I am redirected to the About Page if the link is in correct place")
    public void redirectToAboutPage() {
        Assert.assertEquals(driver.getCurrentUrl(), Constants.Urls.ABOUT_URL,
                "After clicking 'About' link user has been redirected to another page");
    }

    @Then("I verify that About Page title is correct")
    public void aboutPageTitleIsCorrect() {
        Assert.assertEquals(aboutPage.getTitleText(), Constants.PageTitles.ABOUT_PAGE_TITLE,
                "After clicking 'About' link user has been redirected to the page with another title");
    }

    @When("I log out")
    public void logOut() {
        createAccountPage.openAccountsOverviewPage();
        Assert.assertTrue(accountsOverviewPage.isPageOpened(), "After clicking 'Accounts Overview' button user has been redirected to different page");
        accountsOverviewPage.clickLogOutButton();
    }

    @And("I log in  with valid credentials")
    public void logIn() {
        landingPage.typeUsername(username);
        landingPage.typePassword(password);
        landingPage.clickLogInButton();
        waitUntilElementStatic(landingPage, landingPage.getOpenAboutPage(), Duration.ofSeconds(5));
        Assert.assertTrue(accountsOverviewPage.isPageOpened(), "After clicking 'Log In' button user has been redirected to different page");
    }

    @Then("I am redirected to the Accounts Overview Page after logging in")
    public void redirectToAccOverviewPage() {
        Assert.assertTrue(accountsOverviewPage.isPageOpened(), "After logging in user has not been redirected to the Accounts Overview page");
    }
}
