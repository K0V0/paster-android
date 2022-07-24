package space.kovo.paster.activities.mainActivity;

import android.os.Bundle;
import space.kovo.paster.R;
import space.kovo.paster._base.activity.BaseActivity;

public class MainActivity extends BaseActivity {

    private MainActivityActions actions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actions = new MainActivityActions(this);
        actions.decideActionAfterStart();
    }

}