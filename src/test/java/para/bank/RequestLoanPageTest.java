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

public class RequestLoanPageTest extends AbstractParaBankPageTest {

    @Test(description = "Check if user is able to pay less than 10% for down payment")
    @TestCaseKey("ID-10")
    @MethodOwner(owner = "abuda")
    public void verifyUserPaysLoanLessThanDownPaymentAllows() {
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
        String balance = accountsOverviewPage.getFirstAccountBalance();
        String amount = balance.replace("$","").replace(".00","");

        RequestLoanPage requestLoanPage = accountsOverviewPage.openRequestLoanPage();
        Assert.assertTrue(requestLoanPage.isPageOpened(),
                "After opening 'Request Loan' pager user has been redirected to different page");

        requestLoanPage.fillLoanAmount(amount);
        requestLoanPage.fillDownPayment(String.format("%.0f", Double.parseDouble(amount) * 0.09));
        requestLoanPage.clickSubmitButton();
        waitUntilElementStatic(requestLoanPage, requestLoanPage.getTable(), Duration.ofSeconds(5));
        softAssert.assertEquals(requestLoanPage.getErrorMessage(), "We cannot grant a loan in that amount with the given down payment.",
                "After Requesting a loan user has been redirected to the page without an error");
        softAssert.assertEquals(requestLoanPage.getStatus(), "Denied",
                "After Requesting a loan user has been redirected to the page without a proper status");
    }

    @Test(description = "Check if user can pay more down payment than his balance allows for")
    @TestCaseKey("ID-11")
    @MethodOwner(owner = "abuda")
    public void verifyUserPaysMoreDownPaymentThanBalance() {
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
        String balance = accountsOverviewPage.getFirstAccountBalance();
        String amount = balance.replace("$","").replace(".00","");

        RequestLoanPage requestLoanPage = accountsOverviewPage.openRequestLoanPage();
        Assert.assertTrue(requestLoanPage.isPageOpened(),
                "After opening 'Request Loan' pager user has been redirected to different page");

        requestLoanPage.fillLoanAmount(amount);
        requestLoanPage.fillDownPayment(String.format("%.0f", Double.parseDouble(amount) * 1.1));
        requestLoanPage.clickSubmitButton();
        waitUntilElementStatic(requestLoanPage, requestLoanPage.getTable(), Duration.ofSeconds(5));
        softAssert.assertEquals(requestLoanPage.getErrorMessage(), "You do not have sufficient funds for the given down payment.",
                "After Requesting a loan user has been redirected to the page without an error");
        softAssert.assertEquals(requestLoanPage.getStatus(), "Denied",
                "After Requesting a loan user has been redirected to the page without a proper status");
    }

    @Test(description = "Check if user can take a loan")
    @TestCaseKey("ID-12")
    @MethodOwner(owner = "abuda")
    public void verifyUserTakesALoan() {
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
        String balance = accountsOverviewPage.getFirstAccountBalance();
        String amount = balance.replace("$","").replace(".00","");

        RequestLoanPage requestLoanPage = accountsOverviewPage.openRequestLoanPage();
        Assert.assertTrue(requestLoanPage.isPageOpened(),
                "After opening 'Request Loan' pager user has been redirected to different page");

        requestLoanPage.fillLoanAmount(String.format("%.0f", Double.parseDouble(amount) * 0.5));
        requestLoanPage.fillDownPayment(String.format("%.0f", Double.parseDouble(amount) * 0.05));
        requestLoanPage.clickSubmitButton();
        waitUntilElementStatic(requestLoanPage, requestLoanPage.getTable(), Duration.ofSeconds(5));
        softAssert.assertEquals(requestLoanPage.getSuccessMessage(), "Congratulations, your loan has been approved.",
                "After Requesting a loan user has been redirected to the page without another message");
        softAssert.assertEquals(requestLoanPage.getStatus(), "Approved",
                "After Requesting a loan user has been redirected to the page without a proper status");

        pause(2);
        String newAccNumber = requestLoanPage.getNewAccountNumber();
        AccountActivityPage accountActivityPage = requestLoanPage.clickNewAccNumber();
        Assert.assertTrue(accountActivityPage.isPageOpened(),
                "After clicking on New Account number user has been redirected to different page than Account Activity");
        waitUntilElementStatic(accountActivityPage, accountActivityPage.getNoTransactionsFoundText(), Duration.ofSeconds(5));
        String openedAccountNumber = accountActivityPage.getAccountNumber();
        String accountType = "LOAN";
        softAssert.assertTrue(accountActivityPage.isAccountAnExpectedType(accountType),
                "After clicking the account number, account is not +"+accountType+" type");
        softAssert.assertEquals(newAccNumber, openedAccountNumber,
                "Loan account number doesn't match the number of opened account");
    }
}
