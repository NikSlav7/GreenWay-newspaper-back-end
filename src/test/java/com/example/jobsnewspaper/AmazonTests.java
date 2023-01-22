package com.example.jobsnewspaper;


import com.example.jobsnewspaper.daos.EmailMessagesDao;
import com.example.jobsnewspaper.daos.EmailNewspaperFollowersDAO;
import com.example.jobsnewspaper.domains.EmailNewspaperFollower;
import com.example.jobsnewspaper.domains.MessageStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.Message;
import java.util.List;

@SpringBootTest
public class AmazonTests {


    @Autowired
    private EmailNewspaperFollowersDAO followersDAO;

    @Autowired
    private EmailMessagesDao messagesDao;

    @Test
    void getAllFollowersTest(){
        List<EmailNewspaperFollower> followersList = followersDAO.getAllEmailNewspaperFollowers();
        followersList.stream().forEach(System.out::println);
        assert followersList.size() != 0;
    }

    @Test
    void testIfUserPresent(){
        assert !followersDAO.checkIfEmailAlreadyRegistered("niks");
    }

    @Test
    void getDayMessages(){
        assert messagesDao.getDayMessages(1673886290180L, 0, MessageStatus.NOT_SENT.getInd()).size() != 0;
    }
}
