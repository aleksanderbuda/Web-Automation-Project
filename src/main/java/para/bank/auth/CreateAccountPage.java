package para.bank.auth;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import lombok.Getter;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import para.bank.components.CreateAccountForm;
import para.bank.pages.AbstractParaBankPage;
import para.bank.pages.AccountsOverviewPage;
import para.bank.pages.OpenNewBankAccountPage;

import java.util.List;

@Getter
public class CreateAccountPage extends AbstractParaBankPage {

    @FindBy(xpath = "//table[@class='form2']//span")
    private CreateAccountForm createAccountFormErrors;

    @FindBy(xpath = "//table[@class='form2']")
    private CreateAccountForm createAccountForm;

    @FindBy(xpath = "//input[@value='Log In']")
    private ExtendedWebElement logInBtn;

    @FindBy(xpath = "//input[@value='Register']")
    private ExtendedWebElement registerButton;

    @FindBy(xpath = "//h1[@class='title']/following-sibling::p[1]")
    private ExtendedWebElement welcomeMessage;

    @FindBy(xpath = "//a[@href='openaccount.htm']")
    private ExtendedWebElement openOpenNewAccountPage;


    @FindBy(xpath = "//a[@href='overview.htm']")
    private ExtendedWebElement openAccountOverviewPage;

    public CreateAccountPage(WebDriver driver) {
        super(driver, PageTitles.CREATE_ACCOUNT_PAGE_TITLE);
    }

    @Override
    public boolean isPageOpened() {
        return getTitleText().equals(PageTitles.CREATE_ACCOUNT_PAGE_TITLE) && getCurrentUrl().contains(Urls.CREATE_ACCOUNT_URL);
    }

    @Override
    public String getTitleText() {
        return getTitle();
    }

    public String getWelcomeMessage() {
        return welcomeMessage.getText();
    }

    public void clickCreateAccountBtn() {
        registerButton.click();
    }

    public OpenNewBankAccountPage openOpenNewAccountPage() {
        openOpenNewAccountPage.click();
        OpenNewBankAccountPage openNewBankAccountPage = new OpenNewBankAccountPage(getDriver());
        openNewBankAccountPage.isPageOpened(15);
        return openNewBankAccountPage;
    }

    public AccountsOverviewPage openAccountsOverviewPage() {
        openAccountOverviewPage.click();
        AccountsOverviewPage accountsOverviewPage = new AccountsOverviewPage(getDriver());
        accountsOverviewPage.isPageOpened(15);
        return accountsOverviewPage;
    }

    public void createAnAccount() {
        String firstName = createRandomUsername();
        String lastName = createRandomUsername();
        String address = StringUtils.capitalize(RandomStringUtils.randomAlphabetic(6).toLowerCase());
        String city = StringUtils.capitalize(RandomStringUtils.randomAlphabetic(7).toLowerCase());
        String state = RandomStringUtils.randomAlphabetic(2).toUpperCase();
        String zipCode = RandomStringUtils.randomNumeric(6);
        String phone = RandomStringUtils.randomNumeric(6);
        String ssn = RandomStringUtils.randomNumeric(6);
        String username = createRandomUsername();
        String password = createRandomStrongPassword();

        fillName(firstName);
        fillLastName(lastName);
        fillAddress(address);
        fillCity(city);
        fillState(state);
        fillZipCode(zipCode);
        fillPhoneNumber(phone);
        fillSecurityNum(ssn);
        fillUsername(username);
        fillPassword(password);
        fillConfirmPassword(password);

        clickCreateAccountBtn();
        pause(3);
    }
    public static String createRandomUsername() {
        return "user_" + RandomStringUtils.randomAlphanumeric(8).toLowerCase();
    }

    public String createRandomStrongPassword() {
        return createRandomStrongPassword(10);
    }

    public String createRandomStrongPassword(int size) {
        return RandomStringUtils.randomAlphabetic(size - 4) +
                RandomStringUtils.randomAlphabetic(1).toUpperCase() +
                RandomStringUtils.randomAlphabetic(1).toLowerCase() +
                RandomStringUtils.randomNumeric(1);
    }

