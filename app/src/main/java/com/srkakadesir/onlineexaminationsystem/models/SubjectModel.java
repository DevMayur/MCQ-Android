package com.srkakadesir.onlineexaminationsystem.models;

public class SubjectModel extends BlogPostId{
    String title,uid;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public SubjectModel() {
    }

    public SubjectModel(String title, String uid) {
        this.title = title;
        this.uid = uid;
    }
}
