package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import static java.time.Duration.ofSeconds;

public class WebDriverCreator {

    public static WebDriver createWebDriver() {
        WebDriver webDriver = createWebDriver(System.getProperty("browser"));
        webDriver.manage().timeouts().implicitlyWait(ofSeconds(3));
        return webDriver;
    }

    public static WebDriver createWebDriver(String browser) {
        if (browser == null) {
            return createChromeDriver();
        }

        switch (browser) {
            case "firefox":
                return createFirefoxDriver();
            case "chrome":
            default:
                return createChromeDriver();
        }
    }

    private static WebDriver createChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver() {
        return new FirefoxDriver();
    }
}
