package login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.GlobalComponents;

import java.time.Duration;

public class LoginController extends GlobalComponents {
    private LoginPage loginPage;

    public LoginController(WebDriver driver) {
        super(driver);
        loginPage = new LoginPage(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean putEmail(String email) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            //System.out.println("Received Email"+email);

            wait.until(ExpectedConditions.visibilityOf(loginPage.getEmailInput()));
            if (isElementDisplayed(loginPage.getEmailInput())) {
                loginPage.getEmailInput().sendKeys(email);
            } else {
                System.out.println("Email Input Failed");
            }
            return true;
        } catch (Exception e) {
            System.out.println("Failed to put Email" + e);
            return false;
        }
    }

    public boolean clickOnSendOtpButton() {
        try {
            if (isElementDisplayed(loginPage.getSendOtpButton())) {
                loginPage.sendOtpButton.click();
            } else {
                System.out.println("Send Otp Button Click Failed");
            }
            return true;
        } catch (Exception e) {
            System.out.println("Failed to click on send otp button" + e);
            return false;
        }
    }

    public boolean putOtp(String... otp) {
        try {
            for (int i = 0; i < otp.length; i++) {
                int index = i + 1;
                By locator = By.xpath("(//input[@type='text'])[" + index + "]");

                boolean vis = waitForElementVisible(locator, 30);
                boolean clickable = waitForElementToBeClickable(locator, 30);
                if (!vis || !clickable) {
                    System.out.println("Otp Field " + index + " not ready (vis=" + vis + ", clickable=" + clickable + ")");
                    return false;
                }

                WebElement field = driver.findElement(locator);
                scrollIntoElement(field);

                try {
                    field.clear();
                    field.click();
                    field.sendKeys(otp[i]);
                } catch (ElementNotInteractableException enie) {
                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input'));",
                            field, otp[i]
                    );
                }
            }
            return true;

        } catch (Exception e) {
            System.out.println("Failed To Put OTP" + e);
            return false;
        }
    }

    public boolean clickOnVerifyOtpButton() {
        try {
            if (isElementDisplayed(loginPage.getVerifyOtpButton())) {
                loginPage.getVerifyOtpButton().click();
            } else {
                System.out.println("Verify Otp Button Click Failed");
            }

            return true;
        } catch (Exception e) {
            System.out.println("Failed to click on Verify Otp Button" + e);
            return false;
        }

    }


}