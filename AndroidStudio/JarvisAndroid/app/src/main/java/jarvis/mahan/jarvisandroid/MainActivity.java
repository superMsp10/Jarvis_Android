package jarvis.mahan.jarvisandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


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
        Point size = new Point();
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


        final Button hubButton = (Button) findViewById(R.id.tohubButton);
        hubButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               changeToHub();
            }
        });

        final Button calenderButton = (Button) findViewById(R.id.calenderButton);
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


    }

    private void changeToHub() {

        Intent intent;
        intent =  new Intent(this,Hub.class);
        startActivity(intent);

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
