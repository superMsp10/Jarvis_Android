package jarvis.mahan.jarvisandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends AppCompatActivity {

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

        setTitle("Jarvis");

        int displayHeight = getWindowManager().getDefaultDisplay().getHeight();


        Button button = (Button) findViewById(R.id.CafeMenuButton);
        button.getLayoutParams().height = displayHeight / 6;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeToCafeMenu();
            }
        });
        buttonEffect(button);

        button = (Button) findViewById(R.id.tohubButton);
        button.getLayoutParams().height = displayHeight / 6;
        buttonEffect(button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeToHub();
            }
        });

        button = (Button) findViewById(R.id.NewsFeed);
        button.getLayoutParams().height = displayHeight / 6;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeToCafeMenu();
            }
        });
        buttonEffect(button);

        button = (Button) findViewById(R.id.calenderButton);
        button.getLayoutParams().height = displayHeight / 6;
        buttonEffect(button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeToCalender();
            }
        });

        TextView text = (TextView) findViewById(R.id.welcomLabel);
        text.getLayoutParams().height = (displayHeight / 6) / 3;
        text = (TextView) findViewById(R.id.dateLabel);
        text.getLayoutParams().height = (displayHeight / 6) / 3;
        text = (TextView) findViewById(R.id.dayAndPeriodLabel);
        text.getLayoutParams().height = (displayHeight / 6) / 3;

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

            DayCalculator calc = new DayCalculator(data, new SimpleDateFormat("d LLLL yyyy hh mm ss").parse("10 september 2015 00 00 00"));
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    private void changeToHub() {

        Intent intent;
        intent = new Intent(this, Hub.class);
        startActivity(intent);

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

    private void changeToCafeMenu() {

        Intent intent;
        intent = new Intent(this, CafeMenu.class);
        startActivity(intent);

    }

    private void changeToCalender() {

        Intent intent;
        intent = new Intent(this, Calender.class);
        startActivity(intent);

    }


}
