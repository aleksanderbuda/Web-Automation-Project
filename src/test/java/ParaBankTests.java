import config.Drivers;
import org.testng.Assert;
import static org.testng.Reporter.getCurrentTestResult;

import org.testng.ITestResult;
import org.testng.annotations.Test;
import config.Log;

public class ParaBankTests extends BaseTest {

    @Test(priority = 1)
    public void registrationFieldValidationTest() throws InterruptedException {
        String testCaseName = getCurrentTestName(getCurrentTestResult());
        Log.startTestCase(testCaseName);
        UserSignupFormValidation registrationFieldValidation = new UserSignupFormValidation(driver);
        registrationFieldValidation.validateSignupForm();
        Log.endTestCase(testCaseName);
    }
    @Test(priority = 2)
    public void userLogInTest() throws InterruptedException {
        String testCaseName = getCurrentTestName(getCurrentTestResult());
        Log.startTestCase(testCaseName);
        UserLoginService paraBankLogIn = new UserLoginService(driver);
        HomePageUserRegistration paraBankHomePageRegistration = new HomePageUserRegistration(driver);
        paraBankHomePageRegistration.registerNewUser();
        paraBankLogIn.loginUser();
        Assert.assertTrue(paraBankLogIn.isUserLoggedIn());
        log.info("User login successful");
        Log.endTestCase(testCaseName);
    }
    @Test(priority = 3)
    public void userOpensNewBankAccountTest() throws InterruptedException {
        String testCaseName = getCurrentTestName(getCurrentTestResult());
        Log.startTestCase(testCaseName);
        NewBankAccountOpener openNewBankAccount = new NewBankAccountOpener(driver);
        HomePageUserRegistration paraBankHomePageRegistration = new HomePageUserRegistration(driver);
        UserLoginService paraBankLogIn = new UserLoginService(driver);
        paraBankHomePageRegistration.registerNewUser();
        paraBankLogIn.loginUser();
        openNewBankAccount.openNewAccount();

        String expectedFirstBalance = "$49900.00";
        Assert.assertEquals(openNewBankAccount.getFirstBalanceText(), expectedFirstBalance, "Incorrect first account balance");
        String expectedSecondBalance = "$100.00";
        Assert.assertEquals(openNewBankAccount.getSecondBalanceText(), expectedSecondBalance, "Incorrect second account balance");
        Log.endTestCase(testCaseName);
    }
    @Test(priority = 4)
    public void userTransfersTheFundsTest() throws InterruptedException {
        String testCaseName = getCurrentTestName(getCurrentTestResult());
        Log.startTestCase(testCaseName);
        NewBankAccountOpener openNewBankAccount = new NewBankAccountOpener(driver);
        HomePageUserRegistration paraBankHomePageRegistration = new HomePageUserRegistration(driver);
        UserLoginService paraBankLogIn = new UserLoginService(driver);
        FundsTransferService transferFunds = new FundsTransferService(driver);
        paraBankHomePageRegistration.registerNewUser();
        paraBankLogIn.loginUser();
        openNewBankAccount.openNewAccount();
        transferFunds.transferFunds();
        Assert.assertTrue(transferFunds.isBalanceCorrectForFirstRow());
        Assert.assertTrue(transferFunds.isBalanceCorrectForSecondRow());
        Log.endTestCase(testCaseName);
    }
    @Test(priority = 5)
    public void userRequestsLoanTest() throws InterruptedException {
        String testCaseName = getCurrentTestName(getCurrentTestResult());
        Log.startTestCase(testCaseName);
        HomePageUserRegistration paraBankHomePageRegistration = new HomePageUserRegistration(driver);
        UserLoginService paraBankLogIn = new UserLoginService(driver);
        LoanRequestService requestLoan = new LoanRequestService(driver);
        paraBankHomePageRegistration.registerNewUser();
        paraBankLogIn.loginUser();
        requestLoan.processLoanRequest();
        Log.endTestCase(testCaseName);
    }
    @Test(priority = 6)
    public void userPayBillsTest() throws InterruptedException {
        String testCaseName = getCurrentTestName(getCurrentTestResult());
        Log.startTestCase(testCaseName);
        HomePageUserRegistration paraBankHomePageRegistration = new HomePageUserRegistration(driver);
        UserLoginService paraBankLogIn = new UserLoginService(driver);
        BillPayProcessor billPayment = new BillPayProcessor(driver);
        NewBankAccountOpener openNewBankAccount = new NewBankAccountOpener(driver);
        paraBankHomePageRegistration.registerNewUser();
        paraBankLogIn.loginUser();
        openNewBankAccount.openNewAccount();
        billPayment.processBillPayment();
        Assert.assertTrue(billPayment.validatePaymentAmount());
        Assert.assertTrue(billPayment.validatePayee());
        Log.endTestCase(testCaseName);
    }
    @Test(priority = 7)
    public void userFindsTransactionTest() throws InterruptedException {
        String testCaseName = getCurrentTestName(getCurrentTestResult());
        Log.startTestCase(testCaseName);
        HomePageUserRegistration paraBankHomePageRegistration = new HomePageUserRegistration(driver);
        UserLoginService paraBankLogIn = new UserLoginService(driver);
        NewBankAccountOpener openNewBankAccount = new NewBankAccountOpener(driver);
        TransactionFinder findTransaction = new TransactionFinder(driver);
        paraBankHomePageRegistration.registerNewUser();
        paraBankLogIn.loginUser();
        openNewBankAccount.openNewAccount();
        findTransaction.findTransaction();
        Assert.assertEquals(findTransaction.transactionID, findTransaction.foundTransactionID, "Transaction IDs don't match.");
        Assert.assertEquals(findTransaction.dateOfTransaction, findTransaction.foundTransactionDate, "Transaction dates don't match.");
        Assert.assertEquals(findTransaction.foundTransactionAmount, findTransaction.amountPaid, "Amounts don't match");
        Log.endTestCase(testCaseName);
    }
}
//zmienić Thread sleep na wait > to nie wiem OCB nie moge zrobić
