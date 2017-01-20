package com.apexmediatechnologies.apexmedia;

public class JobProposals {

    private String jobId;

    private String rating;

    private String amount_per_hour;

    private String estimated_duration;

    private String description;

    private String created_at;

    private String posted_user_firt_name;
    private String hours_per_week;
    private String user_image;
    private String awarded;
    private String proposalId;
    private String contractorId;


    public JobProposals(String jobId, String rating, String amount_per_hour, String estimated_duration, String description,
                        String created_at, String posted_user_firt_name, String hours_per_week,String user_image,
                        String awarded, String proposalId, String contractorId)
    {

        this.jobId=jobId;

       this.rating=rating;

       this.amount_per_hour=amount_per_hour;

       this.estimated_duration=estimated_duration;

       this.description=description;

       this.created_at=created_at;

       this.posted_user_firt_name=posted_user_firt_name;

       this.hours_per_week=hours_per_week;
        this.user_image=user_image;
        this.awarded=awarded;
        this.proposalId=proposalId;
        this.contractorId=contractorId;

    }


    public String getJobId ()
    {
        return jobId;
    }

    public void setJobId (String jobId)
    {
        this.jobId = jobId;
    }

    public String getRating ()
    {
        return rating;
    }

    public void setRating (String rating)
    {
        this.rating = rating;
    }

    public String getAmountPerHour ()
    {
        return amount_per_hour;
    }

    public void setAmountPerHour (String amount_per_hour)
    {
        this.amount_per_hour = amount_per_hour;
    }

    public String getEstimatedDuration ()
    {
        return estimated_duration;
    }

    public void setEstimatedDuration (String estimated_duration)
    {
        this.estimated_duration = estimated_duration;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getCreatedAt ()
    {
        return created_at;
    }

    public void setCreatedAt (String created_at)
    {
        this.created_at = created_at;
    }

    public String getUserName ()
    {
        return posted_user_firt_name;
    }

    public void setUserName (String posted_user_firt_name)
    {
        this.posted_user_firt_name = posted_user_firt_name;
    }

    public String getHoursPerWeek ()
    {
        return hours_per_week;
    }

    public void setHoursPerWeek (String hours_per_week)
    {
        this.hours_per_week = hours_per_week;
    }

    public String getUserImage ()
    {
        return user_image;
    }

    public void setUserImage (String user_image)
    {
        this.user_image = user_image;
    }


    public String getAwarded ()
    {
        return awarded;
    }

    public void setAwarded (String awarded)
    {
        this.awarded = awarded;
    }


    public String getProposalId ()
    {
        return proposalId;
    }

    public void setProposalId(String proposalId)
    {
        this.proposalId = proposalId;
    }

    public String getContractorId()
    {
        return contractorId;
    }

    public void setContractorId(String contractorId)
    {
        this.contractorId = contractorId;
    }


}