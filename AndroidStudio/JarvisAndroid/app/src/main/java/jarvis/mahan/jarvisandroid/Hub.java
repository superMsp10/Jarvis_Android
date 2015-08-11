package jarvis.mahan.jarvisandroid;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class Hub extends AppCompatActivity {


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

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
        int textWidth = screenWidth - labelHeight;


        LinearLayout s = (LinearLayout) findViewById(R.id.hubMain);
        LayoutInflater inflater = LayoutInflater.from(this);
        View space = new View(this);
        View v;
        space.setLayoutParams(new LinearLayout.LayoutParams(screenWidth, labelPos));


        //Instagram
        inflater.inflate(R.layout.hub_cell, s);
        v = findViewById(R.id.hubCellButton);
        v.getLayoutParams().height = labelHeight;
        v.getLayoutParams().width = labelHeight;
        v = findViewById(R.id.hubCellTextView);
        v.getLayoutParams().height = labelHeight;
        v.invalidate();
        v.setId(0);



        s.addView(space, 1);
        inflater.inflate(R.layout.hub_cell, s);
        v = findViewById(R.id.hubCellButton);
        v.getLayoutParams().height = labelHeight;
        v.getLayoutParams().width = labelHeight;
        v = findViewById(R.id.hubCellTextView);
        v.getLayoutParams().height = labelHeight;
        v.invalidate();
        v.setId(0);


    }
}
