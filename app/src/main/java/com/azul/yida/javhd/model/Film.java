package com.azul.yida.javhd.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Film  implements Parcelable{
    private String realurl;
    private String title;
    private String url;
    private String pid;
    private String actorimg;
    private String picurl;
    private String actorname;
    private String videobackimg;

    protected Film(Parcel in) {
        realurl = in.readString();
        title = in.readString();
        url = in.readString();
        pid = in.readString();
        actorimg = in.readString();
        picurl = in.readString();
        actorname = in.readString();
        videobackimg = in.readString();
    }

    public static final Creator<Film> CREATOR = new Creator<Film>() {
        @Override
        public Film createFromParcel(Parcel in) {
            return new Film(in);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };

    public String getRealurl() {
        return realurl;
    }

    public void setRealurl(String realurl) {
        this.realurl = realurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getActorimg() {
        return actorimg;
    }

    public void setActorimg(String actorimg) {
        this.actorimg = actorimg;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getActorname() {
        return actorname;
    }

    public void setActorname(String actorname) {
        this.actorname = actorname;
    }

    public String getVideobackimg() {
        return videobackimg;
    }

    public void setVideobackimg(String videobackimg) {
        this.videobackimg = videobackimg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(realurl);
        dest.writeString(title);
        dest.writeString(url);
        dest.writeString(pid);
        dest.writeString(actorimg);
        dest.writeString(picurl);
        dest.writeString(actorname);
        dest.writeString(videobackimg);
    }
}
