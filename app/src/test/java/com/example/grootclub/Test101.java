package com.example.grootclub;

import org.junit.Test;
import org.openqa.selenium.By;

import io.appium.java_client.MobileElement;

public class Test101 extends BaseClient {

    @Test
    public void testValidateLabelAndClickButton() {
        setup();
//        MobileElement btn = driver.findElement(By.id("com.example.grootclub:id/card"));
//        MobileElement btn = driver.findElement(By.xpath("//*[@resource-id='com.example.grootclub:id/card']"));
        MobileElement btn = driver.findElement(By.xpath("(//android.view.ViewGroup[@resource-id=\"com.example.grootclub:id/card\"])[1]"));


        btn.click();
//        Thread.sleep(10000);


        //ช่อง Email
        MobileElement email = driver.findElement(By.id("com.example.grootclub:id/etEmail"));
        email.sendKeys("sukanya13@gmaiil.com");
//        Thread.sleep(10000);

        //ช่อง Password
        MobileElement password = driver.findElement(By.id("com.example.grootclub:id/etPass"));
        password.sendKeys("12345678su13");
//        Thread.sleep(10000);

        //ปุ่ม Login
        MobileElement login = driver.findElement(By.id("com.example.grootclub:id/btnSignIn"));

//        Thread.sleep(10000);
        teardown();
    }
}
