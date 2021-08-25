package com.udacity.jwdnd.c1.review.controllers;

import com.udacity.jwdnd.c1.review.model.ChatForm;
import com.udacity.jwdnd.c1.review.model.ChatMessage;
import com.udacity.jwdnd.c1.review.service.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
        This is to reset the message chat everytime someone clicks out of and into the page again while the server stays running.
        Ideally I would put this into a timer of sorts. So that if the clicked off by accident the message history could still be there.
        */
        ms.postConstruct();
        model.addAttribute("messages", new ArrayList<>());
        return "chat";
    }

    @PostMapping
    public String newMessage(ChatForm chatForm, Model model) {
        ms.addChatMessage(chatForm);
        model.addAttribute("messages", ms.getMessages());
        return "chat";
    }

    /* This automatically updates
    @ModelAttribute("allMessageTypes")
    public String[] allMessageTypes() {return new String[] {"say", "Shout", "Whisper"};}
    */
}
