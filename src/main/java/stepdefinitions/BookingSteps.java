package stepdefinitions;

import base.BaseStep;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import utils.Constant;

public class BookingSteps extends BaseStep {

    @Given("I have launched the application Successfully")
    public void launched_the_application_successfully() {
        init();
        driver.get(Constant.URL);
        Assert.assertEquals(Constant.URL, driver.getCurrentUrl(), "Failed to launch the Application");
    }

    @And("I logged in the Application")
    public void iLoggedInTheApplication() {
        bookingPage.loginSuccessfully();
    }

    @When("I create a booking with {string}, {string}, {string}, {string}, {string}, {string}, {string}")
    public void i_create_a_booking_with(String bookingType, String location, String material, String cleaners, String hours, String cleanerSelection, String dateSelection) {
        bookingPage.selectNumberOfHours(hours);
        bookingPage.selectNumberOfCleaners(cleaners);
        bookingPage.selectMaterialAndClickNext(material);
        bookingPage.selectLocation(location);
        bookingPage.selectBookingType(bookingType);

        if (cleanerSelection.equalsIgnoreCase("Yes")) {
            bookingPage.selectFirstCleaner();
        }

        if (dateSelection.equalsIgnoreCase("Yes")) {
            bookingPage.selectFirstAvailableDateTimeAndClickNext();
        } else {
            bookingPage.selectTomorrow9AM();
        }
    }

    @Then("The booking should be created successfully")
    public void booking_created() {
    }

    @And("I have successfully did the payment with as {string}")
    public void iHaveSuccessfullyDidThePaymentWithAs(String paymentMethod) {
        bookingPage.selectPaymentTypeAndCompletePayment(paymentMethod);
    }
}