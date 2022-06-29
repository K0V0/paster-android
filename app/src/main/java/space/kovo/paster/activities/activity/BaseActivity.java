package space.kovo.paster.activities.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

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
    }

    @Override
    protected void onStart() {
        super.onStart();
        binders.bindServices();
    }
    @Override
    protected void onResume() {
        super.onResume();
        recievers.registerRecievers();
        recievers.setOnPoorOrNoConnectionHandler(() -> actions.showDialogIfInternetNoneOrPoor());
    }

    @Override
    protected void onPause() {
        super.onPause();
        recievers.unregisterRecievers();
    }

    @Override
    protected void onStop() {
        super.onStop();
        binders.unbindServices();
    }
}
