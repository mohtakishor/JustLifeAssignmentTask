package hooks;

import driver.DriverFactory;
import driver.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utils.Constant;
import utils.LoggerUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Hooks {

    protected Logger logger;
    DriverManager driverManager = new DriverFactory();
    WebDriver driver;

    @Before
    public void setUp() {
        logger = LoggerUtil.getLogger(this.getClass());
        logger.info("===== Test Suite Started =====");
        driver = driverManager.getDriver();
        driver.get(Constant.URL);
        logger.info("Successfully launched the application");
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            logger.debug("Added screenshot for failed test cases");
            takeScreenshot(scenario);
        }
        logger.info("Closing the Web");
        driverManager.quitDriver();
    }

    private void takeScreenshot(Scenario scenario) {
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot, "image/png", "Failure Screenshot");

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