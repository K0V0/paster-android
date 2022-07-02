package space.kovo.paster.activities.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import space.kovo.paster.androidServices.incomingDataObserver.IncomingDataObserver;
import space.kovo.paster.services.loginService.LoginService;
import space.kovo.paster.services.loginService.LoginServiceImpl;
import space.kovo.paster.utils.Logging;

public class BaseActivityBinders {
    private final Context context;
    private final LoginService loginService;
    private IncomingDataObserver incomingDataObserver;
    private boolean incomingDataObserverIsBound;


    public BaseActivityBinders(Context context) {
        this.context = context;
        this.loginService = new LoginServiceImpl(context);
    }

    public void bindServices() {}

    public void unbindServices() {}



    public IncomingDataObserver getIncomingDataObserver() {
        return incomingDataObserver;
    }
    public void bindIncomingDataObserver() {
        if (loginService.isLoggedIn()) {
            Logging.log("baseActivityBinders: bindIncomingDataObserver", "binder binded");
            Intent intent = new Intent(context, IncomingDataObserver.class);
            context.startService(intent);
            context.bindService(intent, incomingDataObserverServiceConnection, Context.BIND_AUTO_CREATE);
        }
    }
    public void unbindIncomingDataObsever() {
        Logging.log("baseActivityBinders: bindIncomingDataObserver", "binder unbinded");
        context.unbindService(incomingDataObserverServiceConnection);
    }



    private final ServiceConnection incomingDataObserverServiceConnection = new ServiceConnection() {
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
