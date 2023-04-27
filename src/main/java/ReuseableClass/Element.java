package ReuseableClass;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.MessageFormat;
import java.time.Duration;


import static Utils.Driver.getDriver;


public class Element {

    protected  WebDriver driver=getDriver();
    protected  WebDriverWait wait=new WebDriverWait(driver,Duration.ofMillis(10_000));

    private By locator;
    private WebElement element;
    private Actions actions;


    public static Element $(By locator){
        return new Element(locator);

    }

    Element(By locator){
        this.locator=locator;
        element=wait.until(ExpectedConditions.presenceOfElementLocated(locator));

    }
    Element(){

    }

    public Element sendKeys(CharSequence... text){
        element.sendKeys(text);
        return this;
    }


    public Element click(){
        waitFor(Conditions.visibilty,null);
        element.click();
        return this;
    }

    public void open(String url){
        driver.get(url);
    }

    public static void _open(String url){
        new Element().open(url);
    }

    public  Element waitFor(Conditions conditions, String text){
        waitFor(conditions,locator,text);
        return this;
    }

    public static void waitFor( Conditions conditions, By locator, String text){
        Duration duration = Duration.ofMillis(15_000);
        switch (conditions){
            case visibilty:
                new WebDriverWait(getDriver(),duration).until(ExpectedConditions.visibilityOfElementLocated(locator));
                break;
            case clickable:
                new WebDriverWait(getDriver(),duration).until(ExpectedConditions.elementToBeClickable(locator));
            case invisible:
                new WebDriverWait(getDriver(),duration).until(ExpectedConditions.invisibilityOfElementLocated(locator));
            case exist:
                new WebDriverWait(getDriver(),duration).until(ExpectedConditions.presenceOfElementLocated(locator));
                break;
            case urlContains:
                new WebDriverWait(getDriver(),duration).until(ExpectedConditions.urlContains(text));
        }
    }


    public Element shouldContainsText(String text){
        wait.until(d -> {
            try{
                if (!driver.findElement(locator).getText().toLowerCase().contains(text.toLowerCase()))
                    throw new Exception();
                return true;
            }catch (Exception e){
                return false;
            }

        });
        return this;
    }

    public Element navigate(Navigate navigate, String url){
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

    public Element findElementforSecond(String locator){
        element.findElement(By.xpath("."+locator));
        return this;
    }
    public Element findElementForSecond(String locator,String label){
        String format = MessageFormat.format("." + locator, label);
        element.findElement(By.xpath(format));
        return this;
        // TODO: 20.03.2023  
    }

    public String getText(){
        return element.getText();
    }

    public static void sleep(long milis){
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void clear(){element.clear();}
    public boolean isDisplayed(){ return element.isDisplayed();}
    public boolean isEnabled(){return element.isEnabled(); }
    public boolean isSelected(){return element.isSelected(); }

    public void submit(){
        element.submit();
    }

}
