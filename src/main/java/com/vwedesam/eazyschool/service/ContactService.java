package com.vwedesam.eazyschool.service;

import com.vwedesam.eazyschool.model.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

@Service
//@RequestScope
//@SessionScope
//@ApplicationScope
public class ContactService {

    private int counter = 0;
    private static Logger log = LoggerFactory.getLogger(ContactService.class);

    public ContactService(){
        System.out.println("Contact Service Bean initialized");
    }

    /**
     * Save Contact Details into DB
     * @param contact
     * @return
     */
    public boolean saveMessageDetails(Contact contact){
        boolean isSaved = true;
        // TODO - Need to persist the data into the DB table
        log.info(contact.toString());
        return isSaved;
    }

    public int getCounter(){
        return counter;
    }

    public void setCounter(int counter){
        this.counter = counter;
    }

}
