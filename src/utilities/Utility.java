package utilities;

import browserfactory.BaseTest;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Utility extends BaseTest {
    Alert alert;
    WebDriverWait wait;

   /**
     * Finding web element
     */
    public WebElement findSingleElement(By by){
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    /**
     * Finding web elements
     */
    public List<WebElement> findMultipleElements(By by){
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    /**
     * This method will click on element
     */
    public void clickOnElement(By by) {
        findSingleElement(by).click();
    }

    /**
     * This method will click on element
     */
    public void clickOnWebElement(WebElement element) {
        element.click();
    }

    /**
     * This method will get text from the element
     */
    public String getTextFromElement(By by) {
        return findSingleElement(by).getText();
    }

    /**
     * This method will send text to the element
     */
    public void sendTextToElement(By by, String text) {
        driver.findElement(by).sendKeys(text);
    }

    //************************* Alert Methods *****************************************************
    //Total 5 methods need to create
    //1. switchToAlert
    public void switchToAlert() {
        alert = driver.switchTo().alert();
    }
    //2. acceptAlert
    public void acceptAlert(){
        alert.accept();
    }
    //3. dismissAlert
    public void dismissAlert(){
        alert.dismiss();
    }
    //4. sendTextToAlert
    public void sendTextToAlert(String str){
        alert.sendKeys(str);
    }
    //5. getTextFromAlert
    public String getTextFromAlert(){
        return alert.getText();
    }

    //*************************** Select Class Methods ***************************************//

    //1. selectByValueFromDropDown()
    public void selectByValueFromDropDown(By by, String value) {
        WebElement dropDown = driver.findElement(by);
        // Create the object of Select class
        Select select = new Select(dropDown);
        // Select by visible Text
        select.selectByValue(value);
    }

    //2. selectByIndexFromDropDown()
    public void selectByIndexFromDropDown(By by, int value) {
        WebElement dropDown = driver.findElement(by);
        // Create the object of Select class
        Select select = new Select(dropDown);
        // Select by visible Text
        select.selectByIndex(value);
    }

    //3. selectByVisibleTextFromDropDown()
    public void selectByVisibleTextFromDropDown(By by, String value) {
        WebElement dropDown = driver.findElement(by);
        // Create the object of Select class
        Select select = new Select(dropDown);
        // Select by visible Text
        select.selectByVisibleText(value);
    }
}
