package space.kovo.paster.activities.itemsActivity.events;

import android.content.Context;
import space.kovo.paster.activities.itemsActivity.recyclerView.ItemsAdapterSetToClipboardEvent;
import space.kovo.paster.androidServices.clipboardObserver.ClipboardObserverEvent;
import space.kovo.paster.androidServices.incomingDataObserver.IncomingDataObserverEvent;

import java.util.Optional;

public class ItemsActivityEvents {
    private final Context context;
    private ItemsActivityNewItemsEventHandler onNewItems;
    private ItemsActivityItemSetToClipboardEventHandler onItemSet;
    private ItemsActivityClipboardChangeEventHandler onClipChange;

    public ItemsActivityEvents(Context context) {
        this.context = context;
    }

    public void handle(Object event) {
        Optional.ofNullable(event)
                .ifPresent(e -> {
                    if (e instanceof IncomingDataObserverEvent) { handleNewItems((IncomingDataObserverEvent) e); }
                    else if (e instanceof ItemsAdapterSetToClipboardEvent) { handleItemSet((ItemsAdapterSetToClipboardEvent) e);  }
                    else if (e instanceof ClipboardObserverEvent) { handleClipboardChange((ClipboardObserverEvent) e);  }
                });
    }



    public void onNewItems(ItemsActivityNewItemsEventHandler onNewItems) {
        this.onNewItems = onNewItems;
    }

    public void onClipboardSet(ItemsActivityItemSetToClipboardEventHandler onItemSet) {
        this.onItemSet = onItemSet;
    }

    public void onClipboardChange(ItemsActivityClipboardChangeEventHandler onClipChange) { this.onClipChange = onClipChange; }

    private void handleNewItems(IncomingDataObserverEvent e) {
        Optional.ofNullable(e)
                .filter(evt -> onNewItems != null)
                .filter(IncomingDataObserverEvent::hasNewData)
                .ifPresent(evt -> onNewItems.apply());
    }

    private void handleItemSet(ItemsAdapterSetToClipboardEvent e) {
        Optional.ofNullable(e)
                .filter(evt -> onItemSet != null)
                .ifPresent(evt -> onItemSet.apply(evt.getItemId()));
    }

    private void handleClipboardChange(ClipboardObserverEvent e) {
        Optional.ofNullable(e)
                .filter(evt -> onClipChange != null)
                .ifPresent(evt -> onClipChange.apply(evt.getText()));
    }
}
