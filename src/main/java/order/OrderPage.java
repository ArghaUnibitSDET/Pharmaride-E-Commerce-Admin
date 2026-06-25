package order;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

public class OrderPage {
    public OrderPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    // --- Orders List Page ---

    @FindBy(css = "a[href='/orders']")
    public WebElement orderButton;

    @FindBy(css = "h1[class='text-lg md:text-2xl']")
    public WebElement orderPageHeading;

    @FindBy(css = "input[placeholder='Search by Order ID or Customer Name']")
    public WebElement searchInput;

    @FindBy(css = "#status-filter")
    public WebElement statusFilter;

    @FindBy(css = "tbody tr")
    public List<WebElement> orderRows;

    @FindBy(xpath = "//td[1]")
    public List<WebElement> orderIds;

    @FindBy(xpath = "(//td)[2]")
    public List<WebElement> customerNames;

    // --- Order Details Drawer ---

    @FindBy(xpath = "//h1[text()='Order Details']")
    public WebElement orderDetailsHeading;

    @FindBy(xpath = "//h1[text()='Order Details']/following-sibling::p")
    public WebElement orderDetailsId;

    @FindBy(xpath = "//button[.//text()[contains(.,'Download Invoice')]]")
    public WebElement downloadInvoiceButton;

    @FindBy(xpath = "//button[.//text()[contains(.,'Confirm Order')]]")
    public WebElement confirmOrderButton;

    @FindBy(xpath = "//h1[text()='Customer Information']")
    public WebElement customerInfoHeading;

    @FindBy(xpath = "//h1[text()='Delivery Address']")
    public WebElement deliveryAddressHeading;

    @FindBy(xpath = "//h2[text()='Order Items']")
    public WebElement orderItemsHeading;

    @FindBy(xpath = "//h1[text()='Order Timeline']")
    public WebElement orderTimelineHeading;

    // --- Getters ---

    public WebElement getOrderButton() {
        return orderButton;
    }

    public WebElement getOrderPageHeading() {
        return orderPageHeading;
    }

    public WebElement getSearchInput() {
        return searchInput;
    }

    public WebElement getStatusFilter() {
        return statusFilter;
    }

    public List<WebElement> getOrderRows() {
        return orderRows;
    }

    public WebElement getOrderDetailsHeading() {
        return orderDetailsHeading;
    }

    public WebElement getOrderDetailsId() {
        return orderDetailsId;
    }

    public WebElement getDownloadInvoiceButton() {
        return downloadInvoiceButton;
    }

    public WebElement getConfirmOrderButton() {
        return confirmOrderButton;
    }

    public WebElement getCustomerInfoHeading() {
        return customerInfoHeading;
    }

    public WebElement getDeliveryAddressHeading() {
        return deliveryAddressHeading;
    }

    public WebElement getOrderItemsHeading() {
        return orderItemsHeading;
    }

    public WebElement getOrderTimelineHeading() {
        return orderTimelineHeading;
    }

    public WebElement getFirstOrderId() {
        return orderIds.get(0);
    }

    public List<WebElement> getCustomerNames() {
        return customerNames;
    }

    public WebElement getFirstCustomerName() {
        return customerNames.get(0);
    }
}
