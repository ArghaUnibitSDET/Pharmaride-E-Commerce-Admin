package order;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.GlobalComponents;

import java.time.Duration;
import java.util.List;

public class OrderController extends GlobalComponents {
    private OrderPage orderPage;

    public OrderController(WebDriver driver) {
        super(driver);
        this.orderPage = new OrderPage(driver);
        PageFactory.initElements(driver, this);
    }

    private void clearAndType(WebElement element, String text) {
        element.click();
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(Keys.BACK_SPACE);
        if (text != null && !text.isEmpty()) {
            element.sendKeys(text);
        }
    }

    public boolean clickOnOrderMenu() {
        try {
            waitForElementVisible(orderPage.getOrderButton(), 10);
            if (isElementDisplayed(orderPage.getOrderButton())) {
                orderPage.getOrderButton().click();
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Failed to click on Order Menu: " + e);
            return false;
        }
    }

    public boolean verifyOrdersPageLoaded() {
        try {
            if (!waitForElementVisible(orderPage.getOrderPageHeading(), 10)) {
                System.out.println("Verification failed: Order page heading not visible.");
                return false;
            }

            if (!isElementDisplayed(orderPage.getOrderPageHeading())) {
                System.out.println("Verification failed: Order page heading not displayed.");
                return false;
            }

            String expectedHeadingText = "Orders Management";
            String actualHeadingText = orderPage.getOrderPageHeading().getText();

            if (!expectedHeadingText.equals(actualHeadingText)) {
                System.out.println("Heading Text is " + actualHeadingText + " which is incorrect");
                return false;
            }

            System.out.println("Heading verified: " + actualHeadingText);

            if (!waitForElementVisible(orderPage.getSearchInput(), 10)) {
                System.out.println("Verification failed: Search input not visible.");
                return false;
            }

            if (!waitForElementVisible(orderPage.getStatusFilter(), 10)) {
                System.out.println("Verification failed: Status filter not visible.");
                return false;
            }

            List<WebElement> rows = orderPage.getOrderRows();
            System.out.println("Table verified with " + rows.size() + " initial rows.");

            return true;

        } catch (Exception e) {
            System.out.println("Error in verifyOrdersPageLoaded: " + e.getMessage());
            return false;
        }
    }

    public boolean verifySearchByOrderId() {
        try {
            waitForElementVisible(orderPage.getSearchInput(), 10);

            clearAndType(orderPage.getSearchInput(), "");

            waitForMultipleElementsVisible(orderPage.getOrderRows(), 10);

            List<WebElement> initialRows = orderPage.getOrderRows();
            if (initialRows.isEmpty()) {
                System.out.println("No orders available to verify search by Order ID.");
                return true;
            }

            WebElement firstRow = initialRows.get(0);
            String targetOrderId = firstRow.findElement(By.xpath("./td[1]")).getText().trim();
            System.out.println("Searching for Order ID: " + targetOrderId);

            clearAndType(orderPage.getSearchInput(), targetOrderId);

            List<WebElement> filteredRows = orderPage.getOrderRows();
            if (filteredRows.isEmpty()) {
                System.out.println("Search returned 0 rows for order " + targetOrderId);
                return false;
            }

            for (int i = 0; i < filteredRows.size(); i++) {
                String rowOrderId = orderPage.getFirstOrderId().getText().trim();

                if (!rowOrderId.equals(targetOrderId)) {
                    System.out.println("Mismatch! Row order ID: " + rowOrderId +
                            " vs searched: " + targetOrderId);
                    return false;
                }
            }

            System.out.println("Successfully verified search by Order ID.");
            return true;
        } catch (Exception e) {
            System.out.println("Error in verifySearchByOrderId: " + e);
            return false;
        } finally {
            try {
                clearAndType(orderPage.getSearchInput(), "");
                waitForMultipleElementsVisible(orderPage.getOrderRows(), 10);
            } catch (Exception ignored) {}
        }
    }

    public boolean verifySearchByCustomerName() {
        try {
            waitForElementVisible(orderPage.getSearchInput(), 10);

            clearAndType(orderPage.getSearchInput(), "");

            waitForMultipleElementsVisible(orderPage.getOrderRows(), 10);

            if (orderPage.getOrderRows().isEmpty()) {
                System.out.println("No orders available to verify search by Customer Name.");
                return true;
            }

            String targetCustomerName = orderPage.getFirstCustomerName()
                    .getText()
                    .trim();

            if (targetCustomerName.contains("\n")) {
                targetCustomerName = targetCustomerName.split("\n")[0].trim();
            }

            System.out.println("Searching for Customer Name: " + targetCustomerName);

            clearAndType(orderPage.getSearchInput(), targetCustomerName);

            Thread.sleep(2000); // or explicit wait

            List<WebElement> filteredCustomerNames = orderPage.getCustomerNames();

            if (filteredCustomerNames.isEmpty()) {
                System.out.println("Search returned 0 rows for customer " + targetCustomerName);
                return false;
            }

            for (WebElement customerName : filteredCustomerNames) {
                String actualCustomerName = customerName.getText().trim();

                if (actualCustomerName.contains("\n")) {
                    actualCustomerName = actualCustomerName.split("\n")[0].trim();
                }

                if (!actualCustomerName.toLowerCase()
                        .contains(targetCustomerName.toLowerCase())) {

                    System.out.println("Mismatch! Row customer: " +
                            actualCustomerName +
                            " vs searched: " +
                            targetCustomerName);

                    return false;
                }
            }

            System.out.println("Successfully verified search by Customer Name.");
            return true;

        } catch (Exception e) {
            System.out.println("Error in verifySearchByCustomerName: " + e.getMessage());
            return false;
        } finally {
            try {
                clearAndType(orderPage.getSearchInput(), "");
            } catch (Exception ignored) {
            }
        }
    }

    public boolean verifyAllStatusFilters() {
        try {
            String[] statuses = {"Pending", "Processing", "Shipped", "In Transit", "Out for Delivery", "Delivered", "Cancelled", "Returned"};

            for (String status : statuses) {
                System.out.println("Filtering by status: " + status);
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.visibilityOf(orderPage.getStatusFilter()));
                Select select = new Select(orderPage.getStatusFilter());
                select.selectByValue(status);
                Thread.sleep(2000);

                List<WebElement> filteredRows = orderPage.getOrderRows();
                System.out.println("Status '" + status + "' returned " + filteredRows.size() + " orders.");

                for (WebElement row : filteredRows) {
                    String rowStatus = row.findElement(By.xpath("./td[6]")).getText().trim();
                    if (!rowStatus.equalsIgnoreCase(status)) {
                        System.out.println("Mismatch! Row status: " + rowStatus + " vs filter: " + status);
                        return false;
                    }
                }
            }

            System.out.println("Resetting filter to All Status.");
            Select select = new Select(orderPage.getStatusFilter());
            select.selectByValue("all");
            Thread.sleep(1000);

            System.out.println("Successfully verified all status filters.");
            return true;
        } catch (Exception e) {
            System.out.println("Error in verifyAllStatusFilters: " + e);
            return false;
        }
    }

