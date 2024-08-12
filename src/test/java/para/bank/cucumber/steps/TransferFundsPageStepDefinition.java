package para.bank.cucumber.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import para.bank.AbstractParaBankPageTest;
import para.bank.auth.CreateAccountPage;
import para.bank.constants.Constants;
import para.bank.pages.*;

import java.time.Duration;
import java.util.List;

public class TransferFundsPageStepDefinition extends AbstractParaBankPageTest {
    WebDriver driver = getDriver();
    CreateAccountPage createAccountPage = new CreateAccountPage(driver);
    AccountsOverviewPage accountsOverviewPage = new AccountsOverviewPage(driver);
    LandingPage landingPage = new LandingPage(driver);
    OpenNewBankAccountPage openNewBankAccountPage = new OpenNewBankAccountPage(driver);
    AccountActivityPage accountActivityPage = new AccountActivityPage(driver);
    TransferFundsPage transferFundsPage = new TransferFundsPage(driver);


    @Then("I should see the new account number")
    public void iShouldSeeTheNewAccountNumber() {
        String dropdownAccountNumber = openNewBankAccountPage.getNewAccountNumber();
        accountActivityPage = openNewBankAccountPage.clickNewAccountNumber();
        Assert.assertTrue(accountActivityPage.isPageOpened(), "Account activity page is not opened");
        waitUntilElementStatic(accountActivityPage, accountActivityPage.getTransactionTable(), Duration.ofSeconds(5));
        String openedAccountNumber = accountActivityPage.getAccountNumber();
        Assert.assertEquals(dropdownAccountNumber, openedAccountNumber, "Account number mismatch");
    }

    @And("I open the new account number")
    public void iOpenTheNewAccountNumber() {
    }

    @Then("I should see the account activity page with checking type account")
    public void iShouldSeeTheAccountActivityPageWithCheckingTypeAccount() {
        String expectedType = "CHECKING";
        Assert.assertTrue(accountActivityPage.isAccountAnExpectedType(expectedType), "Account type is not checking");
    }

    @When("I transfer funds from the first account to the second account")
    public void iTransferFundsFromTheFirstAccountToTheSecondAccount() {
        accountsOverviewPage = new AccountsOverviewPage(driver);
        accountsOverviewPage.clickAccountsOverviewButton();
        Assert.assertTrue(accountsOverviewPage.isPageOpened(), "Accounts Overview page is not opened");

        transferFundsPage = accountsOverviewPage.openTransferFundsPage();
        Assert.assertTrue(transferFundsPage.isPageOpened(), "Transfer funds page is not opened");

        String amountToTransfer = "20";
        transferFundsPage.fillAmount(amountToTransfer);
        transferFundsPage.clickSecondAccountDropdownSecondOption();
        transferFundsPage.clickTransferButton();
        pause(3);
    }

    @Then("I should see the transfer completed page")
    public void iShouldSeeTheTransferCompletedPage() {
        Assert.assertEquals(transferFundsPage.getTransferCompletedText(), Constants.PageTitles.TRANSFER_FUNDS_COMPLETED_PAGE_TITLE,
                "Transfer completed page title is not correct");
    }

    @Then("the account balances should be updated correctly")
    public void theAccountBalancesShouldBeUpdatedCorrectly() {
        accountsOverviewPage = new AccountsOverviewPage(driver);
        accountsOverviewPage.clickAccountsOverviewButton();
        waitUntilElementStatic(accountsOverviewPage, accountsOverviewPage.getTotal(), Duration.ofSeconds(5));

        String firstAccountBalanceAfterTransfer = accountsOverviewPage.getFirstAccountBalance();
        String secondAccountBalanceAfterTransfer = accountsOverviewPage.getSecondAccountBalance();
        String firstAccExpBalanceAfterTransfer = "$49930.00";
        String secondAccExpBalanceAfterTransfer = "$70.00";

        Assert.assertEquals(firstAccountBalanceAfterTransfer, firstAccExpBalanceAfterTransfer,
                "First account balance after transfer is incorrect");
        Assert.assertEquals(secondAccountBalanceAfterTransfer, secondAccExpBalanceAfterTransfer,
                "Second account balance after transfer is incorrect");


    }

    @When("I view the account activity for each month")
    public void iViewTheAccountActivityForEachMonth() {
        accountActivityPage = new AccountActivityPage(driver);
        accountsOverviewPage.clickFirstAccountNumber();
    }

    @Then("I should verify the transaction presence or absence as per the month")
    public void iShouldVerifyTheTransactionPresenceOrAbsenceAsPerTheMonth() {
        List<String> expectedMonthValues = accountActivityPage.getEnumMonthValues();
        List<String> actualMonthValues  = accountActivityPage.getAllMonthValues();
        Assert.assertEquals(expectedMonthValues, actualMonthValues, "Month values in the dropdown are incorrect");

        verifyMonthTransactions("January", false);
        verifyMonthTransactions("February", false);
        verifyMonthTransactions("March", false);
        verifyMonthTransactions("April", false);
        verifyMonthTransactions("May", false);
        verifyMonthTransactions("June", true);
        verifyMonthTransactions("July", true);
        verifyMonthTransactions("August", true);
        verifyMonthTransactions("September", true);
        verifyMonthTransactions("October", true);
        verifyMonthTransactions("November", true);
        verifyMonthTransactions("December", true);
    }

    private void verifyMonthTransactions(String month, boolean shouldHaveTransactions) {
        accountActivityPage.fillMonthValue(month);
        accountActivityPage.clickSubmitButton();
        if (shouldHaveTransactions) {
            Assert.assertTrue(accountActivityPage.isTransactionTablePresent(), "Transaction Table is not present for " + month);
            Assert.assertTrue(accountActivityPage.isNoTransactionsFoundTextPresent(), "No transactions text is not visible for " + month);
        } else {
            Assert.assertFalse(accountActivityPage.isTransactionTablePresent(), "Transaction Table is present for " + month);
            Assert.assertTrue(accountActivityPage.isNoTransactionsFoundTextPresent(), "No transactions text is not visible for " + month);
        }
    }
}
