package space.kovo.paster.activities.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.util.Log;
import space.kovo.paster.activities.activity.baseActivityRecieversHandlers.OnNewDataHandler;
import space.kovo.paster.activities.activity.baseActivityRecieversHandlers.OnPoorOrNoConnectionHandler;
import space.kovo.paster.services.connectivityService.ConnectivityService;
import space.kovo.paster.services.connectivityService.ConnectivityServiceImpl;

public class BaseActivityRecievers {
    private final Context context;
    private final ConnectivityService connectivityService;
    private OnPoorOrNoConnectionHandler onPoorOrNoConnectionHandler;
    private OnNewDataHandler onNewDataHandler;

    public BaseActivityRecievers(Context context) {
        this.context = context;
        this.connectivityService = new ConnectivityServiceImpl(context);
    }

    public void registerRecievers() {
        registerNetworkChangeReciever();
    }

    public void unregisterRecievers() {
        unregisterNetworkChangeReciever();
    }

    public void registerNetworkChangeReciever() {
        context.registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    public void unregisterNetworkChangeReciever() {
        context.unregisterReceiver(networkChangeReceiver);
    }

    public void registerDataChangeReciever() {
        context.registerReceiver(newDataReceiver, new IntentFilter("incomingDataObserver.data.new"));
    }

    public void unregisterDataChangeReciever() {
        context.unregisterReceiver(newDataReceiver);
    }

    public void setOnPoorOrNoConnectionHandler(OnPoorOrNoConnectionHandler onPoorOrNoConnectionHandler) {
        this.onPoorOrNoConnectionHandler = onPoorOrNoConnectionHandler;
    }

    public void setOnNewDataHandler(OnNewDataHandler onNewDataHandler) {
        this.onNewDataHandler = onNewDataHandler;
    }

    private BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("baseActivityRecievers: networkChangeReciever", "network has changed");
            if (onPoorOrNoConnectionHandler != null) {
                if (!connectivityService.isConnectedToNetwork()) {
                    onPoorOrNoConnectionHandler.onPoorOrNoConnection();
                }
            }
        }
    };

    private BroadcastReceiver newDataReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("baseActivityRecievers: newDataReciever", "have new data");
            onNewDataHandler.onNewData();
        }
    };

}
