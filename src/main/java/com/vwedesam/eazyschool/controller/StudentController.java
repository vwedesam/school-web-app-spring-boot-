package com.vwedesam.eazyschool.controller;

import com.vwedesam.eazyschool.model.Person;
import com.vwedesam.eazyschool.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;


@Controller
@Slf4j
@RequestMapping("student")
public class StudentController {

    @Autowired
    PersonRepository personRepository;

    @GetMapping("/displayCourses")
    public ModelAndView displayCourses(Model model, Authentication authentication, HttpSession session){

        Person person = (Person) session.getAttribute ("loggedInPerson");
//        Person person = personRepository.readByEmail(authentication.getName());
        ModelAndView modelAndView = new ModelAndView("courses_enrolled.html");
        modelAndView.addObject("person", person);
        return modelAndView;
    }
}
