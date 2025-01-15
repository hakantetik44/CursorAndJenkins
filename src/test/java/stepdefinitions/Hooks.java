package stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import utilities.Driver;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;

public class Hooks {
    
    @Before
    public void setup() {
        // Create reports directory if it doesn't exist
        File reportsDir = new File("test-reports");
        if (!reportsDir.exists()) {
            reportsDir.mkdir();
        }
        
        // Create screenshots directory
        File screenshotsDir = new File("test-reports/screenshots");
        if (!screenshotsDir.exists()) {
            screenshotsDir.mkdirs();
        }
    }
    
    @After
    public void tearDown(Scenario scenario) {
        // Take screenshot if scenario fails
        if (scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) Driver.getDriver())
                .getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", 
                "Screenshot-" + scenario.getName());
        }
        
        // Close browser
        Driver.closeDriver();
    }
} 