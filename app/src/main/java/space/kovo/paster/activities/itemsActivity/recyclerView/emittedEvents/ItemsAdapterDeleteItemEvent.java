package space.kovo.paster.activities.itemsActivity.recyclerView.emittedEvents;

import space.kovo.paster._base.eventBus.Event;

public class ItemsAdapterDeleteItemEvent extends Event<Long> {

    public ItemsAdapterDeleteItemEvent(Long data) {
        super(data);
    }
}
