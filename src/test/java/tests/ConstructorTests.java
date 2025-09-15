package tests;

import extensions.BrowserExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.WebDriver;
import pageObject.ConstructorPage;
import pageObject.MainPage;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConstructorTests {

    @RegisterExtension
    public BrowserExtension browserExtension = new BrowserExtension();

    @Nested
    @DisplayName("Конструктор: навигация по разделам")
    class ConstructorNavigationTest {

        private MainPage mainPage;
        private ConstructorPage constructorPage;

        @BeforeEach
        public void setUp() {
            WebDriver driver = browserExtension.getDriver();
            mainPage = new MainPage(driver);
            constructorPage = new ConstructorPage(driver);

            step("Открыть главную страницу", () -> {
                mainPage.open().waitForPageLoad();
            });
        }

        @Test
        @DisplayName("Активен раздел 'Булки' по умолчанию")
        public void defaultSectionIsBuns() {
            step("Проверить, что активен раздел 'Булки'", () -> {
                assertEquals("Булки", constructorPage.getSelectedSection(),
                        "Ожидался активный раздел 'Булки' при загрузке страницы");
            });
        }

        @Test
        @DisplayName("Переход в раздел 'Соусы'")
        public void navigateToSaucesSection() {
            step("Перейти в раздел 'Соусы'", () -> {
                constructorPage.clickSaucesSection();
            });

            step("Проверить, что активен раздел 'Соусы'", () -> {
                assertEquals("Соусы", constructorPage.getSelectedSection(),
                        "Раздел 'Соусы' не стал активным после клика");
            });
        }

        @Test
        @DisplayName("Переход в раздел 'Начинки'")
        public void navigateToFillingsSection() {
            step("Перейти в раздел 'Начинки'", () -> {
                constructorPage.clickFillingsSection();
            });

            step("Проверить, что активен раздел 'Начинки'", () -> {
                assertEquals("Начинки", constructorPage.getSelectedSection(),
                        "Раздел 'Начинки' не стал активным после клика");
            });
        }

        @Test
        @DisplayName("Возврат в раздел 'Булки'")
        public void navigateBackToBunsSection() {
            // Сначала переходим в другой раздел, чтобы проверить возврат
            step("Перейти в раздел 'Соусы'", () -> {
                constructorPage.clickSaucesSection();
            });

            step("Вернуться в раздел 'Булки'", () -> {
                constructorPage.clickBunsSection();
            });

            step("Проверить, что активен раздел 'Булки'", () -> {
                assertEquals("Булки", constructorPage.getSelectedSection(),
                        "Раздел 'Булки' не стал активным после возврата");
            });
        }


        @Test
        @DisplayName("Отображение списка булок")
        public void bunsListDisplay() {
            MainPage mainPage = new MainPage(browserExtension.getDriver());
            ConstructorPage constructorPage = new ConstructorPage(browserExtension.getDriver());

            step("Открыть главную страницу", () ->
                    mainPage.open().waitForPageLoad()
            );
            step("Проверить отображение списка булок", () ->
                    assertTrue(constructorPage.isBunsListDisplayed(), "Список булок не отображается")
            );
        }

        @Test
        @DisplayName("Отображение списка соусов")
        public void saucesListDisplay() {
            MainPage mainPage = new MainPage(browserExtension.getDriver());
            ConstructorPage constructorPage = new ConstructorPage(browserExtension.getDriver());

            step("Открыть главную страницу", () ->
                    mainPage.open().waitForPageLoad()
            );
            step("Проверить отображение списка соусов", () ->
                    assertTrue(constructorPage.isSaucesListDisplayed(), "Список соусов не отображается")
            );
        }

        @Test
        @DisplayName("Отображение списка начинок")
        public void fillingsListDisplay() {
            MainPage mainPage = new MainPage(browserExtension.getDriver());
            ConstructorPage constructorPage = new ConstructorPage(browserExtension.getDriver());

            step("Открыть главную страницу", () ->
                    mainPage.open().waitForPageLoad()
            );
            step("Проверить отображение списка начинок", () ->
                    assertTrue(constructorPage.isFillingsListDisplayed(), "Список начинок не отображается")
            );
        }
    }
}