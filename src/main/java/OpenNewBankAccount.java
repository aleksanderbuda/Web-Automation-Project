import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;


public class OpenNewBankAccount {
    OpenNewBankAccount(WebDriver driver) {
        this.driver = driver;
    }
    public static WebDriver driver;
    private WebDriverWait wait;
    protected static Logger log = LogManager.getLogger();
    private static final By clickOpenNewAccountHomePageBtn = By.linkText("Open New Account");
    private static final By clickOpenNewAccountAfterDropDownBtn = By.cssSelector("input.button[type='submit']");
    private static final By goToAccountsOverview = By.linkText("Accounts Overview");

    static String getAccountNumberFromXPath(String accountXPath, String balanceXPath, String expectedBalance) {
        WebElement balanceElement = driver.findElement(By.xpath("//*[@id=\"accountTable\"]/tbody/tr[1]/td[2]"));
        String balanceText = balanceElement.getText();
        if (balanceText.equals(expectedBalance)) {
            WebElement firstAccountElementInTable = driver.findElement(By.xpath("//*[@id=\"accountTable\"]/tbody/tr[1]/td[1]/a"));
            return firstAccountElementInTable.getText();
        } else {
            return null;
        }
    }
     void userOpensNewBankAccount() throws InterruptedException {
         wait = new WebDriverWait(driver, Duration.ofSeconds(10));

         int maxAttempts = 3;
         for (int i = 0; i < maxAttempts; i++) {
             try {
                 Thread.sleep(500);
                 String accountXPath = "//*[@id=\"accountTable\"]/tbody/tr[1]/td[1]/a";
                 String balanceXPath = "//*[@id=\"accountTable\"]/tbody/tr[1]/td[2]";
                 String expectedBalance = "$50000.00";
                 String accountNumber = getAccountNumberFromXPath(accountXPath, balanceXPath, expectedBalance);
                 if (accountNumber != null) {
                     log.info("Found account with number: " + accountNumber + " with expected balance.");
                     break;
                 } else {
                     log.warn("Account with exepected balance not found!.");
                 }
             } catch (Exception e) {
                 log.error("Element not found. Retrying...");
             }

         }
        driver.findElement(clickOpenNewAccountHomePageBtn).click();
         WebElement openBankAccountTypeDropDown = driver.findElement(By.id("type"));
         Select selectSavings = new Select(openBankAccountTypeDropDown);
         selectSavings.selectByIndex(1);
         Thread.sleep(1000); // delete?
         driver.findElement(clickOpenNewAccountAfterDropDownBtn).click();

         driver.findElement(goToAccountsOverview).click();
        Thread.sleep(500);
        String balanceXPath1 = "//*[@id=\"accountTable\"]/tbody/tr[1]/td[2]";
        WebElement firstBalanceElement = driver.findElement(By.xpath(balanceXPath1));
        String firstBalanceText = firstBalanceElement.getText();
        if (firstBalanceText.equals("$49900.00")) {
            log.info("Correct balance of first account found: $49900.00");
        } else {
            log.warn("Error: Incorrect balance of first account: " + firstBalanceText);
        }
        String balanceSecondRowAccount = "//*[@id=\"accountTable\"]/tbody/tr[2]/td[2]";
        WebElement secondBalanceElement = driver.findElement(By.xpath(balanceSecondRowAccount));
        String secondBalanceText = secondBalanceElement.getText();
        if (secondBalanceText.equals("$100.00")) {
            log.info("Correct balance of second account found: $100.00");
        } else {
            log.warn("Error: Incorrect balance of first account: " + secondBalanceText);
        }
    }
    public String getFirstBalanceText() {
        WebElement firstBalanceElement = driver.findElement(By.xpath("//*[@id=\"accountTable\"]/tbody/tr[1]/td[2]"));
        return firstBalanceElement.getText();
    }
    public String getSecondBalanceText() {
        WebElement secondBalanceElement = driver.findElement(By.xpath("//*[@id=\"accountTable\"]/tbody/tr[2]/td[2]"));
        return secondBalanceElement.getText();
    }
}


