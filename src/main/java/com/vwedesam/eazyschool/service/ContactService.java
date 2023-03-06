package com.vwedesam.eazyschool.service;

import com.vwedesam.eazyschool.constants.EazySchoolConstants;
import com.vwedesam.eazyschool.model.Contact;
import com.vwedesam.eazyschool.repository.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

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

    public Page<Contact> findMsgsWithStatus(String status, int pageNum, String sortField, String sortDir){
        int pageSize = 5;
        Pageable pageable = PageRequest.of((pageNum - 1), pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());

        Page<Contact> msgPage = contactRepository.findOpenMsgs(status, pageable);

        return msgPage;

    }

    public ModelAndView displayOpenMessages(int pageNum, String sortField, String sortDir){
        String status = EazySchoolConstants.OPEN;

        return displayMessages(status, pageNum, sortField, sortDir);
    }

    public ModelAndView displayClosedMessages(int pageNum, String sortField, String sortDir){

        String status = EazySchoolConstants.CLOSE;

        return displayMessages(status, pageNum, sortField, sortDir);
    }

    public ModelAndView displayMessages(String status, int pageNum, String sortField, String sortDir){

        ModelAndView modelAndView = new ModelAndView();
        if(status == EazySchoolConstants.CLOSE){
            modelAndView.setViewName("messages_closed.html");
        }else {
            modelAndView.setViewName("messages.html");
        }

        Page<Contact> msgPage = this.findMsgsWithStatus(status, pageNum, sortField, sortDir);

        List<Contact> contactMsgs = msgPage.getContent();

        modelAndView.addObject("currentPage", pageNum);
        modelAndView.addObject ("totalPages", msgPage.getTotalPages ());
        modelAndView.addObject ("totalMsgs", msgPage.getTotalElements());
        modelAndView.addObject("sortField", sortField);
        modelAndView.addObject("sortDir", sortDir);
        modelAndView.addObject("reverseSortDir", sortDir.equals ("asc") ? "desc" : "asc");

        modelAndView.addObject("contactMsgs", contactMsgs);

        return modelAndView;
    }


    public boolean updateMsgStatusOld(int contactId){
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

    public boolean updateMsgStatus(int contactId){
        boolean isUpdated = false;
        String status = EazySchoolConstants.CLOSE;

        int rows = contactRepository.updateStatusById(status, contactId);
        if(rows > 0){
            isUpdated = true;
        }
        return isUpdated;
    }

}
