import config.FakeDataGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class UserLoginService {

    UserLoginService(WebDriver driver) {
        this.driver = driver;
    }

    protected static Logger log = LogManager.getLogger();
    public final WebDriver driver;
    public static By homePageUsernameLogIn = By.name("username");
    public static By homePageUsernamePassword = By.name("password");
    public static By homePageLogInBtn = By.cssSelector("input.button[type='submit']");
    public final By isUserLoggedInPanel = By.id("rightPanel");

    public void loginUser() {
        driver.get(HomePageUserRegistration.gotoWebPageAddress);
        driver.findElement(homePageUsernameLogIn).sendKeys(FakeDataGenerator.storeTheUsername);
        driver.findElement(homePageUsernamePassword).sendKeys(FakeDataGenerator.storeThePassword);
        driver.findElement(homePageLogInBtn).click();
        isUserLoggedIn();
    }
    boolean isUserLoggedIn() {
        try {
            driver.findElement(isUserLoggedInPanel).isDisplayed();
            return true;
        } catch (NoSuchElementException e) {
        log.error("There was an error! User not logged in.");
            return false;
        }
    }
}
