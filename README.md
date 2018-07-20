# se-drag-n-drop
WebDriver/Selenium DragAndDrop does not appear to work anymore, at best it goes between working and not working depending on release of drivers, browsers and selenium. I do not find that any of the java workarounds actually work.

    clickAndHold() moveToElement() release()  // solutions don't work
    
I put together a working Java example after viewing the below mentioned sites. My solution is using rcorreia's javascript

[Elemental Selenium Drag N Drop](http://the-internet.herokuapp.com/drag_and_drop) - example app for testing Drag N Drop 

[rcorreia/drag_and_drop_helper.js](https://gist.github.com/rcorreia/2362544) - rcorreia's javascript helper code

[RomanIsko/elemental-selenium-tips](https://github.com/RomanIsko/elemental-selenium-tips/blob/master/39-drag-and-drop/csharp/DragAndDrop.cs) - a csharp implementation

I figured out how to integrate the javascript file by looking at romanisko csharp code.

    JSDriver.ExecuteScript(dnd_javascript + "$('#column-a').simulateDragDrop({ dropTarget: '#column-b'});");
    
From the above I came up with the following Java Solution:

I'm using maven and have stored the rcorreia drag_and_drop_helper.js file in /resources

Usage:

    WebElement weA = driver.findElement(DragNDropPage.COLUMN_A);
    WebElement weB = driver.findElement(DragNDropPage.COLUMN_B);
    DragAndDropJsHelper ddh = new DragAndDropJsHelper("drag_and_drop_helper.js");

    ddh.dragDrop(driver, "#column-a", "#column-b");

    log.info("wait for opacity to change 1, indicating move complete");
    wait.until(ExpectedConditions.attributeToBe(weA, "opacity", "1"));



