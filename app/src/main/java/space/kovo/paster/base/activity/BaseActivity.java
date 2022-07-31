package space.kovo.paster.base.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import space.kovo.paster.utils.Logging;

public class BaseActivity extends AppCompatActivity {

    protected BaseActivityRecievers recievers;
    protected BaseActivityActions actions;
    protected BaseActivityBinders binders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recievers = new BaseActivityRecievers(this);
        actions = new BaseActivityActions(this);
        binders = new BaseActivityBinders(this);
        inits();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        recievers.registerNetworkChangeReciever();
        //recievers.registerCloseAppReciever();
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
        recievers.unregisterNetworkChangeReciever();
        //recievers.unregisterCloseAppReciever();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    protected void onDestroy() {
        super.onDestroy();
        System.exit(0);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Subscribe
    public void onEventBus(Object event) {
        Logging.log("BaseActivity: onEventBus()", "event received");
    }

    private void inits() {
        recievers.setOnPoorOrNoConnectionHandler(actions::showDialogIfInternetNoneOrPoor);
        //recievers.setOnCloseCommandRecievedHandler(this::finishAffinity);
    }
}
