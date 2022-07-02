package space.kovo.paster.activities.itemsActivity.events;

import android.content.Context;
import space.kovo.paster.activities.itemsActivity.recyclerView.ItemsAdapterSetToClipboardEvent;
import space.kovo.paster.androidServices.incomingDataObserver.IncomingDataObserverEvent;

import java.util.Optional;

public class ItemsActivityEvents {
    private final Context context;
    private ItemsActivityNewItemsEventHandler onNewItems;
    private ItemsActivityItemSetToClipboardEventHandler onItemSet;

    public ItemsActivityEvents(Context context) {
        this.context = context;
    }

    public void handle(Object event) {
        Optional.ofNullable(event)
                .ifPresent(e -> {
                    if (e instanceof IncomingDataObserverEvent) { handleNewItems((IncomingDataObserverEvent) e); }
                    else if (e instanceof ItemsAdapterSetToClipboardEvent) { handleItemSet((ItemsAdapterSetToClipboardEvent) e);  }
                });
    }



    public void onNewItems(ItemsActivityNewItemsEventHandler onNewItems) {
        this.onNewItems = onNewItems;
    }

    public void onClipboardSet(ItemsActivityItemSetToClipboardEventHandler onItemSet) {
        this.onItemSet = onItemSet;
    }



    private void handleNewItems(IncomingDataObserverEvent e) {
        if (e.hasNewData()) {
            onNewItems.apply();
        }
    }

    private void handleItemSet(ItemsAdapterSetToClipboardEvent e) {
        onItemSet.apply(e.getItemId());
    }
}
