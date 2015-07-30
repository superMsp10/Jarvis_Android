package jarvis.mahan.jarvisandroid;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends ActionBarActivity {

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
                        .setDefaultFontPath("fonts/Roboto-RobotoCondensed.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }





}
