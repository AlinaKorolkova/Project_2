package tests;

import clients.ApiClient;
import extensions.BrowserExtension;
import generators.UserGenerator;
import model.CreateUserRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import pageObject.ConstructorPage;
import pageObject.LoginPage;
import pageObject.MainPage;
import pageObject.ProfilePage;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NavigationTests {

    @RegisterExtension
    public BrowserExtension browserExtension = new BrowserExtension();

    @Test
    @DisplayName("Переход в личный кабинет авторизованным пользователем")
    public void navigateToProfileAuthorized() {
        CreateUserRequest user = UserGenerator.randomUser();
        ApiClient apiClient = new ApiClient();
        apiClient.users().create(user);

        MainPage mainPage = new MainPage(browserExtension.getDriver());
        ProfilePage profilePage = new ProfilePage(browserExtension.getDriver());
        LoginPage loginPage = new LoginPage(browserExtension.getDriver());

        step("Открыть страницу входа", () -> loginPage.open().waitForPageLoad());
        step("Заполнить форму входа", () -> {
            loginPage.enterEmail(user.getEmail())
                    .enterPassword(user.getPassword());
        });
        step("Нажать кнопку входа", loginPage::clickLogin);
        step("Перейти в личный кабинет", mainPage::clickProfile);
        step("Проверить открытие страницы профиля", () -> {
            profilePage.waitForPageLoad();
            assertTrue(profilePage.isProfilePageDisplayed());
        });

        apiClient.users().delete();
    }

    @Test
    @DisplayName("Переход в личный кабинет неавторизованным пользователем")
    public void navigateToProfileUnauthorized() {
        MainPage mainPage = new MainPage(browserExtension.getDriver());
        LoginPage loginPage = new LoginPage(browserExtension.getDriver());

        step("Открыть главную страницу", () -> mainPage.open().waitForPageLoad());
        step("Перейти в личный кабинет", mainPage::clickProfile);
        step("Проверить переход на страницу входа", () -> {
            loginPage.waitForPageLoad();
            assertTrue(loginPage.isLoginButtonDisplayed());
        });
    }

    @Test
    @DisplayName("Переход в конструктор из личного кабинета")
    public void navigateToConstructorFromProfile() {
        CreateUserRequest user = UserGenerator.randomUser();
        ApiClient apiClient = new ApiClient();
        apiClient.users().create(user);

        MainPage mainPage = new MainPage(browserExtension.getDriver());
        ProfilePage profilePage = new ProfilePage(browserExtension.getDriver());
        ConstructorPage constructorPage = new ConstructorPage(browserExtension.getDriver());
        LoginPage loginPage = new LoginPage(browserExtension.getDriver());

        step("Открыть страницу входа", () -> loginPage.open().waitForPageLoad());
        step("Заполнить форму входа", () -> {
            loginPage.enterEmail(user.getEmail())
                    .enterPassword(user.getPassword());
        });
        step("Нажать кнопку входа", loginPage::clickLogin);
        step("Перейти в личный кабинет", mainPage::clickProfile);
        step("Перейти в конструктор через ссылку", profilePage::clickConstructor);
        step("Проверить открытие конструктора", () -> {
            constructorPage.waitForPageLoad();
            assertTrue(constructorPage.isBunsListDisplayed());
        });

        apiClient.users().delete();
    }

    @Test
    @DisplayName("Переход в конструктор через логотип")
    public void navigateToConstructorViaLogo() {
        CreateUserRequest user = UserGenerator.randomUser();
        ApiClient apiClient = new ApiClient();
        apiClient.users().create(user);

        MainPage mainPage = new MainPage(browserExtension.getDriver());
        ProfilePage profilePage = new ProfilePage(browserExtension.getDriver());
        ConstructorPage constructorPage = new ConstructorPage(browserExtension.getDriver());

        step("Открыть главную страницу", () -> mainPage.open().waitForPageLoad());
        step("Перейти в личный кабинет", mainPage::clickProfile);
        step("Перейти в конструктор через логотип", profilePage::clickLogo);
        step("Проверить открытие конструктора", () -> {
            constructorPage.waitForPageLoad();
            assertTrue(constructorPage.isBunsListDisplayed());
        });

        apiClient.users().delete();
    }
}