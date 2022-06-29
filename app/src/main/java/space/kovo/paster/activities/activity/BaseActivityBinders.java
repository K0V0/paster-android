package space.kovo.paster.activities.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import space.kovo.paster.backgroundServices.incomingDataObserver.IncomingDataObserver;
import space.kovo.paster.services.loginService.LoginService;
import space.kovo.paster.services.loginService.LoginServiceImpl;

public class BaseActivityBinders {
    private final Context context;
    private final LoginService loginService;
    private IncomingDataObserver incomingDataObserver;
    private boolean incomingDataObserverIsBound;


    public BaseActivityBinders(Context context) {
        this.context = context;
        this.loginService = new LoginServiceImpl(context);
    }

    public void bindServices() {
        if (loginService.isLoggedIn()) {
            Intent intent = new Intent(context, IncomingDataObserver.class);
            context.startService(intent);
            context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
        }
    }

    public void unbindServices() {
        context.unbindService(connection);
    }

    public IncomingDataObserver getIncomingDataObserver() {
        return incomingDataObserver;
    }

    private final ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            IncomingDataObserver.IncomingDataObserverBinder binder = (IncomingDataObserver.IncomingDataObserverBinder) service;
            incomingDataObserver = binder.getIncomingDataObserver();
            incomingDataObserverIsBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            incomingDataObserverIsBound = false;
        }
    };
}
