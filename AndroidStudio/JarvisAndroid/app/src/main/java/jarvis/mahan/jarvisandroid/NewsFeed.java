package jarvis.mahan.jarvisandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Comparator;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class NewsFeed extends AppCompatActivity {
    private JSONArray news;
    public boolean notConnected = false;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.primary));
        }
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("Capsuula.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
        setTitle("News Feed");
        ImageView img = (ImageView)findViewById(R.id.Loading_News);
//        img.setBackgroundResource(R.drawable.spin_animation);
        img.setVisibility(View.VISIBLE);
        // Get the background, which has been compiled to an AnimationDrawable object.
        AnimationDrawable frameAnimation = (AnimationDrawable) img.getDrawable();

        // Start the animation (looped playback by default).
        frameAnimation.start();
        getData();
    }
    void stopLoadingAnimaton(){
        ImageView img = (ImageView)findViewById(R.id.Loading_News);
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
            arr[i] = (start.getJSONObject(i).getInt("Epoch"));
        }

        Arrays.sort(arr, new Comparator<Integer>() {
            @Override
            public int compare(Integer x, Integer y) {
                return y - x;
            }
        });


        JSONArray rs = new JSONArray();

        for (int i = 0; i < start.length(); i++) {
            //For each of the json objects in the res array

            int index = Arrays.asList(arr).indexOf(start.getJSONObject(i).getInt("Epoch"));
            //see where its epoch is in the integer array

            rs.put(index, start.getJSONObject(i));
            //assign the whole json object to that index
        }

        return rs;

    }


    void getData() {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://jarvisapp-supermsp10.rhcloud.com/NewsFeed";


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
                            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("NewsFeed.txt", Context.MODE_PRIVATE));
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
                notConnected = true;

                String ret = "";
                try {
                    InputStream inputStream = openFileInput("NewsFeed.txt");

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

    void useData(JSONArray data) throws JSONException {
        news = new JSONArray(data.toString());


        int displayHeight;
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {

            displayHeight = getWindowManager().getDefaultDisplay().getHeight();
        } else {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            displayHeight = size.y;

        }
        int cellHeight = displayHeight / 6;
        LinearLayout s = (LinearLayout) findViewById(R.id.NewsFeedLinearlayout);

        LayoutInflater inflater = LayoutInflater.from(this);
        int start = 1;
        if (notConnected) {
            noDataUI();
            start = 2;
        }

        for (int i = 0; i < data.length(); i++) {
            String name = data.getJSONObject(i).getString("Title");
            inflater.inflate(R.layout.cafemenucell, s);

            TextView t = (TextView) s.getChildAt(start + i);
            t.setId(i);
            t.getLayoutParams().height = cellHeight;
            t.getBackground().clearColorFilter();
            t.requestLayout();
            t.setBackgroundColor(getResources().getColor(R.color.secondary));

            t.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    if (event.getAction() == MotionEvent.ACTION_UP) {

                        v.getBackground().setColorFilter(Color.parseColor("#AA000000"), PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        try {
                            changeActivity(v.getId());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {

                        v.getBackground().clearColorFilter();
                        v.invalidate();

                    }


                    return true;
                }
            });

            t.setText(name);
        }

//        Log.println(Log.ASSERT, "Data Length", String.valueOf(data.length()));
    stopLoadingAnimaton();

    }

    void noDataUI() {
//        Log.println(Log.ASSERT, "noDataUI", "Start");
        int displayHeight;
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {

            displayHeight = getWindowManager().getDefaultDisplay().getHeight();
        } else {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            displayHeight = size.y;

        }
        int cellHeight = displayHeight / 6;
        LinearLayout s = (LinearLayout) findViewById(R.id.NewsFeedLinearlayout);

        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.cafemenucell, s);

        TextView t = (TextView) s.getChildAt(0);
        t.setId(0);
        t.getLayoutParams().height = cellHeight;
        t.getBackground().clearColorFilter();
        t.requestLayout();
        t.setBackgroundColor(Color.rgb(102, 204, 0));
        t.setText("Please connect to the internet for updates");


    }

    void changeActivity(int index) throws JSONException {

        Intent intent;
        intent = new Intent(this, DetailedNews.class);
        String message = news.getString(index);
        intent.putExtra("News", message);

        startActivity(intent);

    }


}
