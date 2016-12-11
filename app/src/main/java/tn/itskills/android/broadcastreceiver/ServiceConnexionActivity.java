package tn.itskills.android.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by adnenhamdouni on 10/12/2016.
 */

public class ServiceConnexionActivity extends AppCompatActivity{

    protected NetworkChangeReceiver mNetworkChangeReceiver;
    private NetworkInfo.State mNetworkConnectionState;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNetworkChangeReceiver = new NetworkChangeReceiver();

        mNetworkConnectionState = NetworkInfo.State.CONNECTED;

    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetworkChangeReceiver,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mNetworkChangeReceiver);
    }

    public class NetworkChangeReceiver extends BroadcastReceiver {

        ServiceEvent.NetworkChangeEvent event;

        @Override
        public void onReceive(Context context, Intent intent) {


            event = new ServiceEvent.NetworkChangeEvent();

            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = connMgr.getActiveNetworkInfo();

            if (activeNetwork == null) {
                mNetworkConnectionState = NetworkInfo.State.DISCONNECTED;
                event.setActived(false);
                EventBus.getDefault().post(event);

            } else {
                if (activeNetwork.getState() != mNetworkConnectionState) {
                    mNetworkConnectionState = NetworkInfo.State.CONNECTED;
                    event.setActived(true);
                    // Post the event
                    EventBus.getDefault().post(event);
                }

            }


        }
    }



}

