import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class RealDeviceAppsTest {
    DesiredCapabilities caps;
    URL url;
    AndroidDriver<MobileElement> driver;

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
        driver = new AndroidDriver<>(url, caps);
    }

    @Test
    public void launchCalc() {
        //The below 2 capability will only launch the app , it won't check or install the app
        caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.miui.calculator");
        // "Use appPackage value or . before the app activity" //
        caps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".cal.CalculatorActivity");
        launchDriver();
        driver.findElementById("android:id/button1").click();
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MobileElement one = driver.findElementById("com.miui.calculator:id/btn_1_s");
        MobileElement plus = driver.findElementById("com.miui.calculator:id/btn_plus_s");
        MobileElement two = driver.findElementById("com.miui.calculator:id/btn_2_s");
        one.click();
        plus.click();
        two.click();
        MobileElement result = driver.findElementById("com.miui.calculator:id/result");
        Assertions.assertThat(result.getText()).isEqualTo("= 3");
    }

    @Test
    public void launchApiDemos() {
        //The below capability will check and install the app if not present and launch the app
        caps.setCapability(MobileCapabilityType.APP, System.getProperty("user.dir") + "/ApiDemos-debug.apk");
        launchDriver();
        driver.findElementByXPath("//android.widget.TextView[@content-desc='Accessibility']").click();
        Assertions.assertThat(driver.findElementByXPath("//*[@index='3']").getText()).isEqualTo("Custom View");
    }

    @Test
    public void launchSelendroidTestApp() {
        caps.setCapability(MobileCapabilityType.APP, System.getProperty("user.dir") + "/selendroid-test-app-0.17.0.apk");
        launchDriver();
        driver.findElementById("io.selendroid.testapp:id/startUserRegistration").click();
        driver.findElementById("io.selendroid.testapp:id/inputUsername").sendKeys("Sreeram");
        driver.findElementByAccessibilityId("email of the customer").sendKeys("123123@gmail.com");
        driver.findElementById("io.selendroid.testapp:id/inputPassword").sendKeys("Sreeram");
        driver.findElementById("io.selendroid.testapp:id/inputName").sendKeys("sree");
        driver.navigate().back();
        driver.findElementById("android:id/text1").click();
        driver.findElementByXPath("//*[@text='C#']").click();
        driver.findElementByXPath("//*[@text='I accept adds']").click();
        driver.findElementById("io.selendroid.testapp:id/btnRegisterUser").click();
    }


    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}