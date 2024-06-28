package para.bank.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import para.bank.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
@Slf4j
    public class AbstractParaBankPage extends AbstractPage implements Constants {
        private final String titleText;

    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractParaBankPage.class);

    public AbstractParaBankPage(WebDriver driver, String title) {
            super(driver);
            this.titleText = title;
        }

        @FindBy(xpath = "//title[1]")
        private ExtendedWebElement title;

        public String getTitleText() {
            return title.getText();
        }
    @Override
        public boolean isPageOpened() {
        return this.titleText.equals(title.getText());
    }

        public long getContentPageHeight() {
            JavascriptExecutor js = (JavascriptExecutor) getDriver();
            return (long) js.executeScript("return document.documentElement.scrollHeight");
        }

        public void clearField(ExtendedWebElement field) {
            if (!field.getAttribute("value").isEmpty()) {
                tripleClick(field.getElement());
                field.getElement().sendKeys(Keys.DELETE);
                field.getElement().sendKeys(Keys.TAB); //for updating element's tree
                if (!field.getText().isEmpty() || !field.getAttribute("value").isEmpty()) {
                    log.info("The whole text wasn't removed, will try to use Cmd+A hot keys");
                    field.click();
                    field.getElement().sendKeys(Keys.COMMAND + "A");
                    field.getElement().sendKeys(Keys.COMMAND + "a");
                    field.getElement().sendKeys(Keys.BACK_SPACE);
                    field.getElement().sendKeys(Keys.TAB); //for updating element's tree
                }
            }
        }

        public void tripleClick(WebElement element) {
            new Actions(getDriver())
                    .moveToElement(element, 0, 0)
                    .click()
                    .click()
                    .click()
                    .perform();
        }
    }

