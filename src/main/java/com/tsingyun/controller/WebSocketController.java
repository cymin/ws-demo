package com.tsingyun.controller;

import com.tsingyun.model.Message;
import com.tsingyun.model.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@EnableScheduling
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Scheduled(fixedDelay = 1000L)
    public void send() throws InterruptedException {
        Thread.sleep(1000);
        String s = "message: " + Math.random() * 1000;
        messagingTemplate.convertAndSend("/topic/", s);
        System.out.println(s);
    }

    @RequestMapping("/timely")
    public String timely(){
        return "timely";
    }

    @MessageMapping("/chat")
    public void handleChat(Principal principal, Message message) {
        //current user's info in principal
        if (principal.getName().equals("wyf")) {
            messagingTemplate.convertAndSendToUser("wisely", "/queue/notifications",
                    principal.getName() + "-send:" + message.getName());
        } else {
            messagingTemplate.convertAndSendToUser("wyf", "/queue/notifications", principal.getName()
                    + "-send:" + message.getName());
        }
    }

   @MessageMapping("/welcome")
   @SendTo("/topic/getResponse")
   public ResponseMessage sayWelcome(Message message) throws Exception{
       Thread.sleep(1000);
       return new ResponseMessage("Welcome, " + message.getName());
   }

   @RequestMapping("/ws")
   public String ws(){
       return "ws";
   }

   @RequestMapping({"/login","/"})
   public String login(){
       return "login";
   }

   @RequestMapping("chat")
   public String chat(){
       return "chat";
   }

}