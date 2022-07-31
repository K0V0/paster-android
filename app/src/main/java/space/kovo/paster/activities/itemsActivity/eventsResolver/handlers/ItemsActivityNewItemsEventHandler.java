package space.kovo.paster.activities.itemsActivity.eventsResolver.handlers;

import space.kovo.paster.base.resolvers.EventsResolverEventHandler;

public interface ItemsActivityNewItemsEventHandler extends EventsResolverEventHandler<Object> {
    void apply(Object nothing);
}
