package base;

import driver.DriverFactory;
import driver.DriverManager;
import org.openqa.selenium.WebDriver;
import pages.BookingPage;

public class BaseStep {
    protected WebDriver driver;
    protected BookingPage bookingPage;
    protected DriverManager driverManager = new DriverFactory();


    public void init() {
        driver = driverManager.getDriver();
        bookingPage = new BookingPage(driver);
    }
}
