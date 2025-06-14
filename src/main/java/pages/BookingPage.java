package pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.CommonMethods;
import utils.Constant;
import utils.LoggerUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BookingPage extends CommonMethods {

    WebDriver driver;
    protected Logger logger;

    @FindBy(xpath = "//span[contains(@id,'duration-')]")
    List<WebElement> hoursCount;

    @FindBy(xpath = "//span[contains(@id,'number_')]")
    List<WebElement> cleanersCount;

    @FindBy(id = "funnel-next-button")
    WebElement nextCTA;

    @FindBy(id = "one_time")
    WebElement oneTimeBooking;

    @FindBy(id = "weekly")
    WebElement weeklyBooking;

    @FindBy(xpath = "//div[@id='frequency-button']")
    WebElement selectCTa;

    @FindBy(id = "map-location-input")
    WebElement locationInput;

    @FindBy(id = "search-item-0")
    WebElement locationFirst;

    @FindBy(id = "map-modal-map-confirm-button")
    WebElement confirmLocation;

    @FindBy(id = "funnel-next-button")
    WebElement adNextCTA;

    @FindBy(id = "material-1-text")
    WebElement materialYes;

    @FindBy(id = "material-0-text")
    WebElement materialNo;

    @FindBy(xpath = "//span[contains(@class,'body-text sm semiDark')]")
    List<WebElement> firstAvailableCleaner;

    @FindBy(xpath = "//div[@class='dates-wrapper']")
    WebElement dateRange;

    @FindBy(xpath = "//span[contains(@id,'enabled-day-')]")
    List<WebElement> availableDates;

    @FindBy(id = "phone-number-input")
    WebElement enterLoginNum;

    @FindBy(id = "create-otp-button")
    WebElement loginContinueCTA;

    @FindBy(xpath = "//input[contains(@id,'otp-digit-')]")
    List<WebElement> enterOTP;

    @FindBy(id = "payment-change")
    WebElement paymentChangeCTA;

    @FindBy(id = "paymentMethods-button")
    WebElement selectPaymentMethodCTA;

    @FindBy(id = "funnel-complete-button")
    WebElement completePaymentCTA;

    @FindBy(id = "bd-manage-booking-button-text")
    WebElement manageThisBookingCTA;

    @FindBy(id = "bd-manage-booking-button")
    WebElement editBookingCTA;

    @FindBy(id = "mbm-cancel-booking-button-text")
    WebElement cancelBookingCTA;

    @FindBy(id = "cancel-confirm-button")
    WebElement cancelConfirmBookingCTA;

    @FindBy(id = "cancel-question-modal-cancel")
    WebElement confirmCancel;

    @FindBy(xpath = "(//div[@class='radio-circle _vc _c'])[1]")
    WebElement selectReasonForCancel;

    @FindBy(id = "confirm-cancel-reason-button")
    WebElement confirmReasonCancel;

    public BookingPage(WebDriver driver) {
        logger = LoggerUtil.getLogger(this.getClass());
        this.driver = driver;
    }

    public void selectNumberOfHours(String hours) {
        logger.info("Selecting number of Hours :" + hours);
        waitUntilAnyElementVisible(hoursCount, 1000L);
        clickOnListOfElement(hoursCount, Integer.parseInt(hours) - 1);
    }

    public void selectNumberOfCleaners(String cleaners) {
        logger.info("Selecting number of Clearner :" + cleaners);
        waitUntilAnyElementVisible(cleanersCount, 1000L);
        clickOnListOfElement(cleanersCount, Integer.parseInt(cleaners) - 1);
    }

    public void selectMaterialAndClickNext(String material) {
        logger.info("Selecting material option :" + material);
        if (material.equalsIgnoreCase("Yes")) {
            click(materialYes);
        } else {
            click(materialNo);
        }
        click(nextCTA);
    }

    public void selectLocation(String location) {
        logger.info("Entered Location :" + location);
        clickAndSendKeys(locationInput, location);
        click(locationFirst);
        hardWait(5000L);
        click(confirmLocation);
        hardWait(5000L);
        click(adNextCTA);
    }

    public void selectBookingType(String bookingType) {
        logger.info("Type of Booking :" + bookingType);
        if (bookingType.equalsIgnoreCase("One Time")) {
            click(oneTimeBooking);
        } else if (bookingType.equalsIgnoreCase("Weekly")) {
            click(weeklyBooking);
        }
        click(selectCTa);
    }

    public void selectFirstCleaner() {
        logger.info("Booking first clearner ");
        waitUntilAnyElementVisible(firstAvailableCleaner, 5000L);
        clickOnListOfElement(firstAvailableCleaner, 1);
    }

    public void selectFirstAvailableDateTimeAndClickNext() {
        logger.info("Selecting date range and time");
        scrollToElementWithExtraOffset(dateRange, -100);
        waitUntilAnyElementVisible(availableDates, 5000L);
        clickOnListOfElement(availableDates, 1);
        click(nextCTA);
    }

    public void selectTomorrow9AM() {
        logger.info("Clicking on next day date");
        hardWait(5000L);
        LocalDate currentDate = LocalDate.now().plusDays(1);
        String tomorrowDate = currentDate.format(DateTimeFormatter.ofPattern("dd"));
        String dateXpath = "//span[@id='enabled-day-1-text' and text()='" + tomorrowDate + "']";
        hardWait(5000L);
        clickOnElementWithDynamicXpath(dateXpath);
        selectTime("09:00-09:30");
        click(nextCTA);
    }

    public void selectTime(String serviceTime) {
        logger.info("Clicking on time for service :" + serviceTime);
        String timeXpath = "//span[@id='time-2-text' and text()='" + serviceTime + "']";
        clickOnElementWithDynamicXpath(timeXpath);
    }

    public void loginSuccessfully() {
        logger.info("Logged in with number :" + Constant.loginNumber);
        clickAndSendKeys(enterLoginNum, Constant.loginNumber);
        click(loginContinueCTA);
        enterOTP();
    }

    public void enterOTP() {
        logger.info("Entered otp as :" + Constant.loginOTP);
        char otp[] = Constant.loginOTP.toCharArray();
        hardWait(5000L);
        for (int i = 0; i <= otp.length - 1; i++) {
            clickAndSendKeys(enterOTP.get(i), String.valueOf(otp[i]));
        }
    }

    public void selectPaymentTypeAndCompletePayment(String paymentMethod) {
        logger.info("Payment mode selection as :" + paymentMethod);
        click(paymentChangeCTA);
        String paymentTypeXpath = "//span[contains(text(),'" + paymentMethod + "')]/ancestor::div[contains(@class,'radio-selection')]//div[contains(@class,'radio-circle')]";
        clickOnElementWithDynamicXpath(paymentTypeXpath);
        click(selectPaymentMethodCTA);
        hardWait(5000L);
        click(completePaymentCTA);
    }

    public void cancelBooking() {
        logger.info("Cancelling the order");
        scrollToElement(manageThisBookingCTA);
        click(manageThisBookingCTA);
        scrollToElement(editBookingCTA);
        click(editBookingCTA);
        click(cancelBookingCTA);
        click(cancelConfirmBookingCTA);
        click(confirmCancel);
        click(selectReasonForCancel);
        scrollToElement(confirmReasonCancel);
        click(confirmReasonCancel);
    }
}