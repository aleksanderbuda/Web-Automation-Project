package para.bank.cucumber.steps;

import io.cucumber.java.en.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import para.bank.AbstractParaBankPageTest;
import para.bank.auth.CreateAccountPage;
import para.bank.constants.Constants;
import para.bank.pages.*;

import java.time.Duration;

public class BillPayPageStepDefinitions extends AbstractParaBankPageTest {
    WebDriver driver = getDriver();
    SoftAssert softAssert = new SoftAssert();

    LandingPage landingPage = new LandingPage(driver);
    CreateAccountPage createAccountPage = new CreateAccountPage(driver);
    AccountsOverviewPage accountsOverviewPage = new AccountsOverviewPage(driver);
    BillPayPage billPayPage = new BillPayPage(driver);
    AccountActivityPage accountActivityPage = new AccountActivityPage(driver);
    TransactionDetailsPage transactionDetailsPage = new TransactionDetailsPage(driver);

    String accountNumber;
    String payeeName;
    String address;
    String phone;
    String city;
    String state;
    String zipCode;
    String accNumber;
    String amount;

    @Given("the user is on the landing page and registers a new account")
    public void theUserIsOnLandingPageAndRegistersNewAccount() {
        landingPage.open();
        createAccountPage = landingPage.clickRegisterAccountButton();
        Assert.assertTrue(createAccountPage.isPageOpened(),
                "After clicking 'Register' button 'Create account' page is not opened");

        createAccountPage.createAnAccount();
        softAssert.assertEquals(createAccountPage.getWelcomeMessage(), Constants.PageTitles.WELCOME_MESSAGE_TITLE,
                "After Registering an account user has been redirected to the page with another title");
    }

    @Given("the user navigates to the Accounts Overview page")
    public void theUserNavigatesToAccountsOverviewPage() {
        accountsOverviewPage = new AccountsOverviewPage(driver);
        accountsOverviewPage.clickAccountsOverviewButton();
        Assert.assertTrue(accountsOverviewPage.isPageOpened(),
                "After opening 'Accounts Overview' page has not been redirected to 'Accounts Overview' page");
        accountNumber = accountsOverviewPage.getFirstAccountNumber();
    }

    @When("the user navigates to the Bill Pay page")
    public void theUserNavigatesToBillPayPage() {
        billPayPage = accountsOverviewPage.openBillPayPage();
        Assert.assertTrue(billPayPage.isPageOpened(),
                "After opening 'Bill Pay' page has not been redirected to 'Bill Pay' page");
        String fromAccountNumber = billPayPage.getAccountNumber();
        softAssert.assertEquals(accountNumber, fromAccountNumber,
                "Selected account is not the same as on the account on 'Account Overview' page");
    }

    @And("the user fills in the Bill Pay details")
    public void theUserFillsTheBillPayDetails() {
        payeeName = createRandomUsername();
        address = createRandomAddress();
        phone = createRandomPhoneNumber();
        city = StringUtils.capitalize(RandomStringUtils.randomAlphabetic(7).toLowerCase());
        state = RandomStringUtils.randomAlphabetic(2).toUpperCase();
        zipCode = RandomStringUtils.randomNumeric(6);
        accNumber = RandomStringUtils.randomNumeric(6);
        amount = RandomStringUtils.randomNumeric(2);

        billPayPage.fillPayeeName(payeeName);
        billPayPage.fillAddress(address);
        billPayPage.fillCity(city);
        billPayPage.fillState(state);
        billPayPage.fillZipCode(zipCode);
        billPayPage.fillPhoneNumber(phone);
        billPayPage.accountNum(accNumber);
        billPayPage.verifyAccountNum(accNumber);
        billPayPage.fillAmount(amount);
    }

    @And("the user submits the Bill Payment")
    public void theUserSubmitsBillPayment() {
        billPayPage.clickSendPaymentButton();
        softAssert.assertEquals(billPayPage.getBillCompletedMessage(), Constants.PageTitles.BILL_PAYMENT_COMPLETE_PAGE_TITLE,
                "After clicking 'Send Payment' button user has been redirected to the page with another title");
    }

    @Then("the user verifies the transaction details")
    public void theUserVerifiesTransactionDetails() {
        billPayPage.openAccountsOverviewPage();
        Assert.assertTrue(accountsOverviewPage.isPageOpened(),
                "After opening 'Accounts Overview' page user has been redirected to the page with another title");

        accountActivityPage = accountsOverviewPage.clickFirstAccountNumber();
        softAssert.assertTrue(accountActivityPage.isTransactionTablePresent(),
                "Transaction Table is not present");

        waitUntilElementStatic(accountActivityPage, accountActivityPage.getTransactionTable(), Duration.ofSeconds(5));
        accountActivityPage.clickTableFirstTransaction();
        softAssert.assertEquals(accountActivityPage.getTitleText(), Constants.PageTitles.TRANSACTION_DETAILS_PAGE_TITLE,
                "After opening 'Transaction Details' page user has been redirected to the page with another title");

        transactionDetailsPage = new TransactionDetailsPage(driver);
        String description = transactionDetailsPage.getDescription();
        String transferredAmount = transactionDetailsPage.getTransferredAmount();

        softAssert.assertEquals(description, "Bill Payment to " + payeeName,
                "After opening 'Transaction Details' page Payee name is not correct");
        softAssert.assertEquals(transferredAmount, "$" + amount + ",00",
                "After opening 'Transaction Details' page amount sent is not correct");

        softAssert.assertAll();
    }
}
