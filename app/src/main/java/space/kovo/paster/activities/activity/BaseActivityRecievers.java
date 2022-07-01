package space.kovo.paster.activities.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.util.Log;
import space.kovo.paster.activities.activity.baseActivityRecieversHandlers.OnClipboardInsertHandler;
import space.kovo.paster.activities.activity.baseActivityRecieversHandlers.OnNewDataHandler;
import space.kovo.paster.activities.activity.baseActivityRecieversHandlers.OnPoorOrNoConnectionHandler;
import space.kovo.paster.services.connectivityService.ConnectivityService;
import space.kovo.paster.services.connectivityService.ConnectivityServiceImpl;

public class BaseActivityRecievers {
    private final Context context;
    private final ConnectivityService connectivityService;
    private OnPoorOrNoConnectionHandler onPoorOrNoConnectionHandler;
    private OnNewDataHandler onNewDataHandler;
    //TODO nazvoslovie premenny spojenych s touto akcoiu - nemusi byt jasne ci sa jedna o skopirovanie do schranky
    // vyberom z poloziek v aplikacii alebo sa jedna o reakciu na skopirovanie do schranky vykonane inde v systeme
    private OnClipboardInsertHandler onClipboardInsertHandler;

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

    public void registerInsertToClipboardReciever() {
        context.registerReceiver(insertToClipboardReciever, new IntentFilter("itemsActivityActions.clipboard.insert"));
    }

    public void unregisterInsertToClipboardReciever() { context.unregisterReceiver(insertToClipboardReciever); }



    public void setOnPoorOrNoConnectionHandler(OnPoorOrNoConnectionHandler onPoorOrNoConnectionHandler) {
        this.onPoorOrNoConnectionHandler = onPoorOrNoConnectionHandler;
    }

    public void setOnNewDataHandler(OnNewDataHandler onNewDataHandler) {
        this.onNewDataHandler = onNewDataHandler;
    }

    public void setOnClipboardInsertHandler(OnClipboardInsertHandler onClipboardInsertHandler) {
        this.onClipboardInsertHandler = onClipboardInsertHandler;
    }



    private BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("baseActivityRecievers: networkChangeReciever", "network has changed");
            if (onPoorOrNoConnectionHandler != null) {
                //TODO netestuje ci je pripojenie k internetu realne funkcne
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

    private BroadcastReceiver insertToClipboardReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long itemId = intent.getLongExtra("idOfClipboardItem", 0);
            Log.d("baseActivityRecievers: insertToClipboardReciever", String.format("item '%d' selected to clipboard", itemId));
            onClipboardInsertHandler.onClipboardInsert(itemId);
        }
    };

}
