package com.example.campus;

import com.example.campus.bean.User;

import cn.bmob.v3.BmobObject;

public class Grade extends BmobObject {
    private String objectid;
    private User studentid;
    private String classname;
    private String cj;
    private String credit;

    public String getObjectid() {
        return objectid;
    }

    public void setObjectid(String objectid) {
        this.objectid = objectid;
    }

    public User getStudentid() {
        return studentid;
    }

    public void setStudentid(User studentid) {
        this.studentid = studentid;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getCj() {
        return cj;
    }

    public void setCj(String cj) {
        this.cj = cj;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public Grade(String objectid, User studentid, String classname, String cj, String credit) {
        this.objectid = objectid;
        this.studentid = studentid;
        this.classname = classname;
        this.cj = cj;
        this.credit = credit;
    }
}
