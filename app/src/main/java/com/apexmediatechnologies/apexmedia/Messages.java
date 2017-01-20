package com.apexmediatechnologies.apexmedia;

public class Messages {

    private String id;

    private String msg_from;

    private String project;

    private String message;

    private String date_time;
    private String job_id;


    public Messages(String id, String msg_from, String project, String message, String date_time, String job_id)
    {
        this.id = id;
        this.msg_from = msg_from;
        this.project = project;
        this.message = message;
        this.date_time = date_time;
        this.job_id = job_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsgFrom() {
        return msg_from;
    }

    public void setMsgFrom(String msg_from) {
        this.msg_from = msg_from;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateTime() {
        return date_time;
    }

    public void setDateTime(String date_time) {
        this.date_time = date_time;
    }

    public String getJobId() {
        return job_id;
    }

    public void setJobId(String job_id) {
        this.job_id = job_id;
    }


}