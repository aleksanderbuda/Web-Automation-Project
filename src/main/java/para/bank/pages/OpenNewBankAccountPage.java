package para.bank.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

@Getter
public class OpenNewBankAccountPage extends AbstractParaBankPage {

    @FindBy(xpath = "//a[@href='openaccount.htm']")
    private ExtendedWebElement openNewBankAccountLinkButton;

    @FindBy(xpath = "//form/select[@id='type']")
    private ExtendedWebElement accountTypeDropdownChecking;

    @FindBy(xpath = "//form/select[@id='type']/option[2]")
    private ExtendedWebElement accountTypeDropdownSavings;

    @FindBy(xpath = "//form/select[@id='fromAccountId']")
    private ExtendedWebElement existingAccountDropdown;

    @FindBy(xpath = "//input[@type='button']")
    private ExtendedWebElement submitButton;

    @FindBy(xpath = "(//div[@id='openAccountForm']//select)[2]/option")
    private ExtendedWebElement firstAccountNumberInDropdown;

    @FindBy(xpath = "//h1[text()='Account Opened!']")
    private ExtendedWebElement accountOpenedTitle;

    @FindBy(xpath = "//b[text()='Your new account number:']/following-sibling::a")
    private ExtendedWebElement newAccountNumber;

    public OpenNewBankAccountPage(WebDriver driver) {
        super(driver, PageTitles.OPEN_NEW_ACCOUNT_PAGE_TITLE);
    }

    @Override
    public boolean isPageOpened() {
        return getTitleText().equals(PageTitles.OPEN_NEW_ACCOUNT_PAGE_TITLE) && getCurrentUrl().contains(Urls.OPEN_NEW_ACCOUNT_PAGE_URL);
    }

    @Override
    public String getTitleText() {
        return getTitle();
    }

    public void clickOpenNewAccountPanelButton(){
        openNewBankAccountLinkButton.click();
    }

    public void pickSavingsTypeAccount() {
        accountTypeDropdownSavings.click();
    }

    public void clickOpenAccountButton(){
        submitButton.click();
}

    public String getNewAccountNumber() {
        return newAccountNumber.getText();
    }

    public boolean isNewAccountNumberPresent() {
        return newAccountNumber.isElementPresent(2);
    }

    public AccountActivityPage clickNewAccountNumber(){
        newAccountNumber.click();
        AccountActivityPage accountActivityPage = new AccountActivityPage(getDriver());
        accountActivityPage.isPageOpened(15);
        return accountActivityPage;
    }

}

