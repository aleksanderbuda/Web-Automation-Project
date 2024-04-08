import config.FakeDataGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePageUserRegistration {

    HomePageUserRegistration(WebDriver driver) {
        this.driver = driver;
    }
    FakeDataGenerator faker = new FakeDataGenerator();
    protected static Logger log = LogManager.getLogger();
    private final WebDriver driver;
    public static String gotoWebPageAddress = "https://parabank.parasoft.com";
    private String gotoRegisterPage = "https://parabank.parasoft.com/parabank/register.htm";
    private By registerFirstNameId = By.id("customer.firstName");
    private By registerLastNameId = By.id("customer.lastName");
    private By registeraddressStreetId = By.id("customer.address.street");
    private By registercityId = By.id("customer.address.city");
    private By registerstateId = By.id("customer.address.state");
    private By registerzipCodeId = By.id("customer.address.zipCode");
    private By registerphoneNumberId = By.id("customer.phoneNumber");
    private By registerssnID = By.id("customer.ssn");
    private By registerPageUsernameId = By.id("customer.username");
    private By registerPasswordId = By.id("customer.password");
    private By confirmPasswordId = By.id("repeatedPassword");
    private By registersubmitBtn = By.xpath("//input[@value='Register']");
    private By isUserRegisteredText = By.tagName("Your account was created successfully. You are now logged in.");
    private By userLogsOff = By.linkText("Log Out");


   public void registerNewUser() throws InterruptedException {
       driver.get(gotoWebPageAddress);
       driver.get(gotoRegisterPage);

       driver.findElement(registerFirstNameId).sendKeys(faker.generateFirstName());
       driver.findElement(registerLastNameId).sendKeys(faker.generateLastName());
       driver.findElement(registeraddressStreetId).sendKeys(faker.generateAddress());
       driver.findElement(registercityId).sendKeys(faker.generateCityField());
       driver.findElement(registerstateId).sendKeys(faker.generateStateField());
       driver.findElement(registerzipCodeId).sendKeys(faker.generateZipCodeField());
       driver.findElement(registerphoneNumberId).sendKeys(faker.generatePhoneField());
       driver.findElement(registerssnID).sendKeys(faker.generateSsnField());
       driver.findElement(registerPageUsernameId).sendKeys(faker.generateUsernameField());
       driver.findElement(registerPasswordId).sendKeys(faker.generatePasswordField());
       driver.findElement(confirmPasswordId).sendKeys(faker.storeThePassword);
       driver.findElement(registersubmitBtn).click();
       Thread.sleep(500);
       isUserRegistered();
       driver.findElement(userLogsOff).click();

       FakeDataGenerator.getThePasswordAndUsername();
   }
    void isUserRegistered() {
        try {
            WebElement successMessageElement = driver.findElement(isUserRegisteredText);
            successMessageElement.isDisplayed();
        } catch (NoSuchElementException e) {
            log.error("Registration failed: Success message not found");
        }
    }
}
