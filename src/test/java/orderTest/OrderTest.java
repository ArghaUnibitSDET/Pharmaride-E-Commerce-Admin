package orderTest;

import base.BaseTest;
import login.LoginController;
import order.OrderController;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.ConfigReader;

public class OrderTest extends BaseTest {
    LoginController loginController;
    OrderController orderController;

    @BeforeTest
    public void setUp() {
        setDriver();
        loginController = new LoginController(driver);
        orderController = new OrderController(driver);
    }

    @Test(priority = 1)
    public void loginToAdminDashboard(){
        boolean isEmailInput = loginController.putEmail(ConfigReader.get("email"));
        Assert.assertTrue(isEmailInput, "Email not Entered");

        boolean isClickOnSendOtpButton = loginController.clickOnSendOtpButton();
        Assert.assertTrue(isClickOnSendOtpButton, "Send Otp Button Not Clicked");

        boolean isOtpFilled = loginController.putOtp(ConfigReader.get("otp1"),
                ConfigReader.get("otp2"),
                ConfigReader.get("otp3"),
                ConfigReader.get("otp4"),
                ConfigReader.get("otp5"),
                ConfigReader.get("otp6")
        );
        Assert.assertTrue(isOtpFilled, "Otp Not Filled");

        boolean isClickedOnVerifyOtpButton = loginController.clickOnVerifyOtpButton();
        Assert.assertTrue(isClickedOnVerifyOtpButton, "Verify Otp Button Not Clicked");
    }

    @Test(priority = 2)
    public void verifyOrdersPageLoaded(){
        boolean clickedOrderMenu = orderController.clickOnOrderMenu();
        Assert.assertTrue(clickedOrderMenu, "Order Menu Button Not Clicked");

        boolean isPageLoaded = orderController.verifyOrdersPageLoaded();
        Assert.assertTrue(isPageLoaded, "Orders Page failed to load completely.");

    }

    @Test(priority = 3,enabled = true)
    public void verifySearchByOrderId(){
        boolean isVerified = orderController.verifySearchByOrderId();
        Assert.assertTrue(isVerified, "Search by Order ID verification failed.");
    }

    @Test(priority = 4, enabled = true)
    public void verifySearchByCustomerName(){
        boolean isVerified = orderController.verifySearchByCustomerName();
        Assert.assertTrue(isVerified, "Search by Customer Name verification failed.");
    }

    @Test(priority = 5, enabled = false)
    public void verifyAllStatusFilters() throws InterruptedException {
        boolean isVerified = orderController.verifyAllStatusFilters();
        Assert.assertTrue(isVerified, "All Status filters verification failed.");

        Thread.sleep(500);
    }

    @Test(priority = 6, enabled = true)
    public void verifyViewOrderDetails() throws InterruptedException {
        boolean isVerified = orderController.verifyViewOrderDetails();
        Assert.assertTrue(isVerified, "View Order Details verification failed.");

        Thread.sleep(500);
    }

    @Test(priority = 7, enabled = true)
    public void verifyConfirmOrder() throws InterruptedException {
        boolean isVerified = orderController.verifyConfirmOrder();
        Assert.assertTrue(isVerified, "Confirm Order button verification failed.");

        Thread.sleep(500);
    }

    @Test(priority = 8, enabled = true)
    public void verifyInvoiceDownload() throws InterruptedException {
        boolean isVerified = orderController.verifyInvoiceDownload();
        Assert.assertTrue(isVerified, "Invoice Download button verification failed.");

        Thread.sleep(500);
    }

    @Test(priority = 9, enabled = true)
    public void verifyPaymentCalculations() throws InterruptedException {
        boolean isVerified = orderController.verifyPaymentCalculations();
        Assert.assertTrue(isVerified, "Payment Calculations verification failed.");

        Thread.sleep(500);
    }

    @Test(priority = 10, enabled = true)
    public void verifyOrderTimeline() throws InterruptedException {
        boolean isVerified = orderController.verifyOrderTimeline();
        Assert.assertTrue(isVerified, "Order Timeline verification failed.");

        Thread.sleep(500);
    }
}
