package com.vwedesam.eazyschool.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;


@Setter
@Getter
@Entity
@Table(name = "class")
public class EazyClass extends BaseEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private int classId;

    @NotBlank(message="Name must not be blank")
    @Size(min=3, message= "Name must be at least 3 characters long")
    private String name;

    @OneToMany(mappedBy = "eazyClass", fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST, targetEntity = Person.class)
    private Set<Person> persons;
}
