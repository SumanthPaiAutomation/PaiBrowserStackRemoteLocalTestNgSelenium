package com.mytests;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import Utils.ConfigReader;
import io.qameta.allure.Allure;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserStackBaseTest {


    WebDriver driver;
    public static final String USERNAME = ConfigReader.getInstance().getBrowserStackUserName();
    public static final String AUTOMATE_KEY = ConfigReader.getInstance().getBrowserStackAuthKey();
    public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";


    @Parameters({"browser", "browser_version", "os", "os_version"})
    @BeforeMethod
    public void setUp(@Optional("chrome") String browserName,
					  @Optional("") String browser_version,
					  @Optional("") String os,
					  @Optional("") String os_version,
					  @Optional("") Method name) {
        if (ConfigReader.getInstance().getEnvironmentType().equalsIgnoreCase("remote")) {
            System.out.println("browser name is : " + browserName);
            String methodName = name.getName();

            DesiredCapabilities caps = new DesiredCapabilities();

            caps.setCapability("os", os);
            caps.setCapability("os_version", os_version);
            caps.setCapability("browser_version", browser_version);
            caps.setCapability("name", methodName);

            if (browserName.equals("Chrome")) {
                WebDriverManager.chromedriver().setup();
                caps.setCapability("browser", "Chrome");
            } else if (browserName.equals("Firefox")) {
                WebDriverManager.firefoxdriver().setup();
                caps.setCapability("browser", "Firefox");
            }
            try {
                driver = new RemoteWebDriver(new URL(URL), caps);
                driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else if (ConfigReader.getInstance().getEnvironmentType().equalsIgnoreCase("local")) {
            switch (browserName) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driver= new ChromeDriver();
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    driver= new EdgeDriver();
                    break;

                default:
                    throw new RuntimeException("Unable to initiate local driver");

            }
            try {
                driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if(ConfigReader.getInstance().getEnvironmentType().equalsIgnoreCase("local")){
            if(!result.isSuccess()){
                LocalDateTime localDateTime= LocalDateTime.now();
                SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                String fileName="TestResult"+simpleDateFormat.format(localDateTime);
                byte[] screenShot =((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment(fileName,new ByteArrayInputStream(screenShot));
            }
        }
        driver.quit();
    }


}
