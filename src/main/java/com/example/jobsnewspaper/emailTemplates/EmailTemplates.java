package com.example.jobsnewspaper.emailTemplates;
import com.example.jobsnewspaper.emailTemplates.mailtemplates.MailTemplate1;
import com.example.jobsnewspaper.emailTemplates.mailtemplates.Template;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Component
public class EmailTemplates {


    private static List<Template> allTemplates;


    public List<Template> getAllTemplates(){
        if (allTemplates != null) return allTemplates;
        List<Template> templates = new ArrayList<>();
        templates.add(new MailTemplate1());

        allTemplates = templates;
        return templates;
    }

    public Template getTemplateByNum(int ind) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return getAllTemplates().get(ind).getClass().getDeclaredConstructor().newInstance();
    }

    public Template getRandomTemplate() throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return getTemplateByNum(new Random().nextInt(getAllTemplates().size()));
    }





}
