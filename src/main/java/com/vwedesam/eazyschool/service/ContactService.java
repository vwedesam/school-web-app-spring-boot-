package com.vwedesam.eazyschool.service;

import com.vwedesam.eazyschool.model.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    private static Logger log = LoggerFactory.getLogger(ContactService.class);

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


}
