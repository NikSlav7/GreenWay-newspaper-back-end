package com.example.jobsnewspaper.emailTemplates;


import org.springframework.stereotype.Component;

@Component
public class TemplatesDataInserter {





    public String insertContentToTemplate(String template, int paragraphNum, String data){
        return template.replace("PAR" + paragraphNum, data);
    }
}
