package para.bank;

import com.zebrunner.agent.core.annotation.TestCaseKey;
import com.zebrunner.carina.core.registrar.ownership.MethodOwner;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import para.bank.auth.CreateAccountPage;
import para.bank.constants.Constants;
import para.bank.pages.*;

import java.time.Duration;
import java.util.List;

public class TransferFundsPageTest extends AbstractParaBankPageTest {

    @Test(description = "Transfer funds between accounts")
    @TestCaseKey("ID-7")
    @MethodOwner(owner = "abuda")
    public void verifyUserCanTransferTheFundsBetweenAccounts()  {
        SoftAssert softAssert = new SoftAssert();
        WebDriver driver = getDriver();

        LandingPage landingPage = new LandingPage(driver);
        landingPage.open();

        CreateAccountPage createAccountPage = landingPage.clickRegisterAccountButton();
        Assert.assertTrue(createAccountPage.isPageOpened(),
                "After clicking 'Register' button 'Create account' page is not opened");

        createAccountPage.createAnAccount();
        softAssert.assertEquals(createAccountPage.getWelcomeMessage(), Constants.PageTitles.WELCOME_MESSAGE_TITLE,
                "After Registering an account user has been redirected to the page with another title");

        OpenNewBankAccountPage openNewBankAccountPage = new OpenNewBankAccountPage(driver);
        openNewBankAccountPage.clickOpenNewAccountPanelButton();
        Assert.assertTrue(openNewBankAccountPage.isPageOpened(),
                "After clicking 'Open new bank account' button on Account Overview user has not been redirected to 'Open new bank account' page\"");

        waitUntilElementStatic(openNewBankAccountPage, openNewBankAccountPage.getFirstAccountNumberInDropdown(), Duration.ofSeconds(5));
        openNewBankAccountPage.clickOpenAccountButton();
        softAssert.assertTrue(openNewBankAccountPage.isNewAccountNumberPresent(),
                "New account number message is not present");
        String dropdownAccountNumber = openNewBankAccountPage.getNewAccountNumber();

        AccountActivityPage accountActivityPage = openNewBankAccountPage.clickNewAccountNumber();
        Assert.assertTrue(accountActivityPage.isPageOpened(),
                "After clicking 'Submit' button on 'Open New bank Acc' page, Account activity page has not been opened");

        waitUntilElementStatic(accountActivityPage, accountActivityPage.getTransactionTable(), Duration.ofSeconds(5));
        String openedAccountNumber = accountActivityPage.getAccountNumber();
        String expectedType = "CHECKING";
        softAssert.assertTrue(accountActivityPage.isAccountAnExpectedType(expectedType),
                "After clicking in the account number, account is not 'Checking' type");
        softAssert.assertEquals(dropdownAccountNumber, openedAccountNumber,
                "Account number from dropdown doesn't match the number of opened account");

        AccountsOverviewPage accountsOverviewPage = new AccountsOverviewPage(driver);
        accountsOverviewPage.clickAccountsOverviewButton();
        Assert.assertTrue(accountsOverviewPage.isPageOpened(),
        "After clicking 'Accounts Overview' button user has not been redirected to page with different title");

        String firstAccountBalance = accountsOverviewPage.getFirstAccountBalance();
        String secondAccountBalance = accountsOverviewPage.getSecondAccountBalance();
        String firstAccExpBalance = "$49950.00";
        String secondAccExpBalance = "$50.00";

        softAssert.assertEquals(firstAccountBalance, firstAccExpBalance,
                "Account balance after opening new account is wrong");
        softAssert.assertEquals(secondAccountBalance,secondAccExpBalance,
                "Account balance for opened account is wrong");

        TransferFundsPage transferFundsPage = accountsOverviewPage.openTransferFundsPage();
        Assert.assertTrue(transferFundsPage.isPageOpened(),
                "After opening 'Transfer funds' page user has not been redirected to 'Transfer funds' page");

        String amountToTransfer = "20";
        transferFundsPage.fillAmount(amountToTransfer);
        transferFundsPage.clickSecondAccountDropdownSecondOption();
        transferFundsPage.clickTransferButton();
        pause(3);
        softAssert.assertEquals(transferFundsPage.getTransferCompletedText(),Constants.PageTitles.TRANSFER_FUNDS_COMPLETED_PAGE_TITLE,
                "After transferring the funds user doesn't see the 'Transfer completed' screen");

        transferFundsPage.openAccountsOverviewPage();
        waitUntilElementStatic(accountsOverviewPage, accountsOverviewPage.getTotal(), Duration.ofSeconds(5));
        Assert.assertTrue(accountsOverviewPage.isPageOpened(),
                "After clicking new account number after opening an account user has been redirected to the page with another title");

        String firstAccountBalanceAfterTransfer = accountsOverviewPage.getFirstAccountBalance();
        String secondAccountBalanceAfterTransfer = accountsOverviewPage.getSecondAccountBalance();
        String firstAccExpBalanceAfterTransfer = "$49930.00";
        String secondAccExpBalanceAfterTransfer = "$70.00";
        softAssert.assertEquals(firstAccountBalanceAfterTransfer, firstAccExpBalanceAfterTransfer,
                "Account balance for first account after transferring funds is wrong");
        softAssert.assertEquals(secondAccountBalanceAfterTransfer, secondAccExpBalanceAfterTransfer,
                "Account balance for second account after transferring funds is wrong");

        softAssert.assertAll();
    }

