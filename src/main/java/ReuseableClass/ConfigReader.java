package ReuseableClass;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

   public  static Properties properties;



    public static String getProperties(String key) throws IOException {
         properties=new Properties();

            FileReader fileReader=new FileReader("src/main/java/Config/config.properties");
            properties.load(fileReader);
        return properties.getProperty(key);
    }

    public static void writeToPropertiesFile(String key,String value) throws IOException {
        properties=new Properties();
        FileReader fileReader=new FileReader("src/main/java/Config/config.properties");
        properties.load(fileReader);
        properties.put(key,value);
        FileWriter fileWriter=new FileWriter("src/main/java/Config/config.properties");

        properties.store(fileWriter,"Açıklama");
        fileReader.close();
        fileWriter.close();

    }

}
