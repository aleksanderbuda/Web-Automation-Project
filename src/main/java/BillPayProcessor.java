import config.FakeDataGenerator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;


public class BillPayProcessor {

    BillPayProcessor(WebDriver driver) {
        this.driver = driver;
    }
    public WebDriver driver;
    private By gotoBillPay = By.linkText("Bill Pay");
    private By payeeNameField = By.cssSelector("input[ng-model='payee.name']");
    private By addressField = By.cssSelector("input[ng-model='payee.address.street']");
    private By cityField = By.cssSelector("input[ng-model='payee.address.city']");
    private By stateField = By.cssSelector("input[ng-model='payee.address.state']");
    private By zipCodeField = By.cssSelector("input[ng-model='payee.address.zipCode']");
    private By phoneNumberField = By.cssSelector("input[ng-model='payee.phoneNumber']");
    private By accountNumberField = By.cssSelector("input[ng-model='payee.accountNumber']");
    private By verifyAccountNumberField = By.cssSelector("input[ng-model='verifyAccount']");
    private By amountToPay = By.cssSelector("input[ng-model='amount']");
    private By sendPaymentBtn = By.cssSelector("input.button[type='submit']");
    private final By goToAccountsOverview = By.linkText("Accounts Overview");
    private String payeeName;
    private String amountPaid;
    public String fromAccountId;


    void processBillPayment() throws InterruptedException {
        FakeDataGenerator faker = new FakeDataGenerator();
        driver.findElement(gotoBillPay).click();
        driver.findElement(payeeNameField).sendKeys(faker.generateFirstName());
        driver.findElement(addressField).sendKeys(faker.generateAddress());
        driver.findElement(cityField).sendKeys(faker.generateCityField());
        driver.findElement(stateField).sendKeys(faker.generateStateField());
        driver.findElement(zipCodeField).sendKeys(faker.generateZipCodeField());
        driver.findElement(phoneNumberField).sendKeys(faker.generatePhoneField());
        driver.findElement(accountNumberField).sendKeys("123456789");
        driver.findElement(verifyAccountNumberField).sendKeys("123456789");
        driver.findElement(amountToPay).sendKeys("50");

        WebElement fromAccountDropdown = driver.findElement(By.name("fromAccountId"));
        Select selectSecondAccount = new Select(fromAccountDropdown);
        selectSecondAccount.selectByIndex(1);

        driver.findElement(sendPaymentBtn).click();
        Thread.sleep(1000);
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("title")));
        WebElement payeeNameElement = driver.findElement(By.id("payeeName"));
        payeeName = payeeNameElement.getText();
        WebElement amountPaidElement = driver.findElement(By.id("amount"));
        amountPaid = amountPaidElement.getText();
        WebElement fromAccountIdElement = driver.findElement(By.id("fromAccountId"));
        fromAccountId = fromAccountIdElement.getText();

        driver.findElement(goToAccountsOverview).click();
        Thread.sleep(1000);
        String gotoSecondAccount = "https://parabank.parasoft.com/parabank/activity.htm?id="+fromAccountId;
        driver.get(gotoSecondAccount);
        Thread.sleep(500);
    }
    public boolean validatePaymentAmount() {
        WebElement transactionDetailsAmountPaid = driver.findElement(By.xpath("//*[@id=\"transactionTable\"]/tbody/tr[2]/td[3]"));
        String transactionDetailsAmountPaidText = transactionDetailsAmountPaid.getText();
        return transactionDetailsAmountPaidText.equals(amountPaid);
    }
    public boolean validatePayee() {
        WebElement transactionDetailsDescription = driver.findElement(By.xpath("//*[@id=\"transactionTable\"]/tbody/tr[2]/td[2]/a"));
        String transactionDetailsDescriptionText = transactionDetailsDescription.getText();
        return transactionDetailsDescriptionText.contains(payeeName);
    }
}





