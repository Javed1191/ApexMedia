package com.apexmediatechnologies.apexmedia;

public class FindTalent
{
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    String userId;

    public String getContractor_name() {
        return contractor_name;
    }

    public void setContractor_name(String contractor_name) {
        this.contractor_name = contractor_name;
    }

    public String getContractor_country() {
        return contractor_country;
    }

    public void setContractor_country(String contractor_country) {
        this.contractor_country = contractor_country;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public String getHourly_rates() {
        return hourly_rates;
    }

    public void setHourly_rates(String hourly_rates) {
        this.hourly_rates = hourly_rates;
    }

    public String getContractor_pic() {
        return contractor_pic;
    }

    public void setContractor_pic(String contractor_pic) {
        this.contractor_pic = contractor_pic;
    }

    public String getStr_skills() {
        return str_skills;
    }

    public void setStr_skills(String str_skills) {
        this.str_skills = str_skills;
    }

    String contractor_name;
    String contractor_country;
    String overView;
    String hourly_rates;
    String contractor_pic;
    String str_skills;


    public FindTalent(String userId, String contractor_name, String contractor_country, String overView,
                      String contractor_pic, String hourly_rates, String str_skills)
    {
        this.userId=userId;
        this.contractor_name=contractor_name;
        this.contractor_country=contractor_country;
        this.overView=overView;
        this.contractor_pic=contractor_pic;
        this.hourly_rates=hourly_rates;
        this.str_skills=str_skills;

    }




}