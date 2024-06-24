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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FindTransactionsPageTest extends AbstractParaBankPageTest {

    @Test(description = "Check if user can find his transaction by ID")
    @TestCaseKey("ID-13")
    @MethodOwner(owner = "abuda")
    public void checkIfUserFindsTransactionById()  {
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

        AccountsOverviewPage accountsOverviewPage = new AccountsOverviewPage(driver);
        accountsOverviewPage.clickAccountsOverviewButton();
        Assert.assertTrue(accountsOverviewPage.isPageOpened(),
                "After clicking 'Accounts Overview' button user has not been redirected to page with different title");

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

        AccountActivityPage accountActivityPage = accountsOverviewPage.clickSecondAccountNumber();
        Assert.assertTrue(accountActivityPage.isPageOpened(),
                "After clicking first account number in the table user has been redirected to the page with another title");
        waitUntilElementStatic(accountActivityPage, accountActivityPage.getTransactionTable(), Duration.ofSeconds(5));

        TransactionDetailsPage transactionDetailsPage = accountActivityPage.clickTableSecondTransaction();
        Assert.assertTrue(transactionDetailsPage.isPageOpened(),
                "After clicking Second transaction number in the table user has been redirected to the page with another title");
        String id = transactionDetailsPage.getId();

        FindTransactionsPage findTransactionsPage = transactionDetailsPage.openFindTransactionsPage();
        Assert.assertTrue(findTransactionsPage.isPageOpened(),
                "After clicking on 'Find Transactions' page user has been redirected to the page with another title");
        findTransactionsPage.clickSecondOption();
        findTransactionsPage.fillId(id);
        findTransactionsPage.clickFindByIdButton();

        waitUntilElementStatic(transactionDetailsPage, transactionDetailsPage.getTransactionResultsTable(), Duration.ofSeconds(5));
        pause(2);
        transactionDetailsPage.clickSecondTransaction();
        String idAfterFind = transactionDetailsPage.getId();
        softAssert.assertEquals(id, idAfterFind,
                "Found ID is different from the ID provided while finding transaction");

        softAssert.assertAll();
    }

    @Test(description = "Check if user can find his transaction by date")
    @TestCaseKey("ID-14")
    @MethodOwner(owner = "abuda")
    public void checkIfUserFindsTransactionByDate()  {
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

        AccountsOverviewPage accountsOverviewPage = new AccountsOverviewPage(driver);
        accountsOverviewPage.clickAccountsOverviewButton();
        Assert.assertTrue(accountsOverviewPage.isPageOpened(),
                "After clicking 'Accounts Overview' button user has not been redirected to page with different title");

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

        AccountActivityPage accountActivityPage = accountsOverviewPage.clickSecondAccountNumber();
        Assert.assertTrue(accountActivityPage.isPageOpened(),
                "After clicking first account number in the table user has been redirected to the page with another title");
        waitUntilElementStatic(accountActivityPage, accountActivityPage.getTransactionTable(), Duration.ofSeconds(5));

        TransactionDetailsPage transactionDetailsPage = accountActivityPage.clickTableSecondTransaction();
        Assert.assertTrue(transactionDetailsPage.isPageOpened(),
                "After clicking Second transaction number in the table user has been redirected to the page with another title");

        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String formattedDate = localDate.format(formatter);

        String id = transactionDetailsPage.getId();
        FindTransactionsPage findTransactionsPage = transactionDetailsPage.openFindTransactionsPage();
        Assert.assertTrue(findTransactionsPage.isPageOpened(),
                "After clicking on 'Find Transactions' page user has been redirected to the page with another title");
        findTransactionsPage.clickSecondOption();
        findTransactionsPage.fillDateRange(formattedDate);
        findTransactionsPage.clickFindByDateButton();

        waitUntilElementStatic(transactionDetailsPage, transactionDetailsPage.getTransactionResultsTable(), Duration.ofSeconds(5));
        pause(2);
        transactionDetailsPage.clickSecondTransaction();
        String idAfterFind = transactionDetailsPage.getId();
        softAssert.assertEquals(id, idAfterFind,
                "Found transaction ID is different from the ID of found transaction");

        softAssert.assertAll();
    }

    @Test(description = "Check if user can find his transaction by amount")
    @TestCaseKey("ID-15")
    @MethodOwner(owner = "abuda")
    public void checkIfUserFindsTransactionByAmount()  {
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

        AccountsOverviewPage accountsOverviewPage = new AccountsOverviewPage(driver);
        accountsOverviewPage.clickAccountsOverviewButton();
        Assert.assertTrue(accountsOverviewPage.isPageOpened(),
                "After clicking 'Accounts Overview' button user has not been redirected to page with different title");

        TransferFundsPage transferFundsPage = accountsOverviewPage.openTransferFundsPage();
        Assert.assertTrue(transferFundsPage.isPageOpened(),
                "After opening 'Transfer funds' page user has not been redirected to 'Transfer funds' page");

        String amount = "20";
        transferFundsPage.fillAmount(amount);
        transferFundsPage.clickSecondAccountDropdownSecondOption();
        transferFundsPage.clickTransferButton();
        pause(3);
        softAssert.assertEquals(transferFundsPage.getTransferCompletedText(),Constants.PageTitles.TRANSFER_FUNDS_COMPLETED_PAGE_TITLE,
                "After transferring the funds user doesn't see the 'Transfer completed' screen");

        transferFundsPage.openAccountsOverviewPage();
        waitUntilElementStatic(accountsOverviewPage, accountsOverviewPage.getTotal(), Duration.ofSeconds(5));
        Assert.assertTrue(accountsOverviewPage.isPageOpened(),
                "After clicking new account number after opening an account user has been redirected to the page with another title");

        AccountActivityPage accountActivityPage = accountsOverviewPage.clickSecondAccountNumber();
        Assert.assertTrue(accountActivityPage.isPageOpened(),
                "After clicking first account number in the table user has been redirected to the page with another title");
        waitUntilElementStatic(accountActivityPage, accountActivityPage.getTransactionTable(), Duration.ofSeconds(5));

        TransactionDetailsPage transactionDetailsPage = accountActivityPage.clickTableSecondTransaction();
        Assert.assertTrue(transactionDetailsPage.isPageOpened(),
                "After clicking Second transaction number in the table user has been redirected to the page with another title");

        String id = transactionDetailsPage.getId();
        FindTransactionsPage findTransactionsPage = transactionDetailsPage.openFindTransactionsPage();
        Assert.assertTrue(findTransactionsPage.isPageOpened(),
                "After clicking on 'Find Transactions' page user has been redirected to the page with another title");
        findTransactionsPage.clickSecondOption();
        findTransactionsPage.fillAmount(amount);
        findTransactionsPage.clickFindByAmountButton();

        waitUntilElementStatic(transactionDetailsPage, transactionDetailsPage.getTransactionResultsTable(), Duration.ofSeconds(5));
        pause(2);
        transactionDetailsPage.clickFirstTransaction();
        String idAfterFind = transactionDetailsPage.getId();
        softAssert.assertEquals(id, idAfterFind,
                "Found transaction ID is different from the ID of found transaction");

        softAssert.assertAll();
    }
}


