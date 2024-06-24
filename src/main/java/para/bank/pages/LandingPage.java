package para.bank.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import para.bank.auth.CreateAccountPage;

public class LandingPage extends AbstractParaBankPage {

    @FindBy(xpath = "//title[1]")
    private ExtendedWebElement title;

    @FindBy(xpath = "//input[@name='username']")
    private ExtendedWebElement usernameField;

    @FindBy(xpath = "//input[@name='password']")
    private ExtendedWebElement passwordField;

    @Getter
    @FindBy(xpath = "//input[@value='Log In']")
    private ExtendedWebElement logInButton;

    @FindBy(xpath = "//a[@href='register.htm']")
    private ExtendedWebElement registerLinkButton;

    @Getter
    @FindBy(xpath = "(//a[@href='about.htm'])[3]")
    private ExtendedWebElement aboutLink;

    @FindBy(xpath = "(//a[@href='admin.htm'])[2]")
    private ExtendedWebElement openAdminPage;

    public LandingPage(WebDriver driver) {
        super(driver, PageTitles.LANDING_PAGE_TITLE);
    }

    @Override
    public String getTitleText() {
        return getTitle();
    }

    @Override
    public boolean isPageOpened() {
        return getTitleText().equals(PageTitles.LANDING_PAGE_TITLE) && getCurrentUrl().contains(Urls.LANDING_PAGE_URL);
    }
    @Override
    public boolean isPageOpened(long timeout) {
        return title.isElementPresent(timeout) && registerLinkButton.isElementPresent(1);
    }

    public void typeUsername(String username) {
        usernameField.type(username);
    }

    public void typePassword(String pass) {
        passwordField.type(pass);
    }

    public AdminPage openAdminPage() {
        openAdminPage.click();
        AdminPage adminPage = new AdminPage(getDriver());
        adminPage.isPageOpened(15);
        return adminPage;
    }

    public AccountsOverviewPage clickLogInButton() {
        logInButton.click();
        AccountsOverviewPage accountsOverviewPage = new AccountsOverviewPage(getDriver());
        accountsOverviewPage.isPageOpened(15);
        return accountsOverviewPage;
    }

    public CreateAccountPage clickRegisterAccountButton() {
        registerLinkButton.click();
        CreateAccountPage createAccountPage = new CreateAccountPage(getDriver());
        createAccountPage.isPageOpened(15);
        return createAccountPage;
    }

    public AboutPage openAboutPage() {
        aboutLink.click();
        AboutPage aboutPage = new AboutPage(getDriver());
        aboutPage.isPageOpened(15);
        return aboutPage;
    }

}

