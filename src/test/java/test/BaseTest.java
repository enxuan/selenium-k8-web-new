package test;

import driver.DriverFactory;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class BaseTest {

    private final static List<DriverFactory> webDriverThreadPool = Collections.synchronizedList(new ArrayList<>());
    private static ThreadLocal<DriverFactory> driverThread;
    private String browser;

    protected WebDriver getDriver() {
        return driverThread.get().getDriver(browser);
    }

    @BeforeTest(description = "Init browser session")
    @Parameters({"browser"})
    public void initBrowserSession(String browser) {
        this.browser = browser;
        driverThread = ThreadLocal.withInitial(() -> {
            DriverFactory webDriverThread = new DriverFactory();
            webDriverThreadPool.add(webDriverThread);
            return webDriverThread;
        });
    }

    @AfterTest(alwaysRun = true)
    public void closeBrowserSession() {
        driverThread.get().getDriver(browser).quit();
    }

    @AfterMethod
    public void captureScreenShot(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            // testMethodName-yyyy-mm-dd=hh-mm-sec.png

            // 1. get method name
            String methodName = result.getName();

            // 2. get taken time
            Calendar calendar = new GregorianCalendar();
            int y = calendar.get(Calendar.YEAR);
            int m = calendar.get(Calendar.MONTH) + 1;
            int d = calendar.get(Calendar.DATE);
            int h = calendar.get(Calendar.HOUR_OF_DAY);
            int min = calendar.get(Calendar.MINUTE);
            int sec = calendar.get(Calendar.SECOND);
            String fileName = methodName + "_" + y + "_" + m + "_" + d + "_" +
                    h + "_" + min + "_" + sec + ".png";

            // 3. take screenshot
            WebDriver driver = driverThread.get().getDriver(browser);
            File screenshotBase64Data = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            //4. save and attach to allure reporter
            try {
                // save
                String fileLocation = System.getProperty("user.dir") + "/sreenshots/" + fileName;
                FileUtils.copyFile(screenshotBase64Data, new File(fileLocation));

                // attach to allure reporter
                Path content = Paths.get(fileLocation);
                try (InputStream is = Files.newInputStream(content)) {
                    Allure.addAttachment(methodName, is);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
 }
