package para.bank.cucumber.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import para.bank.AbstractParaBankPageTest;
import para.bank.pages.*;

import java.time.Duration;

public class RequestLoanPageStepDefinition extends AbstractParaBankPageTest {

    WebDriver driver = getDriver();
    AccountsOverviewPage accountsOverviewPage = new AccountsOverviewPage(driver);
    AccountActivityPage accountActivityPage = new AccountActivityPage(driver);
    RequestLoanPage requestLoanPage = new RequestLoanPage(driver);
    String amount;

    @And("I navigate to Request Loan page")
    public void openRequestLoanPage() {
        accountsOverviewPage.clickAccountsOverviewButton();
        Assert.assertTrue(accountsOverviewPage.isPageOpened(),
                "After opening 'Accounts Overview' page has not been redirected to 'Accounts Overview' page");

        String balance = accountsOverviewPage.getFirstAccountBalance();
        amount = balance.replace("$", "").replace(".00", "");

        requestLoanPage = accountsOverviewPage.openRequestLoanPage();
        Assert.assertTrue(requestLoanPage.isPageOpened(),
                "After opening 'Request Loan' page user has been redirected to different page");
    }

    @When("I request a loan with a down payment less than 10% of the loan amount")
    public void requestLoanWithDownPaymentLessThan10Percent() {
        requestLoanPage.fillLoanAmount(amount);
        requestLoanPage.fillDownPayment(String.format("%.0f", Double.parseDouble(amount) * 0.09));
        requestLoanPage.clickSubmitButton();
        waitUntilElementStatic(requestLoanPage, requestLoanPage.getTable(), Duration.ofSeconds(5));
    }

    @When("I request a loan with a down payment more than balance allows")
    public void requestLoanWithDownPaymentMoreThanBalanceAllows() {
        requestLoanPage.fillLoanAmount(amount);
        requestLoanPage.fillDownPayment(String.format("%.0f", Double.parseDouble(amount) * 1.1));
        requestLoanPage.clickSubmitButton();
        waitUntilElementStatic(requestLoanPage, requestLoanPage.getTable(), Duration.ofSeconds(5));
    }

    @When("I request a loan with a valid down payment")
    public void requestLoanWithValidDownPayment() {
        requestLoanPage.fillLoanAmount(String.format("%.0f", Double.parseDouble(amount) * 0.5));
        requestLoanPage.fillDownPayment(String.format("%.0f", Double.parseDouble(amount) * 0.05));
        requestLoanPage.clickSubmitButton();
        waitUntilElementStatic(requestLoanPage, requestLoanPage.getTable(), Duration.ofSeconds(5));
    }

    @Then("I see an error message and the loan status is Denied")
    public void verifyErrorMessageAndLoanStatusDenied() {
        Assert.assertEquals(requestLoanPage.getErrorMessage(), "We cannot grant a loan in that amount with the given down payment.",
                "After Requesting a loan user has been redirected to the page without an error");
        Assert.assertEquals(requestLoanPage.getStatus(), "Denied",
                "After Requesting a loan user has been redirected to the page without a proper status");
    }

    @And("I see a success message and the loan is Approved")
    public void verifySuccessMessageAndLoanStatusApproved() {
        Assert.assertEquals(requestLoanPage.getSuccessMessage(), "Congratulations, your loan has been approved.",
                "After Requesting a loan user has been redirected to the page without another message");
        Assert.assertEquals(requestLoanPage.getStatus(), "Approved",
                "After Requesting a loan user has been redirected to the page without a proper status");
    }
    @Then("I see the new loan account in Account Activity")
    public void verifyNewLoanAccountInAccActivity() {
        pause(2);
        String newAccNumber;
        newAccNumber = requestLoanPage.getNewAccountNumber();
        requestLoanPage.clickNewAccNumber();
        Assert.assertTrue(accountActivityPage.isPageOpened(), "After clicking on New Account number user has been redirected to different page than Account Activity");
        waitUntilElementStatic(accountActivityPage, accountActivityPage.getNoTransactionsFoundText(), Duration.ofSeconds(5));
        String openedAccountNumber = accountActivityPage.getAccountNumber();
        String accountType = "LOAN";
        Assert.assertTrue(accountActivityPage.isAccountAnExpectedType(accountType), "After clicking the account number, account is not " + accountType + " type");
        Assert.assertEquals(newAccNumber, openedAccountNumber, "Loan account number doesn't match the number of opened account");
    }
}
