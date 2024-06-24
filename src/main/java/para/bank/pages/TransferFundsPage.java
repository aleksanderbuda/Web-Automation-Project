package para.bank.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class TransferFundsPage extends AbstractParaBankPage {

    @FindBy(xpath = "//a[@href='overview.htm']")
    private ExtendedWebElement accountOverviewButton;

    @FindBy(xpath = "//a[@href='transfer.htm']")
    private ExtendedWebElement transferFundsLinkButton;

    @FindBy(xpath = "//b[text()='Amount:']/following-sibling::input")
    private ExtendedWebElement transferAmountField;

    @FindBy(xpath = "//form[@id='transferForm']//select[1]")
    private ExtendedWebElement firstAccountDropdown;

    @FindBy(xpath = "//form[@id='transferForm']//select[2]")
    private ExtendedWebElement secondAccountDropdown;

    @FindBy(xpath = "//form[@id='transferForm']//select[2]//following-sibling::*")
    private ExtendedWebElement getSecondAccountDropdownSecondOption;

    @FindBy(xpath = "(//form[@id='transferForm']//input)[2]")
    private ExtendedWebElement transferButton;

    @Getter
    @FindBy(xpath = "//h1[text()='Transfer Complete!']")
    private ExtendedWebElement transferCompleted;

    TransferFundsPage(WebDriver driver) {
        super(driver, PageTitles.TRANSFER_FUNDS_PAGE_TITLE);
    }

    @Override
    public boolean isPageOpened() {
        return getTitleText().equals(PageTitles.TRANSFER_FUNDS_PAGE_TITLE) && getCurrentUrl().contains(Urls.TRANSFER_FUNDS_URL);
    }

    @Override
    public String getTitleText() {
        return getTitle();
    }

    public void clickSecondAccountDropdownSecondOption() {
        getSecondAccountDropdownSecondOption.click();
    }

    public String getTransferCompletedText() {
        return transferCompleted.getText();
    }

    public void fillAmount(String amount) {
        transferAmountField.type(amount);
    }

    public void clickTransferButton() {
        transferButton.click();
    }

    public AccountsOverviewPage openAccountsOverviewPage(){
        accountOverviewButton.click();
        AccountsOverviewPage accountsOverviewPage = new AccountsOverviewPage(getDriver());
        accountsOverviewPage.isPageOpened(15);
        return accountsOverviewPage;
    }
}






