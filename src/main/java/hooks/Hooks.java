package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utilies.DriverFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Hooks {
    WebDriver driver = DriverFactory.getDriver();

    @Before
    public void setUp() {
        DriverFactory.getDriver().get("https://www.justlife.com/en-AE/home-cleaning/checkout/details?step=1");
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            takeScreenshot(scenario);
        }
        // Quit the driver to start fresh for next scenario
        DriverFactory.quitDriver();
    }


    private void takeScreenshot(Scenario scenario) {

        // Capture screenshot
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

        // Attach to Cucumber report
        scenario.attach(screenshot, "image/png", "Failure Screenshot");

        //Saving screenshot to the folder
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = "target/screenshots/" + scenario.getName().replaceAll(" ", "_") + "_" + timestamp + ".png";

        try {
            Files.createDirectories(Paths.get("target/screenshots/"));
            Files.copy(screenshotFile.toPath(), Paths.get(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}