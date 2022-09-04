package space.kovo.paster.activities.itemsActivity.eventsResolver;

import android.content.Context;
import space.kovo.paster.activities.itemsActivity.eventsResolver.handlers.ItemsActivityClipboardChangeEventHandler;
import space.kovo.paster.activities.itemsActivity.eventsResolver.handlers.ItemsActivityItemDeleteEventHandler;
import space.kovo.paster.activities.itemsActivity.eventsResolver.handlers.ItemsActivityItemSetToClipboardEventHandler;
import space.kovo.paster.activities.itemsActivity.eventsResolver.handlers.ItemsActivityNewItemsEventHandler;
import space.kovo.paster.activities.itemsActivity.recyclerView.emittedEvents.ItemsAdapterDeleteItemEvent;
import space.kovo.paster.activities.itemsActivity.recyclerView.emittedEvents.ItemsAdapterSetToClipboardEvent;
import space.kovo.paster.androidServices.clipboardObserver.ClipboardObserverEvent;
import space.kovo.paster.androidServices.incomingDataObserver.IncomingDataObserverEvent;
import space.kovo.paster.base.resolvers.EventsResolver;

import java.util.Optional;

public class ItemsActivityEventsResolver extends EventsResolver {
    private ItemsActivityNewItemsEventHandler onNewItems;
    private ItemsActivityItemSetToClipboardEventHandler onItemSet;
    private ItemsActivityClipboardChangeEventHandler onClipChange;
    private ItemsActivityItemDeleteEventHandler onItemDelete;

    public ItemsActivityEventsResolver(Context context) {
        super(context);
    }

    public void handle(Object event) {
        Optional.ofNullable(event)
                .ifPresent(e -> {
                    if (e instanceof IncomingDataObserverEvent) { handleNewItems((IncomingDataObserverEvent) e); }
                    else if (e instanceof ItemsAdapterSetToClipboardEvent) { handleItemSet((ItemsAdapterSetToClipboardEvent) e); }
                    else if (e instanceof ClipboardObserverEvent) { handleClipboardChange((ClipboardObserverEvent) e); }
                    else if (e instanceof ItemsAdapterDeleteItemEvent) { handleItemDelete((ItemsAdapterDeleteItemEvent) e); }
                });
    }



    public void onNewItems(ItemsActivityNewItemsEventHandler onNewItems) {
        this.onNewItems = onNewItems;
    }

    public void onClipboardSet(ItemsActivityItemSetToClipboardEventHandler onItemSet) {
        this.onItemSet = onItemSet;
    }

    public void onClipboardChange(ItemsActivityClipboardChangeEventHandler onClipChange) { this.onClipChange = onClipChange; }

    public void onItemDelete(ItemsActivityItemDeleteEventHandler onItemDelete) { this.onItemDelete = onItemDelete; }



    private void handleNewItems(IncomingDataObserverEvent e) {
        Optional.ofNullable(e)
                .filter(evt -> onNewItems != null)
                .filter(IncomingDataObserverEvent::getData)
                .ifPresent(evt -> onNewItems.apply(null));
    }

    private void handleItemSet(ItemsAdapterSetToClipboardEvent e) {
        super.<ItemsAdapterSetToClipboardEvent, ItemsActivityItemSetToClipboardEventHandler, Long> runAction(e, onItemSet);
    }

    private void handleClipboardChange(ClipboardObserverEvent e) {
        super.<ClipboardObserverEvent, ItemsActivityClipboardChangeEventHandler, String> runAction(e, onClipChange);
    }

    private void handleItemDelete(ItemsAdapterDeleteItemEvent e) {
        super.<ItemsAdapterDeleteItemEvent, ItemsActivityItemDeleteEventHandler, Long> runAction(e, onItemDelete);
    }
}
