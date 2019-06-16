package com.example.campus.bean;


import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by snsoft on 2016/7/12.
 */
public class User extends BmobUser {

    private String sex;//性别
    //    private Integer level;//等级
//    private String pronoun;//头衔
//    private String constellation;//星座
//    private String abo;//血型
    private String studentname;
    private String signature;//个性签名
    private BmobFile avatar;//头像
    private BmobDate birthday;//生日
    private BmobPointer studentid;


    public User() {
    }

//    private Integer levelScore;//等级积分

//    public Integer getLevelScore() {
//        return levelScore;
//    }
//
//    public void setLevelScore(Integer levelScore) {
//        this.levelScore = levelScore;
//    }
//
//    public String getPronoun() {
//        return pronoun;
//    }
//
//    public void setPronoun(String pronoun) {
//        this.pronoun = pronoun;
//    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

//    public Integer getLevel() {
//        return level;
//    }
//
//    public void setLevel(Integer level) {
//        this.level = level;
//    }

//    public String getConstellation() {
//        return constellation;
//    }
//
//    public void setConstellation(String constellation) {
//        this.constellation = constellation;
//    }

//    public String getAbo() {
//        return abo;
//    }
//
//    public void setAbo(String abo) {
//        this.abo = abo;
//    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public BmobFile getAvatar() {
        return avatar;
    }

    public void setAvatar(BmobFile avatar) {
        this.avatar = avatar;
    }

    public BmobDate getBirthday() {
        return birthday;
    }

    public void setBirthday(BmobDate birthday) {
        this.birthday = birthday;
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

//    public BmobPointer getStudentid(){
//        return studentid;
//    }
//    public void setStudentid(BmobPointer studengid){
//        this.studentid = studentid;
//    }
//
//    public void setGrade(Grade studentid) {
//
//    }
}
