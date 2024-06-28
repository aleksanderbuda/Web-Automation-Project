package para.bank;

import com.zebrunner.carina.core.AbstractTest;
import com.zebrunner.carina.webdriver.core.capability.impl.desktop.ChromeCapabilities;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import para.bank.pages.AbstractParaBankPage;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

public class AbstractParaBankPageTest extends AbstractTest {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractParaBankPageTest.class);

    @BeforeSuite
    public void startDockerGrid() throws IOException, InterruptedException {
        LOGGER.info("Starting Docker Grid...");
        String[] startDocker = { "cmd", "/c", "start", "start_docker.bat" };
        ProcessBuilder processBuilder = new ProcessBuilder(startDocker);
        processBuilder.directory(new File("docker"));
        processBuilder.start();

        pause(15);
        LOGGER.info("Docker Grid started.");
    }

    @AfterSuite
    public void stopDockerGrid() throws IOException, InterruptedException {
        LOGGER.info("Stopping Docker Grid...");
        String[] startDocker = { "cmd", "/c", "start", "stop_docker.bat" };
        ProcessBuilder processBuilder = new ProcessBuilder(startDocker);
        processBuilder.directory(new File("docker"));
        processBuilder.start();

        pause(15);
        LOGGER.info("Docker Grid stopped.");
    }

    public long getBottomY(ExtendedWebElement element) {
        long elementTopPointY = element.getLocation().getY();
        int elementHeight = element.getSize().getHeight();
        return elementTopPointY + elementHeight;
    }

    public boolean waitUntilElementStatic(AbstractParaBankPage abstractPage, ExtendedWebElement element, Duration timeout) {
        AtomicInteger elementPosition = new AtomicInteger(0);
        return abstractPage.waitUntil(d -> {
            int currentY = element.getLocation().getY();
            return currentY == elementPosition.getAndSet(currentY);
        }, timeout.toSeconds());
    }

    public static MutableCapabilities prepareLoggingCapabilities() {
        ChromeOptions options = new ChromeCapabilities().getCapability("chrome");
        options.setCapability("goog:loggingPrefs", prepareLoggingPreferences());
        return options;
    }

    private static Map<String, Object> prepareLoggingPreferences() {
        LoggingPreferences logPreferences = new LoggingPreferences();
        logPreferences.enable(LogType.PERFORMANCE, Level.ALL);
        return logPreferences.toJson();
    }

    public String createRandomStrongPassword() {
        return createRandomStrongPassword(10);
    }

    public String createRandomStrongPassword(int size) {
        String pass = RandomStringUtils.randomAlphabetic(size - 4) +
                RandomStringUtils.randomAlphabetic(1).toUpperCase() +
                RandomStringUtils.randomAlphabetic(1).toLowerCase() +
                RandomStringUtils.randomNumeric(1);
        return pass + getRandomSpecialChar();
    }

    public char getRandomSpecialChar() {
        Random random = new Random();
        String specialSymbols = "[$&+=?@#|/\"<>^*()%!]~`";
        return specialSymbols.toCharArray()[random.nextInt(specialSymbols.length())];
    }

    public static String createRandomAddress() {
        int streetNumber = new Random().nextInt(8999) + 1000; // 1000 to 9999
        String streetName = StringUtils.capitalize(RandomStringUtils.randomAlphabetic(10).toLowerCase());
        return String.format("%d %s St", streetNumber, streetName);
    }

    public static String createRandomPhoneNumber() {
        return String.format("%s-%s-%s",
                RandomStringUtils.randomNumeric(3),
                RandomStringUtils.randomNumeric(3),
                RandomStringUtils.randomNumeric(4));
    }

    public static String createRandomSSN() {
        return String.format("%s-%s-%s",
                RandomStringUtils.randomNumeric(3),
                RandomStringUtils.randomNumeric(2),
                RandomStringUtils.randomNumeric(4));
    }

    public static String createRandomUsername() {
        return "user_" + RandomStringUtils.randomAlphanumeric(8).toLowerCase();
    }
}

