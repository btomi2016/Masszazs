package com.example.masszazs;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Booking {
    private String userId;
    private String time;
    private long date;

    public Booking() {
        // Default constructor required for calls to DataSnapshot.getValue(Booking.class)
    }

    public Booking(String userId, String time, long date) {
        this.userId = userId;
        this.time = time;
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
