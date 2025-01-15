package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class AnasayfaPage {
    
    private WebDriverWait wait;
    
    @FindBy(id = "tourFind")
    private WebElement searchBox;

    @FindBy(css = ".search-results, .tour-list, .easy-autocomplete-container")
    private WebElement searchResults;

    public AnasayfaPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void aramaYap(String aramaKelimesi) {
        wait.until(ExpectedConditions.visibilityOf(searchBox));
        searchBox.sendKeys(aramaKelimesi);
        searchBox.sendKeys(Keys.ENTER);
    }

    public boolean sonuclarGoruntuleniyorMu(String aramaKelimesi) {
        try {
            wait.until(ExpectedConditions.visibilityOf(searchResults));
            return searchResults.getText().contains(aramaKelimesi);
        } catch (Exception e) {
            return false;
        }
    }
} 