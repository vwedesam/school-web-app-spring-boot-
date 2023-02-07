package com.vwedesam.eazyschool.controller;

import com.vwedesam.eazyschool.model.Holiday;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HolidaysController {

    private Logger log = LoggerFactory.getLogger(HolidaysController.class);

    @GetMapping("/holidays")
    public String displayHoliday(Model model){

        List<Holiday> holidays = Arrays.asList(
                new Holiday(" Jan 1 ", "New Year's Day", Holiday. Type.FESTIVAL),
                new Holiday(" Oct 31 ", "Halloween", Holiday .Type.FESTIVAL),
                new Holiday(" Nov 24 ", "Thanksgiving Day", Holiday. Type.FESTIVAL),
                new Holiday (" Dec 25 ", "Christmas", Holiday.Type.FESTIVAL),
                new Holiday (" Jan 17 ", "Martin Luther King Jr. Day", Holiday.Type.FEDERAL),
                new Holiday (" July 4 ", "Independence Day", Holiday. Type.FEDERAL),
                new Holiday(" Sep 5 ", "Labor Day", Holiday. Type.FEDERAL),
                new Holiday (" Nov 11 ", "Veterans Day", Holiday. Type.FEDERAL)
        );

        log.info(holidays.toString());

        Holiday.Type[] types = Holiday.Type.values();

        for (Holiday. Type type : types) {
            model.addAttribute (type.toString(),
                    (holidays.stream().filter (holiday -> holiday.getType().equals (type)).
                            collect (Collectors.toList())));
        }

        return "holidays.html";

    }

}
