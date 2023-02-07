package com.vwedesam.eazyschool.model;

import lombok.Data;

/**
 * @Data annotation is provided by Lombok library which generates getter, setter, equals), hashCode, toString() methods & Constructor at compile time.
 * This makes our code short and clean.
 *
 */
@Data
public class Contact {

    private String name;
    private String mobileNum;
    private String email;
    private String title;
    private String message;

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getMobileNum() {
//        return mobileNum;
//    }
//
//    public void setMobileNum(String mobileNum) {
//        this.mobileNum = mobileNum;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }

//    @Override
//    public String toString() {
//        return "Contact{" +
//                "name='" + name + '\'' +
//                ", mobileNum='" + mobileNum + '\'' +
//                ", email='" + email + '\'' +
//                ", title='" + title + '\'' +
//                ", message='" + message + '\'' +
//                '}';
//    }

}
