package space.kovo.paster.activities.itemsActivity.eventsResolver.handlers;

import space.kovo.paster.base.resolvers.EventsResolverEventHandler;

public interface ItemsActivityItemSetToClipboardEventHandler extends EventsResolverEventHandler<Long> {
    void apply(Long itemId);
}
