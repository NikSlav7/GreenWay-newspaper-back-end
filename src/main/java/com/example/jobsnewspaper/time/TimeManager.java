package com.example.jobsnewspaper.time;


import org.mockito.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.time.*;

@Component
public class TimeManager {


    public static long getUTCTime(){
        LocalDateTime time= LocalDateTime.ofInstant(Instant.now(), ZoneOffset.ofHours(0));
        return time.toInstant(ZoneOffset.ofHours(2)).toEpochMilli();
    }

}
