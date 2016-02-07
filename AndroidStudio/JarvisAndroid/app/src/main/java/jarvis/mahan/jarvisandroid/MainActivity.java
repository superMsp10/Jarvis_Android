package jarvis.mahan.jarvisandroid;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends AppCompatActivity {
    private Date d;
    private SimpleDateFormat dateFormat;
    private int mInterval = 60000;
    private Handler mHandler;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.primary));
        }
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("Capsuula.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        dateFormat = new SimpleDateFormat("d LLLL yyyy hh mm ss");
        try {
            d = dateFormat.parse("9 september 2015 10 11 00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // d = new Date();
        normalUI();
        Calender.d = d;
        getCalcData();
        mHandler = new Handler();
       // mStatusChecker.run();


    }


    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            d = new Date();
            float sec = dateInSeconds(d);
            if (sec == 0 || sec == 60) {
                mInterval = 60000;
            } else {
                mInterval = (int) (60000 - (sec * 1000));


            }
            Calender.d = d;
            getCalcData();

//            Calendar cal = Calendar.getInstance();
//            cal.setTime(d);
//            cal.add(Calendar.MINUTE, 45);  // number of days to add
//            d = cal.getTime();
//            Calender.d = d;
//             Log.println(Log.ASSERT, "Updated Date", d.toString());
//            getCalcData(); //this function can change value of mInterval.
            mHandler.postDelayed(mStatusChecker, mInterval);


        }
    };


    void normalUI() {
        int displayHeight;
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {

            displayHeight = getWindowManager().getDefaultDisplay().getHeight();
        } else {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            displayHeight = size.y;

        }
        int buttonHeight = displayHeight / 6;


        Button button = (Button) findViewById(R.id.CafeMenuButton);
        button.getLayoutParams().height = buttonHeight;
        button.invalidate();
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeToCafeMenu();
            }
        });
        buttonEffect(button);

        button = (Button) findViewById(R.id.tohubButton);
        button.getLayoutParams().height = buttonHeight;
        button.invalidate();
        buttonEffect(button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeToHub();
            }
        });

        button = (Button) findViewById(R.id.NewsFeed);
        button.getLayoutParams().height = buttonHeight;
        button.invalidate();
        buttonEffect(button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeToNewsFeed();
            }
        });

        button = (Button) findViewById(R.id.calenderButton);
        button.getLayoutParams().height = buttonHeight;
        button.invalidate();
        buttonEffect(button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeToCalender();
            }
        });

        ImageView img = (ImageView)findViewById(R.id.loading_Main);
