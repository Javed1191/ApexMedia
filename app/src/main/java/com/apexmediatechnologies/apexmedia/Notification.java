package com.apexmediatechnologies.apexmedia;

public class Notification {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getProposal_id() {
        return proposal_id;
    }

    public void setProposal_id(String proposal_id) {
        this.proposal_id = proposal_id;
    }

    public String getFrom_user_id() {
        return from_user_id;
    }

    public void setFrom_user_id(String from_user_id) {
        this.from_user_id = from_user_id;
    }

    public String getTo_user_id() {
        return to_user_id;
    }

    public void setTo_user_id(String to_user_id) {
        this.to_user_id = to_user_id;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    String title, message,job_id, proposal_id, from_user_id, to_user_id, read,
            screen, module, date_time;

    public Notification(String title, String job_id, String proposal_id, String from_user_id, String to_user_id,
                        String read, String screen, String module, String date_time,String message)
    {
        this.title=title;
        this.job_id=job_id;
       this.proposal_id=proposal_id;
       this.from_user_id=from_user_id;
       this.to_user_id=to_user_id;
       this.read=read;
       this.screen=screen;
       this.module=module;
       this.date_time=date_time;
        this.message=message;

    }




}