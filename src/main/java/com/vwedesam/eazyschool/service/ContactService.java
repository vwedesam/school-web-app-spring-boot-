package com.vwedesam.eazyschool.service;

import com.vwedesam.eazyschool.EazySchoolApplication;
import com.vwedesam.eazyschool.constants.EazySchoolConstants;
import com.vwedesam.eazyschool.model.Contact;
import com.vwedesam.eazyschool.repository.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContactService {

    private static Logger log = LoggerFactory.getLogger(ContactService.class);

    @Autowired
    private ContactRepository contactRepository;

    public ContactService(){
        System.out.println("Contact Service Bean initialized");
    }

    /**
     * Save Contact Details into DB
     * @param contact
     * @return
     */
    public boolean saveMessageDetails(Contact contact){
        boolean isSaved = false;

        contact.setStatus(EazySchoolConstants.OPEN);
        contact.setCreatedBy(EazySchoolConstants.ANONYMOUS);
        contact.setCreatedAt(LocalDateTime.now());

        int result = contactRepository.saveContactMsg(contact);

        if(result > 0){
            isSaved = true;
        }

        return isSaved;
    }

    public List<Contact> findMsgsWithStatus(){

       List<Contact> contactMsgs = contactRepository.findMsgsWithStatus(EazySchoolConstants.OPEN);

       return contactMsgs;

    }

    public boolean updateMsgStatus(int contactId, String updatedBy){
        boolean isUpdated = false;

        int result = contactRepository.updateMsgStatus(contactId, EazySchoolConstants.CLOSE, updatedBy);
        if(result >  0){
            isUpdated = true;
        }

        return isUpdated;
    }

}
