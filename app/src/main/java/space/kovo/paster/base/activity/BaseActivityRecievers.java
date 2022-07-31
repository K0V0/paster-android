package space.kovo.paster.base.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import space.kovo.paster.base.activity.baseActivityRecieversHandlers.OnCloseCommandRecievedHandler;
import space.kovo.paster.base.activity.baseActivityRecieversHandlers.OnPoorOrNoConnectionHandler;
import space.kovo.paster.services.connectivityService.ConnectivityService;
import space.kovo.paster.services.connectivityService.ConnectivityServiceImpl;
import space.kovo.paster.utils.Logging;

public class BaseActivityRecievers {
    private static final String CLOSE_APP_INTENT_ACTION = "close_app";
    private final Context context;
    private final ConnectivityService connectivityService;
    private OnPoorOrNoConnectionHandler onPoorOrNoConnectionHandler;
    private OnCloseCommandRecievedHandler onCloseCommandRecievedHandler;

    public BaseActivityRecievers(Context context) {
        this.context = context;
        this.connectivityService = new ConnectivityServiceImpl(context);
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

    public void registerCloseAppReciever() {
        Logging.log("baseActivityRecievers: closeAppReciever", "reciever registered");
        context.registerReceiver(closeAppReciever, new IntentFilter(CLOSE_APP_INTENT_ACTION));
    }

    public void unregisterCloseAppReciever() { context.unregisterReceiver(closeAppReciever); }

    public void setOnCloseCommandRecievedHandler(OnCloseCommandRecievedHandler onCloseCommandRecievedHandler) {
        this.onCloseCommandRecievedHandler = onCloseCommandRecievedHandler;
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

    private BroadcastReceiver closeAppReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Logging.log("baseActivityRecievers: closeAppREciever", "instruction for app termination recieved");
            onCloseCommandRecievedHandler.onClose();
        }
    };

}