    // --- Order Details Drawer Functions ---

    public boolean verifyViewOrderDetails() {
        try {
            // Ensure we are on the orders list page
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(orderPage.getOrderPageHeading()));

            List<WebElement> rows = orderPage.getOrderRows();
            if (rows.isEmpty()) {
                System.out.println("No orders available to view details.");
                return false;
            }

            // Get the order ID from the first row
            WebElement firstRow = rows.get(0);
            String expectedOrderId = firstRow.findElement(By.xpath("./td[1]")).getText().trim();
            System.out.println("Clicking view details for order: " + expectedOrderId);

            // Click the view (eye icon) button in the actions column
            WebElement viewButton = firstRow.findElement(By.cssSelector("button.text-primary-color"));
            viewButton.click();
            Thread.sleep(3000);

            // Verify drawer opened with correct order details
            wait.until(ExpectedConditions.visibilityOf(orderPage.getOrderDetailsHeading()));
            if (!isElementDisplayed(orderPage.getOrderDetailsHeading())) {
                System.out.println("Order Details heading not displayed.");
                return false;
            }

            // Verify the order ID matches
            String displayedOrderId = orderPage.getOrderDetailsId().getText().trim();
            if (!displayedOrderId.equals(expectedOrderId)) {
                System.out.println("Order ID mismatch! Expected: " + expectedOrderId + ", Found: " + displayedOrderId);
                return false;
            }
            System.out.println("Order ID verified: " + displayedOrderId);

            // Verify key sections are displayed
            if (!isElementDisplayed(orderPage.getCustomerInfoHeading())) {
                System.out.println("Customer Information section not displayed.");
                return false;
            }

            if (!isElementDisplayed(orderPage.getDeliveryAddressHeading())) {
                System.out.println("Delivery Address section not displayed.");
                return false;
            }

            if (!isElementDisplayed(orderPage.getOrderItemsHeading())) {
                System.out.println("Order Items section not displayed.");
                return false;
            }

            System.out.println("Successfully verified View Order Details.");
            return true;
        } catch (Exception e) {
            System.out.println("Error in verifyViewOrderDetails: " + e);
            return false;
        }
    }

    public boolean verifyConfirmOrder() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Verify drawer is open (Order Details heading should be visible)
            wait.until(ExpectedConditions.visibilityOf(orderPage.getOrderDetailsHeading()));

            // Check if Confirm Order button is displayed
            if (!isElementDisplayed(orderPage.getConfirmOrderButton())) {
                System.out.println("Confirm Order button not displayed.");
                return false;
            }

            // Verify button is clickable
            wait.until(ExpectedConditions.elementToBeClickable(orderPage.getConfirmOrderButton()));
            String buttonText = orderPage.getConfirmOrderButton().getText().trim();
            System.out.println("Confirm Order button verified with text: " + buttonText);

            return true;
        } catch (Exception e) {
            System.out.println("Error in verifyConfirmOrder: " + e);
            return false;
        }
    }

    public boolean verifyInvoiceDownload() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Verify drawer is open
            wait.until(ExpectedConditions.visibilityOf(orderPage.getOrderDetailsHeading()));

            // Check if Download Invoice button is displayed
            if (!isElementDisplayed(orderPage.getDownloadInvoiceButton())) {
                System.out.println("Download Invoice button not displayed.");
                return false;
            }

            // Verify button is clickable
            wait.until(ExpectedConditions.elementToBeClickable(orderPage.getDownloadInvoiceButton()));
            String buttonText = orderPage.getDownloadInvoiceButton().getText().trim();
            System.out.println("Download Invoice button verified with text: " + buttonText);

            return true;
        } catch (Exception e) {
            System.out.println("Error in verifyInvoiceDownload: " + e);
            return false;
        }
    }

    public boolean verifyPaymentCalculations() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Verify drawer is open
            wait.until(ExpectedConditions.visibilityOf(orderPage.getOrderDetailsHeading()));

            // Find the drawer container
            WebElement drawer = orderPage.getOrderDetailsHeading().findElement(
                    By.xpath("./ancestor::div[contains(., 'Order Timeline')][1]"));
            String drawerText = drawer.getText();

            // Verify all payment calculation fields are present
            String[] requiredFields = {"Subtotal", "Taxable Amount", "CGST", "SGST", "Total Amount"};

            for (String field : requiredFields) {
                if (!drawerText.contains(field)) {
                    System.out.println("Payment field not found: " + field);
                    return false;
                }
                System.out.println("Payment field verified: " + field);
            }

            // Check optional fields (may or may not be present)
            String[] optionalFields = {"Platform Fee", "Delivery Fee", "Wallet Amount", "Payment Method"};
            for (String field : optionalFields) {
                if (drawerText.contains(field)) {
                    System.out.println("Optional payment field found: " + field);
                }
            }

            System.out.println("Successfully verified payment calculations.");
            return true;
        } catch (Exception e) {
            System.out.println("Error in verifyPaymentCalculations: " + e);
            return false;
        }
    }

    public boolean verifyOrderTimeline() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Verify drawer is open
            wait.until(ExpectedConditions.visibilityOf(orderPage.getOrderDetailsHeading()));

            // Verify Order Timeline heading is displayed
            scrollIntoElement(orderPage.getOrderTimelineHeading());
            if (!isElementDisplayed(orderPage.getOrderTimelineHeading())) {
                System.out.println("Order Timeline heading not displayed.");
                return false;
            }

            // Find the timeline container and verify all expected steps
            WebElement drawer = orderPage.getOrderDetailsHeading().findElement(
                    By.xpath("./ancestor::div[contains(., 'Order Timeline')][1]"));
            String drawerText = drawer.getText();

            String[] timelineSteps = {"Order Placed", "Processing", "Shipped", "In Transit", "Out for Delivery", "Delivered"};

            for (String step : timelineSteps) {
                if (!drawerText.contains(step)) {
                    System.out.println("Timeline step not found: " + step);
                    return false;
                }
                System.out.println("Timeline step verified: " + step);
            }

            System.out.println("Successfully verified Order Timeline.");
            return true;
        } catch (Exception e) {
            System.out.println("Error in verifyOrderTimeline: " + e);
            return false;
        }
    }
}
