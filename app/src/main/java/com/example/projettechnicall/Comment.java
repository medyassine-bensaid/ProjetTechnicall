package com.example.projettechnicall;

import com.google.firebase.database.ServerValue;

public class Comment {
    private String content,uid/*,uimg*/, uidtech;
    private Object timestamp;


    public Comment() {
    }

    public Comment(String content, String uid/*, String uimg*/, String uname) {
        this.content = content;
        this.uid = uid;
        //this.uimg = uimg;
        this.uidtech = uname;
        this.timestamp = ServerValue.TIMESTAMP;

    }

    public Comment(String content, String uid/*, String uimg*/, String uname, Object timestamp) {
        this.content = content;
        this.uid = uid;
        //this.uimg = uimg;
        this.uidtech = uname;
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

//    public String getUimg() {
//        return uimg;
//    }
//
//    public void setUimg(String uimg) {
//        this.uimg = uimg;
//    }

    public String getUidtech() {
        return uidtech;
    }

    public void setUidtech(String uidtech) {
        this.uidtech = uidtech;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }
}

