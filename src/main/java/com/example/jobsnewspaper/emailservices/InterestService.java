package com.example.jobsnewspaper.emailservices;


import com.example.jobsnewspaper.domains.Interest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Service
@RestController
@RequestMapping(path = "/api/interest/")
public class InterestService {


    @GetMapping(path = "get-all")
    public List<String> getAllInterest(){
        return Interest.getAllInterestNames();
    }

}
