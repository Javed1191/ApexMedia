package com.apexmediatechnologies.apexmedia;

import java.util.HashMap;
import java.util.List;

public class Psychometric {

    private String qId;

    private String question;

    private String keyed;

    private List<String> ansOpt,list_opt_id;
    private List<Options> listAnsOptObj;




    public Psychometric(String qId, String question, String keyed, List<String> ansOpt,List<Options> listAnsOptObj,List<String>list_opt_id)
    {
        this.qId = qId;
        this.question = question;
        this.keyed = keyed;
        this.ansOpt = ansOpt;
        this. listAnsOptObj=listAnsOptObj;
        this.list_opt_id=list_opt_id;
    }

    public String getQuestionId ()
    {
        return qId;
    }

    public void setQuestionId (String qId)
    {
        this.qId = qId;
    }

    public String getQuestion()
    {
        return question;
    }

    public void setQuestion (String question)
    {
        this.question = question;
    }

    public String getKeyed ()
    {
        return keyed;
    }

    public void setKeyed(String keyed)
    {
        this.keyed = keyed;
    }

    public List getOtp ()
    {
        return ansOpt;
    }

    public void setOpt (List ansOpt)
    {
        this.ansOpt = ansOpt;
    }

    public List<Options> getOtpObjList ()
    {
        return listAnsOptObj;
    }

    public void setOptObjList (List<Options> listAnsOptObj)
    {
        this.listAnsOptObj = listAnsOptObj;
    }


    public List<String> getOtpIdList ()
    {
        return list_opt_id;
    }

    public void setOptIdList (List<String> list_opt_id)
    {
        this.list_opt_id = list_opt_id;
    }

}