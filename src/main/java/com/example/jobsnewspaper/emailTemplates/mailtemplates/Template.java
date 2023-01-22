package com.example.jobsnewspaper.emailTemplates.mailtemplates;

import com.example.jobsnewspaper.emailTemplates.insidecontent.InsideContent;

public abstract class Template {


    private String content;

    public abstract String getTemplateContent();

    public abstract Template setTemplateHeader(String header);

    public abstract Template setTemplateSubHeader(String subHeader);

    public abstract String insertInsideContent(int num, InsideContent insideContent);



}
