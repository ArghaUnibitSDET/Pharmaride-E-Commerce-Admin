package login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.GlobalComponents;

import java.sql.SQLOutput;
import java.time.Duration;

public class LoginController extends GlobalComponents {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    private LoginPage loginPage;

    public LoginController(WebDriver driver){
        super(driver);
        loginPage = new LoginPage(driver);
        PageFactory.initElements(driver,this);
    }

    public boolean putEmail(String email){
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            //System.out.println("Received Email"+email);

            wait.until(ExpectedConditions.visibilityOf(loginPage.getEmailInput()));
            if(isElementDisplayed(loginPage.getEmailInput())){
                loginPage.getEmailInput().sendKeys(email);
            }
            else {
                System.out.println("Email Input Failed");
            }
            return true;
        }
        catch (Exception e){
            System.out.println("Failed to put Email" + e);
            return false;
        }
    }

    public boolean clickOnSendOtpButton(){
        try{
            if(isElementDisplayed(loginPage.getSendOtpButton())){
                loginPage.sendOtpButton.click();
            }
            else {
                System.out.println("Send Otp Button Click Failed");
            }
            return true;
        }
        catch (Exception e){
            System.out.println("Failed to click on send otp button" + e);
            return false;
        }
    }

    public boolean putOtp(String... otp) {
        try {
            WebElement[] otpFields = {
                    loginPage.getOtpField1(),
                    loginPage.getOtpField2(),
                    loginPage.getOtpField3(),
                    loginPage.getOtpField4(),
                    loginPage.getOtpField5(),
                    loginPage.getOtpField6()
            };

            for (int i = 0; i < otp.length; i++) {
                otpFields[i].sendKeys(otp[i]);
            }
            return true;

        } catch (Exception e) {
            System.out.println("Failed To Put OTP" + e);
            return false;
        }
    }

    public boolean clickOnVerifyOtpButton(){
        try{
            if(isElementDisplayed(loginPage.getVerifyOtpButton())){
                loginPage.getVerifyOtpButton().click();
            }
            else {
                System.out.println("Failed to click");
            }
            return true;
        }
        catch (Exception e){
            System.out.println("Failed to click on Verify Otp Button"+e);
            return false;
        }
    }

}
