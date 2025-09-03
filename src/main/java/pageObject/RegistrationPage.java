package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage {

    private final WebDriver driver;

    private final By nameInput = By.xpath("//label[text()='Имя']/following-sibling::input");
    private final By emailInput = By.xpath("//label[text()='Email']/following-sibling::input");
    private final By passwordInput = By.xpath("//label[text()='Пароль']/following-sibling::input");
    private final By registerButton = By.xpath("//button[text()='Зарегистрироваться']");
    private final By errorMessage = By.xpath("//p[contains(@class, 'input__error')]");
    private final By passwordError = By.xpath("//p[contains(text(), 'Некорректный пароль')]");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    public RegistrationPage open() {
        driver.get("https://stellarburgers.nomoreparties.site/register");
        return this;
    }

    public RegistrationPage enterName(String name) {
        driver.findElement(nameInput).sendKeys(name);
        return this;
    }

    public RegistrationPage enterEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
        return this;
    }

    public RegistrationPage enterPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
        return this;
    }

    public void clickRegister() {
        driver.findElement(registerButton).click();
    }

    public boolean isErrorMessageDisplayed() {
        return driver.findElement(errorMessage).isDisplayed();
    }

    public boolean isPasswordErrorDisplayed() {
        return driver.findElement(passwordError).isDisplayed();
    }

    public String getPasswordError() {
        return driver.findElement(passwordError).getText();
    }

    public void waitForPageLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(registerButton));
    }
}