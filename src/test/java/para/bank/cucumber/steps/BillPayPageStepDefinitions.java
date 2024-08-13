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
    String amount;
    String payeeName;


    @And("I navigate to Registration page")
    public void navigateToRegistrationPage() {
        landingPage.clickRegisterAccountButton();
        Assert.assertTrue(createAccountPage.isPageOpened(),
                "After clicking 'Register' button 'Create account' page is not opened");

        createAccountPage.createAnAccount();
        softAssert.assertEquals(createAccountPage.getWelcomeMessage(), Constants.PageTitles.WELCOME_MESSAGE_TITLE,
                "After Registering an account user has been redirected to the page with another title");
    }

    @And("I register new account")
    public void registerNewAccount() {
        createAccountPage.createAnAccount();
        softAssert.assertEquals(createAccountPage.getWelcomeMessage(), Constants.PageTitles.WELCOME_MESSAGE_TITLE,
                "After Registering an account user has been redirected to the page with another title");
    }

    @Given("I navigate to the Accounts Overview page")
    public void navigateToAccountsOverviewPage() {
        accountsOverviewPage.clickAccountsOverviewButton();
        Assert.assertTrue(accountsOverviewPage.isPageOpened(),
                "After opening 'Accounts Overview' page has not been redirected to 'Accounts Overview' page");
        accountNumber = accountsOverviewPage.getFirstAccountNumber();
    }

    @When("I navigate to Bill Pay Page")
    public void navigateToBillPayPage() {
        accountsOverviewPage.openBillPayPage();
        Assert.assertTrue(billPayPage.isPageOpened(),
                "After opening 'Bill Pay' page has not been redirected to 'Bill Pay' page");
        String fromAccountNumber = billPayPage.getAccountNumber();
        softAssert.assertEquals(accountNumber, fromAccountNumber,
                "Selected account is not the same as on the account on 'Account Overview' page");
    }

    @And("I fill random Bill details")
    public void fillsTheBillPayDetails() {
        String address;
        String phone;
        String city;
        String state;
        String zipCode;
        String accNumber;


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

    @And("I submit the Bill Payment")
    public void submitBillPayment() {
        billPayPage.clickSendPaymentButton();
    }

    @And("I should see a bill payment confirmation message")
    public void checkConfirmationMessage() {
        softAssert.assertEquals(billPayPage.getBillCompletedMessage(), Constants.PageTitles.BILL_PAYMENT_COMPLETE_PAGE_TITLE,
                "After clicking 'Send Payment' button user has been redirected to the page with another title");
    }

    @And("I navigate back to the Accounts Overview page")
    public void goBackToAccOverview() {
        billPayPage.openAccountsOverviewPage();
        Assert.assertTrue(accountsOverviewPage.isPageOpened(),
                "After opening 'Accounts Overview' page user has been redirected to the page with another title");
    }
        @Then("I verify the transaction details")
        public void theUserVerifiesTransactionDetails() {
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

