package com.example.quand.bookmanagement.Entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Alarm implements Serializable{

    private int id;
    private int hour;
    private int minute;
    private boolean on;
    private boolean vibrate;
    private int ring;
    private int id_book;
    private boolean[] dayOfWeek;

    public Alarm() {
        dayOfWeek = new boolean[7];
    }

    public Alarm(int id, int hour, int minute, boolean on, boolean vibrate, int ring, int id_book, boolean []dayOfWeek) {
        this.id = id;
        this.hour = hour;
        this.minute = minute;
        this.on = on;
        this.vibrate = vibrate;
        this.ring = ring;
        this.id_book = id_book;
        this.dayOfWeek = dayOfWeek;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public boolean isVibrate() {
        return vibrate;
    }

    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }

    public int getRing() {
        return ring;
    }

    public void setRing(int ring) {
        this.ring = ring;
    }

    public int getId_book() {
        return id_book;
    }

    public void setId_book(int id_book) {
        this.id_book = id_book;
    }

    public boolean[] getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(boolean[] dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

}
