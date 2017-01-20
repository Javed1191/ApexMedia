package com.apexmediatechnologies.apexmedia;

public class JobRequest
{

    public String getRequsestId() {
        return requsestId;
    }

    public void setRequsestId(String requsestId) {
        this.requsestId = requsestId;
    }

    String requsestId;

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    String job_id;

    public String getJob_name() {
        return job_name;
    }

    public void setJob_name(String job_name) {
        this.job_name = job_name;
    }

    String job_name;

    public String getContractor_info() {
        return contractor_info;
    }

    public void setContractor_info(String contractor_info) {
        this.contractor_info = contractor_info;
    }

    String contractor_info;

    public String getContractor_pic() {
        return contractor_pic;
    }

    public void setContractor_pic(String contractor_pic) {
        this.contractor_pic = contractor_pic;
    }

    String contractor_pic;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    String message;

    public String getPosted_date() {
        return posted_date;
    }

    public void setPosted_date(String posted_date) {
        this.posted_date = posted_date;
    }

    String posted_date;



    public JobRequest(String requsestId, String job_id, String job_name, String contractor_info, String contractor_pic,
                      String message, String posted_date)
    {

        this.requsestId=requsestId;

        this.job_id=job_id;

        this.job_name=job_name;

        this.contractor_info=contractor_info;

        this.contractor_pic=contractor_pic;

        this.message=message;

        this.posted_date=posted_date;

    }




}