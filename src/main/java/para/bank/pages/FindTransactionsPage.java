package para.bank.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class FindTransactionsPage extends AbstractParaBankPage {

    @FindBy(xpath = "//a[@href='findtrans.htm']")
    private ExtendedWebElement findTransactionsLinkButton;

    @FindBy(xpath = "//table[@id='accountTable']//a[1]")
    private ExtendedWebElement firstAccountNum;

    @FindBy(xpath = "//a[@href='transfer.htm']")
    private ExtendedWebElement transferFundsLinkButton;

    @FindBy(xpath = "//form[@id='transactionForm']//select[1]")
    private ExtendedWebElement accountSelectDropdown;

    @FindBy(xpath = "//form[@id='transactionForm']//option[2]")
    private ExtendedWebElement secondDropdownOption;

    @FindBy(xpath = "//input[@id='transactionId']")
    private ExtendedWebElement findById;

    @FindBy(xpath = "//input[@id='transactionDate']")
    private ExtendedWebElement findByDate;

    @FindBy(xpath = "//input[@id='fromDate']")
    private ExtendedWebElement findByDateRangeFrom;

    @FindBy(xpath = "//input[@id='toDate']")
    private ExtendedWebElement findByDateRangeTo;

    @FindBy(xpath = "//input[@id='amount']")
    private ExtendedWebElement findByAmount;

    @FindBy(xpath = "//button[@id='findById']")
    private ExtendedWebElement findByIdButton;

    @FindBy(xpath = "//button[@id='findByDate']")
    private ExtendedWebElement findByDateButton;

    @FindBy(xpath = "//button[@id='findByDateRange']")
    private ExtendedWebElement findByRangeButton;

    @FindBy(xpath = "//button[@id='findByAmount']")
    private ExtendedWebElement findByAmountButton;

    @FindBy(xpath = "//a[@href='overview.htm']")
    private ExtendedWebElement accountOverviewButton;

    public FindTransactionsPage(WebDriver driver) {
        super(driver, PageTitles.FIND_TRANSACTIONS_PAGE_TITLE);
    }

    @Override
    public boolean isPageOpened() {
        return getTitleText().equals(PageTitles.FIND_TRANSACTIONS_PAGE_TITLE) && getCurrentUrl().contains(Urls.FIND_TRANSACTION_URL);
    }

    @Override
    public String getTitleText() {
        return getTitle();
    }

    public void clickSecondOption() {
        secondDropdownOption.click();
    }

    public void clickFindByIdButton() {
        findByIdButton.click();
    }

    public void clickFindByDateButton() {
        findByDateButton.click();
    }

    public void clickFindByAmountButton() {
        findByAmountButton.click();
    }

    public void fillId(String id) {
        findById.type(id);
    }

    public void fillDateRange(String date) {
        findByDate.type(date);
    }

    public void fillAmount(String amount) {
        findByAmount.type(amount);
    }
}
