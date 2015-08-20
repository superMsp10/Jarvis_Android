package jarvis.mahan.jarvisandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
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
    private Date d;
    private SimpleDateFormat dateFormat;
    private String[] stringsOfDays;
    int cellOffset;
    int nOfDays;


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

        Intent intent = getIntent();
        String message = intent.getStringExtra("Date");
        dateFormat = new SimpleDateFormat("d LLLL yyyy hh mm ss");

        nOfDays = 30;
        cellOffset = 15;

        try {
            d = dateFormat.parse(message);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //  Log.println(Log.ASSERT, "date", message);


        getCalcData();

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

                String ret = "";

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
                GetDayDiscriptions(d, nOfDays, calc);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }


    }


    private String GetDayDiscriptions(Date startDate, int numOfDays, DayCalculator c) throws JSONException, ParseException {

        int displayHeight = getWindowManager().getDefaultDisplay().getHeight();
        int cellHeight = displayHeight / 6;
        LinearLayout s = (LinearLayout) findViewById(R.id.CalendarLinearlayout);
        LayoutInflater inflater = LayoutInflater.from(this);
        inflater.inflate(R.layout.cafemenucell, s);
        TextView t = (TextView) s.getChildAt(0);
        t.getLayoutParams().height = cellHeight;
        t.requestLayout();
        t.setBackgroundColor(Color.rgb(102, 204, 0));
        t.setText("View more dates");
        t.getBackground().clearColorFilter();
        t.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {

                    v.getBackground().setColorFilter(Color.parseColor("#AA000000"), PorterDuff.Mode.SRC_ATOP);
                    v.invalidate();


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

        for (int i = 0; i < numOfDays; i++) {
            JSONObject a = new JSONObject();
            cal.add(Calendar.DATE, 1);  // number of days to add
            now = cal.getTime();  // dt is now the new date

            c.today = now;

            c.init();



            String dateDay = dateFormat.format(now);


            String f = dateDay + c.getDayDescription();
            inflater.inflate(R.layout.cafemenucell, s);

            TextView txt = (TextView) s.getChildAt(numOfDays - (i + 1));
            txt.setId(i + 1);
            txt.getLayoutParams().height = cellHeight;
            txt.getBackground().clearColorFilter();

            if (f.contains("No School")) {
                txt.setBackgroundColor(getResources().getColor(R.color.primary));
            } else {
                txt.setBackgroundColor(getResources().getColor(R.color.secondary));

            }
            Log.println(Log.ASSERT, "Today", dateFormat.format(d));

            if (f.contains(dateFormat.format(d))) {
                txt.getBackground().setColorFilter(Color.parseColor("#AAFFFFFF"), PorterDuff.Mode.SRC_ATOP);
            }

            txt.requestLayout();

            txt.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    if (event.getAction() == MotionEvent.ACTION_UP) {

                        v.getBackground().setColorFilter(Color.parseColor("#AA000000"), PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();


                    } else {

                        v.getBackground().clearColorFilter();
                        v.invalidate();

                    }


                    return true;
                }
            });

            txt.setText(f);


            a.put("day", c.checkDay());
            a.put("dayDis", f);


            b.put(a);

        }
        return b.toString();

    }


}
