package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.junit.Assert;
import pages.AnasayfaPage;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.cucumber.java.After;
import java.time.Duration;

public class AramaSteps {
    
    private WebDriver driver;
    private AnasayfaPage anasayfaPage;

    public AramaSteps() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        anasayfaPage = new AnasayfaPage(driver);
    }

    @Given("Kullanıcı Gezimanya anasayfasına gider")
    public void kullaniciGezimanyaAnasayfasinaGider() {
        driver.get("https://www.gezisefasi.com/");
        try {
            Thread.sleep(2000); // Sayfa tam yüklensin diye kısa bir bekleme
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("Arama kutusuna {string} yazar")
    public void aramaKutusunaYazar(String aramaKelimesi) {
        anasayfaPage.aramaYap(aramaKelimesi);
    }

    @And("Ara butonuna tıklar")
    public void araButonunaTiklar() {
        // Bu adım artık aramaYap metodunun içinde
    }

    @Then("Arama sonuçlarında {string} ile ilgili içerikler görüntülenir")
    public void aramaSonuclarindaIceriklerGoruntulenir(String aramaKelimesi) {
        boolean sonuc = anasayfaPage.sonuclarGoruntuleniyorMu(aramaKelimesi);
        System.out.println(anasayfaPage.getTestRaporu());
        anasayfaPage.htmlRaporuKaydet();
        Assert.assertTrue("Arama sonuçları görüntülenemedi", sonuc);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
} 