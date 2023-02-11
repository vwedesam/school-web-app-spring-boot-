package com.vwedesam.eazyschool.controller;

import com.vwedesam.eazyschool.model.Holiday;
import com.vwedesam.eazyschool.repository.HolidaysRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HolidaysController {

    private Logger log = LoggerFactory.getLogger(HolidaysController.class);

    @Autowired
    private HolidaysRepository holidaysRepository;

    @GetMapping("/holidays")
    public String displayHoliday(@RequestParam(required = false) boolean festival,
                                 @RequestParam(required = false) boolean federal, Model model){
        // send to the page
        model.addAttribute("festival", festival);
        model.addAttribute("federal", federal);

        List<Holiday> holidays = holidaysRepository.findAllHolidays();
        log.info(holidays.toString());

        Holiday.Type[] types = Holiday.Type.values();

        for (Holiday.Type type : types) {
            // see java 8,9,11 new features
            model.addAttribute (type.toString(),
                    (holidays.stream().filter (holiday -> holiday.getType().equals (type)).
                            collect (Collectors.toList())));
        }

        return "holidays.html";

    }

    @RequestMapping(value = "/holidays/{display}", method = RequestMethod.GET)
    public String displayOneHoliday(@PathVariable String display, Model model){
        // send to the page

        log.info(display + "display path");
        log.info(" " + (display == "all") + " ");

        if(display.equals("all")){
            model.addAttribute("festival", true);
            model.addAttribute("federal", true);
        }else{
            model.addAttribute(String.valueOf(display), true);
        }

        List<Holiday> holidays = holidaysRepository.findAllHolidays();

        log.info(holidays.toString());

        Holiday.Type[] types = Holiday.Type.values();

        for (Holiday.Type type : types) {
            model.addAttribute (type.toString(),
                    (holidays.stream().filter (holiday -> holiday.getType().equals (type)).
                            collect (Collectors.toList())));
        }

        return "holidays.html";
    }

}
