package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.Color;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CallbackTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        System.setProperty("web-driver.chrome.driver", "./driver/win/chromedriver");
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        //options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestV1() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Волков Василий");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79260321212");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] .checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
        assertEquals(expected, actual);


    }
    @Test
    void shouldTestV2() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Volkov Vasiliy");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79260321212");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] .checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);


    }
    @Test
    void shouldTestV3() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Волков Василий");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79260321");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] .checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);


    }@Test
    void shouldTestV4() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Волков Василий");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79260321212");
        driver.findElement(By.cssSelector("button.button_view_extra")).click();
        WebElement invalid = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid .checkbox__text"));
        String color = invalid.getCssValue("color");
        String expected = Color.fromString(color).asHex();
        String actual = "#ff5c5c";
        assertEquals(expected, actual);


    }


}
