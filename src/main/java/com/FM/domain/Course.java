package com.FM.domain;

public class Course {
    private Integer id;
    private String courseName;
    private Integer type;
    private Integer like;
    private String location;
    private Integer order;
    private boolean isdisplay;

    public Integer getId() {
        return id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public boolean isIsdisplay() {
        return isdisplay;
    }

    public void setIsdisplay(boolean isdisplay) {
        this.isdisplay = isdisplay;
    }
}
