package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import utilies.CommonMethods;
import utilies.Constant;

import java.util.List;

public class BookingPage extends CommonMethods {

    WebDriver driver;

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

    public BookingPage(WebDriver driver) {
        this.driver = driver;
    }

    public void selectNumberOfHours(int hours) {
        waitUntilAnyElementVisible(hoursCount, 1000L);
        clickOnListOfElement(hoursCount, hours);
    }

    public void selectNumberOfCleaners(int cleaners) {
        waitUntilAnyElementVisible(cleanersCount, 1000L);
        clickOnListOfElement(cleanersCount, cleaners);
    }

    public void selectMaterialAndClickNext(String material) {
        if (material.equalsIgnoreCase("Yes")) {
            click(materialYes);
        } else {
            click(materialNo);
        }
        click(nextCTA);
    }

    public void selectLocation(String location) {
        clickAndSendKeys(locationInput, location);
        click(locationFirst);
        hardWait(5000L);
        click(confirmLocation);
        hardWait(5000L);
        click(adNextCTA);
    }

    public void selectBookingType(String bookingType) {
        if (bookingType.equalsIgnoreCase("One Time")) {
            click(oneTimeBooking);
        } else if (bookingType.equalsIgnoreCase("Weekly")) {
            click(weeklyBooking);
        }
        click(selectCTa);
    }

    public void selectFirstCleaner() {
        waitUntilAnyElementVisible(firstAvailableCleaner, 5000L);
        clickOnListOfElement(firstAvailableCleaner, 1);
    }

    public void selectFirstAvailableDateTimeAndClickNext() {
        scrollToElementWithExtraOffset(dateRange, -100);
        waitUntilAnyElementVisible(availableDates, 5000L);
        clickOnListOfElement(availableDates, 1);
        click(nextCTA);
    }

    public void loginSuccessfully() {
        clickAndSendKeys(enterLoginNum, Constant.loginNumber);
        click(loginContinueCTA);
        enterOTP();
    }

    public void enterOTP() {
        char otp[] = Constant.loginOTP.toCharArray();
        hardWait(5000L);
        for (int i = 0; i <= otp.length - 1; i++) {
            clickAndSendKeys(enterOTP.get(i), String.valueOf(otp[i]));
        }
    }
}
