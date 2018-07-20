package com.timr.se.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DragAndDropJsHelper {
    Logger log = LogManager.getLogger(DragAndDropJsHelper.class.getName());

    String dragndrop_js = null;

    public DragAndDropJsHelper(String jsFile) throws IOException {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(getURIFromURL(jsFile)))) {
            dragndrop_js = br
                    .lines()
                    .collect(Collectors.joining(" "));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @SuppressWarnings("finally")
    private URI getURIFromURL(String fileName) {
        URI uri = null;
        try {
            URL url = this.getClass().getClassLoader().getResource(fileName);
            uri = url.toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } finally {
            return uri;
        }
    }

    /**
     * <pre>
     * Solution from elemental-selenium-tips
     * https://github.com/tourdedave/elemental-selenium-tips/blob/master/39-drag-and-drop/csharp/DragAndDrop.cs
     * 
     * https://stackoverflow.com/questions/42198564/drag-and-drop-in-selenium-java/50499307#50499307
     * https://gist.github.com/rcorreia/2362544 - drag-and-drop_helper.js
     * https://github.com/RomanIsko/elemental-selenium-tips/blob/master/39-drag-and-drop/csharp/DragAndDrop.cs
     * 
     * 
     * NOTE: Seems fragile, not sure if this works for XPATH or other src/dst type strings
     * TODO: would be good if it worked with WebElement, or BY
     * 
     * USAGE: 
     *     	    DragAndDropJsHelper ddh = new DragAndDropJsHelper("drag_and_drop_helper.js");
     *     	    ddh.dragDrop(driver, "#column-b", "#column-a");
     * 
     * </pre>
     * @param driver
     * @param src
     *            - css string for source element
     * @param dst
     *            - css string for destination element
     */
    public void dragDrop(WebDriver driver, String src, String dst) {
        log.info("drag src element: " + src + " to dst element: " + dst);
        String js = String.format("$('%s').simulateDragDrop({ dropTarget: '%s'});",src,dst);
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript(dragndrop_js + js);
    }
    
}
