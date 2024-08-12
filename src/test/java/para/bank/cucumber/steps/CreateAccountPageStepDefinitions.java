package para.bank.cucumber.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import para.bank.AbstractParaBankPageTest;
import para.bank.auth.CreateAccountPage;
import para.bank.constants.Constants;
import para.bank.pages.AccountsOverviewPage;
import para.bank.pages.LandingPage;

import java.util.List;
import java.util.Map;

import static para.bank.CreateAccountPageTest.findUploadedUserData;

public class CreateAccountPageStepDefinitions extends AbstractParaBankPageTest {
    WebDriver driver = getDriver();
    CreateAccountPage createAccountPage = new CreateAccountPage(driver);
    AccountsOverviewPage accountsOverviewPage = new AccountsOverviewPage(driver);
    LandingPage landingPage = new LandingPage(driver);

    @Given("the user is on the landing page")
    public void theUserIsOnLandingPage() {
        landingPage.open();
    }

    @When("the user clicks the Register button")
    public void theUserClicksTheButton() {
        CreateAccountPage createAccountPage = landingPage.clickRegisterAccountButton();
        Assert.assertTrue(createAccountPage.isPageOpened(),
                "After clicking 'Register' button 'Create account' page is not opened");
    }

    @Given("the user is on the Create Account Page")
    public void theUserIsOnTheCreateAccountPage() {
        CreateAccountPage createAccountPage = landingPage.clickRegisterAccountButton();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(createAccountPage.isPageOpened(),
                "After clicking 'Register' button on Landing page user has been redirected to page with another url" + currentUrl);
    }

    @Then("the user should be redirected to the Create Account Page")
    public void theUserShouldBeRedirectedToCreateAccountPage() {
        Assert.assertTrue(createAccountPage.isPageOpened(),
                "After clicking 'Register' button on Landing page user has been redirected to a page with another URL");
    }

    @When("the user fills in the registration form with valid details")
    public void theUserFillsInTheRegistrationFormWithValidDetails() {
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
    }

    @And("the user clicks the Create Account button")
    public void theUserClicksTheCreateAccountButton() {
        createAccountPage.clickCreateAccountBtn();
    }

    @Then("the user should see a welcome message")
    public void theUserShouldSeeAWelcomeMessage() {
        Assert.assertEquals(createAccountPage.getWelcomeMessage(), Constants.PageTitles.WELCOME_MESSAGE_TITLE,
                "User did not see the expected welcome message");
    }

    @Then("the user should be redirected to the Accounts Overview Page")
    public void theUserShouldBeRedirectedToAccountsOverviewPage() {
        createAccountPage.openAccountsOverviewPage();
        Assert.assertTrue(accountsOverviewPage.isPageOpened(),
                "After registering an account 'Accounts Overview' page is not opened");
    }

    @When("the user clicks the Create Account button without filling in the form")
    public void theUserClicksTheCreateAccountButtonWithoutFillingInTheForm() {
            createAccountPage.clickCreateAccountBtn();
    }

    @Then("the user should see error messages for all required fields")
    public void theUserShouldSeeErrorMessagesForAllRequiredFields() {
        Assert.assertTrue(createAccountPage.isFirstNameErrorPresent(), "First name error message is not present");
        Assert.assertTrue(createAccountPage.isLastNameErrorPresent(), "Last name error message is not present");
        Assert.assertTrue(createAccountPage.isAddressErrorPresent(), "Address error message is not present");
        Assert.assertTrue(createAccountPage.isCityErrorPresent(), "City error message is not present");
        Assert.assertTrue(createAccountPage.isStateErrorPresent(), "State error message is not present");
        Assert.assertTrue(createAccountPage.isZipCodeErrorPresent(), "ZipCode error message is not present");
        Assert.assertTrue(createAccountPage.isPhoneErrorPresent(), "Phone error message is not present");
        Assert.assertTrue(createAccountPage.isSsnErrorPresent(), "SSN error message is not present");
        Assert.assertTrue(createAccountPage.isUsernameErrorPresent(), "Username error message is not present");
        Assert.assertTrue(createAccountPage.isPasswordErrorPresent(), "Password error message is not present");
        Assert.assertTrue(createAccountPage.isConfirmPasswordErrorPresent(), "Confirmation password error message is not present");
    }

    @When("the user fills in the first name with leading and trailing spaces")
    public void theUserFillsInTheFirstNameWithLeadingAndTrailingSpaces() {
        String firstNameWithSpaces = "             " + createRandomUsername() + "               ";
        createAccountPage.fillName(firstNameWithSpaces);
    }

    @Then("the first name should be saved with leading and trailing spaces")
    public void theFirstNameShouldBeSavedWithLeadingAndTrailingSpaces() {
        String firstNameWithSpaces = "             " + createRandomUsername() + "               ";
        List<Map<String, String>> logs = findUploadedUserData(driver);
        String firstNameValue = logs.get(0).get("customer.firstName");
        Assert.assertEquals(firstNameValue, firstNameWithSpaces, "First name is not saved with leading and trailing spaces as intended");
    }
}
