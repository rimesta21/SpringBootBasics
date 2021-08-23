package com.udacity.jwdnd.c1.review.controllers;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class MessageService {
    private List<ChatMessage> messages;
    private String[] bannedWords;

    @PostConstruct
    public void postConstruct() {
        this.messages = new ArrayList<ChatMessage>();
        this.bannedWords = new String[]{"poop head", "fuckface", "lameo"};
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

    public boolean checkForBanned(String message) {
        message = this.lowerCase(message);
        for (String bannedWord : this.bannedWords) {
            if (message.contains(bannedWord)) {
               return true;
            }
        }
        return false;
    }

}