        public void fillName(String name) {
            createAccountForm.getFirstNameField().type(name);
        }

        public void fillLastName(String lastName) {
            createAccountForm.getLastNameField().type(lastName);
        }

        public void fillAddress(String address) {
            createAccountForm.getAddressField().type(address);
        }

        public void fillCity(String city) {
            createAccountForm.getCityField().type(city);
        }
        public void fillState(String state) {
            createAccountForm.getStateField().type(state);
        }

        public void fillZipCode(String postalCode) {
            createAccountForm.getZipCodeField().type(postalCode);
        }

        public void fillPhoneNumber(String phone) {
            createAccountForm.getPhoneField().type(phone);
        }

        public void fillSecurityNum(String ssn) {
            createAccountForm.getSsnField().type(ssn);
        }

        public void fillUsername(String username) {
            createAccountForm.getUsernameField().type(username);
        }

        public void fillPassword(String password) {
            createAccountForm.getPasswordField().type(password);
        }

        public void fillConfirmPassword(String password) {
            createAccountForm.getConfirmPasswordField().type(password);
        }

        public String getFirstNameErrorMessage() {
            return createAccountForm.getFirstNameFieldError().getText();
        }

        public String getLastNameErrorMessage() {
            return createAccountForm.getLastNameFieldError().getText();
        }

        public String getAddressErrorMessage() {
            return createAccountForm.getAddressFieldError().getText();
        }

        public String getCityErrorMessage() {
            return createAccountForm.getCityFieldError().getText();
        }

        public String getStateErrorMessage() {
            return createAccountForm.getStateFieldError().getText();
        }

        public String getZipCodeErrorMessage() {
            return createAccountForm.getZipcodeFieldError().getText();
        }

        public String getPhoneErrorMessage() {
            return createAccountForm.getPhoneFieldError().getText();
        }

        public String getSsnErrorMessage() {
            return createAccountForm.getSsnFieldError().getText();
        }

        public String getUsernameErrorMessage() {
            return createAccountForm.getUsernameFieldError().getText();
        }

        public String getPasswordErrorMessage() {
            return createAccountForm.getPasswordFieldError().getText();
        }

        public String getConfirmPasswordErrorMessage() {
            return createAccountForm.getConfirmPasswordFieldError().getText();
        }

        public boolean isFirstNameErrorPresent() {
            return createAccountForm.getFirstNameFieldError().isElementPresent(1);
        }

        public boolean isLastNameErrorPresent() {
            return createAccountForm.getLastNameFieldError().isElementPresent(1);
        }

        public boolean isAddressErrorPresent() {
            return createAccountForm.getAddressFieldError().isElementPresent(1);
        }

        public boolean isCityErrorPresent() {
            return createAccountForm.getCityFieldError().isElementPresent(1);
        }

        public boolean isStateErrorPresent() {
            return createAccountForm.getStateFieldError().isElementPresent(1);
        }

        public boolean isZipCodeErrorPresent() { return createAccountForm.getZipcodeFieldError().isElementPresent(1);}

        public boolean isPhoneErrorPresent() {
            return createAccountForm.getPhoneFieldError().isElementPresent(1);
        }

        public boolean isSsnErrorPresent() {
            return createAccountForm.getSsnFieldError().isElementPresent(1);
        }

        public boolean isUsernameErrorPresent() {
            return createAccountForm.getUsernameFieldError().isElementPresent(1);
        }

        public boolean isPasswordErrorPresent() {
            return createAccountForm.getPasswordFieldError().isElementPresent(1);
        }

        public boolean isConfirmPasswordErrorPresent() {
            return createAccountForm.getConfirmPasswordFieldError().isElementPresent(1);
        }

        public List<ExtendedWebElement> getErrorTextElements() {
            return createAccountFormErrors.getErrorTextElements();
        }
    }



