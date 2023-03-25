package com.vwedesam.eazyschool.rest;


import com.vwedesam.eazyschool.constants.EazySchoolConstants;
import com.vwedesam.eazyschool.model.Contact;
import com.vwedesam.eazyschool.model.Response;
import com.vwedesam.eazyschool.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;


@Slf4j
@RestController
@RequestMapping(value = "/api/contact", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
@CrossOrigin(origins="*")
public class ContactRestController {

    @Autowired
    ContactRepository contactRepository;

    @GetMapping("/getMessagesByStatus")
    public Map<String, Object> getMessageByStatus(@RequestParam(name = "status", required = true) String status){
        List<Contact> data  = contactRepository.findByStatus(status);

        System.out.println("here ... api reached ...");
        System.out.println(data);
        Map<String, Object> response = new TreeMap<>();

        response.put("status", "success");
        response.put("data", data);

        return response;
    }

    @GetMapping(value = {"", "/getAllMsgsByStatus"})
//    @ResponseBody
    public Page<Contact> getAllMsgsByStatus(@RequestBody Contact contact, @RequestParam(name = "page", required = false, defaultValue = "1") int page){
        String status = "all";
        int _page = 1;
        if(page > 0){
            _page = page;
        }
        if(null != contact && null != contact.getStatus()){
            status = contact.getStatus();
        }
        Pageable pageable = PageRequest.of((_page - 1), 10);
        return contactRepository.findByStatus(status, pageable);
    }

    @GetMapping("/saveMsg")
//    @ResponseBody
    public ResponseEntity<Response> saveMsg(@RequestHeader(name = "source", defaultValue = "api") String source, @Valid @RequestBody Contact contact){

        log.info(String.format("Header source = %s", source));

        contactRepository.save(contact);
        Response response = new Response();
        response.setStatusCode(String.valueOf(HttpStatus.CREATED));
        response.setStatusMsg("Message saved successfully");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("isMsgSaved", "true")
                .body(response);
    }

    @DeleteMapping("/deleteMsg")
//    @ResponseBody
    public ResponseEntity<Response> deleteMsg(RequestEntity<Contact> requestEntity){

        HttpHeaders headers = requestEntity.getHeaders();

        log.info(String.format("Header headers = %s", headers));

        Contact contact = requestEntity.getBody();

        contactRepository.deleteById(contact.getContactId());
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Message successfully deleted");

        return ResponseEntity
                .status(HttpStatus.OK)
                .header("isMsgDeleted", "true")
                .body(response);
    }

    @PatchMapping("/closeMsg")
//    @ResponseBody
    public ResponseEntity<Response> closeMsg(@RequestBody Contact contactReq){
        Response response = new Response();

        Optional<Contact> contact = contactRepository.findById(contactReq.getContactId());

        if(contact.isPresent()){

            contact.get().setStatus(EazySchoolConstants.CLOSE);
            contactRepository.save(contact.get());

        }else {
            response.setStatusCode("400");
            response.setStatusMsg("Invalid Contact ID received");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .header("isMsgClosed", "false")
                    .body(response);
        }

        response.setStatusCode("200");
        response.setStatusMsg("Message successfully closed");

        return ResponseEntity
                .status(HttpStatus.OK)
                .header("isMsgClosed", "true")
                .body(response);
    }


}

