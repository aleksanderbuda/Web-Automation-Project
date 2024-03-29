import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RegistrationFieldValidation {
    public WebDriver driver;
    RegistrationFieldValidation(WebDriver driver) {
        this.driver = driver;
    }

    protected static Logger log = LogManager.getLogger();
    private String gotoRegisterPage = "https://parabank.parasoft.com/parabank/register.htm";
    private final By registerSubmitBtn = By.xpath("//input[@value='Register']");

    public void checkRegistrationErrorFields() throws InterruptedException {
        driver.get(gotoRegisterPage);
        driver.findElement(registerSubmitBtn).click();
        Thread.sleep(1000);
        WebElement firstNameError = driver.findElement(By.id("customer.firstName.errors"));
        String firstNameErrorRequired = firstNameError.getText();
        String firstNameErrorColor = firstNameError.getCssValue("color");
        if (firstNameErrorRequired.equals("First name is required.") && firstNameErrorColor.equals("rgba(255, 0, 0, 1)")) {
            log.info("Error message and color are as intended for First Name field.");
        } else {
            log.warn("Error message or color is not as intended for First Name field.");
        }
        WebElement lastNameError = driver.findElement(By.id("customer.lastName.errors"));
        String lastNameErrorRequired = lastNameError.getText();
        String lastNameErrorColor = firstNameError.getCssValue("color");
        if (lastNameErrorRequired.equals("Last name is required.") && lastNameErrorColor.equals("rgba(255, 0, 0, 1)")) {
            log.info("Error message and color are as intended for Last Name field.");
        } else {
            log.warn("Error message or color is not as intended for Last Name field.");
        }
        WebElement addressError = driver.findElement(By.id("customer.address.street.errors"));
        String addressErrorRequired = addressError.getText();
        String addressErrorColor = addressError.getCssValue("color");
        if (addressErrorRequired.equals("Address is required.") && addressErrorColor.equals("rgba(255, 0, 0, 1)")) {
            log.info("Error message and color are as intended for Address field.");
        } else {
            log.warn("Error message or color is not as intended for Address field.");
        }
        WebElement cityError = driver.findElement(By.id("customer.address.city.errors"));
        String cityErrorRequired = cityError.getText();
        String cityErrorColor = cityError.getCssValue("color");
        if (cityErrorRequired.equals("City is required.") && cityErrorColor.equals("rgba(255, 0, 0, 1)")) {
            log.info("Error message and color are as intended for City field.");
        } else {
            log.warn("Error message or color is not as intended for City field.");
        }
        WebElement stateError = driver.findElement(By.id("customer.address.state.errors"));
        String stateErrorRequired = stateError.getText();
        String stateErrorColor = stateError.getCssValue("color");
        if (stateErrorRequired.equals("State is required.") && stateErrorColor.equals("rgba(255, 0, 0, 1)")) {
            log.info("Error message and color are as intended for State field.");
        } else {
            log.warn("Error message or color is not as intended for State field.");
        }
        WebElement zipCodeError = driver.findElement(By.id("customer.address.zipCode.errors"));
        String zipCodeErrorRequired = zipCodeError.getText();
        String zipCodeErrorColor = zipCodeError.getCssValue("color");
        if (zipCodeErrorRequired.equals("Zip Code is required.") && zipCodeErrorColor.equals("rgba(255, 0, 0, 1)")) {
            log.info("Error message and color are as intended for Zip Code field.");
        } else {
            log.warn("Error message or color is not as intended for Zip Code field.");
        }
        WebElement ssnError = driver.findElement(By.id("customer.ssn.errors"));
        String ssnErrorRequired = ssnError.getText();
        String ssnErrorColor = ssnError.getCssValue("color");
        if (ssnErrorRequired.equals("Social Security Number is required.") && ssnErrorColor.equals("rgba(255, 0, 0, 1)")) {
            log.info("Error message and color are as intended for Social Security Number field.");
        } else {
            log.warn("Error message or color is not as intended for Social Security Number field.");
        }
        WebElement usernameError = driver.findElement(By.id("customer.username.errors"));
        String usernameRequired = usernameError.getText();
        String usernameColor = usernameError.getCssValue("color");
        if (usernameRequired.equals("Username is required.") && usernameColor.equals("rgba(255, 0, 0, 1)")) {
            log.info("Error message and color are as intended for Username field.");
        } else {
            log.warn("Error message or color is not as intended for Username field.");
        }
        WebElement passwordError = driver.findElement(By.id("customer.password.errors"));
        String passwordRequired = passwordError.getText();
        String passwordColor = passwordError.getCssValue("color");
        if (passwordRequired.equals("Password is required.") && passwordColor.equals("rgba(255, 0, 0, 1)")) {
            log.info("Error message and color are as intended for Password field.");
        } else {
            log.warn("Error message or color is not as intended for Password field.");
        }
        WebElement confirmPasswordError = driver.findElement(By.id("repeatedPassword.errors"));
        String confirmPasswordRequired = confirmPasswordError.getText();
        String confirmPasswordColor = confirmPasswordError.getCssValue("color");
        if (confirmPasswordRequired.equals("Password confirmation is required.") && confirmPasswordColor.equals("rgba(255, 0, 0, 1)")) {
            log.info("Error message and color are as intended for Password confirm field.");
        } else {
            log.warn("Error message or color is not as intended for Password confirm field.");
        }
    }
}
