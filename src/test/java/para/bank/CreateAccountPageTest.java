package para.bank;

import com.zebrunner.agent.core.annotation.TestCaseKey;
import com.zebrunner.carina.core.registrar.ownership.MethodOwner;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import para.bank.auth.CreateAccountPage;
import para.bank.constants.Constants;
import para.bank.pages.AccountsOverviewPage;
import para.bank.pages.LandingPage;
import para.bank.pages.OpenNewBankAccountPage;
import para.bank.utils.NetworkUtils;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class CreateAccountPageTest extends AbstractParaBankPageTest {

    @Test(description = "Check redirect to 'Create Account' Page From 'Landing Page'")
    @TestCaseKey("ID-1")
    @MethodOwner(owner = "abuda")
    public void verifyRedirectToCreateAccountPageFromLanding() {
        SoftAssert softAssert = new SoftAssert();
        WebDriver driver = getDriver();

        LandingPage landingPage = new LandingPage(driver);
        landingPage.open();

        CreateAccountPage createAccountPage = landingPage.clickRegisterAccountButton();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(createAccountPage.isPageOpened(),
                "After clicking 'Register' button on Landing page user has been redirected to page with another url" + currentUrl);

        softAssert.assertAll();
    }

    @Test(description = "Check if user can register")
    @TestCaseKey("ID-2")
    @MethodOwner(owner = "abuda")
    public void verifyUserRegisterAccount() {
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
                "After clicking Registering an account user has been redirected to the page with another title");

        AccountsOverviewPage accountsOverviewPage = createAccountPage.openAccountsOverviewPage();
        Assert.assertTrue(accountsOverviewPage.isPageOpened(),
                "After registering an account 'Accounts overview' page is not opened");

        softAssert.assertAll();
    }

    @Test(description = "Check if fields for registration are required")
    @TestCaseKey("ID-3")
    @MethodOwner(owner = "abuda")
    public void verifyRegistrationFieldsAreRequired() {
        SoftAssert softAssert = new SoftAssert();
        WebDriver driver = getDriver();

        LandingPage landingPage = new LandingPage(driver);
        landingPage.open();

        CreateAccountPage createAccountPage = landingPage.clickRegisterAccountButton();
        Assert.assertTrue(createAccountPage.isPageOpened(),
                "After clicking 'Register' button on Landing page, 'Create account' page is not opened");

        softAssert.assertFalse(createAccountPage.isFirstNameErrorPresent(), "First name error message is present before clicking Create Account button");
        softAssert.assertFalse(createAccountPage.isLastNameErrorPresent(), "Last name error message is present before clicking Create Account button");
        softAssert.assertFalse(createAccountPage.isAddressErrorPresent(), "Address error message is present before clicking Create Account button");
        softAssert.assertFalse(createAccountPage.isCityErrorPresent(), "City error message is present before clicking Create Account button");
        softAssert.assertFalse(createAccountPage.isStateErrorPresent(), "State error message is present before clicking Create Account button");
        softAssert.assertFalse(createAccountPage.isZipCodeErrorPresent(), "ZipCode error message is present before clicking Create Account button");
        softAssert.assertFalse(createAccountPage.isPhoneErrorPresent(), "Phone error message is present before clicking Create Account button");
        softAssert.assertFalse(createAccountPage.isSsnErrorPresent(), "SSN error message is present before clicking Create Account button");
        softAssert.assertFalse(createAccountPage.isUsernameErrorPresent(), "Username error message is present before clicking Create Account button");
        softAssert.assertFalse(createAccountPage.isPasswordErrorPresent(), "Password error message is present before clicking Create Account button");
        softAssert.assertFalse(createAccountPage.isConfirmPasswordErrorPresent(), "Confirmation password error message is present before clicking Create Account button");
        List<ExtendedWebElement> errorMessage = createAccountPage.getErrorTextElements();
        softAssert.assertEquals(errorMessage.size(), 0, "Errors are visible when shouldn't be");

        createAccountPage.clickCreateAccountBtn();
        waitUntilElementStatic(createAccountPage, createAccountPage.getCreateAccountFormErrors(), Duration.ofSeconds(5));

        softAssert.assertTrue(createAccountPage.isFirstNameErrorPresent(), "First name error message isn't present after clicking Create Account button");
        softAssert.assertTrue(createAccountPage.isLastNameErrorPresent(), "Lat name error message isn't present after clicking Create Account button");
        softAssert.assertTrue(createAccountPage.isAddressErrorPresent(), "Address error message isn't present after clicking Create Account button");
        softAssert.assertTrue(createAccountPage.isCityErrorPresent(), "City error message isn't present after clicking Create Account button");
        softAssert.assertTrue(createAccountPage.isStateErrorPresent(), "State error message isn't present after clicking Create Account button");
        softAssert.assertTrue(createAccountPage.isZipCodeErrorPresent(), "ZipCode error message isn't present after clicking Create Account button");
        softAssert.assertTrue(createAccountPage.isPhoneErrorPresent(), "Phone error message isn't present after clicking Create Account button");
        softAssert.assertTrue(createAccountPage.isSsnErrorPresent(), "SSN error message isn't present after clicking Create Account button");
        softAssert.assertTrue(createAccountPage.isUsernameErrorPresent(), "Username error message isn't present after clicking Create Account button");
        softAssert.assertTrue(createAccountPage.isPasswordErrorPresent(), "Password error message isn't present after clicking Create Account button");
        softAssert.assertTrue(createAccountPage.isConfirmPasswordErrorPresent(), "Confirmation password error message isn't  present after clicking Create Account button");
        softAssert.assertEquals(errorMessage.size(), 10, "Errors are not visible when should be");

        softAssert.assertAll();
    }

    @Test(description = "Check there is no whitespace trimming for first name")
    @TestCaseKey("ID-4")
    @MethodOwner(owner = "abuda")
    public void verifyWhitespaceTrimmingForFirstName() {
        SoftAssert softAssert = new SoftAssert();
        WebDriver driver = getDriver(DEFAULT, prepareLoggingCapabilities());

        LandingPage landingPage = new LandingPage(driver);
        landingPage.open();

        CreateAccountPage createAccountPage = landingPage.clickRegisterAccountButton();
        Assert.assertTrue(createAccountPage.isPageOpened(),
                "After clicking 'Register' button 'Create account' page is not opened");

        String firstName = createRandomUsername();
        String firstNameWithSpaces = "             " + firstName + "               ";
        String lastName = createRandomUsername();
        String address = createRandomAddress();
        String city = StringUtils.capitalize(RandomStringUtils.randomAlphabetic(7).toLowerCase());
        String state = RandomStringUtils.randomAlphabetic(2).toUpperCase();
        String zipCode = RandomStringUtils.randomNumeric(6);
        String phone = createRandomPhoneNumber();
        String ssn = createRandomSSN();
        String username = createRandomUsername();
        String password = createRandomStrongPassword();

        createAccountPage.fillName(firstNameWithSpaces);
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

        List<Map<String, String>> logs = findUploadedUserData(driver);
        String firstNameValue = logs.get(0).get("customer.firstName");
        softAssert.assertEquals(firstNameValue, firstNameWithSpaces, "First name is not trimmed as intended");

        softAssert.assertAll();
    }

        private static List<Map<String, String>> findUploadedUserData (WebDriver driver){
            return NetworkUtils.findLogsByUrlAndMethod(driver, "/register.htm", "POST"::equals).stream()
                    .map(NetworkUtils::getBodyAsString)
                    .map(CreateAccountPageTest::getQueryParams)
                    .collect(Collectors.toList());
        }

        protected static List<NameValuePair> getCurrentQueryParams (String path){
            List<NameValuePair> queryParams = new ArrayList<>();
            try {
                queryParams = URLEncodedUtils.parse(path, StandardCharsets.UTF_8);
            } catch (Exception e) {
                LOGGER.warn("Unable to parse an URI. " + e.getMessage());
            }
            return queryParams;
        }

        public static Map<String, String> getQueryParams (String url){
            return getCurrentQueryParams(url).stream()
                    .collect(Collectors.toMap(NameValuePair::getName, NameValuePair::getValue, (value1, value2) -> String.join(",", value1, value2), LinkedHashMap::new));
        }
    }

