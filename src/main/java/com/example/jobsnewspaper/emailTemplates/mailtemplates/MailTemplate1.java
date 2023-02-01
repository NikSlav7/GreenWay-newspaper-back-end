package com.example.jobsnewspaper.emailTemplates.mailtemplates;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.example.jobsnewspaper.emailTemplates.insidecontent.InsideContent;

import java.sql.Struct;
import java.util.*;
public class MailTemplate1 extends Template {


    private String header = "", subHeader = "";
    private List<String> contentList = List.of("", "", "");


    @Override
    public String getTemplateContent() {
        StringBuilder builder = new StringBuilder();
        for (String content : contentList){
            builder.append(content + "\n");
        }
        return " <div style=\"width: 600px; background-color: gainsboro;\">\n" +

                "        <div style=\"background-size: cover; margin-left: 50px; width: 500px; padding-top: 35px; padding-bottom: 100px; border-radius: 15px; background-color: white; margin-top: 90px; margin-bottom: 50px;\">\n" +
                "\n" +
                "            <p style=\"width: 100%; text-align: center; font-size: 40px; font-weight: 600; font-family:'montserrat';\">" + header +"</p>\n" +
                "\n" +
                "            <p style=\"width: 100%; text-align: center; font-family: 'montserrat'; font-size: 18px;\">" + subHeader + "</p>\n" +
                "\n" +
                "            <div style=\"display: grid; height: 100%; width: 95%; margin-left: 2.5%; padding-bottom: 10%; row-gap: 5%;\">\n" +
                               builder.toString() + "\n"+
                "            <p style=\"width: 100%;margin-top: 200px ;text-align: center; font-size: 12px; font-family: 'montserrat';\">You may cancel the subscription at any time </br> \n" +
                "            <a style=\"text-decoration: none;\" href=\"google.com\">Click here</a>  </p>\n" +
                "        </div>\n" +
                "    </div>";
    }

    @Override
    public Template setTemplateHeader(String header) {
        this.header = header;
        return this;
    }

    @Override
    public Template setTemplateSubHeader(String subHeader) {
        this.subHeader = subHeader;
        return this;
    }

    public List<String> getContentList(){
        return contentList;
    }

    @Override
    public String insertInsideContent(int num, InsideContent insideContent) {
        if (insideContent == null) return this.getTemplateContent();
        List<String> copy = new ArrayList<>(getContentList());
        if (copy.size() <= num) copy.add(insideContent.getInsideContent());
        else {
            copy.set(num, insideContent.getInsideContent());
        }
        contentList=copy;
        return getTemplateContent();
    }




}
