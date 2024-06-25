package para.bank.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

@Getter
public class AdminPage extends AbstractParaBankPage {

    @FindBy(xpath = "//button[@value='CLEAN']")
    private ExtendedWebElement cleanDatabaseBtn;

    @FindBy(xpath = "//input[@value='Shutdown']")
    private ExtendedWebElement shutdownBtn;

    @FindBy(xpath = "//input[@value='Startup']")
    private ExtendedWebElement startupBtn;

    @FindBy(xpath = "//input[@value='jdbc']")
    private ExtendedWebElement dataAccessModeField;

    @FindBy(xpath = "//table[@class='form2']//input)[2]")
    private ExtendedWebElement soapEndpointField;

    @FindBy(xpath = "//table[@class='form2']//input)[3]")
    private ExtendedWebElement restEndpointField;

    @FindBy(xpath = "//select[@class='input'])[2]//option[@value='down']")
    private ExtendedWebElement loanProcessorField;

    @FindBy(xpath = "//b[text()='Init. Balance:']/following::input[1]")
    private ExtendedWebElement initBalance;

    @FindBy(xpath = "//b[text()='Init. Balance:']/following::input[2]")
    private ExtendedWebElement minBalance;

    @FindBy(xpath = "//input[@value='Submit']")
    private ExtendedWebElement submitButton;

    @FindBy(xpath = "//b[text()='Threshold:']/following::input[1]")
    private ExtendedWebElement thresholdField;

    @FindBy(xpath = "//li[@class='home']")
    private ExtendedWebElement homeBtn;

    @FindBy(xpath = "(//form[@id='adminForm']//table)[2]")
    private ExtendedWebElement table;

    @FindBy(xpath = "//b[text()='Settings saved successfully.']")
    private ExtendedWebElement successMessage;

    public AdminPage(WebDriver driver) {
        super(driver, PageTitles.ADMIN_PAGE_TITLE);
    }

    private static final String INITIAL_BALANCE = "50000";
    private static final String MINIMAL_BALANCE = "50";
    private static final String THRESHOLD = "10";

    @Override
    public boolean isPageOpened() {
        return getTitleText().equals(PageTitles.ADMIN_PAGE_TITLE);
    }

    public String getSuccessMessage() {
       return successMessage.getText();
    }

    public void clickSubmitButton() {
        submitButton.click();
    }

    public void cleanDatabase() {
        cleanDatabaseBtn.click();
    }

    public boolean setRunningStatus() {
        try {
            if (shutdownBtn.isPresent()) {
                return false;
            }
            if (startupBtn.isPresent()) {
                startupBtn.click();
                return true;
            }
        } catch (Exception e) {
            LOGGER.warn("Error while setting running status: " + e.getMessage());
        }
        return false;
    }

    public void setBalance() {
        clearField(initBalance);
        initBalance.type(INITIAL_BALANCE);
        clearField(minBalance);
        minBalance.type(MINIMAL_BALANCE);
    }

    public void setThreshold() {
        clearField(thresholdField);
        thresholdField.type(THRESHOLD);
    }

    public void setLoanProcessor() {
        loanProcessorField.click();
    }

    public LandingPage openLandingPage() {
        homeBtn.click();
        LandingPage landingPage  = new LandingPage(getDriver());
        landingPage.isPageOpened(15);
        return landingPage;
    }
}








