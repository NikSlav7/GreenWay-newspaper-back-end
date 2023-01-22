package com.example.jobsnewspaper.domains;
import java.util.*;
import java.util.stream.Collectors;

public class RegistrationRequest {

    private String email;


    private String interest1;

    private String interest2;

    private String interest3;

    public RegistrationRequest(String email,String interest1, String interest2, String interest3) {
        this.email = email;
        this.interest1 = interest1;
        this.interest2 = interest2;
        this.interest3 = interest3;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getInterest1() {
        return interest1;
    }

    public void setInterest1(String interest1) {
        this.interest1 = interest1;
    }

    public String getInterest2() {
        return interest2;
    }

    public void setInterest2(String interest2) {
        this.interest2 = interest2;
    }

    public String getInterest3() {
        return interest3;
    }

    public void setInterest3(String interest3) {
        this.interest3 = interest3;
    }



    public List<Interest> getAllInterest(){
        List<String> list = new ArrayList<>();
        if (interest1 != null) list.add(interest1);
        if (interest2 != null) list.add(interest2);
        if (interest3 != null) list.add(interest3);

        return list.stream().map(interest1 -> Interest.interestFromString(interest1)).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "RegistrationRequest{" +
                "email='" + email + '\'' +
                ", interest1='" + interest1 + '\'' +
                ", interest2='" + interest2 + '\'' +
                ", interest3='" + interest3 + '\'' +
                '}';
    }
}
