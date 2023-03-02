package com.vwedesam.eazyschool.controller;

import com.vwedesam.eazyschool.model.Person;
import com.vwedesam.eazyschool.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class DashboardController {

    @Autowired
    PersonRepository personRepository;

    @GetMapping(value = "/dashboard")
    public String dashboard(Model model, Authentication authentication, HttpSession session){

        Person person = personRepository.readByEmail(authentication.getName());

        model.addAttribute("username", person.getName());
        model.addAttribute("roles", authentication.getAuthorities().toString());
        session.setAttribute("loggedInPerson", person);

        return "dashboard.html";
    }

}
