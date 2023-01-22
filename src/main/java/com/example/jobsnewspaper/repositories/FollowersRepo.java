package com.example.jobsnewspaper.repositories;


import com.example.jobsnewspaper.domains.EmailNewspaperFollower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowersRepo extends JpaRepository<EmailNewspaperFollower, String> {

}
