package com.example.models;

public class ProfileModel {

    private String profileName;
    private int totalDailyTarget;
    private String date;
    private String time;
    private int weight;

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public int getTotalDailyTarget() {
        return totalDailyTarget;
    }

    public void setTotalDailyTarget(int totalDailyTarget) {
        this.totalDailyTarget = totalDailyTarget;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
