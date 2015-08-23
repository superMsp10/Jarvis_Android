package jarvis.mahan.jarvisandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class DetailedNews extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_news);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.primary));
        }
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("Capsuula.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
        setTitle("News");


        Intent intent = getIntent();
        String message = intent.getStringExtra("News");
        String title = null;
        String des = null;
        try {
            JSONObject news = new JSONObject(message);
            title = news.getString("Title");
            des = news.getString("Discription");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        int displayHeight;
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {

            displayHeight = getWindowManager().getDefaultDisplay().getHeight();
        } else {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            displayHeight = size.y;

        }
        TextView t = (TextView) findViewById(R.id.newsTitle);
        t.getLayoutParams().height = displayHeight / 6;
        t.setText(title);
        t.getBackground().setColorFilter(Color.parseColor("#80000000"), PorterDuff.Mode.SRC_ATOP);

        t = (TextView) findViewById(R.id.news);
        t.setText(des);


    }

}
