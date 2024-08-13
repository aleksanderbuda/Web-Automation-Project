package para.bank.cucumber.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import para.bank.AbstractParaBankPageTest;
import para.bank.pages.*;
import java.time.Duration;

public class OpenNewAccountPageStepDefinitions extends AbstractParaBankPageTest {

    WebDriver driver = getDriver();
    AccountsOverviewPage accountsOverviewPage = new AccountsOverviewPage(driver);
    OpenNewBankAccountPage openNewBankAccountPage = new OpenNewBankAccountPage(driver);
    AccountActivityPage accountActivityPage = new AccountActivityPage(driver);
    private String dropdownAccountNumber;
    private String username;
    private String password;


    @When("I open a checking account")
    public void openCheckingAccount() {
        openNewBankAccountPage.clickOpenNewAccountPanelButton();
        Assert.assertTrue(openNewBankAccountPage.isPageOpened(),
                "After clicking 'Open new Bank Account' button page is not opened");

        waitUntilElementStatic(openNewBankAccountPage, openNewBankAccountPage.getFirstAccountNumberInDropdown(), Duration.ofSeconds(5));
        openNewBankAccountPage.clickOpenAccountButton();
        Assert.assertTrue(openNewBankAccountPage.isNewAccountNumberPresent(),
                "New account number message is not present");
        openNewBankAccountPage.getNewAccountNumber();

        openNewBankAccountPage.clickNewAccountNumber();
        Assert.assertTrue(accountActivityPage.isPageOpened(),
                "After clicking 'Submit' button on 'Open New bank Acc' page, Account activity page has not been opened");

        waitUntilElementStatic(accountActivityPage, accountActivityPage.getTransactionTable(), Duration.ofSeconds(5));
        String openedAccountNumber = accountActivityPage.getAccountNumber();
        Assert.assertTrue(accountActivityPage.isAccountAnExpectedType("CHECKING"),
                "After clicking the account number, account is not 'Checking' type");
        Assert.assertEquals(dropdownAccountNumber, openedAccountNumber,
                "Account number from dropdown doesn't match the number of opened account");
    }
    @Then("I verify checking account is opened")
    public void verifyAccountIsOpened() {
        accountsOverviewPage.clickAccountsOverviewButton();
        Assert.assertTrue(accountsOverviewPage.isPageOpened(),
                "After clicking 'Accounts Overview' button page isn't opened");

        String firstAccountBalance = accountsOverviewPage.getFirstAccountBalance();
        String secondAccountBalance = accountsOverviewPage.getSecondAccountBalance();
        String firstAccountBalanceExpected = "$49950.00";
        String secondAccountBalanceExpected  = "$50.00";

        Assert.assertEquals(firstAccountBalance, firstAccountBalanceExpected,
                "Account balance for first account after opening new account is wrong");
        Assert.assertEquals(secondAccountBalance, secondAccountBalanceExpected,
                "Account balance for second account after opening new account is wrong");
    }

    @When("I open savings account")
    public void openSavingsAccount() {
        openNewBankAccountPage.clickOpenNewAccountPanelButton();
        Assert.assertTrue(openNewBankAccountPage.isPageOpened(),
                "After clicking 'Open new Bank Account' button page is not opened");

        openNewBankAccountPage.pickSavingsTypeAccount();
        waitUntilElementStatic(openNewBankAccountPage, openNewBankAccountPage.getFirstAccountNumberInDropdown(), Duration.ofSeconds(5));
        openNewBankAccountPage.clickOpenAccountButton();
        Assert.assertTrue(openNewBankAccountPage.isNewAccountNumberPresent(),
                "New account number message is not present");
        dropdownAccountNumber = openNewBankAccountPage.getNewAccountNumber();

        accountActivityPage = openNewBankAccountPage.clickNewAccountNumber();
        Assert.assertTrue(accountActivityPage.isPageOpened(),
                "After clicking 'Submit' button on 'Open New bank Acc' page, Account activity page has not been opened");

        waitUntilElementStatic(accountActivityPage, accountActivityPage.getTransactionTable(), Duration.ofSeconds(5));
        String openedAccountNumber = accountActivityPage.getAccountNumber();
        Assert.assertTrue(accountActivityPage.isAccountAnExpectedType("SAVINGS"),
                "After clicking the account number, account is not 'Savings' type");
        Assert.assertEquals(dropdownAccountNumber, openedAccountNumber,
                "Account number from dropdown doesn't match the number of opened account");
    }
    @Then("I verify savings account is opened")
    public void verifySavingsAccountIsOpened() {
        accountsOverviewPage.clickAccountsOverviewButton();
        Assert.assertTrue(accountsOverviewPage.isPageOpened(),
                "After clicking 'Accounts Overview' button page isn't opened");

        String firstAccountBalance = accountsOverviewPage.getFirstAccountBalance();
        String secondAccountBalance = accountsOverviewPage.getSecondAccountBalance();
        Assert.assertEquals(firstAccountBalance, "$49950.00",
                "Account balance after opening new account is wrong");
        Assert.assertEquals(secondAccountBalance, "$50.00",
                "Account balance for opened account is wrong");
    }
}
