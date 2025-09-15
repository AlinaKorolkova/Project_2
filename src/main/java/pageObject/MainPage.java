package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {

    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site/";

    private final WebDriver driver;

    private final By title = By.xpath("//h1[contains(@class, 'text_type_main-large')]");
    private final By profileButton = By.xpath("//a[contains(@href, '/account')]");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public MainPage open() {
        driver.get(BASE_URL);
        return this;
    }

    public String titleText() {
        return driver.findElement(title).getText();
    }

    public void clickProfile() {
        driver.findElement(profileButton).click();
    }

    public void waitForPageLoad() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(title));
    }
}
