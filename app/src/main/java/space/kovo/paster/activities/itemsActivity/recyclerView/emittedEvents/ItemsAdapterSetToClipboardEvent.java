package space.kovo.paster.activities.itemsActivity.recyclerView.emittedEvents;

import space.kovo.paster.base.eventBus.Event;

public class ItemsAdapterSetToClipboardEvent extends Event<Long> {

    public ItemsAdapterSetToClipboardEvent(Long data) {
        super(data);
    }
}
