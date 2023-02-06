package com.vwedesam.eazyschool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping(value = {"", "/", "/home"})
    public String displayHomePage(Model model){
        String username = "Vwede Sam";
        model.addAttribute("username", username);
        return "home.html";
    }

}
