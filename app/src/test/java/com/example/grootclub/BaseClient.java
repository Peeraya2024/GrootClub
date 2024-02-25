package com.example.grootclub;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.net.URL;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobilePlatform;

public class BaseClient {
    static AppiumDriver<MobileElement> driver;
    static MobilePlatform platform;

    @BeforeTest
    public void setup() {
        try {
            DesiredCapabilities cap = new DesiredCapabilities();
            cap.setCapability("deviceName", "Pixel_3a_API_34_extension_level_7_arm64-v8a");
            cap.setCapability("platformVersion", "14");
            cap.setCapability("platformName", "Android");
            cap.setCapability("noReset", true);
            cap.setCapability("automationName", "UIAutomator2");
            cap.setCapability("appPackage", "com.example.grootclub");
            cap.setCapability("appActivity", "com.example.grootclub.ui.splash.SplashActivity");

            URL url = new URL("http://127.0.0.1:4723/wd/hub");
            driver = new AppiumDriver<MobileElement>(url, cap) ;
        } catch (Exception ex) {
            System.out.println("Cause is:" + ex.getCause());
            System.out.println("Message is:" + ex.getMessage());
        }
    }

    @AfterTest
    public void teardown() {
        driver.quit();
    }
}
