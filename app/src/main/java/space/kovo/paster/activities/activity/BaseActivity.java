package space.kovo.paster.activities.activity;

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
        recievers.setOnPoorOrNoConnectionHandler(() -> actions.showDialogIfInternetNoneOrPoor());
//        recievers.setOnShareActionContentRecievedHandler((text) -> {});
        actions = new BaseActivityActions(this);
        binders = new BaseActivityBinders(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        binders.bindServices();
    }
    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        recievers.registerRecievers();
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
        recievers.unregisterRecievers();
    }

    @Override
    protected void onStop() {
        super.onStop();
        binders.unbindServices();
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
}
