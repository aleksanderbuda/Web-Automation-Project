import config.ConfigLoader;
import config.DockerSetup;
import config.Drivers;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

@Listeners(ScreenshotTaker.class)
public class BaseTest {
    WebDriver driver;
    protected static Logger log = LogManager.getLogger();
    protected static String browserType;

//    @BeforeSuite
//    public void startDocker() throws IOException, InterruptedException {
//        ConfigLoader configLoader = new ConfigLoader("src/main/java/config/configuration.properties");
//        boolean isRemote = configLoader.isRemote();
//        if (isRemote) {
//            log.info("Starting Selenium Grid on Docker container");
//            DockerSetup.startDockerGrid();
//        }
//    }

    @BeforeClass
    public void adminPageSettings() throws InterruptedException, MalformedURLException {
        this.driver = Drivers.getDriver(browserType);
        AdminPage adminPage = new AdminPage(driver);
        adminPage.adminSettingsSetUp();
        closeBrowser();
    }

    @BeforeMethod
    public void setup() throws MalformedURLException {
        this.driver = Drivers.getDriver(browserType);
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }

    @AfterClass
    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }

//    @AfterSuite
//    public void stopDocker() throws IOException, InterruptedException {
//        ConfigLoader configLoader = new ConfigLoader("src/main/java/config/configuration.properties");
//        boolean isRemote = configLoader.isRemote();
//        if (isRemote) {
//            log.info("Stopping Selenium Grid on Docker container");
//            DockerSetup.stopDockerGrid();
//        }
//    }
    public void failedTest(String testMethodName, String screenshotDirectory, WebDriver driver) {
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            String screenshotPath = screenshotDirectory + "\\" + testMethodName + "_" + System.currentTimeMillis() + ".png";
            FileUtils.copyFile(screenshotFile, new File(screenshotPath));
        } catch (IOException e) {
            log.error("Error saving screenshot: " + e.getMessage());

        }
    }
}




