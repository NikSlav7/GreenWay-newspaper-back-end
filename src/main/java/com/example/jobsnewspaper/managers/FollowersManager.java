package com.example.jobsnewspaper.managers;


import com.example.jobsnewspaper.daos.EmailNewspaperFollowersDAO;
import com.example.jobsnewspaper.domains.EmailNewspaperFollower;
import com.example.jobsnewspaper.domains.EmailNewspaperFollowerWithStringInterests;
import com.example.jobsnewspaper.exceptions.NoFollowersFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RestController
@RequestMapping(path = "/api")
public class FollowersManager {


    private final EmailNewspaperFollowersDAO emailNewspaperFollowersDAO;

    public FollowersManager(EmailNewspaperFollowersDAO emailNewspaperFollowersDAO) {
        this.emailNewspaperFollowersDAO = emailNewspaperFollowersDAO;
    }

    @PostMapping(path = "/followers/get-by-email")
    public EmailNewspaperFollowerWithStringInterests getEmailNewspaperFollower(@RequestBody String email) throws NoFollowersFoundException {
        return new EmailNewspaperFollowerWithStringInterests().fromFollower(emailNewspaperFollowersDAO.getFollower(email));
    }

    @PostMapping(path = "/followers/get-by-email-prefix")
    public List<EmailNewspaperFollowerWithStringInterests> getEmailFollowers(@RequestBody String prefix) throws NoFollowersFoundException {
        return emailNewspaperFollowersDAO
                .getFollowers(prefix).stream()
                .map(follower -> new EmailNewspaperFollowerWithStringInterests().fromFollower(follower))
                .collect(Collectors.toList());
    }

    @PostMapping(path = "/followers/remove")
    public void removeFollower(@RequestBody String email){
        emailNewspaperFollowersDAO.removeEmailNewspaperFollower(email);
    }

}
