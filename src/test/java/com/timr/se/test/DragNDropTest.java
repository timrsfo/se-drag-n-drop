package com.timr.se.test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.timr.se.utils.DragAndDropJsHelper;

public class DragNDropTest {
    Logger log = LogManager.getLogger(DragNDropTest.class.getName());
    WebDriverWait wait = null;
    public static final By COLUMN_A = By.id("column-a");
    public static final By COLUMN_B = By.id("column-b");
    protected WebDriver driver = null;
    public By columnHeader = By.xpath(".//header");

    @BeforeMethod
    public void setup() {
	
	System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
	System.setProperty("webdriver.chrome.logfile", "/var/log/chromedriver.log");
	System.setProperty("webdriver.chrome.verboseLogging", "true");
	ChromeOptions chopts = new ChromeOptions();
	chopts.addArguments("--start-maximized");

	driver = new ChromeDriver(chopts);
	driver.get("http://the-internet.herokuapp.com/drag_and_drop");
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	wait = new WebDriverWait(driver, 20);
    }

    @Test
    public void dragAtoBTest() {
	try {
	    WebElement weA = driver.findElement(COLUMN_A);
	    WebElement weB = driver.findElement(COLUMN_B);
	    DragAndDropJsHelper ddh = new DragAndDropJsHelper("drag_and_drop_helper.js");
	    ddh.dragDrop(driver, "#column-a", "#column-b");

	    log.info("wait for opacity to change 1, indicating move complete");
	    wait.until(ExpectedConditions.attributeToBe(weA, "opacity", "1"));
	    assertThat(weA.findElement(columnHeader).getText(), containsString("B"));

	} catch (Exception e) {
	    e.printStackTrace();
	    log.error(e);
	    Assert.fail("FAILED validLoginTest");
	}
    }

    @Test
    public void dragBtoATest() {
	try {
	    WebElement weA = driver.findElement(COLUMN_A);
	    WebElement weB = driver.findElement(COLUMN_B);
	    DragAndDropJsHelper ddh = new DragAndDropJsHelper("drag_and_drop_helper.js");

	    ddh.dragDrop(driver, "#column-b", "#column-a");

	    log.info("wait for opacity to change 1, indicating move complete");
	    wait.until(ExpectedConditions.attributeToBe(weA, "opacity", "1"));
	    assertThat(weB.findElement(columnHeader).getText(), containsString("A"));

	} catch (Exception e) {
	    e.printStackTrace();
	    log.error(e);
	    Assert.fail("FAILED validLoginTest");
	}

    }

    @AfterMethod
    public void afterMethod(Method method) {
	    driver.quit();
	    driver = null;
    }
}
