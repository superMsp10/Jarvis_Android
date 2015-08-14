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

    public DayCalculator(JSONArray data, String date) throws ParseException {
        serverData = data;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM dd yyyy");
        today = dateFormat.parse(date);
        init();
        Log.println(Log.ASSERT, "Sept", sept.toString());
        // dateFormat = new SimpleDateFormat("M dd yyyy E");


    }

    public DayCalculator(JSONArray data, Date date) throws ParseException {
        serverData = data;
        today = date;
        init();


    }

    void init() throws ParseException {
        staticSeptemberDayOffset = 1;
        offset = 1;
        SimpleDateFormat dateFormat = new SimpleDateFormat("d LLLL yyyy");
        sept = dateFormat.parse("1 September 2015");



    }


}
