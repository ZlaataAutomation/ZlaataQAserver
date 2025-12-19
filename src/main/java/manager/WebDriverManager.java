package manager;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;

import enums.DriverType;
import enums.EnvironmentType;
import utils.ClickLoggerListener;

public class WebDriverManager {

    private WebDriver driver;
    private DriverType driverType;
    private EnvironmentType environmentType;
    private static boolean isHeadLessModeEnabled;

    public WebDriverManager() {
        driverType = FileReaderManager.getInstance().getConfigReader().getBrowser();
        environmentType = FileReaderManager.getInstance().getConfigReader().getEnvironment();
        isHeadLessModeEnabled = FileReaderManager.getInstance().getConfigReader().getHeadlessModeOption();
    }

    public WebDriver getDriver() {
        if (driver == null)
            driver = createDriver();
        return driver;
    }

    private WebDriver createDriver() {
        WebDriver baseDriver = null;

        switch (environmentType) {
            case LOCAL:
                baseDriver = createLocalDriver();
                break;
            case REMOTE:
                try {
                    baseDriver = createRemoteDriver();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
        }

        // ✅ Attach global click logger
        ClickLoggerListener clickLogger = new ClickLoggerListener();
        driver = new EventFiringDecorator<>(clickLogger).decorate(baseDriver);

        // ✅ Always maximize browser window
      //  driver.manage().window().maximize();
    
        if (!isHeadLessModeEnabled) {
            driver.manage().window().maximize();
        } else {
            driver.manage().window().setSize(new Dimension(1920, 1080));
        }

        // ✅ Apply implicit wait
        driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(FileReaderManager.getInstance().getConfigReader().getImplicitlyWait()));

        return driver;
    }

    private WebDriver createRemoteDriver() throws MalformedURLException {
        switch (driverType) {
            case CHROME:
                ChromeOptions chromeOptions = getChromeOptions();
                return new ChromeDriver(chromeOptions);

            case FIREFOX:
                return new FirefoxDriver();

            default:
                return new ChromeDriver();
        }
    }

    private WebDriver createLocalDriver() {
        switch (driverType) {
            case FIREFOX:
                return new FirefoxDriver();

            case CHROME:
                ChromeOptions chromeOptions = getChromeOptions();
                return new ChromeDriver(chromeOptions);

            default:
                return new ChromeDriver();
        }
    }

    private ChromeOptions getChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        if (isHeadLessModeEnabled) {
         //   chromeOptions.addArguments("--headless", "window-size=1920,1080");
        	chromeOptions.addArguments("--headless=new");   
            chromeOptions.addArguments("--window-size=1920,1080"); 
            chromeOptions.addArguments("--disable-gpu");
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-dev-shm-usage");
          
        }
        chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
        chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        chromeOptions.setAcceptInsecureCerts(true);
 //       chromeOptions.setExperimentalOption("useAutomationExtension", true);
        chromeOptions.addArguments("--disable-save-password-bubble");
        chromeOptions.addArguments("--disable-extensions");
        
        
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--disable-features=AccountConsistency");
        chromeOptions.addArguments("--disable-infobars");
        
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.default_content_setting_values.notifications", 2);
        chromeOptions.setExperimentalOption("prefs", prefs);
        return chromeOptions;
    }

    public void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
