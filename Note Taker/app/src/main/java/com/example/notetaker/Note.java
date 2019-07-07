package com.example.notetaker;

import android.content.Context;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Note implements Serializable {

    private long DateTime;
    private String Title;
    private String Content;

    public Note(long dateTime, String title, String content) {
        DateTime = dateTime;
        Title = title;
        Content = content;
    }

    public long getDateTime() {
        return DateTime;
    }

    public void setDateTime(long dateTime) {
        DateTime = dateTime;
    }

    public String getDateTimeFormatted(Context context)
    {
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/YYY HH:mm:ss",context.getResources().getConfiguration().locale);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(new Date(DateTime));
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }


}
