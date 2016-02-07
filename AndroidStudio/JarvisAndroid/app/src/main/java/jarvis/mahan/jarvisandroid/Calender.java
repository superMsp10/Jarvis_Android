package jarvis.mahan.jarvisandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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


public class Calender extends AppCompatActivity {
    public static Date d;
    private SimpleDateFormat dateFormat;
    private JSONArray stringsOfDays;
    private int cellOffset;
    private int nOfDays;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.primary));
        }
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("Capsuula.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
        setTitle("Calendar");
        nOfDays = 30;
        cellOffset = 15;

      /*  Intent intent = getIntent();
        String message = intent.getStringExtra("Date");

        if (message != null) {

            dateFormat = new SimpleDateFormat("d LLLL yyyy hh mm ss");


            try {
                d = dateFormat.parse(message);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }*/
        ImageView img = (ImageView)findViewById(R.id.loading_Calendar);
//        img.setBackgroundResource(R.drawable.spin_animation);
        img.setVisibility(View.VISIBLE);
        // Get the background, which has been compiled to an AnimationDrawable object.
        AnimationDrawable frameAnimation = (AnimationDrawable) img.getDrawable();

        // Start the animation (looped playback by default).
        frameAnimation.start();

        getCalcData();


    }

    void stopLoadingAnimaton(){
        ImageView img = (ImageView)findViewById(R.id.loading_Calendar);
//        img.setBackgroundResource(R.drawable.spin_animation);
        img.setVisibility(View.GONE);
        // Get the background, which has been compiled to an AnimationDrawable object.
        AnimationDrawable frameAnimation = (AnimationDrawable) img.getDrawable();

        // Start the animation (looped playback by default).
        frameAnimation.stop();

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
                            // Log.println(Log.ASSERT, "Response from server", res.toString());
                            writeToFile(res.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.println(Log.ASSERT, "Failed database json", "failed");

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
            try {
                stringsOfDays = GetDayDiscriptions(d, nOfDays, calc, true);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

        stopLoadingAnimaton();
    }


    private JSONArray GetDayDiscriptions(final Date startDate, final int numOfDays, final DayCalculator c, boolean scrool) throws JSONException, ParseException {

        int displayHeight = getWindowManager().getDefaultDisplay().getHeight();
        int cellHeight = displayHeight / 6;
        final LinearLayout s = (LinearLayout) findViewById(R.id.CalendarLinearlayout);
        LayoutInflater inflater = LayoutInflater.from(this);
        inflater.inflate(R.layout.cafemenucell, s);
        TextView t = (TextView) s.getChildAt(1);
        t.getLayoutParams().height = cellHeight;
        t.requestLayout();
        t.setBackgroundColor(Color.rgb(102, 204, 0));
        t.setText("View more dates");
        t.getBackground().clearColorFilter();
        final ScrollView scroll = (ScrollView) findViewById(R.id.CalendarScrollView);

        t.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    v.getBackground().setColorFilter(Color.parseColor("#AA000000"), PorterDuff.Mode.SRC_ATOP);
                    v.invalidate();


                } else if (event.getAction() == MotionEvent.ACTION_UP) {

                    v.getBackground().clearColorFilter();
                    v.invalidate();
                    if (numOfDays < 365) {
                        try {
                            s.removeAllViews();
                            //  Log.println(Log.ASSERT, "Number of cells ar", String.valueOf(s.getChildCount()));

                            stringsOfDays = GetDayDiscriptions(startDate, numOfDays * 2, c, false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    v.getBackground().clearColorFilter();
                    v.invalidate();
                }


                return true;
            }
        });

        t.setId(0);


        Date now = d;
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        JSONArray b = new JSONArray();
        dateFormat = new SimpleDateFormat("EEEE MMM dd, ");
        cal.add(Calendar.DATE, numOfDays);
        // Log.println(Log.ASSERT, "Number of days", String.valueOf(numOfDays));

        for (int i = 0; i < numOfDays + cellOffset; i++) {
            JSONObject a = new JSONObject();
            cal.add(Calendar.DATE, -1);  // number of days to add
            now = cal.getTime();  // dt is now the new date

            c.today = now;

            c.init();


            String dateDay = dateFormat.format(now);


            String f = dateDay + c.getDayDescription();
            inflater.inflate(R.layout.cafemenucell, s);

            TextView txt = (TextView) s.getChildAt(i + 2);
            txt.setId(i + 1);
            txt.getLayoutParams().height = cellHeight;
            txt.getBackground().clearColorFilter();

            if (f.contains("No School")) {
                txt.setBackgroundColor(getResources().getColor(R.color.primary));
            } else {
                txt.setBackgroundColor(getResources().getColor(R.color.secondary));
                txt.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        if (event.getAction() == MotionEvent.ACTION_DOWN) {

                            v.getBackground().setColorFilter(Color.parseColor("#AA000000"), PorterDuff.Mode.SRC_ATOP);
                            v.invalidate();


                        } else if (event.getAction() == MotionEvent.ACTION_UP) {

                            changeToSchedule(v.getId());

                        } else {

                            v.getBackground().clearColorFilter();
                            v.invalidate();

                        }


                        return true;
                    }
                });
            }
            if (scrool) {
                if (f.contains(dateFormat.format(d))) {
                    txt.setBackgroundColor(Color.rgb(255, 216, 0));
                    final View today = txt;
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            int vLeft = today.getTop();
                            int vRight = today.getBottom();
                            int sWidth = scroll.getHeight();
                            scroll.scrollTo(0, (vLeft + vRight - sWidth) / 2);
                        }
                    });
                }
            }
            txt.requestLayout();


            txt.setText(f);


            a.put("day", c.checkDay());
            a.put("dayDis", f);


            b.put(a);

        }
        //  Log.println(Log.ASSERT, "Number of cells", String.valueOf(s.getChildCount()));

        return b;

    }

    void changeToSchedule(int i) {

        Intent intent;
        intent = new Intent(this, Schedule.class);

        String message = null;

        try {
            message = stringsOfDays.getJSONObject(i - 1).toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        intent.putExtra("Date", message);
        startActivity(intent);


    }


}
