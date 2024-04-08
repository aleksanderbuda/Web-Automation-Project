import org.testng.Assert;
import org.testng.annotations.Test;
import config.Log;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;


import static org.testng.Reporter.getCurrentTestResult;

public class ParaBankTests extends BaseTest {

    @Epic("ParaBank Account Management")
    @Feature("New Account Creation")
    @Test(priority = 1, description = "Validate registration form fields")
    public void registrationFieldValidationTest() throws InterruptedException {
        registrationFieldValidationSteps();
    }

    @Step("Performing registration field validation")
    public void registrationFieldValidationSteps() throws InterruptedException {
        String testCaseName = getCurrentTestName(getCurrentTestResult());
        Log.startTestCase(testCaseName);
        UserSignupFormValidation registrationFieldValidation = new UserSignupFormValidation(driver);
        registrationFieldValidation.validateSignupForm();
        Log.endTestCase(testCaseName);
    }

    @Test(priority = 2, description = "Login to ParaBank with valid credentials")
    public void userLogInTest() throws InterruptedException {
        userLogInSteps();
    }

    @Step("Performing user login")
    public void userLogInSteps() throws InterruptedException {
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

    @Test(priority = 3, description = "Open a new bank account")
    public void userOpensNewBankAccountTest() throws InterruptedException {
        userOpensNewBankAccountSteps();
    }

    @Step("Opening a new bank account")
    public void userOpensNewBankAccountSteps() throws InterruptedException {
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

    @Test(priority = 4, description = "Transfer funds between accounts")
    public void userTransfersTheFundsTest() throws InterruptedException {
        userTransfersTheFundsSteps();
    }

    @Step("Transferring funds between accounts")
    public void userTransfersTheFundsSteps() throws InterruptedException {
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

    @Test(priority = 5, description = "Request a loan")
    public void userRequestsLoanTest() throws InterruptedException {
        userRequestsLoanSteps();
    }

    @Step("Requesting a loan")
    public void userRequestsLoanSteps() throws InterruptedException {
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

    @Test(priority = 6, description = "Pay bills")
    public void userPayBillsTest() throws InterruptedException {
        userPayBillsSteps();
    }

    @Step("Paying bills")
    public void userPayBillsSteps() throws InterruptedException {
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

    @Test(priority = 7, description = "Find a transaction")
    public void userFindsTransactionTest() throws InterruptedException {
        userFindsTransactionSteps();
    }

    @Step("Finding a transaction")
    public void userFindsTransactionSteps() throws InterruptedException {
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
