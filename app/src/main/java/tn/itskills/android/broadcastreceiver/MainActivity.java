package tn.itskills.android.broadcastreceiver;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends ServiceConnexionActivity {

    private static final String TAG = "MainActivity";
    private RelativeLayout relativeLayout;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        relativeLayout = (RelativeLayout) findViewById(R.id.content_main);
        textView = (TextView) findViewById(R.id.textview);

        if (Utility.isNetworkAvailable(this)) {
            textView.setText("is connected");
            relativeLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        } else {
            textView.setText("is disconnected");
            relativeLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        }

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(ServiceEvent.NetworkChangeEvent event) {

        Log.v(TAG, "MainActivity ====> onEvent: event is "+event.isActived());
        if (event.isActived()) {

            textView.setText("is connected");
            relativeLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        } else {
            textView.setText("is disconnected");
            relativeLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
        }

        return super.onOptionsItemSelected(item);
    }
}
