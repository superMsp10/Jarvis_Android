package jarvis.mahan.jarvisandroid;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity {

    private ImageView logoPicture;
    private Drawable lady_logoPicture;
    private Drawable bullDog_logoPicture;

    private boolean changedLogo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       lady_logoPicture = getResources().getDrawable(R.drawable.capture);
       bullDog_logoPicture = getResources().getDrawable(R.drawable.bulldogimage);
       logoPicture = (ImageView)findViewById(R.id.imageView);

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_search) {

            openSearch();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void openSearch() {
        if (!changedLogo) {
            logoPicture.setImageDrawable(bullDog_logoPicture);
            changedLogo = true;
        } else {
            logoPicture.setImageDrawable(lady_logoPicture);

            changedLogo = false;
        }

    }
}
