package com.apexmediatechnologies.apexmedia;

public class JobFeeds {

    private String jobId;

    private String job_budget;

    private String work_rate_description;

    private String job_skills;

    private String work_type;

    private String posted_userId;

    private String posted_user_firt_name;

    private String work_rate;

    private String start_type;

    private String user_country_name;

    private String hours_per_week;

    private String job_title;

    private String created_at;

    private String updated_on;

    private String job_description;

    private String posted_user_last_name;

    private String user_image;

    private String schedule_date;
    private String job_progress_status;
    private String empId;

    public JobFeeds( String job_progress_status, String jobId, String job_budget, String work_rate_description, String job_skills, String work_type,
                     String posted_userId, String posted_user_firt_name, String work_rate, String start_type, String user_country_name,
                     String hours_per_week, String job_title, String created_at, String updated_on, String job_description, String posted_user_last_name,
                     String user_image, String schedule_date, String empId)
    {
        this.job_progress_status=job_progress_status;

        this.jobId=jobId;

       this.job_budget=job_budget;

       this.work_rate_description=work_rate_description;

       this.job_skills=job_skills;

       this.work_type=work_type;

       this.posted_userId=posted_userId;

       this.posted_user_firt_name=posted_user_firt_name;

       this.work_rate=work_rate;

       this.start_type=start_type;

       this.user_country_name=user_country_name;

       this.hours_per_week=hours_per_week;

       this.job_title=job_title;

       this.created_at=created_at;

       this.updated_on=updated_on;

       this.job_description=job_description;

       this.posted_user_last_name=posted_user_last_name;

       this.user_image=user_image;

       this.schedule_date=schedule_date;
        this.empId=empId;

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

    public String getJob_budget ()
    {
        return job_budget;
    }

    public void setJob_budget (String job_budget)
    {
        this.job_budget = job_budget;
    }

    public String getWork_rate_description ()
    {
        return work_rate_description;
    }

    public void setWork_rate_description (String work_rate_description)
    {
        this.work_rate_description = work_rate_description;
    }

    public String getJob_skills ()
    {
        return job_skills;
    }

    public void setJob_skills (String job_skills)
    {
        this.job_skills = job_skills;
    }

    public String getWork_type ()
    {
        return work_type;
    }

    public void setWork_type (String work_type)
    {
        this.work_type = work_type;
    }

    public String getPosted_userId ()
    {
        return posted_userId;
    }

    public void setPosted_userId (String posted_userId)
    {
        this.posted_userId = posted_userId;
    }

    public String getPosted_user_firt_name ()
    {
        return posted_user_firt_name;
    }

    public void setPosted_user_firt_name (String posted_user_firt_name)
    {
        this.posted_user_firt_name = posted_user_firt_name;
    }

    public String getWork_rate ()
    {
        return work_rate;
    }

    public void setWork_rate (String work_rate)
    {
        this.work_rate = work_rate;
    }

    public String getStart_type ()
    {
        return start_type;
    }

    public void setStart_type (String start_type)
    {
        this.start_type = start_type;
    }

    public String getUser_country_name ()
    {
        return user_country_name;
    }

    public void setUser_country_name (String user_country_name)
    {
        this.user_country_name = user_country_name;
    }

    public String getHours_per_week ()
    {
        return hours_per_week;
    }

    public void setHours_per_week (String hours_per_week)
    {
        this.hours_per_week = hours_per_week;
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

    public String getUpdated_on ()
    {
        return updated_on;
    }

    public void setUpdated_on (String updated_on)
    {
        this.updated_on = updated_on;
    }

    public String getJob_description ()
    {
        return job_description;
    }

    public void setJob_description (String job_description)
    {
        this.job_description = job_description;
    }

    public String getPosted_user_last_name ()
    {
        return posted_user_last_name;
    }

    public void setPosted_user_last_name (String posted_user_last_name)
    {
        this.posted_user_last_name = posted_user_last_name;
    }

    public String getUser_image ()
    {
        return user_image;
    }

    public void setUser_image (String user_image)
    {
        this.user_image = user_image;
    }

    public String getSchedule_date ()
    {
        return schedule_date;
    }

    public void setSchedule_date (String schedule_date)
    {
        this.schedule_date = schedule_date;
    }

    public String getEmpId ()
    {
        return empId;
    }

    public void setEmpId (String empId)
    {
        this.empId = empId;
    }


}