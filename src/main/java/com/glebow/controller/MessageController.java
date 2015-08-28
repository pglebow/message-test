/**
 * 
 */
package com.glebow.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Philip Glebow
 *
 */
@RestController
public class MessageController {

    /**
     * 
     */
    public MessageController() {
    }
    
    @RequestMapping("/")
    public String index() {
        return "Hello from the MessageController";
    }

}
