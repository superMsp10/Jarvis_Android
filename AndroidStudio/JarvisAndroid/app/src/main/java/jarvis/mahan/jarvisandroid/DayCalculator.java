package jarvis.mahan.jarvisandroid;

import android.util.Log;

import org.json.JSONArray;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Mahan on 14/08/2015.
 */


public class DayCalculator {
    Date today;
    Date sept;
    int dayOffset;
    int offset;
    int staticSeptemberDayOffset;
    JSONArray serverData;


    public DayCalculator(JSONArray data, Date date) throws ParseException {
        serverData = data;
        today = date;
        Log.println(Log.ASSERT, "Today", today.toString());

        init();


    }

    void init() throws ParseException {
        staticSeptemberDayOffset = 1;
        offset = 1;
        SimpleDateFormat dateFormat = new SimpleDateFormat("d LLLL yyyy");
        sept = dateFormat.parse("1 September 2015");
        int days = daysSinceStart(today);

      // Log.println(Log.ASSERT, "Days", Integer.toString(days));
       Log.println(Log.ASSERT, "Number of Sat and sun", Double.toString(numberOfSatAndSun(days + offset)));
    }

    public int daysSinceStart(Date toDate) {

        long different = toDate.getTime() - sept.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long elapsedDays = different / daysInMilli;

        return (int) elapsedDays;
    }

    double numberOfSatAndSun(int numberOfDays) {
      //  Log.println(Log.ASSERT, "Number of Weeks", Double.toString(numberOfDays / 7));

       // Log.println(Log.ASSERT, "Number of Weeks rounded", Double.toString(Math.floor(numberOfDays / 7)));

        double sS = Math.floor(numberOfDays / 7) * 2;
        return sS;
    }


/*
    public void printDifference(Date startDate,){

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : "+ endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        Log.println(Log.ASSERT, "Difference",Long.toString(elapsedDays));
    } */


}
