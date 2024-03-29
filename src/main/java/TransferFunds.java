import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class TransferFunds {

    TransferFunds(WebDriver driver) {
        this.driver = driver;
    }
    public WebDriver driver;
    private WebDriverWait wait;
    protected static Logger log = LogManager.getLogger();
    private final By goToAccountsOverview = By.linkText("Accounts Overview");
    private final By goToTransferFundsTab = By.linkText("Transfer Funds");
    private final By amountToTransfer = By.id("amount");
    private final By transferCompleteScreen = By.id("amount");
    private final By transferButton = By.cssSelector("input.button[type='submit']");

    String getAccountNumberFromXPath(String expectedBalance) {
        WebElement balanceElement = driver.findElement(By.xpath("//*[@id=\"accountTable\"]/tbody/tr[1]/td[2]"));
        String balanceText = balanceElement.getText();
        if (balanceText.equals(expectedBalance)) {
            WebElement firstAccountElementInTable = driver.findElement(By.xpath("//*[@id=\"accountTable\"]/tbody/tr[1]/td[1]/a"));
            return firstAccountElementInTable.getText();
        } else {
            return null;
        }
    }
    void userTransfersTheFunds() throws InterruptedException {
        wait = new WebDriverWait(driver, Duration.ofMillis(3000));
        driver.findElement(goToAccountsOverview).click();
        Thread.sleep(1000);

        String initialBalanceFirstRowAccount = "//*[@id=\"accountTable\"]/tbody/tr[1]/td[2]";
        WebElement firstBalanceElement = driver.findElement(By.xpath(initialBalanceFirstRowAccount));
        String firstBalanceText = firstBalanceElement.getText();
        Thread.sleep(1000);
        if (firstBalanceText.equals("$49900.00")) {
            log.info("Balance of first account is correct: $49900.00");
        } else {
            log.warn("Error: incorrect balance for first account: " + firstBalanceText);
        }
        String initialBalanceSecondRowAccount = "//*[@id=\"accountTable\"]/tbody/tr[2]/td[2]";
        WebElement secondBalanceElement = driver.findElement(By.xpath(initialBalanceSecondRowAccount));
        String secondBalanceText = secondBalanceElement.getText();
        if (secondBalanceText.equals("$100.00")) {
            log.info("Balance of second account is correct: $100.00");
        } else {
            log.warn("Error: incorrect balance for second account: " + secondBalanceText);
        }
        driver.findElement(goToTransferFundsTab).click();
        Thread.sleep(500);
        driver.findElement(amountToTransfer).sendKeys("5000");

        WebElement transferToAccountIdDropDown = driver.findElement(By.id("toAccountId"));
        Select selectLocal = new Select(transferToAccountIdDropDown);
        selectLocal.selectByIndex(1);
        Thread.sleep(1000);
        driver.findElement(transferButton).click();
        Thread.sleep(1000);
        if (driver.findElement(transferCompleteScreen).getText().equals("$5000.00")) {
            log.info("$5000 has been transferred to second account!");
        } else {
            log.warn("There was an error during the transfer!");
        }
        Thread.sleep(1000);
        driver.findElement(goToAccountsOverview).click();
//        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id=\"accountTable\"]/tbody/tr[1]/td[2]"))));
        Thread.sleep(3000);
    }
    public boolean isBalanceCorrectForFirstRow() {
        String expectedBalanceFirstRowAfterTransfer = "$44900.00";
        String firstRowBalanceAfterTransfer = "//*[@id=\"accountTable\"]/tbody/tr[1]/td[2]";
        WebElement firstBalanceElementAfterTransfer = driver.findElement(By.xpath(firstRowBalanceAfterTransfer));
        try {
            String firstBalanceElementAfterTransferText = firstBalanceElementAfterTransfer.getText();
            if (!firstBalanceElementAfterTransferText.equals(expectedBalanceFirstRowAfterTransfer)) {
                throw new AssertionError("/nBalance of first account is incorrect: " + firstBalanceElementAfterTransferText);
            }
            return true;

        } catch (Exception e) {
            log.error("/nThere was an error during checking of first account: " + e.getMessage());
            return false;
        }
    }
    public boolean isBalanceCorrectForSecondRow() {
        String expectedBalanceSecondRowAfterTransfer = "$5100.00";
        String secondRowBalanceAfterTransfer = "//*[@id=\"accountTable\"]/tbody/tr[2]/td[2]";
        WebElement secondBalanceElementAfterTransfer = driver.findElement(By.xpath(secondRowBalanceAfterTransfer));
        try {
            String secondBalanceElementAfterTransferText = secondBalanceElementAfterTransfer.getText();
            if (!secondBalanceElementAfterTransferText.equals(expectedBalanceSecondRowAfterTransfer)) {
                throw new AssertionError("/nBalance of second account is incorrect: " + secondBalanceElementAfterTransferText);
            }
            return true;

        } catch (Exception e) {
            log.error("/nThere was an error during checking of second account: " + e.getMessage());
            return false;
        }
    }
}







