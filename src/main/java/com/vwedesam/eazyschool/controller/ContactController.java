package com.vwedesam.eazyschool.controller;

import com.vwedesam.eazyschool.model.Contact;
import com.vwedesam.eazyschool.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@Controller
public class ContactController {

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

    @RequestMapping(value = "/saveMsg", method = POST)
    public String saveMessages(@Valid @ModelAttribute("contact") Contact contact, Errors errors){
        if(errors.hasErrors()){
            log.error(errors.toString());
            return "contact.html";
        }

        this.contactService.saveMessageDetails(contact);

        return "redirect:/contact";
    }

    @RequestMapping("/displayMessages")
    public ModelAndView displayMessages(Model model){
        List<Contact> contactMsgs = contactService.findMsgsWithStatus();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("messages.html");
        modelAndView.addObject("contactMsgs", contactMsgs);

        return modelAndView;
    }

    @RequestMapping("/closeMsg")
    public String updateMsgStatus(@RequestParam int id, Authentication authentication){
        contactService.updateMsgStatus(id, authentication.getName());
        return "redirect:/displayMessages";
    }

}