//        img.setBackgroundResource(R.drawable.spin_animation);
        img.setVisibility(View.VISIBLE);
        // Get the background, which has been compiled to an AnimationDrawable object.
        AnimationDrawable frameAnimation = (AnimationDrawable) img.getDrawable();

        // Start the animation (looped playback by default).
        frameAnimation.start();


    }

    void stopLoadingAnimaton(){
        ImageView img = (ImageView)findViewById(R.id.loading_Main);
//        img.setBackgroundResource(R.drawable.spin_animation);
        img.setVisibility(View.GONE);
        // Get the background, which has been compiled to an AnimationDrawable object.
        AnimationDrawable frameAnimation = (AnimationDrawable) img.getDrawable();

        // Start the animation (looped playback by default).
        frameAnimation.stop();

    }

    void setupUI(String dayDescription, String schedule) {

        int displayHeight;
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {

            displayHeight = getWindowManager().getDefaultDisplay().getHeight();
        } else {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            displayHeight = size.y;

        }
        int buttonHeight = displayHeight / 6;

        TextView text = (TextView) findViewById(R.id.welcomLabel);
        text.getLayoutParams().height = buttonHeight / 3;
        text = (TextView) findViewById(R.id.dateLabel);
        text.getLayoutParams().height = buttonHeight / 3;
        dateFormat = new SimpleDateFormat(" MMM dd, yyyy");
        String date = dateFormat.format(d);
        dateFormat = new SimpleDateFormat("EEEE");
        String day = dateFormat.format(d);

        text.setText(day + date);

        text = (TextView) findViewById(R.id.dayAndPeriodLabel);
        text.getLayoutParams().height = buttonHeight / 3;
        if (!dayDescription.contains("No School")) {
            text.setText(dayDescription + ", " + schedule);
        } else text.setText(dayDescription);
        stopLoadingAnimaton();

    }


    JSONArray sortJsonArray(JSONArray start) throws JSONException {
        Integer[] arr;
        arr = new Integer[start.length()];
        for (int i = 0; i < start.length(); i++) {
            arr[i] = (start.getJSONObject(i).getInt("MonthOrder"));
        }

        Arrays.sort(arr, new Comparator<Integer>() {
            @Override
            public int compare(Integer x, Integer y) {
                return x - y;
            }
        });


        JSONArray rs = new JSONArray();

        for (int i = 0; i < start.length(); i++) {
            //For each of the json objects in the res array

            int index = Arrays.asList(arr).indexOf(start.getJSONObject(i).getInt("MonthOrder"));
            //see where its epoch is in the integer array

            rs.put(index, start.getJSONObject(i));
            //assign the whole json object to that index
        }

        return rs;

    }

    float dateInSeconds(Date date) {

        Calendar c = Calendar.getInstance();
        c.setTime(date);




        return c.get(Calendar.SECOND);
    }


    void getCalcData() {


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://jarvisapp-supermsp10.rhcloud.com/Date";


        // Request a string response from the provided URL.

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    JSONArray res;

                    @Override
                    public void onResponse(String response) {


                        try {
                            res = sortJsonArray(new JSONArray(response));
                            useData(res);
                            //  Log.println(Log.ASSERT, "Response from server", res.toString());
                            writeToFile(res.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.println(Log.ASSERT, "Failed database json", "Calendar");

                        }

                    }

                    private void writeToFile(String data) {
                        try {
                            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("Calendar.txt", Context.MODE_PRIVATE));
                            outputStreamWriter.write(data);
                            outputStreamWriter.close();
                        } catch (IOException e) {
                            Log.e("Exception", "File write failed: " + e.toString());
                        }
                    }


                }, new Response.ErrorListener() {
            JSONArray res;

            @Override
            public void onErrorResponse(VolleyError error) {

                try {
                    res = new JSONArray(readFromFile());
                    if (res.length() > 0)
                        useData(res);
                    //Log.println(Log.ASSERT, "Data from file", res.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.println(Log.ASSERT, "Failed json from file", "failed");

                }
            }


            private String readFromFile() {

                String ret = "[{\"_id\":\"558ebe48e4b0b1da5636c1a9\",\"Month\":\"September\",\"MonthOrder\":1,\"NoSchoolDates\":[{\"Date\":1,\"Reason\":\"Summer Break\"},{\"Date\":2,\"Reason\":\"Summer Break\"},{\"Date\":3,\"Reason\":\"P.A. Day\"},{\"Date\":4,\"Reason\":\"Summer Break\"},{\"Date\":7,\"Reason\":\"Labour Day\"}]},{\"_id\":\"558f11c7e4b0b1da5636c6cd\",\"Month\":\"October\",\"MonthOrder\":2,\"NoSchoolDates\":[{\"Date\":12,\"Reason\":\"Thanks Giving\"}]},{\"_id\":\"558ef073e4b0b1da5636c4f9\",\"Month\":\"November\",\"MonthOrder\":3,\"NoSchoolDates\":[{\"Date\":13,\"Reason\":\"P.A. Day\"}]},{\"_id\":\"558ef91ee4b0b1da5636c5c1\",\"Month\":\"December\",\"MonthOrder\":4,\"NoSchoolDates\":[{\"Date\":21,\"Reason\":\"Christmas Break\"},{\"Date\":22,\"Reason\":\"Christmas Break\"},{\"Date\":23,\"Reason\":\"Christmas Break\"},{\"Date\":24,\"Reason\":\"Christmas Break\"},{\"Date\":25,\"Reason\":\"Christmas Break\"},{\"Date\":28,\"Reason\":\"Christmas Break\"},{\"Date\":29,\"Reason\":\"Christmas Break\"},{\"Date\":30,\"Reason\":\"Christmas Break\"},{\"Date\":31,\"Reason\":\"Christmas Break\"}]},{\"_id\":\"558ef9fce4b0b1da5636c5c9\",\"Month\":\"January\",\"MonthOrder\":5,\"NoSchoolDates\":[{\"Date\":1,\"Reason\":\"New Years Day\"}]},{\"_id\":\"558efa72e4b0b1da5636c5d2\",\"Month\":\"February\",\"MonthOrder\":6,\"NoSchoolDates\":[{\"Date\":12,\"Reason\":\"P.A. Day\"},{\"Date\":15,\"Reason\":\"Family Day\"}]},{\"_id\":\"558efdefe4b0b1da5636c5fc\",\"Month\":\"March\",\"MonthOrder\":7,\"NoSchoolDates\":[{\"Date\":14,\"Reason\":\"March Break\"},{\"Date\":15,\"Reason\":\"March Break\"},{\"Date\":16,\"Reason\":\"March Break\"},{\"Date\":17,\"Reason\":\"March Break\"},{\"Date\":18,\"Reason\":\"March Break\"},{\"Date\":25,\"Reason\":\"Good Friday\"},{\"Date\":28,\"Reason\":\"Easter Monday\"},{\"Date\":31,\"Reason\":\" For Some Students OSSLT, See News Feed\"}]},{\"_id\":\"558efe43e4b0b1da5636c602\",\"Month\":\"April\",\"MonthOrder\":8,\"NoSchoolDates\":[]},{\"_id\":\"558efe81e4b0b1da5636c606\",\"Month\":\"May\",\"MonthOrder\":9,\"NoSchoolDates\":[{\"Date\":23,\"Reason\":\"Victoria Day\"}]},{\"_id\":\"558efecfe4b0b1da5636c609\",\"Month\":\"June\",\"MonthOrder\":10,\"NoSchoolDates\":[{\"Date\":28,\"Reason\":\"P.A. Day\"},{\"Date\":13,\"Reason\":\"Exams\"},{\"Date\":14,\"Reason\":\"Exams\"},{\"Date\":15,\"Reason\":\"Exams\"},{\"Date\":16,\"Reason\":\"Exams\"},{\"Date\":17,\"Reason\":\"Exams\"},{\"Date\":20,\"Reason\":\"Exams\"},{\"Date\":21,\"Reason\":\"Exams\"},{\"Date\":22,\"Reason\":\"Exams\"},{\"Date\":23,\"Reason\":\"Exams\"},{\"Date\":24,\"Reason\":\"Exam Consultation Day\"},{\"Date\":27,\"Reason\":\"Exam Consultation Day\"},{\"Date\":30,\"Reason\":\"P.A. Day\"},{\"Date\":29,\"Reason\":\"Family Day\"}]},{\"_id\":\"558f1092e4b0b1da5636c6c6\",\"Month\":\"July\",\"MonthOrder\":11,\"NoSchoolDates\":[]},{\"_id\":\"558f10d0e4b0b1da5636c6c8\",\"Month\":\"August\",\"MonthOrder\":12,\"NoSchoolDates\":[{\"Date\":22,\"Reason\":\"Special Schedule\"}]}]";

                try {
                    InputStream inputStream = openFileInput("Calendar.txt");

                    if (inputStream != null) {
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        String receiveString;
                        StringBuilder stringBuilder = new StringBuilder();

                        while ((receiveString = bufferedReader.readLine()) != null) {
                            stringBuilder.append(receiveString);
                        }

                        inputStream.close();
                        ret = stringBuilder.toString();
                    }
                } catch (FileNotFoundException e) {
                    Log.e("login activity", "File not found: " + e.toString());
                } catch (IOException e) {
                    Log.e("login activity", "Can not read file: " + e.toString());
                }

                return ret;
            }
        }


        );
        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    void useData(JSONArray data) {
        try {
            DayCalculator calc = new DayCalculator(data, d);
            calc.init();
            JSONObject j = new JSONObject();
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
            try {
                int day = calc.checkDay();
                if (day != -1)
                    schedule = calc.getPeriod(day, j);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            setupUI(calc.getDayDescription(), schedule);


        } catch (ParseException e) {
            e.printStackTrace();
        }


    }


    private static void buttonEffect(View button) {
        button.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(Color.parseColor("#AA000000"), PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
    }

    private void changeToHub() {

        Intent intent;
        intent = new Intent(this, Hub.class);
        mHandler.removeCallbacks(mStatusChecker);

        startActivity(intent);

    }

    private void changeToCafeMenu() {

        Intent intent;
        intent = new Intent(this, CafeMenu.class);
        mHandler.removeCallbacks(mStatusChecker);

        startActivity(intent);

    }

    private void changeToNewsFeed() {

        Intent intent;
        intent = new Intent(this, NewsFeed.class);
        mHandler.removeCallbacks(mStatusChecker);

        startActivity(intent);

    }

    private void changeToCalender() {

        Intent intent;
        intent = new Intent(this, Calender.class);
        dateFormat = new SimpleDateFormat("d LLLL yyyy hh mm ss");
        String message = dateFormat.format(d);
        intent.putExtra("Date", message);
        intent.putExtra("Activity", "main");
        mHandler.removeCallbacks(mStatusChecker);

        startActivity(intent);


    }

    public void onDestroy() {

        super.onDestroy();

        mHandler.removeCallbacks(mStatusChecker);
    }

    public void onPause() {

        super.onPause();

        mHandler.removeCallbacks(mStatusChecker);
    }

    public void onStop() {

        super.onStop();

        mHandler.removeCallbacks(mStatusChecker);
    }


}
