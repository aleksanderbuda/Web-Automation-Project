import config.ConfigLoader;
import config.DockerSetup;
import config.Drivers;
import config.Log;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;


public class BaseTest {
    WebDriver driver;
    protected static Logger log = Log.log;
    protected static String browserType;
    private boolean isRemote;

    @BeforeSuite
    public void startDocker() throws IOException, InterruptedException {
        ConfigLoader configLoader = new ConfigLoader("src/main/java/config/configuration.properties");
        isRemote = configLoader.isRemote();
        if (isRemote) {
            log.info("Starting Selenium Grid on Docker container");
            DockerSetup.startDockerGrid();
        }
    }

    @Parameters("browserType")
    @BeforeClass
    public void adminPageSettings(@Optional("chrome") String browserType) throws InterruptedException, MalformedURLException {
        if (browserType == null) {
            browserType = "chrome";
        }
        driver = Drivers.getDriver(browserType);
        AdminPageInitializer adminPage = new AdminPageInitializer(driver);
        adminPage.AdminPanelSetupManager();
        closeBrowser();
    }

    @Parameters("browserType")
    @BeforeMethod
    public void setup(@Optional("chrome") String browserType) throws MalformedURLException {
        if (browserType == null) {
            browserType = "chrome";
        }
        driver = Drivers.getDriver(browserType);
    }

    @AfterMethod
    public void teardown(ITestResult result) {
        WebpageScreenshotUtil screenshotUtil = new WebpageScreenshotUtil(driver);
        screenshotUtil.onTestFailure(result);
        driver.quit();
        Log.info("Driver quit successfully\n");
    }

    private String getScreenshotDirectory() {
        if (isRemote) {
            String jenkinsJobName = System.getenv("JOB_NAME");
            String jenkinsBuildNumber = System.getenv("BUILD_NUMBER");
            return String.format("jobs/%s/builds/%s/screenshots", jenkinsJobName, jenkinsBuildNumber);
        } else {
            return "local-screenshots";
        }
    }

    @AfterClass
    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite
    public void stopDocker() throws IOException, InterruptedException {
        ConfigLoader configLoader = new ConfigLoader("src/main/java/config/configuration.properties");
        isRemote = configLoader.isRemote();
        if (isRemote) {
            log.info("Stopping Selenium Grid on Docker container");
            DockerSetup.stopDockerGrid();
        }
    }

    public void failedTest(String testMethodName, String screenshotDirectory, WebDriver driver) {
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            String screenshotPath = screenshotDirectory + File.separator + testMethodName + "_" + System.currentTimeMillis() + ".png";
            FileUtils.copyFile(screenshotFile, new File(screenshotPath));
        } catch (IOException e) {
            log.error("Error saving screenshot: " + e.getMessage());

        }
    }

    public String getCurrentTestName(ITestResult result) {
        return result.getMethod().getMethodName();
    }
}
