package utils;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GlobalComponents {

    public WebDriver driver;

    public GlobalComponents(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean scrollIntoElement(WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView();", element);
            // System.out.println("Scrolled into view successfully");
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean waitForElementVisible(WebDriver driver, WebElement element, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean explicitlyCheckVisibility(WebElement element) {
        try {
            WebElement myDynamicElement = (new WebDriverWait(driver, Duration.ofSeconds(20)))
                    .until(ExpectedConditions.visibilityOf(element));
            if (myDynamicElement.isDisplayed()) {
                return true;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        System.out.println("Element not found");
        return false;

    }

    public boolean scrollDownByPixels(int pixelsToScroll) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0," + pixelsToScroll + ");");
            return true;
        } catch (Exception e) {
            System.out.println("can not scroll by pixel");
            return false;
        }

    }

    public boolean scrollToBottom() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            return true;
        } catch (Exception e) {
            System.out.println("can not scroll to bottom");
            return false;
        }

    }

    public boolean isElementDisplayed(WebElement ele) {
        if (ele == null) {
            return false;
        }
        return ele.isDisplayed();
    }

    public boolean isListElementsDisplayed(List<WebElement> lists) {
        System.out.println(lists.size());

        for (WebElement element : lists) {
            return element.isDisplayed();
        }
        return false;
    }

    public boolean isActuallyDisplayed(WebElement element) {
        Object result = ((JavascriptExecutor) driver).executeScript(
                "var e = arguments[0];"
                        + "var rect = e.getBoundingClientRect();"
                        + "return (rect.width > 0 && rect.height > 0);",
                element
        );

        boolean isActuallyVisible = Boolean.TRUE.equals(result);

        System.out.println("Custom visible: " + isActuallyVisible);
        return isActuallyVisible;
    }
}