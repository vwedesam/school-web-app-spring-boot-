package com.vwedesam.eazyschool.controller;

import com.vwedesam.eazyschool.model.Courses;
import com.vwedesam.eazyschool.model.EazyClass;
import com.vwedesam.eazyschool.model.Person;
import com.vwedesam.eazyschool.repository.CoursesRepository;
import com.vwedesam.eazyschool.repository.EazyClassRepository;
import com.vwedesam.eazyschool.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    EazyClassRepository eazyClassRepository;

    @Autowired
    CoursesRepository coursesRepository;

    @RequestMapping(value = "/displayClasses")
    public ModelAndView displayClasses() {
        ModelAndView modelAndView = new ModelAndView("classes.html");
        // Static Sorting
        // List<Courses> courses = coursesRepository.findByOrderByNameDesc();
        // Dynamic Sorting
        List<EazyClass> eazyClasses = eazyClassRepository.findAll(Sort.by("name").descending());
        modelAndView.addObject("eazyClasses", eazyClasses);
        modelAndView.addObject("eazyClass", new EazyClass());
        return modelAndView;
    }

    @PostMapping(value = "/addNewClass")
    public String addNewClass(@Valid EazyClass eazyClass){

        EazyClass newEazyClass = eazyClassRepository.save(eazyClass);

        if(newEazyClass != null && newEazyClass.getClassId() > 0){
            return "redirect:/admin/displayClasses";
        }
        return "classes.html";
    }

    @GetMapping(value = "/deleteClass")
    public ModelAndView deleteClass(@RequestParam(value = "id", required = true) int id){
        Optional<EazyClass> eazyClass = eazyClassRepository.findById(id);
        // update all persons enrolled in this class relation to null
        for(Person person : eazyClass.get().getPersons()){
            person.setEazyClass(null);
            personRepository.save(person);
        }
        eazyClassRepository.deleteById(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }

    @GetMapping("/displayStudents")
    public ModelAndView displayStudents (Model model, @RequestParam int classId, HttpSession session,
                                         @RequestParam(value = "error", required = false) String error) {

        Optional<EazyClass> eazyClass = eazyClassRepository.findById(classId);

        ModelAndView modelAndView = new ModelAndView("students.html");
        modelAndView.addObject("person", new Person());
        modelAndView.addObject("eazyClass", eazyClass.get());
        session.setAttribute ("eazyClass", eazyClass.get());
        if(error != null) {
            String errorMessage = "Invalid Email entered!!";
            modelAndView.addObject("errorMessage", errorMessage);
        }
        return modelAndView;
    }

    @PostMapping("/addStudent")
    public ModelAndView addStudent(Model model, @ModelAttribute("person") Person person, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        EazyClass eazyClass = (EazyClass) session.getAttribute("eazyClass");
        Person personEntity = personRepository.readByEmail(person.getEmail());

        if(personEntity==null || !(personEntity.getPersonId()>0)){
            modelAndView.setViewName("redirect:/admin/displayStudents?classId="+eazyClass.getClassId()
                    +"&error=true");
            return modelAndView;
        }

        personEntity.setEazyClass(eazyClass);
        personRepository.save(personEntity);
        eazyClass.getPersons().add(personEntity);
        eazyClassRepository.save(eazyClass);

        modelAndView.setViewName("redirect:/admin/displayStudents?classId="+eazyClass.getClassId());
        return modelAndView;
    }

    @GetMapping("/deleteStudent")
    public ModelAndView deleteStudent(Model model, @RequestParam int personId, HttpSession session) {
        EazyClass eazyClass = (EazyClass) session.getAttribute("eazyClass");

        Optional<Person> person = personRepository.findById(personId);
        person.get().setEazyClass(null);
        eazyClass.getPersons().remove(person.get());

        EazyClass eazyClassSaved = eazyClassRepository.save(eazyClass);

        session.setAttribute("eazyClass", eazyClassSaved);

        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayStudents?classId="+eazyClass.getClassId());
        return modelAndView;
    }

    @GetMapping(value = "/displayCourses")
    public ModelAndView displayCourses() {
        List<Courses> courses = coursesRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("courses_secure.html");
        modelAndView.addObject("courses", courses);
        modelAndView.addObject("course", new Courses());
        return modelAndView;
    }

    @PostMapping(value = "/addNewCourse")
    public String addNewCourse(@Valid  Courses course) {
        coursesRepository.save(course);
        return "redirect:/admin/displayCourses";
    }

    @GetMapping("/viewStudents")
    public ModelAndView viewStudents(@RequestParam int id, HttpSession session,
                                     @RequestParam(required = false) String error){
        Optional<Courses> courses = coursesRepository.findById(id);
        ModelAndView modelAndView = new ModelAndView("course_students.html");
        modelAndView.addObject("courses", courses.get());
        modelAndView.addObject("person", new Person());
        session.setAttribute("courses", courses.get());
        if(error != null) {
            String errorMessage = "Invalid Email entered!!";
            modelAndView.addObject("errorMessage", errorMessage);
        }
        return modelAndView;
    }

    @PostMapping("/addStudentToCourse")
    public ModelAndView addStudentToCourse(@ModelAttribute("person") Person person, HttpSession session){

        ModelAndView modelAndView = new ModelAndView();
        Courses courses = (Courses) session.getAttribute ("courses");

        Person personEntity = personRepository.readByEmail(person.getEmail());

        if(personEntity == null || !(personEntity.getPersonId() > 0 )){
            modelAndView.setViewName("redirect:/admin/viewStudents?id="+courses.getCourseId()
                    +"&error=true");
            return modelAndView;
        }

        personEntity.getCourses().add(courses);
        courses.getPersons().add(personEntity);

        personRepository.save(personEntity);

        session.setAttribute ("courses", courses);

        modelAndView.setViewName("redirect:/admin/viewStudents?id="+courses.getCourseId());
        return modelAndView;

    }


    @GetMapping("/deleteStudentFromCourse")
    public ModelAndView deleteStudentFromCourse(@RequestParam int personId, HttpSession session){

        ModelAndView modelAndView = new ModelAndView();

        Courses courses = (Courses) session.getAttribute("courses");
        Optional<Person> person = personRepository.findById(personId);

        if(person == null){
            modelAndView.setViewName("redirect:/admin/viewStudents?id="+courses.getCourseId()
                    +"&error=true");
            return modelAndView;
        }
        person.get().getCourses().remove(courses);
        courses.getPersons().remove(person);

        personRepository.save(person.get());

        session.setAttribute ("courses", courses);

        modelAndView.setViewName("redirect:/admin/viewStudents?id="+courses.getCourseId());

        return modelAndView;

    }


}
