package para.bank.cucumber.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import para.bank.AbstractParaBankPageTest;
import para.bank.auth.CreateAccountPage;
import para.bank.constants.Constants;
import para.bank.pages.*;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertEquals;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import java.time.Duration;

public class OpenNewAccountPageStepDefinitions extends AbstractParaBankPageTest {

    WebDriver driver = getDriver();
    CreateAccountPage createAccountPage = new CreateAccountPage(driver);
    AccountsOverviewPage accountsOverviewPage = new AccountsOverviewPage(driver);
    LandingPage landingPage = new LandingPage(driver);
    private OpenNewBankAccountPage openNewBankAccountPage = new OpenNewBankAccountPage(driver);
    private AccountActivityPage accountActivityPage = new AccountActivityPage(driver);
    private String dropdownAccountNumber;
    private String username;
    private String password;

    @Given("the user is on the Landing Page")
    public void theUserIsOnLandingPage() {
        landingPage.open();
    }

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
        Assert.assertEquals(createAccountPage.getWelcomeMessage(), Constants.PageTitles.WELCOME_MESSAGE_TITLE,
                "After Registering an account user has been redirected to the page with another title");
    }

    @When("the user opens a new checking account")
    public void theUserOpensCheckingAccount() {
        openNewBankAccountPage.clickOpenNewAccountPanelButton();
        Assert.assertTrue(openNewBankAccountPage.isPageOpened(), "After clicking 'Open new Bank Account' button page is not opened");

        waitUntilElementStatic(openNewBankAccountPage, openNewBankAccountPage.getFirstAccountNumberInDropdown(), Duration.ofSeconds(5));
        openNewBankAccountPage.clickOpenAccountButton();
        Assert.assertTrue(openNewBankAccountPage.isNewAccountNumberPresent(), "New account number message is not present");
        openNewBankAccountPage.getNewAccountNumber();

        openNewBankAccountPage.clickNewAccountNumber();
        Assert.assertTrue(accountActivityPage.isPageOpened(), "After clicking 'Submit' button on 'Open New bank Acc' page, Account activity page has not been opened");

        waitUntilElementStatic(accountActivityPage, accountActivityPage.getTransactionTable(), Duration.ofSeconds(5));
        String openedAccountNumber = accountActivityPage.getAccountNumber();
        Assert.assertTrue(accountActivityPage.isAccountAnExpectedType("CHECKING"), "After clicking the account number, account is not 'Checking' type");
        Assert.assertEquals(dropdownAccountNumber, openedAccountNumber, "Account number from dropdown doesn't match the number of opened account");

        accountsOverviewPage = new AccountsOverviewPage(driver);
        accountsOverviewPage.clickAccountsOverviewButton();
        Assert.assertTrue(accountsOverviewPage.isPageOpened(), "After clicking 'Accounts Overview' button page isn't opened");

        String firstAccountBalance = accountsOverviewPage.getFirstAccountBalance();
        String secondAccountBalance = accountsOverviewPage.getSecondAccountBalance();
        String firstAccountBalanceExpected = "$49950.00";
        String secondAccountBalanceExpected  = "$50.00";

        Assert.assertEquals(firstAccountBalance, firstAccountBalanceExpected, "Account balance for first account after opening new account is wrong");
        Assert.assertEquals(secondAccountBalance, secondAccountBalanceExpected, "Account balance for second account after opening new account is wrong");
    }

    @When("the user opens a new savings account")
    public void theUserOpensSavingsAccount() {
        openNewBankAccountPage = new OpenNewBankAccountPage(driver);
        openNewBankAccountPage.clickOpenNewAccountPanelButton();
        Assert.assertTrue(openNewBankAccountPage.isPageOpened(), "After clicking 'Open new Bank Account' button page is not opened");

        openNewBankAccountPage.pickSavingsTypeAccount();
        waitUntilElementStatic(openNewBankAccountPage, openNewBankAccountPage.getFirstAccountNumberInDropdown(), Duration.ofSeconds(5));
        openNewBankAccountPage.clickOpenAccountButton();
        Assert.assertTrue(openNewBankAccountPage.isNewAccountNumberPresent(), "New account number message is not present");
        dropdownAccountNumber = openNewBankAccountPage.getNewAccountNumber();

        accountActivityPage = openNewBankAccountPage.clickNewAccountNumber();
        Assert.assertTrue(accountActivityPage.isPageOpened(), "After clicking 'Submit' button on 'Open New bank Acc' page, Account activity page has not been opened");

        waitUntilElementStatic(accountActivityPage, accountActivityPage.getTransactionTable(), Duration.ofSeconds(5));
        String openedAccountNumber = accountActivityPage.getAccountNumber();
        Assert.assertTrue(accountActivityPage.isAccountAnExpectedType("SAVINGS"), "After clicking the account number, account is not 'Savings' type");
        Assert.assertEquals(dropdownAccountNumber, openedAccountNumber, "Account number from dropdown doesn't match the number of opened account");

        accountsOverviewPage = new AccountsOverviewPage(driver);
        accountsOverviewPage.clickAccountsOverviewButton();
        Assert.assertTrue(accountsOverviewPage.isPageOpened(), "After clicking 'Accounts Overview' button page isn't opened");

        String firstAccountBalance = accountsOverviewPage.getFirstAccountBalance();
        String secondAccountBalance = accountsOverviewPage.getSecondAccountBalance();
        Assert.assertEquals(firstAccountBalance, "$49950.00", "Account balance after opening new account is wrong");
        Assert.assertEquals(secondAccountBalance, "$50.00", "Account balance for opened account is wrong");
    }
}
