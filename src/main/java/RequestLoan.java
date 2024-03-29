import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.List;

public class RequestLoan {

    RequestLoan(WebDriver driver) {
        this.driver = driver;
    }
    static WebDriver driver;
    protected static Logger log = LogManager.getLogger();
    private final By goToAccountsOverview = By.linkText("Accounts Overview");
    private final By gotoRequestLoanPage = By.linkText("Request Loan");
    private final By loanAmountField = By.id("amount");
    private final By downPaymentField = By.id("downPayment");
    private final By applyNowBtn = By.cssSelector("input.button[type='submit']");
    private final By newAccountIdLink = By.id("newAccountId");
    private final By accountType = By.id("accountType");
    private final By firstAccountNumber = By.xpath("//*[@id=\"accountTable\"]/tbody/tr[1]/td[1]/a");
    private final By secondAccountNumber = By.xpath("//*[@id=\"accountTable\"]/tbody/tr[2]/td[1]/a");
    private final By logOutBtn = By.linkText("Log Out");


    void userRequestsLoan() throws InterruptedException {

        driver.findElement(goToAccountsOverview).click();
        List<WebElement> ngBindingLinks = driver.findElements(By.className("ng-binding"));
        String accountNumberId = null;
        for (WebElement link : ngBindingLinks) {
            String href = link.getAttribute("href");
            if (href != null && href.contains("activity.htm?id=")) {
                accountNumberId = href.substring(href.indexOf("?id=") + 4);
                break;
            }
        }
        driver.findElement(gotoRequestLoanPage).click();
        driver.findElement(loanAmountField).sendKeys("10000");
        driver.findElement(downPaymentField).sendKeys("1500");

        if (accountNumberId != null) {
            WebElement fromAccountId = driver.findElement(By.id("fromAccountId"));
            Select fromAccountIdSelect = new Select(fromAccountId);
            fromAccountIdSelect.selectByIndex(0);
            String selectedAccountId = fromAccountIdSelect.getFirstSelectedOption().getAttribute("value");
            if (accountNumberId.equals(selectedAccountId)) {
                log.info("The account number from which loan was taken matches!");
            } else {
                log.warn("The account number from which loan was taken doesn't match!");
            }
        }
        driver.findElement(applyNowBtn).click();

        List<WebElement> findStatusByNgScope= driver.findElements(By.className("ng-scope"));
        String loanRequestStatus = "Approved";
        for (WebElement filterStatus: findStatusByNgScope) {
            String statusText = filterStatus.getText();
            if (statusText != null && statusText.contains(loanRequestStatus)) {
                log.info("Loan has been approved!");
                break;
            }
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        List<WebElement> newAccountIdAfterLoanRequest = driver.findElements(By.id("newAccountId"));
        String newAccNumberAfterLoan = null; //""; ?
        for (WebElement filterAccountIdBy : newAccountIdAfterLoanRequest) {
            String href = filterAccountIdBy.getAttribute("href");
            if (href != null && href.contains("activity.htm?id=")) {
                newAccNumberAfterLoan = href.substring(href.indexOf("?id=") + 4);
                break;
            }
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
//        System.out.println(newAccNumberAfterLoan);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.findElement(newAccountIdLink).click();
        Thread.sleep(1000);
        WebElement accountDetailsAccountType = driver.findElement(accountType);
        String isAccountTypeLoan = accountDetailsAccountType.getText();
        if (isAccountTypeLoan.equals("LOAN")) {
            log.info("Account type: LOAN");
        } else {
            log.warn("Error: invalid account type: " + isAccountTypeLoan);
        }
        driver.findElement(goToAccountsOverview).click();
        Thread.sleep(500);
        List<WebElement> findNewLoanAccount = driver.findElements(By.className("ng-binding"));
        String accOverviewLoanAccNum = null;
        int count = 0;
        for (WebElement link : findNewLoanAccount) {
            String href = link.getAttribute("href");
            if (href != null && href.contains("activity.htm?id=")) {
                count++;
                if (count == 2) {
                    accOverviewLoanAccNum = href.substring(href.indexOf("?id=") + 4);
                    break;
                }
            }
        }
        Thread.sleep(500);
        if (newAccNumberAfterLoan != null && newAccNumberAfterLoan.equals(accOverviewLoanAccNum)) {
            log.info("Account numbers are matching!");
        } else {
            log.warn("Account numbers are not matching or one of them is null!");
        }
        driver.findElement(firstAccountNumber).click();
        Thread.sleep(500);
        WebElement findDownPaymentForLoan = driver.findElement(By.cssSelector("td.ng-binding.ng-scope"));
        String downPaymentForLoanText= findDownPaymentForLoan.getText();
        WebElement findBalanceAfterDownPayment = driver.findElement(By.id("balance"));
        String balanceAfterDownPaymentText = findBalanceAfterDownPayment.getText();
        if (downPaymentForLoanText.equals("$1500.00") && balanceAfterDownPaymentText.equals("$48500.00")) {
            log.info("First down payment " +downPaymentForLoanText+ " has been paid. Main account balance is: "+balanceAfterDownPaymentText);
        } else {
            log.warn("Error: incorrect down payment amount: " + downPaymentForLoanText);
        }
        driver.findElement(goToAccountsOverview).click();
        Thread.sleep(500);
        driver.findElement(secondAccountNumber).click();
        Thread.sleep(500);
        WebElement findBalanceAfterDownPaymentForLoanAcc = driver.findElement(By.id("balance"));
        String balanceAfterDownPaymentForLoanAccText = findBalanceAfterDownPaymentForLoanAcc.getText();
        if (balanceAfterDownPaymentForLoanAccText.equals("$10000.00")) {
            log.info("Loan account balance: "+balanceAfterDownPaymentForLoanAccText);
        } else {
            log.warn("Error: incorrect balance of loan account: " + balanceAfterDownPaymentForLoanAccText);
        }
        driver.findElement(logOutBtn).click();
    }
}
