package com.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @RequestMapping(path="/public", method = RequestMethod.GET)
    public String publicResource(){
        return "You can always see me";
    }

    @RequestMapping(path="/protected", method = RequestMethod.GET)
    public String protectedResource() {
        return "You can see me if you are certified";
    }
}
