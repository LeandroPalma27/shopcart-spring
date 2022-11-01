package com.leancoder.shopcart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Home controller.
@Controller
public class HomeController {
    
    @GetMapping({"/", ""})
    public String Home() {
        return "home";
    }

}
