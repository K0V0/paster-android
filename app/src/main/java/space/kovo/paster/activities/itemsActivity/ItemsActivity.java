package space.kovo.paster.activities.itemsActivity;

import android.os.Bundle;
import space.kovo.paster.R;
import space.kovo.paster.activities.activity.BaseActivity;
import space.kovo.paster.activities.itemsActivity.actions.ItemsActivityActions;
import space.kovo.paster.activities.itemsActivity.eventsResolver.ItemsActivityEventsResolver;
import space.kovo.paster.activities.itemsActivity.intentResolver.ItemsActivityIntentResolver;
import space.kovo.paster.activities.itemsActivity.uiActionsResolver.UiActionsResolver;

public class ItemsActivity extends BaseActivity {
    private ItemsActivityEventsResolver eventsResolver;
    private ItemsActivityActions actions;
    private ItemsActivityIntentResolver intentResolver;
    private UiActionsResolver uiActionsResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        this.actions = new ItemsActivityActions(this);
        this.eventsResolver = new ItemsActivityEventsResolver(this);
        this.intentResolver = new ItemsActivityIntentResolver(this);
        this.uiActionsResolver = new UiActionsResolver(this);
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
        eventsResolver.handle(event);
    }

    private void inits() {
        eventsResolver.onNewItems(() -> actions.refreshItems());
        eventsResolver.onClipboardSet((itemId) -> actions.sendItemToClipboard(itemId));
        intentResolver.setOnSharedContentIncomingHandler(text -> actions.sendItemToServer(text));
        uiActionsResolver.setOnManualContentSubmit(text -> actions.sendItemToServer(text));
        intentResolver.handle();
        uiActionsResolver.handle();
    }
}