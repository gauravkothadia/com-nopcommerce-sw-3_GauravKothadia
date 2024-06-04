package electronics;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import utilities.Utility;

import java.util.List;

public class ElectronicsTest extends Utility {
    String baseUrl = "https://demo.nopcommerce.com/";

    @Before
    public void setUp() {
        openBrowser(baseUrl);
    }

    @Test
    public void verifyUserShouldNavigateToCellPhonesPageSuccessfully() throws InterruptedException {
//      1.1 Mouse Hover on “Electronics” Tab
//      1.2 Mouse Hover on “Cell phones” and click
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath("//ul[@class='top-menu notmobile']//a[normalize-space()='Electronics']")))
                .moveToElement(driver.findElement(By.xpath("//ul[@class='top-menu notmobile']//a[normalize-space()='Cell phones']"))).click().build().perform();
//      1.3 Verify the text “Cell phones”
        Assert.assertEquals("Cell phones", getTextFromElement(By.xpath("//h1[normalize-space()='Cell phones']")));
    }

    @Test
    public void verifyThatTheProductAddedSuccessfullyAndPlaceOrderSuccessfully() throws InterruptedException {
//      2.1 Mouse Hover on “Electronics” Tab
//      2.2 Mouse Hover on “Cell phones” and click
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath("//ul[@class='top-menu notmobile']//a[normalize-space()='Electronics']")))
                .moveToElement(driver.findElement(By.xpath("//ul[@class='top-menu notmobile']//a[normalize-space()='Cell phones']"))).click().build().perform();
