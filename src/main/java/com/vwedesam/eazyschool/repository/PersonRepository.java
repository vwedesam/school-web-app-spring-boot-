package com.vwedesam.eazyschool.repository;

import com.vwedesam.eazyschool.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {


}
