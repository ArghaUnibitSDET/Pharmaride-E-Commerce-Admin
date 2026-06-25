package login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "#email")
    public WebElement emailInput;

    @FindBy(xpath = "(//button[@type='submit'])[1]")
    public WebElement sendOtpButton;

    @FindBy(xpath = "//button[normalize-space()='Verify OTP']")
    public WebElement verifyOtpButton;

    public WebElement getEmailInput() {
        return emailInput;
    }

    public WebElement getSendOtpButton() {
        return sendOtpButton;
    }

    public WebElement getVerifyOtpButton() {
        return verifyOtpButton;
    }

}
