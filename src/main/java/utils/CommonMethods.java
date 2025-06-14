package utils;

import driver.DriverFactory;
import driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class CommonMethods {

    protected WebDriver driver;
    DriverManager driverManager = new DriverFactory();

    public CommonMethods() {
        this.driver = driverManager.getDriver();
        PageFactory.initElements(driver, this);
    }

    public void click(WebElement element) {
        waitUntilElementVisible(element, 1000L);
        waitForVisibility(element);
        isElementDisplayed(element);
        waitTillElementIsClickable(element);
        element.click();
    }

    public void clickOnElementWithDynamicXpath(String xpath) {
        WebElement element = driver.findElement(By.xpath(xpath));
        click(element);
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
        click(element.get(i));
    }

    public void scrollToElementWithExtraOffset(WebElement element, int yOffset) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", element);
        js.executeScript("window.scrollBy(0, arguments[0]);", yOffset);
    }

    public void scrollToElement(WebElement element) {
        waitForDomLoad();
        hardWait(5000L);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }

    public void waitForDomLoad() {
        ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
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
            explicitWait(element);
        }
    }

    public void waitUntilAnyElementVisible(List<WebElement> elements, long timeoutInMillis) {
        long endTime = System.currentTimeMillis() + (timeoutInMillis * 10);

        while (System.currentTimeMillis() < endTime) {
            for (WebElement element : elements) {
                try {
                    if (element.isDisplayed()) {
                        return;
                    }
                } catch (Exception e) {
                }
            }
            hardWait(5000L);
        }

        throw new NoSuchElementException("No element in the list became visible within the timeout.");
    }

    public void waitUntilElementVisible(WebElement elements, long timeoutInMillis) {
        long endTime = System.currentTimeMillis() + (timeoutInMillis * 10);

        while (System.currentTimeMillis() < endTime) {
            try {
                if (elements.isDisplayed()) {
                    return;
                }
            } catch (Exception e) {
            }
            hardWait(1000L);
        }
        throw new NoSuchElementException("No element in the list became visible within the timeout.");
    }

    public void explicitWait(WebElement element) {
        waitForDomLoad();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitTillElementIsClickable(WebElement element) {
        waitForDomLoad();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        wait.until(ExpectedConditions.elementToBeClickable(element));

    }
}
