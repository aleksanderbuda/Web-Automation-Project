package para.bank.cucumber.steps.hooks;

import com.zebrunner.carina.core.AbstractTest;
import io.cucumber.testng.TestNGCucumberRunner;
import lombok.Getter;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;


@Getter
public class Hooks extends AbstractTest {
    private TestNGCucumberRunner testNGCucumberRunner;

    @AfterClass
    public void tearDownClass(ITestContext context) {
        this.testNGCucumberRunner.finish();
    }

}
