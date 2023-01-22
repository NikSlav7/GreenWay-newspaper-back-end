package com.example.jobsnewspaper.emailTemplates.insidecontent;

public class InsideContentN1 extends InsideContent {


    private String insideContent =
                    "                <div style=\"margin-bottom: -150px; margin-top: 25px; font-family: 'montserrat'; font-weight: 600;\">\n" +
                    "                    <p style=\"padding-left: 15px; font-size: 33px; font-weight: 900;\">$HEAD$</p>\n" +
                    "                </div>\n" +
                            " <style>\\n\" +\n" +
                            "                \"                .img-full{\\n\" +\n" +
                            "                \"                    width: 100%;\\n\" +\n" +
                            "                \"                    aspect-ratio: 4/2.5;\\n\" +\n" +
                            "                \"                    object-fit: contain;\\n\" +\n" +
                            "                \"                }\\n\" +\n" +
                            "                \"\\n\" +\n" +
                            "                \"                .img-small-left{\\n\" +\n" +
                            "                \"                    width: 50%;\\n\" +\n" +
                            "                \"                    aspect-ratio: 4/2.5;\\n\" +\n" +
                            "                \"                    object-fit: contain;\\n\" +\n" +
                            "                \"                }\\n\" +\n" +
                            "                \"                .img-small-center{\\n\" +\n" +
                            "                \"                    width: 50%;\\n\" +\n" +
                            "                \"                    margin-left: 25%;\\n\" +\n" +
                            "                \"                    aspect-ratio: 4/2.5;\\n\" +\n" +
                            "                \"                    object-fit: contain;\\n\" +\n" +
                            "                \"                }\\n\" +\n" +
                            "                \"                .img-small-right{\\n\" +\n" +
                            "                \"                    float: right;\\n\" +\n" +
                            "                \"                    width: 50%;\\n\" +\n" +
                            "                \"                    aspect-ratio: 4/2.5;\\n\" +\n" +
                            "                \"                    object-fit: contain;\\n\" +\n" +
                            "                \"                }\\n\" +\n" +
                            "                \"                \\n\" +\n" +
                            "                \"               \\n\" +\n" +
                            "                \"\\n\" +\n" +
                            "                \"            </style>"+
                    "                <div style=\"background-color: white; margin-top: 10px; border-radius: 20px;\";>\n" +
                    "                    <p style=\"font-family: 'montserrat'; font-weight: 600; padding: 15px; font-size: 110% ; line-height: 160%;\">$PAR$</p>\n" +
                    "                    <p><hr></p>\n" +
                    "                </div>";
    @Override
    public String getInsideContent() {
        return insideContent;
    }

    @Override
    public InsideContent insertHeaderAndText(String header, String txt) {
        insideContent = getInsideContent().replace("$HEAD$", header);
        insideContent = getInsideContent().replace("$PAR$", txt);
        return this;
    }
}
