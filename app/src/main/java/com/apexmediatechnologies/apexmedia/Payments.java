package com.apexmediatechnologies.apexmedia;

public class Payments {

    private String paymentId;

    private String status;

    private String paymentDate;

    private String amount;

    private String description;

    private String project;
    private String projectId;

    public Payments(String paymentId, String status, String paymentDate, String amount, String description,
                    String project, String projectId)
    {

        this.paymentId = paymentId;

        this.status = status;

        this.paymentDate = paymentDate;

        this.amount = amount;

        this.description = description;

        this.project = project;
        this.projectId = projectId;


    }


    public String getPaymentId ()
    {
        return paymentId;
    }

    public void setPaymentId (String paymentId)
    {
        this.paymentId = paymentId;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getPaymentDate ()
    {
        return paymentDate;
    }

    public void setPaymentDate (String paymentDate)
    {
        this.paymentDate = paymentDate;
    }

    public String getAmount ()
    {
        return amount;
    }

    public void setAmount (String amount)
    {
        this.amount = amount;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getProject ()
    {
        return project;
    }

    public void setProject (String project)
    {
        this.project = project;
    }

    public String getProjectId ()
    {
        return projectId;
    }

    public void setProjectId (String projectId)
    {
        this.projectId = projectId;
    }



}