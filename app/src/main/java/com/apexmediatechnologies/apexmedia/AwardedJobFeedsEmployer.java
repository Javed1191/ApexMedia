package com.apexmediatechnologies.apexmedia;

public class AwardedJobFeedsEmployer {

    private String jobId;

    private String empId;

    private String jobName;

    private String jobDesc;

    private String postDate;
    private String proposals;
    private String awardedProposalId;
    private String jobProgress;
    private String jobSkills;
    private String visibility;
    private String Posting_type;
    private String awarded;



    public AwardedJobFeedsEmployer(String jobId, String empId, String jobName, String jobDesc, String postDate,
                                   String proposals, String awardedProposalId, String jobProgress,String jobSkills,
                                   String visibility, String Posting_type, String awarded)
    {
        this.jobId=jobId;
        this.empId=empId;
        this.jobName=jobName;
        this.jobDesc=jobDesc;
        this.postDate=postDate;
        this.proposals=proposals;
        this.awardedProposalId=awardedProposalId;
        this.jobProgress=jobProgress;
        this.jobSkills=jobSkills;
        this.visibility=visibility;
        this.Posting_type=Posting_type;
        this.awarded=awarded;

    }



    public String getJobId ()
    {
        return jobId;
    }

    public void setJobId (String jobId)
    {
        this.jobId = jobId;
    }


    public String getJobName ()
    {
        return jobName;
    }

    public void setJobName(String jobName)
    {
        this.jobName = jobName;
    }

    public String getJobDesc ()
    {
        return jobDesc;
    }

    public void setJobDesc (String jobDesc)
    {
        this.jobDesc = jobDesc;
    }

    public String getPostDate ()
    {
        return postDate;
    }

    public void setPostDate (String postDate)
    {
        this.postDate = postDate;
    }

    public String getEmpId ()
    {
        return empId;
    }

    public void setEmpId (String empId)
    {
        this.empId = empId;
    }

    public String getProposals ()
    {
        return proposals;
    }

    public void setProposals (String proposals)
    {
        this.proposals = proposals;
    }

    public String getAwardedProposalId ()
    {
        return awardedProposalId;
    }

    public void setAwardedProposalId (String awardedProposalId)
    {
        this.awardedProposalId = awardedProposalId;
    }

    public String getJobProgress ()
    {
        return jobProgress;
    }

    public void setJobProgress (String jobProgress)
    {
        this.jobProgress = jobProgress;
    }



    public String getJobSkills ()
    {
        return jobSkills;
    }

    public void setJobSkills (String jobSkills)
    {
        this.jobSkills = jobSkills;
    }
    public String getVisibility ()
    {
        return visibility;
    }

    public void setVisibility (String visibility)
    {
        this.visibility = visibility;
    }
    public String getPosting_type ()
    {
        return Posting_type;
    }

    public void setPosting_type (String Posting_type)
    {
        this.Posting_type = Posting_type;
    }
    public String getAwarded ()
    {
        return awarded;
    }

    public void setAwarded (String awarded)
    {
        this.awarded = awarded;
    }



}