package para.bank.cucumber.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import para.bank.AbstractParaBankPageTest;
import para.bank.auth.CreateAccountPage;
import para.bank.constants.Constants;
import para.bank.pages.*;

import java.time.Duration;

public class RequestLoanPageStepDefinition extends AbstractParaBankPageTest {

    WebDriver driver = getDriver();
    CreateAccountPage createAccountPage = new CreateAccountPage(driver);
    AccountsOverviewPage accountsOverviewPage = new AccountsOverviewPage(driver);
    LandingPage landingPage = new LandingPage(driver);
    AccountActivityPage accountActivityPage = new AccountActivityPage(driver);
    RequestLoanPage requestLoanPage = new RequestLoanPage(driver);
    String amount;
    String newAccNumber;

    @When("the user registers a new account with valid details")
    public void theUserRegistersNewAccountWithValidDetails() {
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
        Assert.assertEquals(createAccountPage.getWelcomeMessage(), Constants.PageTitles.WELCOME_MESSAGE_TITLE,
                "After Registering an account user has been redirected to the page with another title");
    }

    @When("the user opens the Request Loan page")
    public void theUserOpensRequestLoanPage() {
        accountsOverviewPage = new AccountsOverviewPage(driver);
        accountsOverviewPage.clickAccountsOverviewButton();
        Assert.assertTrue(accountsOverviewPage.isPageOpened(), "After opening 'Accounts Overview' page has not been redirected to 'Accounts Overview' page");

        String balance = accountsOverviewPage.getFirstAccountBalance();
        amount = balance.replace("$", "").replace(".00", "");

        requestLoanPage = accountsOverviewPage.openRequestLoanPage();
        Assert.assertTrue(requestLoanPage.isPageOpened(), "After opening 'Request Loan' page user has been redirected to different page");
    }

    @When("the user requests a loan with a down payment less than 10% of the loan amount")
    public void theUserRequestsLoanWithDownPaymentLessThan10Percent() {
        requestLoanPage.fillLoanAmount(amount);
        requestLoanPage.fillDownPayment(String.format("%.0f", Double.parseDouble(amount) * 0.09));
        requestLoanPage.clickSubmitButton();
        waitUntilElementStatic(requestLoanPage, requestLoanPage.getTable(), Duration.ofSeconds(5));
    }

    @When("the user requests a loan with a down payment more than their balance allows")
    public void theUserRequestsLoanWithDownPaymentMoreThanBalanceAllows() {
        requestLoanPage.fillLoanAmount(amount);
        requestLoanPage.fillDownPayment(String.format("%.0f", Double.parseDouble(amount) * 1.1));
        requestLoanPage.clickSubmitButton();
        waitUntilElementStatic(requestLoanPage, requestLoanPage.getTable(), Duration.ofSeconds(5));
    }

    @When("the user requests a loan with a valid down payment")
    public void theUserRequestsLoanWithValidDownPayment() {
        requestLoanPage.fillLoanAmount(String.format("%.0f", Double.parseDouble(amount) * 0.5));
        requestLoanPage.fillDownPayment(String.format("%.0f", Double.parseDouble(amount) * 0.05));
        requestLoanPage.clickSubmitButton();
        waitUntilElementStatic(requestLoanPage, requestLoanPage.getTable(), Duration.ofSeconds(5));
    }

    @Then("the user should see an error message and the loan status should be Denied")
    public void theUserShouldSeeErrorMessageAndLoanStatusDenied() {
        Assert.assertEquals(requestLoanPage.getErrorMessage(), "We cannot grant a loan in that amount with the given down payment.",
                "After Requesting a loan user has been redirected to the page without an error");
        Assert.assertEquals(requestLoanPage.getStatus(), "Denied",
                "After Requesting a loan user has been redirected to the page without a proper status");
    }

    @Then("the user should see a success message and the loan status should be Approved")
    public void theUserShouldSeeSuccessMessageAndLoanStatusApproved() {
        Assert.assertEquals(requestLoanPage.getSuccessMessage(), "Congratulations, your loan has been approved.",
                "After Requesting a loan user has been redirected to the page without another message");
        Assert.assertEquals(requestLoanPage.getStatus(), "Approved",
                "After Requesting a loan user has been redirected to the page without a proper status");

        pause(2);
        newAccNumber = requestLoanPage.getNewAccountNumber();
        accountActivityPage = requestLoanPage.clickNewAccNumber();
        Assert.assertTrue(accountActivityPage.isPageOpened(), "After clicking on New Account number user has been redirected to different page than Account Activity");
        waitUntilElementStatic(accountActivityPage, accountActivityPage.getNoTransactionsFoundText(), Duration.ofSeconds(5));
        String openedAccountNumber = accountActivityPage.getAccountNumber();
        String accountType = "LOAN";
        Assert.assertTrue(accountActivityPage.isAccountAnExpectedType(accountType), "After clicking the account number, account is not " + accountType + " type");
        Assert.assertEquals(newAccNumber, openedAccountNumber, "Loan account number doesn't match the number of opened account");
    }
}
