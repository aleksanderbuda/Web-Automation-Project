package config;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

public class Drivers {
    static WebDriver driver;
    public static String browserType;

    public static WebDriver getDriver(String browserType) throws MalformedURLException {
        Drivers.browserType = browserType;

        Properties props = new Properties();
        try {
            InputStream inputStream = new FileInputStream("src/main/java/config/configuration.properties");
            props.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean isRemote = Boolean.parseBoolean(props.getProperty("isRemote"));


        ChromeOptions chromeOptions = new ChromeOptions();
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        EdgeOptions edgeOptions = new EdgeOptions();
        if (isRemote) {
            switch (browserType.toLowerCase()) {
                case "chrome" -> {
                    driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), chromeOptions);
                }
                case "firefox" -> {
                    driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), firefoxOptions);
                }
                case "edge" -> {
                    driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), edgeOptions);
                }
                default -> {
                    throw new IllegalArgumentException("Invalid browser specified: " + browserType);
                }
            }
        } else {
            switch (browserType.toLowerCase()) {
                case "chrome" -> {
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver(chromeOptions);
                }
                case "firefox" -> {
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver(firefoxOptions);
                }
                case "edge" -> {
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver(edgeOptions);
                }
                default -> {
                    throw new IllegalArgumentException("Invalid browser specified: " + browserType);

                }
            }
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
//        driver.manage().window().maximize();
        return driver;
    }
}


