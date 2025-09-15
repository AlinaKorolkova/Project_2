package tests;

import extensions.BrowserExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import pageObject.MainPage;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OpenPageTest {

    @RegisterExtension
    public BrowserExtension browserExtension = new BrowserExtension();

    @Test
    @DisplayName("Отрытие страницы сборки бургера")
    public void openMainPage() {
        MainPage mainPage = new MainPage(browserExtension.getDriver());
        step("Открыть страницу cборки бургера",
                mainPage::open
        );
        step("", () ->

                assertEquals("Соберите бургер", mainPage.titleText())
        );
    }
}