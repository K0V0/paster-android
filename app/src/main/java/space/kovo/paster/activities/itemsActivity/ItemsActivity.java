package space.kovo.paster.activities.itemsActivity;

import android.os.Bundle;
import space.kovo.paster.R;
import space.kovo.paster.activities.activity.BaseActivity;
import space.kovo.paster.activities.itemsActivity.actions.ItemsActivityActions;
import space.kovo.paster.activities.itemsActivity.events.ItemsActivityEvents;
import space.kovo.paster.activities.itemsActivity.intentResolver.ItemsActivityIntentResolver;
import space.kovo.paster.utils.Logging;

public class ItemsActivity extends BaseActivity {
    private ItemsActivityEvents events;
    private ItemsActivityActions actions;
    private ItemsActivityIntentResolver intentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        Logging.log(getIntent().getStringExtra("newItem"), "");
        this.actions = new ItemsActivityActions(this);
        this.events = new ItemsActivityEvents(this);
        this.intentResolver = new ItemsActivityIntentResolver(this);
        inits();
    }

    @Override
    protected void onStart() {
        super.onStart();
        binders.bindIncomingDataObserver();
        binders.bindClipboardObserver();
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
        binders.unbindClipboardObserver();
    }

    @Override
    public void onEventBus(Object event) {
        super.onEventBus(event);
        events.handle(event);
    }

    private void inits() {
        events.onNewItems(() -> actions.refreshItems());
        events.onClipboardSet((itemId) -> actions.sendItemToClipboard(itemId));
        intentResolver.setOnSharedContentIncomingHandler(text -> actions.sendItemToServer(text));
        intentResolver.handle();
    }
}