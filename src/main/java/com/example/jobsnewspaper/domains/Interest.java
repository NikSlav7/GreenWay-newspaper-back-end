package com.example.jobsnewspaper.domains;
import com.example.jobsnewspaper.exceptions.WrongEmailDetailsException;

import java.sql.SQLTransactionRollbackException;
import java.util.*;
import java.util.stream.Collectors;

public enum Interest {

    OVERALL_HEALTH("Overall Health"),
    HEALTHY_DIET("Healthy Diet"),
    SPORT("Sport"),
    WEIGHT_LOSE("Weight lose"),
    STRESS_HANDLING("Stress handling"),
    MENTAL_HEALTH("Mental Health");




    String interestName;

    Interest(String s) {
        interestName = s;
    }

    public String getInterestName(){
        return interestName;
    }

    public static List<String> getAllInterestNames(){
        return Arrays.stream(Interest.values()).map(interest -> interest.interestName).collect(Collectors.toList());
    }

    public static String interestToString(Interest interest){
        if (interest == null) return null;
        return interest.interestName;
    }

    public static Interest interestFromString(String interestName){
        for (Interest interest : Interest.values()){
            if (interest.interestName.equalsIgnoreCase(interestName) || interest.name().equalsIgnoreCase(interestName)) return interest;

        }
        return null;
    }

    public static Map<Interest, String> interestMapFromStringMap(Map<String, String> map) throws WrongEmailDetailsException {
        if (map.size() != Interest.values().length) throw new WrongEmailDetailsException("Not all messages were provided");
        Map<Interest, String> interestStringMap = new HashMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()){
            Interest interest = Interest.interestFromString(entry.getKey());
            if (interest == null) throw new WrongEmailDetailsException("Can't find provided interest name");
            interestStringMap.put(interest, entry.getValue());
        }
        return interestStringMap;
    }

    public static Map<String, String> stringMapFromInterestMap(Map<Interest, String> map){
        Map<String, String> stringMap = new HashMap<>();

        for (Map.Entry<Interest, String> entry : map.entrySet()){
            stringMap.put(interestToString(entry.getKey()), entry.getValue());
        }
        return stringMap;
    }

}
