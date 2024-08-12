package para.bank.cucumber.steps;

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
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertEquals;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import java.time.Duration;

public class LandingPageStepDefinitions extends AbstractParaBankPageTest {

    WebDriver driver = getDriver();
    LandingPage landingPage = new LandingPage(driver);
    private AboutPage aboutPage = new AboutPage(driver);
    CreateAccountPage createAccountPage = new CreateAccountPage(driver);
    AccountsOverviewPage accountsOverviewPage = new AccountsOverviewPage(driver);
    private String username;
    private String password;


    @Given("the user is on the Landing Page")
    public void theUserIsOnLandingPage() {
        landingPage.open();
    }

    @When("the user opens the About Page")
    public void userOpensAboutPage() {
        waitUntilElementStatic(landingPage, landingPage.getOpenAboutPage(), Duration.ofSeconds(5));
        landingPage.openAboutPage();
        waitUntilElementStatic(aboutPage, aboutPage.getParasoftLink(), Duration.ofSeconds(10));
    }

    @Then("the 'About' link should be positioned at the bottom of the page")
    public void theAboutLinkShouldBePositionedAtTheBottomOfPage() {
        long pageContentHeight = landingPage.getContentPageHeight();
        long aboutLinkPositionY = getBottomY(landingPage.getOpenAboutPage());
        long actualDelta = pageContentHeight - aboutLinkPositionY;
        int expectedDelta = 220;
        Assert.assertTrue(actualDelta < expectedDelta && actualDelta > 160,
                "'About' is not situated at the bottom of the page");
    }

    @Then("the user should be redirected to the About Page")
    public void theUserShouldBeRedirectedToAboutPage() {
        Assert.assertEquals(driver.getCurrentUrl(), Constants.Urls.ABOUT_URL,
                "After clicking 'About' link user has been redirected to another page");
    }

    @Then("the About Page title should be correct")
    public void theAboutPageTitleShouldBeCorrect() {
        Assert.assertEquals(aboutPage.getTitleText(), Constants.PageTitles.ABOUT_PAGE_TITLE,
                "After clicking 'About' link user has been redirected to the page with another title");
    }

    @When("the user registers a new account with valid details")
    public void the_user_registers_a_new_account_with_valid_details() {
        createAccountPage = landingPage.clickRegisterAccountButton();
        Assert.assertTrue(createAccountPage.isPageOpened(), "After clicking 'Register' button 'Create account' page is not opened");

        String firstName = createRandomUsername();
        String lastName = createRandomUsername();
        String address = createRandomAddress();
        String city = StringUtils.capitalize(RandomStringUtils.randomAlphabetic(7).toLowerCase());
        String state = RandomStringUtils.randomAlphabetic(2).toUpperCase();
        String zipCode = RandomStringUtils.randomNumeric(6);
        String phone = createRandomPhoneNumber();
        String ssn = createRandomSSN();
        username = createRandomUsername();
        password = createRandomStrongPassword();

        createAccountPage.fillName(firstName);
        createAccountPage.fillLastName(lastName);
        createAccountPage.fillAddress(address);
        createAccountPage.fillCity(city);
        createAccountPage.fillState(state);
        createAccountPage.fillZipCode(zipCode);
        createAccountPage.fillPhoneNumber(phone);
        createAccountPage.fillSecurityNum(ssn);
        createAccountPage.fillUsername(username);
        createAccountPage.fillPassword(password);
        createAccountPage.fillConfirmPassword(password);
        createAccountPage.clickCreateAccountBtn();
        assertEquals(createAccountPage.getWelcomeMessage(), Constants.PageTitles.WELCOME_MESSAGE_TITLE,
                "After Registering an account user has been redirected to the page with another title");
    }

    @When("the user logs out")
    public void the_user_logs_out() {
        accountsOverviewPage = createAccountPage.openAccountsOverviewPage();
        Assert.assertTrue(accountsOverviewPage.isPageOpened(), "After clicking 'Accounts Overview' button user has been redirected to different page");
        accountsOverviewPage.clickLogOutButton();
    }

    @When("the user logs in with valid credentials")
    public void the_user_logs_in_with_valid_credentials() {
        landingPage.typeUsername(username);
        landingPage.typePassword(password);
        landingPage.clickLogInButton();
        waitUntilElementStatic(landingPage, landingPage.getOpenAboutPage(), Duration.ofSeconds(5));
        Assert.assertTrue(accountsOverviewPage.isPageOpened(), "After clicking 'Log In' button user has been redirected to different page");
    }

    @Then("the user should be redirected to the Accounts Overview Page")
    public void the_user_should_be_redirected_to_the_Accounts_Overview_Page() {
        Assert.assertTrue(accountsOverviewPage.isPageOpened(), "After logging in user has not been redirected to the Accounts Overview page");
    }
}
