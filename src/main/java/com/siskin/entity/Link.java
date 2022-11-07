package com.siskin.entity;


public class Link {
    private String serverAddress;
    private String statistic_time;
    private Integer statistic_count;
    private String start_time;
    private String end_time;
    private String request;

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getStatistic_time() {
        return statistic_time;
    }

    public void setStatistic_time(String statistic_time) {
        this.statistic_time = statistic_time;
    }

    public Integer getStatistic_count() {
        return statistic_count;
    }

    public void setStatistic_count(Integer statistic_count) {
        this.statistic_count = statistic_count;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getRequest() {
        return request.replace("*", "%").substring(0,request.lastIndexOf("/",request.lastIndexOf("/")));
    }

    public void setRequest(String request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "link{" +
                "serverAddress='" + serverAddress + '\'' +
                ", statistic_time='" + statistic_time + '\'' +
                ", statistic_count=" + statistic_count +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", request='" + request + '\'' +
                '}';
    }
}

