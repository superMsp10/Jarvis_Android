package jarvis.mahan.jarvisandroid;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    String dayDescription;
    String monthName = null;
    JSONArray serverData;
    JSONArray noSchoolDays;
    SimpleDateFormat dateFormat;


    public DayCalculator(JSONArray data, Date date) throws ParseException {
        serverData = data;
        today = date;
        Log.println(Log.ASSERT, "Today", today.toString());

        init();


    }

    void init() throws ParseException {
        staticSeptemberDayOffset = 1;
        dayOffset = staticSeptemberDayOffset;
        offset = 1;
        dateFormat = new SimpleDateFormat("d LLLL yyyy");
        sept = dateFormat.parse("1 September 2015");
        dateFormat = new SimpleDateFormat("LLLL");
        for (int i = 0; i < serverData.length(); i++) {
            JSONObject j = null;

            try {
                j = serverData.getJSONObject(i);
                monthName = j.getString("Month");
                noSchoolDays = j.getJSONArray("NoSchoolDates");

            } catch (JSONException e) {
                e.printStackTrace();
            }


            if (monthName != null && monthName.equals(dateFormat.format(today)))
                break;

            dayOffset += noSchoolDays.length();

        }


        //  int days = daysSinceStart(today);
        // Log.println(Log.ASSERT, "Days", Integer.toString(days));
        //Log.println(Log.ASSERT, "Number of Sat and sun", Double.toString(numberOfSatAndSun(days + offset)));
        try {
            Log.println(Log.ASSERT, "# of No school days", String.valueOf(numberOfNoSchoolDatesBeforeToday()));
        } catch (JSONException e) {
            e.printStackTrace();
        }


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

    String getDayDiscription() {

        return dayDescription;
    }


    int numberOfNoSchoolDatesBeforeToday() throws JSONException {

        int NSDays = 0;
        dateFormat = new SimpleDateFormat("d");
        int todayInt = Integer.parseInt(dateFormat.format(today));


        for (int i = 0; i < noSchoolDays.length(); i++) {

            JSONObject j = noSchoolDays.getJSONObject(i);
            int date = j.getInt("Date");

            if (date == todayInt) {
                String reason = j.getString("Reason");
                dayDescription = ("No School " + reason);

                return -1;

            } else if (date < todayInt) {

                NSDays++;
            }

        }

        return NSDays;

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
