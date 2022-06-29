package space.kovo.paster.activities.itemsActivity;

import android.os.Bundle;
import space.kovo.paster.R;
import space.kovo.paster.activities.activity.BaseActivity;
import space.kovo.paster.activities.activity.baseActivityRecieversHandlers.OnNewDataHandler;

public class ItemsActivity extends BaseActivity {

    private ItemsActivityActions actions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        this.actions = new ItemsActivityActions(this);
        recievers.setOnNewDataHandler(() -> actions.loadItems());
    }

    @Override
    protected void onResume() {
        super.onResume();
        recievers.registerDataChangeReciever();
    }

    @Override
    protected void onPause() {
        super.onPause();
        recievers.unregisterDataChangeReciever();
    }
}