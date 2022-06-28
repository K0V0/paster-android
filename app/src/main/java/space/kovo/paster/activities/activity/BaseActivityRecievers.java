package space.kovo.paster.activities.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.util.Log;
import space.kovo.paster.services.connectivityService.ConnectivityService;
import space.kovo.paster.services.connectivityService.ConnectivityServiceImpl;

public class BaseActivityRecievers {
    private final Context context;

    private final BroadcastReceiver networkChangeReceiver;

    private final ConnectivityService connectivityService;

    private OnPoorOrNoConnectionHandler onPoorOrNoConnectionHandler;

    public BaseActivityRecievers(Context context) {
        this.context = context;
        this.networkChangeReceiver = getNetworkChangeReceiver();
        this.connectivityService = new ConnectivityServiceImpl(context);
    }

    public void registerRecievers() {
        IntentFilter connectivityCheckFilter = new IntentFilter();
        connectivityCheckFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(networkChangeReceiver, connectivityCheckFilter);
    }

    public void unregisterRecievers() {
        context.unregisterReceiver(networkChangeReceiver);
    }

    public void setOnPoorOrNoConnectionHandler(OnPoorOrNoConnectionHandler onPoorOrNoConnectionHandler) {
        this.onPoorOrNoConnectionHandler = onPoorOrNoConnectionHandler;
    }

    private BroadcastReceiver getNetworkChangeReceiver() {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("app","Network connectivity change");
                if (onPoorOrNoConnectionHandler != null) {
                    if (!connectivityService.isConnectedToNetwork()) {
                        onPoorOrNoConnectionHandler.onPoorOrNoConnection();
                    }
                }
            }
        };
    }
}
