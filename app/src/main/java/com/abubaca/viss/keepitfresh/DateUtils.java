package com.abubaca.viss.keepitfresh;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by viss on 4/7/17.
 */

public class DateUtils {

    public String getDateTimeStamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return "_"+sdf.format(new Date());
    }

    public String getDateStamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
        return sdf.format(new Date());
    }

    public String addDaysToDate(String date , int daysCount){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
        Calendar c = Calendar.getInstance();

        try {
            c.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        c.add(Calendar.DATE, daysCount);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yy");
        return sdf1.format(c.getTime());
    }


}
