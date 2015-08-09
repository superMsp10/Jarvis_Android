package jarvis.mahan.jarvisandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class DetailedCafeMenu extends ActionBarActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_cafe_menu);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.primary));
        }
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("Capsuula.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

        setTitle("Menu");


        Intent intent = getIntent();
        String message = intent.getStringExtra("Menu");
        try {
            useData(new JSONArray(message));
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    void useData(JSONArray data) throws JSONException {

        int displayHeight = getWindowManager().getDefaultDisplay().getHeight();
        int cellHeight = displayHeight / 4;
        LinearLayout s = (LinearLayout) findViewById(R.id.DetailedCafeLinearlayout);

        LayoutInflater inflater = LayoutInflater.from(this);
        // Log.println(Log.ASSERT, "Linear layout",s.toString());
        for (int i = 0; i < data.length(); i++) {
            String name = data.getString(i);
            inflater.inflate(R.layout.detailed_cafe_menu_cell, s);

            TextView t = (TextView) s.getChildAt(i);
            t.setId(i);
            t.getLayoutParams().height = cellHeight;
            t.requestLayout();
            t.setText(name);
        }


    }


}
