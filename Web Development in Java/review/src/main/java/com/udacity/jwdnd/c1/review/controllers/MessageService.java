package com.udacity.jwdnd.c1.review.controllers;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class MessageService {
    private List<ChatMessage> messages;

    @PostConstruct
    public void postConstruct() {
        this.messages = new ArrayList<ChatMessage>();
    }

    public String upperCase(String message) { return message.toUpperCase(); }

    public String lowerCase(String message) {
        return message.toLowerCase();
    }

    public void addMessage(ChatMessage message) {
        this.messages.add(message);
   }

    public List getMessages(){
        return this.messages;
   }

}
