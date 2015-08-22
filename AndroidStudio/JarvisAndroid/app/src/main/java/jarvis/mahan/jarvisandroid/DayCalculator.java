package jarvis.mahan.jarvisandroid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


class DayCalculator {
    public Date today;
    private Date sept;
    private int dayOffset;
    private int offset;
    private int staticSeptemberDayOffset;
    private String dayDescription;
    private String monthName = null;
    private JSONArray serverData;
    private JSONArray noSchoolDays;
    private SimpleDateFormat dateFormat;


    public DayCalculator(JSONArray data, Date date) throws ParseException {
        serverData = data;
        today = date;

        init();


    }

    void init() throws ParseException {
        staticSeptemberDayOffset = 1;
        dayOffset = staticSeptemberDayOffset;
        offset = 1;
        dateFormat = new SimpleDateFormat("d LLLL yyyy");
        sept = dateFormat.parse("1 September 2015");
        dateFormat = new SimpleDateFormat("LLLL");
        JSONObject j;
        for (int i = 0; i < serverData.length(); i++) {
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


    }


    int daysSinceStart(Date toDate) {

        long different = toDate.getTime() - sept.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long elapsedDays = different / daysInMilli;

        return (int) elapsedDays;
    }

    int numberOfSatAndSun(int numberOfDays) {
        //  Log.println(Log.ASSERT, "Number of Weeks", Double.toString(numberOfDays / 7));
        // Log.println(Log.ASSERT, "Number of Weeks rounded", Double.toString(Math.floor(numberOfDays / 7)));
        return (int) Math.floor(numberOfDays / 7) * 2;
    }

    String getDayDescription() {


        dateFormat = new SimpleDateFormat("LLLL");
        String td = dateFormat.format(today);

        //Log.println(Log.ASSERT, "Today MONTH", td);

        if (td.equals("August") || td.equals("July")) {
            return "No School, Enjoy Summer Break!";

        }

        dateFormat = new SimpleDateFormat("EEEE");
        td = dateFormat.format(today);

        // Log.println(Log.ASSERT, "Today DAY", td);

        if (td.equals("Sunday") || td.equals("Saturday")) {
            return "No School, Weekend";

        } else {
            int day = 0;
            try {
                day = checkDay();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Log.println(Log.ASSERT, "Today SCHDAY", String.valueOf(day));


            if (day != -1) {
                return "Day " + day;
            } else {

                return dayDescription;

            }
        }
    }


    public int checkDay() throws JSONException {

        int numberOfDays = daysSinceStart(today);
        int NSchDays = numberOfNoSchoolDatesBeforeToday();

        if (NSchDays != -1) {

            int sS = numberOfSatAndSun(numberOfDays + offset);
            int numberOfSchDays = numberOfDays - (NSchDays + sS + dayOffset);


            int day = (numberOfSchDays + offset) % 4;
            dateFormat = new SimpleDateFormat("EEEE");
            if (day == 0) {
                day = 4;
            }
            String td = dateFormat.format(today);

            if (td.equals("Sunday") || td.equals("Saturday")) {
                return -1;
            }

            return day;

        }

        return -1;


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
                if (reason.contains("Special Schedule")) {
                    dayDescription = "Special Schedule, check News Feed for details";
                } else
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
