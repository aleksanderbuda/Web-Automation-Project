package para.bank.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import para.bank.components.BillPayForm;

@Getter
public class BillPayPage extends AbstractParaBankPage {

    @FindBy(xpath = "//table[@class='form2']")
    private BillPayForm billPayForm;

    @FindBy(xpath = "//a[@href='overview.htm']")
    private ExtendedWebElement accountsOverviewPage;

    @FindBy(xpath = "//span[text()='Please enter a valid amount.']/following::input")
    private ExtendedWebElement submitButton;

    BillPayPage(WebDriver driver) {
        super(driver, PageTitles.BILL_PAYMENT_PAGE_TITLE);
    }

    @Override
    public boolean isPageOpened() {
        return getTitleText().equals(PageTitles.BILL_PAYMENT_PAGE_TITLE) && getCurrentUrl().contains(Urls.BILL_PAYMENT_PAGE_URL);
    }

    @Override
    public String getTitleText() {
        return getTitle();
    }

    public AccountsOverviewPage openAccountsOverviewPage(){
        accountsOverviewPage.click();
        AccountsOverviewPage openAccountsOverviewPage = new AccountsOverviewPage(getDriver());
        openAccountsOverviewPage.isPageOpened(15);
        return openAccountsOverviewPage;
    }
    public String getAccountNumber() {
        return billPayForm.getFromAccountFirstNumber().getText();
    }

    public void clickSendPaymentButton() {
        submitButton.click();
    }

    public void fillPayeeName(String name) {
        billPayForm.getBillPayeeNameField().type(name);
    }

    public void fillAddress(String address) {
        billPayForm.getBillAddressField().type(address);
    }

    public void fillCity(String city) {
        billPayForm.getBillCityField().type(city);
    }

    public void fillState(String state) {
        billPayForm.getBillStateField().type(state);
    }

    public void fillZipCode(String zip) {
        billPayForm.getBillZipCodeField().type(zip);
    }

    public void fillPhoneNumber(String phone) {
        billPayForm.getBillPhoneField().type(phone);
    }

    public void accountNum(String num) {
        billPayForm.getBillAccountNumberField().type(num);
    }
    public void verifyAccountNum(String num) {
        billPayForm.getBillVerifyAccountNumberField().type(num);
    }

    public void fillAmount(String amount) {
        billPayForm.getBillAmountField().type(amount);
    }
}

