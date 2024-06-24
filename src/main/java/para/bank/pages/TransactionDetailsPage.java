package para.bank.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

@Getter
public class TransactionDetailsPage extends AbstractParaBankPage {

    @FindBy(xpath = "//td[b[text()='Description:']]/following-sibling::td[1]")
    private ExtendedWebElement description;

    @FindBy(xpath = "//td[b[text()='Amount:']]/following-sibling::td[1]")
    private ExtendedWebElement amount;

    @FindBy(xpath = "//td[b[text()='Transaction ID:']]/following-sibling::td[1]")
    private ExtendedWebElement id;

    @FindBy(xpath = "//a[@href='overview.htm']")
    private ExtendedWebElement accountOverviewButton;

    @FindBy(xpath = "//a[@href='findtrans.htm']")
    private ExtendedWebElement findTransactionsButton;

    @FindBy(xpath = "//table")
    private ExtendedWebElement transactionResultsTable;

    @FindBy(xpath = "(//tbody[@id='transactionBody']//a)[1]")
    private ExtendedWebElement clickFirstTransaction;

    @FindBy(xpath = "(//tbody[@id='transactionBody']//a)[2]")
    private ExtendedWebElement clickSecondTransaction;


    public TransactionDetailsPage(WebDriver driver) {
        super(driver, PageTitles.TRANSACTION_DETAILS_PAGE_TITLE);
    }

    @Override
    public boolean isPageOpened() {
        return getTitleText().equals(PageTitles.TRANSACTION_DETAILS_PAGE_TITLE) && getCurrentUrl().contains(Urls.TRANSACTION_DETAILS_URL);
    }

    @Override
    public String getTitleText() {
        return getTitle();
    }

    public String getId() {
        return id.getText();
    }

    public void clickSecondTransaction() {
        clickSecondTransaction.click();
    }

    public void clickFirstTransaction() {
        clickFirstTransaction.click();
    }


    public String getDescription() {
        return description.getText();
    }

    public String getTransferredAmount() {
        return amount.getText();
    }

    public FindTransactionsPage openFindTransactionsPage(){
        findTransactionsButton.click();
        FindTransactionsPage findTransactionsPage = new FindTransactionsPage(getDriver());
        findTransactionsPage.isPageOpened(15);
        return findTransactionsPage;
    }
}

