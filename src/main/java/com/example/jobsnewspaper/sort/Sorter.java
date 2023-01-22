package com.example.jobsnewspaper.sort;

import com.example.jobsnewspaper.domains.EmailMessage;
import jakarta.validation.constraints.Email;

import java.util.*;

public class Sorter {




    public void sortEmailMessagesByDate(List<EmailMessage> listToSort){
        if (listToSort.size() <= 1) return;

        List<EmailMessage> messages1 = new ArrayList<>(), messages2 = new ArrayList<>();

        int half = listToSort.size() / 2;

        for (int i = 0; i < half; i++){
            messages1.add(listToSort.get(i));
        }
        for (int i = half; i < listToSort.size();i++){
            messages2.add(listToSort.get(i));
        }
        sortEmailMessagesByDate(messages1);
        sortEmailMessagesByDate(messages2);

        mergeByDate(listToSort, messages1, messages2);
    }

    public void mergeByDate(List<EmailMessage> root, List<EmailMessage> list1, List<EmailMessage> list2){
        int i =0, j = 0, k = 0;
        while (i < list1.size() && j < list2.size()){
            if (list1.get(i).getWhenToSend().isBefore(list2.get(j).getWhenToSend())){
                root.set(k, list1.get(i));
                i++;
                k++;
            }
            else {
                root.set(k, list2.get(j));
                j++;
                k++;
            }
        }
        while (i < list1.size()){
            root.set(k, list1.get(i));
            i++;
            k++;
        }
        while (j < list2.size()){
            root.set(k, list2.get(j));
            j++;
            k++;
        }
    }
}
