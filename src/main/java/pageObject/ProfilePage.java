package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePage {

    private final WebDriver driver;

    private final By nameInput = By.xpath("//label[text()='Имя']/following-sibling::input");
    private final By logoutButton = By.xpath("//*[@id=\"root\"]/div/main/div/nav/ul/li[3]/button");
    private final By constructorLink = By.xpath("//p[contains(text(), 'Конструктор')]");
    private final By logo = By.xpath("//div[contains(@class, 'AppHeader_header__logo')]");

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickLogout() {
        driver.findElement(logoutButton).click();
    }

    public void clickConstructor() {
        driver.findElement(constructorLink).click();
    }

    public void clickLogo() {
        driver.findElement(logo).click();
    }

    public boolean isProfilePageDisplayed() {
        return driver.findElement(nameInput).isDisplayed();
    }

    public void waitForPageLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(nameInput));
    }
}