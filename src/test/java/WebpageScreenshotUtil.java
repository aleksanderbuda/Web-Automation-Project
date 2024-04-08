import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class WebpageScreenshotUtil implements ITestListener {
    private WebDriver driver;
    protected static Logger log = LogManager.getLogger();

    public WebpageScreenshotUtil(WebDriver driver) {
        this.driver = driver;
    }

    public void onTestFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            System.out.println("Failed Test!!");
            if (isRemoteConfigured()) {
                takeRemoteScreenshot(result);
            } else {
                takeLocalScreenshot(result);
            }
        }
    }

    private boolean isRemoteConfigured() {
        File configFile = new File("src/main/java/config/configuration.properties");
        if (!configFile.exists()) {
            log.warn("Configuration file not found.");
            return false;
        }

        Properties properties = new Properties();
        try (FileInputStream fileInput = new FileInputStream(configFile)) {
            properties.load(fileInput);
            return Boolean.parseBoolean(properties.getProperty("isRemote"));
        } catch (IOException e) {
            log.error("Error reading configuration file: " + e.getMessage());
            return false;
        }
    }

    private void takeRemoteScreenshot(ITestResult result) {
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String jenkinsAccessiblePath = "/shared/jenkins/screenshots/" + getTimestamp() + "_" + result.getMethod().getMethodName() + ".png";
        try {
            FileUtils.copyFile(screenshotFile, new File(jenkinsAccessiblePath));
        } catch (IOException e) {
            System.out.println("Error saving screenshot: " + e.getMessage());
        }
    }

    private void takeLocalScreenshot(ITestResult result) {
        String projectDirectory = System.getProperty("user.dir");
        String screenshotsDirectory = projectDirectory + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "screenshots";
        File dir = new File(screenshotsDirectory);

        if (!dir.exists()) {
            dir.mkdirs();
        }
        failedTest(result.getMethod().getMethodName(), screenshotsDirectory);
    }

    public void failedTest(String methodName, String screenshotDirectory) {
        String screenshotPath = screenshotDirectory + File.separator + methodName + ".png";
        try {
            FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(screenshotPath));
        } catch (IOException e) {
            log.error("Error saving screenshot to " + screenshotPath , e);
        }
    }

    private String getTimestamp() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    }
}
