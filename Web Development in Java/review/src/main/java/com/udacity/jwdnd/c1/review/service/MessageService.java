package com.udacity.jwdnd.c1.review.service;

import com.udacity.jwdnd.c1.review.model.ChatForm;
import com.udacity.jwdnd.c1.review.model.ChatMessage;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MessageService {
    private List<ChatMessage> messages;
    private String[] bannedWords;

    @PostConstruct
    public void postConstruct() {
        this.messages = new ArrayList<>();
        this.bannedWords = new String[]{"poop", "fuck", "lameo", "bitch", "shit"};
    }

    public String upperCase(String message) { return message.toUpperCase(); }

    public String lowerCase(String message) {
        return message.toLowerCase();
    }

    public List<ChatMessage> getMessages(){
        return this.messages;
   }

   public void addChatMessage(ChatForm chatForm) {
       chatForm.setMessageText(this.replaceBannedWords(chatForm.getMessageText()));
       if(chatForm.getMode().equals("Shout")) {
           chatForm.setMessageText(this.upperCase(chatForm.getMessageText()));
       } else if(chatForm.getMode().equals("Whisper")) {
           chatForm.setMessageText(this.lowerCase(chatForm.getMessageText()));
       }
       //Pretty sure we can eliminate ChatMessage by just adding the ChatForm straight into the message list
       ChatMessage cm = new ChatMessage();
       cm.setUsername(chatForm.getUserName());
       cm.setMessage(chatForm.getMessageText());
       this.messages.add(cm);
   }


    public String replaceBannedWords(String message) {
        for (String bannedWord : this.bannedWords) {
            if (message.contains(bannedWord)) {
               List<Integer> indices = getIndices(message, bannedWord);
               for(int i : indices) {
                   message = replaceWithStar(message, bannedWord, i);
               }
            }
        }
        return message;
    }

    private String replaceWithStar(String message, String word, int i) {
        char[] temp = new char[word.length()];
        Arrays.fill(temp, '*');
        return message.substring(0,i) + new String(temp) + message.substring(i + word.length());
    }

    private List<Integer> getIndices(String message, String word) {
        List<Integer> indices = new ArrayList<>();
        int i = 0;
        message = this.lowerCase(message);
        do {
            i = message.indexOf(word, i);
            if(i != -1) {
                indices.add(i);
                i++;
            }
        } while(i != -1);
        return indices;
    }

}
