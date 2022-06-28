package space.kovo.paster.activities.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    protected BaseActivityRecievers recievers;
    protected BaseActivityActions actions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recievers = new BaseActivityRecievers(this);
        actions = new BaseActivityActions(this);
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
}
