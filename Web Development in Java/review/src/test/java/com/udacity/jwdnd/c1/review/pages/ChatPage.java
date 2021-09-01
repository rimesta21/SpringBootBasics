package com.udacity.jwdnd.c1.review.pages;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ChatPage {
    @FindBy(id = "messageText")
    private WebElement messageText;

    @FindBy(id = "messageType")
    private WebElement messageType;

    @FindBy(id = "submit-button")
    private WebElement submitButton;


    @FindBy(id = "messages")
    private List<WebElement> messages;

    @FindBy(id = "logout")
    private WebElement logout;

    public ChatPage(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(webDriver -> webDriver.findElement(By.id("messageText")));
        PageFactory.initElements(driver, this);
    }

    public void messageAndMessageType(String message, String messageMode) {
        inputMessageText(message);
        selectMessageType(messageMode);
        submitMessage();
    }

    public void inputMessageText(String messageText) {
        this.messageText.clear();
        this.messageText.sendKeys(messageText);
    }

    public void selectMessageType(String messageMode) {
        Select dropdown =  new Select(messageType);
        dropdown.selectByVisibleText(messageMode);
    }

    public void submitMessage() {
        submitButton.click();
    }

    public String getXMessage(int index) {
        WebElement message = messages.get(index);
        return message.getText();
    }

    public void goToLogin() {
        logout.click();
    }
}
