package com.example.jobsnewspaper.repositories;

import com.example.jobsnewspaper.domains.EmailNewspaperFollower;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableScan
public interface EmailNewspaperFollowersRepo extends JpaRepository<EmailNewspaperFollower, String> {
    Optional<EmailNewspaperFollower> getEmailNewspaperFollowerByFollowerId(String followerId);
}
