import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class RealDeviceLaunchAppsTest {

    DesiredCapabilities caps;
    URL url;
    AndroidDriver driver;

    @BeforeTest
    public void initDC() {
        caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7.1.2");
        caps.setCapability(MobileCapabilityType.UDID, ""); // Enter device id
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Redmi");
        caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
    }

    public void launchDriver() {
        try {
            url = new URL("http://0.0.0.0:4723/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver = new AndroidDriver(url, caps);
    }

    @Test
    public void launchCalc() {

        //The below 2 capability will only launch the app , it won't check or install the app
        caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.miui.calculator");

        // "Use appPackage value or . before the app activity" //
        caps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".cal.CalculatorActivity");

        launchDriver();
        driver.findElement(By.id("android:id/button1")).click();
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.id("com.miui.calculator:id/btn_1_s")).click();
        driver.findElement(By.id("com.miui.calculator:id/btn_plus_s")).click();
        driver.findElement(By.id("com.miui.calculator:id/btn_2_s")).click();
        Assertions.assertThat(driver.findElement(By.id("com.miui.calculator:id/result")).getText()).isEqualTo("= 3");
    }

    @Test
    public void launchApiDemos() {

        //The below capability will check and install the app if not present and launch the app
        caps.setCapability(MobileCapabilityType.APP, System.getProperty("user.dir") + "/ApiDemos-debug.apk");

        launchDriver();
        driver.findElement(By.xpath("//android.widget.TextView[@content-desc='Accessibility']")).click();
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

}
