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


    @When("I click the Register button")
    public void clickRegisterButton() {
        CreateAccountPage createAccountPage = landingPage.clickRegisterAccountButton();
        Assert.assertTrue(createAccountPage.isPageOpened(),
                "After clicking 'Register' button 'Create account' page is not opened");
    }

    @Given("I am on Create Account Page")
    public void verifyBeingOnCreateAccountPage() {
        landingPage.clickRegisterAccountButton();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(createAccountPage.isPageOpened(),
                "After clicking 'Register' button on Landing page user has been redirected to page with another url" + currentUrl);
    }

    @Then("I am redirected to the Create Account Page")
    public void navigateToCreateAccountPage() {
        Assert.assertTrue(createAccountPage.isPageOpened(),
                "After clicking 'Register' button on Landing page user has been redirected to a page with another URL");
    }

    @When("I fill in the registration form with valid details")
    public void fillInTheRegistrationFormWithValidDetails() {
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

    @And("I click Create Account button")
    public void clickCreateAccountButton() {
        createAccountPage.clickCreateAccountBtn();
    }

    @Then("I see a welcome message after registration")
    public void verifyPresenceOfWelcomeMessage() {
        Assert.assertEquals(createAccountPage.getWelcomeMessage(), Constants.PageTitles.WELCOME_MESSAGE_TITLE,
                "User did not see the expected welcome message");
    }

    @When("I click Accounts Overview button")
    public void redirectToAccountsOverviewPage() {
        createAccountPage.openAccountsOverviewPage();
    }

    @Then("I see Accounts Overview page")
    public void verifyPresenceOfAccountOverviewPage() {
        Assert.assertTrue(accountsOverviewPage.isPageOpened(),
                "After registering an account 'Accounts Overview' page is not opened");
    }

    @When("I click {string} button with empty form")
    public void clickCreateAccountButtonWithoutFillingInTheForm() {
            createAccountPage.clickCreateAccountBtn();
    }

    @Then("I see error messages for all required fields")
    public void verifyErrorMessagesForAllRequiredFields() {
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

    @When("I fill in the correct form with first name having leading and trailing spaces")
    public void fillInTheFirstNameWithLeadingAndTrailingSpaces() {
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
    }

    @Then("I see confirmation that first name is saved with leading and trailing spaces")
    public void verifySavingFirstNameWithLeadingAndTrailingSpaces() {
        String firstNameWithSpaces = "             " + createRandomUsername() + "               ";
        List<Map<String, String>> logs = findUploadedUserData(driver);
        String firstNameValue = logs.get(0).get("customer.firstName");
        Assert.assertEquals(firstNameValue, firstNameWithSpaces, "First name is not saved with leading and trailing spaces as intended");
    }
}
