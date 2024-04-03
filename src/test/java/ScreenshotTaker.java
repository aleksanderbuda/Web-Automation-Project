//import org.openqa.selenium.WebDriver;
//import org.testng.ITestListener;
//import org.testng.ITestResult;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.io.File;
//
//public class ScreenshotTaker extends BaseTest implements ITestListener {
//private WebDriver driver;
//
//    public ScreenshotTaker() {
//        super();
//    }
//    public void onTestFailure(ITestResult result) {
//        System.out.println("Failed Test!!");
//
//        BaseTest baseTest = (BaseTest) result.getInstance();
//        WebDriver driver = baseTest.driver;
//
//        String userHome = System.getProperty("user.home");
//        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String screenshotDirectory = userHome + "\\Desktop\\FailedScreenshots\\" + timestamp;
//        File dir = new File(screenshotDirectory);
//
//        if (!dir.exists()) {
//            boolean success = dir.mkdirs();
//            if (!success) {
//                System.out.println("Error creating screenshot directory: " + screenshotDirectory);
//            }
//        }
//        failedTest(result.getMethod().getMethodName(), screenshotDirectory, driver);
//    }
//}
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ScreenshotTaker extends BaseTest implements ITestListener {
    private WebDriver driver;

    public ScreenshotTaker() {
        super();
    }

    public void onTestFailure(ITestResult result) {
        System.out.println("Failed Test!!");

        BaseTest baseTest = (BaseTest) result.getInstance();
        WebDriver driver = baseTest.driver;

        if (isRemoteConfigured()) {
            takeRemoteScreenshot(driver, result);
        } else {
            takeLocalScreenshot(driver, result);
        }
    }

    private boolean isRemoteConfigured() {
        Properties properties = new Properties();
        try (FileInputStream fileInput = new FileInputStream("configuration.properties")) {
            properties.load(fileInput);
            return Boolean.parseBoolean(properties.getProperty("isRemote"));
        } catch (IOException e) {
            System.err.println("Error reading configuration file: " + e.getMessage());
            return false; // Default to local if the file can't be read
        }
    }

    private void takeRemoteScreenshot(WebDriver driver, ITestResult result) {
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String jenkinsAccessiblePath = "/shared/jenkins/screenshots/" + getTimestamp() + "_" + result.getMethod().getMethodName() + ".png";
        try {
            FileUtils.copyFile(screenshotFile, new File(jenkinsAccessiblePath));
        } catch (IOException e) {
            System.out.println("Error saving screenshot: " + e.getMessage());
        }
    }

    private void takeLocalScreenshot(WebDriver driver, ITestResult result) {
        String userHome = System.getProperty("user.home");
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String screenshotDirectory = userHome + "\\Desktop\\FailedScreenshots\\" + timestamp;
        File dir = new File(screenshotDirectory);

        if (!dir.exists()) {
            dir.mkdirs(); // Easier creation if missing
        }

        failedTest(result.getMethod().getMethodName(), screenshotDirectory, driver);
    }

    public void failedTest(String methodName, String screenshotDirectory, WebDriver driver) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(screenshotDirectory + "\\" + methodName + ".png"));
        } catch (Exception ex) {
            System.out.println("Error taking screenshot: " + ex.getMessage());
        }
    }

    private String getTimestamp() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    }
}

