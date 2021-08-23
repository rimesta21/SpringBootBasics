package com.udacity.jwdnd.c1.review.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/chat")
public class ChatController {
    private MessageService ms;

    public ChatController(MessageService ms) {
        this.ms = ms;
    }

    @GetMapping()
    public String firstTime(ChatForm chatForm, Model model) {
        /*
        This is to reset the message chat everytime someone clicks out of and into the page again. Ideally I would put
        this into a timer of sorts. So that if the clicked off by accident the message history could still be there.
        */
        ms.postConstruct();
        model.addAttribute("messages", new ArrayList<>());
        return "chat";
    }

    @PostMapping
    public String newMessage(ChatForm chatForm, Model model) {
        chatForm.setMessageText(ms.replaceBannedWords(chatForm.getMessageText()));
        if(chatForm.getMode().equals("Shout")) {
            chatForm.setMessageText(ms.upperCase(chatForm.getMessageText()));
        } else if(chatForm.getMode().equals("Whisper")) {
            chatForm.setMessageText(ms.lowerCase(chatForm.getMessageText()));
        }
        //Pretty sure we can eliminate ChatMessage by just adding the ChatForm straight into the message list
        ChatMessage cm = new ChatMessage();
        cm.setUsername(chatForm.getUserName());
        cm.setMessage(chatForm.getMessageText());
        ms.addMessage(cm);
        model.addAttribute("messages", ms.getMessages());
        return "chat";
    }

}
