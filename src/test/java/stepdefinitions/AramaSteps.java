package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import org.junit.Assert;
import pages.AnasayfaPage;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import org.openqa.selenium.chrome.ChromeOptions;

@Epic("Arama Fonksiyonları")
@Feature("Site Araması")
public class AramaSteps {
    
    private WebDriver driver;
    private AnasayfaPage anasayfaPage;

    public AramaSteps() {
        WebDriverManager.chromedriver().setup();
        
        // Chrome options için headless modu ekle
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");  // Chrome 109+ için yeni headless modu
        options.addArguments("--disable-gpu");    // Windows için gerekli
        options.addArguments("--no-sandbox");     // Linux için gerekli
        options.addArguments("--disable-dev-shm-usage"); // Docker/CI için gerekli
        options.addArguments("--window-size=1920,1080"); // Ekran boyutu
        
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        anasayfaPage = new AnasayfaPage(driver);
    }

    @Step("Kullanıcı ana sayfaya gider")
    @Given("Kullanıcı Gezimanya anasayfasına gider")
    public void kullaniciGezimanyaAnasayfasinaGider() {
        driver.get("https://www.gezisefasi.com/");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Step("Kullanıcı {string} için arama yapar")
    @When("Arama kutusuna {string} yazar")
    public void aramaKutusunaYazar(String aramaKelimesi) {
        anasayfaPage.aramaYap(aramaKelimesi);
    }

    @Attachment(value = "Page Screenshot", type = "image/png")
    public byte[] saveScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            saveScreenshot();
        }
        if (driver != null) {
            driver.quit();
        }
    }
} 