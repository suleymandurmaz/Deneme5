package Utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class Driver {

    private static WebDriver driver;
    private ThreadLocal<WebDriver> drivers=new ThreadLocal<>();

    public static WebDriver getDriver(){
      return   getDriver(Browsers.chrome);

    }
    public static WebDriver getDriver(Browsers browser){
        if (driver==null) {
            switch (browser){
                case chrome :
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options=new ChromeOptions();
                    options.addArguments("--start-maximized");
                    options.addArguments("--remote-allow-origins=*");
                    driver=new ChromeDriver(options);
                    break;
                default:
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions option2=new FirefoxOptions();
                    option2.addArguments("--start-maximized");
                    driver=new FirefoxDriver(option2);
            }
        }
        return driver;
    }

    public static void quit(){
        if (driver!=null) {
            driver.quit();
            driver=null;
        }
    }

}
