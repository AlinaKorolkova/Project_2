package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ConstructorPage {

    private final WebDriver driver;

    private final By bunsSection = By.xpath("//span[text()='Булки']/parent::div");
    private final By saucesSection = By.xpath("//span[text()='Соусы']/parent::div");
    private final By fillingsSection = By.xpath("//span[text()='Начинки']/parent::div");
    private final By selectedSection = By.xpath("//div[contains(@class, 'tab_tab_type_current')]");
    private final By bunsList = By.xpath("//h2[text()='Булки']/following-sibling::ul");
    private final By saucesList = By.xpath("//h2[text()='Соусы']/following-sibling::ul");
    private final By fillingsList = By.xpath("//h2[text()='Начинки']/following-sibling::ul");

    public ConstructorPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickBunsSection() {
        driver.findElement(bunsSection).click();
    }

    public void clickSaucesSection() {
        driver.findElement(saucesSection).click();
    }

    public void clickFillingsSection() {
        driver.findElement(fillingsSection).click();
    }

    public String getSelectedSection() {
        return driver.findElement(selectedSection).getText();
    }

    public boolean isBunsListDisplayed() {
        return driver.findElement(bunsList).isDisplayed();
    }

    public boolean isSaucesListDisplayed() {
        return driver.findElement(saucesList).isDisplayed();
    }

    public boolean isFillingsListDisplayed() {
        return driver.findElement(fillingsList).isDisplayed();
    }

    public void waitForPageLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(bunsSection));
    }
}
