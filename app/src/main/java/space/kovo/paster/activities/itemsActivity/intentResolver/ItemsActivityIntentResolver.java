package space.kovo.paster.activities.itemsActivity.intentResolver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import space.kovo.paster.activities.itemsActivity.intentResolver.handlers.OnSharedContentIncomingHandler;
import space.kovo.paster.utils.Logging;

import java.util.Optional;

public class ItemsActivityIntentResolver {
    private final Context context;
    private final Intent intent;
    private OnSharedContentIncomingHandler onSharedContentIncomingHandler;

    public ItemsActivityIntentResolver(Context context) {
        this.context = context;
        this.intent = ((Activity)context).getIntent();
    }

    public void handle() {
        Optional.ofNullable(intent)
                .filter(i -> i.getExtras() != null && i.getExtras().size() > 0)
                .ifPresent(i -> {
                    if (i.getStringExtra("newItem") != null) {
                        handleSharedContentIncoming(i.getStringExtra("newItem"));
                    }
                });
    }

    public void setOnSharedContentIncomingHandler(OnSharedContentIncomingHandler onSharedContentIncomingHandler) {
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
