package para.bank.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

@Getter
public class AboutPage extends AbstractParaBankPage {

    @FindBy(xpath = "//div[@id='leftPanel']/following-sibling::div[1]//a")
    private ExtendedWebElement parasoftLink;

    public AboutPage(WebDriver driver) {
        super(driver, PageTitles.ABOUT_PAGE_TITLE);
    }

    @Override
    public boolean isPageOpened() {
        return getTitleText().equals(PageTitles.ABOUT_PAGE_TITLE) && getCurrentUrl().contains(Urls.ABOUT_URL);
    }

    @Override
    public String getTitleText() {
        return getTitle();
    }
}
