package stepdefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.BookingPage;
import utilies.Constant;
import utilies.DriverFactory;

public class BookingSteps {

    private final WebDriver driver = DriverFactory.getDriver();
    private final BookingPage bookingPage = new BookingPage(driver);

    @When("I create a booking with {string}, {string}, {string}, {string}, {string}, {string}")
    public void i_create_a_booking_with(String bookingType, String location, String material, String cleaners, String hours, String cleanerSelection) {

        // Selecting hours and cleaners (assuming the UI requires index-based selection)
        bookingPage.selectNumberOfHours(Integer.parseInt(hours) - 1);
        bookingPage.selectNumberOfCleaners(Integer.parseInt(cleaners) - 1);

        // Selecting material (Yes/No)
        bookingPage.selectMaterialAndClickNext(material);

        // Selecting location and booking type
        bookingPage.selectLocation(location);
        bookingPage.selectBookingType(bookingType);

        // Selecting the First Cleaner or Custom
        if (cleanerSelection.equalsIgnoreCase("Yes")) {
            bookingPage.selectFirstCleaner();
        }

        // Date and time selection (you can adjust if needed)
        bookingPage.selectFirstAvailableDateTimeAndClickNext();

    }

    @Then("The booking should be created successfully")
    public void booking_created() {
    }

    @Given("I have launched the application Successfully")
    public void launched_the_application_successfully() {
        System.out.println("Launched the Web Site");
        Assert.assertEquals(Constant.URL, driver.getCurrentUrl(), "Failed to launch the Application");
    }

    @And("I logged in the Application")
    public void iLoggedInTheApplication() {
        bookingPage.loginSuccessfully();
    }

    @And("I have successfully did the {string}")
    public void iHaveSuccesfullyDidThe(String payment) {
        System.out.println("Need to discuss for payment" + payment);
    }
}