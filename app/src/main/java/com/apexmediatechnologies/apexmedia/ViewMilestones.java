package com.apexmediatechnologies.apexmedia;

public class ViewMilestones {

    private String paymentId;

    private String status;

    private String paymentDate;

    private String amount;

    private String description;

    private String attachment;

    public ViewMilestones(String paymentId, String status, String paymentDate, String amount, String description,
                          String attachment)
    {

        this.paymentId=paymentId;

       this.status=status;

       this.paymentDate=paymentDate;

       this.amount=amount;

       this.description=description;

       this.attachment=attachment;


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

    public String getAttachment ()
    {
        return attachment;
    }

    public void setAttachment (String attachment)
    {
        this.attachment = attachment;
    }






}