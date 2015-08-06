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
import org.json.JSONObject;

import java.sql.Array;

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
        Button cafe = (Button)findViewById(R.id.CafeMenuButton);
        cafe.getLayoutParams().height = displayHeight / 6;

        Button hub = (Button)findViewById(R.id.tohubButton);
        hub.getLayoutParams().height = displayHeight / 6;

        Button news = (Button)findViewById(R.id.NewsFeed);
        news.getLayoutParams().height = displayHeight / 6;

        Button calendar = (Button)findViewById(R.id.calenderButton);
        calendar.getLayoutParams().height = displayHeight / 6;

        TextView text = (TextView)findViewById(R.id.welcomLabel);
        text.getLayoutParams().height = (displayHeight / 6)/3;
        text = (TextView)findViewById(R.id.dateLabel);
        text.getLayoutParams().height = (displayHeight / 6)/3;
        text = (TextView)findViewById(R.id.dayAndPeriodLabel);
        text.getLayoutParams().height = (displayHeight / 6)/3;

        final TextView mTextView = text;


    // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://jarvisapp-supermsp10.rhcloud.com/CafeMenu";


    // Request a string response from the provided URL.

        StringRequest  stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray res;


                        try {
                             res = new JSONArray(response);
                            JSONObject j = res.getJSONObject(0);
                            Log.println(Log.ASSERT,"Response from server",j.getJSONArray("FoodItems").getString(0));

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.println(Log.ASSERT,"Failed json","failed");

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("That didn't work!");
            }
        });
    // Add the request to the RequestQueue.
        queue.add(stringRequest);



        final Button hubButton = (Button) findViewById(R.id.tohubButton);
        buttonEffect(hubButton);
        hubButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               changeToHub();
            }
        });

        final Button calenderButton = (Button) findViewById(R.id.calenderButton);
        buttonEffect(calenderButton);
        calenderButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeToCalender();
            }
        });

        final Button cafeButton = (Button) findViewById(R.id.CafeMenuButton);
        cafeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeToCafeMenu();
            }
        });
        buttonEffect(cafeButton);

        final Button newsButton = (Button) findViewById(R.id.NewsFeed);
        newsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeToCafeMenu();
            }
        });
        buttonEffect(newsButton);



    }

    private void changeToHub() {

        Intent intent;
        intent =  new Intent(this,Hub.class);
        startActivity(intent);

    }


    public static void buttonEffect(View button){
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
        intent =  new Intent(this,CafeMenu.class);
        startActivity(intent);

    }

    private void changeToCalender() {

        Intent intent;
        intent =  new Intent(this,Calender.class);
        startActivity(intent);

    }






}
