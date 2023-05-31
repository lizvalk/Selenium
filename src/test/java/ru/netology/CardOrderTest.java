package ru.netology;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class CardOrderTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "./driver/win/chromedriver.exe");
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldCardOrderForm() {
        driver.get("http://localhost:9999/");
        List<WebElement> inputs = driver.findElements(By.tagName("input"));
        inputs.get(0).sendKeys("Валькевич Елизавета");
        inputs.get(1).sendKeys("+79138119144");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.tagName("p")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void shouldCardOrderFormUseSelectors() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id = name] input")).sendKeys("Валькевич Елизавета");
        driver.findElement(By.cssSelector("[data-test-id = phone] input")).sendKeys("+79138119144");
        driver.findElement(By.cssSelector("[data-test-id = agreement] span")).click();
        driver.findElement(By.className("button__text")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.tagName("p")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }
}
