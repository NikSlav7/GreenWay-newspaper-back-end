package com.example.jobsnewspaper.managers;


import com.example.jobsnewspaper.daos.EmailNewspaperFollowersDAO;
import com.example.jobsnewspaper.domains.EmailNewspaperFollower;
import com.example.jobsnewspaper.domains.RegistrationRequest;
import com.example.jobsnewspaper.emailservices.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@RestController
@RequestMapping("/api/")
public class RegistrationManager {

    private final EmailNewspaperFollowersDAO emailNewspaperFollowersDAO;

    private final EmailService emailService;



    @Autowired
    public RegistrationManager(EmailNewspaperFollowersDAO emailNewspaperFollowersDAO, EmailService emailService) {
        this.emailNewspaperFollowersDAO = emailNewspaperFollowersDAO;
        this.emailService = emailService;
    }


    @PostMapping(path = "register")
    public void registerNewAccount(@RequestBody RegistrationRequest request) throws MessagingException {
        EmailNewspaperFollower emailNewspaperFollower = new EmailNewspaperFollower().emailNewspaperFollowerFromRegistrationRequest(request);
        emailNewspaperFollowersDAO.saveEmailNewspaperFollower(emailNewspaperFollower);
    }

    @GetMapping(path = "check-if-registered")
    public Boolean isRegistered(@RequestParam String email){
        return emailNewspaperFollowersDAO.checkIfEmailAlreadyRegistered(email);
    }
}
