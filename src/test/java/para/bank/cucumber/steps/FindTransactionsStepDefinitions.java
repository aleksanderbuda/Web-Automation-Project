package para.bank.cucumber.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import para.bank.AbstractParaBankPageTest;
import para.bank.constants.Constants;
import para.bank.pages.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FindTransactionsStepDefinitions extends AbstractParaBankPageTest {

    WebDriver driver = getDriver();
    AccountsOverviewPage accountsOverviewPage = new AccountsOverviewPage(driver);
    OpenNewBankAccountPage openNewBankAccountPage = new OpenNewBankAccountPage(driver);
    TransferFundsPage transferFundsPage = new TransferFundsPage(driver);
    AccountActivityPage accountActivityPage = new AccountActivityPage(driver);
    TransactionDetailsPage transactionDetailsPage = new TransactionDetailsPage(driver);
    FindTransactionsPage findTransactionsPage = new FindTransactionsPage(driver);
    String id;


    @And("I navigate to New Bank account page")
    public void theUserOpensNewBankAccountPage() {
        openNewBankAccountPage.clickOpenNewAccountPanelButton();
    }

    @And("I open new bank account")
    public void openNewBankAccount() {
        openNewBankAccountPage.clickOpenAccountButton();
        Assert.assertTrue(openNewBankAccountPage.isNewAccountNumberPresent());
    }

    @And("I transfer funds")
    public void transferFunds() {
        accountsOverviewPage.clickAccountsOverviewButton();
        accountsOverviewPage.openTransferFundsPage();
        transferFundsPage.fillAmount("20");
        transferFundsPage.clickSecondAccountDropdownSecondOption();
        transferFundsPage.clickTransferButton();
        Assert.assertEquals(transferFundsPage.getTransferCompletedText(), Constants.PageTitles.TRANSFER_FUNDS_COMPLETED_PAGE_TITLE);
    }

    @And("I navigate to Account Activity page")
    public void navigateToAccountActivityPage() {
        accountsOverviewPage.clickSecondAccountNumber();
        transactionDetailsPage = accountActivityPage.clickTableSecondTransaction();
    }

    @And("I select a transaction")
    public void selectTransaction() {
        id = transactionDetailsPage.getId();
    }

    @When("I navigate to Find Transactions page")
    public void openFindTransactionsPage() {
       transactionDetailsPage.openFindTransactionsPage();
        Assert.assertTrue(findTransactionsPage.isPageOpened());
    }

    @When("I search for the transaction by ID")
    public void searchForTransactionById() {
        findTransactionsPage.clickSecondOption();
        findTransactionsPage.fillId(id);
        findTransactionsPage.clickFindByIdButton();
    }

    @When("I search for transaction by date")
    public void searchForTransactionByDate() {
        LocalDate localDate = LocalDate.now();
        String formattedDate = localDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));

        findTransactionsPage.clickSecondOption();
        findTransactionsPage.fillDateRange(formattedDate);
        findTransactionsPage.clickFindByDateButton();
    }

    @And("I search for transaction by amount")
    public void searchForTransactionByAmount() {
        findTransactionsPage.clickSecondOption();
        findTransactionsPage.fillAmount("20");
        findTransactionsPage.clickFindByAmountButton();
    }

    @Then("I see confirmation for transaction found by ID")
    public void checkTransactionFoundById() {
        transactionDetailsPage.clickSecondTransaction();
        String idAfterFind = transactionDetailsPage.getId();
        Assert.assertEquals(id, idAfterFind);
    }

    @Then("I see confirmation for transaction found by date")
    public void checkTransactionFoundByDate() {
        transactionDetailsPage.clickSecondTransaction();
        String idAfterFind = transactionDetailsPage.getId();
        Assert.assertEquals(id, idAfterFind);
    }

    @Then("I see confirmation for transaction found by amount")
    public void checkTransactionFoundByAmount() {
        transactionDetailsPage.clickFirstTransaction();
        String idAfterFind = transactionDetailsPage.getId();
        Assert.assertEquals(id, idAfterFind);
    }
}


