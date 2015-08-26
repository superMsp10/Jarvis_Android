package jarvis.mahan.jarvisandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class Schedule extends AppCompatActivity {
    String[] startTimes;
    String[] endTimes;
    String[] dayOne;
    String[] dayTwo;
    String[] dayThree;
    String[] dayFour;
    JSONObject periods;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.primary));
        }
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("Capsuula.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

        Intent intent = getIntent();
        String message = intent.getStringExtra("Date");
        try {
            JSONObject j = new JSONObject(message);
            setTitle("Schedule: Day " + j.getString("day"));
            message = j.getString("dayDis");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (message.contains("Wednesday")) {
            startTimes = new String[]{"10:10 AM", "11:10 AM", "12:05 AM", "1:05 PM", "2:05 PM"};
            endTimes = new String[]{"11:05 AM", "12:05 AM", "1:00 PM", "2:00 PM", "3:00 PM"};
        } else {

            startTimes = new String[]{"8:50 AM", "10:10 AM", "11:25 AM", "12:25 PM", "1:45 PM"};
            endTimes = new String[]{"10:05 AM", "11:25 AM", "12:25 PM", "1:40 PM", "3:00 PM"};
        }

        periods = getData();

        try {

            dayOne = new String[]{
                    periods.getString("perA"),
                    periods.getString("perB"),
                    periods.getString("lunch"),
                    periods.getString("perC"),
                    periods.getString("perD")};

            dayTwo = new String[]{
                    periods.getString("perE"),
                    periods.getString("perF"),
                    periods.getString("lunch"),
                    periods.getString("perG"),
                    periods.getString("perH")};

            dayThree = new String[]{
                    periods.getString("perB"),
                    periods.getString("perA"),
                    periods.getString("lunch"),
                    periods.getString("perD"),
                    periods.getString("perC")};

            dayFour = new String[]{
                    periods.getString("perF"),
                    periods.getString("perE"),
                    periods.getString("lunch"),
                    periods.getString("perH"),
                    periods.getString("perG")};


            // Log.println(Log.ASSERT, "day", dayOne.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    JSONObject getData() {
        JSONObject j = null;
        String schedule = null;

        try {
            InputStream inputStream = openFileInput("Schedule.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString;
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                try {
                    j = new JSONObject(stringBuilder.toString());
                    // Log.println(Log.ASSERT, "day", j.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {

            try {

                j.put("lunch", "Lunch");
                j.put("perA", "Period A");
                j.put("perB", "Period B");
                j.put("perC", "Period C");
                j.put("perD", "Period D");
                j.put("perE", "Period E");
                j.put("perF", "Period F");
                j.put("perG", "Period G");
                j.put("perH", "Period H");

            } catch (JSONException e1) {
                e1.printStackTrace();
            }


            Log.e("login activity", "File not found: " + e.toString());
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("Schedule.txt", Context.MODE_PRIVATE));
                outputStreamWriter.write(j.toString());
                outputStreamWriter.close();
            } catch (IOException i) {
                Log.e("Exception", "File write failed: " + e.toString());
            }
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }


        return j;
    }

    void setupUI() {
        int displayHeight;
        int screenWidth;
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {

            displayHeight = getWindowManager().getDefaultDisplay().getHeight();
            screenWidth = getWindowManager().getDefaultDisplay().getWidth();

        } else {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            displayHeight = size.y;
            screenWidth = size.x;

        }


        int cellHeight;
        int labelWidth;
        int labelHeight;

        cellHeight = displayHeight / 6;

        labelWidth = (int) (screenWidth / 2.9);
        labelHeight = cellHeight / 2;

        View txt = findViewById(R.id.StartTimeText);
        txt.getLayoutParams().height = labelHeight;
        txt.getLayoutParams().width = labelWidth;

        txt.requestLayout();

        txt = findViewById(R.id.EventText);
        txt.getLayoutParams().height = labelHeight;
        txt.getLayoutParams().width = labelWidth;

        txt.requestLayout();

        txt = findViewById(R.id.EndTimeText);
        txt.getLayoutParams().height = labelHeight;
        txt.getLayoutParams().width = labelWidth;
        txt.requestLayout();

        txt = findViewById(R.id.LabelLayout);
        txt.getBackground().setColorFilter(Color.parseColor("#55000000"), PorterDuff.Mode.SRC_ATOP);

        LinearLayout s = (LinearLayout) findViewById(R.id.ScheduleLayout);

        LayoutInflater inflater = LayoutInflater.from(this);
        TextView t = null;
        for (int i = 0; i < 5; i++) {
            inflater.inflate(R.layout.schedule_cell, s);

            t = (TextView) findViewById(R.id.Event);
            t.setId(i);
            t.getLayoutParams().height = cellHeight;
            t.getLayoutParams().height = labelWidth;
            t.requestLayout();

            t = (TextView) findViewById(R.id.StartTime);
            t.setId(i);
            t.getLayoutParams().height = cellHeight;
            t.getLayoutParams().height = labelWidth;
            t.requestLayout();

            t = (TextView) findViewById(R.id.EndTime);
            t.setId(i);
            t.getLayoutParams().height = cellHeight;
            t.getLayoutParams().height = labelWidth;
            t.requestLayout();


        }


    }


}
