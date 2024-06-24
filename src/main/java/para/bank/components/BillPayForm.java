package para.bank.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import lombok.Getter;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;


public class BillPayForm extends AbstractUIObject {

    @FindBy(xpath = "//title[1]")
    private ExtendedWebElement title;

    @Getter
    @FindBy(xpath = ".//input[@name='payee.name']")
    private ExtendedWebElement billPayeeNameField;

    @Getter
    @FindBy(xpath = ".//input[@name='payee.address.street']")
    private ExtendedWebElement billAddressField;

    @Getter
    @FindBy(xpath = ".//input[@name='payee.address.city']")
    private ExtendedWebElement billCityField;

    @Getter
    @FindBy(xpath = ".//input[@name='payee.address.state']")
    private ExtendedWebElement billStateField;

    @Getter
    @FindBy(xpath = ".//input[@name='payee.address.zipCode']")
    private ExtendedWebElement billZipCodeField;

    @Getter
    @FindBy(xpath = ".//input[@name='payee.phoneNumber']")
    private ExtendedWebElement billPhoneField;

    @Getter
    @FindBy(xpath = ".//input[@name='payee.accountNumber']")
    private ExtendedWebElement billAccountNumberField;

    @Getter
    @FindBy(xpath = ".//input[@name='verifyAccount']")
    private ExtendedWebElement billVerifyAccountNumberField;

    @Getter
    @FindBy(xpath = ".//input[@name='amount']")
    private ExtendedWebElement billAmountField;

    @Getter
    @FindBy(xpath = "//select[@name='fromAccountId']")
    private ExtendedWebElement billAccountSelectDropdown;

    @Getter
    @FindBy(xpath = "//select[@name='fromAccountId']/option")
    private ExtendedWebElement fromAccountFirstNumber;

    public BillPayForm(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public String getTitleText() {
        return title.getText();
    }
}
