package extensions;

import lombok.Getter;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.WebDriver;

import static driver.WebDriverCreator.createWebDriver;

@Getter
public class BrowserExtension implements BeforeEachCallback, AfterEachCallback, TestWatcher {

    private WebDriver driver;

    @Override
    public void beforeEach(ExtensionContext context) {
        driver = createWebDriver();
    }

    @Override
    public void afterEach(ExtensionContext context) {
        driver.close();
    }

}
