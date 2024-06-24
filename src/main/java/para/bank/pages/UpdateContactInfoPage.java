package para.bank.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

@Getter
public class UpdateContactInfoPage extends AbstractParaBankPage {

    @FindBy(xpath = "//input[@name='customer.firstName']")
    private ExtendedWebElement firstNameField;

    @FindBy(xpath = "//input[@name='customer.lastName']")
    private ExtendedWebElement lastNameField;

    @FindBy(xpath = "//input[@name='customer.address.street']")
    private ExtendedWebElement addressField;

    @FindBy(xpath = "//input[@name='customer.address.city']")
    private ExtendedWebElement cityField;

    @FindBy(xpath = "//input[@name='customer.address.state']")
    private ExtendedWebElement stateField;

    @FindBy(xpath = "//input[@name='customer.address.zipCode']")
    private ExtendedWebElement zipCodeField;

    @FindBy(xpath = "//input[@name='customer.phoneNumber']")
    private ExtendedWebElement phoneField;

    @FindBy(xpath = "//input[@type='button']")
    private ExtendedWebElement submitButton;

    @FindBy(xpath = "//a[@href='overview.htm']")
    private ExtendedWebElement accountsOverviewPage;

    @FindBy(xpath = "//h1[text()='Profile Updated']")
    private ExtendedWebElement profileUpdatedTitle;

    public UpdateContactInfoPage(WebDriver driver) {
        super(driver, PageTitles.UPDATE_PROFILE_PAGE_TITLE);
    }

    @Override
    public String getTitleText() {
        return getTitle();
    }

    @Override
    public boolean isPageOpened() {
        return getTitleText().equals(PageTitles.UPDATE_PROFILE_PAGE_TITLE) && getCurrentUrl().contains(Urls.UPDATE_CONTACT_INFO_URL);
    }

    public AccountsOverviewPage openAccountsOverviewPage() {
        accountsOverviewPage.click();
        AccountsOverviewPage accountsOverviewPage = new AccountsOverviewPage(getDriver());
        accountsOverviewPage.isPageOpened(15);
        return accountsOverviewPage;
    }

    public String getProfileUpdatedTitle() {
       return profileUpdatedTitle.getText();
    }
}
