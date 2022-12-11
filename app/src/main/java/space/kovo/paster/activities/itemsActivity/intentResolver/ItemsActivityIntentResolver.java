package space.kovo.paster.activities.itemsActivity.intentResolver;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import space.kovo.paster.activities.itemsActivity.ItemsActivity;
import space.kovo.paster.activities.itemsActivity.intentResolver.handlers.OnSharedContentIncomingHandler;
import space.kovo.paster.base.resolvers.IntentResolver;
import space.kovo.paster.utils.Logging;

import java.util.Optional;

import static android.content.Intent.getIntent;

public class ItemsActivityIntentResolver extends IntentResolver {

    //TODO postrkat taketo konstanty ktore sa mozu nachadzat vo viac castiach aplikacie niekde na jedno miesto
    private static final String NEW_ITEM_INTENT = "newItem";

    private OnSharedContentIncomingHandler onSharedContentIncomingHandler;

    public ItemsActivityIntentResolver(Context context) {
        super(context, ((ItemsActivity)context).getIntent());
    }

    public void handle() {
        Intent intent1 = super.intent;
        String action = intent.getAction();
        String type = intent.getType();
        //intent1.getByteArrayExtra();
        Uri uri = intent1.getData();
        Object o = intent1.getParcelableExtra(Intent.EXTRA_STREAM);
        if (uri != null) {
            String mime = context.getContentResolver().getType(uri); //TODO NPE
            System.out.println(mime);
        }
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
