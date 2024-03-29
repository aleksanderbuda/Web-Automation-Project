import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;

public class ScreenshotTaker extends BaseTest implements ITestListener {
private WebDriver driver;

    public ScreenshotTaker() {
        super();
    }
    public void onTestFailure(ITestResult result) {
        System.out.println("Failed Test!!");

        BaseTest baseTest = (BaseTest) result.getInstance();
        WebDriver driver = baseTest.driver;

        String userHome = System.getProperty("user.home");
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String screenshotDirectory = userHome + "\\Desktop\\FailedScreenshots\\" + timestamp;
        File dir = new File(screenshotDirectory);

        if (!dir.exists()) {
            boolean success = dir.mkdirs();
            if (!success) {
                System.out.println("Error creating screenshot directory: " + screenshotDirectory);
            }
        }
        failedTest(result.getMethod().getMethodName(), screenshotDirectory, driver);
    }
}
