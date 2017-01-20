package com.apexmediatechnologies.apexmedia;

import java.util.List;

public class Options {

    private String ansId;

    private String ansText;

    public Options(String ansId, String ansText)
    {
        this.ansId = ansId;
        this.ansText = ansText;
    }

    public String getAnsId ()
    {
        return ansId;
    }

    public void setAnsId (String ansId)
    {
        this.ansId = ansId;
    }

    public String getAnsText()
    {
        return ansText;
    }

    public void setAnsText(String ansText)
    {
        this.ansText = ansText;
    }


}