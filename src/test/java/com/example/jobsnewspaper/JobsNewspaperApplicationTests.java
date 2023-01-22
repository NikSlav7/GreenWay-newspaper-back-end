package com.example.jobsnewspaper;

import com.example.jobsnewspaper.daos.EmailMessagesDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JobsNewspaperApplicationTests {

	@Autowired
	EmailMessagesDao emailMessagesDao;

	@Test
	void contextLoads() {
	}


	@Test
	void getRecievers(){
		emailMessagesDao.getAllMessagesToSendNow();
	}





}
