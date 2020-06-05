/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebadatadriven;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 *
 * @author abarrime
 */
public class WordpressLoginExcel {

    WebDriver driver;

    public WordpressLoginExcel() {

    }

    @Test(dataProvider = "wordpressData")
    public void loginToWordpress(String username, String password) throws InterruptedException {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.get("http://demosite.center/wordpress/wp-login.php");

        driver.findElement(By.id("user_login")).sendKeys(username);

        driver.findElement(By.id("user_pass")).sendKeys(password);

        driver.findElement(By.xpath(".//*[@id='wp-submit']")).click();

        Thread.sleep(5000);

        //System.out.println(driver.getTitle());
        Assert.assertTrue(driver.getTitle().contains("Dashboard"), "User is not able to login - Invalid Credentails");

        System.out.println("Page Title Verified - User is able to login succesfully");

        driver.quit();

    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @DataProvider(name = "wordpressData")
    public Object[][] passData() {
        String excelPath = "C:\\Users\\abarrime\\Documents\\NetBeansProjects\\ANTProjects\\PruebaDataDriven\\testdata";
        ExcelDataConfig config = new ExcelDataConfig(excelPath);
        int rows = config.getRowCount(0);

        Object[][] data = new Object[rows][2];

        for (int i = 0; i < rows; i++) {
            data[i][0] = config.getData(0, i, 0);
            data[i][1] = config.getData(0, i, 1);
        }

        return data;
    }

}
