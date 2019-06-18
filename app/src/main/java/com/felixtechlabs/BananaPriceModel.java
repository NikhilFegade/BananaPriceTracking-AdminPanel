package com.felixtechlabs;

public class BananaPriceModel {

    private String body;
    private long CreatedAt;
    private long sortCreatedAt;
    private String smsDate;



    public BananaPriceModel(String body, long createdAt, long sortCreatedAt, String smsDate) {
        this.body = body;
        CreatedAt = createdAt;
        this.sortCreatedAt = sortCreatedAt;
        this.smsDate = smsDate;
    }

    public BananaPriceModel() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(long createdAt) {
        CreatedAt = createdAt;
    }

    public long getSortCreatedAt() {
        return sortCreatedAt;
    }

    public void setSortCreatedAt(long sortCreatedAt) {
        this.sortCreatedAt = sortCreatedAt;
    }

    public String getSmsDate() {
        return smsDate;
    }

    public void setSmsDate(String smsDate) {
        this.smsDate = smsDate;
    }
}
