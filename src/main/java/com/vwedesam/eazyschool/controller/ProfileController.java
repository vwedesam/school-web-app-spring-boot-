package com.vwedesam.eazyschool.controller;

import com.vwedesam.eazyschool.model.Address;
import com.vwedesam.eazyschool.model.Person;
import com.vwedesam.eazyschool.model.Profile;
import com.vwedesam.eazyschool.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
public class ProfileController {

    @Autowired
    PersonRepository personRepository;

    @RequestMapping(value = {"/displayProfile"})
    public ModelAndView displayMessages(Model model, Authentication authentication, HttpSession session){

        Person person = (Person) session.getAttribute("loggedInPerson");
        Profile profile = new Profile(person);

        ModelAndView modelAndView = new ModelAndView("profile.html");
        model.addAttribute("profile", profile);
        return modelAndView;
    }

    @PostMapping(value = "/updateProfile")
    public String updateProfile(@Valid @ModelAttribute("profile") Profile profile, Errors errors, HttpSession session){

        if(errors.hasErrors()){
            return "profile.html";
        }
        Person person = (Person) session.getAttribute ("loggedInPerson");
        person.setName(profile.getName());
        person.setMobileNumber(profile.getMobileNumber());
        if(person.getAddress() == null || !(person.getAddress().getAddressId() > 0)){
            person.setAddress(new Address());
        }
        person.getAddress().setAddress1(profile.getAddress1());
        person.getAddress().setAddress2(profile.getAddress2());
        person.getAddress().setCity(profile.getCity());
        person.getAddress().setState(profile.getState());
        person.getAddress().setZipCode(profile.getZipCode());

        personRepository.save(person);
        session.setAttribute("loggedInPerson", person);

        return "redirect:/displayProfile";
    }

}
