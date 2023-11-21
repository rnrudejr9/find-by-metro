package com.ssafy.vue.station.model.service;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



@Log4j2
@ExtendWith(MockitoExtension.class)
class SeleniumServiceTest {

    WebDriver driver;
    @InjectMocks
    SeleniumService seleniumService;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void test() {

        //url을 드라이브 켠다
        driver.get("https://map.naver.com/p/search/역곡역");
        // 정해진 초 시간 만큼 기다린다
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        //html로 이동한다
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe#searchIframe")));
        //css 문법을 이용해 해당 html 요소들을 찾아온다
        List<WebElement> elements = driver.findElements(By.cssSelector("span.Pb4bU"));
        if(elements == null)
            return;

        log.debug(elements.get(0).getText());
        String[] text = elements.get(0).getText().split(" ");
        log.debug(text[text.length-1]);

    }
}