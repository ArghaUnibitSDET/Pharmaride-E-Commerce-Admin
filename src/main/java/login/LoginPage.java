package login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    public  LoginPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    @FindBy(css = "#email")
    public WebElement emailInput;

    @FindBy(css = "button[type='submit']")
    public WebElement sendOtpButton;

    @FindBy(xpath = "(//input[@type='text'])[1]")
    public WebElement otpField1;

    @FindBy(xpath = "(//input[@type='text'])[2]")
    public WebElement otpField2;

    @FindBy(xpath = "(//input[@type='text'])[3]")
    public WebElement otpField3;

    @FindBy(xpath = "(//input[@type='text'])[4]")
    public WebElement otpField4;

    @FindBy(xpath = "(//input[@type='text'])[5]")
    public WebElement otpField5;

    @FindBy(xpath = "(//input[@type='text'])[6]")
    public WebElement otpField6;

    @FindBy(css = "button[type='submit']")
    public WebElement verifyOtpButton;


    public WebElement getEmailInput(){
        return emailInput;
    }

    public WebElement getSendOtpButton(){
        return sendOtpButton;
    }

    public WebElement getOtpField1(){
        return otpField1;
    }

    public WebElement getOtpField2(){
        return otpField2;
    }

    public WebElement getOtpField3(){
        return otpField3;
    }

    public WebElement getOtpField4(){
        return otpField4;
    }

    public WebElement getOtpField5(){
        return otpField5;
    }

    public WebElement getOtpField6(){
        return otpField6;
    }

    public WebElement getVerifyOtpButton(){
        return verifyOtpButton;
    }

}