    @Test(description = "Check if the transfer can be found in the Account Activity in the matching month")
    @TestCaseKey("ID-8")
    @MethodOwner(owner = "abuda")
    public void userChecksMonthForTransfer()  {
        SoftAssert softAssert = new SoftAssert();
        WebDriver driver = getDriver();

        LandingPage landingPage = new LandingPage(driver);
        landingPage.open();

        CreateAccountPage createAccountPage = landingPage.clickRegisterAccountButton();
        Assert.assertTrue(createAccountPage.isPageOpened(),
                "After clicking 'Register' button 'Create account' page is not opened");

        createAccountPage.createAnAccount();
        softAssert.assertEquals(createAccountPage.getWelcomeMessage(), Constants.PageTitles.WELCOME_MESSAGE_TITLE,
                "After Registering an account user has been redirected to the page with another title");

        OpenNewBankAccountPage openNewBankAccountPage = new OpenNewBankAccountPage(driver);
        openNewBankAccountPage.clickOpenNewAccountPanelButton();
        Assert.assertTrue(openNewBankAccountPage.isPageOpened(),
                "After clicking 'Open new bank account' button on Account Overview user has not been redirected to 'Open new bank account' page\"");

        waitUntilElementStatic(openNewBankAccountPage, openNewBankAccountPage.getFirstAccountNumberInDropdown(), Duration.ofSeconds(5));
        openNewBankAccountPage.clickOpenAccountButton();
        softAssert.assertTrue(openNewBankAccountPage.isNewAccountNumberPresent(),
                "New account number message is not present");
        String dropdownAccountNumber = openNewBankAccountPage.getNewAccountNumber();

        AccountActivityPage accountActivityPage = openNewBankAccountPage.clickNewAccountNumber();
        Assert.assertTrue(accountActivityPage.isPageOpened(),
                "After clicking 'Submit' button on 'Open New bank Acc' page, Account activity page has not been opened");

        waitUntilElementStatic(accountActivityPage, accountActivityPage.getTransactionTable(), Duration.ofSeconds(5));
        String openedAccountNumber = accountActivityPage.getAccountNumber();
        softAssert.assertTrue(accountActivityPage.isAccountAnExpectedType("CHECKING"),
                "After clicking in the account number, account is not 'Checking' type");
        softAssert.assertEquals(dropdownAccountNumber, openedAccountNumber,
                "Account number from dropdown doesn't match the number of opened account");

        AccountsOverviewPage accountsOverviewPage = new AccountsOverviewPage(driver);
        accountsOverviewPage.clickAccountsOverviewButton();
        Assert.assertTrue(accountsOverviewPage.isPageOpened(),
                "After clicking new account number after opening an account user has not been redirected to 'Account Activity' page");

        TransferFundsPage transferFundsPage = accountsOverviewPage.openTransferFundsPage();
        Assert.assertTrue(transferFundsPage.isPageOpened(),
                "After opening 'Transfer funds' page user has not been redirected to 'Transfer funds' page");

        String amountToTransfer = "20";
        transferFundsPage.fillAmount(amountToTransfer);
        transferFundsPage.clickSecondAccountDropdownSecondOption();
        transferFundsPage.clickTransferButton();
        pause(2);
        softAssert.assertEquals(transferFundsPage.getTransferCompleted(),Constants.PageTitles.TRANSFER_FUNDS_COMPLETED_PAGE_TITLE,
                "After transferring the funds user doesn't see the 'Transfer completed' screen");

        transferFundsPage.openAccountsOverviewPage();
        softAssert.assertEquals(accountsOverviewPage.getTitleText(), Constants.PageTitles.ACCOUNTS_OVERVIEW_PAGE_TITLE,
                "After clicking new account number after opening an account user has been redirected to the page with another title");

        waitUntilElementStatic(accountsOverviewPage, accountsOverviewPage.getTotal(), Duration.ofSeconds(5));
        accountsOverviewPage.clickFirstAccountNumber();
        softAssert.assertTrue(accountActivityPage.isTransactionTablePresent(),
                "Transaction Table is not present when 'All' is selected");

        List<String> expectedMonthValues = accountActivityPage.getEnumMonthValues();
        List<String> actualMonthValues  = accountActivityPage.getAllMonthValues();
        Assert.assertEquals(expectedMonthValues, actualMonthValues,
                "The month values found in the dropdown do not match the expected values");

        accountActivityPage.fillMonthValue("January");
        accountActivityPage.clickSubmitButton();
        softAssert.assertFalse(accountActivityPage.isTransactionTablePresent(),
                "Transaction Table is present when 'January' is selected");
        softAssert.assertTrue(accountActivityPage.isNoTransactionsFoundTextPresent(),
                "No transaction text is not visible for the month without transfer");

        accountActivityPage.fillMonthValue("February");
        accountActivityPage.clickSubmitButton();
        softAssert.assertFalse(accountActivityPage.isTransactionTablePresent(),
                "Transaction Table is present when 'February' is selected");
        softAssert.assertTrue(accountActivityPage.isNoTransactionsFoundTextPresent(),
                "Transaction text is not visible for the month without transfer");

        accountActivityPage.fillMonthValue("March");
        accountActivityPage.clickSubmitButton();
        softAssert.assertFalse(accountActivityPage.isTransactionTablePresent(),
                "Transaction Table is present when 'March' is selected");
        softAssert.assertTrue(accountActivityPage.isNoTransactionsFoundTextPresent(),
                "Transaction text is not visible for the month without transfer");

        accountActivityPage.fillMonthValue("April");
        accountActivityPage.clickSubmitButton();
        softAssert.assertFalse(accountActivityPage.isTransactionTablePresent(),
                "Transaction Table is present when 'April' is selected");
        softAssert.assertTrue(accountActivityPage.isNoTransactionsFoundTextPresent(),
                "Transaction text is not visible for the month without transfer");

        accountActivityPage.fillMonthValue("May");
        accountActivityPage.clickSubmitButton();
        softAssert.assertFalse(accountActivityPage.isTransactionTablePresent(),
                "Transaction Table is present when 'May' is selected");
        softAssert.assertTrue(accountActivityPage.isNoTransactionsFoundTextPresent(),
                "Transaction text is not visible for the month without transfer");

        accountActivityPage.fillMonthValue("June");
        accountActivityPage.clickSubmitButton();
        softAssert.assertTrue(accountActivityPage.isTransactionTablePresent(),
                "Transaction Table is not present when 'June' is selected");
        softAssert.assertTrue(accountActivityPage.isNoTransactionsFoundTextPresent(),
                "Transaction text is visible for the month without transfer");

        accountActivityPage.fillMonthValue("July");
        accountActivityPage.clickSubmitButton();
        softAssert.assertTrue(accountActivityPage.isTransactionTablePresent(),
                "Transaction Table is not present when 'July' is selected");
        softAssert.assertTrue(accountActivityPage.isNoTransactionsFoundTextPresent(),
                "No transaction text is not visible for the month without transfer");

        accountActivityPage.fillMonthValue("August");
        accountActivityPage.clickSubmitButton();
        softAssert.assertTrue(accountActivityPage.isTransactionTablePresent(),
                "Transaction Table is not present when 'August' is selected");
        softAssert.assertTrue(accountActivityPage.isNoTransactionsFoundTextPresent(),
                "No transaction text is not visible for the month without transfer");

        accountActivityPage.fillMonthValue("September");
        accountActivityPage.clickSubmitButton();
        softAssert.assertTrue(accountActivityPage.isTransactionTablePresent(),
                "Transaction Table is not present when 'September' is selected");
        softAssert.assertTrue(accountActivityPage.isNoTransactionsFoundTextPresent(),
                "No transaction text is not visible for the month without transfer");

        accountActivityPage.fillMonthValue("October");
        accountActivityPage.clickSubmitButton();
        softAssert.assertTrue(accountActivityPage.isTransactionTablePresent(),
                "Transaction Table is not present when 'October' is selected");
        softAssert.assertTrue(accountActivityPage.isNoTransactionsFoundTextPresent(),
                "No transaction text is not visible for the month without transfer");

        accountActivityPage.fillMonthValue("November");
        accountActivityPage.clickSubmitButton();
        softAssert.assertTrue(accountActivityPage.isTransactionTablePresent(),
                "Transaction Table is not present when 'November' is selected");
        softAssert.assertTrue(accountActivityPage.isNoTransactionsFoundTextPresent(),
                "No transaction text is not visible for the month without transfer");

        accountActivityPage.fillMonthValue("December");
        accountActivityPage.clickSubmitButton();
        softAssert.assertTrue(accountActivityPage.isTransactionTablePresent(),
                "Transaction Table is not present when 'December' is selected");
        softAssert.assertTrue(accountActivityPage.isNoTransactionsFoundTextPresent(),
                "No transaction text is not visible for the month without transfer");

        softAssert.assertAll();
    }
}
