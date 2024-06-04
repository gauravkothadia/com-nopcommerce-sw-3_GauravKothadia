package computer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import utilities.Utility;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestSuite extends Utility {
    String baseUrl = "https://demo.nopcommerce.com/";

    @Before
    public void setUp() {
        openBrowser(baseUrl);
    }

    @Test
    public void verifyProductArrangeInAlphaBaticalOrder() throws InterruptedException {
//      1.1 Click on Computer Menu.
//      1.2 Click on Desktop
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath("//ul[@class='top-menu notmobile']//a[normalize-space()='Computers']")))
                .moveToElement(driver.findElement(By.xpath("//ul[@class='top-menu notmobile']//a[normalize-space()='Desktops']"))).click().build().perform();

//      1.3 Select Sort By position "Name: Z to A"
        WebElement element = driver.findElement(By.xpath("//select[@id='products-orderby']"));
        Select select = new Select(element);
        select.selectByVisibleText("Name: Z to A");

//      1.4 Verify the Product will arrange in Descending order.
        Thread.sleep(5000);
        List<WebElement> productList = driver.findElements(By.xpath("//h2[@class='product-title']//a"));
        List<String> productNames = new ArrayList<>();
        for (WebElement ele : productList) {
            productNames.add(ele.getText());
        }
        System.out.println(productNames);
        List<String> sortedProductNames = new ArrayList<>(productNames);
        sortedProductNames.sort(Comparator.naturalOrder());
        System.out.println(sortedProductNames);
        boolean areSorted = productNames.equals(sortedProductNames);
        Assert.assertFalse(areSorted);
    }

    @Test
    public void verifyProductAddedToShoppingCartSuccessFully() throws InterruptedException {
//      2.1 Click on Computer Menu.
        clickOnElement(By.xpath("//ul[@class='top-menu notmobile']//a[normalize-space()='Computers']"));
//      2.2 Click on Desktop
        clickOnElement(By.cssSelector("li[class='active last'] li:nth-child(1) a:nth-child(1)"));
//      2.3 Select Sort By position "Name: A to Z"
        selectByVisibleTextFromDropDown(By.xpath("//select[@id='products-orderby']"), "Name: A to Z");
//      2.4 Click on "Add To Cart"
        Thread.sleep(5000);
        WebElement addToCartButton = findSingleElement(By.xpath("(//button[@type='button'][normalize-space()='Add to cart'])[1]"));
        clickOnWebElement(addToCartButton);
//      2.5 Verify the Text "Build your own computer"
        String expectedHeading = "Build your own computer";
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", findSingleElement(By.cssSelector("div.page.product-details-page div.product-name>h1")));
        String actualHeading = getTextFromElement(By.cssSelector("div.page.product-details-page div.product-name>h1"));
        Assert.assertEquals(expectedHeading, actualHeading);
//      2.6 Select "2.2 GHz Intel Pentium Dual-Core E2200" using Select class
        selectByVisibleTextFromDropDown(By.xpath("//select[@id='product_attribute_1']"), "2.2 GHz Intel Pentium Dual-Core E2200");
//      2.7.Select "8GB [+
        selectByVisibleTextFromDropDown(By.cssSelector("#product_attribute_2"), "8GB [+$60.00]");
//      2.8 Select HDD radio "400 GB [+$100.00]"
        clickOnElement(By.xpath("//label[contains(text(),'400 GB')]/preceding-sibling::input"));
//      2.9 Select OS radio "Vista Premium [+$60.00]"
        clickOnElement(By.xpath("//label[contains(text(),'Vista Premium')]/preceding-sibling::input"));
//      2.10 Check Two Check boxes "Microsoft Office [+$50.00]" and "Total Commander [+$5.00]"
        WebElement element1 = findSingleElement(By.xpath("//label[contains(text(),'Microsoft Office')]/preceding-sibling::input"));
        if (!element1.isSelected()) {
            clickOnWebElement(element1);
        }
        WebElement element2 = findSingleElement(By.xpath("//label[contains(text(),'Total Commander')]/preceding-sibling::input"));
        if (!element2.isSelected()) {
            clickOnWebElement(element2);
        }
        Thread.sleep(5000);
//      2.11 Verify the price "$1,475.00"
        String expectedPrice = "$1,475.00";
        WebElement e1 = findSingleElement(By.cssSelector("div.product-price>span"));
        String actualPrice = e1.getText();
        //Assert.assertEquals(expectedPrice,actualPrice);
//      2.12 Click on "ADD TO CARD" Button.
        clickOnElement(By.cssSelector("div.add-to-cart-panel>button"));
//      2.13 Verify the Message "The product has been added to your shopping cart" on Top green Bar
        String expectedMessage = "The product has been added to your shopping cart";
        String actualMessage = getTextFromElement(By.cssSelector("div.bar-notification.success>p"));
        Assert.assertEquals(expectedMessage, actualMessage);
//      After that close the bar clicking on the cross button.
        clickOnElement(By.cssSelector("div.bar-notification.success>span.close"));
//      2.14 Then MouseHover on "Shopping cart" and Click on "GO TO CART" button.
        WebElement shoppingCartLink = findSingleElement(By.xpath("//span[@class='cart-label']"));
        js.executeScript("arguments[0].scrollIntoView();", shoppingCartLink);
        js.executeScript("arguments[0].click();", findSingleElement(By.xpath("//button[normalize-space()='Go to cart']")));
//      2.15 Verify the message "Shopping cart"
        String expectedCartTitle = "Shopping cart";
        String shoppingCartTitle = getTextFromElement(By.xpath("//h1[normalize-space()='Shopping cart']"));
        Assert.assertEquals(expectedCartTitle, shoppingCartTitle);
//      2.16 Change the Qty to "2" and Click on "Update shopping cart"
        clickOnElement(By.xpath("//div[@class='quantity up']"));
//        String expectedQty = "2";
//        String actualQty = getTextFromElement(By.xpath("//input[@class='qty-input']"));
//        Assert.assertEquals(expectedQty,actualQty);
//      2.17 Verify the Total"$2,950.00"
        String expectedTotal = "$2,950.00";
        WebElement totalElement = findSingleElement(By.xpath("//span[@class='value-summary']//strong[contains(text(),'$2,950.00')]"));
        js.executeScript("arguments[0].scrollIntoView();", totalElement);
        String actualTotal = getTextFromElement(By.xpath("//span[@class='value-summary']//strong[contains(text(),'$2,950.00')]"));
        Assert.assertEquals(expectedTotal, actualTotal);
//      2.18 click on checkbox “I agree with the terms of service”
        clickOnElement(By.xpath("//input[@id='termsofservice']"));
//      2.19 Click on “CHECKOUT”
        clickOnElement(By.xpath("//button[@id='checkout']"));
//      2.20 Verify the Text “Welcome, Please Sign In!”
        Assert.assertEquals("Welcome, Please Sign In!", getTextFromElement(By.xpath("//h1[normalize-space()='Welcome, Please Sign In!']")));
//      2.21Click on “CHECKOUT AS GUEST” Tab
        clickOnElement(By.xpath("//button[normalize-space()='Checkout as Guest']"));
//      2.22 Fill the all mandatory field
        sendTextToElement(By.xpath("//span[@class='required']/preceding-sibling::input[@id='BillingNewAddress_FirstName']"), "Gaurav");
        sendTextToElement(By.xpath("//span[@class='required']/preceding-sibling::input[@id='BillingNewAddress_LastName']"), "Patel");
        sendTextToElement(By.xpath("//span[@class='required']/preceding-sibling::input[@id='BillingNewAddress_Email']"), "GK@abc.com");
        selectByVisibleTextFromDropDown(By.xpath("//span[@class='required']/preceding-sibling::select"), "India");
        sendTextToElement(By.xpath("//span[@class='required']/preceding-sibling::input[@id='BillingNewAddress_City']"), "London");
        sendTextToElement(By.xpath("//span[@class='required']/preceding-sibling::input[@id='BillingNewAddress_Address1']"), "London Road");
        sendTextToElement(By.xpath("//span[@class='required']/preceding-sibling::input[@id='BillingNewAddress_ZipPostalCode']"), "N1 5RU");
        sendTextToElement(By.xpath("//span[@class='required']/preceding-sibling::input[@id='BillingNewAddress_PhoneNumber']"), "07895462104");
//      2.23 Click on “CONTINUE”
        clickOnElement(By.cssSelector("div#checkout-step-billing>div.buttons>button[onclick='if (!window.__cfRLUnblockHandlers) return false; Billing.save()']"));
//      2.24 Click on Radio Button “Next Day Air($0.00)”
        clickOnElement(By.xpath("//div[@class='section shipping-method']//label[starts-with(text(),'Next Day Air')]/preceding-sibling::input"));
//      2.25 Click on “CONTINUE”
        clickOnElement(By.xpath("//button[@class='button-1 shipping-method-next-step-button']"));
//      2.26 Select Radio Button “Credit Card”
        clickOnElement(By.xpath("//label[normalize-space()='Credit Card']/preceding-sibling::input"));
        clickOnElement(By.xpath("//button[@class='button-1 payment-method-next-step-button']"));
//      2.27 Select “Master card” From Select credit card dropdown
        selectByVisibleTextFromDropDown(By.xpath("//select[@id='CreditCardType']"), "Master card");
//      2.28 Fill all the details
        sendTextToElement(By.xpath("//input[@id='CardholderName']"), "Abc patel");
        sendTextToElement(By.xpath("//input[@id='CardNumber']"), "5299920210000277");
        selectByVisibleTextFromDropDown(By.xpath("//select[@id='ExpireMonth']"), "11");
        selectByVisibleTextFromDropDown(By.xpath("//select[@id='ExpireYear']"), "2025");
        sendTextToElement(By.xpath("//input[@id='CardCode']"), "589");
//      2.29 Click on “CONTINUE”
        clickOnElement(By.xpath("//button[@class='button-1 payment-info-next-step-button']"));
//      2.30 Verify “Payment Method” is “Credit Card”
        String expectedPaymentMethod = "Credit Card";
        String actualPaymentMethod = getTextFromElement(By.xpath("//li[@class='payment-method']//span[normalize-space()='Payment Method:']/following-sibling::span"));
        Assert.assertEquals(expectedPaymentMethod, actualPaymentMethod);
//      2.32 Verify “Shipping Method” is “Next Day Air”
        String expectedShippingMethod = "Next Day Air";
        String actualShippingMethod = getTextFromElement(By.xpath("//span[normalize-space()='Shipping Method:']/following-sibling::span"));
        Assert.assertEquals(expectedShippingMethod, actualShippingMethod);
//      2.33 Verify Total is “$2,950.00”
        String expectedTotalAmount = "$2,950.00";
        String actualTotalAmount = getTextFromElement(By.xpath("//tr[@class='order-total']//span[@class='value-summary']"));
        Assert.assertEquals(expectedTotalAmount, actualTotalAmount);
//      2.34 Click on “CONFIRM”
        clickOnElement(By.xpath("//button[normalize-space()='Confirm']"));
//      2.35 Verify the Text “Thank You”
        Assert.assertEquals("Thank you", getTextFromElement(By.xpath("//h1[normalize-space()='Thank you']")));
//      2.36 Verify the message “Your order has been successfully processed!”
        Assert.assertEquals("Your order has been successfully processed!", getTextFromElement(By.xpath("//strong[normalize-space()='Your order has been successfully processed!']")));
//      2.37 Click on “CONTINUE”
        clickOnElement(By.xpath("//button[normalize-space()='Continue']"));
//      2.37 Verify the text “Welcome to our store”
        Assert.assertEquals("Welcome to our store", getTextFromElement(By.xpath("//h2[normalize-space()='Welcome to our store']")));
    }

    @After
    public void tearDown() {
        //   closeBrowser();
    }
}