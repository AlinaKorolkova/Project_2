package tests;

import extensions.BrowserExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import pageObject.ConstructorPage;
import pageObject.MainPage;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConstructorTests {

    @RegisterExtension
    public BrowserExtension browserExtension = new BrowserExtension();

    @Test
    @DisplayName("Переход по разделам конструктора")
    public void navigateConstructorSections() {
        MainPage mainPage = new MainPage(browserExtension.getDriver());
        ConstructorPage constructorPage = new ConstructorPage(browserExtension.getDriver());

        step("Открыть главную страницу", () -> mainPage.open().waitForPageLoad());
        step("Проверить активность раздела 'Булки' по умолчанию", () ->
                assertEquals("Булки", constructorPage.getSelectedSection())
        );
        step("Перейти в раздел 'Соусы'", () -> {
            constructorPage.clickSaucesSection();
            assertEquals("Соусы", constructorPage.getSelectedSection());
        });
        step("Перейти в раздел 'Начинки'", () -> {
            constructorPage.clickFillingsSection();
            Thread.sleep(500);
            assertEquals("Начинки", constructorPage.getSelectedSection());
        });
        step("Вернуться в раздел 'Булки'", () -> {
            constructorPage.clickBunsSection();
            Thread.sleep(500);
            assertEquals("Булки", constructorPage.getSelectedSection());
        });
    }

    @Test
    @DisplayName("Отображение списков ингредиентов")
    public void ingredientsListsDisplay() {
        MainPage mainPage = new MainPage(browserExtension.getDriver());
        ConstructorPage constructorPage = new ConstructorPage(browserExtension.getDriver());

        step("Открыть главную страницу", () -> mainPage.open().waitForPageLoad());
        step("Проверить отображение списка булок", () ->
                assertTrue(constructorPage.isBunsListDisplayed())
        );
        step("Проверить отображение списка соусов", () ->
                assertTrue(constructorPage.isSaucesListDisplayed())
        );
        step("Проверить отображение списка начинок", () ->
                assertTrue(constructorPage.isFillingsListDisplayed())
        );
    }
}