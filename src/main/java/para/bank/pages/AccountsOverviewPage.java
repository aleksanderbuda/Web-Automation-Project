package para.bank.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class AccountsOverviewPage extends AbstractParaBankPage {

    @Getter
    @FindBy(xpath = "//table[@id='accountTable']")
    private AccountsOverviewPage accountsTable;

    @Getter
    @FindBy(xpath = "//b[text()='Total']")
    private ExtendedWebElement total;

    @FindBy(xpath = ".//following-sibling::*//td[2]")
    private ExtendedWebElement firstAccountBalance;

    @FindBy(xpath = ".//following-sibling::*/td[2]")
    private ExtendedWebElement secondAccountBalance;

    @Getter
    @FindBy(xpath = "(//table[@id='accountTable']//a)[1]")
    private ExtendedWebElement firstAccountNumber;

    @Getter
    @FindBy(xpath = "(//table[@id='accountTable']//a)[2]")
    private ExtendedWebElement secondAccountNumber;

    @FindBy(xpath = "//a[@href='overview.htm']")
    private ExtendedWebElement openAccountsOverviewPage;

    @FindBy(xpath = "//a[@href='transfer.htm']")
    private ExtendedWebElement openTransferFundsPage;

    @FindBy(xpath = "//a[@href='billpay.htm']")
    private ExtendedWebElement openBillPayPage;

    @FindBy(xpath = "//a[@href='requestloan.htm']")
    private ExtendedWebElement openRequestLoanPage;

    @FindBy(xpath = "//a[@href='updateprofile.htm']")
    private ExtendedWebElement openUpdateContactInfoPage;

    @FindBy(xpath = "//a[@href='logout.htm']")
    private ExtendedWebElement LogOutButton;

    @FindBy(xpath = "//td[text()='Account Number:']/following-sibling::td")
    private ExtendedWebElement accountNumber;

    public AccountsOverviewPage(WebDriver driver) {
        super(driver, PageTitles.ACCOUNTS_OVERVIEW_PAGE_TITLE);
    }

    @Override
    public boolean isPageOpened() {
        return getTitleText().equals(PageTitles.ACCOUNTS_OVERVIEW_PAGE_TITLE) && getCurrentUrl().contains(Urls.ACCOUNTS_OVERVIEW_URL) && getTotal().isElementPresent(1);
    }

    @Override
    public String getTitleText() {
        return getTitle();
    }

    public String getAccountNumber() {
        return accountNumber.getText();
    }

    public String getFirstAccountBalance() {
        return firstAccountBalance.getText();
    }

    public String getSecondAccountBalance() {
        return secondAccountBalance.getText();
    }

    public void clickAccountsOverviewButton(){
        openAccountsOverviewPage.click();
    }

    public String getFirstAccountNumber() {
        return firstAccountNumber.getText();
    }

    public String getSecondAccountNumber() {
        return secondAccountNumber.getText();
    }


    public AccountActivityPage clickFirstAccountNumber(){
        firstAccountNumber.click();
        AccountActivityPage accountActivityPage = new AccountActivityPage(getDriver());
        accountActivityPage.isPageOpened(15);
        return accountActivityPage;
    }

    public AccountActivityPage clickSecondAccountNumber(){
        secondAccountNumber.click();
        AccountActivityPage accountActivityPage = new AccountActivityPage(getDriver());
        accountActivityPage.isPageOpened(15);
        return accountActivityPage;
    }

    public TransferFundsPage openTransferFundsPage(){
        openTransferFundsPage.click();
        TransferFundsPage transferFundsPage = new TransferFundsPage(getDriver());
        transferFundsPage.isPageOpened(15);
        return transferFundsPage;
    }

    public BillPayPage openBillPayPage(){
        openBillPayPage.click();
        BillPayPage billPayPage = new BillPayPage(getDriver());
        billPayPage.isPageOpened(15);
        return billPayPage;
    }

    public RequestLoanPage openRequestLoanPage(){
        openRequestLoanPage.click();
        RequestLoanPage requestLoanPage = new RequestLoanPage(getDriver());
        requestLoanPage.isPageOpened(15);
        return requestLoanPage;
    }

    public void clickLogOutButton() {
        LogOutButton.click();
    }
}
