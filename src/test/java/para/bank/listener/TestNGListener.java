package para.bank.listener;

import com.zebrunner.carina.webdriver.Screenshot;
import com.zebrunner.carina.webdriver.ScreenshotType;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.log4testng.Logger;

import static com.zebrunner.agent.core.webdriver.RemoteWebDriverFactory.getDriver;

public class TestNGListener implements ITestListener {

    private static final Logger LOGGER = Logger.getLogger(TestNGListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        LOGGER.info("Test Started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LOGGER.info("Test Passed: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LOGGER.info("Test Failed: " + result.getName());
        LOGGER.error("Error Message: " + result.getThrowable());
        Screenshot.capture(getDriver(), ScreenshotType.UNSUCCESSFUL_DRIVER_ACTION);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LOGGER.info("Test Skipped: " + result.getName());
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        LOGGER.info("Test Failed with Timeout: " + result.getName());
        LOGGER.error("Error Message: " + result.getThrowable());
    }
}
