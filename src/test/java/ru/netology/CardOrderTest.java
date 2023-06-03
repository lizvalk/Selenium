package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public class CardOrderTest {
    private WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }
    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
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
        String actual = driver.findElement(By.cssSelector("[data-test-id = order-success]")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void shouldFailIfNameFilledInLatinLetters() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id = name] input")).sendKeys("Valkevich Elizaveta");
        driver.findElement(By.cssSelector("[data-test-id = phone] input")).sendKeys("+7913");
        driver.findElement(By.cssSelector("[data-test-id = agreement] span")).click();
        driver.findElement(By.className("button__text")).click();

        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("span.input_invalid span.input__sub")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void lessThan11DigitsInThePhoneField() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id = name] input")).sendKeys("Валькевич Елизавета");
        driver.findElement(By.cssSelector("[data-test-id = phone] input")).sendKeys("+7913");
        driver.findElement(By.cssSelector("[data-test-id = agreement] span")).click();
        driver.findElement(By.className("button__text")).click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("span.input_invalid span.input__sub")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }
}
