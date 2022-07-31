package space.kovo.paster.activities.itemsActivity.intentResolver;

import android.content.Context;
import space.kovo.paster.base.resolvers.IntentResolver;
import space.kovo.paster.activities.itemsActivity.ItemsActivity;
import space.kovo.paster.activities.itemsActivity.intentResolver.handlers.OnSharedContentIncomingHandler;
import space.kovo.paster.utils.Logging;

import java.util.Optional;

public class ItemsActivityIntentResolver extends IntentResolver {
    private static final String NEW_ITEM_INTENT = "newItem";

    private OnSharedContentIncomingHandler onSharedContentIncomingHandler;

    public ItemsActivityIntentResolver(Context context) {
        super(context, ((ItemsActivity)context).getIntent());
    }

    public void handle() {
        Optional.ofNullable(super.intent)
                .filter(i -> i.getExtras() != null && i.getExtras().size() > 0)
                .ifPresent(i -> {
                    if (i.getStringExtra(NEW_ITEM_INTENT) != null) {
                        Logging.log("ItemsActivityIntentResolver: handle() [newItem]", i.getStringExtra(NEW_ITEM_INTENT));
                        handleSharedContentIncoming(i.getStringExtra(NEW_ITEM_INTENT));
                    }
                });
    }

    public void onSharedContentIncoming(OnSharedContentIncomingHandler onSharedContentIncomingHandler) {
        this.onSharedContentIncomingHandler = onSharedContentIncomingHandler;
    }

    private void handleSharedContentIncoming(String text) {
        Logging.log("ItemsActivityIntentResolver: handleSharedContentIncoming()",
                String.format("got some text: %s", text));
        if (onSharedContentIncomingHandler != null) {
            onSharedContentIncomingHandler.apply(text);
        }
    }
}
