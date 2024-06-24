package para.bank;

import com.zebrunner.agent.core.annotation.TestCaseKey;
import com.zebrunner.carina.core.registrar.ownership.MethodOwner;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import para.bank.auth.CreateAccountPage;
import para.bank.components.BillPayForm;
import para.bank.constants.Constants;
import para.bank.pages.*;

import java.time.Duration;

public class BillPayPageTest extends AbstractParaBankPageTest {

    @Test(description = "Verify user can pay bills from a single account")
    @TestCaseKey("ID-9")
    @MethodOwner(owner = "abuda")
    public void verifyUserIsAbleToPayBills()  {
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

        AccountsOverviewPage accountsOverviewPage = new AccountsOverviewPage(driver);
        accountsOverviewPage.clickAccountsOverviewButton();
        Assert.assertTrue(accountsOverviewPage.isPageOpened(),
                "After opening 'Accounts Overview' page has not been redirected to 'Accounts Overview' page");
        String accountNumber = accountsOverviewPage.getFirstAccountNumber();

        BillPayPage billPayPage = accountsOverviewPage.openBillPayPage();
        Assert.assertTrue(billPayPage.isPageOpened(),
                "After opening 'Bill Pay' page has not been redirected to 'Bill Pay' page");

        String fromAccountNumber = billPayPage.getAccountNumber();
        softAssert.assertEquals(accountNumber, fromAccountNumber,
                "Selected account is not the same as on the account on 'Account Overview' page");

        String payeeName = createRandomUsername();
        String address = createRandomAddress();
        String phone = createRandomPhoneNumber();
        String city = StringUtils.capitalize(RandomStringUtils.randomAlphabetic(7).toLowerCase());
        String state = RandomStringUtils.randomAlphabetic(2).toUpperCase();
        String zipCode = RandomStringUtils.randomNumeric(6);
        String accNumber = RandomStringUtils.randomNumeric(6);
        String amount = RandomStringUtils.randomNumeric(2);

        billPayPage.fillPayeeName(payeeName);
        billPayPage.fillAddress(address);
        billPayPage.fillCity(city);
        billPayPage.fillState(state);
        billPayPage.fillZipCode(zipCode);
        billPayPage.fillPhoneNumber(phone);
        billPayPage.accountNum(accNumber);
        billPayPage.verifyAccountNum(accNumber);
        billPayPage.fillAmount(amount);

        billPayPage.clickSendPaymentButton();
        softAssert.assertEquals(billPayPage.getTitleText(), Constants.PageTitles.BILL_PAYMENT_COMPLETE_PAGE_TITLE,
                "After clicking 'Send Payment' button user has been redirected to the page with another title");

        billPayPage.openAccountsOverviewPage();
        Assert.assertTrue(accountsOverviewPage.isPageOpened(),
                "After opening 'Accounts Overview' page user has been redirected to the page with another title");

        AccountActivityPage accountActivityPage = accountsOverviewPage.clickFirstAccountNumber();
        softAssert.assertTrue(accountActivityPage.isTransactionTablePresent(),
                "Transaction Table is not present");

        waitUntilElementStatic(accountActivityPage, accountActivityPage.getTransactionTable(), Duration.ofSeconds(5));
        accountActivityPage.clickTableFirstTransaction();
        softAssert.assertEquals(accountActivityPage.getTitleText(), Constants.PageTitles.TRANSACTION_DETAILS_PAGE_TITLE,
                "After opening 'Transaction Details' page user has been redirected to the page with another title");

        TransactionDetailsPage transactionDetailsPage = new TransactionDetailsPage(driver);
        String description = transactionDetailsPage.getDescription();
        String transferredAmount = transactionDetailsPage.getTransferredAmount();

        softAssert.assertEquals(description, "Bill Payment to " + payeeName,
                "After opening 'Transaction Details' page Payee name is not correct");
        softAssert.assertEquals(transferredAmount, "$" + amount +",00",
                "After opening 'Transaction Details' page amount sent is not correct");

        softAssert.assertAll();

    }
}
