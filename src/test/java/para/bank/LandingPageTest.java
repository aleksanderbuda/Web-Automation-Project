package para.bank;

import com.zebrunner.agent.core.annotation.TestCaseKey;
import com.zebrunner.carina.core.registrar.ownership.MethodOwner;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import para.bank.auth.CreateAccountPage;
import para.bank.constants.Constants;
import para.bank.pages.AboutPage;
import para.bank.pages.AccountsOverviewPage;
import para.bank.pages.LandingPage;
import para.bank.pages.OpenNewBankAccountPage;

import java.time.Duration;

public class LandingPageTest extends AbstractParaBankPageTest {

    @Test(description = "Check about page")
    @TestCaseKey("ID-16")
    @MethodOwner(owner = "abuda")
    public void verifyOpeningAboutPageFromLandingPage() {
        SoftAssert softAssert = new SoftAssert();
        WebDriver driver = getDriver();

        LandingPage landingPage = new LandingPage(driver);
        landingPage.open();

        waitUntilElementStatic(landingPage, landingPage.getAboutLink(), Duration.ofSeconds(5));
        long pageContentHeight = landingPage.getContentPageHeight();
        long aboutLinkPositionY = getBottomY(landingPage.getAboutLink());
        long actualDelta = pageContentHeight - aboutLinkPositionY;
        int expectedDelta = 220;
        softAssert.assertTrue(actualDelta < expectedDelta && actualDelta > 160,
                "'About' is not situated at the bottom of the page");

        AboutPage aboutPage = landingPage.openAboutPage();
        waitUntilElementStatic(aboutPage, aboutPage.getParasoftLink(), Duration.ofSeconds(10));
        softAssert.assertEquals(driver.getCurrentUrl(), Constants.Urls.ABOUT_URL,
                "After clicking 'Terms of Use Link' user has been redirected to another page");
        softAssert.assertEquals(aboutPage.getTitleText(), Constants.PageTitles.ABOUT_PAGE_TITLE,
                "After clicking 'Terms of Use Link' user has been redirected to the page with another title");

        softAssert.assertAll();
    }

    @Test(description = "Check if user can log in with valid account credentials")
    @TestCaseKey("ID-17")
    @MethodOwner(owner = "abuda")
    public void verifyUserIsAbleToLogInWithValidAccount() {
        SoftAssert softAssert = new SoftAssert();
        WebDriver driver = getDriver();

        LandingPage landingPage = new LandingPage(driver);
        landingPage.open();

        CreateAccountPage createAccountPage = landingPage.clickRegisterAccountButton();
        Assert.assertTrue(createAccountPage.isPageOpened(),
                "After clicking 'Register' button 'Create account' page is not opened");

        String firstName = createRandomUsername();
        String lastName = createRandomUsername();
        String address = createRandomAddress();
        String city = StringUtils.capitalize(RandomStringUtils.randomAlphabetic(7).toLowerCase());
        String state = RandomStringUtils.randomAlphabetic(2).toUpperCase();
        String zipCode = RandomStringUtils.randomNumeric(6);
        String phone = createRandomPhoneNumber();
        String ssn = createRandomSSN();
        String username = createRandomUsername();
        String password = createRandomStrongPassword();

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
        softAssert.assertEquals(createAccountPage.getWelcomeMessage(), Constants.PageTitles.WELCOME_MESSAGE_TITLE,
                "After Registering an account user has been redirected to the page with another title");

        AccountsOverviewPage accountsOverviewPage = createAccountPage.openAccountsOverviewPage();
        Assert.assertTrue(accountsOverviewPage.isPageOpened(),
                "After clicking 'Accounts Overview' button user has been redirected to different page");
        accountsOverviewPage.clickLogOutButton();

        softAssert.assertEquals(landingPage.getTitleText(), Constants.PageTitles.LANDING_PAGE_TITLE,
                "After clicking 'Log Out' button user has been redirected to the page with another title");
        landingPage.typeUsername(username);
        landingPage.typePassword(password);
        landingPage.clickLogInButton();
        waitUntilElementStatic(accountsOverviewPage, accountsOverviewPage.getTotal(), Duration.ofSeconds(5));
        Assert.assertTrue(accountsOverviewPage.isPageOpened(),
                "After clicking 'Log In' button user has been redirected to different page");

        softAssert.assertAll();
    }
}
