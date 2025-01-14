package utilities;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.openqa.selenium.Keys;

public class ChainTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private String testAdi;
    private StringBuilder testRaporu;
    private StringBuilder htmlRapor;
    private int basariliAdimSayisi = 0;
    private int basarisizAdimSayisi = 0;

    public ChainTest(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.testRaporu = new StringBuilder();
        this.htmlRapor = new StringBuilder();
        htmlRaporBaslat();
    }

    private void htmlRaporBaslat() {
        htmlRapor.append("<!DOCTYPE html>\n")
                .append("<html>\n")
                .append("<head>\n")
                .append("<style>\n")
                .append("body { font-family: Arial, sans-serif; margin: 20px; }\n")
                .append(".success { color: green; }\n")
                .append(".error { color: red; }\n")
                .append(".step { margin: 10px 0; padding: 5px; border-left: 3px solid #ccc; }\n")
                .append("</style>\n")
                .append("</head>\n")
                .append("<body>\n");
    }

    public ChainTest testBaslat(String testAdi) {
        this.testAdi = testAdi;
        String baslik = "Test Başladı: " + testAdi;
        raporEkle(baslik);
        htmlRapor.append("<h2>").append(baslik).append("</h2>\n");
        return this;
    }

    public ChainTest yaziYaz(WebElement element, String text) {
        try {
            elementGozukeneKadarBekle(element);
            element.sendKeys(text);
            String mesaj = "'" + text + "' yazıldı";
            raporEkle(mesaj);
            raporaBasariliAdimEkle(mesaj);
            basariliAdimSayisi++;
        } catch (Exception e) {
            String hata = "HATA: Yazı yazılamadı - " + e.getMessage();
            raporEkle(hata);
            raporaHataEkle(hata);
            basarisizAdimSayisi++;
            throw e;
        }
        return this;
    }

    public ChainTest tikla(WebElement element) {
        try {
            elementGozukeneKadarBekle(element);
            element.click();
            String mesaj = "Elemente tıklandı";
            raporEkle(mesaj);
            raporaBasariliAdimEkle(mesaj);
            basariliAdimSayisi++;
        } catch (Exception e) {
            String hata = "HATA: Tıklama yapılamadı - " + e.getMessage();
            raporEkle(hata);
            raporaHataEkle(hata);
            basarisizAdimSayisi++;
            throw e;
        }
        return this;
    }

    public ChainTest elementGozukeneKadarBekle(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            String mesaj = "Element görünür oldu";
            raporEkle(mesaj);
            raporaBasariliAdimEkle(mesaj);
            basariliAdimSayisi++;
        } catch (Exception e) {
            String hata = "HATA: Element görünür olmadı - " + e.getMessage();
            raporEkle(hata);
            raporaHataEkle(hata);
            basarisizAdimSayisi++;
            throw e;
        }
        return this;
    }

    public ChainTest dogrula(String mesaj, boolean kosul) {
        try {
            Assert.assertTrue(mesaj, kosul);
            String basarili = "Doğrulama başarılı: " + mesaj;
            raporEkle(basarili);
            raporaBasariliAdimEkle(basarili);
            basariliAdimSayisi++;
        } catch (AssertionError e) {
            String hata = "HATA: Doğrulama başarısız - " + mesaj;
            raporEkle(hata);
            raporaHataEkle(hata);
            basarisizAdimSayisi++;
            throw e;
        }
        return this;
    }

    public ChainTest enterBasma(WebElement element) {
        try {
            elementGozukeneKadarBekle(element);
            element.sendKeys(Keys.ENTER);
            raporEkle("ENTER tuşuna basıldı");
            basariliAdimSayisi++;
        } catch (Exception e) {
            String hata = "HATA: ENTER tuşuna basılamadı - " + e.getMessage();
            raporEkle(hata);
            raporaHataEkle(hata);
            basarisizAdimSayisi++;
            throw e;
        }
        return this;
    }

    private void raporEkle(String mesaj) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        testRaporu.append(timestamp).append(" - ").append(mesaj).append("\n");
    }

    private void raporaBasariliAdimEkle(String mesaj) {
        htmlRapor.append("<div class='step success'>")
                .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
                .append(" - ✓ ")
                .append(mesaj)
                .append("</div>\n");
    }

    private void raporaHataEkle(String mesaj) {
        htmlRapor.append("<div class='step error'>")
                .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
                .append(" - ✗ ")
                .append(mesaj)
                .append("</div>\n");
    }

    public String raporuGetir() {
        return "=== " + testAdi + " Raporu ===\n" + testRaporu.toString();
    }

    public void htmlRaporuKaydet() {
        String ozet = String.format(
            "<div class='summary'>" +
            "<h3>Test Özeti</h3>" +
            "<p>Toplam Adım: %d</p>" +
            "<p class='success'>Başarılı Adım: %d</p>" +
            "<p class='error'>Başarısız Adım: %d</p>" +
            "</div>",
            (basariliAdimSayisi + basarisizAdimSayisi),
            basariliAdimSayisi,
            basarisizAdimSayisi
        );

        htmlRapor.append(ozet)
                 .append("</body></html>");

        String dosyaAdi = "test-raporu-" + 
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")) + 
            ".html";

        try (FileWriter writer = new FileWriter("test-raporlari/" + dosyaAdi)) {
            writer.write(htmlRapor.toString());
            System.out.println("HTML rapor oluşturuldu: " + dosyaAdi);
        } catch (IOException e) {
            System.err.println("Rapor kaydedilemedi: " + e.getMessage());
        }
    }
} 