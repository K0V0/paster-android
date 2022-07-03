package space.kovo.paster.activities.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import space.kovo.paster.activities.activity.baseActivityRecieversHandlers.OnPoorOrNoConnectionHandler;
import space.kovo.paster.services.connectivityService.ConnectivityService;
import space.kovo.paster.services.connectivityService.ConnectivityServiceImpl;
import space.kovo.paster.utils.Logging;

public class BaseActivityRecievers {
    private final Context context;
    private final ConnectivityService connectivityService;
    private OnPoorOrNoConnectionHandler onPoorOrNoConnectionHandler;

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
        Logging.log("baseActivityRecievers: networkChangeReciever", "reciever registered");
        context.registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    public void unregisterNetworkChangeReciever() {
        context.unregisterReceiver(networkChangeReceiver);
    }

    public void setOnPoorOrNoConnectionHandler(OnPoorOrNoConnectionHandler onPoorOrNoConnectionHandler) {
        this.onPoorOrNoConnectionHandler = onPoorOrNoConnectionHandler;
    }



    private BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Logging.log("baseActivityRecievers: networkChangeReciever", "network has changed");
            if (onPoorOrNoConnectionHandler != null) {
                //TODO netestuje ci je pripojenie k internetu realne funkcne
                if (!connectivityService.isConnectedToNetwork()) {
                    onPoorOrNoConnectionHandler.onPoorOrNoConnection();
                }
            }
        }
    };

}
