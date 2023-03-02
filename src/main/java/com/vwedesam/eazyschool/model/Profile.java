package com.vwedesam.eazyschool.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Optional;

@Data
public class Profile {

    public Profile(Person person){
//        if(person) {
            this.setName(person.getName());
            this.setMobileNumber(person.getMobileNumber());
            this.setEmail(person.getEmail());
            if (person.getAddress() != null && person.getAddress().getAddressId() > 0) {
                this.setAddress1(person.getAddress().getAddress1());
                this.setAddress2(person.getAddress().getAddress2());
                this.setCity(person.getAddress().getCity());
                this.setState(person.getAddress().getState());
                this.setZipCode(person.getAddress().getZipCode());
            }
//        }
    }

    public Profile(){}

    @NotBlank(message="Name must not be blank")
    @Size(min=3, message="Name must be at least 3 characters long")
    private String name;

    @NotBlank(message="Mobile number must not be blank")
    @Pattern(regexp="(^$|[0-9]{8,15})",message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotBlank(message="Email must not be blank")
    @Email(message = "Please provide a valid email address" )
    private String email;

    @NotBlank(message="Address1 must not be blank")
    @Size(min=5, message="Address1 must be at least 5 characters long")
    private String address1;

    private String address2;

    @NotBlank(message="City must not be blank")
    @Size(min=5, message="City must be at least 5 characters long")
    private String city;

    @NotBlank(message="State must not be blank")
    @Size(min=5, message="State must be at least 5 characters long")
    private String state;

    @NotBlank(message="Zip Code must not be blank")
    @Pattern(regexp="(^$|[0-9]{5,10})",message = "Zip Code must be 5 digits")
    private String zipCode;

}
