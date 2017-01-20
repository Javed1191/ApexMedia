package com.apexmediatechnologies.apexmedia;

public class MyProposals {

    private String jobId;

    private String job_title;

    private String created_at;

    private String status;

    private String job_progress_status;

    private String total_proposals;

    public String getEmployer_id() {
        return employer_id;
    }

    public void setEmployer_id(String employer_id) {
        this.employer_id = employer_id;
    }

    private  String employer_id;

    public String getPorposal_id() {
        return porposal_id;
    }

    public void setPorposal_id(String porposal_id) {
        this.porposal_id = porposal_id;
    }

    private String porposal_id;

    public MyProposals(String jobId, String job_title, String created_at, String status, String job_progress_status,
                       String total_proposals, String employer_id, String porposal_id)
    {
        this.job_progress_status=job_progress_status;
        this.jobId=jobId;

       this.job_title=job_title;

       this.created_at=created_at;

       this.status=status;

       this.total_proposals=total_proposals;
        this.employer_id=employer_id;
        this.porposal_id=porposal_id;

    }


    public String getJobProgress ()
    {
        return job_progress_status;
    }

    public void setJobProgress (String job_progress_status)
    {
        this.job_progress_status = job_progress_status;
    }


    public String getJobId ()
    {
        return jobId;
    }

    public void setJobId (String jobId)
    {
        this.jobId = jobId;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getTotal_proposals ()
    {
        return total_proposals;
    }

    public void setTotal_proposals (String total_proposals)
    {
        this.total_proposals = total_proposals;
    }


    public String getJob_title ()
    {
        return job_title;
    }

    public void setJob_title (String job_title)
    {
        this.job_title = job_title;
    }

    public String getCreated_at ()
    {
        return created_at;
    }

    public void setCreated_at (String created_at)
    {
        this.created_at = created_at;
    }



}