package com.example.jobsnewspaper.securtity;


import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Service
@RestController
@RequestMapping(path = "/")
public class JWTCheck {

    @GetMapping(path = "api/check-token")
    public boolean checkToken(){
        return true;
    }
//
//    @GetMapping(path = "/login")
//    public String loginPage(){
//        return "Login Page";
//    }


}
