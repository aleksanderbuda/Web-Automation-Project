import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ParaBankLogIn {

    public final WebDriver driver;
    public static By homePageUsernameLogIn = By.name("username");
    public static By homePageUsernamePassword = By.name("password");
    public static By homePageLogInBtn = By.cssSelector("input.button[type='submit']");
    public final By isUserLoggedInPanel = By.id("rightPanel");

    ParaBankLogIn(WebDriver driver) {
        this.driver = driver;
    }

    public void userLogsIn() {
        driver.get(ParaBankHomePageRegistration.gotoWebPageAddress);
        driver.findElement(homePageUsernameLogIn).sendKeys(getFaker.storeTheUsername);
        driver.findElement(homePageUsernamePassword).sendKeys(getFaker.storeThePassword);
        driver.findElement(homePageLogInBtn).click();
    }
    boolean isUserLoggedIn() {
        return isUserLoggedInPanel != null;
    }
}
