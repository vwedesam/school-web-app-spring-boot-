package com.vwedesam.eazyschool.service;

import com.vwedesam.eazyschool.constants.EazySchoolConstants;
import com.vwedesam.eazyschool.model.Person;
import com.vwedesam.eazyschool.model.Roles;
import com.vwedesam.eazyschool.repository.PersonRepository;
import com.vwedesam.eazyschool.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean createNewPerson(Person person){

        Roles role = rolesRepository.getByRoleName(EazySchoolConstants.STUDENT_ROLE);

        person.setRoles(role);
        person.setPwd(passwordEncoder.encode(person.getPwd()));

        person = personRepository.save(person);

        if(person !=null && person.getPersonId() > 0){
            return true;
        }
        return false;
    }


}
