package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.Keys;
import utilities.ChainTest;

public class AnasayfaPage {
    
    private WebDriver driver;
    private ChainTest chainTest;

    public AnasayfaPage(WebDriver driver) {
        this.driver = driver;
        this.chainTest = new ChainTest(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "tourFind")
    private WebElement searchBox;

    @FindBy(css = ".search-results, .tour-list, .easy-autocomplete-container")
    private WebElement searchResults;

    public void aramaYap(String aramaKelimesi) {
        chainTest
            .testBaslat("Arama Testi")
            .yaziYaz(searchBox, aramaKelimesi)
            .enterBasma(searchBox);
    }

    public boolean sonuclarGoruntuleniyorMu(String aramaKelimesi) {
        try {
            chainTest
                .elementGozukeneKadarBekle(searchResults)
                .dogrula("Arama sonuçları görüntülendi", 
                    searchResults.getText().contains(aramaKelimesi));
            return true;
        } catch (Exception e) {
            System.out.println(chainTest.raporuGetir());
            return false;
        }
    }

    public String getTestRaporu() {
        return chainTest.raporuGetir();
    }

    public void htmlRaporuKaydet() {
        chainTest.htmlRaporuKaydet();
    }
} 