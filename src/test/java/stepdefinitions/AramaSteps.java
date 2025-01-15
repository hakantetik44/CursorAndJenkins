package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import pages.AnasayfaPage;
import utilities.Driver;
import io.qameta.allure.*;

@Epic("Arama Fonksiyonları")
@Feature("Site Araması")
public class AramaSteps {
    
    private AnasayfaPage anasayfaPage;

    public AramaSteps() {
        anasayfaPage = new AnasayfaPage(Driver.getDriver());
    }

    @Step("Kullanıcı ana sayfaya gider")
    @Given("Kullanıcı Gezimanya anasayfasına gider")
    public void kullaniciGezimanyaAnasayfasinaGider() {
        Driver.getDriver().get("https://www.gezisefasi.com/");
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
        return ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            saveScreenshot();
        }
        Driver.closeDriver();
    }
} 