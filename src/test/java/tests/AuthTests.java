package tests;

import clients.ApiClient;
import extensions.BrowserExtension;
import generators.UserGenerator;
import model.CreateUserRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pageObject.LoginPage;
import pageObject.MainPage;
import pageObject.ProfilePage;
import pageObject.RegistrationPage;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthTests {

    @RegisterExtension
    public BrowserExtension browserExtension = new BrowserExtension();

    @Test
    @DisplayName("Успешная регистрация пользователя")
    public void successfulRegistration() {
        CreateUserRequest user = UserGenerator.randomUser();

        RegistrationPage registrationPage = new RegistrationPage(browserExtension.getDriver());
        LoginPage loginPage = new LoginPage(browserExtension.getDriver());

        step("Открыть страницу регистрации", () -> registrationPage.open().waitForPageLoad());
        step("Заполнить форму регистрации", () -> {
            registrationPage.enterName(user.getName())
                    .enterEmail(user.getEmail())
                    .enterPassword(user.getPassword());
        });
        step("Нажать кнопку регистрации", registrationPage::clickRegister);
        step("Проверить переход на страницу логина", () -> {
            loginPage.waitForPageLoad();
            assertTrue(loginPage.isLoginButtonDisplayed());
        });
    }

    @Test
    @DisplayName("Регистрация с некорректным паролем (менее 6 символов)")
    public void registrationWithInvalidPassword() {
        CreateUserRequest user = UserGenerator.randomUser().withPassword("12345");

        RegistrationPage registrationPage = new RegistrationPage(browserExtension.getDriver());

        step("Открыть страницу регистрации", () -> registrationPage.open().waitForPageLoad());
        step("Заполнить форму с некорректным паролем и кликнуть на кнопку", () -> {
            registrationPage.enterName(user.getName())
                    .enterEmail(user.getEmail())
                    .enterPassword(user.getPassword())
                    .clickRegister();
        });
        step("Проверить сообщение об ошибке", () -> {
            assertTrue(registrationPage.isPasswordErrorDisplayed());
            assertEquals("Некорректный пароль", registrationPage.getPasswordError());
        });
    }

    @ParameterizedTest
    @CsvSource({
            "test@, Некорректный email",
            "test, Некорректный email"
    })
    @DisplayName("Регистрация с некорректным email")
    public void registrationWithInvalidEmail(String email, String expectedError) {
        RegistrationPage registrationPage = new RegistrationPage(browserExtension.getDriver());

        step("Открыть страницу регистрации", () -> registrationPage.open().waitForPageLoad());
        step("Ввести некорректный email: " + email, () ->
                registrationPage.enterEmail(email)
        );
        step("Проверить сообщение об ошибке", () -> {
            registrationPage.enterName("Test User");
            registrationPage.enterPassword("validPassword123");
            registrationPage.clickRegister();
            assertTrue(registrationPage.isErrorMessageDisplayed());
        });
    }

    @Test
    @DisplayName("Успешный вход в аккаунт")
    public void successfulLogin() {
        CreateUserRequest user = UserGenerator.randomUser();
        ApiClient apiClient = new ApiClient();
        apiClient.users().create(user);

        LoginPage loginPage = new LoginPage(browserExtension.getDriver());
        MainPage mainPage = new MainPage(browserExtension.getDriver());

        step("Открыть страницу входа", () -> loginPage.open().waitForPageLoad());
        step("Заполнить форму входа", () -> {
            loginPage.enterEmail(user.getEmail())
                    .enterPassword(user.getPassword());
        });
        step("Нажать кнопку входа", loginPage::clickLogin);
        step("Проверить переход на главную страницу", () -> {
            mainPage.waitForPageLoad();
            assertEquals("Соберите бургер", mainPage.titleText());
        });

        apiClient.users().delete();
    }

    @Test
    @DisplayName("Вход с неверными учётными данными")
    public void loginWithInvalidCredentials() {
        LoginPage loginPage = new LoginPage(browserExtension.getDriver());

        step("Открыть страницу входа", () -> loginPage.open().waitForPageLoad());
        step("Ввести неверные данные", () -> {
            loginPage.enterEmail("nonexistent@test.com")
                    .enterPassword("wrongpassword");
        });
        step("Нажать кнопку входа", loginPage::clickLogin);
        step("Проверить страницу входа", () -> {
            assertTrue(loginPage.isLoginButtonDisplayed());
        });
    }

    @Test
    @DisplayName("Выход из аккаунта")
    public void logout() {
        CreateUserRequest user = UserGenerator.randomUser();
        ApiClient apiClient = new ApiClient();
        apiClient.users().create(user);

        MainPage mainPage = new MainPage(browserExtension.getDriver());
        ProfilePage profilePage = new ProfilePage(browserExtension.getDriver());
        LoginPage loginPage = new LoginPage(browserExtension.getDriver());

        step("Открыть страницу входа", () -> loginPage.open().waitForPageLoad());
        step("Заполнить форму входа", () ->
                loginPage.enterEmail(user.getEmail())
                        .enterPassword(user.getPassword())
        );
        step("Нажать кнопку входа", loginPage::clickLogin);
        step("Перейти в личный кабинет", mainPage::clickProfile);
        step("Выйти из аккаунта", profilePage::clickLogout);
        step("Проверить переход на страницу входа", () -> {
            loginPage.waitForPageLoad();
            assertTrue(loginPage.isLoginButtonDisplayed());
        });

        apiClient.users().delete();
    }
}