//      2.3 Verify the text “Cell phones”
        Assert.assertEquals("Cell phones", getTextFromElement(By.xpath("//h1[normalize-space()='Cell phones']")));
        //       2.4 Click on List View Tab
        clickOnElement(By.xpath("//a[normalize-space()='List']"));
        // 2.5 Click on product name “Nokia Lumia 1020” link
        String expectedProduct = "Nokia Lumia 1020";
        List<WebElement> listOfProducts = findMultipleElements(By.cssSelector("div.products-wrapper h2.product-title>a"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        for (WebElement ele : listOfProducts) {
            js.executeScript("arguments[0].scrollIntoView();", ele);
            if (ele.getText().equals(expectedProduct)) {
                js.executeScript("arguments[0].click();", ele);
            }
        }
        //2.6 Verify the text “Nokia Lumia 1020”
        Assert.assertEquals("Nokia Lumia 1020", getTextFromElement(By.xpath("//h1[normalize-space()='Nokia Lumia 1020']")));
        // 2.7 Verify the price “$349.00”
        Assert.assertEquals("$349.00", getTextFromElement(By.xpath("//div[@class='product-price']//span")));
        // 2.8 Change quantity to 2
        findSingleElement(By.xpath("//div[@class='add-to-cart-panel']//input")).clear();
        sendTextToElement(By.xpath("//div[@class='add-to-cart-panel']//input"), "2");
        // 2.9 Click on “ADD TO CART” tab
        clickOnElement(By.xpath("//div[@class='add-to-cart-panel']//button"));
        // 2.10 Verify the Message "The product has been added to your shopping cart" on Top
        // green Bar
        String expectedMessage = "The product has been added to your shopping cart";
        String actualMessage = getTextFromElement(By.cssSelector("div.bar-notification.success>p"));
        Assert.assertEquals(expectedMessage, actualMessage);
        // After that close the bar clicking on the cross button.
        clickOnElement(By.cssSelector("div.bar-notification.success>span.close"));
        // 2.11 Then MouseHover on "Shopping cart" and Click on "GO TO CART" button.
        WebElement shoppingCartLink = findSingleElement(By.xpath("//span[@class='cart-label']"));
        js.executeScript("arguments[0].scrollIntoView();", shoppingCartLink);
        js.executeScript("arguments[0].click();", findSingleElement(By.xpath("//button[normalize-space()='Go to cart']")));
//      2.12 Verify the message "Shopping cart"
        String expectedCartTitle = "Shopping cart";
        String shoppingCartTitle = getTextFromElement(By.xpath("//h1[normalize-space()='Shopping cart']"));
        Assert.assertEquals(expectedCartTitle, shoppingCartTitle);
        // 2.13 Verify the quantity is 2
        Assert.assertEquals("2", findSingleElement(By.xpath("//div[@class='product-quantity']//input")).getAttribute("value"));
        // 2.14 Verify the Total $698.00
        String expectedTotal = "$698.00";
        WebElement totalElement = findSingleElement(By.xpath("//tr[@class='order-total']//label[contains(text(),'Total:')]"));
        js.executeScript("arguments[0].scrollIntoView();", totalElement);
        String actualTotal = getTextFromElement(By.xpath("//tr[@class='order-total']//span[@class='value-summary']/strong"));
        Assert.assertEquals(expectedTotal, actualTotal);
//      2.15 click on checkbox “I agree with the terms of service”
        clickOnElement(By.xpath("//input[@id='termsofservice']"));
//      2.16 Click on “CHECKOUT”
        clickOnElement(By.xpath("//button[@id='checkout']"));
//      2.17 Verify the Text “Welcome, Please Sign In!”
        Assert.assertEquals("Welcome, Please Sign In!", getTextFromElement(By.xpath("//h1[normalize-space()='Welcome, Please Sign In!']")));
        // 2.18 Click on “REGISTER”
        clickOnElement(By.xpath("//button[normalize-space()='Register']"));
        // 2.19 Verify the text “Register”
        Assert.assertEquals("Register", getTextFromElement(By.xpath("//h1[normalize-space()='Register']")));
        // 2.20 Fill the mandatory fields
        clickOnElement(By.cssSelector("div.gender input#gender-male"));
        sendTextToElement(By.cssSelector("#FirstName"), "Gaurav");
        sendTextToElement(By.cssSelector("#LastName"), "Patel");
        selectByVisibleTextFromDropDown(By.xpath("//div[@class='inputs date-of-birth']//select[@name='DateOfBirthDay']"), "10");
        selectByVisibleTextFromDropDown(By.xpath("//div[@class='inputs date-of-birth']//select[@name='DateOfBirthMonth']"), "August");
        selectByVisibleTextFromDropDown(By.xpath("//div[@class='inputs date-of-birth']//select[@name='DateOfBirthYear']"), "1990");
        sendTextToElement(By.xpath("//input[@id='Email']"), "abc0@abc.com");
        sendTextToElement(By.xpath("//input[@id='Company']"), "ABC Ltd.");
        sendTextToElement(By.xpath("//input[@id='Password']"), "abcd@12345");
        sendTextToElement(By.xpath("//input[@id='ConfirmPassword']"), "abcd@12345");
        //2.21 Click on “REGISTER” Button
        clickOnElement(By.xpath("//button[@id='register-button']"));
        // 2.22 Verify the message “Your registration completed”
        Assert.assertEquals("Your registration completed", getTextFromElement(By.xpath("//div[contains(text(),'Your registration completed')]")));
        // 2.23 Click on “CONTINUE” tab
        clickOnElement(By.xpath("//a[@class='button-1 register-continue-button']"));
        // 2.24 Verify the text “Shopping card”
        Assert.assertEquals("Shopping cart", getTextFromElement(By.xpath("//h1[contains(text(),'Shopping cart')]")));
        // 2.25 click on checkbox “I agree with the terms of service”
        clickOnElement(By.xpath("//input[@id='termsofservice']"));
        // 2.26 Click on “CHECKOUT”
        clickOnElement(By.xpath("//button[@id='checkout']"));
        // 2.27 Fill the Mandatory fields
        selectByVisibleTextFromDropDown(By.xpath("//span[@class='required']/preceding-sibling::select"), "Argentina");
        sendTextToElement(By.xpath("//span[@class='required']/preceding-sibling::input[@id='BillingNewAddress_City']"), "London");
        sendTextToElement(By.xpath("//span[@class='required']/preceding-sibling::input[@id='BillingNewAddress_Address1']"), "London Road");
        sendTextToElement(By.xpath("//span[@class='required']/preceding-sibling::input[@id='BillingNewAddress_ZipPostalCode']"), "N1 5RU");
        sendTextToElement(By.xpath("//span[@class='required']/preceding-sibling::input[@id='BillingNewAddress_PhoneNumber']"), "07895462104");
        // 2.28 Click on “CONTINUE”
        clickOnElement(By.cssSelector("div#checkout-step-billing>div.buttons>button[onclick='if (!window.__cfRLUnblockHandlers) return false; Billing.save()']"));
        // 2.29 Click on Radio Button “2nd Day Air ($0.00)”
        clickOnElement(By.xpath("//div[@class='section shipping-method']//label[starts-with(text(),'2nd Day Air')]/preceding-sibling::input"));
        // 2.30 Click on “CONTINUE”
        clickOnElement(By.xpath("//button[@class='button-1 shipping-method-next-step-button']"));
        // 2.31 Select Radio Button “Credit Card”
        clickOnElement(By.xpath("//label[normalize-space()='Credit Card']/preceding-sibling::input"));
        clickOnElement(By.xpath("//button[@class='button-1 payment-method-next-step-button']"));
        // 2.32 Select “Visa” From Select credit card dropdown
        selectByVisibleTextFromDropDown(By.xpath("//select[@id='CreditCardType']"), "Visa");
        // 2.33 Fill all the details
        sendTextToElement(By.xpath("//input[@id='CardholderName']"), "Abc patel");
        sendTextToElement(By.xpath("//input[@id='CardNumber']"), "4916567672177329");
        selectByVisibleTextFromDropDown(By.xpath("//select[@id='ExpireMonth']"), "11");
        selectByVisibleTextFromDropDown(By.xpath("//select[@id='ExpireYear']"), "2025");
        sendTextToElement(By.xpath("//input[@id='CardCode']"), "589");
        // 2.34 Click on “CONTINUE”
        clickOnElement(By.xpath("//button[@class='button-1 payment-info-next-step-button']"));
        // 2.35 Verify “Payment Method” is “Credit Card”
        String expectedPaymentMethod = "Credit Card";
        String actualPaymentMethod = getTextFromElement(By.xpath("//li[@class='payment-method']//span[normalize-space()='Payment Method:']/following-sibling::span"));
        Assert.assertEquals(expectedPaymentMethod, actualPaymentMethod);
        // 2.36 Verify “Shipping Method” is “2nd Day Air”
        String expectedShippingMethod = "2nd Day Air";
        String actualShippingMethod = getTextFromElement(By.xpath("//span[normalize-space()='Shipping Method:']/following-sibling::span"));
        Assert.assertEquals(expectedShippingMethod, actualShippingMethod);
        // 2.37 Verify Total is “$698.00”
        String expectedTotalAmount = "$698.00";
        String actualTotalAmount = getTextFromElement(By.xpath("//tr[@class='order-total']//span[@class='value-summary']"));
        Assert.assertEquals(expectedTotalAmount, actualTotalAmount);
        // 2.38 Click on “CONFIRM”
        clickOnElement(By.xpath("//button[normalize-space()='Confirm']"));
        // 2.39 Verify the Text “Thank You”
        Assert.assertEquals("Thank you", getTextFromElement(By.xpath("//h1[normalize-space()='Thank you']")));
        // 2.40 Verify the message “Your order has been successfully processed!”
        Assert.assertEquals("Your order has been successfully processed!", getTextFromElement(By.xpath("//strong[normalize-space()='Your order has been successfully processed!']")));
        // 2.41 Click on “CONTINUE”
        clickOnElement(By.xpath("//button[normalize-space()='Continue']"));
        // 2.42 Verify the text “Welcome to our store”
        Assert.assertEquals("Welcome to our store", getTextFromElement(By.xpath("//h2[normalize-space()='Welcome to our store']")));
        // 2.43 Click on “Logout” link
        clickOnElement(By.linkText("Log out"));
        // 2.44 Verify the URL is “https://demo.nopcommerce.com/"
        Assert.assertEquals("https://demo.nopcommerce.com/", driver.getCurrentUrl());
    }

    @After
    public void tearDown() {
        closeBrowser();
    }
}
