package utilities;

import io.cucumber.java.Before;
import java.io.File;

public class Hooks {
    @Before
    public void setup() {
        File raporKlasoru = new File("test-raporlari");
        if (!raporKlasoru.exists()) {
            raporKlasoru.mkdir();
        }
    }
} 