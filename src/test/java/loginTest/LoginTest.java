package loginTest;

import base.BaseTest;
import login.LoginController;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.ConfigReader;

public class LoginTest  extends BaseTest {
    LoginController loginController;
    @BeforeTest
    public void setUp(){
        setDriver();
        loginController = new LoginController(driver);
    }

    @Test(priority = 1)
    public void verifyEmailInputAndClickOnSendOtpButton() throws InterruptedException {
        boolean isEmailInput = loginController.putEmail(ConfigReader.get("email"));
        Assert.assertTrue(isEmailInput, "Email not Entered");

        boolean isClickOnSendOtpButton = loginController.clickOnSendOtpButton();
        Assert.assertTrue(isClickOnSendOtpButton, "Send Otp Button Not Clicked");

        Thread.sleep(5000);
    }

    @Test(priority = 2)
    public void putOtpAndClickOnVerifyOtpButton() throws InterruptedException {
        boolean isOtpFilled = loginController.putOtp(ConfigReader.get("otp1"),
                ConfigReader.get("otp2"),
                ConfigReader.get("otp3"),
                ConfigReader.get("otp4"),
                ConfigReader.get("otp5"),
                ConfigReader.get("otp6")
        );
        Assert.assertTrue(isOtpFilled,"Otp Not Filled");

        boolean isClickedOnVerifyOtpButton = loginController.clickOnVerifyOtpButton();
        Assert.assertTrue(isClickedOnVerifyOtpButton,"Verify Otp Button Not Clicked");

        Thread.sleep(5000);
      }
}
