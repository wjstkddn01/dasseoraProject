package com.cookandroid.lastproject;

public class Memo {


    int seq;
    String title;     //메모제목
    String maintext;  //메모
    String subtext;   //날짜
    int isdone;       //완료여부

    public Memo(int seq, String title, String maintext, String subtext, int isdone) {
        this.title = title;
        this.seq = seq;
        this.maintext = maintext;
        this.subtext = subtext;
        this.isdone = isdone;
    }

    public Memo(String title, String maintext, String subtext, int isdone) {
        this.title = title;
        this.maintext = maintext;
        this.subtext = subtext;
        this.isdone = isdone;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getMaintext() {
        return maintext;
    }

    public void setMaintext(String maintext) {
        this.maintext = maintext;
    }

    public String getSubtext() {
        return subtext;
    }

    public void setSubtext(String subtext) {
        this.subtext = subtext;
    }

    public int getIsdone() {
        return isdone;
    }

    public void setIsdone(int isdone) {
        this.isdone = isdone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
