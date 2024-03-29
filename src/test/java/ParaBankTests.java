import config.Drivers;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class ParaBankTests extends BaseTest {

    @BeforeSuite
    public void setBrowserType() {
        Drivers.browserType = "chrome";
        browserType = Drivers.browserType;
    }
    @Test(priority = 1)
    public void registrationFieldValidationTest() throws InterruptedException {
        RegistrationFieldValidation registrationFieldValidation = new RegistrationFieldValidation(driver);
        registrationFieldValidation.checkRegistrationErrorFields();
    }
    @Test(priority = 2)
    public void userLogInTest() {
        ParaBankLogIn paraBankLogIn = new ParaBankLogIn(driver);
        ParaBankHomePageRegistration paraBankHomePageRegistration = new ParaBankHomePageRegistration(driver);
        paraBankHomePageRegistration.registerUser();
        paraBankLogIn.userLogsIn();
        Assert.assertTrue(paraBankLogIn.isUserLoggedIn());
        log.info("User login successful");
    }
    @Test(priority = 3)
    public void userOpensNewBankAccountTest() throws InterruptedException {
        OpenNewBankAccount openNewBankAccount = new OpenNewBankAccount(driver);
        ParaBankHomePageRegistration paraBankHomePageRegistration = new ParaBankHomePageRegistration(driver);
        ParaBankLogIn paraBankLogIn = new ParaBankLogIn(driver);
        paraBankHomePageRegistration.registerUser();
        paraBankLogIn.userLogsIn();
        openNewBankAccount.userOpensNewBankAccount();

        String expectedFirstBalance = "$49900.00";
        Assert.assertEquals(openNewBankAccount.getFirstBalanceText(), expectedFirstBalance, "Incorrect first account balance");
        String expectedSecondBalance = "$100.00";
        Assert.assertEquals(openNewBankAccount.getSecondBalanceText(), expectedSecondBalance, "Incorrect second account balance");
    }
    @Test(priority = 4)
    public void userTransfersTheFundsTest() throws InterruptedException {
        OpenNewBankAccount openNewBankAccount = new OpenNewBankAccount(driver);
        ParaBankHomePageRegistration paraBankHomePageRegistration = new ParaBankHomePageRegistration(driver);
        ParaBankLogIn paraBankLogIn = new ParaBankLogIn(driver);
        TransferFunds transferFunds = new TransferFunds(driver);
        paraBankHomePageRegistration.registerUser();
        paraBankLogIn.userLogsIn();
        openNewBankAccount.userOpensNewBankAccount();
        transferFunds.userTransfersTheFunds();
        Assert.assertTrue(transferFunds.isBalanceCorrectForFirstRow());
        Assert.assertTrue(transferFunds.isBalanceCorrectForSecondRow());
    }
    @Test(priority = 5)
    public void userRequestsLoanTest() throws InterruptedException {
        ParaBankHomePageRegistration paraBankHomePageRegistration = new ParaBankHomePageRegistration(driver);
        ParaBankLogIn paraBankLogIn = new ParaBankLogIn(driver);
        RequestLoan requestLoan = new RequestLoan(driver);
        paraBankHomePageRegistration.registerUser();
        paraBankLogIn.userLogsIn();
        requestLoan.userRequestsLoan();
    }
    @Test(priority = 6)
    public void userPayBillsTest() throws InterruptedException {
        ParaBankHomePageRegistration paraBankHomePageRegistration = new ParaBankHomePageRegistration(driver);
        ParaBankLogIn paraBankLogIn = new ParaBankLogIn(driver);
        BillPayment billPayment = new BillPayment(driver);
        OpenNewBankAccount openNewBankAccount = new OpenNewBankAccount(driver);
        paraBankHomePageRegistration.registerUser();
        paraBankLogIn.userLogsIn();
        openNewBankAccount.userOpensNewBankAccount();
        billPayment.userPayBills();
        Assert.assertTrue(billPayment.validatePaymentAmount());
        Assert.assertTrue(billPayment.validatePayee());
    }
    @Test(priority = 7)
    public void userFindsTransactionTest() throws InterruptedException {
        ParaBankHomePageRegistration paraBankHomePageRegistration = new ParaBankHomePageRegistration(driver);
        ParaBankLogIn paraBankLogIn = new ParaBankLogIn(driver);
        OpenNewBankAccount openNewBankAccount = new OpenNewBankAccount(driver);
        FindTransaction findTransaction = new FindTransaction(driver);
        paraBankHomePageRegistration.registerUser();
        paraBankLogIn.userLogsIn();
        openNewBankAccount.userOpensNewBankAccount();
        findTransaction.userFindsTransaction();
        Assert.assertEquals(findTransaction.transactionID, findTransaction.foundTransactionID, "Transaction IDs don't match.");
        Assert.assertEquals(findTransaction.dateOfTransaction, findTransaction.foundTransactionDate, "Transaction dates don't match.");
        Assert.assertEquals(findTransaction.foundTransactionAmount, findTransaction.amountPaid, "Amounts don't match");
    }
}


//zrobić automatycznie żeby się docker/jenkins odpalał isRemote = true
//stworzyć testng.xml file żeby odpalać run automatycznie(?)
//porozdzielać na foldery lepiej i powkładać odpowiednie rzeczy w odpowiednie miejsca
//dodać driver do configu? co z log4j2.xml?
//jak odpalać automatycznie selenium grid server??


//dodać klase TestSuite/testRun jako enkapsulacje klasy ParaBankTests?
//zmienić Thread sleep na wait > to nie wiem OCB nie moge zrobić

//-cucumber?