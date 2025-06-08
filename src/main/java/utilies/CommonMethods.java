package utilies;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class CommonMethods {

    protected WebDriver driver;

    public CommonMethods() {
        this.driver = utilies.DriverFactory.getDriver();
        PageFactory.initElements(driver, this);
    }

    public void click(WebElement element) {
        waitForVisibility(element);
        isElementDisplayed(element);
        element.click();
    }

    public void clickAndSendKeys(WebElement element, String text) {
        click(element);
        element.clear();
        element.sendKeys(text);
    }

    public void waitForVisibility(WebElement element) {
        waitForDomLoad();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void clickOnListOfElement(List<WebElement> element, int i) {
        System.out.println(element.get(i).getText());
        click(element.get(i));
    }

    public void scrollToElementWithExtraOffset(WebElement element, int yOffset) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", element);
        js.executeScript("window.scrollBy(0, arguments[0]);", yOffset);
    }

    public void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }

    public void waitForDomLoad() {
        ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals(
                        "complete");
            }
        };
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        wait.until(pageLoadCondition);
    }

    public void hardWait(Long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void isElementDisplayed(WebElement element) {
        if (!element.isDisplayed()) {
            hardWait(5000L);
        }
    }

    public void waitUntilAnyElementVisible(List<WebElement> elements, long timeoutInMillis) {
        long endTime = System.currentTimeMillis() + (timeoutInMillis * 5);

        while (System.currentTimeMillis() < endTime) {
            for (WebElement element : elements) {
                try {
                    if (element.isDisplayed()) {
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            hardWait(5000L);
        }

        throw new NoSuchElementException("No element in the list became visible within the timeout.");
    }

    public void explicitWait(WebElement element) {
        waitForDomLoad();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOf(element));
    }
}
