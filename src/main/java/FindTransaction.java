import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class FindTransaction {

    public WebDriver driver;
    FindTransaction(WebDriver driver) {
        this.driver = driver;
    }

    private final By gotoFindTransactions = By.linkText("Find Transactions");
    private final By firstAccountNumber = By.xpath("//*[@id=\"accountTable\"]/tbody/tr[1]/td[1]/a");
    private final By gotoTransaction = By.linkText("Funds Transfer Sent");
    private final By findByTransactionIdField = By.id("criteria.transactionId");
    private final By findTransactionBtn = By.cssSelector("button.button[ng-click*=\"criteria.searchType = 'ID'\"]");
    private final By findByDateField = By.id("criteria.onDate");
    private final By findByDateBtn = By.cssSelector("button.button[ng-click*=\"criteria.searchType = 'DATE'\"]");
    private final By findByAmountField = By.id("criteria.amount");
    private final By findByAmountBtn = By.cssSelector("button.button[ng-click*=\"criteria.searchType = 'AMOUNT'\"]");
    private final By logOutBtn = By.linkText("Log Out");
    public String transactionID;
    public String amountPaid;
    public String dateOfTransaction;
    public String foundTransactionID;
    public String foundTransactionDate;
    public String foundTransactionAmount;



    void userFindsTransaction() throws InterruptedException {
        Thread.sleep(1000);
        driver.findElement(firstAccountNumber).click();
        Thread.sleep(1000);
        driver.findElement(gotoTransaction).click();

        WebElement transactionIdElement = driver.findElement(By.xpath("//*[@id=\"rightPanel\"]/table/tbody/tr[1]/td[2]"));
        transactionID = transactionIdElement.getText();
        WebElement dateOfTransactionElement = driver.findElement(By.xpath("//*[@id=\"rightPanel\"]/table/tbody/tr[2]/td[2]"));
        dateOfTransaction = dateOfTransactionElement.getText();
        WebElement transactionDetailsAmountPaidElement = driver.findElement(By.xpath("//*[@id=\"rightPanel\"]/table/tbody/tr[5]/td[2]"));
        amountPaid = transactionDetailsAmountPaidElement.getText();
        String amountPaidStripped = amountPaid.replace("$", "").replace(",00", "");
        String amountPaid = amountPaidStripped.trim();
        driver.findElement(gotoFindTransactions).click();
        Thread.sleep(500);

        driver.findElement(findByTransactionIdField).sendKeys(transactionID);
        driver.findElement(findTransactionBtn).click();
        Thread.sleep(500);
        driver.findElement(gotoTransaction).click();
        Thread.sleep(500);
        WebElement foundTransactionIdElement = driver.findElement(By.xpath("//*[@id=\"rightPanel\"]/table/tbody/tr[1]/td[2]"));
        foundTransactionID = foundTransactionIdElement.getText();
        driver.findElement(gotoFindTransactions).click();
        Thread.sleep(500);

        LocalDate todayDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String formattedDate = todayDate.format(formatter);
        driver.findElement(findByDateField).sendKeys(formattedDate);
        driver.findElement(findByDateBtn).click();
        Thread.sleep(500);
        WebElement foundTransactionDateElement = driver.findElement(By.xpath("//*[@id=\"transactionTable\"]/tbody/tr/td[1]"));
        foundTransactionDate = foundTransactionDateElement.getText();

        driver.findElement(gotoFindTransactions).click();
        Thread.sleep(500);
        driver.findElement(findByAmountField).sendKeys(amountPaid);
        driver.findElement(findByAmountBtn).click();
        Thread.sleep(500);
        driver.findElement(gotoTransaction).click();

        WebElement foundTransactionAmountElement = driver.findElement(By.xpath("//*[@id=\"rightPanel\"]/table/tbody/tr[5]/td[2]"));
        foundTransactionAmount = foundTransactionAmountElement.getText();
        Thread.sleep(500);
        driver.findElement(logOutBtn).click();
    }
}
