package com.example.jobsnewspaper.domains;

import java.util.*;
public enum MessageStatus {
    NOT_SENT(0, "Not Sent"),
    SENDING(1, "Sending"),
    SENT(2, "Sent");




    public int ind;
    public String name;
    MessageStatus(int i, String sent) {
        ind = i;
        name = sent;
    }

    public int getInd(){return ind;}

    public String getName(){
        return name;
    }

    public static List<String> getAllStatusesNames(){
        List<String> list = new ArrayList<>();
        for (MessageStatus status : MessageStatus.values()){
            list.add(status.getName());
        }
        return list;
    }
}
