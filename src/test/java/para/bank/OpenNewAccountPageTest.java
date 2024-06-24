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

public class OpenNewAccountPageTest extends AbstractParaBankPageTest {

    @Test(description = "Verify user can open 'Checking' type account")
    @TestCaseKey("ID-5")
    @MethodOwner(owner = "abuda")
    public void verifyUserCanOpenCheckingAccount() {
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
                "After clicking 'Open new Bank Account' button page is not opened");

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
        String accountType = "CHECKING";
        softAssert.assertTrue(accountActivityPage.isAccountAnExpectedType(accountType),
                "After clicking the account number, account is not 'Checking' type");
        softAssert.assertEquals(dropdownAccountNumber, openedAccountNumber,
                "Account number from dropdown doesn't match the number of opened account");

        AccountsOverviewPage accountsOverviewPage = new AccountsOverviewPage(driver);
        accountsOverviewPage.clickAccountsOverviewButton();
        Assert.assertTrue(accountsOverviewPage.isPageOpened(),
                "After clicking 'Accounts Overview' button page isn't opened");

        String firstAccountBalance = accountsOverviewPage.getFirstAccountBalance();
        String secondAccountBalance = accountsOverviewPage.getSecondAccountBalance();
        String firstAccountBalanceExpected = "$49950.00";
        String secondAccountBalanceExpected  = "$50.00";

        softAssert.assertEquals(firstAccountBalance, firstAccountBalanceExpected,
                "Account balance for first account after opening new account is wrong");
        softAssert.assertEquals(secondAccountBalance, secondAccountBalanceExpected,
                "Account balance for second account after opening new account is wrong");

        softAssert.assertAll();
    }

    @Test(description = "Verify user can open 'Savings' type account")
    @TestCaseKey("ID-6")
    @MethodOwner(owner = "abuda")
    public void verifyUserCanOpenSavingsAccount() {
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

        openNewBankAccountPage.pickSavingsTypeAccount();
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
        String accountType = "SAVINGS";
        softAssert.assertTrue(accountActivityPage.isAccountAnExpectedType(accountType),
                "After clicking in the account number, account is not 'Checking' type");
        softAssert.assertEquals(dropdownAccountNumber, openedAccountNumber,
                "Account number from dropdown doesn't match the number of opened account ");

        AccountsOverviewPage accountsOverviewPage = new AccountsOverviewPage(driver);
        accountsOverviewPage.clickAccountsOverviewButton();
        Assert.assertTrue(accountsOverviewPage.isPageOpened(),
                "After clicking 'Accounts Overview' button page isn't opened");

        String firstAccountBalance = accountsOverviewPage.getFirstAccountBalance();
        String secondAccountBalance = accountsOverviewPage.getSecondAccountBalance();
        softAssert.assertEquals(firstAccountBalance, "$49950.00",
                "Account balance after opening new account is wrong");
        softAssert.assertEquals(secondAccountBalance, "$50.00",
                "Account balance for opened account is wrong");

        softAssert.assertAll();
    }
}
