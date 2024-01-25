package Utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static ConfigReader configfilereader=null;

    private Properties properties;

    // Private constructor for Singleton pattern
    private ConfigReader() {
        properties = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
        } catch (Exception ex) {
            ex.printStackTrace();
            // Handle exception - maybe throw a custom exception
        }
    }

    // Public method to get the instance
    public static ConfigReader getInstance() {
        if (configfilereader== null) {
            configfilereader = new ConfigReader();
        }
        return configfilereader;
    }

    public String getEnvironmentType() {
        return properties.getProperty("runtype");
    }

    //            //brusername=sumanthpai_SelaVd
    //            //brautokey=VqQyVWsPDodWNdkVxQM1
    public String getBrowserStackUserName(){
        return properties.getProperty("brusername");
    }

    public String getBrowserStackAuthKey(){
        return properties.getProperty("brautokey");
    }
}
