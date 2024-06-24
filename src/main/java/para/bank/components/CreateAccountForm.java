package para.bank.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import lombok.Getter;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@Getter
public class CreateAccountForm extends AbstractUIObject {

        @FindBy(xpath = ".//input[@name='customer.firstName']")
        private ExtendedWebElement firstNameField;

        @FindBy(xpath = ".//input[@name='customer.firstName']/following::span[1]")
        private ExtendedWebElement firstNameFieldError;

        @FindBy(xpath = ".//input[@name='customer.lastName']")
        private ExtendedWebElement lastNameField;

        @FindBy(xpath = ".//input[@name='customer.lastName']/following::span[1]")
        private ExtendedWebElement lastNameFieldError;

        @FindBy(xpath = ".//input[@name='customer.address.street']")
        private ExtendedWebElement addressField;

        @FindBy(xpath = ".//input[@name='customer.address.street']/following::span[1]")
        private ExtendedWebElement addressFieldError;

        @FindBy(xpath = ".//input[@name='customer.address.city']")
        private ExtendedWebElement cityField;

        @FindBy(xpath = ".//input[@name='customer.address.city']/following::span[1]")
        private ExtendedWebElement cityFieldError;

        @FindBy(xpath = ".//input[@name='customer.address.state']")
        private ExtendedWebElement stateField;

        @FindBy(xpath = ".//input[@name='customer.address.state']/following::span[1]")
        private ExtendedWebElement stateFieldError;

        @FindBy(xpath = ".//input[@name='customer.address.zipCode']")
        private ExtendedWebElement zipCodeField;

        @FindBy(xpath = ".//input[@name='customer.address.zipCode']/following::span[1]")
        private ExtendedWebElement zipcodeFieldError;

        @FindBy(xpath = ".//input[@name='customer.phoneNumber']")
        private ExtendedWebElement phoneField;

        @FindBy(xpath = ".//input[@name='customer.phoneNumber']/following::span[1]")
        private ExtendedWebElement phoneFieldError;

        @FindBy(xpath = ".//input[@name='customer.ssn']")
        private ExtendedWebElement ssnField;

        @FindBy(xpath = ".//input[@name='customer.ssn']/following::span[1]")
        private ExtendedWebElement ssnFieldError;

        @FindBy(xpath = ".//input[@name='customer.username']")
        private ExtendedWebElement usernameField;

        @FindBy(xpath = ".//input[@name='customer.username']/following::span[1]")
        private ExtendedWebElement usernameFieldError;

        @FindBy(xpath = ".//input[@name='customer.password']")
        private ExtendedWebElement passwordField;

        @FindBy(xpath = ".//input[@name='customer.password']/following::span[1]")
        private ExtendedWebElement passwordFieldError;

        @FindBy(xpath = ".//input[@name='repeatedPassword']")
        private ExtendedWebElement confirmPasswordField;

        @FindBy(xpath = ".//input[@name='repeatedPassword']/following::span[1]")
        private ExtendedWebElement confirmPasswordFieldError;

        @FindBy(xpath = ".//button[@type = 'submit']")
        private ExtendedWebElement createAccountBtn;

        @FindBy(xpath = "//table[@class='form2']//span")
        private List<ExtendedWebElement> errorTextElements;

        public CreateAccountForm(WebDriver driver, SearchContext searchContext) {
            super(driver, searchContext);
        }
    }


