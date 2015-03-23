package com.javivaquero.todolistfragments.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by javi-vaquero on 23/03/15.
 */
public class ToDo implements Parcelable{

    private int mData;


    private String task;
    private Date created;

    public ToDo(String task) {
        this.task = task;
        this.created = new Date();
    }

    public ToDo(String task, Date created) {
        this.task = task;
        this.created = created;
    }

    public String getTask() {
        return task;
    }

    public Date getCreated() {
        return created;
    }

    public String getCreatedFormatted() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(created);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(task);
        dest.writeLong(created.getTime());
    }

    private ToDo(Parcel in) {
        task = in.readString();
        created=new Date(in.readLong());
    }

    public static final Parcelable.Creator<ToDo> CREATOR
            = new Parcelable.Creator<ToDo>() {
        public ToDo createFromParcel(Parcel in) {
            return new ToDo(in);
        }

        public ToDo[] newArray(int size) {
            return new ToDo[size];
        }
    };

}
