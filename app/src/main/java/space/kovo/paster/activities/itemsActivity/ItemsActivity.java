package space.kovo.paster.activities.itemsActivity;

import android.os.Bundle;
import space.kovo.paster.R;
import space.kovo.paster.activities.activity.BaseActivity;
import space.kovo.paster.activities.itemsActivity.actions.ItemsActivityActions;
import space.kovo.paster.activities.itemsActivity.events.ItemsActivityEvents;

public class ItemsActivity extends BaseActivity {
    private ItemsActivityEvents events;
    private ItemsActivityActions actions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        this.actions = new ItemsActivityActions(this);
        this.events = new ItemsActivityEvents(this);
        events.onNewItems(() -> actions.refreshItems());
        events.onClipboardSet((itemId) -> actions.sendItemToClipboard(itemId));
    }

    @Override
    protected void onStart() {
        super.onStart();
        binders.bindIncomingDataObserver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        actions.refreshItems();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        binders.unbindIncomingDataObsever();
    }

    @Override
    public void onEventBus(Object event) {
        super.onEventBus(event);
        events.handle(event);
    }
}