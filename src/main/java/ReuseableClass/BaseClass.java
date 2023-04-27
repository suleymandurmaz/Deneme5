package ReuseableClass;


import Utils.Driver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import static ReuseableClass._Conditions.visibilityOfNestedElementsLocatedBy;
import static Utils.Driver.getDriver;


public class BaseClass {

    protected WebDriver driver= getDriver();
    protected WebDriverWait wait= new WebDriverWait(driver,Duration.ofMillis(10000));
    private WebElement element;
    private By locator;
    private JavascriptExecutor js;

    public BaseClass() {
    }

    public   BaseClass $(WebElement element){
        this.element=element;
        return this;
    }
    public BaseClass $(By locator){
        this.locator=locator;
        element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return this;
    }

    public BaseClass click(){
        wait.until(driver1 -> {
            try {
                element.click();
                return true;
            }catch (Exception e1){
                try {
                    new Actions(driver1).moveToElement(element).click().perform();
                    return true;
                }catch (Exception e2){
                    try {
                        ((JavascriptExecutor) driver1).executeScript("arguments[0].click()", element);
                        return true;
                    }catch (Exception e3){
                        return false;
                    }
                }
            }
        });
        return this;
    }

    public BaseClass sendkeys(By locator, String text){
        element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        sendKeys(text);
        return this;
    }

    public BaseClass sendKeys(CharSequence... text){
        wait.until(driver1 -> {
            try {
                element.clear();
                element.sendKeys(text);
                return true;
            }catch (Exception e1){
                try {
                    element.clear();
                    new Actions(driver1).moveToElement(element).sendKeys(text).perform();
                    return true;
                }catch (Exception e2){
                    try {
                        element.clear();
                        ((JavascriptExecutor) driver1).executeScript("arguments[0].value='" + text + "'", element);
                        return true;
                    }catch (Exception e3){
                        return false;
                    }
                }
            }
        });
        return this;
    }

    public BaseClass open(String Url){
        driver.get(Url);
        return  this;
    }

    public BaseClass submit(){
        element.submit();
      return this;
    }

    /**
     *
     * @param xpathLocator
     * @return Elementin altındaki elementi döndürür.
     */
    public BaseClass findElement(String xpathLocator){
        waitFor(visibilityOfNestedElementsLocatedBy,"."+xpathLocator);
        element=element.findElement(By.xpath("."+xpathLocator));

        return this;
    }
    public List<WebElement> getElementList(){
        return driver.findElements(locator);
    }

    public BaseClass waitFor(_Conditions conditions, String text){
        Duration duration = Duration.ofMillis(15_000);
        switch (conditions){
            case visibilty:
                new WebDriverWait(driver,duration).until(ExpectedConditions.visibilityOf(element));
                break;
            case clickable:
                new WebDriverWait(driver,duration).until(ExpectedConditions.elementToBeClickable(element));
                break;
            case invisible:
                new WebDriverWait(driver,duration).until(ExpectedConditions.invisibilityOf(element));
                break;
            case urlContains:
                new WebDriverWait(driver,duration).until(ExpectedConditions.urlContains(text));
                break;
            case urlToBe:
                new WebDriverWait(driver,duration).until(ExpectedConditions.urlToBe(text));
                break;
            case visibilityOfNestedElementsLocatedBy:
                new WebDriverWait(driver,duration).until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(element,By.xpath(text)));
                break;
        }
        return this;
    }

    public BaseClass navigate(_Navigate navigate, String url){
        switch (navigate){
            case refresh:
                driver.navigate().refresh();
                break;
            case forward:
                driver.navigate().forward();
                break;
            case back:
                driver.navigate().back();
                break;
            case to: {
                driver.navigate().to(url);
            }
        }
        return this;
    }

    public BaseClass scrollByElement(){
        js= ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].scrollIntoView();", element);
        return  this;
    }

    public WebElement getCurrentElement(){ return element;}

    public String getText(){ return element.getText();}

    public void scrollIntoView(WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", element);
    }

    public void scrollBy(int y){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, " + y + ");");
    }

    public void scrollTo(int x, int y){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(" + x + ", " + y + ");");
    }

    public void clickbyJs(WebElement element){
        this.element=element;
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public BaseClass scrollIntoView(WebElement element, boolean istoTheTop){
        this.element=element;
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(" + istoTheTop +");", element);
        return this;
    }
    public BaseClass scrollIntoView(boolean istoTheTop){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(" + istoTheTop +");", element);
        return this;
    }
    public BaseClass clear(){
        element.clear();
        return this;
    }
    public static String getScreenshot(String name) throws IOException {
        // naming the screenshot with the current date to avoid duplication
        String date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        // TakesScreenshot is an interface of selenium that takes the screenshot
        TakesScreenshot ts = (TakesScreenshot) getDriver();
        File source = ts.getScreenshotAs(OutputType.FILE);
        // full path to the screenshot location
        String target = System.getProperty("user.dir") + "/test-output/Screenshots/" + name + date + ".png";
        File finalDestination = new File(target);
        // save the screenshot to the path given
        FileUtils.copyFile(source, finalDestination);
        return target;
    }
    public static void switchToWindow(String targetTitle) {
        String origin = getDriver().getWindowHandle();
        for (String handle :getDriver().getWindowHandles()) {
            getDriver().switchTo().window(handle);
            if ( getDriver().getTitle().equals(targetTitle)) {
                return;
            }
        }
        getDriver().switchTo().window(origin);
    }

    public BaseClass hower(){
        new Actions(driver).moveToElement(element).build().perform();
        return this;
    }

    public static String getElementCssProperty(WebElement element, String cssValue ){
        return element.getCssValue(cssValue);
    }
    public String getElementCssProperty(String cssValue ){
        return element.getCssValue(cssValue);
    }
}
