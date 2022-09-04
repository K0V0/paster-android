package space.kovo.paster.base.activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import space.kovo.paster.androidServices.clipboardObserver.ClipboardObserver;
import space.kovo.paster.androidServices.incomingDataObserver.IncomingDataObserver;
import space.kovo.paster.services.loginService.LoginService;
import space.kovo.paster.services.loginService.LoginServiceImpl;
import space.kovo.paster.utils.Logging;

public class BaseActivityBinders {
    private final Context context;
    private final LoginService loginService;
    private IncomingDataObserver incomingDataObserver;
    private ClipboardObserver clipboardObserver;
    private boolean incomingDataObserverIsBound;
    private boolean clipboardObserverIsBound;

    public BaseActivityBinders(Context context) {
        this.context = context;
        this.loginService = new LoginServiceImpl(context);
    }



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
    public void destroyIncomingDataObserver() { destroyService(incomingDataObserver); }

    public ClipboardObserver getClipboardObserver() {
        return clipboardObserver;
    }
    public void bindClipboardObserver() {
        if (loginService.isLoggedIn()) {
            Logging.log("baseActivityBinders: bindClipboardObserver", "binder binded");
            Intent intent = new Intent(context, ClipboardObserver.class);
            context.startService(intent);
            context.bindService(intent, clipboardObserverServiceConnection, Context.BIND_AUTO_CREATE);
        }
    }
    public void unbindClipboardObserver() {
        Logging.log("baseActivityBinders: bindClipboardObserver", "binder unbinded");
        context.unbindService(clipboardObserverServiceConnection);
    }
    public void destroyClipboardObserver() { destroyService(clipboardObserver); }




    private final ServiceConnection incomingDataObserverServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            IncomingDataObserver.IncomingDataObserverBinder binder = (IncomingDataObserver.IncomingDataObserverBinder) service;
            incomingDataObserver = binder.getIncomingDataObserver();
            incomingDataObserverIsBound = true;
        }
        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            incomingDataObserverIsBound = false;
        }
    };

    private final ServiceConnection clipboardObserverServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            ClipboardObserver.ClipboardObserverBinder binder = (ClipboardObserver.ClipboardObserverBinder) service;
            clipboardObserver = binder.getIncomingDataObserver();
            clipboardObserverIsBound = true;
        }
        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            clipboardObserverIsBound = false;
        }
    };

    private static void destroyService(Service service) {
        if (service != null) {
            service.onDestroy();
        }
    }
}
