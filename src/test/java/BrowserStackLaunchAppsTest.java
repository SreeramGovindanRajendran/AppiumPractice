import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class BrowserStackLaunchAppsTest {
    DesiredCapabilities caps;
    URL url;
    AndroidDriver<MobileElement> driver;

    @BeforeTest
    public void initDC() {
        caps = new DesiredCapabilities();
        caps.setCapability("browserstack.user", ""); // enter browserstack user id
        caps.setCapability("browserstack.key", ""); // enter browserstack key id
        caps.setCapability("app", ""); // enter app id
        caps.setCapability("device", "Google Pixel 3");
        caps.setCapability("os_version", "9.0");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("project", "First Java Project");
        caps.setCapability("build", "Java Android");
        caps.setCapability("acceptSslCert", false);
        caps.setCapability("platformName", "Android");
        caps.setCapability("new_bucketing", true);
    }

    public void launchDriver() {
        try {
            url = new URL("http://hub.browserstack.com/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver = new AndroidDriver<>(url, caps);
    }

    @Test
    public void launchApiDemos(Method method) {
        caps.setCapability("name", method.getName());
        launchDriver();
        driver.findElement(By.xpath("//android.widget.TextView[@content-desc='Accessibility']")).click();
        Assertions.assertThat(driver.findElement(By.xpath("//*[@index='3']")).getText()).isEqualTo("Custom View");
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}