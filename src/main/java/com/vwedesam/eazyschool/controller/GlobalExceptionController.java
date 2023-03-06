package com.vwedesam.eazyschool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


/**
 * @ControllerAdvice is a specialization of the
 * @Component annotation which allows to handle exceptions across the whole application in one global handling component.
 * It can be viewed as an interceptor of exceptions thrown by methods annotated with @RequestMapping and similar.
 */
@ControllerAdvice(annotations = Controller.class)
public class GlobalExceptionController {

    /**
     * @ExceptionHandler will register the given method for a given exception type,
     * so that ControllerAdvice can invoke this method logic if a given exception type is thrown inside the web application.
    **/
    @ExceptionHandler({Exception.class})
    public ModelAndView globalException(Exception exception){

        ModelAndView errorPage = new ModelAndView();
        errorPage.setViewName("error");
        String errorMsg = null;
        if(exception.getMessage() != null){
            errorMsg = exception.getMessage();
        }else if (exception.getCause() != null){
            errorMsg = exception.getCause().toString();
        }else if (exception!=null) {
            errorMsg = exception.toString();
        }
        errorPage.addObject("errorMsg", errorMsg) ;
        errorPage.addObject("errorMsg", exception.getMessage());
        return errorPage;
    }

}
