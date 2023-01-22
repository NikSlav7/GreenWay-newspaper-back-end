package com.example.jobsnewspaper.emailTemplates.insidecontent;

public abstract class InsideContent {


    public abstract String getInsideContent();

    public abstract InsideContent insertHeaderAndText(String header, String txt);


}
