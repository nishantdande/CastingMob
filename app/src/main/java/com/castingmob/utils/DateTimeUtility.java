package com.castingmob.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nishant on 13/03/16.
 */
public class DateTimeUtility {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm aaa");

    public static String messageTimeStamp(long time){
        return simpleDateFormat.format(new Date(time));
    }

    public static String messageTimeStamp(Date date){
        return simpleDateFormat.format(date);
    }
}
