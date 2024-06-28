package para.bank.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import para.bank.constants.Constants;

@Getter
public class RequestLoanPage extends AbstractParaBankPage {

    @FindBy(xpath = "//a[@href='openaccount.htm']")
    private ExtendedWebElement openAccountsOverviewPage;

    @FindBy(xpath = "//a[@href='requestloan.htm']")
    private ExtendedWebElement openRequestLoanPage;

    @FindBy(xpath = "//input[@id='amount']")
    private ExtendedWebElement loanAmountField;

    @FindBy(xpath = "//input[@id='downPayment']")
    private ExtendedWebElement downPaymentField;

    @FindBy(xpath = "//table[@class='form2']//select[1]")
    private ExtendedWebElement accountDropdown;

    @FindBy(xpath = "//input[@value='Apply Now']")
    private ExtendedWebElement submitButton;

    @FindBy(xpath = "//div[@id='loanRequestDenied']//p[1]")
    private ExtendedWebElement errorMessage;

    @FindBy(xpath = "//div[@id='loanRequestApproved']/p[1]")
    private ExtendedWebElement successMessage;

    @FindBy(xpath = "(//td[@align='right']/following-sibling::td)[9]")
    private ExtendedWebElement status;

    @FindBy(xpath = "//table[@class='form']")
    private ExtendedWebElement table;

    @FindBy(xpath = "//div[@id='loanRequestApproved']//a[1]")
    private ExtendedWebElement newAccountNumber;

    RequestLoanPage(WebDriver driver) {
        super(driver, Constants.PageTitles.ADMIN_PAGE_TITLE);
    }

    @Override
    public boolean isPageOpened() {
        return getTitleText().equals(PageTitles.REQUEST_LOAN_PAGE_TITLE) && getCurrentUrl().contains(Urls.REQUEST_LOAN_URL);
    }

    @Override
    public String getTitleText() {
        return getTitle();
    }

    public void fillLoanAmount(String amount) {
        loanAmountField.type(amount);
    }

    public void fillDownPayment(String amount) {
        downPaymentField.type(amount);
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }

    public String getSuccessMessage() {
        return successMessage.getText();
    }

    public String getStatus() {
        return status.getText();
    }

    public void clickSubmitButton() {
        submitButton.click();
    }

    public String getNewAccountNumber() {
        return newAccountNumber.getText();
    }

    public AccountActivityPage clickNewAccNumber(){
        newAccountNumber.click();
        AccountActivityPage accountActivityPage = new AccountActivityPage(getDriver());
        accountActivityPage.isPageOpened(15);
        return accountActivityPage;
    }
}
