package com.vwedesam.eazyschool.controller;

import com.vwedesam.eazyschool.model.Contact;
import com.vwedesam.eazyschool.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@Controller
public class ContactController {

//    private static Logger log = LoggerFactory.getLogger(ContactController.class);
    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService){
        this.contactService = contactService;
    }

    @RequestMapping(value = "/contact", method = GET)
    public String displayContactPage(Model model){
        // link contact.html with Contact POJO class
        model.addAttribute("contact", new Contact());
        return "contact.html";
    }

//    @PostMapping("/saveMsg")
//    public ModelAndView saveMessages(@RequestParam String name, @RequestParam String mobileNum,
//                               @RequestParam String email, @RequestParam(name="subject") String title,
//                               @RequestParam String message){
//
//        log.info ("Name : " + name);
//        log.info("Mobile Number : " + mobileNum);
//        log.info ("Email Address : " + email);
//        log.info("Subject : " + title);
//        log.info("Message : " + message);
//
//        return new ModelAndView("redirect:/contact");
//    }

    @RequestMapping(value = "/saveMsg", method = POST)
    public String saveMessages(@Valid @ModelAttribute("contact") Contact contact, Errors errors){
        if(errors.hasErrors()){
            log.error(errors.toString());
            return "contact.html";
        }
        // contactService.saveMessageDetails(contact);
        this.contactService.saveMessageDetails(contact);
        this.contactService.setCounter(contactService.getCounter() + 1);
        log.info("Number of times the Contact form is submitted : "+ contactService.getCounter());
//        return new ModelAndView("redirect:/contact");
        return "redirect:/contact";
    }

}
