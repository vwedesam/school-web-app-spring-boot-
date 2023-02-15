package com.vwedesam.eazyschool.service;

import com.vwedesam.eazyschool.constants.EazySchoolConstants;
import com.vwedesam.eazyschool.model.Contact;
import com.vwedesam.eazyschool.repository.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
//        contact.setCreatedBy(EazySchoolConstants.ANONYMOUS);
//        contact.setCreatedAt(LocalDateTime.now());
        // allow jpa auditing manage these fields

        Contact savedContact = contactRepository.save(contact);

        if(null != savedContact && savedContact.getContactId() > 0){
            isSaved = true;
        }

        return isSaved;
    }

    public List<Contact> findMsgsWithStatus(){

       List<Contact> contactMsgs = contactRepository.findByStatus(EazySchoolConstants.OPEN);

       return contactMsgs;

    }

    public boolean updateMsgStatus(int contactId){
        boolean isUpdated = false;
        Optional<Contact> contact = contactRepository.findById(contactId);

        contact.ifPresent(contact1 -> {
            contact1.setStatus(EazySchoolConstants.CLOSE);
//            contact1.setUpdatedBy(updatedBy);
//            contact1.setUpdatedAt(LocalDateTime.now());
            // allow jpa auditing manage these fields
        });

        Contact updatedContact = contactRepository.save(contact.get());
        if(null != updatedContact && updatedContact.getUpdatedBy() != null){
            isUpdated = true;
        }

        return isUpdated;
    }

}
