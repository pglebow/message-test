/**
 * 
 */
package com.glebow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.glebow.service.MessageService;

/**
 * @author Philip Glebow
 *
 */
@RestController
public class MessageController {

    @Autowired
    private MessageService service;
    
    /**
     * 
     */
    public MessageController() {
    }
    
    @RequestMapping("/")
    public String index() {
        return "Hello from the MessageController";
    }
    
    @RequestMapping("/send")
    public void send(@RequestParam(value="message") String message) {
        service.send(message);
    }

}
