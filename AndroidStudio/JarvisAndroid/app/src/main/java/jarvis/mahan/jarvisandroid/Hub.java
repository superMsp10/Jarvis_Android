package jarvis.mahan.jarvisandroid;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
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
    @SuppressWarnings("deprecation")
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


        int displayHeight;
        int displayWidth = 0;

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {

            displayHeight = getWindowManager().getDefaultDisplay().getHeight();
        } else {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            displayHeight = size.y;
            displayWidth = size.x;

        }

        int labelSize = displayHeight / 7;
        int labelPos = (labelSize / 4);


        LinearLayout s = (LinearLayout) findViewById(R.id.hubMain);
        LayoutInflater inflater = LayoutInflater.from(this);
        View space = new View(this);
        Button b;
        TextView v;
        space.setLayoutParams(new LinearLayout.LayoutParams(displayWidth, labelPos));


        //Instagram
        inflater.inflate(R.layout.hub_cell, s);
        b = (Button) findViewById(R.id.hubCellButton);
        b.getLayoutParams().height = labelSize;
        b.getLayoutParams().width = labelSize;

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
        v.getLayoutParams().height = labelSize;
        v.setText("Instagram profile of the Jarvis CI App");
        v.setId(0);
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


        //Twitter
        inflater.inflate(R.layout.hub_cell, s);
        b = (Button) findViewById(R.id.hubCellButton);
        b.getLayoutParams().height = labelSize;
        b.getLayoutParams().width = labelSize;
        b.setId(0);
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            b.setBackgroundDrawable(getResources().getDrawable(R.drawable.twitterlogo));
        } else {
            b.setBackground(getResources().getDrawable(R.drawable.twitterlogo));
        }

        b.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.getBackground().clearColorFilter();
                    goToLink("https://twitter.com/jarvisstudents");

                    v.invalidate();


                } else {
                    v.getBackground().setColorFilter(Color.parseColor("#AA000000"), PorterDuff.Mode.SRC_ATOP);
                    v.invalidate();

                }

                return true;
            }
        });
        v = (TextView) findViewById(R.id.hubCellTextView);
        v.getLayoutParams().height = labelSize;
        v.setText("Twitter profile of the Jarvis CI student council");
        v.setId(0);
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.getBackground().clearColorFilter();
                    goToLink("https://twitter.com/jarvisstudents");

                    v.invalidate();


                } else {
                    v.getBackground().setColorFilter(Color.parseColor("#AA000000"), PorterDuff.Mode.SRC_ATOP);
                    v.invalidate();

                }

                return true;
            }
        });
        v.invalidate();

        space = new View(this);
        space.setLayoutParams(new LinearLayout.LayoutParams(displayWidth, labelPos));
        s.addView(space, 3);

        //Jarvis Website
        inflater.inflate(R.layout.hub_cell, s);
        b = (Button) findViewById(R.id.hubCellButton);
        b.getLayoutParams().height = labelSize;
        b.getLayoutParams().width = labelSize;
        b.setId(0);
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            b.setBackgroundDrawable(getResources().getDrawable(R.drawable.minerva_shield_colour));
        } else {
            b.setBackground(getResources().getDrawable(R.drawable.minerva_shield_colour));
        }

        b.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.getBackground().clearColorFilter();
                    goToLink("http://jarviscollegiate.com");

                    v.invalidate();


                } else {
                    v.getBackground().setColorFilter(Color.parseColor("#AA000000"), PorterDuff.Mode.SRC_ATOP);
                    v.invalidate();

                }

                return true;
            }
        });
        v = (TextView) findViewById(R.id.hubCellTextView);
        v.getLayoutParams().height = labelSize;
        v.setText("Jarvis CI Website");
        v.setId(0);
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.getBackground().clearColorFilter();
                    goToLink("http://jarviscollegiate.com");

                    v.invalidate();


                } else {
                    v.getBackground().setColorFilter(Color.parseColor("#AA000000"), PorterDuff.Mode.SRC_ATOP);
                    v.invalidate();

                }

                return true;
            }
        });
        v.invalidate();

        space = new View(this);
        space.setLayoutParams(new LinearLayout.LayoutParams(displayWidth, labelPos));
        s.addView(space, 5);

        //Facebook
        inflater.inflate(R.layout.hub_cell, s);
        b = (Button) findViewById(R.id.hubCellButton);
        b.getLayoutParams().height = labelSize;
        b.getLayoutParams().width = labelSize;
        b.setId(0);


        b.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.getBackground().clearColorFilter();
                    goToLink("https://m.facebook.com/pages/Jarvis-Collegiate-Institute/111887858827833");

                    v.invalidate();


                } else {
                    v.getBackground().setColorFilter(Color.parseColor("#AA000000"), PorterDuff.Mode.SRC_ATOP);
                    v.invalidate();

                }

                return true;
            }
        });
        v = (TextView) findViewById(R.id.hubCellTextView);
        v.getLayoutParams().height = labelSize;
        v.setText("Facebook page of Jarvis CI");
        v.setId(0);
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.getBackground().clearColorFilter();
                    goToLink("https://m.facebook.com/pages/Jarvis-Collegiate-Institute/111887858827833");

                    v.invalidate();


                } else {
                    v.getBackground().setColorFilter(Color.parseColor("#AA000000"), PorterDuff.Mode.SRC_ATOP);
                    v.invalidate();

                }

                return true;
            }
        });
        v.invalidate();

        space = new View(this);
        space.setLayoutParams(new LinearLayout.LayoutParams(displayWidth, labelPos));
        s.addView(space, 7);
        //Emails
        inflater.inflate(R.layout.hub_cell, s);
        b = (Button) findViewById(R.id.hubCellButton);
        b.setVisibility(View.GONE);
        v = (TextView) findViewById(R.id.hubCellTextView);
        v.getLayoutParams().height = labelSize;
        v.setBackgroundColor(getResources().getColor(R.color.primary));
        v.setText("Be sure to send us your feedback at jarvisCIApp@gmail.com");


    }

    void goToLink(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
