package Steps;


import Utils.Driver;
import io.cucumber.java.After;




public class Hooks {





    @After(order = 0)
    public void after0(){
        Driver.quit();
    }


}
