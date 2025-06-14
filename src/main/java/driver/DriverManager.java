package driver;

import org.openqa.selenium.WebDriver;

public interface DriverManager {
    WebDriver getDriver();

    void quitDriver();
}
