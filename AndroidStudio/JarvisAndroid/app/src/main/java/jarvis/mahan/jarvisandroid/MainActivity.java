package jarvis.mahan.jarvisandroid;

import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import static jarvis.mahan.jarvisandroid.R.drawable.bulldogimage;


public class MainActivity extends ActionBarActivity {

    private ImageView logoPicture;
    private Drawable temp_logoPicture;
    boolean changedLogo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logoPicture = (ImageView) findViewById(R.id.imageView);
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
            openSettings();
            return true;
        }else if(id == R.id.action_search){

            openSearch();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openSettings() {

    }

    private void openSearch() {
        if(!changedLogo) {
            temp_logoPicture = logoPicture.getDrawable();
            logoPicture.setImageResource(bulldogimage);
            changedLogo = true;
        }else{
            logoPicture.setImageDrawable(temp_logoPicture);
            temp_logoPicture = getDrawable(bulldogimage);
            changedLogo = false;


        }

            }
}
