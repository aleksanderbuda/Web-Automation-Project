package para.bank.cucumber.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import para.bank.AbstractParaBankPageTest;
import para.bank.auth.CreateAccountPage;
import para.bank.constants.Constants;
import para.bank.pages.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FindTransactionsStepDefinitions extends AbstractParaBankPageTest {

    WebDriver driver = getDriver();
    CreateAccountPage createAccountPage = new CreateAccountPage(driver);
    AccountsOverviewPage accountsOverviewPage = new AccountsOverviewPage(driver);
    LandingPage landingPage = new LandingPage(driver);
    OpenNewBankAccountPage openNewBankAccountPage = new OpenNewBankAccountPage(driver);
    TransferFundsPage transferFundsPage = new TransferFundsPage(driver);
    AccountActivityPage accountActivityPage = new AccountActivityPage(driver);
    TransactionDetailsPage transactionDetailsPage = new TransactionDetailsPage(driver);
    FindTransactionsPage findTransactionsPage = new FindTransactionsPage(driver);
    String id;

    @Given("the user is on the Landing Page")
    public void theUserIsOnLandingPage() {
        landingPage.open();
    }

    @When("the user registers a new account")
    public void theUserRegistersNewAccount() {
        createAccountPage = landingPage.clickRegisterAccountButton();
        createAccountPage.createAnAccount();
        Assert.assertEquals(createAccountPage.getWelcomeMessage(), Constants.PageTitles.WELCOME_MESSAGE_TITLE);
    }

    @When("the user opens a new bank account")
    public void theUserOpensNewBankAccount() {
        openNewBankAccountPage = new OpenNewBankAccountPage(driver);
        openNewBankAccountPage.clickOpenNewAccountPanelButton();
        openNewBankAccountPage.clickOpenAccountButton();
        Assert.assertTrue(openNewBankAccountPage.isNewAccountNumberPresent());
    }

    @When("the user transfers funds")
    public void theUserTransfersFunds() {
        accountsOverviewPage = new AccountsOverviewPage(driver);
        accountsOverviewPage.clickAccountsOverviewButton();

        transferFundsPage = accountsOverviewPage.openTransferFundsPage();
        transferFundsPage.fillAmount("20");
        transferFundsPage.clickSecondAccountDropdownSecondOption();
        transferFundsPage.clickTransferButton();
        Assert.assertEquals(transferFundsPage.getTransferCompletedText(), Constants.PageTitles.TRANSFER_FUNDS_COMPLETED_PAGE_TITLE);
    }

    @When("the user navigates to Account Activity page")
    public void theUserNavigatesToAccountActivityPage() {
        accountActivityPage = accountsOverviewPage.clickSecondAccountNumber();
        transactionDetailsPage = accountActivityPage.clickTableSecondTransaction();
    }

    @When("the user selects a transaction")
    public void theUserSelectsTransaction() {
        id = transactionDetailsPage.getId();
    }

    @When("the user opens the Find Transactions page")
    public void theUserOpensTheFindTransactionsPage() {
        findTransactionsPage = transactionDetailsPage.openFindTransactionsPage();
        Assert.assertTrue(findTransactionsPage.isPageOpened());
    }

    @When("the user searches for the transaction by ID")
    public void theUserSearchesForTransactionById() {
        findTransactionsPage.clickSecondOption();
        findTransactionsPage.fillId(id);
        findTransactionsPage.clickFindByIdButton();
    }

    @When("the user searches for the transaction by date")
    public void theUserSearchesForTransactionByDate() {
        LocalDate localDate = LocalDate.now();
        String formattedDate = localDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));

        findTransactionsPage.clickSecondOption();
        findTransactionsPage.fillDateRange(formattedDate);
        findTransactionsPage.clickFindByDateButton();
    }

    @When("the user searches for the transaction by amount")
    public void theUserSearchesForTransactionByAmount() {
        findTransactionsPage.clickSecondOption();
        findTransactionsPage.fillAmount("20");
        findTransactionsPage.clickFindByAmountButton();
    }

    @Then("the transaction should be found by ID")
    public void theTransactionShouldBeFoundById() {
        transactionDetailsPage.clickSecondTransaction();
        String idAfterFind = transactionDetailsPage.getId();
        Assert.assertEquals(id, idAfterFind);
    }

    @Then("the transaction should be found by date")
    public void theTransactionShouldBeFoundByDate() {
        transactionDetailsPage.clickSecondTransaction();
        String idAfterFind = transactionDetailsPage.getId();
        Assert.assertEquals(id, idAfterFind);
    }

    @Then("the transaction should be found by amount")
    public void theTransactionShouldBeFoundByAmount() {
        transactionDetailsPage.clickFirstTransaction();
        String idAfterFind = transactionDetailsPage.getId();
        Assert.assertEquals(id, idAfterFind);
    }
}


