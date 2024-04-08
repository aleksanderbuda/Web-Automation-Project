import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class AdminPageInitializer {

    AdminPageInitializer(WebDriver driver) {
        this.driver = driver;
    }
    public WebDriver driver;
    private String goToAdminPage = "https://parabank.parasoft.com/parabank/admin.htm";
    private By databaseCleanBtn = By.cssSelector("button.button[value='CLEAN']");
    private By jmsServiceStartupBtn = By.cssSelector("input.button[value='Startup']");
    private By loanProcessorTreshold = By.id("loanProcessorThreshold");
    private By dataAccessModeJDBC = By.id("accessMode4");
    private By initialBalance = By.id("initialBalance");
    private By minBalance = By.id("minimumBalance");
    private By adminPageSubmitBtn = By.cssSelector("input.button[value='Submit']");

    public void AdminPanelSetupManager() throws InterruptedException {
        driver.get(goToAdminPage);
        driver.findElement(databaseCleanBtn).click();
        Thread.sleep(500);
        String jmsServiceStatusTextXPath = "//*[@id=\"rightPanel\"]/table/tbody/tr/td[2]/form/table/tbody/tr/td[2]";
        WebElement jmsServiceStatus = driver.findElement(By.xpath(jmsServiceStatusTextXPath));
        String statusText = jmsServiceStatus.getText();
        if (statusText.equals("Stopped")) {
            driver.findElement(jmsServiceStartupBtn).click();
            Thread.sleep(1200);
        }
        driver.findElement(dataAccessModeJDBC).click();
        driver.findElement(initialBalance).clear();
        driver.findElement(initialBalance).sendKeys("50000");
        driver.findElement(minBalance).clear();
        driver.findElement(minBalance).sendKeys("100");
        WebElement loanProvider = driver.findElement(By.id("loanProvider"));
        Select selectLocal = new Select(loanProvider);
        selectLocal.selectByValue("local");
        WebElement loanProcessor = driver.findElement(By.id("loanProcessor"));
        Select selectDown = new Select(loanProcessor);
        selectDown.selectByValue("down");
        driver.findElement(loanProcessorTreshold).clear();
        driver.findElement(loanProcessorTreshold).sendKeys("10");
        driver.findElement(adminPageSubmitBtn).click();
    }
}







