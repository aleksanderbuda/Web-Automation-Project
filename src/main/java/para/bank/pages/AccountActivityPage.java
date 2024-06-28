package para.bank.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import para.bank.components.MonthValues;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class AccountActivityPage extends AbstractParaBankPage {

    @FindBy(xpath = "//td[text()='Account Type:']/following-sibling::td")
    private ExtendedWebElement accountType;

    @FindBy(xpath = "//td[text()='Account Number:']/following-sibling::td")
    private ExtendedWebElement accountNumber;

    @FindBy(xpath = "//select[@id='month']")
    private ExtendedWebElement monthDropdown;

    @FindBy(xpath = "//select[@id='month']//preceding-sibling::*")
    private List<ExtendedWebElement> monthList;

    @FindBy(xpath = "//input[@type='submit']")
    private ExtendedWebElement submitButton;

    @FindBy(xpath = "//b[text()='No transactions found.']")
    private ExtendedWebElement noTransactionsFoundText;

    @FindBy(xpath = "//table[@id='transactionTable']")
    private ExtendedWebElement transactionTable;

    @FindBy(xpath = "(//table[@id='transactionTable']//a)[1]")
    private ExtendedWebElement tableFirstTransaction;

    @FindBy(xpath = "(//table[@id='transactionTable']//a)[2]")
    private ExtendedWebElement tableSecondTransaction;

    public AccountActivityPage(WebDriver driver) {
        super(driver, PageTitles.ACCOUNT_ACTIVITY_PAGE_TITLE);
    }

    @Override
    public boolean isPageOpened() {
        return getTitleText().equals(PageTitles.ACCOUNT_ACTIVITY_PAGE_TITLE) && getCurrentUrl().contains(Urls.ACCOUNT_ACTIVITY_URL);
    }

    @Override
    public String getTitleText() {
        return getTitle();
    }

    public String getAccountType() {
        return accountType.getText();
    }

    public boolean isAccountAnExpectedType(String type) {
        return accountType.getText().equals(type);
    }

    public String getAccountNumber() {
        return accountNumber.getText();
    }

    public void openMonthDropdown() {
        monthDropdown.click();
    }

    public void clickSubmitButton() {
        submitButton.click();
    }

    public void clickTableFirstTransaction() {
        tableFirstTransaction.click();
    }

    public TransactionDetailsPage clickTableSecondTransaction(){
        tableSecondTransaction.click();
        TransactionDetailsPage transactionDetailsPage = new TransactionDetailsPage(getDriver());
        transactionDetailsPage.isPageOpened(15);
        return transactionDetailsPage;
    }

    public boolean isTransactionTablePresent() {
        return transactionTable.isElementPresent(1);
    }

    public boolean isNoTransactionsFoundTextPresent() {
        return noTransactionsFoundText.isElementPresent(1);
    }

    public void selectMonth(String month) {
        List<ExtendedWebElement> expandedMonthDropdown = getMonthList();
        ExtendedWebElement targetElement = expandedMonthDropdown.stream()
                .filter(option -> option.getText().equals(month))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Desired month isn't found: " + month));
        targetElement.click();
        }

    public List<String> getAllMonthValues() {
        return getMonthList().stream()
                .map(ExtendedWebElement::getText)
                .collect(Collectors.toList());
    }

    public List<String> getEnumMonthValues() {
        return Arrays.stream(MonthValues.values())
                .map(MonthValues::getMonthValue)
                .collect(Collectors.toList());
    }

    public void fillMonthValue(String month) {
        selectMonth(month);
    }
}
