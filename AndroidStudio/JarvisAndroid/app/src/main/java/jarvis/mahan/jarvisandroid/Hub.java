package jarvis.mahan.jarvisandroid;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class Hub extends AppCompatActivity {


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.primary));
        }
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("Capsuula.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
        setTitle("Social HUB");


        int screenHeight = getWindowManager().getDefaultDisplay().getHeight();
        int screenWidth = getWindowManager().getDefaultDisplay().getHeight();

        int labelHeight = screenHeight / 7;
        int labelPos = (int) (labelHeight / 1.5);


        LinearLayout s = (LinearLayout) findViewById(R.id.hubMain);
        LayoutInflater inflater = LayoutInflater.from(this);
        View space = new View(this);
        Button b;
        TextView v;
        space.setLayoutParams(new LinearLayout.LayoutParams(screenWidth, labelPos));


        //Instagram
        inflater.inflate(R.layout.hub_cell, s);
        b = (Button) findViewById(R.id.hubCellButton);
        b.getLayoutParams().height = labelHeight;
        b.getLayoutParams().width = labelHeight;

        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            b.setBackgroundDrawable(getResources().getDrawable(R.drawable.instagram));
        } else {
            b.setBackground(getResources().getDrawable(R.drawable.instagram));
        }

        b.setId(0);
        b.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.getBackground().clearColorFilter();
                    goToLink("https://instagram.com/jarvisciapp/");

                    v.invalidate();


                } else {
                    v.getBackground().setColorFilter(Color.parseColor("#AA000000"), PorterDuff.Mode.SRC_ATOP);
                    v.invalidate();

                }

                return true;
            }
        });
        v = (TextView) findViewById(R.id.hubCellTextView);
        v.getLayoutParams().height = labelHeight;
        v.setText("Instagram profile of the Jarvis CI App");
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.getBackground().clearColorFilter();
                    goToLink("https://instagram.com/jarvisciapp/");

                    v.invalidate();


                } else {
                    v.getBackground().setColorFilter(Color.parseColor("#AA000000"), PorterDuff.Mode.SRC_ATOP);
                    v.invalidate();

                }

                return true;
            }
        });
        v.invalidate();



        s.addView(space, 1);


    }

    void goToLink(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
